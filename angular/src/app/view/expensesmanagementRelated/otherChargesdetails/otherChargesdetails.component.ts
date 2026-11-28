import { OtherChargesService } from './../../../services/otherCharges.service';
import { ActionHistoryObjectAndId, OtherChargesExpenseRequest, OtherChargesExpenseResponse } from '../../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Loginservices } from '../../../services/login.services';

@Component({
  selector: 'app-otherChargesdetails',
  templateUrl: './otherChargesdetails.component.html',
  styleUrls: ['./otherChargesdetails.component.css']
})

export class OtherChargesdetailsComponent implements OnInit {

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
  otherChargesExpenseRequest: OtherChargesExpenseRequest = new OtherChargesExpenseRequest();
  otherChargesExpenseResponse: OtherChargesExpenseResponse = new OtherChargesExpenseResponse();
  _cFlag: string;

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

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
    private _otherChargesExpenseService: OtherChargesService, private loginService: Loginservices) {
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
