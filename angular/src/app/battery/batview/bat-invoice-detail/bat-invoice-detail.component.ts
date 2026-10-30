import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, FileUploadResponse, ToBeApprovedHistoryResponse, VendorCommRequest, vendorInvoiceResponse } from '../../../services/model';
import * as fileSaver from 'file-saver';
import { DomSanitizer } from '@angular/platform-browser';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';
import { BtWccServiceService } from '../../bat_services/bt-wcc-service.service';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';

@Component({
  selector: 'app-bat-invoice-detail',
  templateUrl: './bat-invoice-detail.component.html',
  styleUrls: ['./bat-invoice-detail.component.scss']
})
export class BatInvoiceDetailComponent implements OnInit {
  history: Array<any> = [];
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  isSapEditMode:boolean= false;
  @ViewChild('createInvoiceModel', { static: false }) createInvoiceModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('commercialModel', { static: false }) commercialModel: ModalDirective;
  @ViewChild('SAPUpdateModel', { static: false }) SAPUpdateModel: ModalDirective;
  @ViewChild('choosenFilesShow', { static: false }) choosenFilesShow: ModalDirective;
  @ViewChild('approvalModule',{static:false})approvalModule:ModalDirective;
  modelOpen = {
    invoiceModal: false,
    grnModal: false,
    sapModal: false,
    fileShoe: false
  };
  comreq: VendorCommRequest = new VendorCommRequest();
  sapId: string = '';
  message: string;
  tabledata: any;
  isEditMode: boolean = false;
  heading: string;
  page: number = 0;
  serialNumber: number;
  totalSite: number;
  first: boolean;
  last: boolean;
  edit: boolean = false;
  isModelOpen: boolean;
  uploadForm: FormGroup;
  // CREATE_ACCOUNT_HEADING: string = 'Create SSA';
  EDIT_ACCOUNT_HEADING: string = 'Edit Invoice';
  venInvoiceId: number;
  InvoiceApprovalId:number;
  popupNgClass: string = 'alert alert-danger center center-block';
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
  SAinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  loginData: any;
  _cFlag: string;
  fileList: string[] = [];
  imgsrc: any;
  imageToShow: any;
  isImageLoading: any;
  fileLists: Array<FileUploadResponse> = [];
  fileName: string;
  historyList: Array<ToBeApprovedHistoryResponse> = [];
  objectname: string;
  venInvRes: vendorInvoiceResponse = new vendorInvoiceResponse();
  serviceappointmentList: Array<any> = [];
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  
  constructor(private formBuilder: FormBuilder,
    private tobeapproved:BtTobeApprovedService,
    private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute,
    private loginservice: Loginservices,
    private _btvendorinvoiceservice: BtInvoiceService,
    private domSanitizer: DomSanitizer,
    private approvalHistoryService: ApprovalHistoryService,
    private _btwccService: BtWccServiceService,
    private _global: GlobalserviceService) {     
    this._activatedRoute.params.subscribe(pram => {
      this.venInvoiceId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.venInvoiceId;
      this.actionHistoryObjectAndId.objectName = 'BtVendorInvoiceDetails'; 
    });   
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
   
  }
 
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getApprovalButton();
    this.getLoggedInUser();
    this.DetailsByID();
    this.getFileUploadList();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  
  DetailsByID() {
    this.spinner.show();
    this._btvendorinvoiceservice.getVendInvoiceById(this.venInvoiceId).subscribe(data => {
      this.spinner.hide();
      this.venInvRes = data;
      this.comreq.grnDate = data.grnDate;
      this.comreq.grnNumber = data.grnNumber;
      this.comreq.entrySheetNumber = data.entrySheetNumber;
      this.comreq.internalServiceCode = data.internalCodeId;
      this.getSAByWccId(data.wccDetailsId);
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  showApprovalWindow(){
    this.approvalModule.show();
  }

  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  SAInfoDisplay() {
    this.SAinfoDisplay.open = !this.SAinfoDisplay.open;
    if (this.SAinfoDisplay.open) {
      this.SAinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.SAinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.DetailsByID();
    // this.getFileUploadList();
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getApprovalHistory();
      this.getFileUploadList();
    }
    return false;
  }
  editWCCModel(res) {
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createInvoiceModel.show();
  }
  cancelInvoiceData() {
    this.createInvoiceModel.hide();
  }


  getApprovalHistory() {
    this.objectname = 'BtVendorInvoiceDetails';
    this.approvalHistoryService.getApprovalStatusbt(this.objectname, this.venInvoiceId).subscribe(data => {
      this.historyList = data;
    });
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getApprovalHistory();
  }

  setPage(event: any) {
    event.preventDefault();
    this.getApprovalHistory();
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.getApprovalHistory();
  }
  oninvoicereated($event) {

  }
  forCommercial() {
    this.commercialModel.show();
    this.heading = 'Update GRN';
  }
  cancelCommercial() {
    this.commercialModel.hide();
  }
  onGRNupdate($event) {
    // this.venInvRes.grnDate = $event.grnDate;
    // this.venInvRes.grnNumber = $event.grnNumber;
    // this.venInvRes.glCode = $event.glCode;
    // this.venInvRes.entrySheetNumber = $event.entrySheetNumber;
    this._global.alert({ type: '', message: 'Updated Successfully' });
    this.DetailsByID();
    // this.getFileUploadList();

  }

  sapUpdate() {
    if(this.venInvRes.sapNumber==null || this.venInvRes.sapNumber==0 || this.venInvRes.sapNumber==undefined)
    {
      this.isSapEditMode=false;
    
    }
    else{
      this.isSapEditMode=true;
    }
    this.heading = 'Update SAP';
    this.SAPUpdateModel.show();
  }
  cancelSAP() {
    this.SAPUpdateModel.hide();
  }

  getFileUploadList() {
    this.fileList = [];
    this.spinner.show();
    this._btvendorinvoiceservice.getFileList(this.venInvoiceId).subscribe(data => {
      this.spinner.hide();
      this.fileLists = data;
      this.fileLists.forEach(e => {
        this.fileName = e.fileName;
      });
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
  }

  downloadFile(fileName: any) {
    console.log(fileName)
    this.spinner.show();
    this._btvendorinvoiceservice.getDownloadFile(fileName).subscribe(data => {
      fileSaver.saveAs(data, fileName);
      this.spinner.hide();
    },
      err => {
        this.spinner.hide();  
        this._global.alert({ type: 'alert', message:err.error.message});
      });
  }
  onSAPUpdate($event: vendorInvoiceResponse) {
    this.venInvRes.sapNumber = $event.sapNumber;
    this.venInvRes.sapDate = $event.sapDate;
    this._global.alert({ type: '', message: 'Updated Successfully' });
    this.DetailsByID();
    this.getFileUploadList();

  }

  editInvoiceModal() {
    this.createInvoiceModel.show();
  }
  onInvoiceUpdated(data) {
    this.createInvoiceModel.hide();
    this._global.alert({ type: '', message: 'Updated Successfully' });
    this.DetailsByID();
    this.getFileUploadList();
  }
  sanitizeUrl(purl) {
    return this.domSanitizer.bypassSecurityTrustUrl(purl);
  }
  isPdf(purl: string): boolean {
    if (purl == undefined || purl == null || purl == '') {
      return false;
    }
    if (purl.lastIndexOf('.pdf') === (purl.length - 4)) {
      return true;
    } else {
      return false;
    }
  }

  getSAByWccId(id) {
    this._btwccService.getSAbyWccId(id).subscribe(data => {
      this.serviceappointmentList = data;
      console.log(data);
    });
  }
  btnshow:boolean=false;
  getApprovalButton() {
  
    
    this.spinner.show()
    this.tobeapproved.getItemToBeApprovdButton( this.actionHistoryObjectAndId.objectName, this.venInvoiceId).subscribe(data => {
      this.spinner.hide();
      if( data.id != null && data != null && data != undefined)
      {
        this.btnshow = true;
        this.InvoiceApprovalId= data.id;
        
        console.log(this.InvoiceApprovalId);
        
      }
      else {
        this.btnshow = false;
        this.InvoiceApprovalId = this.venInvoiceId;
      }
    }, error=>{
  
    });
  
  
  }
  onApprovalDone(){
    this.approvalModule.hide();
    // this.getPendingPage();
    this.DetailsByID();
    this.getApprovalButton();
  }

  submitForApproval() {
    this.spinner.show();
    this._btvendorinvoiceservice.submitForApproval(this.venInvoiceId).subscribe(data => {
      this.spinner.hide();
      this._global.alert({ type: '', message: 'Submitted Successfully For Approval' });
      this.DetailsByID();
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

}
