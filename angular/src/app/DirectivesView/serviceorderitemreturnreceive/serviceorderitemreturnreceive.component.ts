import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from './../../globalservice.service';
import { ServiceOrderitemReturnService } from './../../services/serviceorderitemreturn.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-serviceorderitemreturnreceive',
  templateUrl: './serviceorderitemreturnreceive.component.html',
  styleUrls: ['./serviceorderitemreturnreceive.component.scss']
})
export class ServiceorderitemreturnreceiveComponent implements OnInit {

  pendingdata = [];
  globalCheck = false;

  @ViewChild('pendingitemlist') pendingitemlist: ModalDirective;

  constructor(
    private _service: ServiceOrderitemReturnService,
    private _ngSpinner: NgxSpinnerService,
    private _global: GlobalserviceService) { }

  ngOnInit() {
  }

  getPendingRecord() {
    this.pendingitemlist.show();
    this._ngSpinner.show();
    this.pendingdata = [];
    this._service.getPendingToReceivve().subscribe(data => {
      this.pendingdata = data;
      this._ngSpinner.hide();
    }, error => this._ngSpinner.hide());
  }
  chengeCheckBox() {
    const ln = this.pendingdata.length;
    for (let i = 0; i < ln; i++) {
      this.pendingdata[i].selected = this.globalCheck;
    }
  }
  postData() {
    const temp = [];
    const ln = this.pendingdata.length;
    for (let i = 0; i < ln; i++) {
      if (this.pendingdata[i].selected) {
        temp.push(this.pendingdata[i].reurnItemId);
      }
    }

    if (temp.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please select items to receive' });
      return;
    }
    this._ngSpinner.show();
    this._service.postPendingToReceive(temp).subscribe(data => {
      this.getPendingRecord();
      this._ngSpinner.hide();
      this._global.alert({ type: '', message: 'Item received' });
    }, error => {
      this._ngSpinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });

  }

}
