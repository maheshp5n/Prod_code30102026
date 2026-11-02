import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BtotherChargesExpenseService } from '../../../bat_services/btother-charges-expense.service';
import { BtOtherChargesExpenseRequest, BtOtherChargesExpenseResponse } from '../../../bt_model';

@Component({
  selector: 'app-bat-other-chargesdetails',
  templateUrl: './bat-other-chargesdetails.component.html',
  styleUrls: ['./bat-other-chargesdetails.component.css']
})
export class BatOtherChargesdetailsComponent implements OnInit {

  otherchangeamountToDisbursed:number;
  isOtherChargesOpen: boolean;
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
  loginData:any;
  CREATE_OtherCharges_HEADING: string = 'New Other Charges';
  EDIT_OtherCharges_HEADING: string = 'Edit Other Charges';
  headingOtherCharges: string;
  isEditModeOtherCharges: boolean = false;
  otherChargesExpenseRequest: BtOtherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
  otherChargesExpenseResponse: BtOtherChargesExpenseResponse = new BtOtherChargesExpenseResponse();
  _cFlag: string;

  @ViewChild('createOtherChargesModel') createOtherChargesModel: ModalDirective;

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
    private _otherChargesExpenseService: BtotherChargesExpenseService, private loginService: Loginservices, private _global:GlobalserviceService,) {
    this._activatedRoute.params.subscribe(pram => {
      this.otherChargesExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.otherChargesExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'OtherChargesExpense';
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
    this.getOtherChargesExpenseDetails();
  }

  getOtherChargesExpenseDetails() {
    this.spinner.show();
    this._otherChargesExpenseService.getOtherChargesDetails(this.otherChargesExpenseResponse.id).subscribe((data) => {
      this.otherChargesExpenseResponse = data;
      this.spinner.hide();
    });
  }
  onClickEditOtherCharges(id) {
    this.otherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
    this.spinner.show();
    this._otherChargesExpenseService.getOtherChargesDetails(id).subscribe((data) => {
      // this.otherChargesExpenseRequest = data;
      this.otherChargesExpenseRequest.id = data.id;
      this.otherChargesExpenseRequest.btExpenseManagementId = data.btExpenseManagementId;
      this.otherChargesExpenseRequest.expenseManagementManualId = data.btExpenseManagementManualId;
      this.otherChargesExpenseRequest.amount = data.amount;
      this.otherChargesExpenseRequest.amountToDisbursed = data.amountToDisbursed;
      this.otherChargesExpenseRequest.billNo = data.billNo;
      this.otherChargesExpenseRequest.manualId = data.manualId;
      this.otherChargesExpenseRequest.remark = data.remark;
      this.otherChargesExpenseRequest.businessDomainId = data.businessDomainId;
      this.otherChargesExpenseRequest.date = data.date;
      this.otherChargesExpenseRequest.otherChargesTypeId = data.otherChargesTypeId;
      this.spinner.hide();
    });
    this.isEditModeOtherCharges = true;
    this.createOtherChargesModel.show();
    // this.spinner.hide();
  }


  cancelOtherCharges() {
    this.spinner.hide();
    this.createOtherChargesModel.hide();
  }
  onOtherChargesCreated($event)
  {
    this.otherChargesExpenseResponse=$event;
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
