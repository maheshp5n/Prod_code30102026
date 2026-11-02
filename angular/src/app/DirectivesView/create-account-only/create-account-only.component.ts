import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseAccountsOnlyRequest, ExpenseManagementRequest } from '../../services/model';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-account-only',
  templateUrl: './create-account-only.component.html',
  styleUrls: ['./create-account-only.component.scss']
})
export class CreateAccountOnlyComponent implements OnInit {
  @Input()
  set ExpanceManagementID(expenseManagementId:number){
    this.expenseManagementId=expenseManagementId;
  }
  get ExpanceManagementID():number{
   return this.expenseManagementId;
  }
  @Input()
  set editAccountsOnlyData(accountOnlyReq: ExpenseAccountsOnlyRequest){
    this.accountOnlyReq=accountOnlyReq
    // this.accountOnlyReq.businessPlaceId=_expenseManagementRequest.businessPlaceId;
    // this.accountOnlyReq.narration=_expenseManagementRequest.narration;
  }
  get editAccountsOnlyData():ExpenseAccountsOnlyRequest{
    return this.accountOnlyReq;
  }
  @ViewChild('popupSuccessModel') popupSuccessModel : ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };
  @Output()
  onAccountsOnlyCreated: EventEmitter<ExpenseAccountsOnlyRequest> = new EventEmitter<ExpenseAccountsOnlyRequest>();
  @Output()
  onCancleAccountsOnly: EventEmitter<string> = new EventEmitter<string>();

  openModels = { legalEntityModel: false, accountModel: false };
  // expensemanagementId:number;
  
  date: any = new Date();
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
 buisnessAreaList:Array<ExpenseManagementBusinessArea>=[];
 buisnessPlaceList:Array<ExpenseManagementBusinessPlace>=[]
 accountOnlyReq:ExpenseAccountsOnlyRequest=new ExpenseAccountsOnlyRequest();
 expenseManagementId:number;
 domainId:number
 loginData:any;
  constructor( private expensemanagementservice:ExpenseManagementService, private spinner: NgxSpinnerService, private datePipe: DatePipe,private loginservice: Loginservices) {
    this.date = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this.getLoggedInUser();
   }
  ngOnInit() {
    this.getBuisnessArea();
    this.getBuisnessPlace();
    this.accountOnlyReq.postPark=true;
    
  } 
  
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
  
  }
  getBuisnessArea(){
    this.expensemanagementservice.getBusinessAreaList(this.domainId).subscribe(data=>{
      this.buisnessAreaList=data;
    })
  }
  getBuisnessPlace(){
    this.expensemanagementservice.getBusinessPlaceList(this.domainId).subscribe(data=>{
      this.buisnessPlaceList=data;
    })
  }
  postAccountOnly(){
    
    this.accountOnlyReq.expenseManagementId=this.ExpanceManagementID;
    if(this.accountOnlyReq.businessAreaId===undefined || this.accountOnlyReq.businessAreaId===0){
      this.showAlert('Please Fill Buisness Area Name');
    }
     else if(this.accountOnlyReq.businessPlaceId===undefined || this.accountOnlyReq.businessPlaceId===0){
      this.showAlert('Please Fill Buisness Place Name');
    }
    else if(this.accountOnlyReq.documentDate===undefined || this.accountOnlyReq.documentDate===null){
      this.showAlert('Please Fill Document Date')
    }else{
      this.spinner.show();
      this.expensemanagementservice.postexpenseAccountsOnly(this.accountOnlyReq).subscribe(data=>{
        this.spinner.hide();
         this.showMessage('Updated Successfully');
         this.onAccountsOnlyCreated.emit(data);
        },error=>{
          this.spinner.hide();
          this.showAlert(error.error.message);
        })
    }
 
  }
  cancelAcountOnly(){
    this.onCancleAccountsOnly.emit('cancel')

  }
  showAlert(message) {
    this.alertmeta.ngClass = 'alert alert-danger';
    this.alertmeta.message = message;
    this.popupSuccessModel.show();
  }
  showMessage(message) {
    this.alertmeta.ngClass = 'alert alert-success';
    this.alertmeta.message = message;
    this.popupSuccessModel.show();
  }
  popupSuccessclose(){
    this.popupSuccessModel.hide();
    if(this.alertmeta.ngClass==='alert alert-success'){
        // this.accountOnlyReq=new ExpenseAccountsOnlyRequest();
      this.onCancleAccountsOnly.emit('cancel');
    }
  }
  onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
  if(key.target.value.length>44){
      // this.message="More than 5 digits can not be accepted";
      this.showAlert('More than 45 digits can not be accepted');
      this.popupSuccessModel.show();
      return false;
    }
    return true;

  }

}
