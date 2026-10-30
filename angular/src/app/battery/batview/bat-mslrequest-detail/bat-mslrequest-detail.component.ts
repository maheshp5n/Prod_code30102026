import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import {MSLItemRequestResponse, MSLRequest, MSLRequestResponse} from '../../../services/model';
import { BtproductService } from '../../bat_services/btproduct.service';
import { ProductsService } from '../../../services/products.services';
import { BtMslrequestService } from '../../bat_services/bt-mslrequest.service';
import {BtMSLItemRequestRequest, BtMslItemResponse, BTProductForListResponse, BtProductList} from '../../bt_model';

@Component({
  selector: 'app-bat-mslrequest-detail',
  templateUrl: './bat-mslrequest-detail.component.html',
  styleUrls: ['./bat-mslrequest-detail.component.scss']
})
export class BatMslrequestDetailComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('addItemModel', { static: false }) addItemModel: ModalDirective;
  @ViewChild('submitForApprovalModal', { static: false }) submitForApprovalModal: ModalDirective;
  response: MSLRequestResponse = new MSLRequestResponse()
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  remarks: string = '';
  Id: any;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create MSL Entity';
  EDIT_ACCOUNT_HEADING: string = 'Edit MSL Request Entity'
  req: MSLRequest = new MSLRequest();
  res: MSLRequestResponse = new MSLRequestResponse();
  responseEntity: Array<MSLRequestResponse>
  message: string = "";
  page: number = 0;
  sortColumnName: string;
  sortOrder: string;
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  mslLineItem: Array<BtMslItemResponse>;
  mslLineItemTotal: any;
  // localres:LocationResponse= new LocationResponse();
  products: Array<BTProductForListResponse> = [];
  _mslItemRequest: BtMSLItemRequestRequest = new BtMSLItemRequestRequest(); 
  btProductList : Array<BtProductList>=[];
  loginData;
  _cFlag: string;

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

  constructor(private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService, private loginservice: Loginservices, private _productService: ProductsService,
    private _btMslRequest : BtMslrequestService,private _btproductService: BtproductService) {
    this._activatedRoute.params.subscribe(pram => {
      this.Id = pram['mslId'];
      this.MSLById();
      this.getLineItems(this.sortColumnName, this.sortOrder);
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  ngOnInit() {
    this.getLoggedInUser();
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getLineItems(this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();

    this.getLineItems(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();     
    this.page = this.page + 1;
    this.getLineItems(this.sortColumnName, this.sortOrder);
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
      this.MSLById();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getLineItems(this.sortColumnName, this.sortOrder);
    }
    return false;
  }
  createRequestModelOpen(res: MSLRequestResponse) {
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this._btMslRequest.getMslById(res.mslId).subscribe(data => {
      this.spinner.hide()
      this.res = data
    });

  }
  MSLById() {
    this.spinner.show();
    this._btMslRequest.getMslById(this.Id).subscribe(data => {
      this.spinner.hide();
      this.response = data
    }, err => this.spinner.hide());
  }
  onMSLCreated($event: MSLRequestResponse) {
    this.response = $event

  }

  getLineItems(column: string, order: string) {
    this._btMslRequest.getLineItemsById(this.Id, this.page, column, order).subscribe(data => {
      this.mslLineItem = data.content;
      this.mslLineItemTotal = data.totalElements;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    })
  }

  cancelData() {
  this.res = new MSLRequestResponse()
    //this.createRequestModel.hide();
    this.isEditMode = false;

  }
  searchBtProductList($event){
    this._btproductService.fetchProductList($event.term).subscribe(data=>{
      const temp = [];
      data.forEach(d=>{
        let btPrList = new BtProductList();
        btPrList.id = d.id;
        btPrList.partCode = d.partCode;
        btPrList.productType = d.productType;
        btPrList.bothvalues = d.partCode + ' ' +d.productType;
        temp.push(btPrList);
      })
      this.btProductList = temp;
    })
  }
  addItems() {
    if (this._mslItemRequest.btProductId == null || this._mslItemRequest.btProductId == undefined) {
      this.message = 'Please select part code';
      this.popupSuccessModel.show();
      return false;
    }
    if (this._mslItemRequest.hslQty == null || this._mslItemRequest.hslQty == undefined) {
      this._mslItemRequest.hslQty = "0";
    }
    if (this._mslItemRequest.mslQty == null || this._mslItemRequest.mslQty == undefined) {
      this._mslItemRequest.mslQty = "0";
    }
    if (this._mslItemRequest.mslQty > this._mslItemRequest.hslQty) {
      this.message = 'MSL Can Not Be Greater Then HSL';
      this.popupSuccessModel.show();
      return;
    }
    this.spinner.show();
    this._mslItemRequest.btMslRequestId = this.Id;
    this._btMslRequest.addItems(this.Id, this._mslItemRequest).subscribe(data => {
      this.spinner.hide();
      this.addItemModel.hide();
      this.getLineItems(this.sortColumnName, this.sortOrder);
      this.MSLById();
      this._mslItemRequest = new BtMSLItemRequestRequest();    
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
  }
  submitForApproval() {
    this.spinner.show();
    this._btMslRequest.submitForApproval(this.Id, this.remarks).subscribe(arrp => {
      this.submitForApprovalModal.hide();
      this.spinner.hide();
      this.message = "Request Submitted for Approval";
      this.popupSuccessModel.show();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    })
  }
}
