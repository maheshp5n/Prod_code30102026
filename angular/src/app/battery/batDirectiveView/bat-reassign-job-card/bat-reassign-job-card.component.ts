import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtJobCardServiceService } from '../../bat_services/bt-job-card-service.service';
import { BtJobCardResponse, ReAssignJobCardReq, RepairEngineerList } from '../../bt_model';

@Component({
  selector: 'app-bat-reassign-job-card',
  templateUrl: './bat-reassign-job-card.component.html',
  styleUrls: ['./bat-reassign-job-card.component.css']
})
export class BatReassignJobCardComponent implements OnInit {
  repairEngList: Array<RepairEngineerList> = [];
  reAssignJobCardRequest: ReAssignJobCardReq = new ReAssignJobCardReq();
  assignEmailId: string;
  constructor(private _spinner: NgxSpinnerService, private _btJobCardService: BtJobCardServiceService,
    private _global: GlobalserviceService) { }
  @Input()
  set reAssignJC(response: any) {
    this.reAssignJobCardRequest.jobCardId = response.jobCardId;
    this.assignEmailId = response.assignedToEmailId;
  }
  get reAssignJC() {
    return this.reAssignJobCardRequest.jobCardId;
  }
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onReassignJcCreated: EventEmitter<any> = new EventEmitter<any>();
  ngOnInit(): void {
    this.getListOfRepairEnginner();
  }
  getListOfRepairEnginner() {
    this.repairEngList = [];
    this._spinner.show();
    this._btJobCardService.btServiceOrderRepairEngineerList().subscribe(data => {
      this._spinner.hide();
      for (let i = 0; i <= data.length; i++) {
        if (data[i].contactEmailId != this.assignEmailId) {
          this.repairEngList.push(data[i]);
        }
      }
    }, err => {
      this._spinner.hide();
    })
  }
  postReassign() {
    if (this.reAssignJobCardRequest.reAssignTo == '0' || this.reAssignJobCardRequest.reAssignTo == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Select Repair Engineer' });
      return;
    }
    else {
      this._spinner.show();
      this._btJobCardService.postReAssign(this.reAssignJobCardRequest.jobCardId, this.reAssignJobCardRequest).subscribe(data => {
        this._spinner.hide();
        this.onReassignJcCreated.emit(data);
      }, err => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      })
    }
  }
  cancelReAssign() {
    this.onCancle.emit('cancel');
  }
}
