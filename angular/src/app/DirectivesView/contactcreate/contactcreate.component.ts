// DEVELOPED BY ANKUR SAXENA
import { ExpenseManagementService } from './../../services/expenses-management.service';
import { WarehouseService } from './../../services/warehouse.service';
import { ContactType, ContactDesignation, ServiceCenterForList, ExpenseManagementBusinessPlace } from './../../services/model';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ContactsService } from '../../services/contacts.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import {
  Contacts, BoardingLoadingForListResponse, AccountsForList, CountryDetails, StateProvinceDetails,
  BoardingLoading, Page, AccountCreate, LegalEntityResponse, BoardingLoadingDetailsResponse, BoardingLoadingDetailsRequest
} from '../../services/model';
import { AccountService } from '../../services/accounts.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { BoardingLoadingService } from '../../services/boarding-loading.service';

@Component({
  selector: 'app-contactcreate',
  templateUrl: './contactcreate.component.html',
  styleUrls: ['./contactcreate.component.css']
})
export class ContactcreateComponent implements OnInit {

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editContactData(Contact: Contacts) {
    this.newcontact = Contact;
    if(this.newcontact.contactTypeId==1){
    this.CodeName='Employee Code'
    // if(this.newcontact.contactTypeId == 1){
      this.spinner.show();
      this._contactService.getDesignationForlist().subscribe(data => {
        this.spinner.hide();
        this.designationForlist = data;
      });
    
    }
   else if(this.newcontact.contactTypeId==2){
    this.CodeName='Customer Code';
    }
    else{
        this.CodeName='Vendor Code';
        
      this.designationForlist = [];
    }
    this.popupNgClass = 'alert alert-danger center center-block';
    if (this.newcontact.contactid != null) {
      this._contactService
        .getContact(this.newcontact.contactid)
        .subscribe(data => {
          if (this.newcontact.contactMallingCountryId !== ''&& this.newcontact.contactMallingCountryId!==undefined &&this.newcontact.contactMallingCountryId !==null) {
            this._countryDetailsService
              .getStateByCountryCode(this.newcontact.contactMallingCountryId)
              .subscribe(data => (this._billingState = data));
          }
          this.newcontact.contactMallingCountryId =
            data.contactMallingCountryId;
          this.newcontact.contactMallingStateId = data.contactMallingStateId;
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
    }
    this._contactService.getTypeForList().subscribe(data => {
      this.typeForlist = data;
    });
  }

  get editContactData(): Contacts {
    return this.newcontact;
  }

  @Input()
  set editEntityData(res: LegalEntityResponse) {
    this.req = res;
  }

  get editEntityData(): LegalEntityResponse {
    return this.req;
  }

  @Output()
  onContactCreated: EventEmitter<Contacts> = new EventEmitter<Contacts>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  departments=[];

  openModels = { legalEntityModel: false, accountModel: false };
  constructor(
    private spinner: NgxSpinnerService, private bordingservice: BoardingLoadingService,
    private loginservice: Loginservices, private _accountService: AccountService,
    private _contactService: ContactsService, private _countryDetailsService: CountryDetailsServices,
    private _warehouseService: WarehouseService, private _expenseManagementService: ExpenseManagementService
  ) { }

  isAccountOpen: boolean;
  public _isEditMode: boolean;
  flag: boolean;
  message: string;
  accountForList: Array<AccountsForList> = [];
  newcontact: Contacts = new Contacts();
  contactId;
  _countryDetails: Array<CountryDetails> = [];
  _billingState: Array<StateProvinceDetails> = [];
  boardingList: Array<BoardingLoadingForListResponse> = [];
  legalEntityList: Array<ServiceCenterForList> = [];
  sid;
  postBoardingLoading: BoardingLoading = new BoardingLoading();
  contactsList2: Contacts = new Contacts();
  loginData;
  accountPage: Page<AccountCreate> = new Page<AccountCreate>();
  accountList: Array<AccountCreate> = [];
  stateByCountryId: Array<StateProvinceDetails> = [];
  classRequiredLE: string='col-md-6 col-sm-6 form-group';
  classRequiredDept: string='col-md-6 col-sm-6 form-group';
  classRequiredDesig: string='col-md-6 col-sm-6 form-group';
  classRequiredGrade: string='col-md-6 col-sm-6 form-group';
  classRequiredCode: string='col-md-6 col-sm-6 form-group';
  classRequiredBusPlace: string='col-md-6 col-sm-6 form-group';
  CodeName: string;
  typeForlist: Array<ContactType> = [];
  designationForlist: Array<ContactDesignation> = [];
  reqBoarding: BoardingLoadingDetailsRequest = new BoardingLoadingDetailsRequest();

  faPlus: string = 'btn btn-primary fa fa-plus';
  req: LegalEntityResponse = new LegalEntityResponse();
  heading: string;
  CREATE_LegalEntity_HEADING: string = 'New Legal Entity';
  createContactModel: ModalDirective;
  createAccount: AccountCreate = new AccountCreate();
  CREATE_ACCOUNT_HEADING: string = 'New Account';
  popupNgClass: string = 'alert alert-danger center center-block';
  businessPlaceForList: Array<ExpenseManagementBusinessPlace> = [];
  createBoarding: BoardingLoadingDetailsResponse = new BoardingLoadingDetailsResponse();
  istrue: boolean;
  isEditModeBoarding: boolean;
  boadingNameTamp: string;
  boadingIdTamp: number;
  idd: number;
  name: string;
  isEditModeLegaEntity: boolean;
  isEditModeAcount: boolean;

  @ViewChild('createContactModel', { static: false })
  @ViewChild('createLegalEntityModel', { static: false }) createLegalEntityModel: ModalDirective;
  @ViewChild('GradeModel', { static: false }) GradeModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createAcountModel', { static: false }) createAcountModel: ModalDirective;

  accountCreate = new AccountCreate();
  countryReq:string='col-md-6 col-sm-6 form-group';
  ngOnInit() {
    this.getLoggedInUser();
    this.getAccountList();
    this.getBoardingList();
    this.getAllCountryDetails();
    this.billingCountry();
    this.getLegalEntityForList();
    // this.classRequiredLE = 'col-md-6 col-sm-6 form-group';
    // this.classRequiredDept = 'col-md-6 col-sm-6 form-group';
    // this.classRequiredDesig = 'col-md-6 col-sm-6 form-group';
    // this.classRequiredGrade = 'col-md-6 col-sm-6 form-group';
    this.getContactType();
    // this.getContactDesignation();
    // this.changeNgRequired();
    this.getBusinessPlaceList();
    this.getDepartments();
  }

  getContactType() {
    this._contactService.getTypeForList().subscribe(data => {
      this.typeForlist = data;
    });
  }

  getContactDesignation() {
    if(this.newcontact.contactTypeId == 1){
      this.spinner.show();
      this._contactService.getDesignationForlist().subscribe(data => {
        this.spinner.hide();
        this.designationForlist = data;
      });
    }else{
      this.designationForlist = [];
    }
    
    
  }

  getAccountList() {
    this.spinner.show();
    this._accountService.getAccountsForList().subscribe(data => {
      this.accountForList = data;
      this.spinner.hide();
    });
  }

  cancel() {
    this.newcontact = new Contacts();
  }

  getAllCountryDetails() {
    this._countryDetailsService.getAllCountry().subscribe(data => (this._countryDetails = data));
  }

  billingCountry() {
    if (this.newcontact.contactMallingCountryId !== ''&&this.newcontact.contactMallingCountryId !==undefined && this.newcontact.contactMallingCountryId !==null) {
      this.spinner.show();
      this._countryDetailsService
        .getStateByCountryCode(this.newcontact.contactMallingCountryId).subscribe(data => {
          this._billingState = data;
          this.spinner.hide();
        });
    }
  }

  getBoardingList() {
    this._contactService.getBoardingForList().subscribe(data => {
      this.boardingList = data;
    });
  }

  getLegalEntityForList() {
    this._warehouseService.getLegalEntityForList().subscribe(data => {
      this.legalEntityList = data;
    });
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  cancelData() {
    this.newcontact = new Contacts();
    this.onCancle.emit('cancled');
  }
  space:boolean=false;
  countSpace:Array<any> = [];
  spaeArray:Array<any>=[];
   AvoidSpace(key) {
    if (key.code === 'Space') {
   this.countSpace.push(key.code);
   this.spaeArray=this.countSpace;
      this.space=true
  }
  // else if(key.code!==' '){
  //   console.log("not space here")
  //   const index = this.countSpace.indexOf(key.code[0]);
  //   this.countSpace.splice(key.code);
  // }

}
  createContacts() {
    // this.countSpace.push(this.newcontact.contactFirstName)
    // console.log(this.countSpace);
    // for(let i=0;i<=this.countSpace.length;i++){
    //   if(this.countSpace[i]==" "){
    //     this.message='Space not in first Name';
    //       }
    // }
    this.flag = false;
    if (FieldValidation.isEmpty(this.newcontact.accountSAPNumber)) {
      this.message = 'Please fill Account Name !';
    } else if (this.newcontact.contactTypeId === 0) {
      this.message = 'Please fill Contact Type !';
    } else if (this.newcontact.contactTypeId === 1 && FieldValidation.isEmpty(this.newcontact.employeeCode)) {
      this.message = 'Please fill Employee Code !';
    } else if (this.newcontact.contactTypeId === 3 && FieldValidation.isEmpty(this.newcontact.employeeCode)) {
      this.message = 'Please fill Vendor Code !';
    } else if (!FieldValidation.isEmpty(this.newcontact.employeeCode) && (this.newcontact.employeeCode).length > 200) {
      this.message = 'Employee Code exceeds 200 characters !';
    } else if (FieldValidation.isEmpty(this.newcontact.salutation)) {
      this.message = 'Please fill Salutation !';
    } else if (FieldValidation.isEmpty(this.newcontact.contactFirstName)) {
      this.message = 'Please fill First Name !';
    }
    else if(this.newcontact.contactFirstName.match(" ")){  
      this.message='Space is not valid in First Name';
    }

    else if (!FieldValidation.isValidName(this.newcontact.contactFirstName)) {
      this.message = 'Please fill valid First Name !';
    } else if (!FieldValidation.isEmpty(this.newcontact.contactMiddleName) && (this.newcontact.contactMiddleName).length > 200 && this.newcontact.contactMiddleName.match(" ") ) {
      this.message = 'First Name exceeds 200 characters';
    } else if (FieldValidation.isEmpty(this.newcontact.contactLastName)) {
      this.message = 'Please fill Last Name !';
    } else if (!FieldValidation.isValidName(this.newcontact.contactLastName)) {
      this.message = 'Please fill valid Last Name !';
    }
    else if(this.newcontact.contactLastName.match(" ")){  
      this.message='Space is not valid in Last Name';
    }
     else if (FieldValidation.isEmpty(this.newcontact.contactMobileNumber)) {
      this.message = 'Please fill Mobile Number !';
    } else if (FieldValidation.isValidMobileNumber(this.newcontact.contactMobileNumber) === false) {
      this.message = 'Please fill valid Mobile Number !';
    } else if (this.newcontact.contactPhoneNumber !==undefined && this.newcontact.contactPhoneNumber !== '' &&
      FieldValidation.isValidMobileNumber(this.newcontact.contactPhoneNumber) === false) {
      this.message = 'Please fill valid Phone Number !';
    } else if (!FieldValidation.isEmpty(this.newcontact.contactFaxNumber) && (this.newcontact.contactFaxNumber).length > 15) {
      this.message = 'Fax Number exceeds maximum limit';
    } 
    // else if(( this.newcontact.contactEmailId!=undefined ||this.newcontact.contactEmailId!=null)&&(FieldValidation.isEmpty(this.newcontact.contactEmailId))){
    //   this.message='Please fill Valid Email Id';
    // }
    else if ( this.emailtrue==true &&  FieldValidation.isEmpty(this.newcontact.contactEmailId)) {
      this.message = 'Please fill Email';
    }
    else if(this.newcontact.contactEmailId!==undefined && this.newcontact.contactEmailId!==null && this.newcontact.contactEmailId!=='' && (!FieldValidation.isValidEmail(this.newcontact.contactEmailId))){
      this.message='Please fill valid Email id'
    } 
    // else if (this.newcontact.contactEmailId!==undefined|| !FieldValidation.isEmpty(this.newcontact.contactEmailId)&&
    //   (!FieldValidation.isValidEmail(this.newcontact.contactEmailId))) {
    //   this.message = 'Please fill valid Email Id !';
    // } 
    else if (this.newcontact.contactTypeId === 1 && (FieldValidation.isEmpty(this.newcontact.contactDepartment))) {
      this.message = 'Please fill Department !';
    } else if (!FieldValidation.isEmpty(this.newcontact.contactDepartment) && (this.newcontact.contactDepartment).length > 200) {
      this.message = 'Department exceeds 200 characters';
    } else if (this.newcontact.contactTypeId === 1 && (this.newcontact.contactDesignationId === 0)) {
      this.message = 'Please fill designation !';
    } else if (this.newcontact.contactTypeId !== 2 && (this.newcontact.businessPlaceId === 0)) {
      this.message = 'Please select Business Place !';
    } else if ((this.newcontact.contactTypeId===1) && FieldValidation.isEmpty(this.newcontact.contactMallingAddress)) {
      this.message = 'Please fill Address !';
    } else if (this.newcontact.contactTypeId===1 && !FieldValidation.isEmpty(this.newcontact.contactMallingAddress) && (this.newcontact.contactMallingAddress).length > 200) {
      this.message = 'Address exceeds 200 characters!';
    } else if (!FieldValidation.isEmpty(this.newcontact.contactMallingStreet) && (this.newcontact.contactMallingStreet).length > 200) {
      this.message = 'Street exceeds 200 characters!';
    } else if (!FieldValidation.isEmpty(this.newcontact.contactMallingCity) && (this.newcontact.contactMallingCity).length > 200) {
      this.message = 'City exceeds 200 characters!';
    } else if (this.newcontact.contactTypeId===1 && FieldValidation.isEmpty(this.newcontact.contactMallingCountryId)) {
      this.message = 'Please fill Mailing country !';
    } else if (this.newcontact.contactTypeId===1 && this.newcontact.contactMallingStateId === null) {
      this.message = 'Please fill Mailing State !';
    } else if (this.newcontact.contactTypeId===1 && FieldValidation.isValidPostalCodeMendatory(this.newcontact.contactMallingPincode) === false) {
      this.message = 'Please fill valid Pin Code !';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this.newcontact.contactOwnerFirstName = this.loginData.firstName;
        this.newcontact.contactOwnerLastName = this.loginData.lastName;
        this.newcontact.contactOwnerEmailId = this.loginData.emailId;
        this._contactService.postContacts(this.newcontact).subscribe(
          data => {
            this.idd = data.contactid;
            if (data.contactMiddleName === undefined || data.contactMiddleName === null) {
              this.name = data.contactFirstName + ' ' + data.contactLastName;
            } else {
              this.name = data.contactFirstName + ' ' + data.contactMiddleName + ' ' + data.contactLastName;
            }
            this.message = 'Created Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.onContactCreated.emit(data);
            this.spinner.hide();
            this.popupSuccessModel.show();
          }, err => {
            this.spinner.hide();
            this.popupNgClass = 'alert alert-danger center center-block';
            this.message = err.error.message;
            this.popupSuccessModel.show();
          });
      } else {
        this.spinner.show();
        this._contactService.editContacts(this.newcontact.contactid, this.newcontact).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onContactCreated.emit(data);
        }, err => {
          this.spinner.hide();
          this.popupNgClass = 'alert alert-danger center center-block';
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      // this.newcontact = new Contacts();
    }
    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }
  notReqDep:boolean=false;
 requiredField:boolean=false;
 addrReq:string='col-md-6 col-sm-6 form-group';
 statRq:string='col-md-6 col-sm-6 form-group';
 pinRq:string='col-md-6 col-sm-6 form-group';
 emailReq='col-md-6 col-sm-6 form-group';
 emailtrue:boolean=false;
  changeNgRequired() {
    if (this.newcontact.contactTypeId === 1) {
      this.classRequiredDept = 'col-md-6 col-sm-6 form-group required';
      this.classRequiredDesig = 'col-md-6 col-sm-6 form-group required';
      this.classRequiredGrade = 'col-md-6 col-sm-6 form-group required';
      this.countryReq='col-md-6 col-sm-6 form-group required'
      this.addrReq='col-md-6 col-sm-6 form-group required';
      this.statRq='col-md-6 col-sm-6 form-group required';
      this.pinRq='col-md-6 col-sm-6 form-group required';
      this.emailReq='col-md-6 col-sm-6 form-group required';
      this.emailtrue=true;
      this.requiredField=true;
      this.notReqDep=false;
      this.newcontact.contactDesignationId = 1;
    } else {
      this.requiredField=false;
      this.classRequiredDept = 'col-md-6 col-sm-6 form-group';
      this.classRequiredDesig = 'col-md-6 col-sm-6 form-group';
      this.classRequiredGrade = 'col-md-6 col-sm-6 form-group';
      this.countryReq='col-md-6 col-sm-6 form-group';
      this.addrReq='col-md-6 col-sm-6 form-group';
      this.statRq='col-md-6 col-sm-6 form-group';
      this.pinRq='col-md-6 col-sm-6 form-group';
      this.emailReq='col-md-6 col-sm-6 form-group';
      this.emailtrue=false;
      this.notReqDep=true;
    }
    if (this.newcontact.contactTypeId === 2) {
      this.classRequiredLE = 'col-md-6 col-sm-6 form-group';
      this.classRequiredCode = 'col-md-6 col-sm-6 form-group';
      this.classRequiredBusPlace = 'col-md-6 col-sm-6 form-group';
      this.emailReq='col-md-6 col-sm-6 form-group';
      this.emailtrue=false;
      this.requiredField=false;
    } else {
      this.requiredField=true;
      this.classRequiredLE = 'col-md-6 col-sm-6 form-group required';
      this.classRequiredCode = 'col-md-6 col-sm-6 form-group required';
      this.classRequiredBusPlace = 'col-md-6 col-sm-6 form-group required';
      
    }
    if (this.newcontact.contactTypeId === 3) {
      this.CodeName = 'Vendor Code';
      this.emailtrue=true;
      this.emailReq='col-md-6 col-sm-6 form-group required';
    }
    else if(this.newcontact.contactTypeId==2){
      this.emailtrue=false;
      this.CodeName='Customer code';
      this.emailReq='col-md-6 col-sm-6 form-group';
    }
     else {
       this.emailtrue=true;
      this.CodeName = 'Employee Code';
      this.emailReq='col-md-6 col-sm-6 form-group required';
    }
    this.getContactDesignation();
  }

  onEntityCreated($event: LegalEntityResponse) {
    const temp = new ServiceCenterForList();
    temp.legalEntityId = $event.legalentityId;
    temp.legalEntityName = $event.legalEntityName;
    this.legalEntityList.push(temp);
  }

  createAcountModelhide() {
    this.createAccount = new AccountCreate();
    this.createAcountModel.hide();
  }

  addNewLegaEntity() {
    this.req = new LegalEntityResponse();
    this.isEditModeLegaEntity = false;
    this.heading = this.CREATE_LegalEntity_HEADING;
    this.createLegalEntityModel.show();
  }

  cancelDataLegalEntity() {
    this.req = new LegalEntityResponse();
    this.isEditModeLegaEntity = false;
    this.createLegalEntityModel.hide();
  }

  addNewGrade() {
    this.postBoardingLoading = new BoardingLoading();
    this.isEditModeBoarding = false;
    // this.heading = this.CREATE_ACCOUNT_HEADING;
    this.GradeModel.show();
  }

  cancelGrade() {
    this.postBoardingLoading = new BoardingLoading();
    this.isEditMode = false;
    this.GradeModel.hide();
  }

  addNewAccount() {
    this.createAccount = new AccountCreate();
    this.isEditModeAcount = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  cancelDataAccount() {
    this.createAccount = new AccountCreate();
    this.isEditModeAcount = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.hide();
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  getBusinessPlaceList() {
    this._expenseManagementService
      .getBusinessPlaceList(2)
      .subscribe(data => {
        this.businessPlaceForList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  cancelBoarding() {
    this.GradeModel.hide();
  }

  onAccountCreated($event) {
    this.getAccountList();
    this.newcontact.accountSAPNumber = $event.sampAccountNumber;
  }

  onBoardingCreated($event) {
    this.getBoardingList();
    this.newcontact.gradeName = $event.boardingName;
  }

  onLegalEntityCreated($event) {
    this.getLegalEntityForList();
    this.newcontact.legalEntityId = $event.legalentityId;
  }
  // shagufta
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
  onlyLetters(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
 
    if ( charCode!==32 && (charCode < 48 || charCode > 57)) {
      return true;
    }
    return false;
    
   
  }
  LimteExceed:boolean;
  onKey(event):any {
     if(event.target.value.length>9){
       this.LimteExceed=true;
      
     }
     else{
       this.LimteExceed=false;
     }
    }
 getDepartments(){
   this._contactService.getDepartments().subscribe(data=>this.departments=data);
 }
}
