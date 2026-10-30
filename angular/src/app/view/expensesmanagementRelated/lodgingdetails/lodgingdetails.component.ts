import { LodgingExpenseService } from './../../../services/LodgingExpenseService';
import { ActionHistoryObjectAndId, LodgingExpenseRequest, LodgingExpenseResponse } from '../../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../../services/login.services';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-lodgingdetails',
  templateUrl: './lodgingdetails.component.html',
  styleUrls: ['./lodgingdetails.component.css']
})

export class LodgingdetailsComponent implements OnInit {

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
  lodgingExpenseRequest: LodgingExpenseRequest = new LodgingExpenseRequest();
  lodgingExpenseResponse: LodgingExpenseResponse = new LodgingExpenseResponse();
  isLodgingOpen: boolean;
  _cFlag: string;

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createLodgingModel', { static: false }) createLodgingModel: ModalDirective;

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
  constructor(private datePipe: DatePipe,private spinner: NgxSpinnerService, private lodgingService: LodgingExpenseService, private _activatedRoute: ActivatedRoute,
    private _lodgingExpenseService: LodgingExpenseService, private loginService: Loginservices) {
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
    this.lodgingExpenseRequest = new LodgingExpenseRequest();
  }

  onClickEditLodging(id) {
    this.lodgingExpenseRequest = new LodgingExpenseRequest();
    this.spinner.show();
    this.lodgingService.getLodgingExpenseDetails(id).subscribe((data) => {
      this.lodgingExpenseRequest = data;
      this.lodgingExpenseRequest.id = data.id;
      this.lodgingExpenseRequest.expenseManagementId = data.expenseManagementId;
      this.lodgingExpenseRequest.expenseManagementManualId = data.expenseManagementManualId;
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
