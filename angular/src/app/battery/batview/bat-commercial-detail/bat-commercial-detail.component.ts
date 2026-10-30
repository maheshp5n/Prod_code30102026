import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { CommercialService } from '../../../services/commercial.services';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, BatteryFSRResponse, CaseInvoiceRequest, caseLogReject, CaseRequest, CaseResponse, FileUploadResponse, OppertunityRequest, OppertunityResponse, ProblemReported, QuoteResponse, SecondServiceAppointmentRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentResponse } from '../../../services/model';
import * as fileSaver from 'file-saver';
import { BtCommercialService } from '../../bat_services/bt-commercial.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCaseResponse } from '../../bt_model';
@Component({
  selector: 'app-bat-commercial-detail',
  templateUrl: './bat-commercial-detail.component.html',
  styleUrls: ['./bat-commercial-detail.component.scss']
})
export class BatCommercialDetailComponent implements OnInit {
  @ViewChild('createInvoiceModel', { static: false }) createInvoiceModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('sureModel',{static:false}) sureModel:ModalDirective;
  @ViewChild('rejectcasemodel',{static:false}) rejectcasemodel:ModalDirective;
  @ViewChild('choosenFilesShow',{static:false}) choosenFilesShow:ModalDirective;
  @ViewChild('changeCaseTypemodel',{static:false}) changeCaseTypemodel :ModalDirective;
  res: CaseResponse = new CaseResponse();
  req: CaseRequest = new CaseRequest();
  caseres: BTCaseResponse = new BTCaseResponse()
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  loginData;
  caseid: any
  message: string = "";
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  CREATE_ACCOUNT_HEADING: string = 'Create Case';
  EDIT_ACCOUNT_HEADING: string = 'Edit Case'
  problemReportedDetails: Array<ProblemReported>
  page: number = 0;
  sortColumnName: string;
  sortOrder: string;
  problem: string
  problemName: Array<ProblemReported>
  secodServiceAppointmentRequest: SecondServiceAppointmentRequest = new SecondServiceAppointmentRequest();
  serviceAppontmentList: Array<ServiceAppointmentResponse> = [];
  serviceAppointmentTotal: number;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  totalOpportunity: number;
  totaolProdctConsume: number;
  productConsumedList: Array<ServiceAppointmentProductConsumedResponse> = [];
  //for serviceAppontment
  firstservcAptmnt: boolean;
  lastservcAptmnt: boolean;
  serialNumberservcAptmnt: number;
  sizeservcAptmnt: number;
  pageservcAptmnt: number = 0;
  serialNumberAptmnt: number;
  totalBattryFSR: number;
  battryFSRDATA: Array<BatteryFSRResponse> = [];
  pageBtryFSR: number = 0;
  firstBFSR: boolean;
  lastBFSR: boolean;
  serialNumberBFSR: number;
  sizeBFSR: number;
  firstOprt: boolean;
  lastOprt: boolean;
  serialNumberOprt: number;
  sizeOprt: number;
  pageOprt: number = 0;
  i: number;
  isEditModelShow: boolean = false;
  caseOpportunities: Array<OppertunityResponse> = [];
  isOpportunityOpen: boolean;
  newOpportunity: OppertunityRequest = new OppertunityRequest();
  opportunityList: Array<OppertunityResponse> = [];
  CREATE_Opportunity_HEADING: string = 'New Opportunity';
  EDIT_Opportunity_HEADING: string = 'Edit Opportunity';
  QueteList: Array<QuoteResponse>;
  caseInvoiceRequest:CaseInvoiceRequest=new CaseInvoiceRequest();
  popupNgClass: string = 'alert alert-danger center center-block';
  isCaseModelOpen: boolean = false;
  approvalmessage:string= 'Do you want to Recieve Document ?';
  RejectLogList:Array<caseLogReject>=[];
              // approvalmessage:string=;
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  additionDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  siteDetaiDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  AssignmentDetaiDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  problemDetaiDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  proofDetaiDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  invoiceDetaisDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  vendorDetaisDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  QueteDetaiDisplay = {
    open: false
  }
  _cFlag:string;
  modelsOpenState = { caseEditModelOpen: false }
actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private datePipe: DatePipe,private loginservice: Loginservices, private _btcaseservice: BtCaseserviceService, private _activatedRoute: ActivatedRoute, 
    private spinner: NgxSpinnerService,private commercialService:CommercialService,private _btComercialService : BtCommercialService) { 
    this._activatedRoute.params.subscribe(pram => {
      this.caseid = pram['id'];
      this.actionHistoryObjectAndId.objectId = this.caseid;
      this.actionHistoryObjectAndId.objectName = 'BtCase';
  });
  this._activatedRoute.queryParams.subscribe(pram => {
    this._cFlag = pram['_c'];
  });

  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.showCaseResponse();
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
  changeAdditionalDisplay() {
    this.additionDisplay.open = !this.additionDisplay.open
    if (this.additionDisplay.open) {
      this.additionDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.additionDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changeSiteDetailDisplay() {
    this.siteDetaiDisplay.open = !this.siteDetaiDisplay.open
    if (this.siteDetaiDisplay.open) {
      this.siteDetaiDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.siteDetaiDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  assignmentDisplay() {
    this.AssignmentDetaiDisplay.open = !this.AssignmentDetaiDisplay.open
    if (this.AssignmentDetaiDisplay.open) {
      this.AssignmentDetaiDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.AssignmentDetaiDisplay.angleIcon = 'fa fa-angle-right';
    }
  }


  changeProblemDetailDisplay() {
    this.problemDetaiDisplay.open = !this.problemDetaiDisplay.open
    if (this.problemDetaiDisplay.open) {
      this.problemDetaiDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.problemDetaiDisplay.angleIcon = 'fa fa-angle-right';
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
  invoiceDetailDisplay() {
    this.invoiceDetaisDisplay.open = !this.invoiceDetaisDisplay.open
    if (this.invoiceDetaisDisplay.open) {
      this.invoiceDetaisDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.invoiceDetaisDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  vendorInfoDisplay() {
    this.vendorDetaisDisplay.open = !this.vendorDetaisDisplay.open
    if (this.vendorDetaisDisplay.open) {
      this.vendorDetaisDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.vendorDetaisDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  proofDisplay() {
    this.proofDetaiDisplay.open = !this.proofDetaiDisplay.open
    if (this.sysinfoDisplay.open) {
      this.proofDetaiDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.proofDetaiDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.showCaseResponse();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
       this.rejectLogs();

    }
    return false;
  }
  canreject:boolean=false;
  showCaseResponse() {
    this.spinner.show();
    this._btcaseservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.spinner.hide();
      this.caseres = data;
      this.caseResponse.caseTypeName=this.caseres.caseTypeName;
      this.caseResponse.caseReasonName=this.caseres.caseReasonName;
      // comment by pawan
      this.caseResponse.caseId=this.caseres.caseId;
      this.caseResponse.caseManualId=this.caseres.caseManualId;
      if(this.caseres.caseStageId==17 || this.caseres.caseStageId==18 || this.caseres.caseStageId==9 || this.caseres.caseStageId==3 ||this.caseres.caseStageId==21 || this.caseres.caseStageId==22 || this.caseres.caseStageId==23){
       this.canreject=true;
      }
      if (this.caseres.problemReported != null && this.caseres.problemReported.length > 0) {

        this.problemReportedDetails = this.caseres.problemReported
      }

    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });
  }
  
  invoiceCreate(){
    this.isEditMode=true;
    this.heading='Create Invoice'
    this.createInvoiceModel.show();
    this.spinner.show();
    this._btcaseservice.getDetailByCaseId(this.caseid).subscribe(data => {
    this.spinner.hide();
    this.caseres = data;
    console.log(this.caseres)
    this.caseInvoiceRequest.invoiceNumber=this.caseres.invoiceNumber;
    this.caseInvoiceRequest.invoiceDate=this.datePipe.transform(this.caseres.invoiceDate, 'yyyy-MM-dd HH:mm:ss');
    this.caseInvoiceRequest.poNumber=this.caseres.ponumber;
    this.caseInvoiceRequest.poDate=this.datePipe.transform(this.caseres.podate, 'yyyy-MM-dd HH:mm:ss');
    this.caseInvoiceRequest.vendorInvoiceDate=this.caseres.vendorInvoiceDate
    this.caseInvoiceRequest.vendorInvoiceNumber=this.caseres.vendorInvoiceNumber;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });

  }
  isInvoiceOpen:boolean;
  cancelInvoiceData(){
 this.createInvoiceModel.hide();
 this.isEditMode = false;
 this.caseInvoiceRequest=new CaseInvoiceRequest();
  }
  popupClose() {
    this.popupSuccessModel.hide();
  }
  onCaseInvoiceCreated($event){
    this.caseres=$event;
    this.showCaseResponse();
  }
  docRecieved(){
    this.sureModel.show();
   
  }
  // yesRequestFor(){
  //   this.spinner.show();
  //   this.commercialService.updateDocRecieved(this.caseid).subscribe(data=>{
  //     this.spinner.hide();
  //     this.popupNgClass='alert alert-success center center-block';
  //     this.message='Submit Successfull';
  //     this.popupSuccessModel.show();
  //   },error=>{
  //     this.spinner.hide();
  //     this.message=error.error.message;
  //     this.popupSuccessModel.show();
  //   });

  // }
  noRequestFor(){
    this.sureModel.hide();

  }
  onDOcumnetSuccess($event){
    this.showCaseResponse();

  }
  
  rejectcasemodelCancel(){
    this.rejectcasemodel.hide();
  }
  OnSuccess($event){
    this.showCaseResponse();

  }
  rejectLogs(){
    this.RejectLogList=[];
    this._btcaseservice.getRejectLogListByCaseId(this.caseres.caseManualId).subscribe(data=>{
      this.RejectLogList=data;
    });
  }
  fileName: any
  isImageLoading:any;
  imageToShow:any;
  opneImage(file: FileUploadResponse) {
    this.isImageLoading = true;
    // this.choosenFilesShow.show();
    this.spinner.show();
    this._btcaseservice.filedownload(file.fileName).subscribe(data => {
      this.spinner.hide();
      this.createImageFromBlob(data);
      this.fileName = file.fileName
     
      // if (data.type == 'image/jpeg' || data.type == 'image/png') {
        this.choosenFilesShow.show();
        this.isImageLoading = false;
    },error=>{
      this.spinner.hide();
      this.message=error.message;
      this.popupSuccessModel.show()
    })
  }
  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    } else {
      //  this.downloadFile(this.fileName)
    }
  }
  downloadFile(fileName: any) {
    this.spinner.show();
    this._btcaseservice.getDownloadFile(fileName).subscribe(data => {
      fileSaver.saveAs(data, fileName);
      this.spinner.hide();
    },
      err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }
  caseResponse:BTCaseResponse=new BTCaseResponse();
  changeCaseTypeOpen(){
 this.caseResponse.caseTypeName=this.caseres.caseTypeName;
 this.caseResponse.caseReasonName=this.caseres.caseReasonName;
 this.caseResponse.caseId=this.caseres.caseId;
 this.caseResponse.caseManualId=this.caseres.caseManualId;
 this.changeCaseTypemodel.show();
  }
  CloseCaseTypeChange(){
    this.changeCaseTypemodel.hide();
  }
  onChnagedUpdate($event){
    this.showCaseResponse();
  }

}
