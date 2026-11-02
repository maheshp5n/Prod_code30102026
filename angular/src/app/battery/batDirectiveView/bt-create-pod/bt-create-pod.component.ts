import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CaseProofOfDeliveryRequest } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtWccCustomerService } from '../../bat_services/bt-wcc-customer.service';
import { BTCaseProofOfDeliveryRequest, BTCaseResponse, BtCustomerWccResp, BTPODRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-create-pod',
  templateUrl: './bt-create-pod.component.html',
  styleUrls: ['./bt-create-pod.component.scss']
})
export class BtCreatePodComponent implements OnInit {

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
  casePDRequest: BTPODRequest = new BTPODRequest();
  caseres: BTCaseResponse = new BTCaseResponse();
  previous90days:Date=new Date();
  // caseid: number;
  podDate:any;
  expectDate:any;
  isEditMode:boolean;
  caseid: Array<number>;
  POD_date:any;
  expectedShippingdate:any;
  uploadForm: UntypedFormGroup;
  isBrcRepairCase:boolean=false;
  formData = new FormData();

  brcCaseMandatory="col-md-6 col-sm-6 form-group"

  @Input()
  set isBrcRepair(isBrcRepair:boolean){
    this.isBrcRepairCase=isBrcRepair;

    if(this.isBrcRepairCase)
    {
      this.brcCaseMandatory="col-md-6 col-sm-6 form-group required";
    }
    else{
      this.brcCaseMandatory="col-md-6 col-sm-6 form-group";

    }
  }
  get isBrcRepair():boolean{
    return this.isBrcRepairCase;
  }
  @Input()
  set isthisEditMode(isEditMode: boolean) {
    this.isEditMode = isEditMode;
  }

  get isthisEditMode(): boolean {
    return this.isEditMode;
  }

  @Input()
  set caseID(caseselectlist: any) {
    this.caseid = caseselectlist;
    this.casePDRequest=new BTPODRequest();
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set editPDData(caseres: BTCaseResponse) {
    this.caseres = caseres;
    console.log(this.podDate);
    if(this.caseres.poddate==null||caseres.poddate==undefined ||this.caseres.podShippingExpectedDate==undefined || this.caseres.podShippingExpectedDate==null){
     this.podDate=this.today;
     this.expectDate=this.today;
    }
    else
  {
    this.expectDate=this.dateConversion(this.caseres.podShippingExpectedDate);
    this.podDate=this.dateConversion(this.caseres.poddate);
  }
  this.casePDRequest.podNumber=this.caseres.podnumber;
  this.casePDRequest.podDate=this.caseres.poddate;
  this.casePDRequest.podShippingExpectedDate=this.caseres.podShippingExpectedDate;
  this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
  this.casePDRequest.podShippingToName=this.caseres.podShippingMode;
  this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;
  this.casePDRequest.jmsNo = this.caseres.jmsNo;
  this.casePDRequest.customer = this.caseres.customer;
 

  }
  get editPDData(): BTCaseResponse {
    return this.caseres;
  }

  @Output()
  onCasePDCreated: EventEmitter<BtCustomerWccResp> = new EventEmitter<BtCustomerWccResp>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  onBack:EventEmitter<string> = new EventEmitter<string>();
  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe, private wccservice: BtWccCustomerService,
    private _global:GlobalserviceService,private formBuilder:UntypedFormBuilder) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    console.log(this.isBrcRepairCase);
    let today=new Date();
    today.setDate(this.today.getDate()-90);
    let date90=this.datePipe.transform(today,'yyyy-MM-dd');
    this.previous90days=new Date(date90);

    this.uploadForm = this.formBuilder.group({
      profile: ['']
     
    });
  }
  postPD() {
    console.log(this.casePDRequest.podDate);
      //  this.expectDate=this.dateConversion(this.expectedShippingdate);
    // this.podDate=this.dateConversion( this.POD_date);
    // this.casePDRequest.podNumber=this.caseres.podnumber;
    this.casePDRequest.customerPoDate= this.datePipe.transform(this.casePDRequest.customerPoDate, 'yyyy-MM-dd HH:mm:ss');
    this.casePDRequest.podDate= this.datePipe.transform(this.casePDRequest.podDate, 'yyyy-MM-dd HH:mm:ss');
    this.casePDRequest.podShippingExpectedDate=this.datePipe.transform(this.casePDRequest.podShippingExpectedDate, 'yyyy-MM-dd HH:mm:ss');
    // this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
    // this.casePDRequest.podShippingToName=this.caseres.podShippingMode;
    // this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;
    
    console.log(this.casePDRequest);
    if ( this.casePDRequest.podNumber=="" || this.casePDRequest.podNumber === null || this.casePDRequest.podNumber === undefined && this.isBrcRepair) {
     this.message = "Please Fill POD Number"
     this._global.alert({ type: 'alert', message: this.message });

   }
   else if ((this.casePDRequest.podDate === null || this.casePDRequest.podDate === undefined) && this.isBrcRepair) {
     this.message = "Please Fill POD Date"
      this._global.alert({ type: 'alert', message: this.message });

   }
   else if (this.casePDRequest.customerPoNumber =="" || this.casePDRequest.customerPoNumber === null || this.casePDRequest.customerPoNumber === undefined) {
    this.message = "Please Fill Customer PO Number"
     this._global.alert({ type: 'alert', message: this.message });

  }
  else if (this.casePDRequest.customerPoDate === null || this.casePDRequest.customerPoDate === undefined) {
    this.message = "Please Fill Cutomer PO Date"
     this._global.alert({ type: 'alert', message: this.message });

  }
   else if (this.casePDRequest.grnNumber=="" || this.casePDRequest.grnNumber === null || this.casePDRequest.grnNumber === undefined && this.isBrcRepair) {
    this.message = "Please Fill GRN Number"
     this._global.alert({ type: 'alert', message: this.message });

  }
  else if (this.casePDRequest.grnDate === null || this.casePDRequest.grnDate === undefined && this.isBrcRepair) {
    this.message = "Please Fill GRN Date"
     this._global.alert({ type: 'alert', message: this.message });

  }
  else if (this.fileToUpload == null || this.fileToUpload==undefined) {
    this.message = "Please Select Any File"
    this._global.alert({ type: 'alert', message: this.message });

  }
   
   else {
     this.isEditMode = true;
     this.casePDRequest.caseId = this.caseid;
     const fd = new FormData;
     for (let i = 0; i < this.fileList.length; i++) {
      fd.append('file', this.fileList[i]);
    }
    //  fd.append('file',this.fileList,this.fileName);
     fd.append('data',new Blob([JSON.stringify(this.casePDRequest)],{type:'application/json'}));
     this.spinner.show();
    //  console.log(this.casePDRequest);

    //  console.log(fd);
     this.wccservice.updatePODwithFile(fd).subscribe(data => {
 

       this.spinner.hide()
       this.message = data.manualId+' Created Successfully';
       this._global.alert({ type: '', message: this.message });
       this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null;
       this.onCasePDCreated.emit(data);
       this.onCancle.emit('cancled');
     }, error => {
       this.spinner.hide();
       this.message = error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
       this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null;
       // this.popupNgClass = 'alert alert-danger center center-block';
       

     }
     )
     
   }
 }
  cancelPDData() {
    this.casePDRequest = new BTPODRequest();
    this.fileList = []; 
    this.fileToUpload=null;
    (<HTMLInputElement>document.getElementById('getFile')).value=null;
    this.formData = new FormData();

    this.onCancle.emit('cancled');

    }
  popupClose() {
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.casePDRequest=new BTPODRequest();
    }
  }

  dateConversion(date:Date){
    let d=new Date(date);
    d.setMinutes(d.getMinutes());
    return d;
  }
  onexpectchange($event){
    this.casePDRequest.podShippingExpectedDate=$event;
  }
  onpodateDatechange($event){
    this.casePDRequest.podDate=$event;
  }
  fileDetails: File;
  fileName:any;
  fileToUpload:File;
  fileList:string[]=[];
  onFileSelect($event) {
    for (let i = 0; i < $event.target.files.length; i++) {
      this.fileList.push($event.target.files[i]);
      this.fileToUpload = $event.target.files[i];
    }
    // this.fileDetails = $event.target.files[0];
    // this.fileName = this.fileDetails.name;
    this.uploadForm.get('profile').setValue(this.fileDetails);

  }
  backclik(ids){
    console.log(ids)
    this.caseid=ids;
    this.onCancle.emit('cancled');
  }
   //Validation for numric tye value
   numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

  letterOnly(event) 
{
  var charCode = event.keyCode;

   if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || charCode == 8)

     return true;
     else
     return false;
}
allowAlphaNumric(event){
  var charCode = event.keyCode;
  console.log(charCode);
  
  if((charCode > 46 && charCode < 91) || (charCode>96 && charCode<123) )
  {
    return true;
  }
  else{
    return false;
  }

}
}
