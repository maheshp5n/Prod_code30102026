import { Component, OnInit, Output, EventEmitter, ViewChild, Input } from '@angular/core';
import { AccountsTypeService } from '../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';
import { BusinessDomain, AccountsType, AccountCreate, CountryDetails, StateProvinceDetails, AccountsForList, CustomerForList } from '../../services/model';
import { AccountService } from '../../services/accounts.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ModalDirective } from 'ngx-bootstrap';
import { CustomerService } from '../../services/customers.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-createaccount',
  templateUrl: './createaccount.component.html',
  styleUrls: ['./createaccount.component.css']
})
export class CreateaccountComponent implements OnInit {

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createCustomerModel', { static: false }) createCustomerModel: ModalDirective;
  @ViewChild('createParentAccountModel', { static: false }) createParentAccountModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<AccountCreate> = new EventEmitter<AccountCreate>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  _types: Array<AccountsType> = [];
  _catagory: Array<BusinessDomain> = [];
  _countryDetails: Array<CountryDetails> = [];
  _billingState: Array<StateProvinceDetails> = [];
  _shippingState: Array<StateProvinceDetails> = [];
  customerList: Array<CustomerForList> = [];
  cutomerForList: CustomerForList = new CustomerForList();
  accountForList: Array<AccountsForList> = []
  accountList: AccountsForList = new AccountsForList();
  loginData;
  createAccount: AccountCreate = new AccountCreate();
  parentAccount: AccountCreate = new AccountCreate();
  parentcustomer: CustomerForList = new CustomerForList();
  message: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  isCustomerModelShow: boolean;
  isParentAccountModelShow: boolean;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(accountCreate: AccountCreate) {
    this.createAccount = accountCreate;
    if (this.createAccount.billingCountryId != null) {
      this._countryDetailsService.getStateByCountryCode(this.createAccount.billingCountryId).subscribe(data => this._billingState = data);
    }
    if (this.createAccount.shippingCountryId != null) {
      this._countryDetailsService.getStateByCountryCode(this.createAccount.shippingCountryId).subscribe(data => this._shippingState = data);
    }
  }

  get editAccountData(): AccountCreate {
    return this.createAccount;
  }

  constructor(private spinner: NgxSpinnerService, private _accountTypeService: AccountsTypeService
    , private _accountCatagoryServices: AccountsCatagoryServices
    , private _accountCreateService: AccountService
    , private _countryDetailsService: CountryDetailsServices
    , private _customerService: CustomerService, private loginService: Loginservices
  ) { }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getCatagory();
    this.getType();
    this.getAllCountryDetails();
    this.getAllCustomer(),
      this.getAccountForList();
    this.ownerName();
    this.faPlus;
  }
  getCatagory() {
    this._accountCatagoryServices.getCatagory().subscribe(data => this._catagory = data);
  }
  getType() {
    this._accountTypeService.getAccountsType().subscribe(data => this._types = data);
  }
  getAllCountryDetails() {
    this._countryDetailsService.getAllCountry().subscribe(data => this._countryDetails = data)
  }
  billingState(billingCountryId: string) {
    if (billingCountryId != null && billingCountryId != undefined) {
      this.spinner.show();
      this._countryDetailsService.getStateByCountryCode(billingCountryId).subscribe(data => {
        this._billingState = data;
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {

      });
    }
  }
  shippingState(shippingCountryId: string) {
    if (shippingCountryId != null && shippingCountryId != undefined) {
      this.spinner.show();
      this._countryDetailsService.getStateByCountryCode(shippingCountryId).subscribe(data => {
        this._shippingState = data;
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {

      });
    }
  }

  getAllCustomer() {
    this._customerService.getAllCustomer().subscribe(data => {
      this.customerList = data;
    }, err => {

    })
  }
  getAccountForList() {
    this._accountCreateService.getAccountsForList().subscribe(data => {
      this.accountForList = data;
    }, err => {

    })
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
  }

  postCreateAccount(sampAccountNumber) {
    this.createAccount.sampAccountNumber = sampAccountNumber;
    if (this.createAccount.accountName == null || this.createAccount.accountName == '' || this.createAccount.accountName == undefined) {
      this.message = 'Please fill the account name';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.accountName != undefined && (this.createAccount.accountName).length >= 200) {
      this.message = 'Please fill the account name under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.sampAccountNumber == null || this.createAccount.sampAccountNumber == '' || this.createAccount.sampAccountNumber == undefined) {
      this.message = 'Please fill the SAP customer number';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.sampAccountNumber != undefined && (this.createAccount.sampAccountNumber).length >= 200) {
      this.message = 'Please fill the SAP customer number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.parentCustomerId == null || this.createAccount.parentCustomerId === 0) {
      this.message = 'Please fill the parent customer';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.accountCategoryId === 0) {
      this.message = 'Please fill the account category';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.accountTypeId === 0) {
      this.message = 'Please fill the account type';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.phone == null || (FieldValidation.isValidTelephoneNumber(this.createAccount.phone) == false)) {
      this.message = 'Please fill the 10 digit phone no ';
      this.popupSuccessModel.show();
    }
    else if (FieldValidation.isValidEmailMandatory(this.createAccount.emailid) == false || this.createAccount.emailid == '' || this.createAccount.emailid == undefined) {
      this.message = 'Please fill the valid email id';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.emailid != undefined && (this.createAccount.emailid).length >= 200) {
      this.message = 'Please fill the valid email id under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.description != undefined && (this.createAccount.description).length >= 500) {
      this.message = 'Please fill the account description under 500 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingAddress == null || this.createAccount.billingAddress == '' || this.createAccount.billingAddress == undefined) {
      this.message = 'Please fill the account billing address';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingAddress != undefined && (this.createAccount.billingAddress).length >= 200) {
      this.message = 'Please fill the account billing address under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingStreet != undefined && (this.createAccount.billingStreet).length >= 200) {
      this.message = 'Please fill the account billing street under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingCity == null || this.createAccount.billingCity == '' || this.createAccount.billingCity == undefined) {
      this.message = 'Please fill the account billing city ';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingCity != undefined && (this.createAccount.billingCity).length >= 200) {
      this.message = 'Please fill the account billing city under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (FieldValidation.isEmpty(this.createAccount.billingCountryId) || this.createAccount.billingCountryId === '0') {
      this.message = 'Please fill the account billing country';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.billingStateId == null || this.createAccount.billingStateId === 0) {
      this.message = 'Please fill the account billing state';
      this.popupSuccessModel.show();
    }

    else if (this.createAccount.billingPincode == null || FieldValidation.isValidPostalCode(this.createAccount.billingPincode) == false) {
      this.message = 'Please fill the 6 digit billing pin code';
      this.popupSuccessModel.show();
    }

    else if (this.createAccount.shippingAddress != undefined && (this.createAccount.shippingAddress).length >= 200) {
      this.message = 'Please fill the account shipping address under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.shippingStreet != undefined && (this.createAccount.shippingStreet).length >= 200) {
      this.message = 'Please fill the account shipping street under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.createAccount.shippingCity != undefined && (this.createAccount.shippingCity).length >= 200) {
      this.message = 'Please fill the account shipping city under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (!FieldValidation.isShippingPostalCode(this.createAccount.shippingPincode)) {
      this.message = 'Please fill the 6 digit shipping pin code';
      this.popupSuccessModel.show();
    }

    else {
      if (!this._isEditMode) {
        this.spinner.show();
        this._accountCreateService.postAccounts(this.createAccount).subscribe(data => {
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAccountCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      else {
        this.spinner.show();
        this._accountCreateService.updateAccountDetails(this.createAccount, this.createAccount.sampAccountNumber).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAccountCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      }
    }
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.onCancle.emit('cancled');
  }

  // parent Account
  createParentAccountModelshow() {
    this.parentAccount = new AccountCreate();
    this.createParentAccountModel.show()
  }
  createParentAccountModelhide() {
    this.parentAccount = new AccountCreate();
    this.createParentAccountModel.hide();
  }
  cancelDataParent() {
    this.parentAccount = new AccountCreate();
    this.createParentAccountModel.hide();
  }

  // parent customer
  createCustomerModelshow() {
    this.parentcustomer = new CustomerForList();
    this.createCustomerModel.show();
  }

  createCustomerModelhide() {
    this.parentcustomer = new CustomerForList();
    this.createCustomerModel.hide();
  }

  cancelDataCustomer() {
    this.parentcustomer = new CustomerForList();
    this.createCustomerModel.hide();
  }

  sameAsBillingAddress($event) {
    if ($event.target.checked) {
      this.createAccount.shippingAddress = this.createAccount.billingAddress;
      this.createAccount.shippingStreet = this.createAccount.billingStreet;
      this.createAccount.shippingCity = this.createAccount.billingCity;
      this.createAccount.shippingCountryId = this.createAccount.billingCountryId;
      this.shippingState(this.createAccount.shippingCountryId);
      this.createAccount.shippingStateId = this.createAccount.billingStateId;
      this.createAccount.shippingPincode = this.createAccount.billingPincode;
    }
    else {
      this.createAccount.shippingAddress = '';
      this.createAccount.shippingStreet = '';
      this.createAccount.shippingCity = '';
      this.createAccount.shippingCountryId = '0';
      this.createAccount.shippingStateId = 0;
      this.createAccount.shippingPincode = '';
    }
  }


  onParentCustomerCreated($event: CustomerForList) {
    this.getAllCustomer();
    this.createAccount.parentCustomerId = $event.customerId;
  }
  onParentAccountCreated($event: AccountCreate) {
    this.getAccountForList();
    this.createAccount.parentAccountSapNumber = $event.sampAccountNumber;
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}
