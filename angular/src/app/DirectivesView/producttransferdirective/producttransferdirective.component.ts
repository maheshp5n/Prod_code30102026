import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { LocationProductRequestResponse, LocationProductRequestItemResponse, OkItemResponse, LPTransferRequest, LPTransferItemRequest } from '../../services/model';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';

@Component({
  selector: 'app-producttransferdirective',
  templateUrl: './producttransferdirective.component.html',
  styleUrls: ['./producttransferdirective.component.scss']
})
export class ProducttransferdirectiveComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();
  locProductRequestResponse:LocationProductRequestResponse=new LocationProductRequestResponse();
  @Input()
  set productRequest(_productReqeust:LocationProductRequestResponse){
    if(_productReqeust){
      this.locProductRequestResponse=_productReqeust;
      this.getProductRequestItems(_productReqeust.prId);
    }
  }
  @Output() onTransferDone:EventEmitter<LPTransferRequest> =new EventEmitter<LPTransferRequest>();
  @Output() onCancel:EventEmitter<string>=new EventEmitter<string>();
  @ViewChild('alertModal',{static:false})alertModal:ModalDirective;

  locationProductRequestItemResponse:Array<LocationProductRequestItemResponse>=[];
  _lpTransferRequest:LPTransferRequest =new LPTransferRequest();
  constructor(private _productRequestService:LocationProductRequestService,private _ngxSpinner:NgxSpinnerService) { 
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  alertstypes={
    type:'',
    message:''
  }
  ngOnInit() {
  }
  getProductRequestItems(productRequestId:number,pageNumber?,size?){
    this._ngxSpinner.show();
    this._productRequestService.getItemsfortransfer(productRequestId).subscribe(data=>{
      this._ngxSpinner.hide();
      this.locationProductRequestItemResponse=data;
      for(let i=0;i<this.locationProductRequestItemResponse.length;i++){
        this.getOkLineItems(this.locationProductRequestItemResponse[i].lpRequestItemId);
      }
    },error=>{this._ngxSpinner.hide()});

    // this._productRequestService.getItems(productRequestId,'page='+pageNumber+'&size='+size).subscribe(items=>{
    //   if(items.first){
    //     this.locationProductRequestItemResponse=items.content;
    //   }
    //   else{
    //     items.content.forEach((item)=>{
    //       this.locationProductRequestItemResponse.push(item);
    //     });
    //   }
    //   if(!items.last){
    //     this.getProductRequestItems(this.locProductRequestResponse.prId,items.number+1);
    //   }
    //   if(items.last){
    //     for(let i=0;i<this.locationProductRequestItemResponse.length;i++){
    //       this.getOkLineItems(this.locationProductRequestItemResponse[i].lpRequestItemId);
    //     }
    //   }
    // },error=>this._ngxSpinner.hide());
  }
  getOkLineItems(lineitemId){
    this._productRequestService.getOkQuantity(this.locProductRequestResponse.prId,lineitemId).subscribe(data=>{
      for(let i=0;i<this.locationProductRequestItemResponse.length;i++){
        if(lineitemId==this.locationProductRequestItemResponse[i].lpRequestItemId){
          let ok=new OkItemResponse();
          ok.serialNumbers=[];
          ok.okQuantityOnHand=data.okQuantityOnHand;
          if(data.serialNumbers!=undefined &&data.serialNumbers!=null){
            for(let j=0;j<data.serialNumbers.length;j++){
              ok.serialNumbers.push({selected:false,serialNumber:data.serialNumbers[j]});
            }
          }
          this.locationProductRequestItemResponse[i].okItems=ok;
          break;
        }
      }
      this._ngxSpinner.hide();
    },error=>this._ngxSpinner.hide());
  }
  transferItems(){
    this._lpTransferRequest.locationProductRequestId=this.locProductRequestResponse.prId;
    if(this._lpTransferRequest.shippingMode==undefined||this._lpTransferRequest.shippingMode==null||this._lpTransferRequest.shippingMode=='' || this._lpTransferRequest.shippingMode=='select'){
      this.showAlerts("Please select Shippimg mode");
      return;
    }
    if(this._lpTransferRequest.shippingToName==undefined||this._lpTransferRequest.shippingToName==null||this._lpTransferRequest.shippingToName==''){
      this.showAlerts('Please provide Shipping to name');
      return;
    }
    
    if(this._lpTransferRequest.trackingNumber==undefined||this._lpTransferRequest.trackingNumber==null||this._lpTransferRequest.trackingNumber==''){
      this.showAlerts('Please provide tracking number');
      return;
    }
    if(this._lpTransferRequest.actualDeliveryDate==undefined||this._lpTransferRequest.actualDeliveryDate==null){
      this.showAlerts('Please provide delivery date');
      return;
    }

    let temp=[];
    this.locationProductRequestItemResponse.forEach(item=>{
      let tempSerialNumbers=[];
      item.okItems.serialNumbers.forEach(ser=>{
        if(ser.selected){
          tempSerialNumbers.push(ser.serialNumber);
        }
      });
      let it= new LPTransferItemRequest();
        it.lpRequestLineItemId=item.lpRequestItemId;
      if(tempSerialNumbers.length>0){
        it.transferQuantity=tempSerialNumbers.length;
        it.serialNumbers=tempSerialNumbers;
      }
      if(item.productType=='Component'){
        it.transferQuantity=item.quantityToBeIssued;
      }
      if(it.transferQuantity>0){
        temp.push(it);
      }
    });
    this._lpTransferRequest.items=temp;
    if(this._lpTransferRequest.items.length==0){
      this.showAlerts("Transfer Items not found");
      return;
    }
    this._ngxSpinner.show();
    this._productRequestService.postProductTransfer(this.locProductRequestResponse.prId,this._lpTransferRequest).subscribe(data=>{
     this._ngxSpinner.hide();
      this.onTransferDone.emit(data);
    },error=>{
      this._ngxSpinner.hide();
      this.showAlerts(`Error : ${error.error.message}`);
    });
  }
  showAlerts(message){
    this.alertstypes.type='danger';
    this.alertstypes.message=message;
    this.alertModal.show();
  }
  showMessage(message){
    this.alertstypes.type='info';
    this.alertstypes.message=message;
    this.alertModal.show();
  }
  cancelTransfer(){
    this.onCancel.emit('Cancled');
  }
  allChecked($event,partcode){
    // console.log($event.target.checked)
    // console.log(partcode);
    
    this.locationProductRequestItemResponse.forEach(item=>{
    
      item.okItems.serialNumbers.forEach(ser=>{
          if (item.partCode== partcode) {
            item.allselect=$event.target.checked;
            ser.selected = $event.target.checked;
            if(ser.selected){
              item.totalSelected=item.okItems.serialNumbers.length;
            }
            else{
              item.totalSelected=0;
            }
          }
      });
    
    });
  }
  checkSingle(item,o){
    if(o.selected){
      item.totalSelected++;
    }
    else{
      item.totalSelected--;
    }
    
  }
}
