import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, Output ,EventEmitter} from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';

@Component({
  selector: 'app-bt-edit-vendor-invoice',
  templateUrl: './bt-edit-vendor-invoice.component.html',
  styleUrls: ['./bt-edit-vendor-invoice.component.scss']
})
export class BtEditVendorInvoiceComponent implements OnInit {

  today = new Date();
  // HOLD DATA AS INPUT FROM OTHER COMPONENT //
  invoice: any = {};
  // PENDING WCC WHICH HAS NOT BEEN INVOLBED IN ANY INVOICE
  pendingWcc: Array<any> = [];
  dateCustomClasses: DatepickerDateCustomClasses[];
  // REQUET PAYLOAD
  _invoiceRequest: any = {};

  // FILE OBJECT
  _file: File;
  invoiceId : number;

  @Input()
  set vendorInvocieDetails(invoice: any) {
    this.invoice = invoice;
    this.invoiceId = invoice.invoiceId;
    if (invoice) {
      this._invoiceRequest = {
        id: invoice.invoiceId,
        wccId: invoice.wccDetailsId,
        vendorInvoiceNumber: invoice.vendorInvoiceNumber,
        vendorInvoiceDate: this.datepipe.transform(invoice.vendorInvoiceDate,'yyyy-MM-dd HH:mm:ss'),
        // this.dateConversion(invoice.vendorInvoiceDate),
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

  constructor(
    private _spiner: NgxSpinnerService,
    private _global: GlobalserviceService,
    private _btvendorinvoiceservice: BtInvoiceService,
    private datepipe:DatePipe) {
      this.dateCustomClasses = [
        { date: this.today, classes: ['bg-secondary'] }
      ];
     }

  ngOnInit() {
    this.getVendorPendingWccDetails();
  }

  getVendorPendingWccDetails() {
    this._spiner.show();
    this._btvendorinvoiceservice.getPendingInvoice().subscribe(data => {
      this._spiner.hide();
      this.pendingWcc = data;
    }, error => this._spiner.hide());
  }
  fileChangeEvent($event) {
    this._file = $event.target.files[0];
  }
  updateVendorInvoice() {

    this._invoiceRequest.vendorInvoiceDate=this.datepipe.transform(this._invoiceRequest.vendorInvoiceDate,'yyyy-MM-dd HH:mm:ss')
    console.log(this._invoiceRequest.vendorInvoiceDate);
    if (this._invoiceRequest.vendorInvoiceNumber == undefined || this._invoiceRequest.vendorInvoiceNumber == null || this._invoiceRequest.vendorInvoiceNumber == '') {
      this._global.alert({ type: 'alert', message: `Please Enter Valid Invoice Number` });
      return;
    }
   else  if (this._invoiceRequest.vendorInvoiceDate == undefined || this._invoiceRequest.vendorInvoiceDate == null || this._invoiceRequest.vendorInvoiceDate == '') {
      this._global.alert({ type: 'alert', message: `Please Enter Valid Invoice Date` });
      return;
    }
    else if (this._file == undefined || this._file == null) {
      this._global.alert({ type: 'alert', message: 'Please Provide Invoice Soft Copy' });
      return;
    }
 
    const form = new FormData();
    form.append('file', this._file);
    form.append('data',new Blob([JSON.stringify(this._invoiceRequest)],{type:'application/json'}));
    this._spiner.show();
    this._btvendorinvoiceservice.updateVendorInvoice(this.invoiceId,form).subscribe(data => {
      this._spiner.hide();
      this.onInvoiceUpdated.emit(data);    
       this.onCancel.emit('cancled');
    }, error => {
      this._spiner.hide();
      this._global.alert({ type: 'alert', message: `Error ${error.error.message}` });
    });
  }
  onCancled() {
    this.onCancel.emit({});
  }
  dateConversion(date:Date){
    let d=new Date(date);
    d.setMinutes(d.getMinutes());
    return d;
  }
  datepickss(date){
    console.log(date)
  }
}
