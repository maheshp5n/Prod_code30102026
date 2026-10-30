import { QuoteService } from './../../services/Quote.services';
import { QuoteResponse, QuoteRequest, QuoteLineItemRequest, QuoteLineItemResponse, WorkOrderRequest, MailResponse, ActionHistoryObjectAndId } from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import {
  ToBeApprovedHistoryResponse,
  FileUploadResponse
} from '../../services/model';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import * as fileSaver from 'file-saver'; // npm i --save file-saver
import { PdfServiceService } from '../../services/pdf-service.service';
import { AngularEditorConfig } from '@kolkov/angular-editor';


@Component({
  selector: 'app-quotedetails',
  templateUrl: './quotedetails.component.html',
  styleUrls: ['./quotedetails.component.css']
})
export class QuotedetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createNewQuote', { static: false }) createNewQuote: ModalDirective;
  @ViewChild('changeStatus', { static: false }) changeStatus: ModalDirective;
  @ViewChild('createaddProductModel', { static: false }) createaddProductModel: ModalDirective;
  @ViewChild('editQuoteLineItemModel', { static: false }) editQuoteLineItemModel: ModalDirective;
  @ViewChild('confirmationPopup', { static: false }) confirmationPopup: ModalDirective;
  @ViewChild('showPreviewModel', { static: false }) showPreviewModel: ModalDirective;
  @ViewChild('sendMail', { static: false }) sendMail: ModalDirective;
  @ViewChild('searchResult', { static: false }) searchResult: ModalDirective;
  @ViewChild('submitApprovelModel',{static:false}) submitApprovelModel:ModalDirective
  @ViewChild('workOrderForQuoteModel',{static:false}) workOrderForQuoteModel:ModalDirective;
  
  fileList: string[] = [];
  formData = new FormData();
  uploadFilesLength: number;
  flag: boolean;
  ngClassDetails: string = 'active';
  ngClassActivity: string = '';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  vActivity: boolean;
  vReleated: boolean;
  message: string;
  isEditMode: boolean = false;
  heading: string;
  CREATE_Quote_HEADING: string = 'New Quote';
  EDIT_Quote_HEADING: string = 'Edit Quote';
  quoteResponse: QuoteResponse = new QuoteResponse();
  currentIndex: number;
  prevIndex: number;
  quoteId: number;
  quoteRequest: QuoteRequest = new QuoteRequest();
  syncing: boolean;
  approved: boolean;
  isQuoteCreatedOver: boolean;
  headingaddProduct: string = 'Choose Price Book';
  viewAddPriceBook: boolean;
  headingeditPro: string;
  headingaddPro: string;
  isEditAll: boolean;

  firstQuoteLineItems: boolean;
  lastQuoteLineItems: boolean;
  sizeQuoteLineItems: number;
  serialNumberQuoteLineItems: number;
  public pageQuoteLineItems: number = 0;
  totalElementsQuoteLineItems: number;
  sortColumnNameQuoteLineItems: string = 'createdDate'; // first column variable
  sortOrderQuoteLineItems: string = 'desc';
  ASCENDINGQuoteLineItems: string = 'asc';
  DESCENDINGQuoteLineItems: string = 'desc';
  getQuoteLineItemRequestArray: Array<QuoteLineItemResponse> = [];
  isQuote: boolean;
  isChangeStatus: boolean;
  isaddProductOpen: boolean;
  quoteLineItemRequest: QuoteLineItemRequest = new QuoteLineItemRequest();
  iseditProductOpen: boolean;
  businessDomainId: number;
  totalQuote: number;
  _cFlag: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  approvalHistoryList: Array<ToBeApprovedHistoryResponse>=[];
  objectName: string = 'Quote';
  imageUrl: any;
  fileName: string;
  emailList:Array<MailResponse>=[];
  approvalmessage:string='Confirm for Submission';
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  totalDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  workOrderDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  useDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private loginservice: Loginservices,private _expenseManagementService: ExpenseManagementService, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute, private _pdfService: PdfServiceService,
    private _quoteService: QuoteService, private loginService: Loginservices, private _approvalHistoryService: ApprovalHistoryService) {
    this._activatedRoute.params.subscribe(pram => {
      this.quoteId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.quoteId;
      this.actionHistoryObjectAndId.objectName = 'Quote';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }


  ngOnInit() {
    // this.classFlags = ['pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending', 'pending'];
    this.getQuoteDetails();
    this.getLoggedInUser();

  }
  loginData:any;
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.quoteRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
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
  changeTotalDisplay() {
    this.totalDisplay.open = !this.totalDisplay.open;
    if (this.totalDisplay.open) {
      this.totalDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.totalDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changeWorkOrderDisplay() {
    this.workOrderDisplay.open = !this.workOrderDisplay.open;
    if (this.workOrderDisplay.open) {
      this.workOrderDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.workOrderDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  useInfoDisplay() {
    this.useDisplay.open = !this.useDisplay.open;
    if (this.useDisplay.open) {
      this.useDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.useDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.ngClassActivity = '';
      this.vDetails = true;
      this.vReleated = false;
      this.vActivity = false;
      this.getQuoteDetails();
    } else if (tabName === 'releated') {
      this.getEmails();
      this.getApprovalHistoryList();
      this.getFileList('Quote', this.quoteId);
      this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.ngClassActivity = '';
      this.vReleated = true;
      this.vDetails = false;
      this.vActivity = false;
      this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = '';
      this.ngClassActivity = 'active';
      this.vActivity = true;
      this.vDetails = false;
      this.vReleated = false;
    }
    return false;
  }

  // setBreadcrumb(i: number) {
  //   this.prevIndex = this.classFlags.indexOf('current');
  //   this.classFlags[i] = 'current';
  //   this.classFlags[this.prevIndex] = 'currentOld';
  //   this.classFlagindex = i;
  //   this.quoteRequest.quoteStatusId = this.classFlagindex;
  // }

  // changeBreadcrumb() {
  //   this.isQuoteCreatedOver = false;
  //   this.spinner.show();
  //   this._quoteService.putQuote(this.quoteRequest).subscribe(data => {
  //     this._onQuoteCreated(data);
  //     this.popupNgClass = 'alert alert-success center center-block';
  //     if (this.isQuoteCreatedOver) {
  //       this.spinner.hide();
  //     }
  //   }, err => {
  //     this.spinner.hide();
  //     this.popupNgClass = 'alert alert-danger center center-block';
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //   });
  //   this.isQuoteCreatedOver = false;
  // }
caseManualId
oppoId
  onClickEdit() {
    this.spinner.show();
    this._quoteService.getQuoteDetails(this.quoteId).subscribe(data => {
      this.spinner.hide();
      this.loginData = this.loginservice.getLoggedInUser();
      this.quoteResponse.businessDomainId = this.loginData.emptype['businessDomainId'];
      this.quoteRequest.businessDomainId=this.quoteResponse.businessDomainId;
      this.quoteRequest = data;
      this.caseManualId=data.caseNumber;
      this.oppoId = data.oppertunityManualId;
      this.isEditMode = true;
      this.heading = this.EDIT_Quote_HEADING;
      this.createNewQuote.show();
    },error=>{
      this.spinner.hide();
      this.message=error.message;
      this.popupSuccessModel.show();
    });
  }

  cancelData() {
    this.createNewQuote.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Quote_HEADING;
    this.quoteRequest = new QuoteRequest();
  }

  _onQuoteCreated($event: QuoteResponse) {
    this.spinner.show();
    this.quoteResponse = $event;
    // this.classFlagindex = this.quoteResponse.quoteStatusId;
    this.isQuoteCreatedOver = true;
    // let j: any;
    // for (j in this.classFlags) {
    //   if (j <= this.classFlagindex) {
    //     this.classFlags[j] = 'complete';
    //   } else if (j > this.classFlagindex) {
    //     this.classFlags[j] = 'pending';
    //   }
    // }
    this.getEmails();
    this.getQuoteDetails();
    this.getApprovalHistoryList();
    this.getFileList('Quote', this.quoteId);
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

    this.spinner.hide();
  }


  getQuoteDetails() {
    this.spinner.show();
    this._quoteService.getQuoteDetails(this.quoteId).subscribe(data => {
      this.quoteResponse = data;

      this.quoteRequest = this.quoteResponse;
      
      this.syncing = data.syncing;
      this.approved = data.approved;
      this.businessDomainId = data.businessDomainId;

      // this.classFlagindex = this.quoteResponse.quoteStatusId;
      // this.setBreadcrumb(this.quoteResponse.quoteStatusId);
      // let j: any;
      // for (j in this.classFlags) {
      //   if (j <= this.classFlagindex) {
      //     this.classFlags[j] = 'complete';
      //   } else if (j > this.classFlagindex) {
      //     this.classFlags[j] = 'pending';
      //   }
      // }
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.popupNgClass = 'alert alert-danger center center-block';
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  addProduct() {
    this.headingaddPro = this.headingaddProduct;
    this.viewAddPriceBook = true;
    this.createaddProductModel.show();
  }

  openEditAll() {
    this.headingeditPro = 'Edit All Products';
    this.isEditAll = true;
    this.editQuoteLineItemModel.show();
  }


  openEditQuoteLineItem(quoteLineItemId) {
    this.spinner.show();
    this._quoteService.getQuoteLineItem(quoteLineItemId).subscribe(data => {
      this.quoteLineItemRequest.id = data.id;
      this.quoteLineItemRequest.listPrice = data.listPrice;
      // this.quoteLineItemRequest.price = data.salePrice;
      this.quoteLineItemRequest.quantity = data.quantity;
      // this.quoteLineItemRequest.priceBookId = data.priceBookId;
      this.quoteLineItemRequest.priceBookEntryId = data.priceBookEntryId;
      this.quoteLineItemRequest.partCode = data.productPartCode;
      this.quoteLineItemRequest.quoteName = data.quoteName;
      this.quoteLineItemRequest.price = data.price;
      // this.quoteLineItemRequest. = data. 
      // this.quoteLineItemRequest.quoteNumber = data.quoteNumber;
      this.quoteLineItemRequest.totalPrice = data.totalPrice;
      this.quoteLineItemRequest.pdoductDescription = data.pdoductDescription;
      this.spinner.hide();
    });
    this.isEditAll = false;
    this.headingeditPro = 'Edit Product';
    this.editQuoteLineItemModel.show();
  }

  deleteQuoteLineItem(quoteLineItemId) {
    this.spinner.show();
    this._quoteService.deleteQuoteLineItem(quoteLineItemId).subscribe(data => {
      this.message = 'Deleted Successfully !';
      this.spinner.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
    }, err => {
      this.spinner.hide();
      this.message = err.error.status + ' : ' + err.error.message;
      this.popupSuccessModel.show();
    });
    this.getEmails();
    this.getQuoteDetails();
    this.getApprovalHistoryList();
    this.getFileList('Quote', this.quoteId);
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

  }

  setPagePreviousQuoteLineItems(event: any) {
    event.preventDefault();
    this.pageQuoteLineItems = this.pageQuoteLineItems - 1;
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);
  }

  setPageQuoteLineItems(event: any) {
    event.preventDefault();
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);
  }

  setPageNextQuoteLineItems(event: any) {
    event.preventDefault();
    this.pageQuoteLineItems = this.pageQuoteLineItems + 1;
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);
  }

  onaddProductCreated($event) {
    this.getQuoteLineItemRequestArray = $event;
    this.getQuoteDetails();
    this.getApprovalHistoryList();
    this.getFileList('Quote', this.quoteId);
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

  }

  cancelDataaddProduct() {
    this.createaddProductModel.hide();
  }

  headingadd($event) {
    this.headingaddPro = $event;
  }

  getQuoteLineItems(id: number, column: string, order: string) {
    this.spinner.show();
    this._quoteService.getQuoteLineItems(this.quoteId, this.pageQuoteLineItems, column, order).subscribe(data => {
      this.getQuoteLineItemRequestArray = data.content;

      this.firstQuoteLineItems = data.first;
      this.lastQuoteLineItems = data.last;
      this.sizeQuoteLineItems = data.size;
      this.totalQuote = data.totalElements;
      this.serialNumberQuoteLineItems = ((this.pageQuoteLineItems) * this.sizeQuoteLineItems);
      this.spinner.hide();
    });
  }


  cancelDataEditProduct() {
    this.editQuoteLineItemModel.hide();
  }

  sync() {
    this.spinner.show();
    if (this.quoteResponse.syncing) {
      this._quoteService.stopSyncQuoteWithOppertunity(this.quoteId, 2).subscribe(data => {
        this.quoteResponse.syncing = data;
        this._onQuoteCreated(this.quoteResponse);
        
        this.spinner.hide();
        this.message = 'Sync. Stoped Successfully';
        this.popupNgClass = 'alert alert-success center center-block'
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    } else {
      this._quoteService.syncQuoteWithOppertunity(this.quoteId, 2).subscribe(data => {
        this.quoteResponse.syncing = data;
        if(data==true){
          this.getEmails();
          this.getQuoteDetails();
          this.getApprovalHistoryList();
          this.getFileList('Quote', this.quoteId);
          this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);
  
        }
        this._onQuoteCreated(this.quoteResponse);
        this.spinner.hide();
        this.message = 'Sync. Started Successfully';
        this.popupNgClass = 'alert alert-success center center-block'
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.popupNgClass = 'alert alert-danger center center-block';
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
  }

  oneditProductCreated($event) {
    // if (!this.isEditMode) {
    //   this.contactsList.unshift($event);
    // } else {
    let index = 0;
    this.getQuoteLineItemRequestArray.forEach(element => {
      if (element.id === $event.id) {
        this.getQuoteLineItemRequestArray[index] = $event;
      } else {
        index++;
      }
    });
    // }
    this.getEmails();
    this.getQuoteDetails();
    this.getApprovalHistoryList();
    this.getFileList('Quote', this.quoteId);
    this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

  }

  oneditAllProductCreated($event) {
    this.getQuoteLineItemRequestArray = $event;
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.createaddProductModel.hide();
      this.editQuoteLineItemModel.hide();
    
    }
  }

  getApprovalHistoryList() {
    this.spinner.show();
    this._approvalHistoryService.getApprovalStatus(this.objectName, this.quoteId).subscribe(data => {
      this.approvalHistoryList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }

  createPDF() {
    this.spinner.show();
    this._pdfService.createPDF('api/quote', 'quoteId', this.quoteId).subscribe(data => {
      let file = new Blob([data], { type: 'application/pdf' });
      var fileURL = URL.createObjectURL(file);
      // fileURL = fileURL +'/'+ this.quoteResponse.quoteNumber;
      window.open(fileURL);
      // this.getFileList('Quote', this.quoteId);
      this.spinner.hide();
      this.message = 'Created successfully';
      this.confirmationPopup.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
      this.getEmails();
      this.getQuoteDetails();
      this.getApprovalHistoryList();
      this.getFileList('Quote', this.quoteId);
      this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

    }, error => {
      this.spinner.hide();
      this.confirmationPopup.hide();
      this.message = error.message;
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    })
  }
  fileUploadList: Array<FileUploadResponse> = [];

  getFileList(objectName: string, objectId: number) {
    this.spinner.show();
    this._expenseManagementService.getFileList(objectName, objectId).subscribe(data => {
      this.fileUploadList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  // showPreview() {
  //   let file = new Blob([data], { type: 'application/pdf' });
  //   var fileURL = URL.createObjectURL(file);
  //   window.open(fileURL);
  // }

  // ecbIframe_id: HTMLIFrameElement;


  showPreview(url, fileName) {
    this.fileName = fileName;
    this.spinner.show();
    this.imageUrl = url;
    this._expenseManagementService.downloadFile(fileName).subscribe(data => {
      this.showPreviewModel.show();
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = 'Error while showing preview';
      this.popupSuccessModel.show();
    });
  }

  saveFile() {
    fileSaver.saveAs(this.imageUrl, this.fileName);
    this.spinner.hide();
  }

  onFileSelect(event) {
    this.fileList = [];
    if (event.target.files.length > 0) {
      this.uploadFilesLength = event.target.files.length;
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
      }
      // this.searchResult.show();
    }
  }
  closeUploadFiles() {
    this.searchResult.hide();
    this.formData = new FormData();
  }
  onSubmit() {
    // this.spinner.show();
    // for (let i = 0; i < this.fileList.length; i++) {
    //   this.formData.append('files', this.fileList[i]);
    // }
    // this._http.post<any>('api/uploadMultipleFiles?objectName=ExpenseManagement&objectId=' +
    //   this.expensesmanagement.expenseManagementId, this.formData).subscribe(data => {
    //     this.searchResult.hide();
    //     this.getFileList('ExpenseManagement', this.expensesmanagement.expenseManagementId);
    //     // this.fileUploadList.push(this.fileData);
    //     this.spinner.hide();
    //     this.popupNgClass = 'alert alert-success center center-block';
    //     if (this.fileList.length === 0) {
    //       this.message = 'No File uploaded';
    //     } else {
    //       this.message = 'Files have been uploaded successfully';
    //     }
    //     this.popupSuccessModel.show();
    //     this.fileList = [];
    //   }, error => {
    //     this.fileList = [];
    //     this.spinner.hide();
    //     this.popupNgClass = 'alert alert-danger center center-block';
    //     this.message = error.error.message;
    //     this.popupSuccessModel.show();
    //   });
    // this.formData = new FormData();
  }

  removeFile(fileName) {
    if (this.fileList.length > 0) {
      for (const value of this.fileList) {
        if (value['name'] === fileName) {
          this.fileList.splice(this.fileList.indexOf(value), 1);
        }
      }
      // this.searchResult.show();
    }
  }


  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    };
  yesQuote:boolean;
    onClickSSumitApproval(){
   this.yesQuote=true;
      this.submitApprovelModel.show();
    }
    onSubmitted($event){
      this.getEmails();
      this.getQuoteDetails();
      this.getApprovalHistoryList();
      this.getFileList('Quote', this.quoteId);
      this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

    }
    noRequestFor(){
      this.submitApprovelModel.hide();
    }
    yesRequestForSA(){
      this.spinner.show();
      console.log(this.quoteId)
      this._quoteService.postsubmitforapproval(this.quoteId).subscribe(data=>{
        this.getEmails();
        this.getQuoteDetails();
        this.getApprovalHistoryList();
        this.getFileList('Quote', this.quoteId);
        this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

        this.spinner.hide();
        this.submitApprovelModel.hide();
        this.message='Submit successfully';
        this.popupNgClass='alert alert-success center center-block';
        this.popupSuccessModel.show();
        
      },error=>{
        this.popupNgClass='alert alert-danger center center-block';
        this.spinner.hide();
        this.message=error.error.message;
        this.popupSuccessModel.show();

      })
    }
    noRequestForSA(){
      this.submitApprovelModel.hide();
    }
    
    workOrderRequest:WorkOrderRequest=new WorkOrderRequest()
    QuteId:number;
    addWorkOrder(){
      // this.quotePdfHeaderAndFooterResponse.id=this.workOrderRequest.quoteId;
      this.isEditMode=true;
      this.heading='Add Work Order';
     this.QuteId=this.quoteId;
      this.workOrderForQuoteModel.show();
    }
    workOrdercancelData(){
      this.workOrderForQuoteModel.hide();
     this.workOrderRequest=new WorkOrderRequest();
    }
    onworkOrderCreated($event:QuoteResponse){
      this.quoteResponse=$event;
      // this.quoteTemplate=$event;
      this.getEmails();
      this.getQuoteDetails();
      this.getApprovalHistoryList();
      this.getFileList('Quote', this.quoteId);
      this.getQuoteLineItems(this.quoteId, this.sortColumnNameQuoteLineItems, this.sortOrderQuoteLineItems);

    }
    emailFileName:any;
    path:any
    
    getEmails(){
      this.spinner.show();
      this._quoteService.getMailList(this.quoteId).subscribe(data=>{
        this.emailList=data;
      data.forEach(e=>{
        this.path= e.attachemt;
      })
      const regexPath = /^(.*[\\\/])(.*)$/;
      const match = regexPath.exec(this.path);
      if (match !== null) {  const filePath = match[1];
          const fileName = match[2];
          this.emailFileName=fileName;
      }
        this.spinner.hide()
      })
    }
 
}
