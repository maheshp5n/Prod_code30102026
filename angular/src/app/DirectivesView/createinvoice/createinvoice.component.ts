import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatePipe } from '@angular/common';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { CommercialService } from '../../services/commercial.services';
import { CaseInvoiceRequest, CaseResponse } from '../../services/model';

@Component({
  selector: 'app-createinvoice',
  templateUrl: './createinvoice.component.html',
  styleUrls: ['./createinvoice.component.scss']
})
export class CreateinvoiceComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  _isEditMode: boolean
  message: string;
  istrue: boolean;
  noMatchCase: string;
  iscaseSelected:string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempManualId:string;
  tempId:number;
  today=new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  caseInvoiceRequest:CaseInvoiceRequest=new CaseInvoiceRequest();
  caseres: CaseResponse = new CaseResponse()
  caseid:any;
  inovoiceDate:any;
  poDate:any;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set caseID(caseid: any) {
    this.caseid=caseid;
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set editInvoice(caseInvoiceRequest: CaseInvoiceRequest){

  this.caseInvoiceRequest=caseInvoiceRequest;
  this.inovoiceDate=this.dateConversion(this.caseInvoiceRequest.invoiceDate);
  this.poDate=this.dateConversion(this.caseInvoiceRequest.poDate)
    
    // this.caseInvoiceRequest.invoiceNumber=caseres.invoiceNumber;
    // this.caseInvoiceRequest.invoiceDate=caseres.invoiceDate;
    // this.caseInvoiceRequest.poNumber=caseres.ponumber;
    // this.caseInvoiceRequest.poDate=caseres.podate;
    // this.caseInvoiceRequest.vendorInvoiceDate=caseres.vendorInvoiceDate;
    // this.caseInvoiceRequest.vendorInvoiceNumber=caseres.vendorInvoiceNumber;
  }
  get editInvoice():CaseInvoiceRequest{
    return this.caseInvoiceRequest;
  }
  
  @Output()
  onCaseInvoiceCreated: EventEmitter<CaseResponse> = new EventEmitter<CaseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor(private loginservice: Loginservices, private _activatedRoute: ActivatedRoute,private spinner: NgxSpinnerService, private datePipe: DatePipe,private commercialservice:CommercialService) 
  {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
   }

  ngOnInit() {
  }
 
  cancelInvoiceData() {
    this.caseInvoiceRequest=new CaseInvoiceRequest();
    this.onCancle.emit('cancled');
  }
  postInvoice(){
    this.isEditMode=true;
    if(this.caseInvoiceRequest.poNumber!==null&& this.caseInvoiceRequest.poDate===null|| this.caseInvoiceRequest.poDate===undefined){
     this.message="Please Fill PO Date"
     this.popupNgClass = 'alert alert-danger center center-block';
     this.popupSuccessModel.show();}
 else if(this.caseInvoiceRequest.poDate!==null && this.caseInvoiceRequest.poNumber===null || this.caseInvoiceRequest.poDate===undefined){
  this.message="Please Fill PO Number"
  this.popupNgClass = 'alert alert-danger center center-block';
  this.popupSuccessModel.show();
 }
 else if(this.caseInvoiceRequest.invoiceDate!==null && this.caseInvoiceRequest.invoiceNumber===null || this.caseInvoiceRequest.invoiceNumber===undefined){
  this.message="Please Fill Invoice Number"
 this.popupNgClass = 'alert alert-danger center center-block';
 this.popupSuccessModel.show();
 }
 else if(this.caseInvoiceRequest.invoiceNumber!==null && this.caseInvoiceRequest.invoiceDate===null || this.caseInvoiceRequest.invoiceDate===undefined){
  this.message="Please Fill Invoice Date"
  this.popupNgClass = 'alert alert-danger center center-block';
  this.popupSuccessModel.show();
 }
//  else if((this.caseInvoiceRequest.poNumber==null || this.caseInvoiceRequest.poDate==null || 
//   this.caseInvoiceRequest.invoiceNumber==null ||this.caseInvoiceRequest.invoiceDate==null)&&this.caseInvoiceRequest.vendorInvoiceDate!==null && this.caseInvoiceRequest.vendorInvoiceNumber===null || this.caseInvoiceRequest.vendorInvoiceNumber===undefined){
//   this.message="Please Fill Vendor Invoice Number "
//   this.popupNgClass = 'alert alert-danger center center-block';
//   this.popupSuccessModel.show();
//  }
//  else if( this.caseInvoiceRequest.vendorInvoiceNumber!==null && this.caseInvoiceRequest.vendorInvoiceDate===null || this.caseInvoiceRequest.vendorInvoiceDate===undefined){
//   this.message="Please Fill Vendor Date"
//   this.popupNgClass = 'alert alert-danger center center-block';
//   this.popupSuccessModel.show();
//  }
    else{
      this.caseInvoiceRequest.caseId=this.caseid;
      this.caseInvoiceRequest.invoiceDate=this.datePipe.transform(this.caseInvoiceRequest.invoiceDate, 'yyyy-MM-dd');
      this.caseInvoiceRequest.poDate=this.datePipe.transform(this.caseInvoiceRequest.poDate, 'yyyy-MM-dd');
      this.spinner.show();
      this.commercialservice.updateCaseInvoice(this.caseInvoiceRequest).subscribe(data=>{
        this.onCaseInvoiceCreated.emit(data);
        this.spinner.hide()
        this.message = 'Created Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.popupSuccessModel.show();
      },(error) => {
        this.spinner.hide();
        this.message = error.error.message;
        this.popupNgClass='alert alert-danger center center-block';
        this.popupSuccessModel.show();
  
      }
      )
    }
    

  }
 
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.caseInvoiceRequest=new CaseInvoiceRequest();
    }
  }
  dateConversion(date:Date){
    let d=new Date(date);
    d.setMinutes(d.getMinutes());
    return d;
  }
  onInvoiceDatechange($event){
    this.caseInvoiceRequest.invoiceDate=$event;
  }
  onPODatechange($event){
    this.caseInvoiceRequest.poDate=$event;
  }
}
