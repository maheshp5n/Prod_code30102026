import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtWccCustomerService } from '../../bat_services/bt-wcc-customer.service';
import { BTCaseResponse, BtCustomerWccRejectReq, BTPODRequest, BtWccCustomerApproveRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-create-wcc-customer-approve',
  templateUrl: './bt-create-wcc-customer-approve.component.html',
  styleUrls: ['./bt-create-wcc-customer-approve.component.css']
})
export class BtCreateWccCustomerApproveComponent implements OnInit {

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
  wccCustomerRequest: BtWccCustomerApproveRequest = new BtWccCustomerApproveRequest();
  caseres: BTCaseResponse = new BTCaseResponse()
  previous30Day=new Date();
  // caseid: number;
  // podDate:any;
  // expectDate:any;
  isEditMode:boolean;
  // // caseid: Array<number>;
  // POD_date:any;
  // expectedShippingdate:any;
  uploadForm: FormGroup;

  actiondata={
    action:'00',
    remarks:undefined,
    id:0
  }
  expanceApprovalId:number;
  @Input()
  set isthisEditMode(isEditMode: boolean) {
    this.isEditMode = isEditMode;
  }

  get isthisEditMode(): boolean {
    return this.isEditMode;
  }

  @Input()
  set wccCustomerReq(wccCustomerRequest: BtWccCustomerApproveRequest) {
    // console.log(caseselectlist);
    this.wccCustomerRequest.id = wccCustomerRequest.id;
    this.wccCustomerRequest.invoiceDate=wccCustomerRequest.invoiceDate;
    // this.wccCustomerRequest.customerPoDate=wccCustomerRequest.customerPoDate;
    this.wccCustomerRequest.invoiceNumber=wccCustomerRequest.invoiceNumber;
    // this.wccCustomerRequest.customerPo=wccCustomerRequest.customerPo;
  }
  get wccCustomerReq(): BtWccCustomerApproveRequest{
    return this.wccCustomerRequest;
  }

  // get caseID(): any {
  //   return this.caseid;
  // }
  // @Input()
  // set editPDData(caseres: BTCaseResponse) {
  //   this.caseres = caseres;
  //   console.log(this.podDate);
  //   if(this.caseres.poddate==null||caseres.poddate==undefined ||this.caseres.podShippingExpectedDate==undefined || this.caseres.podShippingExpectedDate==null){
  //    this.podDate=this.today;
  //    this.expectDate=this.today;
  //   }
  //   else
  // {
  //   this.expectDate=this.dateConversion(this.caseres.podShippingExpectedDate);
  //   this.podDate=this.dateConversion(this.caseres.poddate);
  // }
  // this.casePDRequest.podNumber=this.caseres.podnumber;
  // this.casePDRequest.podDate=this.caseres.poddate;
  // this.casePDRequest.podShippingExpectedDate=this.caseres.podShippingExpectedDate;
  // this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
  // this.casePDRequest.podShippingToName=this.caseres.podShippingMode;
  // this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;

  // }
  // get editPDData(): BTCaseResponse {
  //   return this.caseres;
  // }

  @Output()
  onWccCustomerCreated: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe,private loginservice:Loginservices, private btwccCustomerService:BtWccCustomerService,private tobeapproved:BtTobeApprovedService,
    private _global:GlobalserviceService,private formBuilder:FormBuilder) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  loginData:any;
  ngOnInit() {
    let today=new Date();
    today.setDate(this.today.getDate()-30);
    let day30=this.datePipe.transform(today,'yyyy-MM-dd');
    this.previous30Day=new Date(day30);
    this.loginData = this.loginservice.getLoggedInUser();

    console.log(this.wccCustomerRequest)
    this.uploadForm = this.formBuilder.group({
      profile: ['']
     
    });
  }
  postPD() {

    // this.wccCustomerRequest.customerPoDate= this.datePipe.transform(this.wccCustomerRequest.customerPoDate, 'yyyy-MM-dd');
    this.wccCustomerRequest.invoiceDate=this.datePipe.transform(this.wccCustomerRequest.invoiceDate, 'yyyy-MM-dd');
    
  //   if (this.wccCustomerRequest.customerPo === null || this.wccCustomerRequest.customerPo === undefined || this.wccCustomerRequest.customerPo.trim()=='') {
  //    this.message = "Please Fill Customer PO Number";
  //    this._global.alert({ type: 'alert', message: this.message });
  //  }
  //  else if (this.wccCustomerRequest.customerPoDate === null || this.wccCustomerRequest.customerPoDate === undefined) {
  //    this.message = "Please Fill Customer PO Date";
  //    this._global.alert({ type: 'alert', message: this.message });

  //  }
    if(this.wccCustomerRequest.invoiceNumber === null || this.wccCustomerRequest.invoiceNumber === undefined || this.wccCustomerRequest.invoiceNumber.trim()==''){
     this.message = "Please Fill Exicom Invoice Number";
     this._global.alert({ type: 'alert', message: this.message });
   }
   else if(this.wccCustomerRequest.invoiceDate === null || this.wccCustomerRequest.invoiceDate === undefined){
     this.message = "Please Fill Exicom Invoice Date"
     this._global.alert({ type: 'alert', message: this.message });
   }
   else if(this.wccCustomerRequest.sapInvoiceNumber === null || this.wccCustomerRequest.sapInvoiceNumber === undefined || this.wccCustomerRequest.sapInvoiceNumber.trim()==''){
    this.message = "Please Fill SAP Invoice Reference Number";
    this._global.alert({ type: 'alert', message: this.message });
  }
  else if(this.wccCustomerRequest.sapInvoiceNumber.length>20)
  {
    this.message = "SAP Invoice Reference Number Exceed 20 Characters";
    this._global.alert({ type: 'alert', message: this.message });
  }
   else if(this.fileList.length==0)
   {
    this.message = "Atlease One File To Upload"
    this._global.alert({ type: 'alert', message: this.message });
   }
   else {

     this.isEditMode = true;
     const fd = new FormData;
     for (let i = 0; i < this.fileList.length; i++) {
      fd.append('file', this.fileList[i]);
    }
    console.log(this.fileList);
    console.log(this.wccCustomerRequest);
    console.log(fd);
     fd.append('data',new Blob([JSON.stringify(this.wccCustomerRequest)],{type:'application/json'}));
     this.spinner.show();
     console.log(fd);
     this.btwccCustomerService.approveWccCustomer(fd).subscribe(data => {
       this.getApprovalButton(this.wccCustomerRequest.id);
       this.onWccCustomerCreated.emit("created");
       this.onCancle.emit("cancled");
       this.spinner.hide()
       this.message = 'Approved Successfully';
       this._global.alert({ type: '', message: this.message });

     }, error => {
       this.spinner.hide();
       this.message = error.error.message;
       this._global.alert({ type: 'alert', message: this.message });
       

     }
     )
   }
 }

 getApprovalButton(id) {
  this.spinner.show()
  this.tobeapproved.getItemToBeApprovdButton("BtCustomerWcc", id).subscribe(data => {
    console.log(data);
    this.spinner.hide();
    if( data.id != null && data != null && data != undefined)
    {
      this.expanceApprovalId = data.id;
      this.saveDetails(this.expanceApprovalId);

    }
  }, error=>{

  });
}


 saveDetails(id){
  // this.getApprovalButton();
  this.actiondata.id=id;
  this.actiondata.action="Approved";
  this.actiondata.remarks="Approved By "+this.loginData.emailId;  
 this.spinner.show()
  this.tobeapproved.updateAction(this.actiondata.id,this.actiondata).subscribe(data=>{
    this.spinner.hide();
  },error=>{
    this.spinner.hide();
    this._global.alert({type:'alert',message:error.error.message});
  });
  
}

 cancelWccData() {
    this.wccCustomerRequest = new BtWccCustomerApproveRequest();
    this.onCancle.emit('cancled');

  }
  popupClose() {
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.wccCustomerRequest=new BtWccCustomerApproveRequest();
    }
  }

  // dateConversion(date:Date){
  //   let d=new Date(date);
  //   d.setMinutes(d.getMinutes());
  //   return d;
  // }
  // onexpectchange($event){
  //   this.wccCustomerRequest.invoiceDate=$event;
  // }
  // onpodateDatechange($event){
  //   this.wccCustomerRequest.customerPoDate=$event;
  // }
  fileDetails: File;
  fileName:any;
  fileToUpload:File;
  fileList:string[]=[];
  onFileSelect($event) {
    if($event.target.files.length>3)
    {
      this._global.alert({ type: 'alert', message: 'You can not upload file more than three' });
      this.fileList=[];
      (<HTMLInputElement>document.getElementById('getFile')).value=null;
    }
    else{
      for (let i = 0; i < $event.target.files.length; i++) {
        this.fileList.push($event.target.files[i]);
        this.fileToUpload = $event.target.files[i];
      }
    }
 
    // this.fileDetails = $event.target.files[0];
    // this.fileName = this.fileDetails.name;
    this.uploadForm.get('profile').setValue(this.fileList);

  }

   //Validation for numric tye value
   numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 33 || charCode > 57) && charCode!=64) {
      return false;
    }
    return true;

  }
}
