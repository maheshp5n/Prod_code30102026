import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { LpReturnOrderResponse, LpReturnOrderItemResponse, LpReturnOrderReceiveRequest, LpReturnOrderReceiveItem } from '../../services/model';
import { ProductReturnService } from '../../services/productreturnservice';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { throttle } from 'rxjs/operators';

@Component({
  selector: 'app-productreturnreceivedirective',
  templateUrl: './productreturnreceivedirective.component.html',
  styleUrls: ['./productreturnreceivedirective.component.scss']
})
export class ProductreturnreceivedirectiveComponent implements OnInit {
  @ViewChild('alertModal') alertModal: ModalDirective;
  returnOrderResponse: LpReturnOrderResponse = new LpReturnOrderResponse();
  returnOrderItem: Array<LpReturnOrderItemResponse> = [];

  @Input()
  set returnOrder(ret: LpReturnOrderResponse) {
    if (ret) {
      this.returnOrderResponse = ret;
      this.getReturnOrderItems(ret.returnOrderId);
    }
  }
  get returnOrder() {
    return this.returnOrderResponse;
  }

  @Output() onReceive: EventEmitter<string> = new EventEmitter<string>();
  @Output() onCancel: EventEmitter<string> = new EventEmitter<string>();
  alertDetails = {
    type: 'danger',
    message: ''
  };
  constructor(private productReturnService: ProductReturnService, private _ngxspinner: NgxSpinnerService) { }

  ngOnInit() {
  }
  getReturnOrderItems(returnOrderId) {
    this._ngxspinner.show();
    this.productReturnService.getPenditItemsToReceive(returnOrderId).subscribe(data => {
      this._ngxspinner.hide();
      this.returnOrderItem = data;
      console.log(this.returnOrderItem);
      this.returnOrderItem.forEach(e=>{
        console.log(e.lineItems.length)
      })
    }, error => { this._ngxspinner.hide(); });
  }
  receiveData() {
    let items: LpReturnOrderReceiveRequest = new LpReturnOrderReceiveRequest();
    items.items = [];
    throttle
    this.returnOrderItem.forEach(item => {
      let serialNumbers = [];
      console.log(item.lineItems.length);
      item.lineItems.forEach(lineItem => {
        if (lineItem.acutalReceive) {
          serialNumbers.push(lineItem.serialNumber);
        }
      });
      if (serialNumbers.length > 0) {
        let _item = new LpReturnOrderReceiveItem();
        _item.lpReturnOrderItemId = item.returnOrderItemId;
        _item.productPartCode = item.partCode;
        _item.serialNumber = serialNumbers;
        items.items.push(_item);
      }
    });
    if (items.items.length > 0) {
      items.lpReturnOrderId = this.returnOrderResponse.returnOrderId;
      items.quantityReceived = items.items.length;
      this._ngxspinner.show();
      this.productReturnService.receiveItems(this.returnOrderResponse.returnOrderId, items).subscribe(data => {
        this._ngxspinner.hide();
        this.onReceive.emit('updated');
      }, error => {
        this._ngxspinner.hide();
        this.showAlert(`Error : ${error.error.message}`);
      });
    }
    else {
      this.showAlert('No item selected to receive');
    }

  }
  cancelDataReceive() {
    this.onCancel.emit('Cancel');
  }
  showAlert(message) {
    this.alertDetails.type = 'danger';
    this.alertDetails.message = message;
    this.alertModal.show();
  }
  mainChecked(item,$event) {
    if($event.target.checked==true){
      this.checkedLen=item.lineItems.length;
    }
    else{
      this.checkedLen=0;
    }
    const d = item.lineItems.length;
    for (let i = 0; i < d; i++) {
      item.lineItems[i].acutalReceive = item.gcheck;

    }
  }
  checkedLen:number=0;
  singleChecked($event){
    if($event.target.checked==true){
    this.checkedLen=this.checkedLen+1
    }
    else
  {  
    this.checkedLen=this.checkedLen-1;
  }
   
  }
}
