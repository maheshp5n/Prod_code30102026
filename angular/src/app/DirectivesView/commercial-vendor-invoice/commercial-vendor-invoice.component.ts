import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { VendorCommRequest, vendorInvoiceResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-commercial-vendor-invoice',
  templateUrl: './commercial-vendor-invoice.component.html',
  styleUrls: ['./commercial-vendor-invoice.component.scss']
})
export class CommercialVendorInvoiceComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  comreq: VendorCommRequest = new VendorCommRequest();
  id: number;
  venInvoiceId: number;
  today = new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  popupNgClass: string = 'alert alert-danger center center-block';
  message: string;
  internalServiceCodes = [];
  public _isEditMode: boolean;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  @Input()
  set editGRNData(comreq: VendorCommRequest) {
    this.comreq = comreq;
  }

  get editGRNData(): VendorCommRequest {
    return this.comreq;
  }
  @Input()
  set venInvoiceID(venInvoiceId: number) {
    this.venInvoiceId = venInvoiceId;
  }
  get venInvoiceID(): number {
    console.log(this.venInvoiceId)
    return this.venInvoiceId;
  }
  @Output()
  onCancel: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onGRNupdate: EventEmitter<vendorInvoiceResponse> = new EventEmitter<vendorInvoiceResponse>();

  constructor(private spinner: NgxSpinnerService, private vendrservice: VendorInvoiceService, private _global: GlobalserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getInternalServiceCode();
    this.comreq.internalServiceCode = 0;
  }
  cancelCommercial() {
    this.onCancel.emit('cancled');
  }
  postGRN() {
    if (this.comreq.entrySheetNumber == undefined || this.comreq.entrySheetNumber == null || this.comreq.entrySheetNumber == '') {
      this._global.alert({ type: 'alert', message: 'Plase provide Service Entry Number' });
      return;
    }
    if (this.comreq.grnNumber == undefined || this.comreq.grnNumber == null || this.comreq.grnNumber == '') {
      this._global.alert({ type: 'alert', message: 'Please provide GRN Number' });
      return;
    }
    if (this.comreq.internalServiceCode == undefined || this.comreq.internalServiceCode == null || this.comreq.internalServiceCode == 0) {
      this._global.alert({ type: 'alert', message: 'Please select internal service code' });
      return;
    }
    this.spinner.show();
    this.comreq.vendorInvoiceId = this.venInvoiceId;
    this.vendrservice.postVendorInvGRN(this.venInvoiceId, this.comreq).subscribe(data => {
      this.onGRNupdate.emit(data);
      this.spinner.hide();
      //   this.popupSuccessModel.show();

    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancel.emit('cancled');

    }
  }

  getInternalServiceCode() {
    this.vendrservice.getInternalServiceCode().subscribe(data => this.internalServiceCodes = data);
  }

}
