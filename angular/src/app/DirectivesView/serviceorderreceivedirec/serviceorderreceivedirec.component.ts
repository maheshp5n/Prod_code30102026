import { NgxSpinnerService } from 'ngx-spinner';
import { selected } from './../../services/model';
import { GlobalserviceService } from './../../globalservice.service';
import { ServiceOrderService } from './../../services/serviceorder.servvices';
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-serviceorderreceivedirec',
  templateUrl: './serviceorderreceivedirec.component.html',
  styleUrls: ['./serviceorderreceivedirec.component.scss']
})
export class ServiceorderreceivedirecComponent implements OnInit {

  _orderiD;


  pendingItems: Array<any> = [];
  globalcheckbox: boolean = false;
  @Output()
  received: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  cancled: EventEmitter<any> = new EventEmitter<any>();


  @Input()
  set orderId(orderid) {
    if (orderid) {
      this._orderiD = orderid;
      this.getPendintItemByOrderId(this._orderiD);
    } else {
      this.getAllNoreceived();
    }
  }

  public get(): any {
    return this._orderiD;
  }


  constructor(private _serviceOrderService: ServiceOrderService,
    private _global: GlobalserviceService,
    private _ngx: NgxSpinnerService
  ) { }

  ngOnInit() {

  }

  getAllNoreceived() {
    this._serviceOrderService.getPendingToReceiveAll().subscribe(data => this.pendingItems = data);
  }
  getPendintItemByOrderId(id) {
    this._serviceOrderService.getPendintToReceiveByOrderId(id).subscribe(data => this.pendingItems = data);
  }
  gloablCheckBoxEvent() {
    const counter = this.pendingItems.length;
    for (let i = 0; i < counter; i++) {
      this.pendingItems[i].selected = this.globalcheckbox;
    }
  }

  receiveItem() {
    const temp = [];
    const counter = this.pendingItems.length;
    for (let i = 0; i < counter; i++) {
      if (this.pendingItems[i].selected) {
        temp.push(this.pendingItems[i].itemId);
      }
    }

    // VALIDATION
    if (temp.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please select items to receive' });
      return;
    }
    this._ngx.show();
    this._serviceOrderService.receiveItems(temp).subscribe(data => {
      this._ngx.hide();
      this.received.emit();
    }, error => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });


  }
  cadoCancelncled() {
    this.cancled.emit();
  }
}
