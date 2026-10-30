import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { FileDeleteService } from '../../../services/filedeletService.services';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, FileUploadResponse } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BtRmaService } from '../../bat_services/bt-rma.service';
import { RmaRejectByIdRequest, RMAResponse } from '../../bt_model';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-bat-rma-detail',
  templateUrl: './bat-rma-detail.component.html',
  styleUrls: ['./bat-rma-detail.component.css']
})
export class BatRmaDetailComponent implements OnInit {



  // @ViewChild('fileUploadModel', { static: false }) fileUploadModel: ModalDirective;
  @ViewChild('choosenFiles',{static:false}) choosenFiles: ModalDirective;
  @ViewChild('rejectcasemodel',{static:false}) rejectcasemodel: ModalDirective;
  @ViewChild('filePreviewModel', { static: false }) filePreviewModel: ModalDirective;

  // @ViewChild('submitForApprovalModal', { static: false }) submitForApprovalModal: ModalDirective;
  // response: MSLRequestResponse = new MSLRequestResponse()
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  remarks: string = '';
  Id: any;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create MSL Entity';
  EDIT_ACCOUNT_HEADING: string = 'Edit MSL Request Entity';
  // req: MSLRequest = new MSLRequest();
  res: RMAResponse = new RMAResponse();
  // responseEntity: Array<MSLRequestResponse>
  message: string = "";
  page: number = 0;
  sortColumnName: string;
  sortOrder: string;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  // mslLineItem: Array<BtMslItemResponse>;
  mslLineItemTotal: any;
  isBulkUpload:boolean=false;
  modalName:string='';
  file: File;

  // localres:LocationResponse= new LocationResponse();
  // products: Array<BTProductForListResponse> = [];
  // _mslItemRequest: BtMSLItemRequestRequest = new BtMSLItemRequestRequest(); 
  // btProductList : Array<BtProductList>=[];
  loginData;
  _cFlag: string;
  isreject:boolean=false;
  objectName = 'BtRMADetails';

  btRmaRejectById: RmaRejectByIdRequest = new RmaRejectByIdRequest();
  fileUploadList: Array<FileUploadResponse> = [];
  formData: FormData = new FormData();
  // file:FormData = new FormData();
  fileName: string;
  imageUrl: string;
  fileList: string[] = [];
  uploadFilesLength: number;
  uploadForm: FormGroup;
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

  constructor(private _serviceAppointmentService: ServiceAppointmentService, private deleteservice: FileDeleteService,private _http: HttpClient, private _activatedRoute: ActivatedRoute,private formBuilder: FormBuilder,private _global:GlobalserviceService, private spinner: NgxSpinnerService, private loginservice: Loginservices,
    private btRmaservice : BtRmaService) {
    this._activatedRoute.params.subscribe(pram => {
      this.Id = pram['id'];
      this.RMAById();
      this.actionHistoryObjectAndId.objectId = '' + this.Id;
      this.actionHistoryObjectAndId.objectName = 'BtRMADetails';
      // this.getLineItems(this.sortColumnName, this.sortOrder);
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  ngOnInit() {
    this.getLoggedInUser();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
     
    });
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    // this.getLineItems(this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();

    // this.getLineItems(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();     
    this.page = this.page + 1;
    // this.getLineItems(this.sortColumnName, this.sortOrder);
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
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.RMAById();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.getLineItems(this.sortColumnName, this.sortOrder);
    }
    return false;
  }
  // createRequestModelOpen(res: MSLRequestResponse) {
  //   this.isEditMode = true;
  //   this.heading = this.EDIT_ACCOUNT_HEADING;
  //   this.spinner.show();
  //   this._btMslRequest.getMslById(res.mslId).subscribe(data => {
  //     this.spinner.hide()
  //     this.res = data
  //   });

  // }
  RMAById() {
    this.spinner.show();
    this.btRmaservice.getRmaById(this.Id).subscribe(data => {
      this.getFileList(this.objectName, this.Id);

      this.spinner.hide();
      console.log(data)
      this.res = data;
      
    }, err => this.spinner.hide());
  }
  // onMSLCreated($event: MSLRequestResponse) {
  //   this.response = $event

  // }

  // getLineItems(column: string, order: string) {
  //   this._btMslRequest.getLineItemsById(this.Id, this.page, column, order).subscribe(data => {
  //     this.mslLineItem = data.content;
  //     this.mslLineItemTotal = data.totalElements;
  //     this.first = data.first;
  //     this.last = data.last;
  //     this.size = data.size;
  //     this.serialNumber = (((this.page) * this.size));
  //   })
  // }

  cancelData() {
  this.res = new RMAResponse()
    //this.createRequestModel.hide();
    this.isEditMode = false;

  }

  rejectcasemodelCancel()
  {
    this.rejectcasemodel.hide();
    this.btRmaRejectById.remarks="";
    this.formData = new FormData();

  (<HTMLInputElement>document.getElementById('getFile')).value = null
  }

  RejectSubmit()
  {
  
    this.btRmaRejectById.id=this.res.id;
    if(this.btRmaRejectById.remarks=="" )
    {
      this.message="Please Fill Remarks";
      this._global.alert({type:'alert',message:this.message});

      // this.popupSuccessModel.show();
    }
    else if(this.file==undefined || this.file==null)
    {
      this.message="Please Fill Upload File";
      this._global.alert({type:'alert',message:this.message});
    }
    else{
      const fd = new FormData;
      fd.append('file',this.file,this.fileName);
      fd.append('data',new Blob([JSON.stringify(this.btRmaRejectById)],{type:'application/json'}));
      if(this.isreject==true)
      {
    this.spinner.show();
    this.btRmaservice.putRmaRejectById(fd).subscribe(data=>{
      this.spinner.hide();
      this.message="Rejected Successfully";
      this.btRmaRejectById = new RmaRejectByIdRequest();
      this._global.alert({type:'',message:this.message});
      this.rejectcasemodel.hide();
      // console.log(data);
      this.RMAById();

    },err=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});

    })
  }
  else
  {
    this.spinner.show()
    this.btRmaservice.changeRmaStatusById(fd).subscribe(data=>{
      this.spinner.hide();
      this.message="Status Updated Successfully";
      this._global.alert({type:'',message:this.message});
      this.rejectcasemodel.hide();
      this.btRmaRejectById = new RmaRejectByIdRequest();

      this.RMAById();

    },err=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});
  
    })
  }
  }

  }



  rejectrmamodelshow(s)
  {
    // if(this.fileUploadList.length==0)
    // {
    if(s=="edit")
    {
      this.modalName='Status';
    this.isreject=false;
    this.rejectcasemodel.show();
    }
    else{
      this.modalName='Reject';
      this.isreject=true;
      this.rejectcasemodel.show();
    }
  // }
  // else{
  //   this._global.alert({type:'alert',message:'Please Upload RMA First'})
  // }
  }
  // searchBtProductList($event){
  //   this._btproductService.fetchProductList($event.term).subscribe(data=>{
  //     const temp = [];
  //     data.forEach(d=>{
  //       let btPrList = new BtProductList();
  //       btPrList.id = d.id;
  //       btPrList.partCode = d.partCode;
  //       btPrList.productType = d.productType;
  //       btPrList.bothvalues = d.partCode + ' ' +d.productType;
  //       temp.push(btPrList);
  //     })
  //     this.btProductList = temp;
  //   })
  // }
  // addItems() {
  //   if (this._mslItemRequest.btProductId == null || this._mslItemRequest.btProductId == undefined) {
  //     this.message = 'Please select part code';
  //     this.popupSuccessModel.show();
  //     return false;
  //   }
  //   if (this._mslItemRequest.hslQty == null || this._mslItemRequest.hslQty == undefined) {
  //     this._mslItemRequest.hslQty = "0";
  //   }
  //   if (this._mslItemRequest.mslQty == null || this._mslItemRequest.mslQty == undefined) {
  //     this._mslItemRequest.mslQty = "0";
  //   }
  //   if (this._mslItemRequest.mslQty > this._mslItemRequest.hslQty) {
  //     this.message = 'MSL can not be greater then HSL';
  //     this.popupSuccessModel.show();
  //     return;
  //   }
  //   this.spinner.show();
  //   this._mslItemRequest.btMslRequestId = this.Id;
  //   this._btMslRequest.addItems(this.Id, this._mslItemRequest).subscribe(data => {
  //     this.spinner.hide();
  //     this.addItemModel.hide();
  //     this.getLineItems(this.sortColumnName, this.sortOrder);
  //     this.MSLById();
  //     this._mslItemRequest = new BtMSLItemRequestRequest();    
  //   }, error => {
  //     this.spinner.hide();
  //     this.message = error.error.message;
  //     this.popupSuccessModel.show();
  //   });
  // }
  // submitForApproval() {
  //   this.spinner.show();
  //   this._btMslRequest.submitForApproval(this.Id, this.remarks).subscribe(arrp => {
  //     this.submitForApprovalModal.hide();
  //     this.spinner.hide();
  //     this.message = "Request Submitted for Approval";
  //     this.popupSuccessModel.show();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //   })
  // }

  // onSubmit() {
  //   this.spinner.show();
  //   for (let i = 0; i < this.fileList.length; i++) {
  //     this.formData.append('files', this.fileList[i]);
  //   }
  //   this._http.post<any>('api/uploadMultipleFiles?objectName=BtRMADetails&objectId=' +
  //     this.res.id, this.formData).subscribe(data => {
  //       // this.searchResult.hide();
  //       this.getFileList('BtRMADetails', this.res.id);
  //       // this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
  //       // this.getApprovalHistoryList();
  //       //   this.getLodgingListByExpenseId(this.expensesmanagement.id);
  //       //   this.getBoardingListByExpenseId(this.expensesmanagement.id);
  //       //   this.getLocalConveyanceListByExpenseId(this.expensesmanagement.id);
  //       //   this.getOtherChargesListByExpenseId(this.expensesmanagement.id);
  //       //   this.getSAListByExpenseId(this.expensesmanagement.id)
  //       //  this.getGLCodes();
  //       this.spinner.hide();
  //       if (this.fileList.length === 0) {
  //         this.message = 'No File uploaded';
  //       } else {
  //         this.message = 'Updated Successfully';
  //         this.fileUploadModel.hide();
  //       }
  //       this._global.alert({type:'',message:this.message});
  //       this.fileList = [];
        
  //   (<HTMLInputElement>document.getElementById('getFile')).value=null
  //     }, error => {
  //       this.fileList = [];
  //       this.spinner.hide();
  //       this.message = error.error.message;
  //       this._global.alert({type:'alert',message:this.message});
  //         this.fileUploadModel.hide();
  //     });
  //   this.formData = new FormData();
  // }

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
      this._global.alert({type:'alert',message:this.message});
    });
  }

      // file upload
      fileToUpload:File;
      onFileSelect($event) {
        this.file = $event.target.files[0];
        this.fileName = this.file.name;
        this.uploadForm.get('profile').setValue(this.file);
    
      }

    //   closeUploadFiles()
    //   {
    //     this.formData = new FormData();
    //   this.fileUploadModel.hide();

    // (<HTMLInputElement>document.getElementById('getFile')).value = null
    //   }


      // DeletAttachment(imageid) {
      //   const objectId = this.Id;
      //   this.spinner.show();
      //   this.deleteservice.deleteAttachment(imageid, this.objectName, objectId).subscribe(data => {
      //     this.spinner.hide();
      //     this.getFileList(this.objectName, this.Id);
      //   }, error => {
      //     this.spinner.hide();
      //     this._global.alert({ type: 'alert', message: error.error.message });
      //   })
      // }
//       imageToShow:any;
// createImageFromBlob(image:Blob)
// {
//   let reader = new FileReader();
//   reader.addEventListener("load", ()=>{
//     this.imageToShow = reader.result;
//   },false)

//   if(image)
//   {
//     reader.readAsDataURL(image);
//     this.filePreviewModel.show();
//   }
//   else
//   {
//     fileSaver.saveAs(this.imageUrl, this.fileName);
//   }
// }
        // show preview file and save file

  

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
}
