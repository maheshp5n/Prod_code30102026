import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { DatePipe } from '@angular/common';
import { VendorInvSAPreq, vendorInvoiceResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-update-sap-number',
  templateUrl: './update-sap-number.component.html',
  styleUrls: ['./update-sap-number.component.scss']
})
export class UpdateSapNumberComponent implements OnInit {
  _isEditMode: boolean;
  invoiceId: number;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  @Input()
  set venInvoiceID(venInvoiceId: number) {
    this.invoiceId = venInvoiceId;
  }
  get venInvoiceID(): number {
    return this.invoiceId;
  }

  @Output()
  onSAPUpdate: EventEmitter<vendorInvoiceResponse> = new EventEmitter<vendorInvoiceResponse>();
  @Output()
  onCancleSAP: EventEmitter<string> = new EventEmitter<string>();
  today = new Date();
  date: any = new Date();
  sapreq: VendorInvSAPreq = new VendorInvSAPreq();
  dateCustomClasses: DatepickerDateCustomClasses[];
  popupNgClass: string = 'alert alert-danger center center-block';
  message: string;
  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe, private vendorinvoiceservice: VendorInvoiceService) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
  }
  updateSAP() {
    if (this.sapreq.sapNumber == null || this.sapreq.sapNumber === undefined) {
      this.message = 'Please fill SAP Number';
      this.popupSuccessModel.show();
    }
    else if (this.sapreq.sapDate === null || this.sapreq.sapDate === undefined) {
      this.message = 'Please fill SAP Date';
    } else {
      this.spinner.show();
      this.sapreq.vendorInvoiceId = this.invoiceId;
      this.vendorinvoiceservice.updateInvoiceSAP(this.invoiceId, this.sapreq).subscribe(data => {
        console.log(data);
        this.onSAPUpdate.emit(data);
        this.spinner.hide();

      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this.popupSuccessModel.show();
      });

    }

  }
  cancelSAP() {

    this.onCancleSAP.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancleSAP.emit('cancled');

    }
  }
}
