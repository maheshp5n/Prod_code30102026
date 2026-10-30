import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import {
  CaseResponse,
  CaseRequest,
  ProblemReported,
  opportunitycaseResponse,
  ServiceAppointmentProductConsumedResponse,
  ServiceAppointmentResponse,
  OppertunityResponse,
  BatteryFSRResponse,
  QuoteResponse,
  SecondServiceAppointmentRequest,
  OppertunityRequest,
  CaseProofOfDeliveryRequest,
  FileUploadResponse,
  ToBeApprovedHistoryResponse,
  ActionHistoryObjectAndId,
  RejectCaseRequest,
  caseLogReject,
  PreRegCaseResponse,
  Page,
  FileUploadResponseCase
} from '../../services/model';
import { Loginservices } from '../../services/login.services';
import { ModalDirective } from 'ngx-bootstrap';
import { caseService } from '../../services/caseService.service';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinner } from 'ngx-spinner/lib/ngx-spinner.enum';
import { NgxSpinnerService } from 'ngx-spinner';
import { StringifyOptions } from 'querystring';
import { OpportunityService } from '../../services/Opportunity.services';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import * as fileSaver from 'file-saver';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { FileDeleteService } from '../../services/filedeletService.services';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { GlobalserviceService } from '../../globalservice.service';
@Component({
  selector: 'app-case-details',
  templateUrl: './case-details.component.html',
  styleUrls: ['./case-details.component.scss']
})
export class CaseDetailsComponent implements OnInit {
  @ViewChild('createCaseModel', { static: false }) createCaseModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createServiceAppointmentModel', { static: false }) createServiceAppointmentModel: ModalDirective;
  @ViewChild('createPD',{static:false}) createPD:ModalDirective;
  res: CaseResponse = new CaseResponse();
  req: CaseRequest = new CaseRequest();
  caseres: CaseResponse = new CaseResponse()
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  loginData;
  caseid: any;
  isCaseModelOpen: boolean = false;
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
  fileList: string[] = [];
  imgsrc: any
  imageToShow: any;
  isImageLoading: any;
  historyList:Array<ToBeApprovedHistoryResponse>=[];
  objectname:string;
  CaseDetails;
  uploadForm: FormGroup;
  formData = new FormData();
  fileLists: Array<FileUploadResponseCase> = [];
  RejectLogList:Array<caseLogReject>=[];
  caseRejectreq:RejectCaseRequest=new RejectCaseRequest();
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('createNewOpportunity', { static: false }) createNewOpportunity: ModalDirective;
  @ViewChild('choosenFiles',{static:false}) choosenFiles:ModalDirective;
  @ViewChild('choosenFilesShow',{static:false}) choosenFilesShow:ModalDirective;
  @ViewChild('rejectcasemodel',{static:false}) rejectcasemodel: ModalDirective;
  @ViewChild('changeCaseTypemodel',{static:false}) changeCaseTypemodel :ModalDirective;

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
  LogedEmailId:string;
  _cFlag:string;
  modelsOpenState = { caseEditModelOpen: false, serviceAppointmentModelOpen: false }
  
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

  constructor( private deleteservice:FileDeleteService,private approvalHistoryService:ApprovalHistoryService,private formBuilder: FormBuilder,
    private loginservice: Loginservices, private caseservice: caseService, private _activatedRoute: ActivatedRoute, 
    private spinner: NgxSpinnerService, private oportunityServic: OpportunityService,
    private _global:GlobalserviceService,
    private prcservice:PreRegCaseService) {
    this._activatedRoute.params.subscribe(pram => {
      this.caseid = pram['caseid'];
      this.actionHistoryObjectAndId.objectId = this.caseid;
      this.actionHistoryObjectAndId.objectName = 'CaseDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
    this.loginData = this.loginservice.getLoggedInUser();
    // this.domainId = this.loginData.emptype['businessDomainId']
    this.LogedEmailId=this.loginData.emailId;

  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getLoggedInUser()
    this.showCaseResponse();
    this.CaseHistory();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    // this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
    // this.productConsumed(this.sortColumnName, this.sortOrder);
    // this.ApportunityByCase(this.sortColumnName, this.sortOrder);
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

  queteDesiplay(ser: OppertunityResponse) {
    this.spinner.show();
    this.QueteDetaiDisplay.open = !this.QueteDetaiDisplay.open;
    this.oportunityServic.getQuoteByOppoId(ser.oppertunityId, this.page).subscribe(data => {
      this.spinner.hide();
      this.QueteList = data.content;
    })


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
      this.getBattryFSR(this.sortColumnName, this.sortOrder);
      this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
      this.productConsumed(this.sortColumnName, this.sortOrder);
      this.ApportunityByCase(this.sortColumnName, this.sortOrder);
      this.getPrcByCase();
      this.CaseHistory();
      this.getFileUploadList();
      this.rejectLogs();

    }
    return false;
  }
  siteseviceResourceID:number;
  serviceCenterId:number;
  editCaseModelshow(res: CaseResponse) {
    // this.res = res
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.caseservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.res = data;
      this.siteseviceResourceID=data.serviceResourceId;
      this.serviceCenterId=data.serviceCenterId;
      this.res.contactPersonId=data.contactPersonId;
      this.res.caseOriginId=data.caseOriginId;
      this.res.caseOriginName=data.caseOriginName;
      this.spinner.hide();
      this.createCaseModel.show();
    }, error => {
      this.spinner.hide();
      this.createCaseModel.show();
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
  
    });
  }
  showCaseDetails() {
    this.spinner.show()
    this.caseservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.spinner.hide()
      this.res = data;
      if (this.res.problemReported != null && this.res.problemReported.length > 0) {
        // for(let i=0;i<this.res.problemReported.length;i++){
        //     let data = this.res.problemReported[i].problemName;
        //     this.problemReportedDetails=this.problemReportedDetails+' , '+data;
        // }


      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;

      this.popupSuccessModel.show()
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();

      // }, 2000
      // );

    })

  }

  cancelDatacase() {
    this.isEditMode = false;
    this.createCaseModel.hide();
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  // editserviceModelshow($event:ServiceAppointmentResponse){
  //   this.serviceAppointmentResponse=$event;
  //   this.isEditMode=true;
  //   this.heading="Edit Service Appointment";
  //   this.createServiceAppointmentModel.show()
  // }
  buisnessDomainId: number;
  caseNumber: string;
  accountId: string;
  accountname: string;
 fileUploadbtn:boolean=false;
 canreject:boolean=false;
 caseResponse:CaseResponse=new CaseResponse();
  showCaseResponse() {
    this.spinner.show();
    this.caseservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.spinner.hide();
      this.caseres = data;
      console.log(this.caseres.iSmsSend);
      this.caseNumber = data.caseManualId;
      this.accountId = data.accountSapNumber;
      this.accountname = data.accountName;
      this.caseResponse.caseTypeName=this.caseres.caseTypeName;
      this.caseResponse.caseReasonName=this.caseres.caseReasonName;
      this.caseResponse.caseId=this.caseres.caseId;
      this.caseResponse.caseManualId=this.caseres.caseManualId;

      if(this.caseres.caseStatusId==2 || this.caseres.caseStatusId==4){
        this.canreject=true;
      }
      if(data.caseOwnerEmailId==this.LogedEmailId){
        this.fileUploadbtn=true;
      }
      else{
        this.fileUploadbtn=false;
      }

      this.buisnessDomainId = data.caseRecordTypeId;
      if (this.caseres.problemReported != null && this.caseres.problemReported.length > 0) {

        this.problemReportedDetails = this.caseres.problemReported
      }

    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this.popupSuccessModel.show();

    });
  }
 
  onCaseCreated($event: CaseResponse) {
    this.caseres = $event
    this.message = 'Updated Successfully';
    this._global.alert({ type: '', message: this.message });
   this.showCaseResponse();
    if (this.caseres.caseTypeId !== 1) {

      this.getBattryFSR(this.sortColumnName, this.sortOrder);
      if ($event.caseStatusId !== 1 || $event.caseStageId !== 1) {
        this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
      }
   
      this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
      this.productConsumed(this.sortColumnName, this.sortOrder);
      this.ApportunityByCase(this.sortColumnName, this.sortOrder);
      this.CaseHistory();
      this.getFileUploadList();
      this.rejectLogs();
    }


  }

  serviceAppointmentById(column: string, order: string) {
    this.spinner.show();
    this.caseservice.getServiceAppointmentByCaseId(this.caseid, this.pageservcAptmnt, column, order).subscribe(data => {
      this.spinner.hide();
      this.serviceAppontmentList = data.content;
      this.serviceAppointmentTotal = data.totalElements;
      this.firstservcAptmnt = data.first;
      this.lastservcAptmnt = data.last;
      this.sizeservcAptmnt = data.size;
      this.serialNumberservcAptmnt = (((this.pageservcAptmnt) * this.sizeservcAptmnt));
    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }

  productConsumed(column: string, order: string) {
    this.spinner.show();
    this.caseservice.getProductConsuedBycaseId(this.caseid, this.page, column, order).subscribe(data => {
      this.spinner.hide()
      this.productConsumedList = data.content;
      this.totaolProdctConsume = data.totalElements;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));

    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this.popupSuccessModel.show()

    })
  }

  ApportunityByCase(column: string, order: string) {
    this.spinner.show()
    this.caseservice.getProductOpportunityBycaseId(this.caseid, this.pageOprt, column, order).subscribe(data => {
      this.spinner.hide();
      this.caseOpportunities = data.content;
      this.totalOpportunity = data.totalElements;
      this.firstOprt = data.first;
      this.lastOprt = data.last;
      this.sizeOprt = data.size;
      this.serialNumberOprt = (((this.page) * this.sizeOprt));
    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this.popupSuccessModel.show()

    })
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.productConsumed(this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();

    this.productConsumed(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.productConsumed(this.sortColumnName, this.sortOrder);
  }
  //pageable function for  serviceAppiontment
  setPagePreviousServiceApnt(event: any) {
    event.preventDefault();
    this.pageservcAptmnt = this.pageservcAptmnt - 1;
    this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
  }

  setPageServiceApnt(event: any) {
    event.preventDefault();

    this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
  }

  setPageNextServiceApnt(event: any) {
    event.preventDefault();
    this.pageservcAptmnt = this.pageservcAptmnt + 1;
    this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
  }

  //pageable function for apportunities
  setPagePreviousOprt(event: any) {
    event.preventDefault();
    this.pageOprt = this.pageOprt - 1;
    this.ApportunityByCase(this.sortColumnName, this.sortOrder);
  }

  setPageOprt(event: any) {
    event.preventDefault();

    this.ApportunityByCase(this.sortColumnName, this.sortOrder);
  }

  setPageNextOprt(event: any) {
    event.preventDefault();
    this.pageOprt = this.pageOprt + 1;
    this.ApportunityByCase(this.sortColumnName, this.sortOrder);
  }

  getBattryFSR(column: string, order: string) {
    this.spinner.show();
    this.caseservice.getBattryFSRByCaseId(this.caseid, this.pageBtryFSR, column, order).subscribe(data => {
      this.spinner.hide();
      this.battryFSRDATA = data.content;
      this.totalBattryFSR = data.totalElements;
      this.firstBFSR = data.first;
      this.lastBFSR = data.last;
      this.sizeBFSR = data.size;
      this.serialNumberBFSR = (((this.pageBtryFSR) * this.sizeBFSR));
    }, error => {
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  setPagePreviousBFSR(event: any) {
    event.preventDefault();
    this.pageBtryFSR = this.pageBtryFSR - 1;
    this.getBattryFSR(this.sortColumnName, this.sortOrder);
  }

  setPageBFSR(event: any) {
    event.preventDefault();

    this.getBattryFSR(this.sortColumnName, this.sortOrder);
  }

  setPageNextBFSR(event: any) {
    event.preventDefault();
    this.pageBtryFSR = this.pageBtryFSR + 1;
    this.getBattryFSR(this.sortColumnName, this.sortOrder);
  }
  ServiceApmntNew() {
    this.isEditMode = false;
    this.createServiceAppointmentModel.show();
    this.heading = "New Service Appointment";
    this.secodServiceAppointmentRequest.caseId = this.caseres.caseId;
    this.secodServiceAppointmentRequest.caseManaualId = this.caseres.caseManualId;
    this.secodServiceAppointmentRequest.serviceResourceId = this.caseres.serviceResourceId;
    this.secodServiceAppointmentRequest.serviceResourceName = this.caseres.serviceResourceName;
  }
  createServiceAppointmentModelhide() {
    this.secodServiceAppointmentRequest = new SecondServiceAppointmentRequest();
    this.createServiceAppointmentModel.hide();

  }
  onSecondServiceAppointmentCreated($event: ServiceAppointmentResponse) {
    this.serviceAppontmentList.unshift($event);
    this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
    this.getBattryFSR(this.sortColumnName, this.sortOrder);
     this.getFileUploadList();
      this.productConsumed(this.sortColumnName, this.sortOrder);
      this.ApportunityByCase(this.sortColumnName, this.sortOrder);
      this.CaseHistory();

  }

  popupClose() {
    this.popupSuccessModel.hide();
  }

  casemanId: string;

  showCreateOpportunityView() {

    this.newOpportunity = new OppertunityRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
    this.newOpportunity.caseId = this.caseid;
    // this.newOpportunity.caseNumber = this.caseNumber;
    // this.newOpportunity.caseManualId = this.caseNumber;
    this.newOpportunity.accountId = this.accountId;
    this.newOpportunity.accountName = this.accountname;

    this.createNewOpportunity.show();

  }

  onOpportunityCreated($event: OppertunityResponse) {
    if (!this.isEditMode) {
      this.ApportunityByCase(this.sortColumnName, this.sortOrder);
      this.totalOpportunity = this.totalOpportunity + 1;
      this.caseOpportunities.unshift($event);
    } else {
      let index = 0;
      this.caseOpportunities.forEach(element => {
        if (element.oppertunityId === $event.oppertunityId) {
          this.caseOpportunities[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getBattryFSR(this.sortColumnName, this.sortOrder);
      this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
      this.productConsumed(this.sortColumnName, this.sortOrder);
      this.ApportunityByCase(this.sortColumnName, this.sortOrder);
      this.CaseHistory();
  }

  cancelData() {
    this.createNewOpportunity.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
    this.newOpportunity = new OppertunityRequest();
  }
  // proove of delivery functions
  casePDRequest:CaseProofOfDeliveryRequest=new CaseProofOfDeliveryRequest();
  pofDModelshow(){
    this.heading="Proof Of Delivery (For SCH use only)"
     this.isEditMode=true;
    this.spinner.show();
    this.caseservice.getDetailByCaseId(this.caseid).subscribe(data=>{
      this.spinner.hide();
      this.caseres=data;
      this.casePDRequest.podNumber=this.caseres.podnumber;
      this.casePDRequest.podDate=this.caseres.poddate;
      this.casePDRequest.podShippingExpectedDate=this.caseres.podShippingExpectedDate;
      this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
      this.casePDRequest.podShippingToName=this.caseres.podShippingToName;
      this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;
      this.createPD.show();
    },error=>{
      this.spinner.hide();
    });
   
  }
  onCasePDCreated($event){
  this.caseres=$event;
  this.showCaseResponse();
  this.ApportunityByCase(this.sortColumnName, this.sortOrder);
  this.getBattryFSR(this.sortColumnName, this.sortOrder);
  this.serviceAppointmentById(this.sortColumnName, this.sortOrder);
  this.productConsumed(this.sortColumnName, this.sortOrder);
  this.ApportunityByCase(this.sortColumnName, this.sortOrder);
  this.CaseHistory();
  
  }
  cancelPDData(){
    this.casePDRequest=new CaseProofOfDeliveryRequest();
    this.createPD.hide();
  }
  fileToUpload:File;
  onFileSelect(event) {
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
      }
    }
  }
  remove(unselectReason) {
    // if (unselectReason !== undefined) {
    //   const index = this.fileList.indexOf(unselectReason);
    //   this.fileList.splice(index, 1);
    // }
    if (this.fileList.length > 0) {
      for (const value of this.fileList) {
        if (value['name'] === unselectReason) {
          this.fileList.splice(this.fileList.indexOf(value), 1);
        }
      }
      // this.fileUploadModel.show();
    }
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
      this.downloadFile(this.fileName)
    } else {
       this.downloadFile(this.fileName);
    }
  }
  fileName: any
  fileid:number;
  opneImage(imgarr) {
    console.log(imgarr);
    this.fileid=imgarr.id;
    this.fileName=imgarr.uploaded_file_name;
    this.isImageLoading = true;
    // this.choosenFilesShow.show();
    this.spinner.show();
    this.caseservice.filedownloadnew(this.fileid,this.caseid).subscribe(data => {
      this.spinner.hide();
      this.createImageFromBlob(data);
        this.isImageLoading = false;
    },error=>{
      this.spinner.hide();
      this.message=error.message;
      this.popupSuccessModel.show()
    })
  }

  downloadFile(fileName: any) {
    this.spinner.show();
    this.caseservice.getDownloadFile(fileName).subscribe(data => {
      fileSaver.saveAs(data, fileName);
      this.spinner.hide();
    },
      err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  cancelChoosen() {
    this.fileList = [];
    this.formData = new FormData();
    this.choosenFiles.hide();
  }

  onSubmit() {

    this.spinner.show();
    const formData = new FormData();
    for (let i = 0; i < this.fileList.length; i++) {
      formData.append('files', this.fileList[i]);
    }
    // this.caseservice.uploadImageFile(this.caseres.caseId, formData).subscribe(data => {
    //   this.spinner.hide();
    //   this.choosenFiles.hide();
    //   this.fileList = [];
    //   this.getFileUploadList();
    // }, error => {
    //   this.spinner.hide();
    //   this.message = error.error.message;
    //   this.popupSuccessModel.show();
    // })
  
     
      this.caseservice.uploadFilecase(this.caseid,this.fileToUpload).subscribe(data=>{
       this.fileList = [];
       this.spinner.hide();
        (<HTMLInputElement>document.getElementById('select')).value=null
            // this.spinner.hide();
            this.message = 'File Uploaded Successfully';
            // this.popupNgClass = 'alert alert-success center center-block';
            this.choosenFiles.hide();
            this._global.alert({ type: '', message: this.message });
            this.getFileUploadList();
          }, error => {
            this.fileList = [];
            this.spinner.hide();
            // this.message = error.error.message;
            this.message=error.message;
            this._global.alert({ type: 'alert', message: this.message });
          });
        this.formData = new FormData();
    

  }
 
  getFileUploadList() {
    this.fileList = [];
    this.spinner.show();
    this.caseservice.getFileListcasenew(this.caseid).subscribe(data => {

      this.spinner.hide();
      this.fileLists = data;
      console.log(this.fileLists.length)
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }

  //
  CaseHistory(){
    this.objectname="CaseDetails"
    this.approvalHistoryService.getApprovalStatus(this.objectname,this.caseid).subscribe(data=>{
this.historyList=data;
    })
  }
  DeletAttachment(imageid){
    // console.log(imageid);
    const objectId=this.actionHistoryObjectAndId.objectId
    this.spinner.show();
    this.deleteservice.deleteAttachment(imageid,this.actionHistoryObjectAndId.objectName,objectId).subscribe(data=>{
      this.spinner.hide();
      this.getFileUploadList();
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }

  encodeurlc(urlc){
    return encodeURIComponent(urlc);
  }
  RejectCase(){
    this.rejectcasemodel.show();
  }

  rejectcasemodelCancel(){
    this.rejectcasemodel.hide();
  }
  OnSuccess($event){
    this.showCaseResponse();

  }
  rejectLogs(){
    this.RejectLogList=[];
    this.caseservice.getRejectLogListByCaseId(this.caseres.caseManualId).subscribe(data=>{
      this.RejectLogList=data;
    })
  }
  onChnagedUpdate($event){
    this.showCaseResponse();
  }
  prcdata:Page<PreRegCaseResponse>=new Page<PreRegCaseResponse>()
  getPrcByCase(){
    this.spinner.show();
    this.prcdata.content=[];
    this.prcservice.getPRCByCaseId(this.caseid,this.page).subscribe(data=>{
    this.spinner.hide();
    this.prcdata=data;
    })
  }
}
