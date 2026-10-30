import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { BtexpenseServiceappointmentService } from '../../../bat_services/btexpense-serviceappointment.service';
import { btExpServiceAppointmentReq, btExpServiceAppointmentResp } from '../../../bt_model';

@Component({
  selector: 'app-bat-saexpensedetails',
  templateUrl: './bat-saexpensedetails.component.html',
  styleUrls: ['./bat-saexpensedetails.component.css']
})
export class BatSAExpensedetailsComponent implements OnInit {

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  heading: string;
  CREATE_CONTACT_HEADING: string = 'New Contact';
  EDIT_CONTACT_HEADING: string = 'Edit Contact';
  flag: boolean;
  isEditModeExpSA: boolean = false;
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  SAExpenseRequest: btExpServiceAppointmentReq = new btExpServiceAppointmentReq();
  SAExpenseResponse: btExpServiceAppointmentResp = new btExpServiceAppointmentResp();
  isSAExpOpen: boolean;
  _cFlag: string;

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createExpSAModel', { static: false }) createExpSAModel: ModalDirective;

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
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private expSAservice:BtexpenseServiceappointmentService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.SAExpenseResponse.id = pram['id2'];
      this.actionHistoryObjectAndId.objectId = '' + this.SAExpenseResponse.id;
      this.actionHistoryObjectAndId.objectName = 'ServiceAppointmentExpense';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getSAExpenseDetails();
  }

  getSAExpenseDetails() {
    this.spinner.show();
    this.expSAservice.getServiceAppointmentExpenseDetailsById(this.SAExpenseResponse.id).subscribe((data) => {
      this.SAExpenseResponse = data;
      // data.toDate=this.datePipe.transform(data.toDate,'dd-MM-yyyy');
      // data.toTime=data.toDate+' '+data.toTime;
      // data.fromDate=this.datePipe.transform(data.fromDate,'dd-MM-yyyy');
      // data.fromTime=data.fromDate+' '+data.fromTime;
      this.spinner.hide();
    });
  }

  cancelBoarding() {
    this.createExpSAModel.hide();
    this.isEditModeExpSA = false;
    this.SAExpenseRequest = new btExpServiceAppointmentReq();
  }

  onClickEditBoarding(id) {
    this.SAExpenseRequest = new btExpServiceAppointmentReq();
    this.spinner.show();
    this.expSAservice.getServiceAppointmentExpenseDetailsById(id).subscribe((data) => {
      this.SAExpenseResponse.id = data.id;
      this.SAExpenseResponse = data;      
      this.SAExpenseResponse.btExpenseManagementId = data.id;
      this.spinner.hide();
    });
    this.isEditModeExpSA = true;
    this.createExpSAModel.show();
  }

  cancelSAExp()
  {
    this.createExpSAModel.hide();
  }
  onExpSACreated($event){
    this.SAExpenseResponse = $event;
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
