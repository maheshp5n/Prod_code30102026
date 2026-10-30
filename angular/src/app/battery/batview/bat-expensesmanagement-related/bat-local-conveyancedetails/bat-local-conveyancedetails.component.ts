import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BtlocalConveyanceExpenseService } from '../../../bat_services/btlocal-conveyance-expense.service';
import { BtLocalConveyanceExpenseRequest, BtLocalConveyanceExpenseResponse } from '../../../bt_model';

@Component({
  selector: 'app-bat-local-conveyancedetails',
  templateUrl: './bat-local-conveyancedetails.component.html',
  styleUrls: ['./bat-local-conveyancedetails.component.css']
})
export class BatLocalConveyancedetailsComponent implements OnInit {

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditModeLocalConveyance: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
  localConveyanceExpenseResponse: BtLocalConveyanceExpenseResponse = new BtLocalConveyanceExpenseResponse();
  isLocalConveyanceOpen: boolean;
  _cFlag: string;

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createLocalConveyanceModel', { static: false }) createLocalConveyanceModel: ModalDirective;

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
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private localConveyanceService: BtlocalConveyanceExpenseService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.localConveyanceExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.localConveyanceExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'LocalConveyanceExpense';
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
    this.getLocalConveyanceExpenseDetails();
  }

  getLocalConveyanceExpenseDetails() {
    this.spinner.show();
    this.localConveyanceService.getLocalConveyanceExpenseDetails(this.localConveyanceExpenseResponse.id).subscribe((data) => {
      this.localConveyanceExpenseResponse = data;
      this.spinner.hide();
    });
  }

  cancelLocalConveyance() {
    this.createLocalConveyanceModel.hide();
    this.isEditModeLocalConveyance = false;
    this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
  }

  onClickEditLocalConveyance(id) {
    this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
    this.spinner.show();
    this.localConveyanceService.getLocalConveyanceExpenseDetails(id).subscribe((data) => {
      // this.localConveyanceExpenseRequest = data;
      this.localConveyanceExpenseRequest.id = data.id;
      this.localConveyanceExpenseRequest.btExpenseManagementId = data.btExpenseManagementId;
      this.localConveyanceExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
      this.localConveyanceExpenseRequest.ownConveyanceId = data.ownConveyanceId;
      this.localConveyanceExpenseRequest.amount = data.amount;
      this.localConveyanceExpenseRequest.amountToDisbursed = data.amountToDisbursed;
      this.localConveyanceExpenseRequest.date = data.date;
      this.localConveyanceExpenseRequest.distance = data.distance;
      this.localConveyanceExpenseRequest.fromPlace = data.fromPlace;
      this.localConveyanceExpenseRequest.manualId = data.manualId;
      this.localConveyanceExpenseRequest.modeOfTravelId = data.modeOfTravelId;
      this.localConveyanceExpenseRequest.ownConveyanceId = data.ownConveyanceId;
      this.localConveyanceExpenseRequest.remark = data.remark;
      this.localConveyanceExpenseRequest.toPlace =data.toPlace;
      this.spinner.hide();
    });
    this.isEditModeLocalConveyance = true;
    this.createLocalConveyanceModel.show();
  }

  onLocalConveyanceCreated($event) {
    this.localConveyanceExpenseResponse = $event;
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
