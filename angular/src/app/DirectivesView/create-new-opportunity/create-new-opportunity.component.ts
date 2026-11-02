import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { caseService } from './../../services/caseService.service';
import { DatePipe } from '@angular/common';
import { OppertunityType, OppertunityStage, OppertunityForecastCategory } from './../../services/model';
import {
  Component, OnInit, ViewChild, Input, Output, EventEmitter
} from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import {
  OppertunityRequest, OppertunityResponse, AccountsForList, AccountCreate, CaseForList, CaseResponse
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { OpportunityService } from '../../services/Opportunity.services';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-create-new-opportunity',
  templateUrl: './create-new-opportunity.component.html',
  styleUrls: ['./create-new-opportunity.component.css']
})
export class CreateNewOpportunityComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];

  constructor(
    private spinner: NgxSpinnerService,
    public datepipe: DatePipe,
    private opportunityService: OpportunityService,
    private loginservice: Loginservices,
    private _accountService: AccountService,
    private _caseService: caseService
  ) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  @Input()
  set editOpportunityData(opportunity: OppertunityRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
    this.newOpportunity = opportunity;
    if (!this._isEditMode) {
      this.newOpportunity.accountId = opportunity.accountId;
      this.newOpportunity.accountName = opportunity.accountName;
      this.newOpportunity.caseId = opportunity.caseId;
      // this.newOpportunity.caseNumber = opportunity.caseNumber;
      // this.newOpportunity.caseManualId = opportunity.caseManualId;
    }
  }

  get editOpportunityData(): OppertunityRequest {
    return this.newOpportunity;
  }
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createCaseModel') createCaseModel: ModalDirective;

  isAccountOpen: boolean;
  isCaseOpen: boolean;
  res: CaseResponse = new CaseResponse();
  createAccount: AccountCreate = new AccountCreate();
  newOpportunity: OppertunityRequest = new OppertunityRequest();
  flag: boolean;
  message: string;
  public _isEditMode: boolean;
  loginData;
  accountForList: Array<AccountsForList> = [];
  caseForList: Array<CaseForList> = [];
  accountForListAfterPush: Array<AccountsForList> = [];
  stageForList: Array<OppertunityStage> = [];
  typeForList: Array<OppertunityType> = [];
  forecastCategoryForList: Array<OppertunityForecastCategory> = [];
  isStageSelected: string;
  isTypeSelected: string;
  isForecastCategorySelected: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  heading: string;
  oppertunityResponse = new OppertunityResponse();
  CREATE_ACCOUNT_HEADING: string = 'New Account';
  CREATE_CASE_HEADING: string = 'New Case';
  isAccountSelected: string;
  noMatchAccount: string;
  isCaseSelected: string;
  noMatchCase: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempOppoId: number;
  tempOppoName: number;
  today = new Date();

  @ViewChild('createAcountModel')
  createAcountModel: ModalDirective;

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  // tslint:disable-next-line: no-output-on-prefix
  // tslint:disable-next-line: member-ordering
  @Output()
  onOpportunityCreated: EventEmitter<OppertunityResponse> = new EventEmitter<OppertunityResponse>();
  isEditModeAcount: boolean;
  isEditModeCase: boolean;

  ngOnInit() {
    this.getAccountList();
    this.getCaseList();
    this.getLoggedInUser();
    this.getOppertunityStage();
    this.getOppertunityType();
    this.getOppertunityForecastCategory();
  }

  // FORLISTS STARTS
  getAccountList() {
    this._accountService.getAccountsForList().subscribe(data => {
      this.accountForList = data;
    });
  }

  getCaseList() {
    this._caseService.getCaseForList().subscribe(data => {
      this.caseForList = data;
    });
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  getOppertunityStage() {
    this.opportunityService.getOppertunityStage().subscribe(data => {
      this.stageForList = data;
    });
  }

  getOppertunityType() {
    this.opportunityService.getOppertunityType().subscribe(data => {
      this.typeForList = data;
    });
  }

  getOppertunityForecastCategory() {
    this.opportunityService.getOppertunityForecastCategory().subscribe(data => {
      this.forecastCategoryForList = data;
    });
  }
  // FORLISTS ENDS
  // if ((this.newOpportunity.caseId === null || this.newOpportunity.caseId === 0)) {
  //       this.message = 'Please fill Case';
  //     } else if (this.newOpportunity.accountId === null || this.newOpportunity.accountId === 0) {
  //       this.message = 'Please fill Account Name';
  //     } else 

  createOpportunity() {
    // this.newOpportunity.closeDate = this.datepipe.transform(this.newOpportunity.closeDate, 'yyyy-MM-dd');
    this.flag = false;
    if (this.newOpportunity.oppertunityTypeId === 0) {
      this.message = 'Please fill Type';
    } else if (this.newOpportunity.oppertunityStageId === 0) {
      this.message = 'Please fill Stage';
    } else if (this.newOpportunity.forcastCategoryId === 0) {
      this.message = 'Please fill Forecast Category';
    } else if (!FieldValidation.isEmpty(this.newOpportunity.probability) && (!FieldValidation.isValidCurrency
      (this.newOpportunity.probability) || !FieldValidation.isValidNumber
        (this.newOpportunity.probability) || (this.newOpportunity.probability).length > 10)) {
      this.message = 'Please fill valid probability';
    } else if (!FieldValidation.isEmpty(this.newOpportunity.ammount) && (!FieldValidation.isValidCurrency
      (this.newOpportunity.ammount) || (this.newOpportunity.ammount).length > 8)) {
      this.message = 'Please fill valid amount';
    } else if (this.newOpportunity.closeDate !== null && this.newOpportunity.closeDate > this.today) {
      this.message = 'Please fill valid date';
    } else if (this._isEditMode === true && !FieldValidation.isEmpty(this.newOpportunity.siteName) &&
      (this.newOpportunity.expectedRevenue).length > 200) {
      this.message = 'Site Name exceeds 200 characters';
    } else if (this._isEditMode === true && (!FieldValidation.isEmpty(this.newOpportunity.expectedRevenue) &&
      !FieldValidation.isValidNumber(this.newOpportunity.expectedRevenue))) {
      this.message = 'Please fill valid Expected Revenue';
    } else if (this._isEditMode === true && (!FieldValidation.isEmpty(this.newOpportunity.expectedRevenue) &&
      (this.newOpportunity.expectedRevenue).length > 8)) {
      this.message = 'Expected Revenue exceeds maximum limit';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this.opportunityService.postOpportunity(this.newOpportunity).subscribe(data => {
          this.tempOppoId = data.oppertunityId;
          this.tempOppoName = data.oppertunityNumber;
          this.message = 'Created Successfully';
          this.onOpportunityCreated.emit(data);
          this.spinner.hide();
          this.popupNgClass = 'alert alert-success center center-block';
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner.show();
        this.opportunityService.putOpportunity(this.newOpportunity).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onOpportunityCreated.emit(data);
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
    }
    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }

  cancelData() {
    this.onCancle.emit('cancled');
  }

  getAccountById() {
    this._accountService
      .getAccount(this.createAccount.sampAccountNumber)
      .subscribe(data => {
      });
  }

  getOpportunityDetails() {
    this.spinner.show();
    this.opportunityService.getOpportunityDetails(this.newOpportunity.oppertunityId).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.oppertunityResponse = data;
    });
  }

  // addNewAccount() {
  //   this.createAccount = new AccountCreate();
  //   this.isEditModeAcount = false;
  //   this.heading = this.CREATE_ACCOUNT_HEADING;
  //   this.createAcountModel.show();
  // }

  cancelData2() {
    this.createAccount = new AccountCreate();
    this.isEditMode = false;
    this.createAcountModel.hide();
  }

  // addNewCase() {
  //   this.res = new CaseResponse();
  //   this.isEditModeCase = false;
  //   this.heading = this.CREATE_CASE_HEADING;
  //   this.createCaseModel.show();
  // }

  cancelDatacase() {
    this.isEditModeCase = false;
    this.createCaseModel.hide();
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  onAccountCreated($event) {
    const newAccountsList = [];
    this.accountForList.forEach(data => {
      newAccountsList.push(data);
    });
    newAccountsList.push($event);
    this.accountForList = newAccountsList;
    this.newOpportunity.accountId = $event.sampAccountNumber;
    this.newOpportunity.accountName = $event.accountName;
  }

}
