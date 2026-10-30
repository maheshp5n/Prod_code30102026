import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ExpenseCreate, AccountsType, BusinessDomain } from '../../services/model';
import { AccountsTypeService } from '../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';
import { AccountService } from '../../services/accounts.services';

@Component({
  selector: 'app-create-expenses-report',
  templateUrl: './create-expenses-report.component.html',
  styleUrls: ['./create-expenses-report.component.css']
})
export class CreateExpensesReportComponent implements OnInit {

  @Output()
  onExpenseCreated: EventEmitter<ExpenseCreate> = new EventEmitter<ExpenseCreate>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onExpenseCreatedAndNew: EventEmitter<ExpenseCreate> = new EventEmitter<ExpenseCreate>();


  _types: Array<AccountsType> = [];
  _catagory: Array<BusinessDomain> = [];
  createAccount: ExpenseCreate = new ExpenseCreate();

  constructor(private _accountTypeService: AccountsTypeService
    , private _accountCatagoryServices: AccountsCatagoryServices
    , private _ExpenseCreateService: AccountService
  ) { }

  ngOnInit() {
    this.getCatagory();
    this.getType();
  }
  getCatagory() {
    this._accountCatagoryServices.getCatagory().subscribe(data => this._catagory = data);
  }
  getType() {
    this._accountTypeService.getAccountsType().subscribe(data => this._types = data);
  }
  // postCreateAccount() {
  //   this._ExpenseCreateService.postAccounts(this.createAccount).subscribe(data => {
  //     this.createAccount = new ExpenseCreate();
  //     this.onExpenseCreated.emit(data);
  //   });
  // }
  // postCreateAccountAndNew() {
  //   this._ExpenseCreateService.postAccounts(this.createAccount).subscribe(data => {
  //     this.createAccount = new ExpenseCreate();
  //     this.onExpenseCreatedAndNew.emit(data);
  //   });
  // }
  cancelData() {
    this.createAccount = new ExpenseCreate();
    this.onCancle.emit('cancled');
  }
}
