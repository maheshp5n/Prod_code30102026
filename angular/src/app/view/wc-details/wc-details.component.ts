import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { WCCResponse, WccSA, VendorInvoiceRequest, WccRequest, ToBeApprovedHistoryResponse, ActionHistoryObjectAndId } from '../../services/model';
import { WCCService } from '../../services/wcc.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-wc-details',
  templateUrl: './wc-details.component.html',
  styleUrls: ['./wc-details.component.scss']
})
export class WcDetailsComponent implements OnInit {
  history: Array<any> = [];
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  venReq: VendorInvoiceRequest = new VendorInvoiceRequest();
  wccres: WCCResponse = new WCCResponse();
  @ViewChild('createWCCModel') createWCCModel: ModalDirective;
  @ViewChild('commercialModel') commercialModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('alertModal') alertModal: ModalDirective;
  alertmeta = {
    ngClass: 'alert-success',
    message: ''
  };
  wccSAList: Array<WccSA> = [];
  newInvoice: boolean = true;
  statusDraft: boolean = true;
  sapId: string = '';
  message: string;
  tabledata: any
  isEditMode: boolean = false;
  heading: string;
  page: number = 0;
  serialNumber: number;
  totalSite: number;
  first: boolean;
  last: boolean;
  edit: boolean = false;
  isModelOpen: boolean = false;
  historyList: Array<ToBeApprovedHistoryResponse> = [];
  objectname: string;
  wccreq: WccRequest = new WccRequest();
  WccID: number;
  EDIT_ACCOUNT_HEADING: string = 'Edit WCC';
  wccid: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  SAinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  loginData: any;
  _cFlag: string;

  invoiceList: Array<any> = [];
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private wccservice: WCCService,
    private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute,
    private loginservice: Loginservices,
    private approvalHistoryService: ApprovalHistoryService,
    private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.wccid = pram['id'];
      this.getInvoiceByWccId(this.wccid);
      this.actionHistoryObjectAndId.objectId = '' + this.wccid;
      this.actionHistoryObjectAndId.objectName = 'WccDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
    this.getDetails();
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getLoggedInUser();

    this.getSAByWccID();

  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  SAInfoDisplay() {
    this.SAinfoDisplay.open = !this.SAinfoDisplay.open
    if (this.SAinfoDisplay.open) {
      this.SAinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.SAinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getDetails();
      this.getSAByWccID();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getApprovalHistory();
      this.getInvoiceByWccId(this.wccid);
      
    }
    return false;
  }

  editWCCModel(id) {
    this.isEditMode = true;
    this.heading = "Edit WCC";
    this.createWCCModel.show();

    this.spinner.show();
    this.wccservice.getWccById(id).subscribe(data => {
      this.spinner.hide();
      this.wccreq = new WccRequest();
      // this.WccID=data.id;
      this.wccreq.circleName = data.circleName;
      this.wccreq.wccTypeId = data.wccTypeId;
      this.wccreq.startDate = data.fromDate;
      this.wccreq.toDate = data.toDate;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
  }
  cancelData() {
    // this.ssares=new SSAResponse();
    this.createWCCModel.hide();
  }
  onWccCreated($event) {
    this.createWCCModel.hide();
    try {
      this.getDetails();
      this.getSAByWccID();
      this.getInvoiceByWccId(this.wccid);
    } catch (e) {
      console.log(e);
    }
    this.showMessage('Updated Successfully');
  }


  getApprovalHistory() {
    this.objectname = "WccDetails"
    this.spinner.show();
    this.approvalHistoryService.getApprovalStatus(this.objectname, this.wccid).subscribe(data => {
      this.spinner.hide();
      this.historyList = data;
    },error=>{
      this.popupNgClass = 'alert alert-danger center center-block';
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
     
    });
  }

  submitForApproval() {
    this.spinner.show();
    this.wccservice.submitForApproval(this.wccid).subscribe(data => {
      this.message = 'Submitted For Approval';
      this.popupNgClass = 'alert alert-success center center-block';
      this.spinner.hide();
      // this.popupSuccessModel.show();
      this._global.alert({ type: '', message: 'Successfully submitted for approval' });
      this.getInvoiceByWccId(this.wccid);
      this.getApprovalHistory();
      this.getDetails();
    }, err => {
      this.popupNgClass = 'alert alert-danger center center-block';
      this.message = err.error.message;
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
      // this.popupSuccessModel.show();
    });
  }

  getDetails() {
    this.spinner.show();
    this.wccservice.getWccById(this.wccid).subscribe(data => {
      this.spinner.hide();
      this.wccres = data;
      this.WccID = data.id;
      if (this.wccres.wccStatus === "Rejected") {
        this.newInvoice = false;
        this.statusDraft = true;

      } else if (this.wccres.wccStatus === "Draft") {
        this.statusDraft = true;
      }
      else {
        this.newInvoice = true;
        this.statusDraft = false;
      }
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  SAtrue: boolean;
  wccSAID: number;
  getSAByWccID() {
    this.spinner.show();
    this.wccservice.getSAbyWccId(this.wccid).subscribe(data => {
      this.spinner.hide();
      this.wccSAList = data;
      data.forEach(e => {
        this.SAtrue = e.SAtrue;
      });
    },err=>{
      this.popupNgClass = 'alert alert-danger center center-block';
      // this.message = err.error.message;
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });
  }

  createINVShowModal() {
    this.heading = "Create Invoice";
    this.commercialModel.show();
    this.wccid = this.wccres.id
    // this.v = this.wccres.manulId;
    //  this.venReq.vendorInvoiceDate=this.wccres.vendorName
  }
  cancelInvoice() {
    this.commercialModel.hide();
  }
  onInvoiceCreated($event) {
    this._global.alert({ type: '', message: 'Invoice Created' });
    this.getInvoiceByWccId(this.wccid);
    this.getApprovalHistory();
    this.getDetails();
  }

  getInvoiceByWccId(id) {
    this.wccservice.getVendorInvocieByWccId(id).subscribe(data => {
      this.invoiceList = data;
      console.log(data);
    },err=>{
      this.popupNgClass = 'alert alert-danger center center-block';
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });
  }

  showAlert(message) {
    this.alertmeta.ngClass = 'alert alert-danger';
    this.alertmeta.message = message;
    this.alertModal.show();
  }
  showMessage(message) {
    this.alertmeta.ngClass = 'alert alert-success';
    this.alertmeta.message = message;
    this.alertModal.show();
  }

}
