import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { caseService } from '../../services/caseService.service';
import { Loginservices } from '../../services/login.services';
import {  CaseResponse, ChangecaseTypeRequest, ChangeCase_caseTypeList } from '../../services/model';

@Component({
  selector: 'app-change-casetype-commer',
  templateUrl: './change-casetype-commer.component.html',
  styleUrls: ['./change-casetype-commer.component.css']
})
export class ChangeCasetypeCommerComponent implements OnInit {
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;

  _isEditMode:boolean;
  loginData:any;
  domainId:any;
  message:string;
  caseres:CaseResponse=new CaseResponse();
  casetypereq:ChangecaseTypeRequest=new ChangecaseTypeRequest();
  caseTypeList:any=[];
  caseReason: Array<any> = [];
  changecaseTypelist:Array<ChangeCase_caseTypeList>=[];
  today=new Date();
  date: any = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  popupNgClass :string= 'alert alert-danger center center-block';
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  @Input()
  set caseResponse(casetyperes:CaseResponse){
    this.caseres=casetyperes;
    this.caseres.caseReasonName=casetyperes.caseReasonName;
    this.caseres.caseTypeName=casetyperes.caseTypeName;
    this.caseres.caseManualId=casetyperes.caseManualId;
    this.caseType(this.caseres.caseTypeName,this.caseres.caseReasonName);
    
  }
  get caseResponse():CaseResponse{
    return this.caseres;
  }
  @Output()
  onChanged: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor( private caseservice:caseService,private loginservice:Loginservices,
    private spinner: NgxSpinnerService,private datePipe:DatePipe) {
      this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
      this.dateCustomClasses = [
        { date: this.today, classes: ['bg-secondary'] }
      ];
     }

  ngOnInit() {
    
  }
  cancelChange(){
    this.onCancle.emit('cancled');

  }
  submitcaseType(){
    if(this.casetypereq.caseType==null && this.casetypereq.caseType==undefined &&this.casetypereq.caseType=='' ){
      this.message="Please fill New Case Type";
      this.popupSuccessModel.show();
    }
    else if(this.casetypereq.caseReason==null && this.casetypereq.caseReason==undefined && this.casetypereq.caseReason==''){
      this.message="Please fill New Case Reason";
      this.popupSuccessModel.show();
    }
    else{
      this.casetypereq.caseManualId=this.caseres.caseManualId;
      this.spinner.show();
      this.caseservice.updateChnageCaseType(this.casetypereq).subscribe(data=>{
        this.spinner.hide();
        this.onChanged.emit(data);
        this.popupNgClass="alert alert-success center center-block";
        this.message="Submitted Successfully !";
        this.popupSuccessModel.show();
      },
      error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this.popupSuccessModel.show();
      })

    }
  
  }
  
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    // this.loginData.empType['businessDomainId'];
  }
  caseType(precaseType,precaseReason) {
    this.caseservice.getCaseTypeschangeCaseType(precaseType,precaseReason).subscribe(data => {
    let temparr=[];
     data.forEach(e=>{
      temparr.push({"caseType":e})
     })
     this.caseTypeList=temparr;
    });
  }
 
  getCasereasonchange(selectedcasetype) {
    this.spinner.show();
    this.caseservice.getCaseTypeschangeCaseReason(this.caseres.caseTypeName,this.caseres.caseReasonName,selectedcasetype).subscribe((data) => {
       this.spinner.hide();
    
      this.caseReason = data;
      let temparr2=[];
      data.forEach(e=>{
       temparr2.push({"casereason":e})
      })
      this.caseReason=temparr2;

    }, error => {
      this.spinner.hide();
    });
  }
  onPODatechange($event){
    this.casetypereq.poDate=$event;
  }
  
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
