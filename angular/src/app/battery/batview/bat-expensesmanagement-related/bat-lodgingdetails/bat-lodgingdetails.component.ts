import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { LodgingExpenseService } from '../../../../services/LodgingExpenseService';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BtlodgingExpenseService } from '../../../bat_services/btlodging-expense.service';
import { BtLodgingExpenseRequest, BtLodgingExpenseResponse } from '../../../bt_model';

@Component({
  selector: 'app-bat-lodgingdetails',
  templateUrl: './bat-lodgingdetails.component.html',
  styleUrls: ['./bat-lodgingdetails.component.css']
})
export class BatLodgingdetailsComponent implements OnInit {

  
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditModeLodging: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  lodgingExpenseRequest: BtLodgingExpenseRequest = new BtLodgingExpenseRequest();
  lodgingExpenseResponse: BtLodgingExpenseResponse = new BtLodgingExpenseResponse();
  isLodgingOpen: boolean;
  _cFlag: string;

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createLodgingModel') createLodgingModel: ModalDirective;

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
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private datePipe: DatePipe,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _lodgingExpenseService: BtlodgingExpenseService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => { 
      this.lodgingExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.lodgingExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'LodgingExpense';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
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

  ngOnInit() {
    this.getLodgingExpenseDetails();
  }

  getLodgingExpenseDetails() {
    this.spinner.show();
    this._lodgingExpenseService.getLodgingExpenseDetails(this.lodgingExpenseResponse.id).subscribe((data) => {
      this.lodgingExpenseResponse = data;
      data.toDate=this.datePipe.transform(data.toDate,'dd-MM-yyyy');
      data.toTime=data.toDate+' '+data.toTime;
      data.fromDate=this.datePipe.transform(data.fromDate,'dd-MM-yyyy');
      data.fromTime=data.fromDate+' '+data.fromTime;
      this.spinner.hide();
    });
  }

  cancelLodging() {
    this.createLodgingModel.hide();
    this.isEditModeLodging = false;
    this.lodgingExpenseRequest = new BtLodgingExpenseRequest();
  }

  onClickEditLodging(id) {
    this.lodgingExpenseRequest = new BtLodgingExpenseRequest();
    this.spinner.show();
    this._lodgingExpenseService.getLodgingExpenseDetails(id).subscribe((data) => {
      // this.lodgingExpenseRequest = data;
      this.lodgingExpenseRequest.id = data.id;
      this.lodgingExpenseRequest.btExpenseManagementId = data.btExpenseManagementId;
      this.lodgingExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
      this.lodgingExpenseRequest.amount = data.amount;
      this.lodgingExpenseRequest.amountToDisbursed = data.amountToDisbursed;
      this.lodgingExpenseRequest.approvedAmount = data.approvedAmount;
      this.lodgingExpenseRequest.billNo = data.billNo;
      this.lodgingExpenseRequest.cityCategoryId = data.cityCategoryId;
      this.lodgingExpenseRequest.cityName = data.cityName;
      this.lodgingExpenseRequest.comapnyPaid = data.isComapnyPaid;
      this.lodgingExpenseRequest.from = data.fromDate;
      this.lodgingExpenseRequest.fromDate = data.fromDate;
      this.lodgingExpenseRequest.fromTime = data.fromTime;
      this.lodgingExpenseRequest.hotelName = data.hotelName;
      this.lodgingExpenseRequest.isComapnyPaid = data.isComapnyPaid
      this.lodgingExpenseRequest.isSelfArrangement = data.isSelfArrangement;
      this.lodgingExpenseRequest.manualId = data.manualId;
      this.lodgingExpenseRequest.otherCity = data.otherCity;
      this.lodgingExpenseRequest.remark = data.remark;
      this.lodgingExpenseRequest.selfArrangement = data.isSelfArrangement;
      this.lodgingExpenseRequest.to = data.toDate;
      this.lodgingExpenseRequest.toDate = data.toDate;
      this.lodgingExpenseRequest.toTime = data.toTime;
      this.spinner.hide();
    });
    this.isEditModeLodging = true;
    this.createLodgingModel.show();
    // this.spinner.hide();
  }

  onLodgingCreated($event) {
    this.lodgingExpenseResponse = $event;
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
