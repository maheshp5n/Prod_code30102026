import { Component, Input, OnInit, Output,EventEmitter, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { LocationProductRequestItemResponse, LPTransferRequest} from '../../../services/model';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtProductRequestResponse, BtProductTransferItemRequest, BtProductTransferRequest, GetPrTransferItemsResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-producttransfer',
  templateUrl: './bt-producttransfer.component.html',
  styleUrls: ['./bt-producttransfer.component.css']
})
export class BtProducttransferComponent implements OnInit {

  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();
  locProductRequestResponse:BtProductRequestResponse=new BtProductRequestResponse();
  prTransferItemRes : Array<GetPrTransferItemsResponse>=[];

  locationProductRequestItemResponse:Array<LocationProductRequestItemResponse>=[];
  _lpTransferRequest:LPTransferRequest =new LPTransferRequest();
  btPTransferReq : BtProductTransferRequest = new BtProductTransferRequest();
  @Input()
  set productRequest(_productReqeust:BtProductRequestResponse){
    if(_productReqeust){
      this.locProductRequestResponse=_productReqeust;
      this.locProductRequestResponse.sourceLocationId = _productReqeust.sourceLocationId;
      console.log(this.locProductRequestResponse.sourceLocationId)
      this.getProductRequestItems(_productReqeust.productRequestId);
    }
  }
  @Output() onTransferDone:EventEmitter<BtProductTransferRequest> =new EventEmitter<BtProductTransferRequest>();
  @Output() onCancel:EventEmitter<string>=new EventEmitter<string>();
  @ViewChild('alertModal')alertModal:ModalDirective;
  constructor(private _ngxSpinner:NgxSpinnerService,private _btPRequestService : BtproductrequestService,private _btTranserService:BatproducttransferService) { 
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

 
  getProductRequestItems(productRequestId:number){
    this._ngxSpinner.show();
    this._btPRequestService.btProductTransferItems(productRequestId).subscribe(data=>{
      this._ngxSpinner.hide();
      this.prTransferItemRes = data;
    },error=>{this._ngxSpinner.hide()});
  }
  transferItems(){
    
  }

  // transferItems(){
  //   this.btPTransferReq.btProductRequestId=this.locProductRequestResponse.productRequestId;
  //   if(this.btPTransferReq.shippingMode==undefined||this.btPTransferReq.shippingMode==null||this.btPTransferReq.shippingMode=='' || this.btPTransferReq.shippingMode=='select'){
  //     this.showAlerts("Please select Shippimg mode");
  //     return;
  //   }
  //   if(this.btPTransferReq.shippingToName==undefined||this.btPTransferReq.shippingToName==null||this.btPTransferReq.shippingToName==''){
  //     this.showAlerts('Please provide Shipping to name');
  //     return;
  //   }
    
  //   if(this.btPTransferReq.trackingNumber==undefined||this.btPTransferReq.trackingNumber==null||this.btPTransferReq.trackingNumber==''){
  //     this.showAlerts('Please provide tracking number');
  //     return;
  //   }
  //   if(this.btPTransferReq.actualDeliveryDate==undefined||this.btPTransferReq.actualDeliveryDate==null){
  //     this.showAlerts('Please provide delivery date');
  //     return;
  //   }
  //  let temp = [];
  //   this.prTransferItemRes.forEach(items=>{
  //     let itemObj = new BtProductTransferItemRequest();
  //     itemObj.btTransferItemId = items.btpRequestItemId;
  //     itemObj.transferQuantity = items.quantityToBeIssued;
  //     if(itemObj.transferQuantity>0){
  //       temp.push(itemObj);
  //     }   
  //   })
  //   this.btPTransferReq.items=temp;
  //   if(this.btPTransferReq.items.length==0){
  //     this.showAlerts("Transfer Items not found");
  //     return;
  //   }
  //   this._ngxSpinner.show();
  //   this._btTranserService.getProductTransferReq(this.btPTransferReq.btProductRequestId,this.btPTransferReq).subscribe(data=>{
  //    this._ngxSpinner.hide();
  //     this.onTransferDone.emit(data);
  //   },error=>{
  //     this._ngxSpinner.hide();
  //     this.showAlerts(`Error : ${error.error.message}`);
  //   });
  // }
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
