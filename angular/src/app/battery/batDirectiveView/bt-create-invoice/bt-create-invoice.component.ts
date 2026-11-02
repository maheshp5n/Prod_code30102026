import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CommercialService } from '../../../services/commercial.services';
import { Loginservices } from '../../../services/login.services';
import { CaseInvoiceRequest, CaseResponse } from '../../../services/model';
import { BtCommercialService } from '../../bat_services/bt-commercial.service';

@Component({
  selector: 'app-bt-create-invoice',
  templateUrl: './bt-create-invoice.component.html',
  styleUrls: ['./bt-create-invoice.component.scss']
})
export class BtCreateInvoiceComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  message: string;
  istrue: boolean;
  noMatchCase: string;
  iscaseSelected: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempManualId: string;
  tempId: number;
  today = new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  caseInvoiceRequest: CaseInvoiceRequest = new CaseInvoiceRequest();
  caseres: CaseResponse = new CaseResponse()
  caseid: any;
  inovoiceDate: any;
  poDate: any;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set caseID(caseid: any) {
    this.caseid = caseid;
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set editInvoice(caseInvoiceRequest: CaseInvoiceRequest) {
    this.caseInvoiceRequest = caseInvoiceRequest;
    this.inovoiceDate = this.dateConversion(this.caseInvoiceRequest.invoiceDate);
    this.poDate = this.dateConversion(this.caseInvoiceRequest.poDate)
  }
  get editInvoice(): CaseInvoiceRequest {
    return this.caseInvoiceRequest;
  }

  @Output()
  onCaseInvoiceCreated: EventEmitter<CaseResponse> = new EventEmitter<CaseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor(private loginservice: Loginservices, private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService, private datePipe: DatePipe, private _btcommercialservice: BtCommercialService,
    private _global: GlobalserviceService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
  }

  cancelInvoiceData() {
    this.caseInvoiceRequest = new CaseInvoiceRequest();
    this.onCancle.emit('cancled');
  }
  postInvoice() {
    this.isEditMode = true;
    if (this.caseInvoiceRequest.poNumber !== null && this.caseInvoiceRequest.poDate === null || this.caseInvoiceRequest.poDate === undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill PO Date' });
    }
    else if (this.caseInvoiceRequest.poDate !== null && this.caseInvoiceRequest.poNumber === null || this.caseInvoiceRequest.poDate === undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill PO Number' });
    }
    else if (this.caseInvoiceRequest.invoiceDate !== null && this.caseInvoiceRequest.invoiceNumber === null || this.caseInvoiceRequest.invoiceNumber === undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Invoice Number' });
    }
    else if (this.caseInvoiceRequest.invoiceNumber !== null && this.caseInvoiceRequest.invoiceDate === null || this.caseInvoiceRequest.invoiceDate === undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Invoice Date' });
    }
    else {
      this.caseInvoiceRequest.caseId = this.caseid;
      this.caseInvoiceRequest.invoiceDate = this.datePipe.transform(this.caseInvoiceRequest.invoiceDate, 'yyyy-MM-dd');
      this.caseInvoiceRequest.poDate = this.datePipe.transform(this.caseInvoiceRequest.poDate, 'yyyy-MM-dd');
      this.spinner.show();
      this._btcommercialservice.updateCaseInvoice(this.caseInvoiceRequest).subscribe(data => {
        this.onCaseInvoiceCreated.emit(data);
        this.spinner.hide();
        this._global.alert({ type: '', message: data.invoiceNumber+' Created Successfully' });
      }, (error) => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });

      }
      )
    }


  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.caseInvoiceRequest = new CaseInvoiceRequest();
    }
  }
  dateConversion(date: Date) {
    let d = new Date(date);
    d.setMinutes(d.getMinutes());
    return d;
  }
  onInvoiceDatechange($event) {
    this.caseInvoiceRequest.invoiceDate = $event;
  }
  onPODatechange($event) {
    this.caseInvoiceRequest.poDate = $event;
  }
}
