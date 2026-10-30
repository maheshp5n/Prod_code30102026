import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { CommercialService } from '../../../services/commercial.services';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, BatteryFSRResponse, CaseInvoiceRequest, caseLogReject, CaseRequest, CaseResponse, FileUploadResponse, OppertunityRequest, OppertunityResponse, QuoteResponse, SecondServiceAppointmentRequest, ServiceAppointmentProductConsumedResponse, ServiceAppointmentResponse, ToBeApprovedHistoryResponse } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtCommercialService } from '../../bat_services/bt-commercial.service';
import { BTCaseResponse, BtCustomerWccResp, BtCustomerWccRejectionList, BtWccCustomerApproveRequest, ProblemReported, BtCustomerWccRejectReq, BTCaseProofOfDeliveryRequest, BtWccCaseResp } from '../../bt_model';
import * as fileSaver from 'file-saver';
import { BtWccCustomerService } from '../../bat_services/bt-wcc-customer.service';
import { GlobalserviceService } from '../../../globalservice.service';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BtTobeApprovedService } from '../../bat_services/bat-to-beapprovedservice';

@Component({
  selector: 'app-bat-wcc-customer-detail',
  templateUrl: './bat-wcc-customer-detail.component.html',
  styleUrls: ['./bat-wcc-customer-detail.component.css']
})
export class BatWccCustomerDetailComponent implements OnInit {

  // @ViewChild('createInvoiceModel', { static: false }) createInvoiceModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  // @ViewChild('sureModel',{static:false}) sureModel:ModalDirective;
  // @ViewChild('rejectcasemodel',{static:false}) rejectcasemodel:ModalDirective;
  // @ViewChild('choosenFilesShow',{static:false}) choosenFilesShow:ModalDirective;
  @ViewChild('createWccCustomermodel',{static:false}) createWccCustomermodel :ModalDirective;
  @ViewChild('rejectWccCustomermodel',{static:false}) rejectWccCustomermodel :ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('filePreviewModel', { static: false }) filePreviewModel: ModalDirective;
  @ViewChild('createPD',{static:false}) createPD:ModalDirective;
  @ViewChild('approvalModule',{static:false})approvalModule:ModalDirective;

 
  isEditMode: boolean = false;
  historyList: Array<ToBeApprovedHistoryResponse> = [];
  heading: string;
  edit: boolean = false;
  loginData;
  fileName: string;
  imageUrl: string;
  wccid: any
  message: string = "";
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  caseid: number;
  wccCaseResp:Array<BtWccCaseResp>=[];
  wccCustomerReq:BtWccCustomerApproveRequest = new BtWccCustomerApproveRequest();
  reasonList:Array<BtCustomerWccRejectionList>=[];
  rejectReasonReq:BtCustomerWccRejectReq= new BtCustomerWccRejectReq();
  fileUploadList: Array<FileUploadResponse> = [];
  PODfileUploadList: Array<FileUploadResponse> = [];

  caseres: BTCaseResponse = new BTCaseResponse()
  expanceApprovalId:number;
  actiondata={
    action:'00',
    remarks:undefined,
    id:0
  }
  // CREATE_ACCOUNT_HEADING: string = 'Create Case';
  // EDIT_ACCOUNT_HEADING: string = 'Edit Case'
  
  // page: number = 0;
  // sortColumnName: string;
  // sortOrder: string;

  // first: boolean;
  // last: boolean;
  // serialNumber: number;
  // size: number;
  // isEditModelShow: boolean = false;
  // RejectLogList:Array<caseLogReject>=[];

  AllBtWccCustomer :BtCustomerWccResp=new BtCustomerWccResp();
  wccApprovalId:number;
  btnshow:boolean=false;

              // approvalmessage:string=;
  // addressDisplay = {
  //   angleIcon: 'fa fa-angle-down',
  //   open: true
  // };
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
  // siteDetaiDisplay = {
  //   angleIcon: 'fa fa-angle-down',
  //   open: true
  // }
  // AssignmentDetaiDisplay = {
  //   angleIcon: 'fa fa-angle-down',
  //   open: true
  // }
  // problemDetaiDisplay = {
  //   angleIcon: 'fa fa-angle-down',
  //   open: true
  // }
  // proofDetaiDisplay = {
  //   angleIcon: 'fa fa-angle-down',
  //   open: true
  // }
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
  objectName:string;
  objectPODName:string;
  modelsOpenState = { wccEditModelOpen: false }
actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private _global: GlobalserviceService,
    private loginservice: Loginservices,
     private _activatedRoute: ActivatedRoute, 
     private spinner: NgxSpinnerService,
    private btwccCustomerService:BtWccCustomerService, 
     private caseservice: BtCaseserviceService, 
     private approvalHistoryService: ApprovalHistoryService,
     private _serviceAppointmentService: ServiceAppointmentService,private tobeapproved:BtTobeApprovedService) { 
    this._activatedRoute.params.subscribe(pram => {
      this.wccid = pram['id'];
      this.actionHistoryObjectAndId.objectId = this.wccid;
      this.actionHistoryObjectAndId.objectName = 'BtCustomerWcc';
      this.objectName = 'BtCustomerWcc';
      this.objectPODName = 'BtPODUpdate';
      this.getApprovalButtonRsh();

  });
  this._activatedRoute.queryParams.subscribe(pram => {
    this._cFlag = pram['_c'];
  });

  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.showWccResponse();
    this.getReasonList();
    this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);

  }

  getReasonList(){
    this.btwccCustomerService.getReasonList().subscribe(data=>{
      this.reasonList=data;
    })
  }
  // changeAddressDisplay() {
  //   this.addressDisplay.open = !this.addressDisplay.open
  //   if (this.addressDisplay.open) {
  //     this.addressDisplay.angleIcon = 'fa fa-angle-down';
  //   }
  //   else {
  //     this.addressDisplay.angleIcon = 'fa fa-angle-right';
  //   }
  // }
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
  // changeSiteDetailDisplay() {
  //   this.siteDetaiDisplay.open = !this.siteDetaiDisplay.open
  //   if (this.siteDetaiDisplay.open) {
  //     this.siteDetaiDisplay.angleIcon = 'fa fa-angle-down';
  //   }
  //   else {
  //     this.siteDetaiDisplay.angleIcon = 'fa fa-angle-right';
  //   }
  // }
  // assignmentDisplay() {
  //   this.AssignmentDetaiDisplay.open = !this.AssignmentDetaiDisplay.open
  //   if (this.AssignmentDetaiDisplay.open) {
  //     this.AssignmentDetaiDisplay.angleIcon = 'fa fa-angle-down';
  //   }
  //   else {
  //     this.AssignmentDetaiDisplay.angleIcon = 'fa fa-angle-right';
  //   }
  // }


  // changeProblemDetailDisplay() {
  //   this.problemDetaiDisplay.open = !this.problemDetaiDisplay.open
  //   if (this.problemDetaiDisplay.open) {
  //     this.problemDetaiDisplay.angleIcon = 'fa fa-angle-down';
  //   }
  //   else {
  //     this.problemDetaiDisplay.angleIcon = 'fa fa-angle-right';
  //   }
  // }
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
  // proofDisplay() {
  //   this.proofDetaiDisplay.open = !this.proofDetaiDisplay.open
  //   if (this.sysinfoDisplay.open) {
  //     this.proofDetaiDisplay.angleIcon = 'fa fa-angle-down';
  //   }
  //   else {
  //     this.proofDetaiDisplay.angleIcon = 'fa fa-angle-right';
  //   }

  // }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.showWccResponse();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getApprovalHistory();
      this.getWccCase();
      this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);
      this.getPODFileList(this.objectPODName, this.wccid);

      //  this.rejectLogs();

    }
    return false;
  }
  
  getApprovalHistory() {
    
    this.spinner.show();
    this.approvalHistoryService.getApprovalStatusbt(this.objectName, this.wccid).subscribe(data => {
      this.spinner.hide();
      this.historyList = data;
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
     
    });
  }
  canreject:boolean=false;
  showWccResponse() {
    this.spinner.show();
    this.btwccCustomerService.getWccCustomerByID(this.wccid).subscribe(data => {
      this.spinner.hide();
      this.AllBtWccCustomer = data;
      this.getPODFileList(this.objectPODName, this.wccid);


    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
      // this.popupSuccessModel.show();

    });
  }


  wccCustomerApprove()
  {
    this.showWccResponse();
    // this.wccCustomerReq.customerPoDate=this.AllBtWccCustomer.podate;
    // this.wccCustomerReq.customerPo=this.AllBtWccCustomer.ponumber;
    this.wccCustomerReq.invoiceDate=this.AllBtWccCustomer.invoice_date;
    this.wccCustomerReq.invoiceNumber=this.AllBtWccCustomer.invoice_number;
    this.wccCustomerReq.id=this.AllBtWccCustomer.id;
    this.createWccCustomermodel.show();
  }

  onWccCustomerCreated()
  {
    this.showWccResponse();
    this.getApprovalHistory();
    this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);
    this.getPODFileList(this.objectPODName, this.wccid);

  }
  
  closeRejectModel()
  {
    this.rejectWccCustomermodel.hide();
  }

  rejectWccCustomer()
  {
    if(this.AllBtWccCustomer.wccRejectReasonId==null){
      this.rejectReasonReq.rejectReasonId=0;
    }else{
      this.rejectReasonReq.rejectReasonId=this.AllBtWccCustomer.wccRejectReasonId;

    }
    this.rejectReasonReq.remarks="";
    this.rejectWccCustomermodel.show();
  }

  rejected(){
    if (this.rejectReasonReq.rejectReasonId === null || this.rejectReasonReq.rejectReasonId === undefined || this.rejectReasonReq.rejectReasonId==0) {
      this.message = "Please Fill Rejection Reason";
      this._global.alert({ type: 'alert', message: this.message });
    }
    else if (this.rejectReasonReq.remarks === null || this.rejectReasonReq.remarks === undefined || this.rejectReasonReq.remarks.trim()=="") {
      this.message = "Please Fill Remarks";
      this._global.alert({ type: 'alert', message: this.message });
 
    }
    else{
      this.rejectReasonReq.id=this.AllBtWccCustomer.id;
      this.spinner.show();
      this.btwccCustomerService.rejectWccCustomer(this.rejectReasonReq).subscribe(data=>{
        this.getApprovalButton(this.AllBtWccCustomer.id);

        this.spinner.hide();
        this._global.alert({type:'', message: 'Rejected Sucessfully' });
        this.showWccResponse();
        this.closeRejectModel();
        this.getApprovalHistory();
        this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);
        this.getPODFileList(this.objectPODName, this.wccid);
      },err=>{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });

      })

    }
  }
 
  getApprovalButton(id) {
    this.spinner.show()
    this.tobeapproved.getItemToBeApprovdButton("BtCustomerWcc", id).subscribe(data => {
      console.log(data);
      this.spinner.hide();
      if( data.id != null && data != null && data != undefined)
      {
        this.expanceApprovalId = data.id;
        this.saveDetails(this.expanceApprovalId);
  
      }
    }, error=>{
  
    });
  }
  
  
   saveDetails(id){
    // this.getApprovalButton();
    this.actiondata.id=id;
    this.actiondata.action="Rejected";
    this.actiondata.remarks=this.rejectReasonReq.remarks;  
   this.spinner.show()
    this.tobeapproved.updateAction(this.actiondata.id,this.actiondata).subscribe(data=>{
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
    
  }

  casePDRequest:BTCaseProofOfDeliveryRequest=new BTCaseProofOfDeliveryRequest();
   caseselectlist:any[]=[]
  // pofDModelshow(){
  //   this.heading="Proof Of Delivery (For SCH use only)"
  //    this.isEditMode=true;
  //   this.spinner.show();
  //   this.caseservice.getDetailByCaseId(this.AllBtWccCustomer.caseid).subscribe(data=>{
  //     this.spinner.hide();
  //     this.caseres=data;
  //     this.caseres.manualId
  //     console.log(this.caseres);
  //     this.casePDRequest.podNumber=this.caseres.podnumber;
  //     this.casePDRequest.caseId=this.AllBtWccCustomer.caseid;
  //     this.caseselectlist.push(this.caseres.caseId);
  //     this.casePDRequest.podDate=this.caseres.PODate;
  //     this.casePDRequest.jmsNo = this.caseres.jmsNo;
  //     this.casePDRequest.customer = this.caseres.customer;
  //     this.casePDRequest.podShippingExpectedDate=this.caseres.podShippingExpectedDate;
  //     this.casePDRequest.podShippingMode=this.caseres.podShippingMode;
  //     this.casePDRequest.podShippingToName=this.caseres.podShippingMode;
  //     this.casePDRequest.podShippingTrackingNumber=this.caseres.podShippingTrackingNumber;
  //     this.createPD.show();
  //   },error=>{
  //     this.spinner.hide();
  //   });
   
  // }
  // invoiceCreate(){
  //   this.isEditMode=true;
  //   this.heading='Create Invoice'
  //   this.createInvoiceModel.show();
  //   this.spinner.show();
  //   this._btcaseservice.getDetailByCaseId(this.wccid).subscribe(data => {
  //   this.spinner.hide();
  //   this.caseres = data;
  //   console.log(this.caseres)
  //   this.caseInvoiceRequest.invoiceNumber=this.caseres.invoiceNumber;
  //   this.caseInvoiceRequest.invoiceDate=this.datePipe.transform(this.caseres.invoiceDate, 'yyyy-MM-dd HH:mm:ss');
  //   this.caseInvoiceRequest.poNumber=this.caseres.ponumber;
  //   this.caseInvoiceRequest.poDate=this.datePipe.transform(this.caseres.podate, 'yyyy-MM-dd HH:mm:ss');
  //   this.caseInvoiceRequest.vendorInvoiceDate=this.caseres.vendorInvoiceDate
  //   this.caseInvoiceRequest.vendorInvoiceNumber=this.caseres.vendorInvoiceNumber;
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.error.message;
  //     this.popupSuccessModel.show();

  //   });

  // }
//   isInvoiceOpen:boolean;
//   cancelInvoiceData(){
//  this.createInvoiceModel.hide();
//  this.isEditMode = false;
//  this.caseInvoiceRequest=new CaseInvoiceRequest();
//   }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  // }
  // onCaseInvoiceCreated($event){
  //   this.caseres=$event;
  //   this.showCaseResponse();
  // }
  // docRecieved(){
  //   this.sureModel.show();
   
  // }
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
  // noRequestFor(){
  //   this.sureModel.hide();

  // }
  // onDOcumnetSuccess($event){
  //   this.showCaseResponse();

  // }
  
  // rejectcasemodelCancel(){
  //   this.rejectcasemodel.hide();
  // }
  // OnSuccess($event){
  //   this.showCaseResponse();

  // }
  // rejectLogs(){
  //   this.RejectLogList=[];
  //   this._btcaseservice.getRejectLogListByCaseId(this.caseres.caseManualId).subscribe(data=>{
  //     this.RejectLogList=data;
  //   });
  // }
  // fileName: any
  // isImageLoading:any;
  // imageToShow:any;
  // opneImage(file: FileUploadResponse) {
  //   this.isImageLoading = true;
  //   // this.choosenFilesShow.show();
  //   this.spinner.show();
  //   this._btcaseservice.filedownload(file.fileName).subscribe(data => {
  //     this.spinner.hide();
  //     this.createImageFromBlob(data);
  //     this.fileName = file.fileName
     
  //     // if (data.type == 'image/jpeg' || data.type == 'image/png') {
  //       this.choosenFilesShow.show();
  //       this.isImageLoading = false;
  //   },error=>{
  //     this.spinner.hide();
  //     this.message=error.message;
  //     this.popupSuccessModel.show()
  //   })
  // }
//   createImageFromBlob(image: Blob) {
//     let reader = new FileReader();
//     reader.addEventListener("load", () => {
//       this.imageToShow = reader.result;
//     }, false);

//     if (image) {
//       reader.readAsDataURL(image);
//     } else {
//       //  this.downloadFile(this.fileName)
//     }
//   }
//   downloadFile(fileName: any) {
//     this.spinner.show();
//     this._btcaseservice.getDownloadFile(fileName).subscribe(data => {
//       fileSaver.saveAs(data, fileName);
//       this.spinner.hide();
//     },
//       err => {
//         this.spinner.hide();
//         this.message = err.error.message;
//         this.popupSuccessModel.show();
//       });
//   }
//   caseResponse:BTCaseResponse=new BTCaseResponse();
//   changeCaseTypeOpen(){
//  this.caseResponse.caseTypeName=this.caseres.caseTypeName;
//  this.caseResponse.caseReasonName=this.caseres.caseReasonName;
//  this.caseResponse.caseId=this.caseres.caseId;
//  this.caseResponse.caseManualId=this.caseres.caseManualId;
//  this.changeCaseTypemodel.show();
//   }
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
getPODFileList(objectPODName: string, objectId: number) {
  this.spinner.show();
  this._serviceAppointmentService.getFileList(objectPODName, objectId).subscribe(data => {
    this.PODfileUploadList = data;
    this.PODfileUploadList.forEach(data => {
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

filePreview(url, fileName) {
  this.fileName = fileName;
  this.imageUrl = url;
  this.spinner.show();
  this._serviceAppointmentService.getDownloadFile(fileName).subscribe(data => {
    this.spinner.hide();
    this.saveFile();
    // this.createImageFromBlob(data);
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
  CloseWccCustomerModel(){
    this.getPODFileList(this.objectPODName, this.AllBtWccCustomer.caseid)
    this.createWccCustomermodel.hide();

  }
//   onChnagedUpdate($event){
//     this.showCaseResponse();
//   }

onCasePDCreated($event){
  this.showWccResponse();
    this.getReasonList();
    this.getApprovalHistory();
    this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);
  
  }
  cancelPDData(){
   
    this.createPD.hide();
  }


  getWccCase()
  {
    this.btwccCustomerService.getAllWccCase(this.AllBtWccCustomer.id).subscribe(data=>{
      this.wccCaseResp=data;
      console.log(data)
    })
  }

  showApprovalWindow(){
    this.approvalModule.show();
  }

  onApprovalDone(){
    this.approvalModule.hide();
    this.showWccResponse();
    this.getReasonList();
    this.getApprovalHistory();
    this.getFileList(this.objectName, this.actionHistoryObjectAndId.objectId);
    this.getPODFileList(this.objectPODName, this.wccid)

    this.getApprovalButtonRsh();
  }


  getApprovalButtonRsh() {

    this.spinner.show()
    this.tobeapproved.getItemToBeApprovdButton( this.actionHistoryObjectAndId.objectName,this.actionHistoryObjectAndId.objectId).subscribe(data => {
      this.spinner.hide();
      if( data.id != null && data != null && data != undefined)
      {
        this.btnshow = true;
        this.wccApprovalId= data.id;
                
      }
      else {
        this.btnshow = false;
        this.wccApprovalId = this.actionHistoryObjectAndId.objectId;
      }
    }, error=>{
  
    });
  
  
  }

  
}


