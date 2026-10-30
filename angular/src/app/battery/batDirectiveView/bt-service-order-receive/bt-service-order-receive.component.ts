import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtServiceOrderService } from '../../bat_services/bt-service-order.service';
import { BtSericeOrderReceiveItemReq, BtServiceOrderRecieveItems } from '../../bt_model';

@Component({
  selector: 'app-bt-service-order-receive',
  templateUrl: './bt-service-order-receive.component.html',
  styleUrls: ['./bt-service-order-receive.component.css']
})
export class BtServiceOrderReceiveComponent implements OnInit {
  _orderid;
  @Output()
  received: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  cancled: EventEmitter<any> = new EventEmitter<any>();


  @Input()
  set orderId(orderid) {
    this._orderid = orderid;
    if (this._orderid != undefined) {
      this.getListOfPendingToReceiveSeriveOrderByID();
    }
    else {
      this.getListOfPendingToReceiveServiceOrder();
    }
  }

  public get(): any {
    return this._orderid;
  }

  global: boolean = true;

  btServiceOrderReceiveItem: Array<BtServiceOrderRecieveItems> = [];
  btSericeOrderReceiveItemReq: Array<BtSericeOrderReceiveItemReq> = [];
  constructor(private _spinner: NgxSpinnerService, private _btServiceOrderService: BtServiceOrderService,
    private _global: GlobalserviceService) { }

  ngOnInit(): void {
  }

  getListOfPendingToReceiveServiceOrder() {
    this.btServiceOrderReceiveItem = [];
    this._spinner.show();
    this._btServiceOrderService.btItemsForPendingToReceive().subscribe(data => {
      this._spinner.hide();
      this.btServiceOrderReceiveItem = data;
      this.btServiceOrderReceiveItem.forEach(d => {
        d.isChecked = true;
      });
    }, err => {
      this._spinner.hide();
    })

  }
  getListOfPendingToReceiveSeriveOrderByID() {
    this.btServiceOrderReceiveItem = [];
    this._spinner.show();
    this._btServiceOrderService.btItemsForPendingToReceiveBySeriveOrderId(this._orderid).subscribe(data => {
      this._spinner.hide();
      this.btServiceOrderReceiveItem = data;
      this.btServiceOrderReceiveItem.forEach(d => {
        d.isChecked = true;
      })
    }, err => {
      this._spinner.hide();
    })
  }
  SaveServiceOrderRecieve() {
    this.btSericeOrderReceiveItemReq = [];
    for (let i = 0; i < this.btServiceOrderReceiveItem.length; i++) {
      if (this.btServiceOrderReceiveItem[i].isChecked) {
        let temp = new BtSericeOrderReceiveItemReq();
        temp.serviceOrderItemId = this.btServiceOrderReceiveItem[i].serviceOrderItemId;
        this.btSericeOrderReceiveItemReq.push(temp);
      }
    }
    if (this.btSericeOrderReceiveItemReq.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please Select Items To Receive' });
      return;
    }

    if (this._orderid != undefined) {
      this._spinner.show();
      this._btServiceOrderService.updatebtItemsForPendingToReceiveById(this.btSericeOrderReceiveItemReq, this._orderid).subscribe(data => {
        this._spinner.hide();
        this.received.emit(data);
      }, err => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      })
    }
    else {
      this._spinner.show();
      this._btServiceOrderService.updatebtItemsForPendingToReceive(this.btSericeOrderReceiveItemReq).subscribe(data => {
        this._spinner.hide();
        this.received.emit(data);
      }, err => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      })
    }
  }
  canelServiceOrderRecieve() {
    this.cancled.emit('cancelled');

  }


  globalselectchange(check) {
    const lent = this.btServiceOrderReceiveItem.length;
    for (let i = 0; i < lent; i++) {
      this.btServiceOrderReceiveItem[i].isChecked = this.global;
      console.log(this.btServiceOrderReceiveItem[i].isChecked);
    }
  }
}
