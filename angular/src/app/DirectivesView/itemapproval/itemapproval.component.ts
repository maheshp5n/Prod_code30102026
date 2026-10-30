import { ItemsToBeApprovedService } from '../../services/itemstobeapprovedservices';
import { ToBeApproved, ApprovalRequest } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { ViewChild } from '@angular/core';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-itemapproval-button',
  templateUrl: './itemapproval.component.html',
  styleUrls: ['./itemapproval.component.scss']
})
export class ItemapprovalComponent implements OnInit {

  objectId: any;
  btnshow: boolean = false;


  toBeApprovedSelected: ToBeApproved = new ToBeApproved();
  approvalRequest: ApprovalRequest = new ApprovalRequest();

  @ViewChild('approvalModule', { static: false }) approvalModule: ModalDirective;
  @ViewChild('alertModule', { static: false }) alertModule: ModalDirective;
  alerttypes = {
    type: 'danger',
    message: ''
  };
  @Input() objectName: string;

  @Input() set _objectId(obId: any) {
    this.objectId = obId;
    this.getApprovalButton();
  }
  get() {
    return this.objectId;
  }

  constructor(private _approval: ItemsToBeApprovedService, private _ngxspinner: NgxSpinnerService, private _global: GlobalserviceService) { }

  ngOnInit() {

  }
  getApprovalButton() {
    this._approval.getItemToBeApprovdButton(this.objectName, this.objectId).subscribe(data => {
      if (data != null && data != undefined && data.id != undefined) {

        this.btnshow = true;
        this.toBeApprovedSelected = data;
        this.approvalRequest.approvalId = data.id;
      }
      else {
        this.btnshow = false;
      }
    }, error => this.btnshow = false);
  }
  showMessage(message) {
    this.alerttypes.type = 'success';
    this.alerttypes.message = message;
    this.alertModule.show();
  }
  showAlert(message) {
    this.alerttypes.type = 'danger';
    this.alerttypes.message = message;
    this.alertModule.show();
  }
  doApproveOrReject() {
    if (this.approvalRequest.approvalId == undefined || this.approvalRequest.approvalId == null || this.approvalRequest.approvalId == 0) {
      this.showAlert('Some thing went wrong,please reload the page');
      return;
    }
    if (this.approvalRequest.action == undefined || this.approvalRequest.action == null || this.approvalRequest.action == '' || this.approvalRequest.action === 'select') {
      this.showAlert('Please choose action Approve or Reject');
      return;
    }
    if (this.approvalRequest.remark != null && this.approvalRequest.remark != undefined && this.approvalRequest.remark.length > 200) {
      this.showAlert('Please write remark in less than 200 characters');
      return;
    }

    this._ngxspinner.show();
    this._approval.approveRejectItems(this.approvalRequest.approvalId, this.approvalRequest).subscribe(data => {
      this._ngxspinner.hide();
      this.approvalModule.hide();
      this.getApprovalButton();
      // this.showMessage('Submitted successfully');
      this._global.alert({ type: '', message: 'Submitted successfully' });
    }, error => {
      this._ngxspinner.hide()
      this.showAlert(`Error : ${error.error.message}`);
    });
  }
}
