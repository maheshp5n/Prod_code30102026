import { DatePipe } from '@angular/common';
import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { getMinutes } from 'ngx-bootstrap/chronos/utils/date-getters';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AssetsService } from '../../../services/assets.services';
import { Loginservices } from '../../../services/login.services';
import { AssetsForList, AssetsResponse, CaseForList, CaseResponse, ClaimStatus, PendingResponsibility, Product, ProductsForList, ServiceAppointmentRequest, ServiceAppointmentResponse, ServiceAppointmentStatus } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtFsrService } from '../../bat_services/bt-fsr.service';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BTAssetForLIst, BtPendingReasonValue, BtResolutionMethod, BtSAServicePayout, btServiceAppointmentRequest, BTServiceAppointmentResponse, btServiceAppointmentResponseWithSCH, btSolutionImparted, BtSolutionImparted, ProblemReported } from '../../bt_model';

@Component({
  selector: 'app-bt-create-new-service-appointment',
  templateUrl: './bt-create-new-service-appointment.component.html',
  styleUrls: ['./bt-create-new-service-appointment.component.scss']
})
export class BtCreateNewServiceAppointmentComponent implements OnInit {

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('accountModel') accountModel: ModalDirective;
  @ViewChild('createCaseModel') createCaseModel: ModalDirective;
  @ViewChild('createServieResourceModel') createServieResourceModel: ModalDirective;
  @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('createProductModel') createProductModel: ModalDirective;

  @Output()
  onServiceAppointmentCreated: EventEmitter<BTServiceAppointmentResponse> = new EventEmitter<BTServiceAppointmentResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  btPendingReasonValue: BtPendingReasonValue[];
  _isEditMode: boolean;
  message: string;
  serviceAppointmentResponse: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  serviceAppointmentRequest: btServiceAppointmentRequest = new btServiceAppointmentRequest();
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
  assetList: Array<BTAssetForLIst> = [];
  assetForlist: AssetsForList = new AssetsForList();
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  tempId: string;
  tempManual: string;
  isServiceResourceModelShow: boolean;
  isCaseModelShow: boolean;
  isAccountModelShow: boolean;
  isProductModelShow: boolean;
  isAssetModelShow: boolean;
  foSCHonly:boolean=false;
  assetreq:string='col-md-6 col-sm-6 form-group';
  today = new Date();
  todayDate=new Date();
  _visitDate=new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  servicePayOUtList : Array<BtSAServicePayout> = [];
  btResolutionMethod: BtResolutionMethod[];
  issch:boolean=false;
  isSchFill:boolean=false;
  fileUploadListLength:number;
  _todayDate3:Date = new Date();
  _datetoday3:Date = new Date();
  
  schId:number;
  createDateMin:Date;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set fileUploadList(fileUploadList: number) {
    this.fileUploadListLength = fileUploadList;
    
  }

  get fileUploadList(): number {
    return this.fileUploadListLength;
  }

  @Input()
  set editServiceAppointmentData(serviceAppointmentRespons: btServiceAppointmentResponseWithSCH) {
     this.serviceAppointmentResponse = serviceAppointmentRespons;

    // console.log(this.serviceAppointmentResponse.createDate);
    // moment(this.value).format('YYYY/MM/DD')

      let createDateMin=this.datePipe.transform(this.serviceAppointmentResponse.createDate, 'yyyy-MM-dd');
      this.createDateMin = new Date(createDateMin);

      
      
      let _todayDate = new Date();
      _todayDate.setDate(_todayDate.getDate() -2);

      

      let _todayDate3 = this.datePipe.transform(_todayDate,'yyyy-MM-dd');
  
    
  
      if( this.createDateMin.getDate() < _todayDate.getDate() || this.createDateMin.getMonth() <_todayDate.getMonth() || this.createDateMin.getFullYear() < _todayDate.getFullYear())
      {
      
        
        this._todayDate3 = _todayDate;
        let datetoday3 = this.datePipe.transform(this._todayDate3,'yyyy-MM-dd');
        this._datetoday3 = new Date(datetoday3);

      }
      else{
        this._datetoday3 = this.today;
       
        

      }

     
      

 
    

// Find Greater sch ID
// for(let i=0;i<this.serviceAppointmentResponse.btSchOnlyResponses.length;i++)
// {
//        this.schId= this.serviceAppointmentResponse.btSchOnlyResponses[0].schOnlyId;
//       if(this.serviceAppointmentResponse.btSchOnlyResponses[i].schOnlyId>this.schId)
//       {
//          this.schId= this.serviceAppointmentResponse.btSchOnlyResponses[i].schOnlyId;

//       }
      
//     }
    // console.log(this.schId+"sch grater id ");
    // this.serviceAppointmentResponse.btSolutionImpartedProblemResponses=[];
    // for(let i=0;i<this.serviceAppointmentResponse.btSchOnlyResponses.length;i++)
    // {
    //   if(this.schId==this.serviceAppointmentResponse.btSchOnlyResponses[i].schOnlyId)
    //   {
    //     this.serviceAppointmentResponse.correctiveAction = this.serviceAppointmentResponse.btSchOnlyResponses[i].correctiveAction;
    //     this.serviceAppointmentResponse.resolutionMethodId = this.serviceAppointmentResponse.btSchOnlyResponses[i].resolutionMethodId;
    //     this.serviceAppointmentResponse.sparePart = this.serviceAppointmentResponse.btSchOnlyResponses[i].sparePartName;
    //     this.serviceAppointmentResponse.sparePartQuantity = this.serviceAppointmentResponse.btSchOnlyResponses[i].sparePartQuantity;
    //     this.serviceAppointmentResponse.sparePartReplaced = this.serviceAppointmentResponse.btSchOnlyResponses[i].sparePartReplaced;
    //     this.serviceAppointmentResponse.sparePartReplacedFrom = this.serviceAppointmentResponse.btSchOnlyResponses[i].sparePartReplacedFrom;
    //     // this.problemOccured  = this.serviceAppointmentResponse.btSolutionImpartedProblemResponses;
        
               

    //   }
    // }
     
     for(let i=0 ;i<this.serviceAppointmentResponse.btSolutionImpartedProblemResponses.length; i++)
     {
       this.problemOccured.push({id:this.serviceAppointmentResponse.btSolutionImpartedProblemResponses[i].id,
         solutionValue:this.serviceAppointmentResponse.btSolutionImpartedProblemResponses[i].solutinName})
       
       
     }
     
    //  if(this.serviceAppointmentResponse.visitDate==undefined||this.serviceAppointmentResponse.visitDate==null||this.serviceAppointmentResponse.visitDate==''){
    //     // this.serviceAppointmentResponse.visitDate=this.today;
    //  }
    //  else{
    //    this._visitDate=this.dateConversion(this.serviceAppointmentResponse.visitDate);
    //  }

     if(this.serviceAppointmentResponse.assetsSapSerialNumber!=null&&this.serviceAppointmentResponse.assetsSapSerialNumber!=undefined&&this.serviceAppointmentResponse.assetsSapSerialNumber!=''){
      // this.assetList.push({serialNumber:this.serviceAppointmentResponse.assetsSapSerialNumber,productPartCode:this.serviceAppointmentResponse.assetsPartCode,productDescription:this.serviceAppointmentResponse.assetDescription});
      // console.log(this.assetList)
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
  get editServiceAppointmentData(): btServiceAppointmentResponseWithSCH {
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
    private _btserviceAppointment: BtServiceappointmentService, private _btassetService: BTassetserviceService,
    private _global: GlobalserviceService, private caseservice:BtCaseserviceService,private Btfsr: BtFsrService) {
    this.dateCustomClasses = [      
      { date: this.serviceAppointmentResponse.visitDate , classes: ['bg-secondary'] }
    ];
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  
  
  AllProblemList: Array<BtSolutionImparted> = [];
  problem:BtSolutionImparted = new BtSolutionImparted();

  problemReported:Array<btSolutionImparted>=[];

  ngOnInit() {
    // this.issch = this.rights('BtServiceAppointment','edit');

    // console.log(this.rights('BtServiceAppointment','edit'));
    this.loginData = this.loginService.getLoggedInUser();
    // console.log(this.loginData);
    
    
    this.ownerName();
    this.getServiceAppointmentStatus();
    this.getPendingResponsibility();
    this.getClaimStatus();
    this.getCaseList();
    this.getTimeList();
    this.getProductList();
    this.getListOfServicePayout();
    this.ProblemReport(1);
    this.getBtPendingReasonCustomerBucket()
    this.getBtResolutionMethod();
    this.faPlus;
    this.isStatus(this.serviceAppointmentResponse.serviceAppointmentStatusId);
    this.isSchEng();
  
      // console.log(this.isSchENG);
   
    // console.log(this.serviceAppointmentResponse.serviceResourceName);
    
  }
  isSchENG:boolean=false;
  isSchEng(){
  
    // console.log(this.loginData.roleGroupAndAccount.roleGroup);
    // console.log(this.loginData.emailId+"Login email id ");
    // console.log(this.serviceAppointmentResponse.ownerEmailId+"Owner email id");
    
    
    
    if(this.loginData.roleGroupAndAccount.roleGroup=='SCH')
    {
      if(this.loginData.emailId==this.serviceAppointmentResponse.ownerEmailId)
      {

        this.isSchENG=false;
      }
      else{
        this.isSchENG=true;
      }
      
    }
    else{
      this.isSchENG=false;
    }


  }
  ProblemReport(id) {
    this.spinner.show();
    this.Btfsr.getBtSolutionImparted()
    .subscribe(data => {
      // console.log(data);
      this.AllProblemList = data;
      // console.log(this.AllProblemList);
      this.spinner.hide()
    });

    
  }
  problemOccured: Array<BtSolutionImparted> = [];
  
  
    // multiple array
    moveRight(selectedReason) {
      for(let i=0;i<selectedReason.length;i++){
        
      if (selectedReason!== undefined) {  
        const index = this.AllProblemList.indexOf(selectedReason[i]);
      
        this.problemOccured.push(selectedReason[i]);
        this.AllProblemList.splice(index,selectedReason.length);

        // console.log(this.AllProblemList);
        // console.log(this.problemOccured);

        
        
        this.problem = new BtSolutionImparted();
      } else {
        this.message = "Please select any 1 problem";
        this._global.alert({type:'alert',message:this.message})
      }
    }
    }
    moveLeft(unselectReason) {
    
      for(let i=0;i<unselectReason.length;i++){
      if (unselectReason!== undefined) {
        const index = this.problemOccured.indexOf(unselectReason[i]);
        this.AllProblemList.push(unselectReason[i]);
        this.problemOccured.splice(index,1);
        this.problem = new BtSolutionImparted();
      } else {
        this.message = "Please select any 1 problem";
        this._global.alert({type:'alert',message:this.message})
       
  
      }
    }
    }
    getBtPendingReasonCustomerBucket() {
     
        this.Btfsr.getBtPendingReasonCustomerBucket().subscribe((res) => {
          this.btPendingReasonValue = res;
    
        }, (error) => {
    
        })
      }
      getBtResolutionMethod() {
        this.Btfsr.getBtResolutionMethod().subscribe((res) => {
          this.btResolutionMethod = res;
    
        }, (error) => {
    
        })
      }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
    // this.serviceAppointmentResponse.ownerEmailId = this.loginData.emailId;
  }

  getServiceAppointmentStatus() {
    this._btserviceAppointment.getServiceAppointmentStatus().subscribe(data => {
      this.statusList = data;
    }, err => {

    });
  }
  getPendingResponsibility() {
    this._btserviceAppointment.getPendingResponsibility().subscribe(data => {
      for(let i=0;i<2;i++)
      {
        this.pendingResList[i] = data[i];

      }
    }, err => {

    });
  }
  getClaimStatus() {
    this._btserviceAppointment.getClaimStatus().subscribe(data => {
      this.claimStatusList = data;
    }, err => {

    });
  }
  getCaseList() {
    this._btserviceAppointment.getCaseLists().subscribe(data => {
      this.caseList = data;
    }, err => {

    });
  }

  getTimeList() {
    this._btserviceAppointment.getTimeList().subscribe(data => {
      this.timeList = data;
    }, err => {

    });
  }
  getProductList() {
    this._btassetService.getProductForList().subscribe(data => {
      this.productList = data;
    }, err => {

    })
  }
  getAssetList() {
    this._btassetService.getAssetsForListForInstallation().subscribe(data => {
      this.assetList = data;
    }, err => {
    })
  }

  getAssetsListSmall(key){
    this._btassetService.getListForInstallationSmall(key.term).subscribe(data=>{
      this.assetList=data.content;
      if(this.serviceAppointmentResponse.assetsSapSerialNumber!=null&&this.serviceAppointmentResponse.assetsSapSerialNumber!=undefined&&this.serviceAppointmentResponse.assetsSapSerialNumber!=''){
        // this.assetList.push({serialNumber:this.serviceAppointmentResponse.assetsSapSerialNumber,productPartCode:this.serviceAppointmentResponse.assetsPartCode,productDescription:this.serviceAppointmentResponse.assetDescription});
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
  getListOfServicePayout(){
    this.spinner.show();
    this._btserviceAppointment.getListOfservicePayout().subscribe(data=>{
      if(data!=null){
        this.spinner.hide();
      }
      this.servicePayOUtList = data;
    })   
  }
  disableRma:boolean=false;
  isStatus(statusId)
  {
      if(statusId==3 || statusId==4)
      {
        this.serviceAppointmentResponse.rmaCreated = false;
        this.disableRma = true;
      }
      else{
        this.disableRma = false;
      }
  }
  resolutionMethodChnage(id){
    if(id==1){
      this.serviceAppointmentResponse.sparePartReplaced='';
      this.serviceAppointmentResponse.sparePartQuantity=null;
      this.serviceAppointmentResponse.sparePart='';
      this.serviceAppointmentResponse.sparePartReplacedFrom='';


    }
    
  }
   //Validation for numeric type value

   keyPressNumbersWithDecimal(event) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode != 46 && charCode > 31
      && (charCode < 48 || charCode > 57)) {
      event.preventDefault();
      return false;
    }
    return true;
  }
   //Validation for numric tye value
   numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

  // Post and put method
  postCreateServiceAppointment() {
    if ((this.fileUploadListLength == null || this.fileUploadListLength === 0) && (this.serviceAppointmentResponse.serviceAppointmentStatusId!=3) && (this.serviceAppointmentResponse.uploadfsrMandatory==true)) {     
      this._global.alert({ type: 'alert', message: 'Please Upload FSR To Edit This Service Appointment' });
     } 
     else if ((this.serviceAppointmentResponse.fsrCreated == false ) && (this.serviceAppointmentResponse.serviceAppointmentStatusId!=3) && (this.serviceAppointmentResponse.fsrMandatory==true)) {     
      this._global.alert({ type: 'alert', message: 'Please Create FSR To Edit This Service Appointment' });
     }
    else if (this.serviceAppointmentResponse.caseId == null || this.serviceAppointmentResponse.caseId === 0) {     
     this._global.alert({ type: 'alert', message: 'Please Fill The Case' });
    }
    
     else if (this.serviceAppointmentResponse.serviceAppointmentStatusId == null ||
      this.serviceAppointmentResponse.serviceAppointmentStatusId === 0) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Status' });
    } else if (this.serviceAppointmentResponse.pendingResponsibilityId == null ||
      this.serviceAppointmentResponse.pendingResponsibilityId === 0) {     
      this._global.alert({ type: 'alert', message: 'Please Fill The Pending Responsibility' });
    }
    // else if(this.serviceAppointmentResponse.faultObserve == undefined || this.serviceAppointmentResponse.faultObserve==null){      
    //   this._global.alert({ type: 'alert', message: 'Please Fill The Fault Observed' });
    // }
    // else if ((this.serviceAppointmentResponse.faultObserve).length >= 1000) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The Fault Observed Under 1000 Characters' });
    // } 
   
    // else if (this.serviceAppointmentResponse.punchPointExicom != undefined &&
    //   (this.serviceAppointmentResponse.punchPointExicom).length >= 1000) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The Punch Point Exiom Under 1000 Characters' });
    // }
    // else if (this.serviceAppointmentResponse.punchPointCustomer != undefined &&
    //   (this.serviceAppointmentResponse.punchPointCustomer).length >= 1000) {
    //   this._global.alert({ type: 'alert', message: 'Please Fill The Punch Point Customer Under 1000 Characters' });
    // }
    else if (this.serviceAppointmentResponse.remark != undefined &&
      (this.serviceAppointmentResponse.remark).length >= 1000) {
      this._global.alert({ type: 'alert', message: 'Please Fill The Remarks Under 500 Characters' });
    }
    // else if(this.serviceAppointmentResponse.caseReason=='Installation' && this.serviceAppointmentResponse.assetsSapSerialNumber==undefined && this.serviceAppointmentResponse.assetsSapSerialNumber==null){
    //   this._global.alert({ type: 'alert', message: 'Please Fill Asset Serial Number' });
    // }
    else if(this.serviceAppointmentResponse.acknowledgedByName==undefined || this.serviceAppointmentResponse.acknowledgedByName==''){
      this._global.alert({ type: 'alert', message: 'Please Fill Signoff Name' });
    }
    else if(this.serviceAppointmentResponse.acknowledgedByPhone==undefined || this.serviceAppointmentResponse.acknowledgedByPhone==''){
      this._global.alert({ type: 'alert', message: 'Please Fill Asset Signoff Number' });
    }
    else if(this.serviceAppointmentResponse.acknowledgedByPhone.length<10){
      this._global.alert({ type: 'alert', message: 'Please Fill Asset Signoff Number In 10 Digit' });
    }
    // else if(this.serviceAppointmentResponse.servicePayout == null || this.serviceAppointmentResponse.servicePayout== 0){
    //   this._global.alert({ type: 'alert', message: 'Please Fill Service Payout' });
    // }
    else if((this.serviceAppointmentResponse.pendingResponsibilityId==1)&& (this.serviceAppointmentResponse.pendingResource==null || this.serviceAppointmentResponse.pendingResource=='' || this.serviceAppointmentResponse.pendingResource==null)){
   
      this._global.alert({ type: 'alert', message: 'Please Fill Exicom Bucket' });
     }
    else if((this.serviceAppointmentResponse.pendingResponsibilityId==2) && this.serviceAppointmentResponse.customerBucketReasonId==0 || this.serviceAppointmentResponse.customerBucketReasonId==undefined || this.serviceAppointmentResponse.customerBucketReasonId==null){
    
        this._global.alert({ type: 'alert', message: 'Please Fill Customer Bucket' });
      }
  
    else {
      // console.log( this.rights('BtSchUseOnlyDetails','create'));
      
     
      // this.serviceAppointmentResponse.visitDate=this.datePipe.transform(this.cre, 'yyyy-MM-dd');
   
      if (!this._isEditMode) {
        this.serviceAppointmentRequest.ownerId = this.serviceAppointmentResponse.ownerEmailId;
        this.serviceAppointmentRequest.caseId = this.serviceAppointmentResponse.caseId;
        this.serviceAppointmentRequest.faultObserved = this.serviceAppointmentResponse.faultObserve;
        // this.serviceAppointmentRequest.correctiveActions;
        this.serviceAppointmentRequest.punchPointExiom = this.serviceAppointmentResponse.punchPointExicom;
        this.serviceAppointmentRequest.punchPointCustomer = this.serviceAppointmentResponse.punchPointCustomer;
        this.serviceAppointmentRequest.customerRate = this.serviceAppointmentResponse.customerRate;
        this.serviceAppointmentRequest.customerBillingRate = this.serviceAppointmentResponse.customerBillingRate;
        if(this.serviceAppointmentResponse.visitDate!=null || this.serviceAppointmentResponse.visitDate!=undefined)
        {

          // this.serviceAppointmentRequest.visitDate = this.serviceAppointmentResponse.visitDate.toString();
        }
        this.serviceAppointmentRequest.statusId = this.serviceAppointmentResponse.serviceAppointmentStatusId;
        this.serviceAppointmentRequest.customerRepresentativeName = this.serviceAppointmentResponse.customerRepresentativeName;
        this.serviceAppointmentRequest.customerRepresentativeContact = this.serviceAppointmentResponse.customerRepresentativeContact;
        this.serviceAppointmentRequest.pendingResponsibilityId = Number( this.serviceAppointmentResponse.pendingResponsibilityId);
        this.serviceAppointmentRequest.remark = this.serviceAppointmentResponse.remark;
        this.serviceAppointmentRequest.earliestStartPermittedDate = this.serviceAppointmentResponse.earliestStartPermittedDate;
        this.serviceAppointmentRequest.earliestStartPermittedTime = this.serviceAppointmentResponse.earliestStartPermittedTime;
        this.serviceAppointmentRequest.dueDate = this.serviceAppointmentResponse.dueDate;
        this.serviceAppointmentRequest.dueTime = this.serviceAppointmentResponse.dueTime;
        this.serviceAppointmentRequest.tFlag = this.serviceAppointmentResponse.tFlag;
        this.serviceAppointmentRequest.partCode= this.serviceAppointmentResponse.productId
        // this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetsSapSerialNumber;
        this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        this.serviceAppointmentRequest.acknowledgedByName=this.serviceAppointmentResponse.acknowledgedByName;
        this.serviceAppointmentRequest.acknowledgedByPhone=this.serviceAppointmentResponse.acknowledgedByPhone;
        if(this.serviceAppointmentResponse.rmaCreated==false && this.serviceAppointmentResponse.serviceAppointmentStatusId==5)
        {
          this.serviceAppointmentResponse.servicePayoutId = 2; 

        }else if(this.serviceAppointmentResponse.rmaCreated==true && this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          this.serviceAppointmentResponse.servicePayoutId = 3; 
        }
        else if(this.serviceAppointmentResponse.rmaCreated==false && this.serviceAppointmentResponse.serviceAppointmentStatusId==4){
          this.serviceAppointmentResponse.servicePayoutId = 4; 
        }
        else{
          this.serviceAppointmentResponse.servicePayoutId = 0; 
        }
        this.serviceAppointmentRequest.servicePayout = this.serviceAppointmentResponse.servicePayoutId;
        this.serviceAppointmentRequest.rma = this.serviceAppointmentResponse.rmaCreated;       
        this.serviceAppointmentRequest.partCode= this.serviceAppointmentResponse.productId;
   
        // this.serviceAppointmentResponse.btSolutionImpartedProblemResponses = this.serviceAppointmentResponse.btSolutionImpartedProblemResponses;
        if(this.serviceAppointmentResponse.serviceAppointmentStatusId==4||this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          if(!confirm(`Please Verify That You Have Done The Inventory Consumption While Closing The Service Appointment. If Not Required, Please Ignore This Message. \n\nAfter Closing The Service Appointment, System Will Not Allow To Add Consumption!`)){
            return;
          }
        }
        this.spinner.show();
        this._btserviceAppointment.postServiceAppoinment(this.serviceAppointmentRequest).subscribe(data => {
          this.tempId = data.serviceAppointmentId;
          this.tempManual = data.manualId;
          this._global.alert({ type: '', message: data.manualId+' Created Successfully' });
          this.onServiceAppointmentCreated.emit(data);              
          this.onCancle.emit('cancled');
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
        this.serviceAppointmentRequest.serviceAppointmentId = this.serviceAppointmentResponse.appointmentId;
        this.serviceAppointmentRequest.ownerId = this.serviceAppointmentResponse.ownerEmailId;
        this.serviceAppointmentRequest.caseId = this.serviceAppointmentResponse.caseId;
        this.serviceAppointmentRequest.faultObserved = this.serviceAppointmentResponse.faultObserve;
        this.serviceAppointmentRequest.correctiveActions = this.serviceAppointmentResponse.correctiveAction;
        this.serviceAppointmentRequest.punchPointExiom = this.serviceAppointmentResponse.punchPointExicom;
        this.serviceAppointmentRequest.punchPointCustomer = this.serviceAppointmentResponse.punchPointCustomer;
     
      
        let DateReq = new Date();
        
            DateReq = this.serviceAppointmentResponse.visitDate;
        let nowTime = new Date();
        let myDateVis  = new Date(DateReq);
       
        
        myDateVis.setHours(nowTime.getHours() + 5);
        myDateVis.setMinutes(nowTime.getMinutes() + 30);
       

        this.serviceAppointmentRequest.visitDate =  this.datePipe.transform(myDateVis,'yyyy-MM-dd HH:mm:ss');
        
        
        

       
        

        this.serviceAppointmentRequest.statusId = this.serviceAppointmentResponse.serviceAppointmentStatusId;
        this.serviceAppointmentRequest.customerRate = this.serviceAppointmentResponse.customerRate;
        this.serviceAppointmentRequest.customerBillingRate = this.serviceAppointmentResponse.customerBillingRate;
        this.serviceAppointmentRequest.customerRepresentativeName = this.serviceAppointmentResponse.customerRepresentativeName;
        this.serviceAppointmentRequest.customerRepresentativeContact = this.serviceAppointmentResponse.customerRepresentativeContact;
        this.serviceAppointmentRequest.pendingResponsibilityId = Number( this.serviceAppointmentResponse.pendingResponsibilityId);
        this.serviceAppointmentRequest.remark = this.serviceAppointmentResponse.remark;
        this.serviceAppointmentRequest.earliestStartPermittedDate = this.serviceAppointmentResponse.earliestStartPermittedDate;
        this.serviceAppointmentRequest.earliestStartPermittedTime = this.serviceAppointmentResponse.earliestStartPermittedTime;
        this.serviceAppointmentRequest.dueDate = this.serviceAppointmentResponse.dueDate;
        this.serviceAppointmentRequest.dueTime = this.serviceAppointmentResponse.dueTime;
        this.serviceAppointmentRequest.tFlag = this.serviceAppointmentResponse.tFlag;
        // this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetId;
        this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        this.serviceAppointmentRequest.acknowledgedByName=this.serviceAppointmentResponse.acknowledgedByName;
        this.serviceAppointmentRequest.acknowledgedByPhone=this.serviceAppointmentResponse.acknowledgedByPhone;
        this.serviceAppointmentRequest.closedFromPlatform= this.serviceAppointmentResponse.closedFromPlatform;    
        if(this.serviceAppointmentResponse.rmaCreated==false && this.serviceAppointmentResponse.serviceAppointmentStatusId==5)
        {
          this.serviceAppointmentResponse.servicePayoutId = 2; 

        }else if(this.serviceAppointmentResponse.rmaCreated==true && this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          this.serviceAppointmentResponse.servicePayoutId = 3; 
        }
        else if(this.serviceAppointmentResponse.rmaCreated==false && this.serviceAppointmentResponse.serviceAppointmentStatusId==4){
          this.serviceAppointmentResponse.servicePayoutId = 4; 
        }
        else{
          this.serviceAppointmentResponse.servicePayoutId = 0; 
        }
        this.serviceAppointmentRequest.servicePayout = this.serviceAppointmentResponse.servicePayoutId;
        this.serviceAppointmentRequest.rma = this.serviceAppointmentResponse.rmaCreated;      
        this.serviceAppointmentRequest.partCode= this.serviceAppointmentResponse.productId;
        this.serviceAppointmentRequest.sparePart= this.serviceAppointmentResponse.sparePart;
        this.serviceAppointmentRequest.sparePartQuantity= Number(this.serviceAppointmentResponse.sparePartQuantity);
        this.serviceAppointmentRequest.sparePartReplaced= this.serviceAppointmentResponse.sparePartReplaced;
        this.serviceAppointmentRequest.address= this.serviceAppointmentResponse.address;
        this.serviceAppointmentRequest.customerPendingBucketId= this.serviceAppointmentResponse.customerBucketReasonId;
        this.serviceAppointmentRequest.customerBucketReasonName= this.serviceAppointmentResponse.customerBucketReasonName;
        // this.serviceAppointmentRequest.solutionImparted= this.serviceAppointmentResponse.btSolutionImpartedProblemResponses;
        this.serviceAppointmentRequest.resolutionMethod= this.serviceAppointmentResponse.resolutionMethodId;
        this.serviceAppointmentRequest.pendingResource= this.serviceAppointmentResponse.pendingResource;
        this.serviceAppointmentRequest.sparePartReplacedFrom= this.serviceAppointmentResponse.sparePartReplacedFrom;
        this.serviceAppointmentRequest.billingRequired= this.serviceAppointmentResponse.billingRequired;
      


        if(this.rights('BtSchUseOnlyDetails','create'))
        {
       
          
          this.serviceAppointmentRequest.schFill = true;

        }else{
          this.serviceAppointmentRequest.schFill = false;
        }
        

        
        //for SCH use

        this.serviceAppointmentRequest.solutionImparted=[];
        for(let i=0 ;i<this.problemOccured.length; i++)
        {
          this.serviceAppointmentRequest.solutionImparted.push({id:this.problemOccured[i].id,solutionName:this.problemOccured[i].solutionValue})

          
        }
       
        if (this.serviceAppointmentResponse.caseReasonName == 'Installation') {
      
          this.serviceAppointmentRequest.assetsSerialNumber = this.serviceAppointmentResponse.assetsSapSerialNumber;
          this.serviceAppointmentRequest.partCode = this.serviceAppointmentResponse.assetsPartCode;
        }
        if(this.serviceAppointmentResponse.serviceAppointmentStatusId==4||this.serviceAppointmentResponse.serviceAppointmentStatusId==5){
          if(!confirm(`Please Verify That You Have Done The Inventory Consumption While Closing The Service Appointment. If Not Required, Please Ignore This Message. \n\nAfter Closing The Service Appointment, System Will Not Allow To Add Consumption!`)){
            return;
          } 
        }
        
        if(this.rights('BtSchUseOnlyDetails','create'))
        {
            if(this.serviceAppointmentRequest.customerRate==undefined || this.serviceAppointmentRequest.customerRate==null || this.serviceAppointmentRequest.customerRate=='')
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Site visit charge' });
            }
            else if(this.serviceAppointmentRequest.customerBillingRate==undefined || this.serviceAppointmentRequest.customerBillingRate==null || this.serviceAppointmentRequest.customerBillingRate=='')
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Customer Spare Charges' });
            }
            else if( this.serviceAppointmentRequest.correctiveActions== '' || this.serviceAppointmentRequest.correctiveActions==undefined)
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Corrective Actions' });
            }
           else if( this.serviceAppointmentRequest.resolutionMethod== null || this.serviceAppointmentRequest.resolutionMethod==0 )
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Resolution Method' });
            }
 
            else if(( this.serviceAppointmentRequest.sparePart== '' || this.serviceAppointmentRequest.sparePart==undefined || this.serviceAppointmentRequest.sparePart==null)&& (this.serviceAppointmentResponse.resolutionMethodId==1))
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Spare Part Name' });
            }

            else if( (this.serviceAppointmentRequest.sparePartReplaced== '' || this.serviceAppointmentRequest.sparePartReplaced==undefined)&& (this.serviceAppointmentResponse.resolutionMethodId==1))
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Spare Part Replaced' });
            }
            else if( (this.serviceAppointmentRequest.sparePartQuantity== 0 || this.serviceAppointmentRequest.sparePartQuantity== null || this.serviceAppointmentRequest.sparePartQuantity==undefined)&&(this.serviceAppointmentResponse.resolutionMethodId==1))
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Spare Part Quantity' });
            }
            else if( (this.serviceAppointmentRequest.sparePartReplacedFrom== '' || this.serviceAppointmentRequest.sparePartReplacedFrom==undefined || this.serviceAppointmentRequest.sparePartReplacedFrom==null)&& (this.serviceAppointmentResponse.resolutionMethodId==1))
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Spare Part Replaced From' });
            }
            else if( this.serviceAppointmentRequest.solutionImparted.length == 0 || this.serviceAppointmentRequest.solutionImparted.length ==undefined || this.serviceAppointmentRequest.solutionImparted.length ==null)
            {
              this._global.alert({ type: 'alert', message: 'Please Fill Problem Reported' });
            }
            else{
              this.postServiceAppointment();
            }
        
        }
        else
        {
          this.postServiceAppointment();
        }
      
          
         
      
        
        
        // this.spinner.show();
        // this._btserviceAppointment.updateServiceAppointment(this.serviceAppointmentRequest.serviceAppointmentId,this.serviceAppointmentRequest).subscribe(data => {          
        //   this._global.alert({ type: '', message: 'Updated Successfully' });
        //   this.tempId = data.serviceAppointmentId;
        //   this.tempManual = data.serviceAppointmentManulaId;
        //   this.onServiceAppointmentCreated.emit(data);
        //   if (data != null) {
        //     this.spinner.hide();
        //   }
        // }, err => {
        //   this.spinner.hide();
        //   this.message = err.error.message;
        //   this._global.alert({ type: 'alert', message: this.message });
        // }
        // );
      }
    }
  }

  postServiceAppointment(){

    // console.log(this.serviceAppointmentRequest);

     this.spinner.show();
        this._btserviceAppointment.updateServiceAppointment(this.serviceAppointmentRequest.serviceAppointmentId,this.serviceAppointmentRequest).subscribe(data => {          
          this._global.alert({ type: '', message: data.manualId+' Updated Successfully' });
          this.tempId = data.serviceAppointmentId;
          this.tempManual = data.manualId;
          this.onServiceAppointmentCreated.emit(data);
          this.onCancle.emit('cancled');
          if (data != null) {
            this.spinner.hide();
          }
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        }
        );
  }
  cancelData() {
    this.serviceAppointmentResponse = new btServiceAppointmentResponseWithSCH();
    this.onCancle.emit('cancled');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
  changeSerialNumber(value){
    this.serviceAppointmentResponse.productPartCode=null;
    this.serviceAppointmentResponse.assetsSapSerialNumber=value;
    this.assetList.forEach(data=>{    
      if(value==data.assetId){
       
        this.serviceAppointmentResponse.productPartCode=data.productPartCode;
        this.serviceAppointmentResponse.productId= data.productId;
        // console.log(this.serviceAppointmentResponse.productId)
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
    let visitDate = this.datePipe.transform(this.serviceAppointmentResponse.visitDate,'yyyy-MM-dd HH:mm:ss')
    
    
    this.serviceAppointmentResponse.visitDate  = new Date(visitDate);
   
    
  }
   // shagufta
   onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(key.target.value.length>9){
      this._global.alert({ type: 'alert', message: 'More Than 10 Digits Can Not Be Accept' });     
      return false;
    }
    return true;

  }

}
