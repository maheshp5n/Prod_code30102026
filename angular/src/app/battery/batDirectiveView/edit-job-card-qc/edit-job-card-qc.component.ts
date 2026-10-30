import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatJObCardQcService } from '../../bat_services/bat-job-card-qc.service';
import { UpdateJobCardQcRequest } from '../../bt_model';

@Component({
  selector: 'app-edit-job-card-qc',
  templateUrl: './edit-job-card-qc.component.html',
  styleUrls: ['./edit-job-card-qc.component.css']
})
export class EditJobCardQcComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];
  updateJobCardReq: UpdateJobCardQcRequest = new UpdateJobCardQcRequest();
  jobCardQcRes: any;
  today = new Date();
  ngClassqcPhysicalRemarks = 'form-group required';
  ngClassqcElectricalRemarks = 'form-group required';
  qcPhysicalCheckList: Array<any> = [];
  qcElectricalCheckList: Array<any> = [];
  qcPhysicalRemarksList: Array<any> = [];
  qcElectricalRemarksList: Array<any> = [];
  qcCorrrectiveActionTakenList: Array<any> = [];
  qcsoftwareupdationList: Array<any> = [];
  qcparametercheckList: Array<any> = [];
  qcsohupationList: Array<any> = [];
  qcvoltagapList: Array<any> = [];
  qcfinalapprovalList: Array<any> = [];
  // new 
  qcFaultObservedDuringPDITakenList: Array<any> = [];


  @Input()
  set updateJobCardQc(updateJobCardQc: any) {
    this.jobCardQcRes = updateJobCardQc;
  }

  get updateJobCardQc(): any {
    return this.jobCardQcRes;
  }
  @Output()
  editJobCard: EventEmitter<any> = new EventEmitter();
  @Output()
  cancled: EventEmitter<any> = new EventEmitter();
  constructor(private _btJobCardQcService: BatJObCardQcService, private _spinner: NgxSpinnerService, private _global: GlobalserviceService) { }
  ngOnInit(): void {
    this.getValueOfPhysicalCheck();
    this.getValueOfElectricalCheck();
    this.getValueOfPhysicalRemarks();
    this.getValueOfElectricalRemarks();
    this.getValueOfCorrectiveActionTaken();
    this.getValueOfSoftwareUpdation();
    this.getValueOfParameterCheck();
    this.getValueOfSohUpdatio();
    this.getValueOfVoltageGap();
    this.getValueOfQcFinalApproved();
    this.getValueOfFaultObservedDuringPDITaken();
  }

  getChangePhysicalCheck() {
    console.log(this.updateJobCardReq.qcPhysicalCheck)
    if (this.updateJobCardReq.qcPhysicalCheck == 'OK') {
      this.ngClassqcPhysicalRemarks = 'form-group';
      this.updateJobCardReq.qcPhysicalRemarks = '0';
    }
    else {
      this.ngClassqcPhysicalRemarks = 'form-group required';
    }
  }
  getChangeElectricalCheck() {
    if (this.updateJobCardReq.qcElectricalCheck == 'OK') {
      this.ngClassqcElectricalRemarks = 'form-group';
      this.updateJobCardReq.qcElectricalRemarks = '0';
    }
    else {
      this.ngClassqcElectricalRemarks = 'form-group required';
    }
  }

  getValueOfPhysicalCheck() {
    this._spinner.show();
    this._btJobCardQcService.getQcPhysicalCheck().subscribe(data => {
      this.qcPhysicalCheckList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfElectricalCheck() {
    this._spinner.show();
    this._btJobCardQcService.getQCElectricalCheck().subscribe(data => {
      this.qcElectricalCheckList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfPhysicalRemarks() {
    this._spinner.show();
    this._btJobCardQcService.getqcphysicalremarks().subscribe(data => {
      this.qcPhysicalRemarksList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfElectricalRemarks() {
    this._spinner.show();
    this._btJobCardQcService.getqcelectricalremarks().subscribe(data => {
      this.qcElectricalRemarksList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  // new 
  getValueOfFaultObservedDuringPDITaken() {
    this._spinner.show();
    this._btJobCardQcService.getqcfaultobservedduringpditaken().subscribe(data => {
      this.qcFaultObservedDuringPDITakenList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }

  getValueOfCorrectiveActionTaken() {
    this._spinner.show();
    this._btJobCardQcService.getqccorrectiveactiontaken().subscribe(data => {
      this.qcCorrrectiveActionTakenList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfSoftwareUpdation() {
    this._spinner.show();
    this._btJobCardQcService.getqcsoftwareupdation().subscribe(data => {
      this.qcsoftwareupdationList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfParameterCheck() {
    this._spinner.show();
    this._btJobCardQcService.getqcparametercheck().subscribe(data => {
      this.qcparametercheckList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfSohUpdatio() {
    this._spinner.show();
    this._btJobCardQcService.getqcsohupation().subscribe(data => {
      this.qcsohupationList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfVoltageGap() {
    this._spinner.show();
    this._btJobCardQcService.getqcvoltagap().subscribe(data => {
      this.qcvoltagapList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  getValueOfQcFinalApproved() {
    this._spinner.show();
    this._btJobCardQcService.getqcfinalapproval().subscribe(data => {
      this.qcfinalapprovalList = data;
      this._spinner.hide();
    }, err => {
      this._spinner.hide();
    })
  }
  UpdateJobCardQc() {
    if (this.updateJobCardReq.qcPidDate == '' || this.updateJobCardReq.qcPidDate == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill PDI Date' });
      return;
    }
    if (this.updateJobCardReq.qcPhysicalCheck == '0' || this.updateJobCardReq.qcPhysicalCheck == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Physical Check' });
      return;
    }
    if (this.updateJobCardReq.qcPhysicalCheck == 'Not OK' && (this.updateJobCardReq.qcPhysicalRemarks == '0' || this.updateJobCardReq.qcPhysicalRemarks == undefined)) {
      this._global.alert({ type: 'alert', message: 'Please Select Physical Remarks' });
      return;
    }
    if (this.updateJobCardReq.qcElectricalCheck == '0' || this.updateJobCardReq.qcElectricalCheck == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Electrical Check' });
      return;
    }
    if (this.updateJobCardReq.qcElectricalCheck == 'Not OK' && (this.updateJobCardReq.qcElectricalRemarks == '0' || this.updateJobCardReq.qcElectricalRemarks == undefined)) {
      this._global.alert({ type: 'alert', message: 'Please Fill Electrical Remarks' });
      return;
    }
    if (this.updateJobCardReq.qcFaultObservedDuringPID == '' || this.updateJobCardReq.qcFaultObservedDuringPID == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Fault Observed During PDI' });
      return;
    }
    if (this.updateJobCardReq.qcCorrectiveActionTaken == '0' || this.updateJobCardReq.qcCorrectiveActionTaken == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Corrective Action Taken' });
      return;
    }
    if (this.updateJobCardReq.qcSoftWareupdation == '0' || this.updateJobCardReq.qcSoftWareupdation == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Software Updation' });
      return;
    }
    if (this.updateJobCardReq.qcParameterCheck == '0' || this.updateJobCardReq.qcParameterCheck == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Paramaeters Checks' });
      return;
    }
    if (this.updateJobCardReq.qcSohUpdation == '0' || this.updateJobCardReq.qcSohUpdation == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select SOH Updation' });
      return;
    }
    if (this.updateJobCardReq.qcVoltageGap == '0' || this.updateJobCardReq.qcVoltageGap == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Voltage Gap' });
      return;
    }
    if (this.updateJobCardReq.qcFinalApproval == '0' || this.updateJobCardReq.qcFinalApproval == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Final Approved' });
      return;
    }
    if (this.updateJobCardReq.qcVmax == '' || this.updateJobCardReq.qcVmax == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Vmax' });
      return;
    }
    if (this.updateJobCardReq.qcVmin == '' || this.updateJobCardReq.qcVmin == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill qcVmin' });
      return;
    }
    this.updateJobCardReq.jobCardQcId = this.updateJobCardQc.qcId;
    this._spinner.show();
    this._btJobCardQcService.updateJobCardQc(this.updateJobCardReq.jobCardQcId, this.updateJobCardReq).subscribe(data => {
      this._spinner.hide();
      this.editJobCard.emit(data);
    }, error => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  cancelJobCardQc() {
    this.cancled.emit('cancelled');
  }
}
