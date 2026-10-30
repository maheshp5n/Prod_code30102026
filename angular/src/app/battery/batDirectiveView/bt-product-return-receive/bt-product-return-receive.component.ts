import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtProductReturnService } from '../../bat_services/bt-product-return.service';
import { BtProductReturnReceiveRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-product-return-receive',
  templateUrl: './bt-product-return-receive.component.html',
  styleUrls: ['./bt-product-return-receive.component.css']
})
export class BtProductReturnReceiveComponent implements OnInit {
  btReturnReceiveReq: Array<BtProductReturnReceiveRequest> = [];
  btReturnItemsList: Array<any> = [];
  returnID: string;

  constructor(private _spinner: NgxSpinnerService, private _btReturnReceiveService: BtProductReturnService, private _global: GlobalserviceService) {


  }
  @Input()
  set returnId(_returnId: string) {
    this.returnID = _returnId;
    console.log(this.returnID);
    if (this.returnID != undefined && this.returnID != null && this.returnID != '0') {
      this.getBtProductReturnReceiveItemsById(this.returnID);
    }
    else {
      this.getBtProductReturnReceivedItems();
    }
  }
  get returnId() {
    return this.returnID;
  }

  @Output()
  onProductReturnReceive: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();



  ngOnInit(): void {
  }

  getBtProductReturnReceivedItems() {
    this.btReturnItemsList = [];
    this._spinner.show();
    this._btReturnReceiveService.btPendingReturnReceiveItems().subscribe(data => {
      this._spinner.hide();
      this.btReturnItemsList = data;
      this.btReturnItemsList.forEach(d => {
        d.returnOrderItemQuantityReceived01 = (d.returnOrderItemQuantityReturned - d.returnOrderItemQuantityReceived);
        d.returnOrderItemCustomerQuantityReceived01 = (d.returnOrderItemCustomerQuantityReturned - d.returnOrderItemCustomerQuantityReceived);
      })
    }, error => {
      this._spinner.hide();
    })

  }
  getBtProductReturnReceiveItemsById(returnId) {
    this.btReturnItemsList = [];
    this._spinner.show();
    this._btReturnReceiveService.btPendingReturnRecieveItemsByReturnId(returnId).subscribe(data => {
      this.btReturnItemsList = data;
      this._spinner.hide();
      this.btReturnItemsList.forEach(d => {
        d.returnOrderItemQuantityReceived01 = (d.returnOrderItemQuantityReturned - d.returnOrderItemQuantityReceived);
        d.returnOrderItemCustomerQuantityReceived01 = (d.returnOrderItemCustomerQuantityReturned - d.returnOrderItemCustomerQuantityReceived);
      })
    }, error => {
      this._spinner.hide();
    })
  }
  saveProductReturnRecieve() {
    this.btReturnReceiveReq = [];
    this.btReturnItemsList.forEach(data => {
      let itemReq = new BtProductReturnReceiveRequest();
      if (data.returnOrderItemQuantityReceived01 == null || data.returnOrderItemQuantityReceived01 == "" || data.returnOrderItemQuantityReceived01 == undefined) {
        itemReq.exicomQuantity = 0;
      }
      else {
        itemReq.exicomQuantity = parseInt(data.returnOrderItemQuantityReceived01);
      }
      if (data.returnOrderItemCustomerQuantityReceived01 == null || data.returnOrderItemCustomerQuantityReceived01 == "" || data.returnOrderItemCustomerQuantityReceived01 == undefined) {
        itemReq.customerQuantity = 0;
      }
      else {
        itemReq.customerQuantity = data.returnOrderItemCustomerQuantityReceived01;
      }
      if (itemReq.exicomQuantity == 0 && itemReq.customerQuantity == 0) {

      }
      else {
        itemReq.returnOrderItemId = data.returnOrderItemId;
        this.btReturnReceiveReq.push(itemReq);
      }
    });
    console.log(this.btReturnReceiveReq)
    this._spinner.show();
    this._btReturnReceiveService.postBtPendingReturnReceiveItems(this.btReturnReceiveReq).subscribe(data => {
      this._spinner.hide();
      this.onProductReturnReceive.emit(data);
      this.onCancle.emit('cancelled');
    }, error => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  canelProductReturnRecieve() {
    this.onCancle.emit('Cancelled');

  }

}
