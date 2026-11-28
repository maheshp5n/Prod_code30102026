import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { type } from 'os';
import { GlobalserviceService } from '../../../globalservice.service';
import { PODetails, POType, VendorInvoiceRequest, vendorInvoiceResponse, VendorPOcategory, WccPendingInvoice } from '../../../services/model';
import { VendorInvoiceService } from '../../../services/vendorinvoice.services';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';

@Component({
  selector: 'app-bt-new-invoice',
  templateUrl: './bt-new-invoice.component.html',
  styleUrls: ['./bt-new-invoice.component.scss']
})
export class BtNewInvoiceComponent implements OnInit {
 // uploadForm:FormGroup;
 today = new Date();
 date: any = new Date();
 dateCustomClasses: DatepickerDateCustomClasses[];
 venReq: VendorInvoiceRequest = new VendorInvoiceRequest();
 isVisible: boolean = false;
 _isEditMode: boolean;
 istrue: boolean;
 message: string;
 VPOtype: Array<POType> = [];
 VPOcategory: Array<VendorPOcategory> = [];
 popupNgClass: string = 'alert alert-danger center center-block';
 sortColumnName: string = 'createdDate'; // first column variable
 sortOrder: string = 'desc';
 sortColumn: number = 7;
 columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false];
 columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, true];
 ASCENDING: string = 'asc';
 DESCENDING: string = 'desc';
 @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
 @Input()
 set isEditMode(isEditMode: boolean) {
   this._isEditMode = isEditMode;
 }

 get isEditMode(): boolean {
   return this._isEditMode;
 }

 @Input()
 set editwccId(wccid: number) {
   this.spinner.show();
   this._btvendorinvoiceservice.getPendingInvoice().subscribe(data => {
     console.log("test"+data)
     this.spinner.hide();
     this.wccpendingList = data;
     data.forEach(e => {
       if (wccid == e.wccId) {
         this.venReq.wccId = wccid;
         this.spinner.show();
       }
     })
   }, error => {
     this.spinner.hide();
     this.message = error.error.message;
     this.popupSuccessModel.show();
   });

 }
 get editwccId(): number {
   return this.venReq.wccId;
 }

 @Output()
 onInvoiceCreated: EventEmitter<vendorInvoiceResponse> = new EventEmitter<vendorInvoiceResponse>();
 @Output()
 onCancle: EventEmitter<string> = new EventEmitter<string>();


 constructor(private http: HttpClient, private formBuilder: UntypedFormBuilder,
   private spinner: NgxSpinnerService,
   private datePipe: DatePipe,
   private _global: GlobalserviceService,
   private _btvendorinvoiceservice: BtInvoiceService) {
   this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
   this.dateCustomClasses = [
     { date: this.today, classes: ['bg-secondary'] }
   ];
 }
 uploadForm2: UntypedFormGroup;


 ngOnInit() {
   this.getPOType();
   this.getPendingInvoice();
   this.getPoCat();
   this.uploadForm2 = this.formBuilder.group({
     profile: ['']
   });
 }
 getPOType() {
   this.spinner.show();
   this._btvendorinvoiceservice.getPOTypes().subscribe(data => {
     this.spinner.hide();
     this.VPOtype = data;
   }, error => {
     this.spinner.hide();
     this._global.alert({ type: 'alert', message:error.error.message});
   })
 }
 getPoCat() {
   this.spinner.show();
   this._btvendorinvoiceservice.getPOCategory().subscribe(data => {
     this.spinner.hide();
     this.VPOcategory = data;
   }, error => {
     this.spinner.hide();
     this._global.alert({ type: 'alert', message:error.error.message});
   })
 }
 venInvoicePoNumber: string;
 venInvoicePOdate: string;
 id: number = 1;
 podetails: Array<PODetails> = [];
 getPObyID($event: WccPendingInvoice) {
   console.log($event.wccTypeId)
   this.spinner.show();
   this._btvendorinvoiceservice.getPOByTypeId($event.wccTypeId).subscribe(data => {
     this.spinner.hide();
     this.podetails = data;
     this.podetails.forEach(e => {
       this.venInvoicePOdate = e.poDate;
       this.venInvoicePoNumber = e.poNumber;
       this.venReq.vendorPoId = e.id;
       e.poNumber;
     })
   }, error => {
     this.spinner.hide();
     this.message = error.error.message;
     this.popupSuccessModel.show();
   })
 }
 wccpendingList: Array<WccPendingInvoice> = [];
 getPendingInvoice() {
   this.spinner.show();
   this._btvendorinvoiceservice.getPendingInvoice().subscribe(data => {
     this.spinner.hide();
     this.wccpendingList = data;

   }, error => {
     this.spinner.hide();
     this._global.alert({ type: 'alert', message:error.error.message});
   })
 }


 tempID: number;
 tempManualID: string;

 postInvoice() {
   if (this.venReq.vendorInvoiceNumber == null || this.venReq.vendorInvoiceNumber == '') {
        this._global.alert({ type: 'alert', message:'Please Fill Vendor Invoice Number'});
   }
   else if (this.venReq.vendorInvoiceDate == null || this.venReq.vendorInvoiceDate == undefined) {
     
     this._global.alert({ type: 'alert', message:'Please Fill Vendor Invoice Date'});
   }

   else if (this.venReq.wccId == null || this.venReq.wccId == undefined) {
     this._global.alert({ type: 'alert', message:'Please Fill Wcc Number'});
   }


   else {
     this.istrue = true;
     if (this._isEditMode != true) {
       this.spinner.show();
       this._btvendorinvoiceservice.saveInvoice(this.venReq).subscribe(data => {
         this.onInvoiceCreated.emit(data);

       }, error => {
         this.spinner.hide();       
         this._global.alert({ type: 'alert', message:error.error.message});
         setTimeout(() => {
           this.popupSuccessModel.hide();
         }, 2000
         );
       }

       );
     } else {
     }
   }


 }




 cancelData() {
   if (this.isEditMode != true) {
     // this.res = new cityDetailsResponse();
   }
   this.onCancle.emit('cancled');
 }
 popupClose() {
   this.popupSuccessModel.hide();
   if (this.popupNgClass === 'alert alert-success center center-block') {
     this.onCancle.emit('cancled');

   }
 }
 file: File;
 filname: string;
 onFileSelect($event) {
   this.file = $event.target.files[0];
   this.filname = this.file.name;
   this.uploadForm2.get('profile').setValue(this.file);

 }
 data: any
 onSubmit() {
   if (this.venReq.vendorInvoiceNumber == undefined || this.venReq.vendorInvoiceNumber == null || this.venReq.vendorInvoiceNumber == '') {
     this._global.alert({ type: 'alert', message: 'Please Provide Invoice Number' });
     return;
   }
   else if (this.venReq.vendorInvoiceDate == undefined || this.venReq.vendorInvoiceDate == null || this.venReq.vendorInvoiceDate == '') {
     this._global.alert({ type: 'alert', message: 'Please Provide Invoice Date' });
     return;
   }
   else if (this.file == undefined || this.file == null) {
     this._global.alert({ type: 'alert', message: 'Please Provide Invoice Soft Copy' });
     return;
   }
   else if (this.venReq.wccId == 0|| this.venReq.wccId == null|| this.venReq.wccId == undefined) {
    this._global.alert({ type: 'alert', message: 'Please Provide WCC Number' });
    return;
  }
   const fd = new FormData;  
   fd.append('file', this.file,this.filname);
   fd.append('data',new Blob([JSON.stringify(this.venReq)],{type:'application/json'}));
   this.spinner.show();
   this._btvendorinvoiceservice.vcuploadFiless(fd).subscribe(data => {
     this.onInvoiceCreated.emit(data);
     this.onCancle.emit('cancled');
     this.spinner.hide();
   }, error => {
     this.spinner.hide();
     this._global.alert({ type: 'alert', message: `Error ${error.error.message}` });
   })
 }

}
