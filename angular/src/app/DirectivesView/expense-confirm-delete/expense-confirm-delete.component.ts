import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { ExpenseManagementResponse } from '../../services/model';

@Component({
  selector: 'app-expense-confirm-delete',
  templateUrl: './expense-confirm-delete.component.html',
  styleUrls: ['./expense-confirm-delete.component.css']
})
export class ExpenseConfirmDeleteComponent implements OnInit {
  popupNgClass: string = 'alert alert-danger center center-block';
  message:string;
  caseid:number;
  expenseId:any;
  private url = '';
  private baseUrllodging = 'api/tourexpense';
  private baseUrllocalCon = 'api/tourexpense';
  private baseUrlOther = 'api/tourexpense';
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };
  quoteId:number;
  approvalmessage:string;
  constructor(private spinner: NgxSpinnerService,private expenseservice:ExpenseManagementService) { }
  @Output()
  // onDOcumnetSuccess: EventEmitter<CaseResponse> = new EventEmitter<CaseResponse>();
 onSubmitted:EventEmitter<ExpenseManagementResponse>=new EventEmitter<ExpenseManagementResponse>();
 tourId:any;
  @Input()
  set caseID(caseid: any) {
    this.caseid=caseid;
    console.log(this.caseid);
  }

  get caseID(): any {
    return this.caseid;
  }
  @Input()
  set expensesmanagementID(expensesmanagementid:number){
  this.expenseId=expensesmanagementid;
  }
  
  get expensesmanagementID():number{
    return this.expenseId;
  }
  @Input()
  set ApprovalMessage(approvalmessage:string){
    this.approvalmessage=approvalmessage;
  }
  get ApprovalMessage():string{
    return this.approvalmessage;
  }
  @Input()
  set tourID(tourId:any){
    this.expenseId=tourId;
  }
  get tourID():any{
    return this.expenseId;
  }
  @Input()
  set URL(baseUrl:any){
    this.url=baseUrl;
  }
  get URL():any{
    return this.url;
  }
  
  @Output()
  noRequestFor: EventEmitter<string> = new EventEmitter<string>();
  ngOnInit() {
  }
  noRequestForSure() {
    this.noRequestFor.emit('cancled');
  }
  yesRequestFor(){
    this.spinner.show();
  if(this.expenseId!==null){
    // this.url='api/tourexpense'
    this.expenseservice.deleteExpense(this.expenseId,this.url).subscribe(data=>{
   
      this.spinner.hide();
       this.popupNgClass='alert alert-success center center-block';
       this.message='Deleted Successfully';
         this.showMessage(this.message);
       this.onSubmitted.emit(data);
      //  this.deleteExpModel.show();
     },error=>{
       this.spinner.hide();
       this.showAlert(error.error.message)
     })
  }

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
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.noRequestFor.emit('cancled');
    }
  }

}
