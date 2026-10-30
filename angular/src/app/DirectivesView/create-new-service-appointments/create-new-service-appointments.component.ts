import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import {
  ServiceAppointmentStatus, ClaimStatus, PendingResponsibility, ServiceAppointmentResponse, ServiceAppointmentRequest,
  CaseResponse,
  CaseForList,
  Product,
  AssetsResponse,
  ProductsForList,
  AssetsForList
} from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { AssetsService } from '../../services/assets.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-create-new-service-appointments',
  templateUrl: './create-new-service-appointments.component.html',
  styleUrls: ['./create-new-service-appointments.component.css']
})
export class CreateNewServiceAppointmentsComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('accountModel', { static: false }) accountModel: ModalDirective;
  @ViewChild('createCaseModel', { static: false }) createCaseModel: ModalDirective;
  @ViewChild('createServieResourceModel', { static: false }) createServieResourceModel: ModalDirective;
  @ViewChild('createAssetModel', { static: false }) createAssetModel: ModalDirective;
  @ViewChild('createProductModel', { static: false }) createProductModel: ModalDirective;

  @Output()
  onServiceAppointmentCreated: EventEmitter<ServiceAppointmentRequest> = new EventEmitter<ServiceAppointmentRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  message: string;
  serviceAppointmentResponse: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  serviceAppointmentRequest: ServiceAppointmentRequest = new ServiceAppointmentRequest();
  caseResponse: CaseResponse = new CaseResponse();
  statusList: Array<ServiceAppointmentStatus> = [];
  claimStatusList: Array<ClaimStatus> = [];
  pendingResList: Array<PendingResponsibility> = [];
  caseList: Array<CaseForList> = [];
  caseForList: CaseForList = new CaseForList();
  loginData;
  timeList: Array<string> = [];
  createProduct: Product = new Product();
  assetResponse: AssetsResponse = new AssetsResponse();
  productList: Array<ProductsForList> = []
  productsForList: ProductsForList = new ProductsForList();
  assetList: Array<AssetsForList> = [];
  assetForlist: AssetsForList = new AssetsForList();
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  tempId: number;
  tempManual: string;
  isServiceResourceModelShow: boolean;
  isCaseModelShow: boolean;
  isAccountModelShow: boolean;
  isProductModelShow: boolean;
  isAssetModelShow: boolean;
  assetreq:string='col-md-6 col-sm-6 form-group';
  //buisnessDomainId: number;
  today = new Date();
  todayDate=new Date();
  _visitDate=new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  minDate= new Date();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  // @Input()
  // set domainId(buisnessDomainId: number) {
  //   this.buisnessDomainId = buisnessDomainId;
  // }
  // get domainId(): number {
  //   return this.buisnessDomainId;
  // }

  @Input()
  set editServiceAppointmentData(serviceAppointmentRespons: ServiceAppointmentResponse) {
     this.serviceAppointmentResponse = serviceAppointmentRespons;
     if(this.serviceAppointmentResponse.visitDate==undefined||this.serviceAppointmentResponse.visitDate==null||this.serviceAppointmentResponse.visitDate==''){
        this.serviceAppointmentResponse.visitDate=this.today;
     }
     else{
       this._visitDate=this.dateConversion(this.serviceAppointmentResponse.visitDate);
     }

     if(this.serviceAppointmentResponse.assetsSapSerialNumber!=null&&this.serviceAppointmentResponse.assetsSapSerialNumber!=undefined&&this.serviceAppointmentResponse.assetsSapSerialNumber!=''){
      this.assetList.push({assetsSerialNumber:this.serviceAppointmentResponse.assetsSapSerialNumber,partCode:this.serviceAppointmentResponse.assetsPartCode,productDescription:this.serviceAppointmentResponse.assetDescription});
    }
      if(this.serviceAppointmentResponse.caseReason=="Installation"){
        // console.log("yes");
      }
    //  if(this.serviceAppointmentResponse.visitDate===null && this.serviceAppointmentResponse.visitDate==''){
    //   this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.today, 'yyyy-MM-dd');
    
    //  }
    //  else{
    //    this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.today, 'yyyy-MM-dd');
    //  }
  }
  get editServiceAppointmentData(): ServiceAppointmentResponse {
    return this.serviceAppointmentResponse;
  }
  @Input()
  set casereason(caseReason:string){
    this.serviceAppointmentResponse.caseReason=caseReason;
    if(this.serviceAppointmentResponse.caseReason=="Installation"){
      this.assetreq='col-md-6 col-sm-6 form-group required';
    
    }
    else{
      this.assetreq='col-md-6 col-sm-6 form-group';
    }
  }
  get casereason():string{
    return this.serviceAppointmentResponse.caseReason;
  }

  constructor(private spinner: NgxSpinnerService, private datePipe: DatePipe,private loginService: Loginservices,
    private _serviceAppointment: ServiceAppointmentService, private assetService: AssetsService) {
    this.dateCustomClasses = [      
      { date: this.today, classes: ['bg-secondary'] }
    ];
    //minDate VisitDate
    const today = new Date();
    this.minDate = new Date(today.setDate(today.getDate() - 15));

    //this.today=new Date();
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {

    //this.serviceAppointmentResponse.visitDate=this.today;
    //this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.serviceAppointmentResponse.visitDate, 'yyyy-MM-dd');
    // 'yyyy-MM-dd HH:mm:ss'
    this.ownerName();
    this.getServiceAppointmentStatus();
    this.getPendingResponsibility();
    this.getClaimStatus();
    this.getCaseList();
    this.getTimeList();
    this.getProductList();
    //this.getAssetList();
    this.faPlus;
    //this.serviceAppointmentResponse.visitDate = this.today;
    
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
    this.serviceAppointmentResponse.ownerEmailId = this.loginData.emailId;
  }

  getServiceAppointmentStatus() {
    this._serviceAppointment.getServiceAppointmentStatus().subscribe(data => {
      this.statusList = data;
    }, err => {

    });
  }
  getPendingResponsibility() {
    this._serviceAppointment.getPendingResponsibility().subscribe(data => {
      this.pendingResList = data;
    }, err => {

    });
  }
  getClaimStatus() {
    this._serviceAppointment.getClaimStatus().subscribe(data => {
      this.claimStatusList = data;
    }, err => {

    });
  }
  getCaseList() {
    this._serviceAppointment.getCaseLists().subscribe(data => {
      this.caseList = data;
    }, err => {

    });
  }

  getTimeList() {
    this._serviceAppointment.getTimeList().subscribe(data => {
      this.timeList = data;
    }, err => {

    });
  }
  getProductList() {
    this.assetService.getProductForList().subscribe(data => {
      this.productList = data;
    }, err => {

    })
  }
  getAssetList() {
    this.assetService.getAssetsForListForInstallation().subscribe(data => {
      this.assetList = data;
    }, err => {

    })
  }

  getAssetsListSmall(key){
    this.assetService.getListForInstallationSmall(key.term).subscribe(data=>{
      this.assetList=data.content;
      if(this.serviceAppointmentResponse.assetsSapSerialNumber!=null&&this.serviceAppointmentResponse.assetsSapSerialNumber!=undefined&&this.serviceAppointmentResponse.assetsSapSerialNumber!=''){
        this.assetList.push({assetsSerialNumber:this.serviceAppointmentResponse.assetsSapSerialNumber,partCode:this.serviceAppointmentResponse.assetsPartCode,productDescription:this.serviceAppointmentResponse.assetDescription});
      }
    });
  }

  addNewCase() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.show();
  }
  createCaseModelhide() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.hide();
  }
  cancelDatacase() {
    this.caseResponse = new CaseResponse();
    this.createCaseModel.hide();
  }
  onCaseCreated($event: CaseResponse) {
    this.getCaseList();
    this.serviceAppointmentResponse.caseId = $event.caseId;
  }

  addNewProduct() {
    this.createProduct = new Product();
    this.createProductModel.show();
  }

  createProductModelhide() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }

  cancelDataProd() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  onProductCreated($event: Product) {
    this.getProductList();
    this.serviceAppointmentResponse.assetsPartCode = $event.partCode;
  }

  addNewAsset() {
    this.assetResponse = new AssetsResponse();
    this.createAssetModel.show();
  }
  createAssetModelhide() {
    this.assetResponse = new AssetsResponse();
    this.createAssetModel.hide();
  }
  cancelDataAsset() {
    this.assetResponse = new AssetsResponse();
    this.createAssetModel.hide();
  }
  onAssetCreated($event: AssetsResponse) {
    this.getAssetList();
    this.serviceAppointmentResponse.assetsSapSerialNumber = $event.serialNumber;
  }

  // Post and put method
  postCreateServiceAppointment() {
    if (this.serviceAppointmentResponse.caseId == null || this.serviceAppointmentResponse.caseId === 0) {
      this.message = 'Please fill the case';
      this.popupSuccessModel.show();
    } else if (this.serviceAppointmentResponse.serviceAppointmentStatusId == null ||
      this.serviceAppointmentResponse.serviceAppointmentStatusId === 0) {
      this.message = 'Please fill the status';
      this.popupSuccessModel.show();
    } else if (this.serviceAppointmentResponse.pendingResponsibilityId == null ||
      this.serviceAppointmentResponse.pendingResponsibilityId === 0) {
      this.message = 'Please fill the pending responsibility';
      this.popupSuccessModel.show();
    }
    else if(this.serviceAppointmentResponse.faultObserved == undefined || this.serviceAppointmentResponse.faultObserved==null){
      this.message = 'Please fill the fault observed';
      this.popupSuccessModel.show();
    }
    else if ((this.serviceAppointmentResponse.faultObserved).length >= 1000) {
      this.message = 'Please fill the fault observed under 1000 characters'
      this.popupSuccessModel.show();
    } 
    else if(this.serviceAppointmentResponse.correctiveActions == undefined || this.serviceAppointmentResponse.correctiveActions == null){
      this.message = 'Please fill the corrective actions';
      this.popupSuccessModel.show();
    }
    else if ((this.serviceAppointmentResponse.correctiveActions).length >= 1000) {
      this.message = 'Please fill the corrective actions under 1000 characters'
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentResponse.punchPointExiom != undefined &&
      (this.serviceAppointmentResponse.punchPointExiom).length >= 1000) {
      this.message = 'Please fill the punch point exiom under 1000 characters'
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentResponse.punchPointCustomer != undefined &&
      (this.serviceAppointmentResponse.punchPointCustomer).length >= 1000) {
      this.message = 'Please fill the punch point customer under 1000 characters'
      this.popupSuccessModel.show();
    }
    else if (this.serviceAppointmentResponse.remark != undefined &&
      (this.serviceAppointmentResponse.remark).length >= 1000) {
      this.message = 'Please fill the remarks under 500 characters'
      this.popupSuccessModel.show();
    }
    else if(this.serviceAppointmentResponse.caseReason=='Installation' && this.serviceAppointmentResponse.assetsSapSerialNumber==undefined && this.serviceAppointmentResponse.assetsSapSerialNumber==null){
      this.message='Please fill Asset serial Number';
      this.popupSuccessModel.show();
    }
    else if(this.serviceAppointmentResponse.acknowledgedByName==undefined || this.serviceAppointmentResponse.acknowledgedByName==''){
      this.message='Please fill Signoff Name';
      this.popupSuccessModel.show();
    }
    else if(this.serviceAppointmentResponse.acknowledgedByPhone==undefined || this.serviceAppointmentResponse.acknowledgedByPhone==''){
      this.message='Please fill Asset Signoff Number';
      this.popupSuccessModel.show();
    }
    else if(this.serviceAppointmentResponse.acknowledgedByPhone.length<10){
      this.message='Please fill Asset Signoff Numberm in 10 Digit';
      this.popupSuccessModel.show();
    }
    else {
      //this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.todayDate, 'yyyy-MM-dd');
      this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.serviceAppointmentResponse.visitDate, 'yyyy-MM-dd');
      console.log("visitDate---------> ", this.serviceAppointmentResponse.visitDate);
      if (!this._isEditMode) {
        this.serviceAppointmentRequest.ownerId = this.serviceAppointmentResponse.ownerEmailId;
        this.serviceAppointmentRequest.caseId = this.serviceAppointmentResponse.caseId;
        this.serviceAppointmentRequest.faultObserved = this.serviceAppointmentResponse.faultObserved;
        this.serviceAppointmentRequest.correctiveActions = this.serviceAppointmentResponse.correctiveActions;
        this.serviceAppointmentRequest.punchPointExiom = this.serviceAppointmentResponse.punchPointExiom;
        this.serviceAppointmentRequest.punchPointCustomer = this.serviceAppointmentResponse.punchPointCustomer;
        this.serviceAppointmentRequest.visitDate = this.serviceAppointmentResponse.visitDate;
        this.serviceAppointmentRequest.statusId = this.serviceAppointmentResponse.serviceAppointmentStatusId;
        this.serviceAppointmentRequest.customerRepresentativeName = this.serviceAppointmentResponse.customerRepresentativeName;
        this.serviceAppointmentRequest.customerRepresentativeContact = this.serviceAppointmentResponse.customerRepresentativeContact;
        this.serviceAppointmentRequest.pendingResponsibilityId = this.serviceAppointmentResponse.pendingResponsibilityId;
        this.serviceAppointmentRequest.remark = this.serviceAppointmentResponse.remark;
        this.serviceAppointmentRequest.earliestStartPermittedDate = this.serviceAppointmentResponse.earliestStartPermittedDate;
        this.serviceAppointmentRequest.earliestStartPermittedTime = this.serviceAppointmentResponse.earliestStartPermittedTime;
        this.serviceAppointmentRequest.dueDate = this.serviceAppointmentResponse.dueDate;
        this.serviceAppointmentRequest.dueTime = this.serviceAppointmentResponse.dueTime;
        this.serviceAppointmentRequest.tFlag = this.serviceAppointmentResponse.tFlag;

        this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetsSapSerialNumber;
        this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        this.serviceAppointmentRequest.acknowledgedByName=this.serviceAppointmentResponse.acknowledgedByName;
        this.serviceAppointmentRequest.acknowledgedByPhone=this.serviceAppointmentResponse.acknowledgedByPhone;
        //this.serviceAppointmentRequest.businessDomainId = this.buisnessDomainId;
        if(this.serviceAppointmentResponse.serviceAppointmentStatusId==4||this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          if(!confirm(`Please verify that you have done the inventory consumption while closing the Service appointment. If not required, please ignore this message. \n\nAfter closing the service appointment, system will not allow to add consumption!`)){
            return;
          }
        }
        this.spinner.show();
        this._serviceAppointment.postServiceAppoinment(this.serviceAppointmentRequest).subscribe(data => {
          this.tempId = data.serviceAppointmentId;
          this.tempManual = data.serviceAppointmentManulaId;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onServiceAppointmentCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.serviceAppointmentRequest.serviceAppointmentId = this.serviceAppointmentResponse.serviceAppointmentId;
        this.serviceAppointmentRequest.ownerId = this.serviceAppointmentResponse.ownerEmailId;
        this.serviceAppointmentRequest.caseId = this.serviceAppointmentResponse.caseId;
        this.serviceAppointmentRequest.faultObserved = this.serviceAppointmentResponse.faultObserved;
        this.serviceAppointmentRequest.correctiveActions = this.serviceAppointmentResponse.correctiveActions;
        this.serviceAppointmentRequest.punchPointExiom = this.serviceAppointmentResponse.punchPointExiom;
        this.serviceAppointmentRequest.punchPointCustomer = this.serviceAppointmentResponse.punchPointCustomer;
        this.serviceAppointmentRequest.visitDate = this.serviceAppointmentResponse.visitDate;
        this.serviceAppointmentRequest.statusId = this.serviceAppointmentResponse.serviceAppointmentStatusId;
        this.serviceAppointmentRequest.customerRepresentativeName = this.serviceAppointmentResponse.customerRepresentativeName;
        this.serviceAppointmentRequest.customerRepresentativeContact = this.serviceAppointmentResponse.customerRepresentativeContact;
        this.serviceAppointmentRequest.pendingResponsibilityId = this.serviceAppointmentResponse.pendingResponsibilityId;
        this.serviceAppointmentRequest.remark = this.serviceAppointmentResponse.remark;
        this.serviceAppointmentRequest.earliestStartPermittedDate = this.serviceAppointmentResponse.earliestStartPermittedDate;
        this.serviceAppointmentRequest.earliestStartPermittedTime = this.serviceAppointmentResponse.earliestStartPermittedTime;
        this.serviceAppointmentRequest.dueDate = this.serviceAppointmentResponse.dueDate;
        this.serviceAppointmentRequest.dueTime = this.serviceAppointmentResponse.dueTime;
        this.serviceAppointmentRequest.tFlag = this.serviceAppointmentResponse.tFlag;

        this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetsSapSerialNumber;
        this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        this.serviceAppointmentRequest.acknowledgedByName=this.serviceAppointmentResponse.acknowledgedByName;
        this.serviceAppointmentRequest.acknowledgedByPhone=this.serviceAppointmentResponse.acknowledgedByPhone;
        //this.serviceAppointmentRequest.businessDomainId = this.buisnessDomainId;
        this.serviceAppointmentRequest.closedFromPlatform= this.serviceAppointmentResponse.closedFromPlatform
        if (this.serviceAppointmentResponse.caseReason == 'Installation') {
          this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetsSapSerialNumber;
          this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        }
        if(this.serviceAppointmentResponse.serviceAppointmentStatusId==4||this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          if(!confirm(`Please verify that you have done the inventory consumption while closing the Service appointment. If not required, please ignore this message. \n\nAfter closing the service appointment, system will not allow to add consumption!`)){
            return;
          }
        }
        this.spinner.show();
        this._serviceAppointment.updateServiceAppointment(this.serviceAppointmentRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.tempId = data.serviceAppointmentId;
          this.tempManual = data.serviceAppointmentManulaId;
          this.onServiceAppointmentCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      }
    }
  }
  cancelData() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  changeSerialNumber(){
    this.serviceAppointmentResponse.assetsPartCode=null;
    this.assetList.forEach(data=>{
      if(data.assetsSerialNumber==this.serviceAppointmentResponse.assetsSapSerialNumber){
        this.serviceAppointmentResponse.assetsPartCode=data.partCode;
      }
    });
  }
  dateConversion(date:Date){
    let d=new Date(date);
    d.setMinutes(d.getMinutes());
    // console.log(`Time offset ${d.getTimezoneOffset()} todayoffset ${this.today.getTimezoneOffset()}`);

    return d;
  }
  onVisitDatechange($event){
    this.serviceAppointmentResponse.visitDate=$event;
    console.log("on visit date : ", this.serviceAppointmentResponse.visitDate)
  }
   // shagufta
   onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(key.target.value.length>9){
      this.message="More than 10 digits can not be accept";
      this.popupSuccessModel.show();
      return false;
    }
    return true;

  }
}
