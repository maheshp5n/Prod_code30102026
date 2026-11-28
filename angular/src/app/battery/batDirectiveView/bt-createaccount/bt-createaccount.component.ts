import { Component, OnInit, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AccountsTypeService } from '../../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../../services/catagory.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, AccountsForList, AccountsType, BusinessDomain, CountryDetails, CustomerForList, StateProvinceDetails } from '../../../services/model';
import { FieldValidation } from '../../../services/validation.services';
import { BtaccountService } from '../../bat_services/btaccount.service';

@Component({
  selector: 'app-bt-createaccount',
  templateUrl: './bt-createaccount.component.html',
  styleUrls: ['./bt-createaccount.component.css']
})
export class BtCreateaccountComponent implements OnInit {

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createCustomerModel') createCustomerModel: ModalDirective;
  @ViewChild('createParentAccountModel') createParentAccountModel: ModalDirective;

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
  checked:boolean=false;

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
    console.log(this.createAccount);
    
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
    , private _countryDetailsService: CountryDetailsServices
    , private _customerService: CustomerService, private loginService: Loginservices,
    private _btAccountServie: BtaccountService, private _global: GlobalserviceService
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
    this.createAccount.billingCountryId='In';
    this.faPlus;
  }
  btCategory: Array<BusinessDomain> = [];
  getCatagory() {
    this._accountCatagoryServices.getCatagory().subscribe(data => {
      this._catagory = data;
      for (let i = 0; i < this._catagory.length; i++) {
        if (this._catagory[i].businessDomainName == "Battery") {
          this.btCategory.push(this._catagory[i]);
        }
      }

    })
  }
  getType() {
    this._accountTypeService.getAccountsType().subscribe(data => this._types = data);
  }
  getAllCountryDetails(){
    this._countryDetailsService.getAllCountry().subscribe(data=>{
      this._countryDetails = data;
      this.billingState(this.createAccount.billingCountryId);
    })
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
    this.loginData = this.loginService.getLoggedInUser();
  }
  
  LimteExceed:boolean;
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


   onlyNumbersZIP(key): boolean {
    // let zipCode = key.target.value;
    // let patt = "^[1-9][0-9]{5}$";
    

    // if(zipCode.match(patt))
    // {
    //   return false;
    // }
    // else {
    //   this._global.alert({ type: 'alert', message: 'Limit Exceeds' });
    //   return true;
    // }
    const charCode = (key.which) ? key.which : key.keyCode;
    console.log(charCode);
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    if(charCode==48){
      return false
    }
    else if(this.LimteExceed==true){
      this._global.alert({ type: 'alert', message: 'Limit Exceeds' });
      return false;
    }
    return true;

  }
  onKeyZIP(event):any {
    if(event.target.value.length>5){
      this.LimteExceed=true;
     
    }
    else{
      this.LimteExceed=false;
    }
   }
  postCreateAccount(sampAccountNumber) {
    this.createAccount.sampAccountNumber = sampAccountNumber;
    if (this.createAccount.accountName == null || this.createAccount.accountName == '' || this.createAccount.accountName == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Name' });
    }
    else if (this.createAccount.accountName != undefined && (this.createAccount.accountName).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Name Under 200 Characters' });
    }
    else if (this.createAccount.sampAccountNumber == null || this.createAccount.sampAccountNumber == '' || this.createAccount.sampAccountNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The SAP Customer Number' });
    }
    else if (this.createAccount.sampAccountNumber != undefined && (this.createAccount.sampAccountNumber).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The SAP Customer Number Under 200 Characters' });
    }
    else if (this.createAccount.parentCustomerId == null || this.createAccount.parentCustomerId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Parent Customer' });
    }
    else if (this.createAccount.accountCategoryId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Category' });
    }
    else if (this.createAccount.accountTypeId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Category' });
    }
    else if (this.createAccount.phone == null || (FieldValidation.isValidTelephoneNumber(this.createAccount.phone) == false)) {
      this._global.alert({ type: 'alert', message: 'Please Fill The 10 Digit Phone No' });
    }
    // else if (FieldValidation.isValidEmailMandatory(this.createAccount.emailid) == false || this.createAccount.emailid == '' || this.createAccount.emailid == undefined) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The Valid Email Id' });
    // }
    else if (this.createAccount.emailid != undefined && (this.createAccount.emailid).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Valid Email Id Under 200 Characters' });
    }
    else if (this.createAccount.description != undefined && (this.createAccount.description).length >= 500) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Description Under 500 Characters' });
    }
    else if (this.createAccount.billingAddress == null || this.createAccount.billingAddress == '' || this.createAccount.billingAddress == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Address' });
    }
    else if (this.createAccount.billingAddress != undefined && (this.createAccount.billingAddress).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Address Under 200 Characters' });
    }
    else if (this.createAccount.billingStreet != undefined && (this.createAccount.billingStreet).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Street Under 200 Characters' });
    }
    else if (this.createAccount.billingCity == null || this.createAccount.billingCity == '' || this.createAccount.billingCity == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing City' });
    }
    else if (this.createAccount.billingCity != undefined && (this.createAccount.billingCity).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing City Under 200 Characters' });
    }
    else if (FieldValidation.isEmpty(this.createAccount.billingCountryId) || this.createAccount.billingCountryId === '0') {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing Country' });
    }
    else if (this.createAccount.billingStateId == null || this.createAccount.billingStateId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Billing State' });
    }
    else if(FieldValidation.isEmpty(this.createAccount.billingPincode))
    {
      this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Valid Pin Code'});
    }

    // else if (this.createAccount.billingPincode == null || FieldValidation.isValidPostaCodeNotStartZero(this.createAccount.billingPincode) == false) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Billing Pin Code Or Valid Pin Code' });
    // }

    else if (this.createAccount.shippingAddress != undefined && (this.createAccount.shippingAddress).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Shipping Address Under 200 Characters' });
    }
    else if (this.createAccount.shippingStreet != undefined && (this.createAccount.shippingStreet).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Shipping Street Under 200 Characters' });
    }
    else if (this.createAccount.shippingCity != undefined && (this.createAccount.shippingCity).length >= 200) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Account Shipping City Under 200 Characterss' });
    }
    
    else if(FieldValidation.isEmpty(this.createAccount.shippingPincode) && this.checked==true)
    {
      console.log(this.checked);
      this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Valid Pin Code' });
    }
    // else if (!FieldValidation.isValidPostaCodeNotStartZero(this.createAccount.shippingPincode)) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The 6 Digit Shipping Pin Code Valid Pin Code' });
    // }

    else {
      if (!this._isEditMode) {
        this.spinner.show();
        this._btAccountServie.postBtAccounts(this.createAccount).subscribe(data => {        
          this._global.alert({ type: '', message: data.accountName+' Created Successfully' });
          this.onAccountCreated.emit(data);        
          this.onCancle.emit('cancled');
          if (data != null) {
            this.spinner.hide();
          }
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
      }
      else {
        this.spinner.show();
        this._btAccountServie.updateBtAccountDetails(this.createAccount, this.createAccount.sampAccountNumber).subscribe(data => {
          this._global.alert({ type: '', message: data.accountName+' Updated Successfully' });
          this.onAccountCreated.emit(data);
          this.onCancle.emit('cancled');
          if (data != null) {
            this.spinner.hide();
          }
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
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
    console.log("same is called");
    
    console.log(this.checked);
    
    if ($event.target.checked) {
       this.checked= true;
       console.log(this.checked);
      this.createAccount.shippingAddress = this.createAccount.billingAddress;
      this.createAccount.shippingStreet = this.createAccount.billingStreet;
      this.createAccount.shippingCity = this.createAccount.billingCity;
      this.createAccount.shippingCountryId = this.createAccount.billingCountryId;
      this.shippingState(this.createAccount.shippingCountryId);
      this.createAccount.shippingStateId = this.createAccount.billingStateId;
      this.createAccount.shippingPincode = this.createAccount.billingPincode;
    }
    else {
      this.checked= false;
      console.log(this.checked);
      this.createAccount.shippingAddress = null;
      this.createAccount.shippingStreet = null;
      this.createAccount.shippingCity = null;
      this.createAccount.shippingCountryId = null;
      this.createAccount.shippingStateId = null;
      this.createAccount.shippingPincode = null;
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
  // popupClose() {
  // //  this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');
  //   }
  // }

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
  cantStartWithZero(pinCode):boolean{

    
    if (pinCode.startsWith(0)) 
      {
        
        let message = "Zip Code Cannot Start With 0";
        this.createAccount.billingPincode=null;
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


}
