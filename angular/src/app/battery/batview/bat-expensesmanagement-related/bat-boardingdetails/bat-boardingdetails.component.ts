import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BtboardingExpenseService } from '../../../bat_services/btboarding-expense.service';
import { BtBoardingExpenseRequest, BtBoardingExpenseResponse } from '../../../bt_model';

@Component({
  selector: 'app-bat-boardingdetails',
  templateUrl: './bat-boardingdetails.component.html',
  styleUrls: ['./bat-boardingdetails.component.css']
})
export class BatBoardingdetailsComponent implements OnInit {

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditModeBoarding: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  boardingExpenseRequest: BtBoardingExpenseRequest = new BtBoardingExpenseRequest();
  boardingExpenseResponse: BtBoardingExpenseResponse = new BtBoardingExpenseResponse();
  isBoardingOpen: boolean;
  _cFlag: string;

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createBoardingModel') createBoardingModel: ModalDirective;

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
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private datePipe: DatePipe,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _boardingExpenseService: BtboardingExpenseService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.boardingExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.boardingExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'BoardingExpense';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getBoardingExpenseDetails();
  }

  getBoardingExpenseDetails() {
    this.spinner.show();
    this._boardingExpenseService.getBoardingExpenseDetails(this.boardingExpenseResponse.id).subscribe((data) => {
      this.boardingExpenseResponse = data;
      data.toDate=this.datePipe.transform(data.toDate,'dd-MM-yyyy');
      data.toTime=data.toDate+' '+data.toTime;
      data.fromDate=this.datePipe.transform(data.fromDate,'dd-MM-yyyy');
      data.fromTime=data.fromDate+' '+data.fromTime;
      this.spinner.hide();
    });
  }

  cancelBoarding() {
    this.createBoardingModel.hide();
    this.isEditModeBoarding = false;
    this.boardingExpenseRequest = new BtBoardingExpenseRequest();
  }

  onClickEditBoarding(id) {
    this.boardingExpenseRequest = new BtBoardingExpenseRequest();
    this.spinner.show();
    this._boardingExpenseService.getBoardingExpenseDetails(id).subscribe((data) => {
      this.boardingExpenseRequest.id = data.id;
      this.boardingExpenseRequest.btExpenseManagementId = data.btExpenseManagementId;
      this.boardingExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
      this.boardingExpenseRequest.NoOfDayes = data.noOfDays;
      this.boardingExpenseRequest.amount = data.amount;
      this.boardingExpenseRequest.amountToDisbursed = data.amountToDisbursed;
      this.boardingExpenseRequest.cityCategoryId = data.cityCategoryId;
      this.boardingExpenseRequest.cityName = data.cityName;
      this.boardingExpenseRequest.entitlementPerDay = data.entitlementPerDay;
      this.boardingExpenseRequest.from = data.fromDate;
      this.boardingExpenseRequest.fromDate = data.fromDate;
      this.boardingExpenseRequest.fromTime = data.fromTime;
      this.boardingExpenseRequest.id = data.id;
      this.boardingExpenseRequest.manualId = data.manualId;
      this.boardingExpenseRequest.otherCity = data.otherCity;
      this.boardingExpenseRequest.remark = data.remark;
      this.boardingExpenseRequest.to = data.toDate;
      this.boardingExpenseRequest.toDate = data.toDate;
      this.boardingExpenseRequest.toTime = data.toTime;
      this.spinner.hide();
    });
    this.isEditModeBoarding = true;
    this.createBoardingModel.show();
  }

  onBoardingCreated($event) {
    this.boardingExpenseResponse = $event;
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }
}
