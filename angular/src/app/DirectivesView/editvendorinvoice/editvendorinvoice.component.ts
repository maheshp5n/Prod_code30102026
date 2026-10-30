
import { GlobalserviceService } from './../../globalservice.service';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { EventEmitter, Output } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-editvendorinvoice',
  templateUrl: './editvendorinvoice.component.html',
  styleUrls: ['./editvendorinvoice.component.scss']
})
export class EditvendorinvoiceComponent implements OnInit {
  today = new Date();
  // HOLD DATA AS INPUT FROM OTHER COMPONENT //
  invoice: any = {};
  // PENDING WCC WHICH HAS NOT BEEN INVOLBED IN ANY INVOICE
  pendingWcc: Array<any> = [];

  // REQUET PAYLOAD
  _invoiceRequest: any = {};

  // FILE OBJECT
  _file: File;

  @Input()
  set vendorInvocieDetails(invoice: any) {
    this.invoice = invoice;
    if (invoice) {
      this._invoiceRequest = {
        id: invoice.invoiceId,
        wccId: invoice.wccDetailsId,
        vendorInvoiceNumber: invoice.vendorInvoiceNumber,
        vendorInvoiceDate: invoice.vendorInvoiceDate,
        remarks: invoice.invoiceRemarks
      };
    }


  }
  get vendorInvoiceDetails() {
    return this.invoice;
  }

  @Output()
  onInvoiceUpdated: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  onCancel: EventEmitter<any> = new EventEmitter<any>();

  constructor(private vendorInvoiceService: VendorInvoiceService,
    private _spiner: NgxSpinnerService,
    private datePipe: DatePipe,
    private _global: GlobalserviceService) { }

  ngOnInit() {

    this.getVendorPendingWccDetails();

  }

  getVendorPendingWccDetails() {
    this._spiner.show();
    this.vendorInvoiceService.getPendingInvoice().subscribe(data => {
      this._spiner.hide();
      this.pendingWcc = data;
    }, error => this._spiner.hide());
  }
  fileChangeEvent($event) {
    this._file = $event.target.files[0];
  }
  updateVendorInvoice() {
    const form = new FormData();
    form.append('file', this._file);
    if (this._invoiceRequest.vendorInvoiceNumber == undefined || this._invoiceRequest.vendorInvoiceNumber == null || this._invoiceRequest.vendorInvoiceNumber == '') {
      this._global.alert({ type: 'alert', message: `Please enter valid Invoice Number` });
      return;
    }
    if (this._invoiceRequest.vendorInvoiceDate == undefined || this._invoiceRequest.vendorInvoiceDate == null || this._invoiceRequest.vendorInvoiceDate == '') {
      this._global.alert({ type: 'alert', message: `Please enter valid Invoice Date` });
      return;
    }
    this._invoiceRequest.vendorInvoiceDate=this.datePipe.transform(this._invoiceRequest.vendorInvoiceDate,'yyyy-MM-dd');
    this._spiner.show();
    this.vendorInvoiceService.updateVendorInvoice(this._invoiceRequest, form).subscribe(data => {
      this._spiner.hide();
      this.onInvoiceUpdated.emit({});
    }, error => {
      this._spiner.hide();
      this._global.alert({ type: 'alert', message: `Error ${error.error.message}` });
    });
  }
  onCancled() {
    this.onCancel.emit({});
  }
}
