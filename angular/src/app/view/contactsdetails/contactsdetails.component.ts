import { ActionHistoryObjectAndId, ExpenseManagementRequest } from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ContactsService } from '../../services/contacts.services';
import { ActivatedRoute } from '@angular/router';
import { Contacts, AccountCreate, Page, BoardingLoading, ExpenseManagementResponse } from '../../services/model';
import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-contactsdetails',
  templateUrl: './contactsdetails.component.html',
  styleUrls: ['./contactsdetails.component.css']
})
export class ContactsdetailsComponent implements OnInit {
  isExpensesmanagementOpen: boolean;
  contactsList: Array<Contacts> = [];
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  boardingList;
  firstName;
  lastName;
  loginData;
  sapId: string = '';
  account: AccountCreate = new AccountCreate();
  accountList: Array<AccountCreate> = [];
  gradeId;
  content;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  newcontact: Contacts = new Contacts();
  contactid;
  contact: Contacts = new Contacts();
  flag: boolean;
  isEditMode: boolean = false;
  legalEntityList;
  postBoardingLoading: BoardingLoading = new BoardingLoading();
  accountPage: Page<AccountCreate> = new Page<AccountCreate>();
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  newexpensesmanagement: ExpenseManagementRequest = new ExpenseManagementRequest();
  EDIT_expensesmanagement_HEADING: string = 'Edit Expenses';

  CREATE_ExpenseManagement_HEADING: string = 'New Expense';
  EDIT_ExpenseManagement_HEADING: string = 'Edit Expense';
  headingExpenseManagement: string;
  isEditModeExpenseManagement: boolean = false;
  firstExpenseManagement: boolean;
  lastExpenseManagement: boolean;
  sizeExpenseManagement: number;
  serialNumberExpenseManagement: number;
  public pageExpenseManagement: number = 0;
  totalElementsExpenseManagement: number;
  expenseManagementResponse: ExpenseManagementResponse = new ExpenseManagementResponse();
  expenseListByContactId: Array<ExpenseManagementResponse> = [];
  _cFlag: string;
  isContactOpen: boolean;

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  @ViewChild('createContactModel') createContactModel: ModalDirective;
  @ViewChild('GradeModel') GradeModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createexpensesmanagementModel') createexpensesmanagementModel: ModalDirective;

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private _accountService: AccountService, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute, private _contactService: ContactsService,
    private _expenseManagementService: ExpenseManagementService, private loginService: Loginservices) {
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this._activatedRoute.params.subscribe(pram => {
      this.contactid = pram['id'];
      // this.getAccount();
      this.getContactDetails();
      // this.getLegalEntityId();
      // this.getBoardingList();
      // this.getExpenseManagementListByExpenseId(this.contactid);
      this.actionHistoryObjectAndId.objectId = this.contactid;
      this.actionHistoryObjectAndId.objectName = 'ContactDetails';
    });
  }

  // getAccount() {
  //   this._accountServices.getAccount(this.sapId).subscribe(data => {
  //     this.account = data;
  //   });
  // }

  postBoarding() {
    this._accountService.postBoardingList(this.postBoardingLoading).subscribe(data => {
      this.postBoardingLoading = new BoardingLoading();
      this.GradeModel.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.message;
      this.popupSuccessModel.show();
    
    });
  }

  // getLegalEntityId() {
  //   this.ligalentitservice.getLegalEntityId().subscribe(data => {
  //     this.legalEntityList = data;
  //   });
  // }

  modo(value: string) {
    if (value === 'myVal') {
      this.GradeModel.show();
    }
  }

  // getBoardingList() {
  //   this._accountService.getBoardingList().subscribe(data => {
  //     this.boardingList = data;
  //     if (this.boardingList.length > 0) {
  //       this.newcontact.gradeName = this.boardingList[0].gradeName;
  //     }
  //   });
  // }

  onClickEdit(contact, i) {
    this.spinner.show();
    this._contactService.getContact(i).subscribe(data => {
      this.newcontact = data;
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CONTACT_HEADING;
    this.createContactModel.show();

  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getContactDetails();
    } else {
      // if (this.expenseListByContactId.length === 0) {
      // this.getExpenseManagementListByExpenseId(this.contactid);
      // }
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }

  getContactDetails() {
    this.spinner.show();
    this._contactService.getContact(this.contactid).subscribe(data => {
      this.contact = data;
        this.spinner.hide();
     
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
  }

  cancelData() {
    this.createContactModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_CONTACT_HEADING;
    this.newcontact = new Contacts();
  }

  onContactCreated($event: Contacts) {
    // this.contactsList.push($event);
    this.contact = $event;
    this.getContactDetails();
    // this.getExpenseManagementListByExpenseId(this.contactid);;
  }

  onClickEditExpenses(id) {
    this.spinner.show();
    this._expenseManagementService.getexpensesmanagementById(id).subscribe(data => {
      this.newexpensesmanagement = data;
      // this.newexpensesmanagement.contactId = this.contactid;
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditModeExpenseManagement = true;
    this.headingExpenseManagement = this.EDIT_expensesmanagement_HEADING;
    this.createexpensesmanagementModel.show();
  }

  onexpensesmanagementCreated($event) {
    // this.getExpenseManagementListByExpenseId(this.contactid) 
       if (!this.isEditModeExpenseManagement) {
      this.expenseListByContactId.unshift($event);
      this.totalElementsExpenseManagement = this.totalElementsExpenseManagement + 1;
    } else {
      let index = 0;
      this.expenseListByContactId.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.expenseListByContactId[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  cancelDataExpense() {
    this.createexpensesmanagementModel.hide();
    this.isEditModeExpenseManagement = false;
    // this.heading = this.CREATE_expensesmanagement_HEADING;
    this.newexpensesmanagement = new ExpenseManagementRequest();
    // this.newexpensesmanagement.next = false;
  }



  // ExpenseManagement starts
  setPagePreviousExpenseManagement(event: any) {
    event.preventDefault();
    this.pageExpenseManagement = this.pageExpenseManagement - 1;
    // this.getExpenseManagementListByExpenseId(this.contactid);

  }

  setPageExpenseManagement(event: any) {
    event.preventDefault();
    // this.getExpenseManagementListByExpenseId(this.contactid);
  }

  setPageNextExpenseManagement(event: any) {
    event.preventDefault();
    this.pageExpenseManagement = this.pageExpenseManagement + 1;
    // this.getExpenseManagementListByExpenseId(this.contactid);
  }

  // getExpenseManagementListByExpenseId(contactId) {
  //   this.spinner.show();
  //   this._contactService.getExpenseManagementListByExpenseId(contactId, this.pageExpenseManagement).subscribe(
  //     data => {
  //       this.expenseListByContactId = data.content;
  //       this.firstExpenseManagement = data.first;
  //       this.lastExpenseManagement = data.last;
  //       this.sizeExpenseManagement = data.size;
  //       this.totalElementsExpenseManagement = data.totalElements;
  //       this.serialNumberExpenseManagement = ((this.pageExpenseManagement) * this.sizeExpenseManagement);
  //       if (data !== null) {
  //         this.spinner.hide();
  //       }
  //     }, err => {
  //       this.spinner.hide();
  //       this.message = err.error.message;
  //       this.popupSuccessModel.show();
  //     });
  // }

  showExpenseManagement() {
    this.isEditModeExpenseManagement = false;
    this.headingExpenseManagement = this.CREATE_ExpenseManagement_HEADING;
    this.newexpensesmanagement = new ExpenseManagementRequest();
    // this.newexpensesmanagement.contactId = this.contactid;
    // this.newexpensesmanagement.contactId = null;
    this.createexpensesmanagementModel.show();
  }

  cancelExpenseManagement() {
    this.createexpensesmanagementModel.hide();
    this.isEditModeExpenseManagement = false;
    this.headingExpenseManagement = this.CREATE_ExpenseManagement_HEADING;
    this.newexpensesmanagement = new ExpenseManagementRequest();
  }

}
