import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { caseService } from '../../services/caseService.service';
import { Loginservices } from '../../services/login.services';
import { ActionHistoryObjectAndId, BatteryFSRResponse, CaseProofOfDeliveryRequest, FileUploadResponse, OppertunityRequest, OppertunityResponse, PrcActionRequest, PreRegCaseRequest, PreRegCaseResponse, ProblemReported, QuoteResponse, SecondServiceAppointmentRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentResponse, ToBeApprovedHistoryResponse } from '../../services/model';
import { OpportunityService } from '../../services/Opportunity.services';
import { PreRegCaseService } from '../../services/preregcaseservice.services';

@Component({
  selector: 'app-case-pre-regis-detail',
  templateUrl: './case-pre-regis-detail.component.html',
  styleUrls: ['./case-pre-regis-detail.component.scss']
})
export class CasePreRegisDetailComponent implements OnInit {
  @ViewChild('createpreregiCaseModel') createpreregiCaseModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createServiceAppointmentModel') createServiceAppointmentModel: ModalDirective;
  @ViewChild('createPD') createPD:ModalDirective;
  res: PreRegCaseResponse = new PreRegCaseResponse();
  req: PreRegCaseRequest = new PreRegCaseRequest();
  caseres: PreRegCaseResponse = new PreRegCaseResponse()
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
  CREATE_ACCOUNT_HEADING: string = 'Create Pre-Registration Case';
  EDIT_ACCOUNT_HEADING: string = 'Edit Pre-Rgistration Case'
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
  objectname:string;
  comment:string;
 GenerateCase:string;
 actionreq:PrcActionRequest=new PrcActionRequest();
 prcreq:PrcActionRequest=new PrcActionRequest();
 ActionName:string;
 actionMessage:string;
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
  uploadForm: FormGroup;
  formData = new FormData();
  buisnessDomainId: number;
  caseNumber: string;
  accountId: string;
  accountname: string;
  prcres:PreRegCaseResponse=new PreRegCaseResponse();
  siteseviceResourceID:number;
  serviceCenterId:number;
  casemanId: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('createNewOpportunity') createNewOpportunity: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles:ModalDirective;
  @ViewChild('choosenFilesShow') choosenFilesShow:ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup:ModalDirective;
  @ViewChild('IgnorModel') IgnorModel :ModalDirective;
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
  modelsOpenState = { caseEditModelOpen: false, serviceAppointmentModelOpen: false }
  
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

  constructor( private approvalHistoryService:ApprovalHistoryService,private formBuilder: FormBuilder,private loginservice: Loginservices, private caseservice: caseService, private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService, private oportunityServic: OpportunityService
    ,private preregservice:PreRegCaseService) {
    this._activatedRoute.params.subscribe(pram => {
      this.caseid = pram['id'];
      this.actionHistoryObjectAndId.objectId = this.caseid;
      this.actionHistoryObjectAndId.objectName = 'CaseDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getLoggedInUser()
    this.showCaseResponse();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
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
      this.getCaseByPrc(this.sortColumnName, this.sortOrder);
     

    }
    return false;
  }
 
  editCaseModelshow(res: PreRegCaseResponse) {
    // this.res = res
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.preregservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.res = data;
      this.siteseviceResourceID=data.serviceResourceId;
      this.serviceCenterId=data.serviceCenterId;
      this.res.contactPersonId=data.contactPersonId;
      this.spinner.hide();
      this.createpreregiCaseModel.show();
    }, error => {
      this.spinner.hide();
      this.createpreregiCaseModel.show();
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
  
    });
  }


  cancelDatacase() {
    this.isEditMode = false;
    this.createpreregiCaseModel.hide();
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  
  showCaseResponse() {
    this.spinner.show();
    this.preregservice.getDetailByCaseId(this.caseid).subscribe(data => {
      this.spinner.hide();
      this.caseres = data;
      this.caseNumber = data.prcCaseManualId;
      this.accountId = data.accountSapNumber;
      this.accountname = data.accountName;

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
 
  onCaseCreated($event: PreRegCaseResponse) {
    this.caseres = $event
   this.showCaseResponse();
    if (this.caseres.caseTypeId !== 1) {

      this.getCaseByPrc(this.sortColumnName, this.sortOrder);
      if ($event.caseStatusId !== 1 || $event.caseStageId !== 1) {
      }
    }


  }

  getCaseByPrc(column: string, order: string) {
    this.spinner.show();
    this.preregservice.getCaseByPrcId(this.caseid).subscribe(data => {
      this.spinner.hide();
      if(data!=null)
      {
        this.prcres=data;
      }
      else{
        this.prcres=new PreRegCaseResponse();
      }
     }, error => {
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  

  ServiceApmntNew() {
    this.isEditMode = false;
    this.createServiceAppointmentModel.show();
    this.heading = "New Service Appointment";
    this.secodServiceAppointmentRequest.caseId = this.caseres.prcCaseId;
    this.secodServiceAppointmentRequest.caseManaualId = this.caseres.prcCaseManualId;
    this.secodServiceAppointmentRequest.serviceResourceId = this.caseres.serviceResourceId;
    this.secodServiceAppointmentRequest.serviceResourceName = this.caseres.serviceResourceName;
  }
  createServiceAppointmentModelhide() {
    this.secodServiceAppointmentRequest = new SecondServiceAppointmentRequest();
    this.createServiceAppointmentModel.hide();

  }

  popupClose() {
    this.popupSuccessModel.hide();
  }



  showCreateOpportunityView() {

    this.newOpportunity = new OppertunityRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
    this.newOpportunity.caseId = this.caseid;
    this.newOpportunity.accountId = this.accountId;
    this.newOpportunity.accountName = this.accountname;

    this.createNewOpportunity.show();

  }


  cancelData() {
    this.createNewOpportunity.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
    this.newOpportunity = new OppertunityRequest();
  }

  Generat(action){
   this.ActionName=action;
  //  this.actioeq=new PrcActionRequest();
  this.actionreq=new PrcActionRequest();
   this.actionreq.caseid=this.caseid;

  
   this.IgnorModel.show();
    if(action=='Generate Case'){
      this.heading="Generate Case";
      this.actionMessage="Do you want to Generate Case";
    }
    else{
      this.heading="Ignore Case";
      this.actionMessage="Do you want to Ignore this Record";
    }
 
  }

  closeIgnor(){
    this.actionMessage="";
    this.ActionName="";
    this.actionreq.comment=null;
    this.IgnorModel.hide();
    // this.prcreq=new PrcActionRequest();
    // this.actionreq=new PrcActionRequest();
  }
  OnGenerated($event){
    this.caseres=$event;
    this.showCaseResponse();
    this.actionreq=new PrcActionRequest();
  }
}
