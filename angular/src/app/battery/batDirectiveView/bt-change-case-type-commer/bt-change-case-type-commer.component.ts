import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { CaseResponse, ChangecaseTypeRequest, ChangeCase_caseTypeList } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCaseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-change-case-type-commer',
  templateUrl: './bt-change-case-type-commer.component.html',
  styleUrls: ['./bt-change-case-type-commer.component.css']
})
export class BtChangeCaseTypeCommerComponent implements OnInit { 
@ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;

_isEditMode:boolean;
loginData:any;
domainId:any;
message:string;
caseres:BTCaseResponse=new BTCaseResponse();
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
set caseResponse(casetyperes:BTCaseResponse){
  this.caseres=casetyperes;
  this.caseres.caseReasonName=casetyperes.caseReasonName;
  this.caseres.caseTypeName=casetyperes.caseTypeName;
  this.caseres.caseManualId=casetyperes.caseManualId;
  this.caseres.caseId=casetyperes.caseId;

  this.caseType(this.caseres.caseTypeName,this.caseres.caseReasonName);
  
}
get caseResponse():BTCaseResponse{
  return this.caseres;
}
@Output()
onChanged: EventEmitter<any> = new EventEmitter<any>();
@Output()
onCancle: EventEmitter<string> = new EventEmitter<string>();
constructor( private _btcaseservice:BtCaseserviceService,private loginservice:Loginservices,
  private spinner: NgxSpinnerService,private datePipe:DatePipe,private _global : GlobalserviceService) {
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
    this._global.alert({ type: 'alert', message: 'Please Fill New Case Type' });

  }
  else if(this.casetypereq.caseReason==null && this.casetypereq.caseReason==undefined && this.casetypereq.caseReason==''){ 
    this._global.alert({ type: 'alert', message: 'Please Fill New Case Reason' });

  }
  else{
    this.casetypereq.caseManualId=this.caseres.caseManualId;
    // this.casetypereq.caseReason=this.caseres.caseReasonName;
    // this.casetypereq.caseType=this.caseres.caseTypeName;
    // this.casetypereq.poDate=this.caseres.podate;
    // this.casetypereq.poNo=this.caseres.ponumber;
    this.spinner.show();
    this._btcaseservice.updateChnageCaseType(this.casetypereq).subscribe(data=>{
      this.spinner.hide();
      this.onChanged.emit(data);    
     this.onCancle.emit('cancled');
      this._global.alert({ type: '', message: 'Submitted Successfully !' });
    },
    error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }

}

getLoggedInUser() {
  this.loginData = this.loginservice.getLoggedInUser();
  this.domainId = this.loginData.emptype['businessDomainId']
  // this.loginData.empType['businessDomainId'];
}
caseType(precaseType,precaseReason) {
  this._btcaseservice.getCaseTypeschangeCaseType(precaseType,precaseReason).subscribe(data => {
  let temparr=[];
   data.forEach(e=>{
    temparr.push({"caseType":e})
   })
   this.caseTypeList=temparr;
  });
}

getCasereasonchange(selectedcasetype) {
  this.spinner.show();
  this._btcaseservice.getCaseTypeschangeCaseReason(this.caseres.caseTypeName,this.caseres.caseReasonName,selectedcasetype).subscribe((data) => {
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
