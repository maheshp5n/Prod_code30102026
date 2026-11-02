import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ServiceAppointmentResponse, ServiceAppointmentProductConsumedRequest, ServiceResourseResponse, ServiceResourseRequest, BatteryFSRResponse, BatteryFSRRequest, ProblemReported, ServiceAppointmentProductConsumedResponse, ServiceAppointmentReAssignRequest, ToBeApproved, EmployeeDetails, ToBeApprovedHistoryResponse, FileUploadResponse, ActionHistoryObjectAndId, ServiceAppointmentUnAssignToRequest } from '../../services/model';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { Loginservices } from '../../services/login.services';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import * as fileSaver from 'file-saver';
import { DatePipe } from '@angular/common';
import { FileDeleteService } from '../../services/filedeletService.services';
import { GlobalserviceService } from '../../globalservice.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-serviceappointmentindetails',
  templateUrl: './serviceappointmentindetails.component.html',
  styleUrls: ['./serviceappointmentindetails.component.scss']
})
export class ServiceappointmentindetailsComponent implements OnInit {

  @ViewChild('createServiceAppointmentModel') public createServiceAppointmentModel: ModalDirective;
  @ViewChild('productConsumedModel') public productConsumedModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createServiceResourceModel') createServiceResourceModel: ModalDirective;
  @ViewChild('createBFSRModel') createBFSRModel: ModalDirective;
  @ViewChild('reAssingServiceAppointmentModel') reAssingServiceAppointmentModel: ModalDirective;
  @ViewChild('popupRequestforSA') popupRequestforSA: ModalDirective;
  @ViewChild('fileUploadModel') fileUploadModel: ModalDirective;
  @ViewChild('filePreviewModel') filePreviewModel: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup:ModalDirective;
  // @ViewChild('imgeid',{static:false}) imgeid;
  public message: string = '';
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  BFSRres: BatteryFSRResponse = new BatteryFSRResponse();
  BFRSreq: BatteryFSRRequest = new BatteryFSRRequest();
  appointment: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  serviceAppointmentResponse: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  appointmentList: Array<ServiceAppointmentResponse> = [];
  serialNumber: number;
  newServiceResource: ServiceResourseRequest = new ServiceResourseRequest();
  serviceResourceResponse: ServiceResourseResponse = new ServiceResourseResponse();
  remarks:string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  serviceAppointmentUnAssignToRequest:ServiceAppointmentUnAssignToRequest = new ServiceAppointmentUnAssignToRequest();
  CREATE_ACCOUNT_HEADING: string = 'New Service Appointment';
  EDIT_ACCOUNT_HEADING: string = 'Edit Service Appointment';
  EDIT_ServiceResource_HEADING: string = 'Edit Service Resource';

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
  consumedList: Array<ServiceAppointmentProductConsumedResponse> = [];
  serviceAppointmentReAssignRequest: ServiceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
  approvalHistoryList: Array<ToBeApprovedHistoryResponse> = [];

  objectName = 'ServiceAppointment';
  fileUploadList: Array<FileUploadResponse> = [];
  uploadForm: UntypedFormGroup;
  fileList: string[] = [];
  uploadFilesLength: number;
  formData: FormData = new FormData()
  fileName: string;
  imageUrl: string;
  LogedEmailId:string;

  addressDisplay = {
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
  esignDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  zoom:any;
  caseId: number;
  FSRUploadbtn:boolean=false;
  reAssignbtn:boolean=false;
  imageSource:any;
  loginData:any;
  domainId:any;
  _cFlag: string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private datePipe: DatePipe,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private BFSRservice: BatrryFSRService,
    private _serviceAppointmentService: ServiceAppointmentService, private loginService: Loginservices
    , private formBuilder: UntypedFormBuilder, private _http: HttpClient,
    private loginservice:Loginservices,private deleteservice:FileDeleteService,
    private _global:GlobalserviceService,private sanitizer: DomSanitizer) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getServiceAppointmentById(this.sapId);
      this.actionHistoryObjectAndId.objectId = '' + this.sapId;
      this.actionHistoryObjectAndId.objectName = 'ServiceAppointment';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
    
    this.loginData = this.loginservice.getLoggedInUser();
    this.domainId = this.loginData.emptype['businessDomainId']
    this.LogedEmailId=this.loginData.emailId;

  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
    
  }
  canMapview:boolean=false;
  getServiceAppointmentById(sapId: number) {
    this.spinner.show();
    console.log("hello");
    this._serviceAppointmentService.getServiceAppointmentById(sapId).subscribe(data => {
      this.appointment = data;
      this.spinner.hide();
      this.getBase64(data.eSignature);
      if(data.ownerEmailId==this.LogedEmailId){
      this.FSRUploadbtn=true;
      this.reAssignbtn=true;
     
      }
      else{
        this.FSRUploadbtn=false;
        this.reAssignbtn=false;
      }
      if(data.serviceAppointmentStatusId==4 || data.serviceAppointmentStatusId==5 || data.serviceAppointmentStatusId==6 || data.serviceAppointmentStatusId==7){
        this.canMapview=true;
          }
          else{
            this.canMapview=false;
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
  }

  getServiceResourceList(sapId: number, serviceResourcePage: number) {
    this.spinner.show();
    this._serviceAppointmentService.getServiceResourceByAppointmentId(sapId, serviceResourcePage).subscribe(data => {
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
    this._serviceAppointmentService.getconsumeProductDetails(sapId, consumedPage).subscribe(data => {
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
    this._serviceAppointmentService.getApprovalHistory(objectName, objectId).subscribe(data => {
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
      this.esignDisplay.angleIcon ='fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getServiceAppointmentById(this.sapId);
    } else {
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      this.getConsumedList(this.sapId, this.consumedPage);
      this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }
  todayDate=new Date();
  caseReason:string='';
  createEditModelshow(serviceappointment: ServiceAppointmentResponse) {
   
    this.spinner.show();
    this._serviceAppointmentService.getServiceAppointmentById(serviceappointment.serviceAppointmentId).subscribe(data => {
      // this.serviceAppointmentResponse = data;
     
      this.caseReason=data.caseReason;
      this.serviceAppointmentResponse.caseId=data.caseId;
      this.serviceAppointmentResponse.caseIdManual=data.caseIdManual;
      this.serviceAppointmentResponse.serviceAppointmentId=data.serviceAppointmentId;
      this.serviceAppointmentResponse.accountName=data.accountName;
      this.serviceAppointmentResponse.accountSapNumber=data.accountSapNumber;
      this.serviceAppointmentResponse.serviceAppointmentManulaId=data.serviceAppointmentManulaId;
      this.serviceAppointmentResponse.siteId=data.siteId;
      this.serviceAppointmentResponse.siteIdManual=data.siteIdManual;
      this.serviceAppointmentResponse.siteName=data.siteName;
      this.serviceAppointmentResponse.sastatus=data.sastatus;
      this.serviceAppointmentResponse.remark=data.remark;
      this.serviceAppointmentResponse.pendingResponsibilityId=data.pendingResponsibilityId;
      this.serviceAppointmentResponse.pendingResponsibilityName=data.pendingResponsibilityName;
      this.serviceAppointmentResponse.faultObserved=data.faultObserved;
      this.serviceAppointmentResponse.fixedPayout=data.fixedPayout;
      this.serviceAppointmentResponse.punchPointCustomer=data.punchPointCustomer;
      this.serviceAppointmentResponse.punchPointExiom=data.punchPointExiom;
      this.serviceAppointmentResponse.correctiveActions=data.correctiveActions;
      this.serviceAppointmentResponse.sstatus= data.sstatus;
      this.serviceAppointmentResponse.serviceAppointmentStatusId=data.serviceAppointmentStatusId;
      this.serviceAppointmentResponse.serviceAppointmentStatusName=data.serviceAppointmentStatusName;
      this.serviceAppointmentResponse.caseReason=data.caseReason;
      this.serviceAppointmentResponse.assetsSapSerialNumber=data.assetsSapSerialNumber;
      this.serviceAppointmentResponse.assetsPartCode=data.assetsPartCode;
      this.serviceAppointmentResponse.assetDescription=data.assetDescription;
      if(data.closedFromPlatform!=null){
      this.serviceAppointmentResponse.closedFromPlatform=data.closedFromPlatform;
    }else{
      this.serviceAppointmentResponse.closedFromPlatform="Web";
    }
      if(data.visitDate!==null){
       
        this.serviceAppointmentResponse.visitDate=data.visitDate;
      }
      else{
        this.serviceAppointmentResponse.visitDate=this.todayDate;//this.datePipe.transform(this.todayDate, 'yyyy-MM-dd');
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
    
  }

  cancelData() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.createServiceAppointmentModel.hide();

  }
  createServiceAppointmentModelhide() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.createServiceAppointmentModel.hide();

  }

  onServiceAppointmentCreated($event: ServiceAppointmentResponse) {
    let serviceAppointmentEvent = new ServiceAppointmentResponse();
    this.spinner.show();
    this._serviceAppointmentService.getServiceAppointmentById($event.serviceAppointmentId).subscribe(data => {
      this.spinner.hide();
      serviceAppointmentEvent = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();

    });
    this.appointmentList.push(serviceAppointmentEvent);
    this.appointment = $event;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
    this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);
  }

  productConsumedModelShow(appointmentId, serviceAppointmentManualId) {
    this.serviceAppointmentProductConsumedRequest = new ServiceAppointmentProductConsumedRequest();
    this.serviceAppointmentProductConsumedRequest.serviceAppointmentNumber = appointmentId;
    this.serviceAppointmentProductConsumedRequest.serviceAppointmentManualId = serviceAppointmentManualId;
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

  onProductConsumedCreated($event: Array<ServiceAppointmentProductConsumedResponse>) {
    this.consumedList = $event;
    this.spinner.show();
    this._serviceAppointmentService.getconsumeProductDetails(this.sapId, this.consumedPage).subscribe(data => {
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
      this.BatteryFSRDATA();
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

  reAssignAppointmentView(appointment: ServiceAppointmentResponse) {
    this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.serviceAppointmentReAssignRequest.serviceAppointmentId = appointment.serviceAppointmentId;
    this.serviceAppointmentReAssignRequest.caseId = appointment.caseId;
    this.serviceAppointmentReAssignRequest.serviceCenterId = appointment.serviceCenterId;
    this.serviceAppointmentReAssignRequest.serviceCenterName = appointment.serviceCenterName;
    this.serviceAppointmentReAssignRequest.serviceResourceId = appointment.serviceResourceId;
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
  onReassignAppointmentCreated($event: ServiceAppointmentResponse) {
    let serviceAppointmentEvent = new ServiceAppointmentResponse();
    this.spinner.show();
    this._serviceAppointmentService.getServiceAppointmentById($event.serviceAppointmentId).subscribe(data => {
      this.spinner.hide();
      serviceAppointmentEvent = data;
    });
    this.appointmentList.push(serviceAppointmentEvent);
    this.appointment = $event;
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
    this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);
  }

  requestForSA() {
    this.popupNgClass = "alert alert-info center center-block";
    this.popupRequestforSA.show();
  }

  yesRequestForSA() {
    if(this.remarks==null && this.remarks==undefined){
      this.message="Please fill Remarks";
      this.popupNgClass = "alert alert-danger center center-block";
      this._global.alert({type:'alert',message:this.message});
    }
    else{
      this.spinner.show();
      this._serviceAppointmentService.postRequestForSA(this.caseId,this.remarks).subscribe(data => {
        this.popupRequestforSA.hide();
        this.spinner.hide();
        this.popupNgClass = "alert alert-success center center-block";
        this.message = 'Request   ted for Approval';
        this._global.alert({type:'',message:this.message});
      }, err => {
        this.popupRequestforSA.hide();
        this.spinner.hide();
        this.popupNgClass = "alert alert-danger center center-block";
        this.message = err.error.message;
        this._global.alert({type:'alert',message:this.message});
      });
      this.getServiceResourceList(this.sapId, this.serviceResourcePage);
      this.getConsumedList(this.sapId, this.consumedPage);
      this.BatteryFSRDATA();
      this.getApprovalHistoryList(this.objectName, this.sapId);
      this.getFileList(this.objectName, this.sapId);
      
    }
   

  }
  noRequestForSA() {
    this.popupRequestforSA.hide();
  }


  popupClose() {
    this.popupSuccessModel.hide();
  }
  BFSRNew() {
    this.heading = "Create Battery FSR CBMS & Li-ION Battery";
    this.createBFSRModel.show();
    this.spinner.show();
    this._serviceAppointmentService.getServiceAppointmentById(this.sapId).subscribe(data => {
      this.appointment = data;

      this.BFSRres.caseId = this.appointment.caseId;
      this.BFSRres.serviceAppointmentId = this.appointment.serviceAppointmentId;
      this.BFSRres.serviceAppointmentManualId = this.appointment.serviceAppointmentManulaId;
      this.BFSRres.caseManualId = this.appointment.caseIdManual;
      this.BFSRres.correctiveAction = this.appointment.correctiveActions;
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
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });



  }
  cancelBFSRModelhide() {
    this.BFSRres = new BatteryFSRResponse();
    this.createBFSRModel.hide();
  }

  BatteryFSRDATA() {
    this.spinner.show();
    this._serviceAppointmentService.getBatteryFSRByserviceId(this.sapId, this.pageFSR).subscribe(data => {
      this.spinner.hide();
      this.BatteryFSRList = data.content;
      this.totalBFSR = data.totalElements;
      this.firstFSR = data['first'];
      this.lastFSR = data['last'];
      this.sizeFsr = data['size'];
      this.serialNumber = (((this.pageFSR) * data.size));
    })
  }
  onBFSRCreated($event: BatteryFSRResponse) {
    this.BatteryFSRList.unshift($event);
    this.BatteryFSRDATA();
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    this.getConsumedList(this.sapId, this.consumedPage);
    // this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);

  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.pageFSR = this.pageFSR - 1;
    this.BatteryFSRDATA();

  }

  setPage(event: any) {
    event.preventDefault();
    this.BatteryFSRDATA();
  }
  setPageNext(event: any) {
    event.preventDefault();
    this.pageFSR = this.pageFSR + 1;
    this.BatteryFSRDATA();
  }

  // file upload
  onFileSelect(event) {
    this.fileList = [];
    if (event.target.files.length > 0) {
      this.uploadFilesLength = event.target.files.length;
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
      }
      this.fileUploadModel.show();
    }
  }
  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
    }
    this._http.post<any>('api/uploadMultipleFiles?objectName=ServiceAppointment&objectId=' +
      this.appointment.serviceAppointmentId, this.formData).subscribe(data => {
        this.fileList = [];
        
    (<HTMLInputElement>document.getElementById('getFile')).value=null
        this.spinner.hide();
        this.message = 'Updated Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.getFileList(this.objectName, this.sapId);
        this.getServiceResourceList(this.sapId, this.serviceResourcePage);
        this.getConsumedList(this.sapId, this.consumedPage);
        this.BatteryFSRDATA();
        this.getApprovalHistoryList(this.objectName, this.sapId);
        // this.getFileList(this.objectName, this.sapId);
        this.fileUploadModel.hide();
        this.popupSuccessModel.show();
      }, error => {
        this.fileList = [];
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = error.error.message;
        this.popupSuccessModel.show();
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
    this.formData = new FormData();
    this.fileUploadModel.hide();
    
    (<HTMLInputElement>document.getElementById('getFile')).value=null
  }

  // show preview file and save file
  filePreview(url, fileName) {
    this.fileName = fileName;
    this.imageUrl = url;
    this.spinner.show();
    this._serviceAppointmentService.getDownloadFile(fileName).subscribe(data => {
      this.spinner.hide();
      this.filePreviewModel.show();
    }, err => {
      this.spinner.hide();
      this.message = err.message;
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    });
  }
  saveFile() {
    fileSaver.saveAs(this.imageUrl, this.fileName);
    this.spinner.hide();
    this.filePreviewModel.hide();
  }
  // close file
// delete product consumed by shagufta

deleteProConsumed(id) {
  this.spinner.show();
  this._serviceAppointmentService.deleteProductConsumed(id).subscribe(data => {
    this.message = 'Deleted Successfully !';
    this.spinner.hide();
    this.popupNgClass = 'alert alert-success center center-block';
    this.popupSuccessModel.show();
    this.getConsumedList(this.sapId,this.page);
    this.getServiceResourceList(this.sapId, this.serviceResourcePage);
    // this.getConsumedList(this.sapId, this.consumedPage);
    this.BatteryFSRDATA();
    this.getApprovalHistoryList(this.objectName, this.sapId);
    this.getFileList(this.objectName, this.sapId);
  }, err => {
    this.spinner.hide();
    this.message = err.error.status + ' : ' + err.error.message;
    this.popupSuccessModel.show();
  });
}
DeletAttachment(imageid){
  const objectId=this.sapId;
  this.spinner.show();
  this.deleteservice.deleteAttachment(imageid,this.objectName,objectId).subscribe(data=>{
    this.spinner.hide();
    this.getFileList(this.objectName, this.sapId);
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
  })
}
unAssignAppointmentView(){
  this.serviceAppointmentUnAssignToRequest.id = this.sapId;
  console.log(this.serviceAppointmentUnAssignToRequest);
  this.spinner.show();
  
  this._serviceAppointmentService.unAssignSa(this.serviceAppointmentUnAssignToRequest).subscribe(data=>{
    this.spinner.hide();
    this.getServiceAppointmentById(this.sapId);
    this.message="Un-Assign Succesfully Done.."
    this._global.alert({type:"",message:this.message});

  },error=>{
    this.spinner.hide();
  });

}

encodeurl(ursc):string{
  return encodeURIComponent(ursc);
}
onlyNumbers(key): any {
  const charCode = (key.which) ? key.which : key.keyCode;
 if(key.target.value.length>99){
    this.message="More than 100 digits can not be accepted";
    this._global.alert({type:'alert',message:this.message});
    return false;
  }
  return true;

}
createPDF() {
  this.spinner.show();
  this._serviceAppointmentService.createPDF( 'serviceAppointmentId',
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
      this._global.alert({ type: '', message: "Created successfully"});
    }, err => {
      this.spinner.hide();
      this.confirmationPopup.hide();
      this._global.alert({ type: 'alert', message: err.error.message });
    });
}
messageofEsign:string='';
getBase64(image) {
  if(image!=null) {
    // Show Image
    this.imageSource=this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${image}`);

  }else{
    if(this.appointment.closedFromPlatform!=null) {
      if(this.appointment.closedFromPlatform=="WEB") {
        this.messageofEsign="Closed from WEB";
      }
    }else {
      // Show messgae "No E-Signature";
      this.messageofEsign="No E-Signature";
    }			
  }
  // this.imageSource=this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${image}`);
  
}


}
