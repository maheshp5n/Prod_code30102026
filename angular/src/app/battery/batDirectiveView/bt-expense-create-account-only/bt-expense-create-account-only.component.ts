import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ExpenseAccountsOnlyRequest, ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace } from '../../../services/model';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BtExpenseAccountsOnlyRequest } from '../../bt_model';

@Component({
  selector: 'app-bt-expense-create-account-only',
  templateUrl: './bt-expense-create-account-only.component.html',
  styleUrls: ['./bt-expense-create-account-only.component.css']
})
export class BtExpenseCreateAccountOnlyComponent implements OnInit {

  @Input()
  set ExpanceManagementID(expenseManagementId:number){
    this.expenseManagementId=expenseManagementId;
  }
  get ExpanceManagementID():number{
   return this.expenseManagementId;
  }
  @Input()
  set editAccountsOnlyData(accountOnlyReq: BtExpenseAccountsOnlyRequest){
    this.accountOnlyReq=accountOnlyReq
    // this.accountOnlyReq.businessPlaceId=_expenseManagementRequest.businessPlaceId;
    // this.accountOnlyReq.narration=_expenseManagementRequest.narration;
  }
  get editAccountsOnlyData():BtExpenseAccountsOnlyRequest{
    return this.accountOnlyReq;
  }
  // @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel : ModalDirective;
  // alertmeta = {
  //   ngClass: 'alert-success',
  //   message: ''
  // };
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
 accountOnlyReq:BtExpenseAccountsOnlyRequest=new BtExpenseAccountsOnlyRequest();
 expenseManagementId:number;
 domainId:number
 loginData:any;
 message:string='';
  constructor( private expensemanagementservice:BtexpensemanagementService, private spinner: NgxSpinnerService, private datePipe: DatePipe,private loginservice: Loginservices, private _global: GlobalserviceService) {
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
    // console.log(this.accountOnlyReq)
    
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
    
    this.accountOnlyReq.btExpenseManagementId=this.expenseManagementId;
    if(this.accountOnlyReq.businessAreaId===undefined || this.accountOnlyReq.businessAreaId===0){
      this.message='Please Fill Buisness Area Name';
      this._global.alert({type:'alert',message:this.message});

    }
     else if(this.accountOnlyReq.businessPlaceId===undefined || this.accountOnlyReq.businessPlaceId===0){
      this.message='Please Fill Buisness Place Name';
      this._global.alert({type:'alert',message:this.message});

    }
    else if(this.accountOnlyReq.documentDate===undefined || this.accountOnlyReq.documentDate===null){
      this.message='Please Fill Document Date';
      this._global.alert({type:'alert',message:this.message});

    }else if(this.accountOnlyReq.narration.trim()=="" || this.accountOnlyReq.narration===null){
      this.message='Please Fill Narration';
      this._global.alert({type:'alert',message:this.message});

    }else{
      this.spinner.show();
      this.expensemanagementservice.postexpenseAccountsOnly(this.accountOnlyReq).subscribe(data=>{
        this.spinner.hide();
        this.message='Updated Successfully';
         this._global.alert({type:'',message:this.message});

         this.onAccountsOnlyCreated.emit(data);
         this.onCancleAccountsOnly.emit('cancel');
        },error=>{
          this.spinner.hide();
          this.message=error.error.message;
          this._global.alert({type:'alert',message:this.message});

        })
    }
 
  }
  cancelAcountOnly(){
    this.onCancleAccountsOnly.emit('cancel')

  }
  // showAlert(message) {
  //   this.alertmeta.ngClass = 'alert alert-danger';
  //   this.alertmeta.message = message;
  //   this.popupSuccessModel.show();
  // }
  // showMessage(message) {
  //   this.alertmeta.ngClass = 'alert alert-success';
  //   this.alertmeta.message = message;
  //   this.popupSuccessModel.show();
  // }
  // popupSuccessclose(){
  //   this.popupSuccessModel.hide();
  //   if(this.alertmeta.ngClass==='alert alert-success'){
  //       // this.accountOnlyReq=new ExpenseAccountsOnlyRequest();
  //     this.onCancleAccountsOnly.emit('cancel');
  //   }
  // }
  onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
  if(key.target.value.length>44){
      // this.message="More than 5 digits can not be accepted";
      this.message='More than 45 digits can not be accepted';
      this._global.alert({type:'alert',message:this.message});
      return false;
    }
    return true;

  }

}
