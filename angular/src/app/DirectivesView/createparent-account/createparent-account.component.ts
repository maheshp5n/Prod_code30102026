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
  selector: 'app-createparent-account',
  templateUrl: './createparent-account.component.html',
  styleUrls: ['./createparent-account.component.scss']
})
export class CreateparentAccountComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createCustomerModel', { static: false }) createCustomerModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<AccountCreate> = new EventEmitter<AccountCreate>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  _types: Array<AccountsType> = [];
  _catagory: Array<BusinessDomain> = [];
  _countryDetails: Array<CountryDetails> = [];
  _billingState: Array<StateProvinceDetails> = [];
  _shippingState: Array<StateProvinceDetails> = [];
  customerList: Array<CustomerForList> = [];
  accountForList: Array<AccountsForList> = []
  loginData;
  parentAccount: AccountCreate = new AccountCreate();
  parentcustomer: CustomerForList = new CustomerForList();
  message: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  isCustomerModelShow: boolean= false;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(accountCreate: AccountCreate) {
    this.parentAccount = accountCreate;
    if (this.parentAccount.billingCountryId != null) {
      this._countryDetailsService.getStateByCountryCode(this.parentAccount.billingCountryId).subscribe(data => this._billingState = data);
    }
    if (this.parentAccount.shippingCountryId != null) {
      this._countryDetailsService.getStateByCountryCode(this.parentAccount.shippingCountryId).subscribe(data => this._shippingState = data);
    }
  }

  get editAccountData(): AccountCreate {
    return this.parentAccount;
  }
  
  constructor(private spinner: NgxSpinnerService, private _accountTypeService: AccountsTypeService
    , private _accountCatagoryServices: AccountsCatagoryServices
    , private _accountCreateService: AccountService
    , private _countryDetailsService: CountryDetailsServices
    , private _customerService: CustomerService, private loginService: Loginservices
  ) { }
  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
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
    this.loginData = this.loginService.getLoggedInUser()
  }

  postCreateAccount(sampAccountNumber) {
    this.parentAccount.sampAccountNumber = sampAccountNumber;
    if (this.parentAccount.accountName == null || this.parentAccount.accountName === '' || this.parentAccount.accountName == undefined) {
      this.message = 'Please fill the account name';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.accountName != undefined && (this.parentAccount.accountName).length >= 200) {
      this.message = 'Please fill the account name under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.sampAccountNumber == null || this.parentAccount.sampAccountNumber === '' || this.parentAccount.sampAccountNumber == undefined) {
      this.message = 'Please fill the SAP customer number';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.sampAccountNumber != undefined && (this.parentAccount.sampAccountNumber).length >= 200) {
      this.message = 'Please fill the SAP customer number under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.parentCustomerId == null || this.parentAccount.parentCustomerId === 0) {
      this.message = 'Please fill the parent customer';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.accountCategoryId === 0) {
      this.message = 'Please fill the account category';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.accountTypeId === 0) {
      this.message = 'Please fill the account type';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.phone == null || (FieldValidation.isValidTelephoneNumber(this.parentAccount.phone) == false)) {
      this.message = 'Please fill the 10 digit phone no ';
      this.popupSuccessModel.show();
    }
    else if (FieldValidation.isValidEmailMandatory(this.parentAccount.emailid) == false || this.parentAccount.emailid == '' || this.parentAccount.emailid == undefined) {
      this.message = 'Please fill the valid email id';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.emailid != undefined && (this.parentAccount.emailid).length >= 200) {
      this.message = 'Please fill the valid email id under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.description != undefined && (this.parentAccount.description).length >= 500) {
      this.message = 'Please fill the account description under 500 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingAddress == null || this.parentAccount.billingAddress === '' || this.parentAccount.billingAddress == undefined) {
      this.message = 'Please fill the account billing address';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingAddress != undefined && (this.parentAccount.billingAddress).length >= 200) {
      this.message = 'Please fill the account billing address under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingStreet != undefined && (this.parentAccount.billingStreet).length >= 200) {
      this.message = 'Please fill the account billing street under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingCity == null || this.parentAccount.billingCity === '' || this.parentAccount.billingCity == undefined) {
      this.message = 'Please fill the account billing city ';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingCity != undefined && (this.parentAccount.billingCity).length >= 200) {
      this.message = 'Please fill the account billing city under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (FieldValidation.isEmpty(this.parentAccount.billingCountryId) || this.parentAccount.billingCountryId === '0') {
      this.message = 'Please fill the account billing country';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.billingStateId == null || this.parentAccount.billingStateId === 0) {
      this.message = 'Please fill the account billing state';
      this.popupSuccessModel.show();
    }

    else if (this.parentAccount.billingPincode == null || FieldValidation.isValidPostalCode(this.parentAccount.billingPincode) == false) {
      this.message = 'Please fill the 6 digit billing pin code';
      this.popupSuccessModel.show();
    }

    else if (this.parentAccount.shippingAddress != undefined && (this.parentAccount.shippingAddress).length >= 200) {
      this.message = 'Please fill the account shipping address under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.shippingStreet != undefined && (this.parentAccount.shippingStreet).length >= 200) {
      this.message = 'Please fill the account shipping street under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (this.parentAccount.shippingCity != undefined && (this.parentAccount.shippingCity).length >= 200) {
      this.message = 'Please fill the account shipping city under 200 characters';
      this.popupSuccessModel.show();
    }
    else if (!FieldValidation.isShippingPostalCode(this.parentAccount.shippingPincode)) {
      this.message = 'Please fill the 6 digit shipping pin code';
      this.popupSuccessModel.show();
    }
    else {
      this.spinner.show();
      this._accountCreateService.postAccounts(this.parentAccount).subscribe(data => {
        this.message = 'Submitted Successfully';
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
  }
  cancelData() {
    this.parentAccount = new AccountCreate();
    this.onCancle.emit('cancled');
  }

  sameAsBillingAddress(event) {
    if (event.target.checked) {
      this.parentAccount.shippingAddress = this.parentAccount.billingAddress;
      this.parentAccount.shippingStreet = this.parentAccount.billingStreet;
      this.parentAccount.shippingCity = this.parentAccount.billingCity;
      this.parentAccount.shippingCountryId = this.parentAccount.billingCountryId;
      this.shippingState(this.parentAccount.shippingCountryId);
      this.parentAccount.shippingStateId = this.parentAccount.billingStateId;
      this.parentAccount.shippingPincode = this.parentAccount.billingPincode;
    }
    else {
      this.parentAccount.shippingAddress = ''
      this.parentAccount.shippingStreet = ''
      this.parentAccount.shippingCity = ''
      this.parentAccount.shippingCountryId = '0'
      this.parentAccount.shippingStateId = 0
      this.parentAccount.shippingPincode = ''
    }
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
  onParentCustomerCreated($event: CustomerForList) {
    this.getAllCustomer();
    this.parentAccount.parentCustomerId = $event.customerId;
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
