import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { FileDeleteService } from '../../../services/filedeletService.services';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, BatteryFSRRequest, BatteryFSRResponse, FileUploadResponse, Page, ProblemReported, ServiceAppointmentProductConsumedRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentReAssignRequest, ServiceAppointmentResponse, ServiceResourseRequest, ServiceResourseResponse, ToBeApprovedHistoryResponse } from '../../../services/model';
import { DomSanitizer } from '@angular/platform-browser';
import * as fileSaver from 'file-saver';
import { BatrryFSRService } from '../../../services/BattryFSRservice.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BtFsrService } from '../../bat_services/bt-fsr.service';
import { BTCaseResponse, BtFsrCommissionDataGetById, BtFsrData, BtFsrServiceDataGetById, BtServiceAppointmentProductConsumedResponse, BtServiceAppointmentResponseSchOnly, btServiceAppointmentResponseWithSCH } from '../../bt_model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';

@Component({
  selector: 'app-bat-serviceappointmentdetails',
  templateUrl: './bat-serviceappointmentdetails.component.html',
  styleUrls: ['./bat-serviceappointmentdetails.component.scss']
})
export class BatServiceappointmentdetailsComponent implements OnInit {

  @ViewChild('createfsrserviceModel') createfsrserviceModel: ModalDirective;
  @ViewChild('createServiceAppointmentModel') public createServiceAppointmentModel: ModalDirective;
  @ViewChild('productConsumedModel') public productConsumedModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createServiceResourceModel') createServiceResourceModel: ModalDirective;
  @ViewChild('createBFSRModel') createBFSRModel: ModalDirective;
  @ViewChild('reAssingServiceAppointmentModel') reAssingServiceAppointmentModel: ModalDirective;
  @ViewChild('popupRequestforSA') popupRequestforSA: ModalDirective;
  @ViewChild('fileUploadModel') fileUploadModel: ModalDirective;
  @ViewChild('filePreviewModel') filePreviewModel: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup: ModalDirective;
  @ViewChild('approvalModule')approvalModule:ModalDirective;

  // @ViewChild('imgeid',{static:false}) imgeid;
  public message: string = '';
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  BFSRres: BatteryFSRResponse = new BatteryFSRResponse();
  BFRSreq: BatteryFSRRequest = new BatteryFSRRequest();
  appointment: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  appointmentWithSch: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();

  serviceAppointmentResponse: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  appointmentList: Array<btServiceAppointmentResponseWithSCH> = [];
  serialNumber: number;
  newServiceResource: ServiceResourseRequest = new ServiceResourseRequest();
  serviceResourceResponse: ServiceResourseResponse = new ServiceResourseResponse();
  remarks: string="";
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Service Appointment';
  EDIT_ACCOUNT_HEADING: string = 'Edit Service Appointment';
  EDIT_ServiceResource_HEADING: string = 'Edit Service Resource';
  splitdate:string;

  popupNgClass: string = 'alert alert-danger center center-block';
  isProductConsumedModelShow: boolean = false;
  isServiceReourceModelShow: boolean = false;
  isEditModelShow: boolean = false;
  isReassingAppointmentModelShow: boolean = false;
  iscreateBFSRModelShow: boolean = false;
  serviceAppointmentProductConsumedRequest: ServiceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
  serviceAppointmentProductConsumedList: Array<ServiceAppointmentProductConsumedRequest> = [];
  serviceResourceList: Array<ServiceResourseResponse> = [];
  sapId: number;
  page: number = 0;
  pageFSR: number = 0;
  firstFSR: boolean;
  lastFSR: boolean;
  sizeFsr: number;
  first: boolean;
  last: boolean;
  size: number;
  totalBFSR: number;
  BatteryFSRList: Array<BatteryFSRResponse> = [];
  serviceResourcePage: number = 0;
  serviceResourceFirst: boolean;
  serviceResourceLast: boolean;
  serviceResourceSize: number;
  totalServiceResources: number;
  serviceResourceSerial: number;
  problemReportedDetails: Array<ProblemReported>

  consumedPage: number = 0;
  consumedFirst: boolean;
  consumedLast: boolean;
  consumedSize: number;
  totalconsumed: number;
  consumedSerial: number;
  consumedList: Array<BtServiceAppointmentProductConsumedResponse> = [];
  serviceAppointmentReAssignRequest: ServiceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
  approvalHistoryList: Array<ToBeApprovedHistoryResponse> = [];
  btCaseResponse: BTCaseResponse =new BTCaseResponse();

  SaApprovalId:number;
  btServiceAppointmentResponseSchOnly:Page<BtServiceAppointmentResponseSchOnly> = new Page<BtServiceAppointmentResponseSchOnly>();

  objectName = 'BtServiceAppointment';
  fileUploadList: Array<FileUploadResponse> = [];
  uploadForm: UntypedFormGroup;
  fileList: string[] = [];
  uploadFilesLength: number;
  formData: FormData = new FormData()
  file:FormData = new FormData();
  fileName: string;
  imageUrl: string;
  LogedEmailId: string;
  isFsrOpen: boolean;
  isEditFsr:boolean=false;
  CREATE_FSR_HEADING: string = 'Create FSR';
  EDIT_FSR_HEADING:string = 'Edit FSR';
  fsrHeading:string;


  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  schUseOnly = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  addressDisplay1 = {
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
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  ratingDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  esignDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  zoom: any;
  caseId: number;
  FSRUploadbtn: boolean = false;
  reAssignbtn: boolean = false;
  imageSource: any;
  loginData: any;
  domainId: any;
  _cFlag: string;
  isCommisionFsr:boolean=false;
  SchPage:any;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private tobeapproved:BtTobeApprovedService,private datePipe: DatePipe, private btFsrService:BtFsrService ,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private BFSRservice: BatrryFSRService,
    private _btserviceAppointment: BtServiceappointmentService, private loginService: Loginservices,private approvalHistory: ApprovalHistoryService,
     private formBuilder: UntypedFormBuilder, private _http: HttpClient,
    private loginservice: Loginservices, private deleteservice: FileDeleteService,
    private _global: GlobalserviceService, private caseService: BtCaseserviceService,
    private sanitizer: DomSanitizer,
    private _serviceAppointmentService: ServiceAppointmentService) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getServiceAppointmentById(this.sapId);
      this.actionHistoryObjectAndId.objectId = '' + this.sapId;
      this.actionHistoryObjectAndId.objectName = 'BtServiceAppointment';
    
      this.getSchDetails(this.sapId);
      this.getApprovalButton();
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    this.LogedEmailId = this.loginData.emailId;
    if(this.loginData.roleGroupAndAccount.roleGroup=="CUSTOMERCARE")
    {
      this.forCallcenter=true;
    }
    else
    {
      this.forCallcenter=false;
    }

  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
     
    });
    this.btServiceAppointmentResponseSchOnly.content=[];
    this.getFileList(this.objectName, this.sapId);

  }


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 5,
    querypram: false
  };

  fsrid:number;
  iscreatefsr:boolean=false;
  fsrurl:string='';
  isCaseClose=false;
  canMapview: boolean = false;
  fsrkey:string;
  forCallcenter:boolean=false;
  schId:number;

  getServiceAppointmentById(sapId: number) {
    this.spinner.show();
    this._btserviceAppointment.getServiceAppointmentById(sapId).subscribe(data => {
      this.appointment = data;
      this.appointment.ownerEmailId = data.ownerEmailId;
      if(data.visitDate!=null)
      {

        let visitOfDate = new String(this.appointment.visitDate);
         this.splitdate =  visitOfDate.slice(0,10);
      // visitOfDate = new Date(data.visitDate);
 
      
     }
      
      
  
      

      if(this.appointment.commFsrId!=0)
      {
        this.fsrid=data.commFsrId;
        this.fsrkey='commFSRId';
        this.iscreatefsr=true;
        this.appointment.iscreatefsr = this.iscreatefsr;
        this.fsrurl='/bt/api/fsr/com'
      }
      else if(this.appointment.serviceFsrId!=0)
      {
        this.fsrid=data.serviceFsrId;
        this.fsrkey='serviceFSRId';
        this.iscreatefsr=true;
        this.appointment.iscreatefsr = this.iscreatefsr;
        this.fsrurl='bt/api/fsr/ser';
      }
      else
      {
        this.iscreatefsr=false;
        this.appointment.iscreatefsr = this.iscreatefsr;

      }
      if(this.appointment.caseReasonName=="Paid Repair" || this.appointment.caseReasonName=="Corrective Maintenance" ||  this.appointment.caseTypeName=="Under Warranty" || this.appointment.caseTypeName=="Without Warranty")
      {
        this.isCommisionFsr=false;
      }
      else
      {
        this.isCommisionFsr=true;
      }
      this.spinner.hide();
      this.getBase64(data.eSignature);
      if (data.ownerEmailId == this.LogedEmailId) {
        this.FSRUploadbtn = true;
        this.reAssignbtn = true;

      }
      else {
        this.FSRUploadbtn = false;
        this.reAssignbtn = false;
      }
      if (data.serviceAppointmentStatusId == 4 || data.serviceAppointmentStatusId == 5 || data.serviceAppointmentStatusId == 6 || data.serviceAppointmentStatusId == 7) {
        this.canMapview = true;
      }
      else {
        this.canMapview = false;
      }
      
      if(data.caseStatus=="Closed")
      {
       
        this.isCaseClose=true;
      }
      this.caseId = this.appointment.caseId;
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });

    this.getSchDetails(this.sapId);
  
  }

  // /Get SCh Detailes By ID 

  getSchDetails(serviceappointmentId){

    // `filtername=${this.dropdownSelected.filtreparam}`
    this._btserviceAppointment.getSchDetailsById(serviceappointmentId,`page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe((data)=>{
      
    
      this.btServiceAppointmentResponseSchOnly = data;
      if(this.btServiceAppointmentResponseSchOnly.content.length!=0)
      {
    
        for(let i=0;i<this.btServiceAppointmentResponseSchOnly.content.length;i++)
      {
        
        this.appointment.correctiveAction = this.btServiceAppointmentResponseSchOnly.content[0].correctiveAction;
        this.appointment.resolutionMethodId = this.btServiceAppointmentResponseSchOnly.content[0].resolutionMethodId;
        this.appointment.sparePartName = this.btServiceAppointmentResponseSchOnly.content[0].sparePartName;
        this.appointment.sparePartQuantity = this.btServiceAppointmentResponseSchOnly.content[0].sparePartQuantity;
        this.appointment.sparePartReplaced = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplaced;
        this.appointment.sparePartReplacedFrom = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplacedFrom;
        this.appointment.customerRate = this.btServiceAppointmentResponseSchOnly.content[0].customerRate;
        this.appointment.customerBillingRate = this.btServiceAppointmentResponseSchOnly.content[0].customerBillingRate;
        this.appointment.btSolutionImpartedProblemResponses = this.btServiceAppointmentResponseSchOnly.content[0].problem;
        
     
      } 
      
    }
      
    },(error)=>{
         
    })
  }


  getServiceResourceList(sapId: number, serviceResourcePage: number) {
    this.spinner.show();
    this._btserviceAppointment.getServiceResourceByAppointmentId(sapId, serviceResourcePage).subscribe(data => {
      this.serviceResourceList = data.content;
      this.totalServiceResources = data.totalElements;
      this.serviceResourceFirst = data.first;
      this.serviceResourceLast = data.last;
      this.serviceResourceSize = data.size;
      this.serviceResourceSerial = (((serviceResourcePage) * this.serviceResourceSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getConsumedList(sapId: number, consumedPage: number) {
    this.spinner.show();
    this._btserviceAppointment.getconsumeProductDetails(sapId, consumedPage).subscribe(data => {
      this.consumedList = data.content;
      this.totalconsumed = data.totalElements;
      this.consumedFirst = data.first;
      this.consumedLast = data.last;
      this.consumedSize = data.size;
      this.consumedSerial = (((consumedPage) * this.consumedSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  getApprovalHistoryList(objectName: string, objectId: number) {
    this.spinner.show();
    this.approvalHistory.getApprovalStatusbt(objectName, objectId).subscribe(data => {
      this.approvalHistoryList = data;
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getFileList(objectName: string, objectId: number) {
    this.spinner.show();
    this._serviceAppointmentService.getFileList(objectName, objectId).subscribe(data => {
      this.fileUploadList = data;
      this.fileUploadList.forEach(data => {
        let fileSize: string = data.fileSize;
      })
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
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
  changeschDisplay() {
    this.schUseOnly.open = !this.schUseOnly.open;
    if (this.schUseOnly.open) {
      this.schUseOnly.angleIcon = 'fa fa-angle-down';
    } else {
      this.schUseOnly.angleIcon = 'fa fa-angle-right';
    }
  }
  changeAddressDisplay1() {
    this.addressDisplay1.open = !this.addressDisplay1.open;
    if (this.addressDisplay1.open) {
      this.addressDisplay1.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay1.angleIcon = 'fa fa-angle-right';
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
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open;
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  ratingSystemDisplay() {
    this.ratingDisplay.open = !this.ratingDisplay.open;
    if (this.ratingDisplay.open) {
      this.ratingDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.ratingDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  eSignatureDisplay() {

    this.esignDisplay.open = !this.esignDisplay.open;
    if (this.esignDisplay.open) {
      this.esignDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.esignDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getServiceAppointmentById(this.sapId);
      this.getSchDetails(this.sapId);
    } else {
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      this.getConsumedList(this.sapId, this.consumedPage);
     // this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }
  todayDate = new Date();
  caseReason: string = '';
  createEditModelshow(serviceappointment: btServiceAppointmentResponseWithSCH)
   {
     
    this.spinner.show();
    this._btserviceAppointment.getServiceAppointmentById(serviceappointment.appointmentId).subscribe(data => {

      this.serviceAppointmentResponse.ownerEmailId = data.ownerEmailId;
      
      this.caseReason = data.caseReason;
      this.serviceAppointmentResponse.createDate = data.createDate;
      this.serviceAppointmentResponse.caseId = data.caseId;
      this.serviceAppointmentResponse.caseManualId = data.caseManualId;
      this.serviceAppointmentResponse.assetId = data.assetId;
      this.serviceAppointmentResponse.siteIdManual = data.siteIdManual;
      this.serviceAppointmentResponse.caseIdManual = data.caseIdManual;
      this.serviceAppointmentResponse.appointmentId = data.appointmentId;
      this.serviceAppointmentResponse.accountName = data.accountName;
      this.serviceAppointmentResponse.accountSapNumber = data.accountSapNumber;
      this.serviceAppointmentResponse.serviceAppointmentManulaId = data.serviceAppointmentManulaId;
      this.serviceAppointmentResponse.siteId = data.siteId;
      this.serviceAppointmentResponse.siteIdManual = data.siteIdManual;
      this.serviceAppointmentResponse.appointmentManualId = data.appointmentManualId;
      this.serviceAppointmentResponse.siteName = data.siteName;
      this.serviceAppointmentResponse.sastatus = data.sastatus;
      this.serviceAppointmentResponse.remark = data.remark;
      this.serviceAppointmentResponse.pendingResponsibilityId = data.pendingResponsibilityId;
      this.serviceAppointmentResponse.pendingResponsibilityName = data.pendingResponsibilityName;
      this.serviceAppointmentResponse.faultObserve = data.faultObserve;
      this.serviceAppointmentResponse.fixedPayout = data.fixedPayout;
      this.serviceAppointmentResponse.punchPointCustomer = data.punchPointCustomer;
      this.serviceAppointmentResponse.correctiveAction = data.correctiveAction;
      this.serviceAppointmentResponse.sstatus = data.sstatus;
      this.serviceAppointmentResponse.serviceAppointmentStatusId = data.serviceAppointmentStatusId;
      this.serviceAppointmentResponse.serviceAppointmentStatusName = data.serviceAppointmentStatusName;
      this.serviceAppointmentResponse.caseReason = data.caseReason;
      this.serviceAppointmentResponse.assetsSapSerialNumber = data.assetsSapSerialNumber;
      this.serviceAppointmentResponse.assetsPartCode = data.assetsPartCode;
      this.serviceAppointmentResponse.assetDescription = data.assetDescription;
      this.serviceAppointmentResponse.caseReasonName = data.caseReasonName;
      this.serviceAppointmentResponse.serviceResourceName = data.serviceResourceName;
      this.serviceAppointmentResponse.contactFirstName = data.contactFirstName;
      this.serviceAppointmentResponse.contactMiddleName = data.contactMiddleName;
      this.serviceAppointmentResponse.contactLastName = data.contactLastName;
      this.serviceAppointmentResponse.iscreatefsr= this.iscreatefsr;
      this.serviceAppointmentResponse.isCommisionFsr= this.isCommisionFsr;
      this.serviceAppointmentResponse.customerBillingRate= data.customerBillingRate;
      this.serviceAppointmentResponse.customerRate= data.customerRate;
      this.serviceAppointmentResponse.billingRequired= data.billingRequired;
      

     

      this.serviceAppointmentResponse.fsrCreated = data.fsrCreated;
      this.serviceAppointmentResponse.acknowledgedByName = data.acknowledgedByName
      this.serviceAppointmentResponse.acknowledgedByPhone = data.acknowledgedByPhone
      this.serviceAppointmentResponse.customerRepresentativeName = data.customerRepresentativeName
      this.serviceAppointmentResponse.faultObserve = data.faultObserve
      this.serviceAppointmentResponse.customerRepresentativeContact = data.customerRepresentativeContact
      this.serviceAppointmentResponse.address = data.address
      this.serviceAppointmentResponse.punchPointExicom = data.punchPointExicom
      this.serviceAppointmentResponse.ssaId = data.ssaId
      this.serviceAppointmentResponse.servicePayoutId = data.servicePayoutId
      this.serviceAppointmentResponse.customerBucketReasonId = data.customerBucketReasonId;
      this.serviceAppointmentResponse.customerBucketReasonName = data.customerBucketReasonName;
      this.serviceAppointmentResponse.rmaCreated = data.rmaCreated;
      this.serviceAppointmentResponse.pendingResource = data.pendingResource;
      this.serviceAppointmentResponse.fsrMandatory = data.fsrMandatory;
      this.serviceAppointmentResponse.uploadfsrMandatory=data.uploadfsrMandatory;

      // For SCH Only

      if(this.btServiceAppointmentResponseSchOnly.content.length!=0)
      {

        this.serviceAppointmentResponse.sparePartReplacedFrom = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplacedFrom;
        this.serviceAppointmentResponse.correctiveAction = this.btServiceAppointmentResponseSchOnly.content[0].correctiveAction;
        this.serviceAppointmentResponse.btSolutionImpartedProblemResponses = this.btServiceAppointmentResponseSchOnly.content[0].problem;
        // this.serviceAppointmentResponse.btSolutionImpartedProblemResponses = this.btServiceAppointmentResponseSchOnly.content[0].problem;
        this.serviceAppointmentResponse.sparePart = this.btServiceAppointmentResponseSchOnly.content[0].sparePartName;
        this.serviceAppointmentResponse.sparePartQuantity = this.btServiceAppointmentResponseSchOnly.content[0].sparePartQuantity;
        this.serviceAppointmentResponse.sparePartReplaced = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplaced;
        this.serviceAppointmentResponse.resolutionMethodId = this.btServiceAppointmentResponseSchOnly.content[0].resolutionMethodId;
        this.serviceAppointmentResponse.customerBillingRate = this.btServiceAppointmentResponseSchOnly.content[0].customerBillingRate;
        this.serviceAppointmentResponse.customerRate = this.btServiceAppointmentResponseSchOnly.content[0].customerRate;
              
      }

   
      // console.log(this.serviceAppointmentResponse.caseReasonName)
      if (data.closedFromPlatform != null) {
        this.serviceAppointmentResponse.closedFromPlatform = data.closedFromPlatform;
      } else {
        this.serviceAppointmentResponse.closedFromPlatform = "Web";
      }
      if (data.visitDate != null) {

        // let visitDate =this.datePipe.transform(data.visitDate,'yyyy-MM-dd');
        //  this.serviceAppointmentResponse.visitDate = new Date(visitDate);
        this.serviceAppointmentResponse.visitDate = data.visitDate;
      }
      else {
        this.serviceAppointmentResponse.visitDate = this.todayDate;
      }
      this.spinner.hide();
      this.isEditMode = true;
      this.heading = this.EDIT_ACCOUNT_HEADING;
      this.createServiceAppointmentModel.show();

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  
    // if(this.btServiceAppointmentResponseSchOnly.content.length!=0)
    // {

    //   this.serviceAppointmentResponse.sparePartReplacedFrom = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplacedFrom;
    //   this.serviceAppointmentResponse.correctiveAction = this.btServiceAppointmentResponseSchOnly.content[0].correctiveAction;
    //   this.serviceAppointmentResponse.btSolutionImpartedProblemResponses = this.btServiceAppointmentResponseSchOnly.content[0].problem;
    //   this.serviceAppointmentResponse.sparePart = this.btServiceAppointmentResponseSchOnly.content[0].sparePartName;
    //   this.serviceAppointmentResponse.sparePartQuantity = this.btServiceAppointmentResponseSchOnly.content[0].sparePartQuantity;
    //   this.serviceAppointmentResponse.sparePartReplaced = this.btServiceAppointmentResponseSchOnly.content[0].sparePartReplaced;
    //   this.serviceAppointmentResponse.resolutionMethodId = this.btServiceAppointmentResponseSchOnly.content[0].resolutionMethodId;
    //   console.log(this.serviceAppointmentResponse);
      
    // }

  }

  cancelData() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.createServiceAppointmentModel.hide();

  }

  createServiceAppointmentModelhide() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.createServiceAppointmentModel.hide();

  }

  onServiceAppointmentCreated($event: btServiceAppointmentResponseWithSCH) {
    let serviceAppointmentEvent = new btServiceAppointmentResponseWithSCH();
    // console.log($event.serviceAppointmentId);
    
    this.getServiceAppointmentById($event.serviceAppointmentId);
    this.getSchDetails(this.sapId);

    // this.spinner.show();
    // this._btserviceAppointment.getServiceAppointmentById($event.appointmentId).subscribe(data => {
    //   this.spinner.hide();
    //   serviceAppointmentEvent = data;
    // }, error => {
    //   this.spinner.hide();
    //   this.message = error.error.message;
    //   this.popupSuccessModel.show();

    // });

    this.appointmentList.push(serviceAppointmentEvent);
    // this.appointment = $event;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
   // this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);
  }

  productConsumedModelShow(appointmentId, manualId) {
    this.serviceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
    this.serviceAppointmentProductConsumedRequest.serviceAppointmentManualId = manualId;
    this.serviceAppointmentProductConsumedRequest.serviceAppointmentId = appointmentId;
    this.isEditMode = false;
    this.productConsumedModel.show();
  }
  productConsumedModelhide() {
    this.serviceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
    this.productConsumedModel.hide();
  }
  cancelDataProductConsumed() {
    this.serviceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
    this.productConsumedModel.hide();
  }

  onProductConsumedCreated($event: Array<BtServiceAppointmentProductConsumedResponse>) {
    this.consumedList = $event;
    // console.log(this.consumedList)
    this.spinner.show();
    this._btserviceAppointment.getconsumeProductDetails(this.sapId, this.consumedPage).subscribe(data => {
      this.consumedList = data.content;
      this.totalconsumed = data.totalElements;
      this.consumedFirst = data.first;
      this.consumedLast = data.last;
      this.consumedSize = data.size;
      this.consumedSerial = (((this.consumedPage) * this.consumedSize));
      if (data != null) {
        this.spinner.hide();
      }
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      this.getConsumedList(this.sapId, this.consumedPage);
      //this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  setPagePreviousServiceResource(event: any) {
    event.preventDefault();
    this.serviceResourcePage = this.serviceResourcePage - 1;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
  }

  setPageServiceResource(event: any) {
    event.preventDefault();
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
  }

  setPageNextServiceResource(event: any) {
    event.preventDefault();
    this.serviceResourcePage = this.serviceResourcePage + 1;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
  }

  setPagePreviousConsumed(event: any) {
    event.preventDefault();
    this.consumedPage = this.consumedPage - 1;
    this.getConsumedList(this.sapId, this.consumedPage);
  }

  setPageConsumed(event: any) {
    event.preventDefault();
    this.getConsumedList(this.sapId, this.consumedPage);
  }

  setPageNextConsumed(event: any) {
    event.preventDefault();
    this.consumedPage = this.consumedPage + 1;
    this.getConsumedList(this.sapId, this.consumedPage);
  }

  reAssignAppointmentView(appointment: btServiceAppointmentResponseWithSCH) {
    this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.serviceAppointmentReAssignRequest.serviceAppointmentId = appointment.appointmentId;
    this.serviceAppointmentReAssignRequest.caseId = appointment.caseId;
    this.serviceAppointmentReAssignRequest.serviceCenterId = appointment.serviceCenterId;
    // console.log(this.serviceAppointmentReAssignRequest.serviceCenterId)
    this.serviceAppointmentReAssignRequest.serviceCenterName = appointment.serviceCenterName;
    this.serviceAppointmentReAssignRequest.serviceResourceId = appointment.serviceResourceId;
    // console.log(this.serviceAppointmentReAssignRequest.serviceResourceId)
    this.serviceAppointmentReAssignRequest.serviceResourceName = appointment.serviceResourceName;
    this.reAssingServiceAppointmentModel.show();
    this.heading = 'Re-Assign';
  }
  reAssingServiceAppointmentModelHide() {
    this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.reAssingServiceAppointmentModel.hide();
  }
  cancelDataReassignAppointment() {
    this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.reAssingServiceAppointmentModel.hide();
  }
  onReassignAppointmentCreated($event: btServiceAppointmentResponseWithSCH) {
    let serviceAppointmentEvent = new btServiceAppointmentResponseWithSCH();
    this.spinner.show();
    this._btserviceAppointment.getServiceAppointmentById($event.serviceAppointmentId).subscribe(data => {
      this.spinner.hide();
      this.appointment = data;
    });
    this.appointmentList.push(serviceAppointmentEvent);
    // this.appointment = $event;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
    //this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);
  }

  requestForSA() {
    this.popupNgClass = "alert alert-info center center-block";
    this.popupRequestforSA.show();
  }

  yesRequestForSA() {
    if (this.remarks == null || this.remarks == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Remarks' });
    }
    else if(this.remarks.trim()=="")
    {
      this._global.alert({ type: 'alert', message: 'Please Fill Remarks' });

    }
    else {
      // console.log(this.remarks)
      this.spinner.show();
      this._btserviceAppointment.postRequestForSA(this.caseId, this.remarks).subscribe(data => {
        this.popupRequestforSA.hide();
        this.spinner.hide();
        this.remarks="";
        this._global.alert({ type: '', message: 'Requested For Approval' });
      }, err => {
        this.popupRequestforSA.hide();
        this.remarks=""
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      this.getConsumedList(this.sapId, this.consumedPage);
      //this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);

    }


  }
  noRequestForSA() {
    this.popupRequestforSA.hide();
  }
  showApprovalWindow(){
    this.approvalModule.show();
  }



  popupClose() {
    this.popupSuccessModel.hide();
  }
  BFSRNew() {
    this.heading = "Create Battery FSR CBMS & Li-ION Battery";
    this.createBFSRModel.show();
    this.spinner.show();
    this._btserviceAppointment.getServiceAppointmentById(this.sapId).subscribe(data => {
      this.appointment = data;

      this.BFSRres.caseId = this.appointment.caseId;
      this.BFSRres.serviceAppointmentId = this.appointment.appointmentId;
      this.BFSRres.serviceAppointmentManualId = this.appointment.serviceAppointmentManulaId;
      this.BFSRres.caseManualId = this.appointment.caseIdManual;
      this.BFSRres.correctiveAction = this.appointment.correctiveAction;
      this.BFSRres.dateOfVisit = this.appointment.visitDate;
      this.BFSRres.technicianFirstName = this.appointment.serviceResourceName;
      this.BFSRservice.getProblemReportByCaseId(this.appointment.caseId).subscribe(data => {
        this.problemReportedDetails = data;
      });
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });



  }
  cancelBFSRModelhide() {
    this.BFSRres = new BatteryFSRResponse();
    this.createBFSRModel.hide();
  }
  // BatteryFSRDATA() {
  //   this.spinner.show();
  //   this._btserviceAppointment.getBatteryFSRByserviceId(this.sapId, this.pageFSR).subscribe(data => {
  //     this.spinner.hide();
  //     this.BatteryFSRList = data.content;
  //     this.totalBFSR = data.totalElements;
  //     this.firstFSR = data['first'];
  //     this.lastFSR = data['last'];
  //     this.sizeFsr = data['size'];
  //     this.serialNumber = (((this.pageFSR) * data.size));
  //   })
  // }
  onBFSRCreated($event: BatteryFSRResponse) {
    this.BatteryFSRList.unshift($event);
    //this.BatteryFSRDATA();
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);

  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.pageFSR = this.pageFSR - 1;
    //this.BatteryFSRDATA();

  }

  setPage(event: any) {
    event.preventDefault();
    //this.BatteryFSRDATA();
  }
  setPageNext(event: any) {
    event.preventDefault();
    this.pageFSR = this.pageFSR + 1;
    //this.BatteryFSRDATA();
  }

 //Upload FSR
  // onSubmit() {
  //   // this.spinner.show();
  //   for (let i = 0; i < this.fileList.length; i++) {
  //     this.file.append('files', this.fileList[i]);
  //   }

   

  //   // this._http.post<any>('bt/api/serviceappointment/fsr/fileupload/' +
  //   //   this.appointment.appointmentId, this.file )
      
  //   this._btserviceAppointment.uploadFsr(this.appointment.appointmentId,this.fileToUpload).subscribe(data => {
  //       this.fileList = [];

  //       (<HTMLInputElement>document.getElementById('getFile')).value = null
  //       this.spinner.hide();
  //       this._global.alert({ type: '', message: 'Updated Successfully' });
  //       // this.getFileList(this.objectName, this.sapId);
  //       // this.getServiceResourceList(this.sapId, this.serviceResourcePage);
  //       // this.getConsumedList(this.sapId, this.consumedPage);
  //      // this.BatteryFSRDATA();
  //       // this.getApprovalHistoryList(this.objectName, this.sapId);
  //       // this.getFileList(this.objectName, this.sapId);
  //       this.fileUploadModel.hide();
  //       // this.popupSuccessModel.show();
  //     }, error => {
  //       this.fileList = [];
  //       this.spinner.hide();
  //       this._global.alert({ type: 'alert', message: error.error.message });
  //     });
  //   this.file = new FormData();
  // }



  // onFileSelect(event) {
  //   this.fileList = [];
  //   if (event.target.files.length > 0) {
  //     this.uploadFilesLength = event.target.files.length;
  //     for (let i = 0; i < event.target.files.length; i++) {
  //       this.fileList.push(event.target.files[i]);
  //     }
  //     // this.searchResult.show();
  //   }
  // }
  

  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
    }
    this._http.post<any>('api/uploadMultipleFiles?objectName=BtServiceAppointment&objectId=' +
      this.appointment.appointmentId, this.formData).subscribe(data => {
        // this.searchResult.hide();
        this.getFileList('BtServiceAppointment', this.appointment.appointmentId);
        // this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
        // this.getApprovalHistoryList();
        //   this.getLodgingListByExpenseId(this.expensesmanagement.id);
        //   this.getBoardingListByExpenseId(this.expensesmanagement.id);
        //   this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
        //   this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
        //   this.getSAListByExpenseId(this.expensesmanagement.id)
        //  this.getGLCodes();
        this.spinner.hide();
        if (this.fileList.length === 0) {
          this.message = 'No File uploaded';
        } else {
          this.message = 'Updated Successfully';
          this.fileUploadModel.hide();
        }
        this._global.alert({type:'',message:this.message});
        this.fileList = [];
        
    (<HTMLInputElement>document.getElementById('getFile')).value=null
      }, error => {
        this.fileList = [];
        this.spinner.hide();
        this.message = error.error.message;
        this._global.alert({type:'alert',message:this.message});
          this.fileUploadModel.hide();
          (<HTMLInputElement>document.getElementById('getFile')).value=null

      });
    this.formData = new FormData();
  }
  removeFile(fileName) {
    if (this.fileList.length > 0) {
      for (const value of this.fileList) {
        if (value['name'] === fileName) {
          this.fileList.splice(this.fileList.indexOf(value), 1);
        }
      }
      this.fileUploadModel.show();
    }
  }
  closeUploadFiles() {
    this.file = new FormData();
    this.fileUploadModel.hide();

    (<HTMLInputElement>document.getElementById('getFile')).value = null
  }

  // show preview file and save file
  filePreview(url, fileName) {
    this.fileName = fileName;
    this.imageUrl = url;
    this.spinner.show();
    this._btserviceAppointment.getDownloadFile(fileName).subscribe(data => {
      this.spinner.hide();
      this.filePreviewModel.show();
    }, err => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });
  }
  saveFile() {
    fileSaver.saveAs(this.imageUrl, this.fileName);
    this.spinner.hide();
    this.filePreviewModel.hide();
  }

  deleteProConsumed(id) {
    this.spinner.show();
    this._btserviceAppointment.deleteProductConsumed(id).subscribe(data => {
      this.spinner.hide();
      this._global.alert({ type: '', message: 'Deleted Successfully ' });
      this.getConsumedList(this.sapId, this.page);
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      // this.getConsumedList(this.sapId, this.consumedPage);
      //this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);
    }, err => {
      this.spinner.hide();
      this.message = err.error.status + ' : ' + err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  DeletAttachment(imageid) {
    const objectId = this.sapId;
    this.spinner.show();
    this.deleteservice.deleteAttachment(imageid, this.objectName, objectId).subscribe(data => {
      this.spinner.hide();
      this.getFileList(this.objectName, this.sapId);
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }

  encodeurl(ursc): string {
    return encodeURIComponent(ursc);
  }
  onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
    if (key.target.value.length > 99) {
      this._global.alert({ type: 'alert', message: "More than 100 digits can not be accepted" });
      return false;
    }
    return true;

  }
  createPDFFsr() {
    this.spinner.show();
    this.btFsrService.createPDFFsr(this.fsrurl,this.fsrkey,
      this.fsrid).subscribe(data => {
        const blob = new Blob([data], { type: 'application/pdf'});
        
        const fileName = `FSR_${this.fsrid}.pdf`;

        const link = document.createElement('a');
        const objectUrl = window.URL.createObjectURL(blob);

        link.href = objectUrl;
        link.download=fileName;
        link.click();

        window.URL.revokeObjectURL(objectUrl);

        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this.spinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: 'success', message: 'Download Successfully' });
      }, err => {
        this.spinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
  }


  createPDF() {
    this.spinner.show();
    this._btserviceAppointment.createPDF('serviceAppointmentId',
      this.sapId).subscribe(data => {
        const file = new Blob([data], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this.spinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: '', message: "Created Successfully" });
      }, err => {
        this.spinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
  }
  messageofEsign: string = '';
  getBase64(image) {
    if (image != null) {
      // Show Image
      this.imageSource = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${image}`);

    } else {
      if (this.appointment.closedFromPlatform != null) {
        if (this.appointment.closedFromPlatform == "WEB") {
          this.messageofEsign = "Closed From WEB";
        }
      } else {
        // Show messgae "No E-Signature";
        this.messageofEsign = "No E-Signature";
      }
    }
    // this.imageSource=this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${image}`);

  }
  fsrDataGetById:BtFsrCommissionDataGetById =  new BtFsrCommissionDataGetById();
  servicefsrById:BtFsrServiceDataGetById = new BtFsrServiceDataGetById();
  // btCreateFsr: BtFsrData = new BtFsrData();

  editFsr()
  {
    // console.log("tst")
    this.changecaseNumber();
    // console.log(this.fsrurl)   
    // console.log(this.fsrid);
    this.fsrHeading=this.EDIT_FSR_HEADING;
    this.spinner.show();
    this.btFsrService.EditFsr(this.fsrurl,this.fsrid).subscribe(data=>{
      this.spinner.hide();
      this.fsrDataGetById=data;
    },err=>{
      this.spinner.hide();
    })
    this.createfsrserviceModel.show();
    this.isEditFsr=true;
    

  }

  fsr() {
    this.isEditFsr=false;
    this.fsrHeading=this.CREATE_FSR_HEADING;
    this.createfsrserviceModel.show();
    this.changecaseNumber();
  }
  changecaseNumber() {
    this.spinner.show();
    this.caseService.getDetailByCaseId(this.appointment.caseId).subscribe((res) => {
      this.spinner.hide();
      this.btCaseResponse = res;
      
    }, (error) => {

    });
    // this.serviceAppointmentResponse.assetsPartCode=null;
    // this.caseList.forEach(data=>{
    //   if(data.assetsSerialNumber==this.serviceAppointmentResponse.assetsSapSerialNumber){
    //     this.serviceAppointmentResponse.assetsPartCode=data.partCode;
    //   }
    // });
  }
  cancelFsrData()
  {
    // console.log(this.fsrDataGetById);
    
    this.fsrDataGetById= new BtFsrCommissionDataGetById();
    this.isEditFsr=false;
    this.btCaseResponse=new BTCaseResponse();
    this.createfsrserviceModel.hide();
    this.getServiceAppointmentById(this.sapId);
    this.getSchDetails(this.sapId);

  }
  onApprovalDone(){
    this.approvalModule.hide();
    this.getServiceAppointmentById(this.sapId);
    this.getSchDetails(this.sapId);
    this.getApprovalButton();
  }

  btnshow:boolean=false;
getApprovalButton() {

  this.spinner.show()
  this.tobeapproved.getItemToBeApprovdButton( this.actionHistoryObjectAndId.objectName,this.actionHistoryObjectAndId.objectId).subscribe(data => {
    this.spinner.hide();
    if( data.id != null && data != null && data != undefined)
    {
      this.btnshow = true;
      this.SaApprovalId= data.id;
      
      console.log(this.SaApprovalId);
      
    }
    else {
      this.btnshow = false;
      this.SaApprovalId = this.actionHistoryObjectAndId.objectId;
    }
  }, error=>{

  });


}

    // file upload
    fileToUpload:File;
    onFileSelect(event) {
      this.fileList = [];
      if (event.target.files.length > 0) {
        this.uploadFilesLength = event.target.files.length;
        for (let i = 0; i < event.target.files.length; i++) {
          this.fileList.push(event.target.files[i]);
          this.fileToUpload = event.target.files[i];
        }
        this.fileUploadModel.show();
      }
    }


}
