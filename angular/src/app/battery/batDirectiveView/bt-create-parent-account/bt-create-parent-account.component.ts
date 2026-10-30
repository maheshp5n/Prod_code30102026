import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AccountService } from '../../../services/accounts.services';
import { AccountsTypeService } from '../../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../../services/catagory.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, AccountsForList, AccountsType, BusinessDomain, CountryDetails, CustomerForList, StateProvinceDetails } from '../../../services/model';
import { FieldValidation } from '../../../services/validation.services';
import { BtaccountService } from '../../bat_services/btaccount.service';

@Component({
  selector: 'app-bt-create-parent-account',
  templateUrl: './bt-create-parent-account.component.html',
  styleUrls: ['./bt-create-parent-account.component.scss']
})
export class BtCreateParentAccountComponent implements OnInit {
 // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
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
    , private _accountCatagoryServices: AccountsCatagoryServices,
    private _btAccountServie: BtaccountService
    , private _accountCreateService: AccountService
    , private _countryDetailsService: CountryDetailsServices
    , private _customerService: CustomerService, private loginService: Loginservices,
      private _global: GlobalserviceService
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
  
  btCategory: Array<BusinessDomain> = [];
  getCatagory() {
    this._accountCatagoryServices.getCatagory().subscribe(data=>{
      this._catagory = data;
      for (let i = 0; i < this._catagory.length; i++) {
        if (this._catagory[i].businessDomainName == "Battery") {
          this.btCategory.push(this._catagory[i]);
          console.log(this.btCategory);
        }
      }

    })
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
    this._btAccountServie.getBtAccountsForList().subscribe(data => {
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
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Name' });
    }
    else if (this.parentAccount.accountName != undefined && (this.parentAccount.accountName).length >= 200) {
     this._global.alert({ type: 'alert', message: 'Please Fill The Account Name Under 200 Characters' });
    }
    else if (this.parentAccount.sampAccountNumber == null || this.parentAccount.sampAccountNumber === '' || this.parentAccount.sampAccountNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The SAP Customer Number'});
    }
    else if (this.parentAccount.sampAccountNumber != undefined && (this.parentAccount.sampAccountNumber).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The SAP Customer Number Under 200 Characters' });
    }
    else if (this.parentAccount.parentCustomerId == null || this.parentAccount.parentCustomerId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Parent Customer' });
    }
    else if (this.parentAccount.accountCategoryId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Category' });
    }
    else if (this.parentAccount.accountTypeId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Type'});
    }
    else if (this.parentAccount.phone == null || (FieldValidation.isValidTelephoneNumber(this.parentAccount.phone) == false)) {
      this._global.alert({ type: 'alert', message: 'Please Fill The 10 Digit Phone No '});
    }
    else if (FieldValidation.isValidEmailMandatory(this.parentAccount.emailid) == false || this.parentAccount.emailid == '' || this.parentAccount.emailid == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Valid Email Id' });
    }
    else if (this.parentAccount.emailid != undefined && (this.parentAccount.emailid).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Valid Email Id Under 200 Characters' });
    }
    else if (this.parentAccount.description != undefined && (this.parentAccount.description).length >= 500) {
      this._global.alert({ type: 'alert', message:'Please Fill The Account Description Under 500 Characters' });
    }
    else if (this.parentAccount.billingAddress == null || this.parentAccount.billingAddress === '' || this.parentAccount.billingAddress == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Address'});
    }
    else if (this.parentAccount.billingAddress != undefined && (this.parentAccount.billingAddress).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Address Under 200 Characters' });
    }
    else if (this.parentAccount.billingStreet != undefined && (this.parentAccount.billingStreet).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Street Under 200 Characters' });
    }
    else if (this.parentAccount.billingCity == null || this.parentAccount.billingCity === '' || this.parentAccount.billingCity == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing City ' });
    }
    else if (this.parentAccount.billingCity != undefined && (this.parentAccount.billingCity).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing City Under 200 Characters'});
    }
    else if (FieldValidation.isEmpty(this.parentAccount.billingCountryId) || this.parentAccount.billingCountryId === '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Country' });
    }
    else if (this.parentAccount.billingStateId == null || this.parentAccount.billingStateId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing State'});
    }

    else if (this.parentAccount.billingPincode == null || FieldValidation.isValidPostalCode(this.parentAccount.billingPincode) == false) {
      this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Billing Pin Code' });
    }

    else if (this.parentAccount.shippingAddress != undefined && (this.parentAccount.shippingAddress).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Shipping Address Under 200 Characters'});
    }
    else if (this.parentAccount.shippingStreet != undefined && (this.parentAccount.shippingStreet).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Shipping Street Under 200 Characters'});
    }
    else if (this.parentAccount.shippingCity != undefined && (this.parentAccount.shippingCity).length >= 200) {
      this._global.alert({ type: 'alert', message:'Please Fill The Account Shipping City Under 200 Characters' });
    }
    else if (!FieldValidation.isShippingPostalCode(this.parentAccount.shippingPincode)) {
      this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Shipping Pin Code'});
    }
    else {
      this.spinner.show();
      this._btAccountServie.postBtAccounts(this.parentAccount).subscribe(data => {
        this._global.alert({ type: '', message: data.accountName+' Submitted Successfully' });
        this.onAccountCreated.emit(data);      
        this.onCancle.emit('cancled');
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {
        this.spinner.hide();       
        this._global.alert({ type: 'alert', message: err.error.message});
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
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  LimteExceed:boolean;
  onKey(event):any {
    console.log(event)
    if(event.target.value.length == 10)
   {
    if(FieldValidation.isValidMobileNumber(event.target.value)===false)
   {
     this.message="Please Fill Valid Phone No";
     this._global.alert({ type: 'alert', message: this.message });
     return false;
   }
   }
    if(event.target.value.length>9){
      this.LimteExceed=true;
     
    }
    else{
      this.LimteExceed=false;
    }
   }

   onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(this.LimteExceed==true){
      return false;
    }
    return true;

  }

  cantStartWithZero(pinCode):boolean{

    
    if (pinCode.startsWith(0)) 
      {
        
        let message = "Zip Code Cannot Start With 0";
        this.parentAccount.billingPincode=null;
        this.parentAccount.shippingPincode=null;

        this._global.alert({ type: 'alert', message: message });
        return false;

      }
      
    
  }
   limitZip(event)
  {
    console.log(event.value.length);
    
      var max_chars = 6;

      if(event.value.length > max_chars) {
        event.value = event.value.substr(0, max_chars);
        return false;

      }
      else{
        return true;
      }
  }

  numberOnly(event): boolean {
    console.log("keypress called");
   
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
   else {

     return true;
   }
  

  }
}
