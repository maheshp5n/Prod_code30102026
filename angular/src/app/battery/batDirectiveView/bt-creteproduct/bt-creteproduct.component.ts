import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Product, ProductSubFamily } from '../../bt_model';
import { FieldValidation } from '../../../services/validation.services';
import { BtproductService } from '../../bat_services/btproduct.service';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bt-creteproduct',
  templateUrl: './bt-creteproduct.component.html',
  styleUrls: ['./bt-creteproduct.component.css']
})

export class BtCreteproductComponent  implements OnInit {

  constructor(private spinner: NgxSpinnerService, private spinner2: NgxSpinnerService,private btProductService:BtproductService,
    private _global : GlobalserviceService) { }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editProductData(product: Product) {
    this.spinner.show();
    this.popupNgClass = 'alert alert-danger center center-block';
    this.newProduct = product;  
      this.btProductService.getProductSubFamily(this.newProduct.productFamilyId).subscribe(data => {
        console.log(this.newProduct.productFamilyId);
        this.productSubFamily = data;
        this.spinner.hide();
      }, err => {
        this._global.alert({ type: 'alert', message:err.error.message});
        this.spinner.hide();
      });
   // }
    this.spinner.hide();
  }

  get editProductData(): Product {
    return this.newProduct;
  }

  productSubFamily: Array<ProductSubFamily> = [];
  newProduct: Product = new Product();
  productFamily;
  flag: boolean;
  message: string;
  isProductSelected: string;
  public _isEditMode: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel', { static: false }) createProductModel: ModalDirective;

  @Output()
  onProductCreated: EventEmitter<Product> = new EventEmitter<Product>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onProductCreatedAndNew: EventEmitter<Product> = new EventEmitter<Product>();

  idd: string;

  ngOnInit() {
    this.spinner.show();
    this.getproductFamily();
  }

  cancelSuccessPOpup() {
    this.popupSuccessModel.hide();
    this.onCancle.emit('cancled');
  }
  createProduct() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newProduct.partcode)) {
      this._global.alert({ type: 'alert', message:'Please Fill Part Code'});
    } 
    else if (FieldValidation.isEmpty(this.newProduct.productType)) {
     this._global.alert({ type: 'alert', message:'Please Fill Type'});
    } 
    else if (this.newProduct.productFamilyId == null) {
     this._global.alert({ type: 'alert', message:'Please Fill Family Name'});
    } 
    else if (this.newProduct.productFamilyId == null) {    
      this._global.alert({ type: 'alert', message:'Please Fill Sub Product Family Name'});
    } 
    else if (this.newProduct.productPrice === null || this.newProduct.productPrice === '' || this.newProduct.productPrice === undefined) {    
      this._global.alert({ type: 'alert', message:'Please Fill Price'});
    } 
     else if ((this.newProduct.productPrice).length > 8) {   
      this._global.alert({ type: 'alert', message:'Price Exceeds 1 Crore'});
    } 
    else if (!FieldValidation.isEmpty(this.newProduct.productDescription) && (this.newProduct.productDescription).length > 200) {    
      this._global.alert({ type: 'alert', message:'Please Fill Valid Product Description'});
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner2.show();
        this.btProductService.createProductReq(this.newProduct).subscribe(data => {
          this.idd = data.partCode;
          this._global.alert({ type: '', message:'Created Successfully'});
          this.onProductCreated.emit(data);         
          this.onCancle.emit('cancled');
          this.spinner2.hide();
        }, err => {
          this.spinner.hide();
          this._global.alert({ type: 'alert', message:err.error.message});
        });
      } else {
        this.spinner2.show();
        this.btProductService.updateProductReq(this.newProduct,).subscribe(data => {
          this._global.alert({ type: '', message:'Updated Successfully'});
          this.spinner2.hide();
          this.onProductCreated.emit(data);         
          this.onCancle.emit('cancled');
        }, err => {  
          this.spinner.hide();

          if(err.error.message==null)
          {
            this._global.alert({ type: '', message:'Somthing Went Wrong'});
          }else{

            this._global.alert({ type: 'alert', message:err.error.message});
          }
        });
      }
    }
    if (this.flag !== true) {
    
    }

  }

  cancelData() {
    this.newProduct = new Product();
    this.onCancle.emit('cancled');
  }

  getproductFamily() {
    this.spinner.show();
    this.btProductService.getProductFamilyService().subscribe(data => {
      this.productFamily = data;
      this.spinner.hide();
    });
  }

  getproductSubFamily() {
    this.spinner.show();
    console.log(this.newProduct.productFamilyId);
    this.btProductService.getProductSubFamily(this.newProduct.productFamilyId).subscribe(data => {
      this.productSubFamily = data;
      this.spinner.hide();
    });
  }

  productSelected() {
    this.bindToProduct();
  }

  productTimes() {
    this.isProductSelected = 'fa fa-search';
    // this.newProduct.productName = null;
  }

  onKeydownProduct() {
    this.isProductSelected = 'fa fa-times';
  }

  bindToProduct() {
    this.productFamily.forEach(element => {
      this.newProduct = element;
      // if(element.productName==this.newProduct.productName){
      // this.newProduct.=element.sapProductNumber;
      // }
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}
