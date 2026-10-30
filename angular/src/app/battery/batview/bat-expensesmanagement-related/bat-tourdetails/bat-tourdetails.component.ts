import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BttourExpenseService } from '../../../bat_services/bttour-expense.service';
import { BtTourExpenseRequest, BtTourExpenseResponse } from '../../../bt_model';

@Component({
  selector: 'app-bat-tourdetails',
  templateUrl: './bat-tourdetails.component.html',
  styleUrls: ['./bat-tourdetails.component.css']
})
export class BatTourdetailsComponent implements OnInit {

  isTourOpen: boolean;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditMode: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  isEditModeTourExpense: boolean = false;
  tourExpenseRequest: BtTourExpenseRequest = new BtTourExpenseRequest();
  tourExpenseResponse: BtTourExpenseResponse = new BtTourExpenseResponse();
  _cFlag: string;

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

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createTourExpenseModel', { static: false }) createTourExpenseModel: ModalDirective;

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService,private datePipe: DatePipe, private _activatedRoute: ActivatedRoute,
    private _tourExpenseService: BttourExpenseService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.tourExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.tourExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'TourExpense';
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
    this.getTourExpenseDetails();
  }

  getTourExpenseDetails() {
    this.spinner.show();
    this._tourExpenseService.getTourExpenseDetails(this.tourExpenseResponse.id).subscribe((data) => {
      this.tourExpenseResponse = data;
      data.arrivalDate=this.datePipe.transform(data.arrivalDate,'dd-MM-yyyy');
      data.arrivalDateTime=data.arrivalDate+' '+data.arrivalTime;
      data.departureDate=this.datePipe.transform(data.departureDate,'dd-MM-yyyy');
      data.departuredateTime=data.departureDate+' '+data.departureTime;
      this.spinner.hide();
    });
  }

  cancelTourExpense() {
    this.isEditModeTourExpense = true;
    this.createTourExpenseModel.hide();
    this.tourExpenseRequest = new BtTourExpenseRequest();
  }

  onClickEdit(tr) {
    this.spinner.show();
    this.isEditModeTourExpense = true;
    this.tourExpenseRequest = new BtTourExpenseRequest();
    this._tourExpenseService.getTourExpenseDetails(tr.id).subscribe(data => {
      this.tourExpenseRequest.id = data.id;
      this.tourExpenseRequest.btExpenseManagementId = data.btExpenseManagementId;
      this.tourExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
      this.tourExpenseRequest.modeOfTravelId = data.modeOfTravelId;
      this.tourExpenseRequest.ownConveyanceId = data.ownConveyanceId;
      this.tourExpenseRequest.departureDate = data.departureDate;
      this.tourExpenseRequest.departureTime = data.departureTime;
      this.tourExpenseRequest.arrivalDate = data.arrivalDate;
      this.tourExpenseRequest.arrivalTime = data.arrivalTime;
      this.tourExpenseRequest.fromPlace = data.fromPlace;
      this.tourExpenseRequest.toPlace = data.toPlace;
      this.tourExpenseRequest.companyPaid = data.companyPaid;
      this.tourExpenseRequest.fare = data.fare;
      this.tourExpenseRequest.approvedAmount = data.approvedAmount;
      this.tourExpenseRequest.distance = data.distance;
      this.tourExpenseRequest.remark = data.remark;
      this.tourExpenseRequest.manualId = data.manual_id;
      this.spinner.hide();
    }, err => {
      this.message = err.status + ' : ' + err.message;
      this.spinner.hide();
    });
    this.createTourExpenseModel.show();
  }

  onTourExpensesCreated($event) {
    this.tourExpenseResponse = $event;
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
