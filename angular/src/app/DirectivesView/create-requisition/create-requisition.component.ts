import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import { AssetsResponse, AssetsRequest, RequisitionStatus, RequisitionProductType, RequisitionApprovalStatus, ProductsForList, RequisitionResponse, RequisitionRequest, Product, RequisitionProductDetailsResponse, RequisitionProductDetailsRequest, WarehouseForList, WarehousedetailsResponse, WarehousedetailsRequest } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { RequisitionService } from '../../services/requisition.services';
import { AssetsService } from '../../services/assets.services';

@Component({
  selector: 'app-create-requisition',
  templateUrl: './create-requisition.component.html',
  styleUrls: ['./create-requisition.component.scss']
})
export class CreateRequisitionComponent implements OnInit {

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('addProductListModel') addProductListModel: ModalDirective;
  @ViewChild('createProductModel') createProductModel: ModalDirective;
  @ViewChild('createWarehouseModel') createWarehouseModel: ModalDirective;


  _isEditMode: boolean
  tempMannulId: any;
  requisitionResponse: RequisitionResponse = new RequisitionResponse();
  requisitionRequest: RequisitionRequest = new RequisitionRequest();

  productListResponse: RequisitionProductDetailsResponse = new RequisitionProductDetailsResponse();

  productList: Array<RequisitionProductDetailsResponse> = [];

  message: string;
  istrue: boolean;
  loginData;

  status: Array<RequisitionStatus> = [];
  productType: Array<RequisitionProductType> = [];
  approvalStatus: Array<RequisitionApprovalStatus> = [];

  _productList: Array<ProductsForList> = [];
  productsForList: ProductsForList = new ProductsForList();

  newwarehouse: WarehousedetailsRequest = new WarehousedetailsRequest();
  warehouseList: Array<WarehouseForList> = [];
  warehouseForList: WarehouseForList = new WarehouseForList();

  isProductSelected: string;
  isFromWarehouseSelected: string;
  isToWarehouseSelected: string;

  noMatchProduct: string;
  noMatchFromWarehouse: string;
  noMatchToWarehouse: string

  faPlus: string = 'btn btn-primary fa fa-plus';
  createProduct: Product = new Product();

  tempId: number;
  tempManual: string;
  popupNgClass: string = 'alert alert-danger center center-block';

  isProductModelShow: boolean;
  isWarehouseModelShow: boolean;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAccountData(requisitionResponse: RequisitionResponse) {
    this.requisitionResponse = requisitionResponse;
  }

  get editAccountData(): RequisitionResponse {
    return this.requisitionResponse;
  }

  @Output()
  onRequisitionCreated: EventEmitter<RequisitionRequest> = new EventEmitter<RequisitionRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices,
    private requisitionService: RequisitionService, private _assetService: AssetsService) { }

  ngOnInit() {
    this.ownerName();
    this.getstatus();
    this.getProductType();
    this.getApprovalStatus();
    this.getProductDetails();
    this.getWarehouseList();
    this.isProductSelected = 'fa fa-search'
    this.isFromWarehouseSelected = 'fa fa-search'
    this.isToWarehouseSelected = 'fa fa-search'
    this.faPlus;
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser()
  }

  getstatus() {
    this.requisitionService.getStatus().subscribe(data => {
      this.status = data;
    })
  }
  getProductType() {
    this.requisitionService.getProductType().subscribe(data => {
      this.productType = data;
    })
  }
  getApprovalStatus() {
    this.requisitionService.getApprovalStatus().subscribe(data => {
      this.approvalStatus = data;
    })
  }
  getProductDetails() {
    this._assetService.getProductForList().subscribe(data => {
      this._productList = data;
    })
  }
  getWarehouseList() {
    this.requisitionService.getWarehouseForList().subscribe(data => {
      this.warehouseList = data;
    })
  }

  addProductList(item: RequisitionProductDetailsResponse) {
    let list = new RequisitionProductDetailsResponse();
    if ((item.productPartCode != null) && (item.quantity > 0)) {
      list.productPartCode = item.productPartCode;
      list.quantity = item.quantity;
      this.productList.push(list);
      this.productListResponse.productPartCode = null;
      this.productListResponse.quantity = 1;
    }
  }
  removeProductList(item: RequisitionProductDetailsResponse) {
    if (item != null) {
      const index: number = this.productList.indexOf(item);
      if (index !== -1) {
        this.productList.splice(index, 1);
      }
    }
  }

  postCreateRequsition() {
    if (this.requisitionResponse.requiredDate == null) {
      this.message = 'Please Entert the requiredDate'
      this.popupSuccessModel.show();
    }
    else if (this.requisitionResponse.requisitionStatusId == null || this.requisitionResponse.requisitionStatusId === 0) {
      this.message = 'Please Enter the requisitionStatusId';
      this.popupSuccessModel.show();
    }
    else if (this.requisitionResponse.fromWarehouseName == null || this.requisitionResponse.fromWarehouseName === '') {
      this.message = 'Please Enter the fromWarehouseId'
      this.popupSuccessModel.show();
    }
    else if (this.requisitionResponse.toWarehouseName == null || this.requisitionResponse.toWarehouseName === '') {
      this.message = 'Please Enter the toWarehouseId'
      this.popupSuccessModel.show();
    }
    else if (this.requisitionResponse.productTypeId == null || this.requisitionResponse.productTypeId === 0) {
      this.message = 'Please Enter the productTypeId'
      this.popupSuccessModel.show();
    }
    else if (this.requisitionResponse.approvalStatusId == null || this.requisitionResponse.approvalStatusId === 0) {
      this.message = 'Please Enter the approvalStatusId'
      this.popupSuccessModel.show();
    }
    else if (this.noMatchProduct === ' ** Record not found') {
      this.message = 'Product not found';
      this.popupSuccessModel.show();
    }
    else {
      if (!this._isEditMode) {
        this.requisitionRequest.requiredDate = this.requisitionResponse.requiredDate;
        this.requisitionRequest.requisitionStatusId = this.requisitionResponse.requisitionStatusId;

        this.requisitionRequest.fromWarehouseId = this.requisitionResponse.fromWarehouseId;
        this.requisitionRequest.toWarehouseId = this.requisitionResponse.toWarehouseId;


        this.requisitionRequest.productTypeId = this.requisitionResponse.productTypeId;
        this.requisitionRequest.approvalStatusId = this.requisitionResponse.approvalStatusId;

        this.requisitionRequest.productList = this.productList;

        this.spinner.show();
        this.requisitionService.postRequisition(this.requisitionRequest).subscribe(data => {
          this.tempId = data.id;
          this.tempMannulId = data.manualId;
          this.message = 'Submitted Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onRequisitionCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
          }
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
      else {
        this.requisitionRequest.requisitionId = this.requisitionResponse.id;
        this.requisitionRequest.requiredDate = this.requisitionResponse.requiredDate;
        this.requisitionRequest.requisitionStatusId = this.requisitionResponse.requisitionStatusId;

        this.requisitionRequest.fromWarehouseId = this.requisitionResponse.fromWarehouseId;
        this.requisitionRequest.toWarehouseId = this.requisitionResponse.toWarehouseId;


        this.requisitionRequest.productTypeId = this.requisitionResponse.productTypeId;
        this.requisitionRequest.approvalStatusId = this.requisitionResponse.approvalStatusId;

        this.requisitionRequest.productList = this.productList;

        this.spinner.show();
        this.requisitionService.updateRequisition(this.requisitionRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onRequisitionCreated.emit(data);
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
    this.requisitionResponse = new RequisitionResponse();
    this.onCancle.emit('cancled');
  }


  // products drop down
  productTimes() {
    this.isProductSelected = 'fa fa-search';
    this.productListResponse.productPartCode = null;
    this.noMatchProduct = '';
  }
  onKeydownPartCode($event) {
    this.isProductSelected = 'fa fa-times';
  }

  onSelectPartCode($event: TypeaheadMatch): void {
    this.productListResponse.productPartCode = $event.item.productPartCode;
    this.noMatchProduct = '';
  }
  typeaheadNoResultsProduct($event) {
    if ($event) {
      this.noMatchProduct = ' ** Record not found';
    }
  }

  noMatchProductCheck() {
    if (this.productListResponse.productPartCode == null || this.productListResponse.productPartCode === '') {
      this.noMatchProduct = '';
      this.isProductSelected = 'fa fa-search'

    }
  }

  // fromWarehouse dropdown
  fromWarehouseTimes() {
    this.isFromWarehouseSelected = 'fa fa-search';
    this.requisitionResponse.fromWarehouseId = null;
    this.noMatchFromWarehouse = '';
  }
  onKeydownFromWarehouse($event) {
    this.isFromWarehouseSelected = 'fa fa-times';
  }

  onSelectFromWarehouse($event: TypeaheadMatch): void {
    this.requisitionResponse.fromWarehouseId = $event.item.warehouseId;
    this.noMatchFromWarehouse = '';
  }
  typeaheadNoResultsFromWarehouse($event) {
    if ($event) {
      this.noMatchFromWarehouse = ' ** Record not found';
    }
  }

  noMatchFromWarehouseCheck() {
    if (this.requisitionResponse.fromWarehouseId == null || this.requisitionResponse.fromWarehouseId === 0) {
      this.noMatchFromWarehouse = '';
      this.isFromWarehouseSelected = 'fa fa-search'

    }
  }

  // To Warehouse
  toWarehouseTimes() {
    this.isToWarehouseSelected = 'fa fa-search';
    this.requisitionResponse.toWarehouseId = null;
    this.noMatchToWarehouse = '';
  }
  onKeydownToWarehouse($event) {
    this.isToWarehouseSelected = 'fa fa-times';
  }

  onSelectToWarehouse($event: TypeaheadMatch): void {
    this.requisitionResponse.toWarehouseId = $event.item.warehouseId;
    this.noMatchToWarehouse = '';
  }
  typeaheadNoResultsToWarehouse($event) {
    if ($event) {
      this.noMatchToWarehouse = ' ** Record not found';
    }
  }

  noMatchToWarehouseCheck() {
    if (this.requisitionResponse.toWarehouseId == null || this.requisitionResponse.toWarehouseId === 0) {
      this.noMatchToWarehouse = '';
      this.isToWarehouseSelected = 'fa fa-search'
    }
  }
  createProductModelhide() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  addNewProduct() {
    this.createProduct = new Product();
    this.createProductModel.show();
  }
  cancelDataProd() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }

  createWarehouseModelhide() {
    this.newwarehouse = new WarehousedetailsRequest();
    this.createWarehouseModel.hide();
  }

  addNewFromWarehouse() {
    this.newwarehouse = new WarehousedetailsRequest();
    this.createWarehouseModel.show();
  }
  addNewToWarehouse() {
    this.newwarehouse = new WarehousedetailsRequest();
    this.createWarehouseModel.show();
  }
  cancelDataWarehouse() {
    this.newwarehouse = new WarehousedetailsRequest();
    this.createWarehouseModel.hide();
  }

  onProductCreated($event: Product) {
    this.productsForList.productPartCode = $event.partCode;
    this.productsForList.productDescription = $event.productDescription;
    this._productList.unshift(this.productsForList);
  }
  onWarehouseCreated($event: WarehousedetailsRequest) {
    this.warehouseForList.warehouseId = $event.warehouseId;
    this.warehouseForList.warehouseName = $event.warehouseName;
    this.warehouseList.unshift(this.warehouseForList);
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
