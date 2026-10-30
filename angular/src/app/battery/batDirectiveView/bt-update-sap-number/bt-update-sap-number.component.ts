import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { vendorInvoiceResponse, VendorInvSAPreq } from '../../../services/model';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';

@Component({
  selector: 'app-bt-update-sap-number',
  templateUrl: './bt-update-sap-number.component.html',
  styleUrls: ['./bt-update-sap-number.component.css']
})
export class BtUpdateSapNumberComponent implements OnInit {

  _isEditMode: boolean;
  invoiceId: number;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @Input()
  set isSapEditMode(isSapEditMode: boolean) {
    this._isEditMode = isSapEditMode;
  }

  get isSapEditMode(): boolean {
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
  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe, private vendorinvoiceservice: BtInvoiceService, private _global:GlobalserviceService,) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
  }
  updateSAP() {
    if(this._isEditMode)
    {
    if (this.sapreq.sapNumber == null || this.sapreq.sapNumber === undefined) {
      this.message = 'Please fill SAP Number';
      this._global.alert({type:'alert',message:this.message});

    }
    else if (this.sapreq.sapDate === null || this.sapreq.sapDate === undefined) {
      this.message = 'Please fill SAP Date';
      this._global.alert({type:'alert',message:this.message});

    } else {
      this.sapreq.sapDate=this.datePipe.transform(this.sapreq.sapDate, 'yyyy-MM-dd HH:mm:ss')
      this.spinner.show();
      this.sapreq.vendorInvoiceId = this.invoiceId;
      this.vendorinvoiceservice.updateInvoiceSAP(this.invoiceId, this.sapreq).subscribe(data => {
        this.onSAPUpdate.emit(data);
        this.spinner.hide();
        this.cancelSAP();


      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({type:'alert',message:this.message});

      });

    }
  }
  else{
    if (this.sapreq.sapNumber == null || this.sapreq.sapNumber === undefined) {
      this.message = 'Please fill SAP Number';
      this._global.alert({type:'alert',message:this.message});

    }
    else if (this.sapreq.sapDate === null || this.sapreq.sapDate === undefined) {
      this.message = 'Please fill SAP Date';
      this._global.alert({type:'alert',message:this.message});

    } else {
      this.sapreq.sapDate=this.datePipe.transform(this.sapreq.sapDate, 'yyyy-MM-dd HH:mm:ss')
      this.spinner.show();
      this.sapreq.vendorInvoiceId = this.invoiceId;
      this.vendorinvoiceservice.postInvoiceSAP(this.invoiceId, this.sapreq).subscribe(data => {
        this.onSAPUpdate.emit(data);
        this.cancelSAP();
        this.spinner.hide();

      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({type:'alert',message:this.message});

      });

    }

  }

  }
  cancelSAP() {

    this.onCancleSAP.emit('cancled');
  }
 
}
