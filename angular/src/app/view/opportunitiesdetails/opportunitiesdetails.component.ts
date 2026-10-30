import { QuoteService } from './../../services/Quote.services';
import {
  OppertunityRequest, QuoteRequest, OppertunityResponse, OppertunityStage, Product, ProductsResponse,
  OppertunityLineItemRequest, OppertunityLineItemResponse, QuoteResponse, ActionHistoryObjectAndId
} from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { OpportunityService } from '../../services/Opportunity.services';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';

@Component({
  selector: 'app-Opportunitiesdetails',
  templateUrl: './opportunitiesdetails.component.html',
  styleUrls: ['./opportunitiesdetails.component.css']
})
export class OpportunitiesdetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createNewOpportunity', { static: false }) createNewOpportunity: ModalDirective;
  // @ViewChild('changeStatus', { static: false }) changeStatus: ModalDirective;
  @ViewChild('OpportunityProductModel', { static: false }) OpportunityProductModel: ModalDirective;
  @ViewChild('createaddProductModel', { static: false }) createaddProductModel: ModalDirective;
  @ViewChild('editOppoLineItemModel', { static: false }) editOppoLineItemModel: ModalDirective;
  @ViewChild('quoteApprovedModel', { static: false }) quoteApprovedModel: ModalDirective;
  @ViewChild('createNewQuote', { static: false }) createNewQuote: ModalDirective;

  isOpportunityOpen: boolean;
  // proposedClass = '';
  oppertunityId: number;
  // closedClass = 'active';
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
  CREATE_Opportunity_HEADING: string = 'New Opportunity';
  EDIT_Opportunity_HEADING: string = 'Edit Opportunity';
  newOpportunity: OppertunityRequest = new OppertunityRequest();
  stageForList: Array<OppertunityStage> = [];
  temp: Array<OppertunityStage> = [];
  oppertunityResponse: OppertunityResponse = new OppertunityResponse();
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  isaddProductOpen: boolean;
  iseditProductOpen: boolean;
  // Add products
  viewAddPriceBook: boolean;
  totalOppoLineItems: number;
  totalQuote: number;

  CREATE_ProductExpenses_HEADING: string = 'New Product';
  EDIT_ProductExpenses_HEADING: string = 'Edit Product';
  headingProductExpenses: string;
  isEditModeProductExpense: boolean = false;
  firstProduct: boolean;
  lastProduct: boolean;
  sizeProduct: number;
  serialNumberProduct: number;
  public pageProduct: number = 0;
  totalElementsProduct: number;
  productExpenseRequest: Product = new Product();
  productExpenseResponse: ProductsResponse = new ProductsResponse();
  productListByExpenseId: Array<ProductsResponse> = [];


  CREATE_QuoteExpenses_HEADING: string = 'New Quote Expense';
  EDIT_QuoteExpenses_HEADING: string = 'Edit Quote Expense';
  headingQuoteExpenses: string;
  isEditModeQuote: boolean;
  firstQuote: boolean;
  lastQuote: boolean;
  sizeQuote: number;
  serialNumberQuote: number;
  public pageQuote: number = 0;
  totalElementsQuote: number;
  headingaddPro: string;
  headingaddProduct: string = 'Choose Price Book';
  getOppertunityLineItemRequestArray: Array<OppertunityLineItemResponse> = [];
  quoteRequest: QuoteRequest = new QuoteRequest();
  quoteList: Array<QuoteResponse> = [];
  iseditQuoteOpen: boolean;
  isEditAll: boolean;
  headingQuote: string;
  CREATE_Quote_HEADING: string = 'New Quote';
  EDIT_Quote_HEADING: string = 'Edit Quote';

  firstOppoLineItems: boolean;
  lastOppoLineItems: boolean;
  sizeOppoLineItems: number;
  serialNumberOppoLineItems: number;
  public pageOppoLineItems: number = 0;
  totalElementsOppoLineItems: number;
  sortColumnNameOppoLineItems: string = 'createdDate'; // first column variable
  sortOrderOppoLineItems: string = 'desc';
  ASCENDINGOppoLineItems: string = 'asc';
  DESCENDINGOppoLineItems: string = 'desc';
  isQuote: boolean;
  headingeditPro: string;
  _cFlag: string;
  oppertunityLineItemRequest: OppertunityLineItemRequest = new OppertunityLineItemRequest();

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private opportunityService: OpportunityService,private accontservice:AccountService,
    private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _opportunityService: OpportunityService
    , private _quoteService: QuoteService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.oppertunityId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.oppertunityId;
      this.actionHistoryObjectAndId.objectName = 'Oppertunity';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    // this.getOppertunityStage();
    this.oppertunityResponse = new OppertunityResponse();
    this.getOpportunityDetails();
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
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

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.ngClassActivity = '';
      this.vDetails = true;
      this.vReleated = false;
      this.vActivity = false;
      this.getOpportunityDetails();
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.ngClassActivity = '';
      this.vReleated = true;
      this.vDetails = false;
      this.vActivity = false;
      this.sortColumnNameOppoLineItems = 'createdDate';
      this.sortOrderOppoLineItems = 'asc';
      this.sortColumnNameOppoLineItems = 'createdDate';
      this.sortOrderOppoLineItems = 'asc';

      this.getOppoLineItems(this.oppertunityId);
      this.getQuoteByOppoId(this.oppertunityId);
    }
    // else {
    //   this.ngClassDetails = '';
    //   this.ngClassReleated = '';
    //   this.ngClassActivity = 'active';
    //   this.vActivity = true;
    //   this.vDetails = false;
    //   this.vReleated = false;
    // }
    return false;
  }

  onClickEdit(oppo) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_Opportunity_HEADING;
    this.newOpportunity.oppertunityId = oppo.oppertunityId;
    this.opportunityService.getOpportunityDetails(oppo.oppertunityId).subscribe(data => {
      this.newOpportunity.oppertunityNumber = data.oppertunityNumber;
      this.newOpportunity.caseId = data.caseId;
      this.newOpportunity.caseManualId = data.caseNumber;
      this.newOpportunity.accountId = data.accountId;
      this.newOpportunity.accountName = data.accountName;
      this.newOpportunity.oppertunityStageId = data.stageId;
      this.newOpportunity.oppertunityTypeId = data.typeId;
      this.newOpportunity.forcastCategoryId = data.forcastCategoryId;
      this.newOpportunity.accountId = data.accountId;
      this.newOpportunity.probability = data.probability;
      this.newOpportunity.ammount = data.ammount;
      this.newOpportunity.closeDate = data.closeDate;
      this.newOpportunity.expectedRevenue = data.expectedRevenue;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.createNewOpportunity.show();
  }

  onClickDelete(opportunityResponse) {
    this.spinner.show();
    this.opportunityService.deleteOpportunity(opportunityResponse.oppertunityId).subscribe(data => {
      this.message = 'Deleted Successfully !';
      this.spinner.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
      this.getOpportunityDetails();
      this.getOppoLineItems(this.oppertunityId);
      this.getQuoteByOppoId(this.oppertunityId);
    }, err => {
      this.message = err.error.status + ' : ' + err.error.message;
    });
  }

  deleteOpportunityLineItem(oppoLineItemId) {
    this.spinner.show();
    this.opportunityService.deleteOpportunityLineItem(oppoLineItemId).subscribe(data => {
      this.message = 'Deleted Successfully !';
      this.spinner.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
    }, err => {
      this.message = err.error.status + ' : ' + err.error.message;
    });
  }

  cancelData() {
    this.createNewOpportunity.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
  }

  getOpportunityDetails() {
    this.spinner.show();
    this._opportunityService.getOpportunityDetails(this.oppertunityId).subscribe(data => {
      this.oppertunityResponse = data;
      if (this.oppertunityResponse.oppertunityNumber == null || this.oppertunityResponse.oppertunityNumber === 0) {
        this.spinner.hide();
      }
      // if (this.oppertunityResponse.stageId === 1) {
      //   this.proposedClass = 'active';
      //   this.closedClass = '';
      // } else {
      //   this.closedClass = 'active';
      //   this.proposedClass = '';
      // }
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' : ' + err.error.message;
      this.popupSuccessModel.show();
    });
  }

  stageSelected() {
  }

  stageEdit() {
    this.spinner.show();
    this.newOpportunity.oppertunityId = this.oppertunityResponse.oppertunityId;
    this.newOpportunity.oppertunityNumber = this.oppertunityResponse.oppertunityNumber;
    this.newOpportunity.caseId = this.oppertunityResponse.caseId;
    this.newOpportunity.accountName = this.oppertunityResponse.accountName;
    this.newOpportunity.oppertunityTypeId = this.oppertunityResponse.typeId;
    this.newOpportunity.forcastCategoryId = this.oppertunityResponse.forcastCategoryId;
    this.newOpportunity.accountId = this.oppertunityResponse.accountId;
    this.newOpportunity.probability = this.oppertunityResponse.probability;
    this.newOpportunity.ammount = this.oppertunityResponse.ammount;
    this.newOpportunity.closeDate = this.oppertunityResponse.closeDate;
    this.newOpportunity.expectedRevenue = this.oppertunityResponse.expectedRevenue;

    this.opportunityService.putOpportunity(this.newOpportunity).subscribe(data => {
      this.oppertunityResponse = data;
      this.message = 'Updated Successfully';
      this.spinner.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    }
    );
    // if (this.newOpportunity.oppertunityStageId === 1) {
    //   this.proposedClass = 'active';
    //   this.closedClass = '';
    // } else {
    //   this.closedClass = 'active';
    //   this.proposedClass = '';
    // }
  }


  onOpportunityCreated($event: OppertunityResponse) {
    this.oppertunityResponse = $event;
    this.getOpportunityDetails();
  }

  createProduct() {

  }

  postBoarding() {

  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      // this.changeStatus.hide();
      this.quoteRequest = new QuoteRequest();

    }
  }

  // Product Starts

  setPagePreviousProduct(event: any) {
    event.preventDefault();
    this.pageProduct = this.pageProduct - 1;
    this.getProductListByOpportunity();
  }

  setPageProduct(event: any) {
    event.preventDefault();
    this.getProductListByOpportunity();
  }

  setPageNextProduct(event: any) {
    event.preventDefault();
    this.pageProduct = this.pageProduct + 1;
    this.getProductListByOpportunity();
  }

  getProductListByOpportunity() {
  }

  showProductExpenses() {
    this.isEditModeProductExpense = false;
    this.headingProductExpenses = this.CREATE_ProductExpenses_HEADING;
    this.productExpenseRequest = new Product();
    // this.createProductModel.show();
  }

  cancelProductExpense() {
    // this.createProductModel.hide();
    this.isEditModeProductExpense = false;
    this.headingProductExpenses = this.CREATE_ProductExpenses_HEADING;
    this.productExpenseRequest = new Product();
  }

  // Quote Starts

  setPagePreviousQuote(event: any) {
    event.preventDefault();
    this.pageQuote = this.pageQuote - 1;
    this.getQuoteByOppoId(this.oppertunityId);
  }

  setPageQuote(event: any) {
    event.preventDefault();
    this.getQuoteByOppoId(this.oppertunityId);
  }

  setPageNextQuote(event: any) {
    event.preventDefault();
    this.pageQuote = this.pageQuote + 1;
    this.getQuoteByOppoId(this.oppertunityId);
  }

  // OppoLineItems Starts

  setPagePreviousOppoLineItems(event: any) {
    event.preventDefault();
    this.pageOppoLineItems = this.pageOppoLineItems - 1;
    this.getOppoLineItems(this.oppertunityId);
  }

  setPageOppoLineItems(event: any) {
    event.preventDefault();
    this.getOppoLineItems(this.oppertunityId);
  }

  setPageNextOppoLineItems(event: any) {
    event.preventDefault();
    this.pageOppoLineItems = this.pageOppoLineItems + 1;
    this.getOppoLineItems(this.oppertunityId);
  }

  addProduct() {
    this.headingaddPro = this.headingaddProduct;
    this.viewAddPriceBook = true;
    this.createaddProductModel.show();
  }

  getOppoLineItems(id: number) {
    this.spinner.show();
    this._opportunityService.getOppoLineItems(this.oppertunityId, this.pageOppoLineItems).subscribe(data => {
      this.getOppertunityLineItemRequestArray = data.content;
      this.firstOppoLineItems = data.first;
      this.lastOppoLineItems = data.last;
      this.sizeOppoLineItems = data.size;
      this.totalOppoLineItems = data.totalElements;
      this.serialNumberOppoLineItems = ((this.pageOppoLineItems) * this.sizeOppoLineItems);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  // , column: string, order: string
  getQuoteByOppoId(id: number) {
    this.spinner.show();
    // , column, order
    this._opportunityService.getQuoteByOppoId(this.oppertunityId, this.pageQuote).subscribe(data => {
      this.quoteList = data.content;
      this.firstQuote = data.first;
      this.lastQuote = data.last;
      this.sizeQuote = data.size;
      this.totalQuote = data.totalElements;
      this.serialNumberQuote = ((this.pageQuote) * this.sizeQuote);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }


  openEditAll() {
    this.headingeditPro = 'Edit All Products';
    this.isEditAll = true;
    this.editOppoLineItemModel.show();
  }


  onaddProductCreated($event) {
    // this.getOppertunityLineItemRequestArray = $event;
    this.getOppoLineItems(this.oppertunityId);
  }

  cancelDataaddProduct() {
    this.createaddProductModel.hide();
  }

  headingadd($event) {
    this.headingaddPro = $event;
  }

  oneditProductCreated($event) {
    let index = 0;
    this.getOppertunityLineItemRequestArray.forEach(element => {
      if (element.id === $event.id) {
        this.getOppertunityLineItemRequestArray[index] = $event;
      } else {
        index++;
      }
    });
    this.isEditAll = false;
    this.getOppoLineItems(this.oppertunityId);
  }


  oneditAllProductCreated($event) {
    // this.getOppertunityLineItemRequestArray=$event;
    this.getOppoLineItems(this.oppertunityId);
    // let index = 0;
    // this.getOppertunityLineItemRequestArray.forEach(element => {
    //   if (element.id === $event.id) {
    //     this.getOppertunityLineItemRequestArray[index] = $event;
    //   } else {
    //     index++;
    //   }
    // });
  }

  cancelDataEditProduct() {
    this.editOppoLineItemModel.hide();
  }

  oneditAllQuoteCreated($event) {
    this.quoteList = $event;
  }

  headingedit($event) {

  }

  openEditOppoLineItem(opportunityLineItemId) {
    this.spinner.show();
    this.opportunityService.getOppoLineItem(opportunityLineItemId).subscribe(data => {
      this.oppertunityLineItemRequest.id = data.id;
      this.oppertunityLineItemRequest.listPrice = data.listPrice;
      this.oppertunityLineItemRequest.price = data.salePrice;
      this.oppertunityLineItemRequest.quantity = data.quantity;
      this.oppertunityLineItemRequest.priceBookId = data.priceBookId;
      this.oppertunityLineItemRequest.priceBookEntryId = data.priceBookEntryId;
      this.oppertunityLineItemRequest.partCode = data.productPartCode;
      this.oppertunityLineItemRequest.oppertunityNumber = data.oppertunityNumber;
      this.oppertunityLineItemRequest.totalPrice = data.totalPrice;
      this.oppertunityLineItemRequest.pdoductDescription = data.pdoductDescription;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditAll = false;
    this.headingeditPro = 'Edit Opportunity Product';
    this.editOppoLineItemModel.show();
  }

  // cancelQuoteApproved() {
  //   this.quoteApprovedModel.hide();
  // }

  // showQuote() {
  // this.quoteApprovedModel.show();
  //   this.isEditModeQuote = false;
  // }
  oppoId: number;
  caseManualId:string;
  BillingcountryId:string=null;
  newQuote(accountId) {
    
    this.headingQuote = this.CREATE_Quote_HEADING;
    this.isEditModeQuote = false;
    // this.quoteRequest.oppertunityManualId = this.oppertunityResponse.oppertunityNumber;
    this.quoteRequest.oppertunityId = this.oppertunityResponse.oppertunityId;
    this.quoteRequest.accountName = this.oppertunityResponse.accountName;
    this.caseManualId=this.oppertunityResponse.caseNumber;
    this.oppoId = this.oppertunityResponse.oppertunityNumber;
    this.spinner.show();
   this.accontservice.getAddressAccountId(accountId).subscribe(data=>{
     this.spinner.hide();
    //  this.quoteRequest=null;
      this.quoteRequest.billingName=data.accountName;
      this.quoteRequest.billingCity=data.billingCity;
      this.quoteRequest.billingStreet=data.billingSteateName;
      // this.quoteRequest.billingCountryId=data.billingCountryId;
      this.quoteRequest.billingStateId=data.billingStateId;
      this.quoteRequest.billingPostalCode=data.billingPincode;
      this.quoteRequest=this.quoteRequest;
      this.BillingcountryId=data.billingCountryId;
   },err=>{
     this.spinner.hide();
     this.message=err.error.message;
     this.popupSuccessModel.show();
   })
    this.createNewQuote.show();
  }

  // saveApproval(i) {
  //   this.quoteRequest = new QuoteRequest();
  //   this.quoteRequest.isApproved = i;
  //   this.quoteRequest.oppertunityManualId = this.oppertunityResponse.oppertunityNumber;
  //   this.quoteRequest.oppertunityId = this.oppertunityResponse.oppertunityId;
  //   this.quoteRequest.accountName = this.oppertunityResponse.accountName;
  // }

  oneditQuoteCreated($event) {
  }

  cancelQuote() {
    // this.quoteApprovedModel.hide();
    this.createNewQuote.hide();
    this.isEditModeQuote = false;
    this.headingQuote = this.CREATE_Quote_HEADING;
    this.quoteRequest = new QuoteRequest();
  }

  _onQuoteCreated($event: QuoteResponse) {
    if (!this.isEditModeQuote) {
      this.quoteList.unshift($event);
    } else {
      let index = 0;
      this.quoteList.forEach(element => {
        if (element.id === $event.id) {
          this.quoteList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  onClickEditQuote(quote: QuoteResponse) {
    this.spinner.show();
    this._quoteService.getQuoteDetails(quote.id).subscribe(data => {
      this.quoteRequest = data;
      this.isEditModeQuote = true;
      this.caseManualId=this.oppertunityResponse.caseNumber;
      this.oppoId = this.oppertunityResponse.oppertunityNumber;
      this.headingQuote = this.EDIT_Quote_HEADING;
      this.createNewQuote.show();
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  deleteQuoteLineItem(quoteId) {
    this.spinner.show();
    this._opportunityService.deleteQuote(quoteId).subscribe(data => {
      this.message = 'Deleted Successfully !';
      this.spinner.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
    }, err => {
      this.spinner.hide();
      this.message = err.error.status + ' : ' + err.error.message;
      this.popupSuccessModel.show();
    });
  }

}
