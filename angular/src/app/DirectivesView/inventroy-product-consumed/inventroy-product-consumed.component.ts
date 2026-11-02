import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ProductsForList, BRCRepariInventoryProductConsumedRequest,  Product, BRCRepariInventoryProductConsumedResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { AssetsService } from '../../services/assets.services';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-inventroy-product-consumed',
  templateUrl: './inventroy-product-consumed.component.html',
  styleUrls: ['./inventroy-product-consumed.component.scss']
})
export class InventroyProductConsumedComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel') createProductModel: ModalDirective;
  @ViewChild('ManageInventoryModel') ManageInventoryModel: ModalDirective;

  @Output()
  onProductConsumedCreated: EventEmitter<BRCRepariInventoryProductConsumedRequest> = new EventEmitter<BRCRepariInventoryProductConsumedRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  _isEditMode: boolean;
  message: string;
  today = new Date();
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  createProduct: Product = new Product();
  productList: Array<ProductsForList> = [];
  productsForList: ProductsForList = new ProductsForList();
  consumedRequest: BRCRepariInventoryProductConsumedRequest = new BRCRepariInventoryProductConsumedRequest();
  consumedResponse: BRCRepariInventoryProductConsumedResponse = new BRCRepariInventoryProductConsumedResponse();
  isProductModelShow: boolean = false;
  isManageInventoryModel: boolean = false;
  dateCustomClasses: DatepickerDateCustomClasses[];

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editInventoryConsumedData(consumedResponse: BRCRepariInventoryProductConsumedResponse) {
    this.consumedResponse = consumedResponse;
  }
  get editInventoryConsumedData(): BRCRepariInventoryProductConsumedResponse {
    return this.consumedResponse;
  }
  constructor(private spinner: NgxSpinnerService, private assetService: AssetsService, private manageInventoryService: ManageInventoryService,
    private loginService: Loginservices) { 
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }
  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }
  
  ngOnInit() {
    this.getProductList();
    this.faPlus;
    this.consumedResponse.quantity = 1;
  }

  getProductList() {
    this.assetService.getProductForList().subscribe(data => {
      this.productList = data;
    });
  }
  getProductDescriptionByPardCode(partCode: string){
    if(partCode !== null && partCode !== undefined){
      this.productList.forEach(data =>{
        if(partCode === data.productPartCode){
          this.consumedResponse.productDescription = data.productDescription;
        }        
      });
    }
  }

  postCreate() {
    if (this.consumedResponse.productPartCode == null || this.consumedResponse.productPartCode === '') {
      this.message = 'Please fill the part code';
      this.popupSuccessModel.show();
    }
    if (this.consumedResponse.quantity == null || this.consumedResponse.quantity < 1) {
      this.message = 'Please fill the quantity 1 or more than 1';
      this.popupSuccessModel.show();
    }
    else if (this.consumedResponse.remarks != undefined && (this.consumedResponse.remarks).length >= 500) {
      this.message = 'Please fill the remarks under 500 character';
      this.popupSuccessModel.show();
    }
    else {
      this.consumedRequest.consumeid = this.consumedResponse.consumeid;
      this.consumedRequest.miCode = this.consumedResponse.miCode;
      this.consumedRequest.productPartCode = this.consumedResponse.productPartCode;
      this.consumedRequest.quantity = this.consumedResponse.quantity;
      this.consumedRequest.materialIssueDate = this.consumedResponse.materialIssueDate;
      this.consumedRequest.faultySubmissionDate = this.consumedResponse.faultySubmissionDate;
      this.consumedRequest.remarks = this.consumedResponse.remarks;
      this.consumedRequest.repairId = this.consumedResponse.repairId;

      this.spinner.show();
      this.manageInventoryService.consumeProduct(this.consumedRequest, this.consumedResponse.repairId).subscribe(data => {
        this.message = 'Submitted Successfully';
        this.popupNgClass = 'alert alert-success center center-block';
        this.onProductConsumedCreated.emit(data);
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
  }
  cancelData() {
    this.consumedResponse = new BRCRepariInventoryProductConsumedResponse();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  createProductModelhide() {
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  addNewProduct() {
    this.createProduct = new Product();
    this.isEditMode = false;
    this.createProductModel.show();
  }
  cancelDataProd() {
    this.isEditMode = false;
    this.createProduct = new Product();
    this.createProductModel.hide();
  }
  onProductCreated($event: Product) {
    this.getProductList();
    this.consumedResponse.productPartCode = $event.partCode;
  }

}
