import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { GlobalserviceService } from '../../../globalservice.service';
import { BtPartcodeStatusChangeService } from '../../bat_services/bat-partcode-status-change-service';
import { BtproductService } from '../../bat_services/btproduct.service';

@Component({
  selector: 'app-bat-partcode-status-change-dir',
  templateUrl: './bat-partcode-status-change-dir.component.html',
  styleUrls: ['./bat-partcode-status-change-dir.component.scss']
})
export class BatPartcodeStatusChangeDirComponent implements OnInit {

  produsts = [];
  changeRequest = {
    productId: 0,
    fromState: 0,
    toState: 0,
    quantity: 0,
    remarks: ''
  };

  @Output() save:EventEmitter<any> = new EventEmitter<any>();
  @Output() cancel:EventEmitter<any> =new EventEmitter<any>();

  constructor(private _ngx: NgxSpinnerService, 
    private productservice: BtproductService, 
    private _global: GlobalserviceService, 
    private _statuschangeservice: BtPartcodeStatusChangeService) { 

    }

  ngOnInit() {
    this.doNothing();
  }

  getValueOfPartCodeFrom($event) {
    this.productservice.getSmallForlist($event.term).subscribe(data => this.produsts = data);
  }

  saveDetails() {
    if (this.changeRequest.productId == undefined || this.changeRequest.productId == null || this.changeRequest.productId == 0) {
      this._global.alert({ type: 'alert', message: 'Please select product ' });
      return;
    }
    if (this.changeRequest.fromState == undefined || this.changeRequest.fromState == null || this.changeRequest.fromState == 0) {
      this._global.alert({ type: 'alert', message: 'Please select from staet' });
      return;
    }
    if (this.changeRequest.quantity == undefined || this.changeRequest.quantity == null || this.changeRequest.quantity == 0 || this.changeRequest.quantity <= 0) {
      this._global.alert({ type: 'alert', message: 'Please fill valid quantity' });
      return;
    }
    if (this.changeRequest.toState == undefined || this.changeRequest.toState == null || this.changeRequest.toState == 0) {
      this._global.alert({ type: 'alert', message: 'Please select to state' });
      return;
    }
    this._ngx.show();
    this._statuschangeservice.postRequest(this.changeRequest).subscribe(data => {
      this._ngx.hide();
      this.save.emit(data);
    }, error => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  doNothing() {
    console.log('Doing Nothing');
  }

  cancleDetails(){
    this.cancel.emit('cancled');
  }

}
