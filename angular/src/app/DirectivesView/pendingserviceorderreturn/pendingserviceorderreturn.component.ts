import { GlobalserviceService } from './../../globalservice.service';
import { selected } from './../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { ServiceOrderitemReturnService } from './../../services/serviceorderitemreturn.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ExcelService } from '../../services/ExcelService ';

@Component({
  selector: 'app-pendingserviceorderreturn',
  templateUrl: './pendingserviceorderreturn.component.html',
  styleUrls: ['./pendingserviceorderreturn.component.scss']
})
export class PendingserviceorderreturnComponent implements OnInit {

  maincheck = false;
  returndata = [];
  returnd = {
    shipToName: '',
    shippingMode: 'ByHand',
    trackingNumber: '',
    items: []
  };
  @ViewChild('pendingToreturn', { static: false }) pendingToreturn: ModalDirective;
  @ViewChild('serviceorderdetailstable',{static:false}) serviceorderdetailstable:ElementRef;
  constructor(
    private _returnservice: ServiceOrderitemReturnService,
    private _global: GlobalserviceService,
    private _ngspinner: NgxSpinnerService,
    private _excelSerivce:ExcelService
  ) { }

  ngOnInit() {

  }
  getPendingList() {
    this._returnservice.getPendingToReturn().subscribe(data => {
      this.returndata = data;
    });
  }

  onReturnClick() {
    this.getPendingList();
    this.pendingToreturn.show();
  }
  oncheckBoxChange() {
    const ln = this.returndata.length;
    for (let i = 0; i < ln; i++) {
      this.returndata[i].selected = this.maincheck;
    }
  }
  onSaveClick() {
    const temp = [];
    const ln = this.returndata.length;
    for (let i = 0; i < ln; i++) {
      if (this.returndata[i].selected) {
        temp.push({ itemReceiveId: this.returndata[i].serviceOrderItemId, finalStatusId: this.returndata[i].jobcardProductFinalStatus })
      }
    }
    this.returnd.items = temp;

    /// VALIDATION PART

    if (this.returnd.shipToName == undefined || this.returnd.shipToName == null || this.returnd.shipToName == '') {
      this._global.alert({ type: 'alert', message: 'Please provide ship to name' });
      return;
    }
    if (this.returnd.shippingMode == undefined || this.returnd.shippingMode == null || this.returnd.shippingMode == '') {
      this._global.alert({ type: 'alert', message: 'Please provide shipping mode' });
      return;
    }
    if (this.returnd.trackingNumber == undefined || this.returnd.trackingNumber == null || this.returnd.trackingNumber == '') {
      this._global.alert({ type: 'alert', message: 'Please provide tracking number' });
      return;
    }

    if (this.returnd.items.length == 0) {
      this._global.alert({ type: 'alert', message: 'Please select item to return' });
      return;
    }
    this._ngspinner.show();
    this._returnservice.postPendingToReturn(this.returnd).subscribe(data => {
      this._ngspinner.hide();
      this.pendingToreturn.hide();
      this._global.alert({ type: '', message: 'Submitted successfully' });
    }, error => this._global.alert({ type: 'alert', message: error.error.message }));
  }

  exportToexcel(){
    this._excelSerivce.exportAsExcelFile(this.returndata,'PendingServiceOrder');
    //this._excelSerivce.exportAsExcelFileFromHTML(this.serviceorderdetailstable,'PendingserviceOrder');
  }
}
