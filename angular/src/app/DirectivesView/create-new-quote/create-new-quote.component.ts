import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { FieldValidation } from './../../services/validation.services';
import { CountryDetailsServices } from './../../services/countrydetails.services';
import { CountryDetails, StateProvinceDetails, QuoteStatus } from './../../services/model';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { QuoteRequest, QuoteResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { QuoteService } from '../../services/Quote.services';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-create-new-quote',
  templateUrl: './create-new-quote.component.html',
  styleUrls: ['./create-new-quote.component.css']
})
export class CreateNewQuoteComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];

  constructor(private spinner: NgxSpinnerService, private _quoteService: QuoteService,
    private loginservice: Loginservices, private _countryDetailsService: CountryDetailsServices) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  @Input()
  set isEditModeQuote(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditModeQuote(): boolean {
    return this._isEditMode;
  }
opportunityManaulId:number;
@Input()
set BillingcountryID(biilincountryid:string){
  this.quoteRequest.billingCountryId=biilincountryid;
  if (this.quoteRequest.billingCountryId !== null) {
    this.spinner.show();
    this._countryDetailsService.getStateByCountryCode(this.quoteRequest.billingCountryId).subscribe(data=>{
        this._billingState = data;
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }
}
get BillingcountryID():string{
  return this.quoteRequest.billingCountryId;
}
  @Input()
  set editQuoteData(quoteRequest: QuoteRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
  if(quoteRequest!==null){
    if (!this._isEditMode) {
          this.quoteRequest = quoteRequest;
          // this.billcontryid=quoteRequest.billingCountryId;
      this.quoteRequest.isApproved = quoteRequest.isApproved;
      // this.opportunityManaulId = quoteRequest.oppertunityManualId;
      this.quoteRequest.accountName = quoteRequest.accountName;
      this.quoteRequest.billingStateId=quoteRequest.billingStateId;
      this.quoteRequest.billingCountryId=quoteRequest.billingCountryId;
      this.quoteRequest.billingName=quoteRequest.accountName;
      this.quoteRequest.billingPostalCode=quoteRequest.billingPostalCode;
      // console.log("this is editmode on countryid=",this.billcontryid);


     

    } else {
      
      this.quoteRequest = quoteRequest;
    }
    this.quoteRequest.isSyncing = quoteRequest.isSyncing;
    this.quoteRequest.isApproved = quoteRequest.isApproved;
    this.quoteRequest.oppertunityId = quoteRequest.oppertunityId;
      this.quoteRequest.businessDomainId = quoteRequest.businessDomainId;
  }
    
  }

  get editQuoteData(): QuoteRequest {
    return this.quoteRequest;
  }
 

  _oppoId: number;
  caseManualId:string;
  @Input()
  set opportunityId(oppoId: number) {
    this.opportunityManaulId = oppoId;
    // this.quoteRequest.oppertunityId = this._oppoId;
  }

  get opportunityId(): number {
    return this.opportunityManaulId;
  }
  @Input()
  set caseId(caseManualId:string){
    this.caseManualId=caseManualId;
  }
  get caseId():string{
    return this.caseManualId
  }

  @Output()
  _onQuoteCreated: EventEmitter<QuoteRequest> = new EventEmitter<QuoteRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  quoteRequest: QuoteRequest = new QuoteRequest();
  quoteResponse: QuoteResponse = new QuoteResponse();
  flag: boolean;
  message: string;
  _isEditMode: boolean;
  loginData;
  popupNgClass: string = 'alert alert-danger center center-block';
  _countryDetails: Array<CountryDetails> = [];
  _billingState: Array<StateProvinceDetails> = [];
  _shippingState: Array<StateProvinceDetails> = [];
  statusList: Array<QuoteStatus> = [];
  syncing: boolean;
  approved: boolean;
  today = new Date();

  tempServiceId: number;
  tempServiceName: string;

  ngOnInit() {
    this.getLoggedInUser();
    this.getAllCountryDetails();
    this.getQuoteStatus();
    if (this._isEditMode) {
      // this.billingCountry(); chanse by shagufta 21-05-2021
      // this.shippingCountry(); chanse by shagufta 21-05-2021
    }
  }

  cancelData() {
    this.onCancle.emit('cancled');
    this.quoteRequest = new QuoteRequest();
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.quoteRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
  }
  //   else if(this.quoteRequest.quoteStatusId === 0 || this.quoteRequest.quoteStatusId === null) {
  //   this.message = 'Please fill Quote Status';
  // }

  createQuote() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.quoteRequest.quoteName)) {
      this.message = 'Please fill Quote Name';
    } else if (!FieldValidation.isEmpty(this.quoteRequest.quoteName) && (this.quoteRequest.quoteName).length > 200) {
      this.message = 'Quote Name exceeds 200 characters';
    } else if (!FieldValidation.isEmpty(this.quoteRequest.validityDays) &&
      (!FieldValidation.isValidCurrency(this.quoteRequest.validityDays))) {
      this.message = 'Please fill valid value for Validity Days';
    } else if ((this.quoteRequest.validityDays !== null
      &&
      (this.quoteRequest.validityDays).length > 3)) {
      this.message = 'Validity Days exceeds maximum limit';
      // else if (this.quoteRequest.billingPostalCode !== '') {
      //   if (FieldValidation.isValidPostalCodeMendatory(this.quoteRequest.billingPostalCode) === false) {
      //     this.message = 'Please fill Valid Billing Zip Code';
      //   }
      // } else if (this.quoteRequest.shippingPostalCode !== '') {
      //   if (FieldValidation.isValidPostalCodeMendatory(this.quoteRequest.shippingPostalCode) === false) {
      //     this.message = 'Please fill Valid Shipping Zip Code';
      //   }
      // }
    } else if (FieldValidation.isEmpty(this.quoteRequest.billingCountryId)) {
      this.message = 'Please fill Billing Country';
    } else if (this.quoteRequest.billingStateId === 0 || this.quoteRequest.billingStateId === null) {
      this.message = 'Please fill Billing State';
    } else if ((this.quoteRequest.toEmailIds) === '') {
      this.message = 'Please fill Emails recipient(to)';
    } else if (!FieldValidation.isEmpty(this.quoteRequest.toEmailIds) && (this.quoteRequest.toEmailIds).length > 500) {
      this.message = 'Emails recipient(to) exceeds maximum limit';
    } else if ((this.quoteRequest.ccEmailIds) === '') {
      this.message = 'Please fill Emails recipient(cc)';
    } else if (!FieldValidation.isEmpty(this.quoteRequest.ccEmailIds) && (this.quoteRequest.ccEmailIds).length > 500) {
      this.message = 'Emails recipient(cc) exceeds maximum limit';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        
        this._quoteService.postQuote(this.quoteRequest).subscribe(data => {
          this.tempServiceName = data.quoteName;
          this.tempServiceId = data.id;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this._onQuoteCreated.emit(data);
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
        this._quoteService.putQuote(this.quoteRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this._onQuoteCreated.emit(data);
        }, err => {
          this.spinner.hide();
          this.popupNgClass = 'alert alert-danger center center-block';
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      // this.quoteRequest = new QuoteRequest();
    } if (!this.flag) {
      this.popupSuccessModel.show();
    }
  }

  sameAsBillingAddress(event) {
    if (event.target.checked) {
      this.quoteRequest.shippingName = this.quoteRequest.billingName;
      this.quoteRequest.shippingCountryId = this.quoteRequest.billingCountryId;
      this.quoteRequest.shippingStreet = this.quoteRequest.billingStreet;
      this.quoteRequest.shippingPostalCode = this.quoteRequest.billingPostalCode;
      this.shippingCountry();
      if (this._shippingState) {
        this.quoteRequest.shippingStateId = this.quoteRequest.billingStateId;
      }
      this.quoteRequest.shippingCity = this.quoteRequest.billingCity;
    } else {
      this.quoteRequest.shippingName = '';
      this.quoteRequest.shippingCountryId = null;
      this.quoteRequest.shippingStreet = '';
      this.quoteRequest.shippingPostalCode = '';
      this.quoteRequest.shippingStateId = 0;
      this.quoteRequest.shippingCity = '';
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  getAllCountryDetails() {
    this._countryDetailsService.getAllCountry().subscribe(data => (this._countryDetails = data));
  }

  billingCountry() {
    if (this.quoteRequest.billingCountryId !== '' || this.quoteRequest.billingCountryId !== null) {
      this.spinner.show();
      this._countryDetailsService.getStateByCountryCode(this.quoteRequest.billingCountryId)
        .subscribe(data => {
          this._billingState = data;
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.popupNgClass = 'alert alert-danger center center-block';
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
    }
  }

  shippingCountry() {
    if (this.quoteRequest.shippingCountryId !== null) {
      this.spinner.show();
      this._countryDetailsService.getStateByCountryCode(this.quoteRequest.shippingCountryId)
        .subscribe(data => {
          this._shippingState = data;
        }, err => {
          this.spinner.hide();
          this.popupNgClass = 'alert alert-danger center center-block';
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      this.spinner.hide();
    }
  }

  getQuoteStatus() {
    this._quoteService.getQuoteStatus().subscribe(data => {
      this.statusList = data;
    }, err => {
      this.spinner.hide();
      this.popupNgClass = 'alert alert-danger center center-block';
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

}
