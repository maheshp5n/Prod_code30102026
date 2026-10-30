import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { ProductsService } from '../../services/products.services';
import { Product, ProductSubFamily } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-create-new-product',
  templateUrl: './create-new-product.component.html',
  styleUrls: ['./create-new-product.component.css']
})

export class CreateNewProductComponent implements OnInit {

  constructor(private spinner: NgxSpinnerService, private spinner2: NgxSpinnerService, private productService: ProductsService) { }

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
    if (this.newProduct.productFamilyId != null) {
      this.productService.getProductSubFamily(this.newProduct.productFamilyId).subscribe(data => {
        this.productSubFamily = data;
        this.spinner.hide();
      }, err => {
        this.message = err.message;
        this.popupSuccessModel.show();
        this.spinner.hide();
      });
    }
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
    if (FieldValidation.isEmpty(this.newProduct.partCode)) {
      this.message = 'Please fill Part Code';
    } else if (FieldValidation.isEmpty(this.newProduct.type)) {
      this.message = 'Please fill Type';
    } else if (this.newProduct.productFamilyId == null) {
      this.message = 'Please fill Family Name';
    } else if (this.newProduct.subProductFamilyId == null) {
      this.message = 'Please fill Sub Product Family Name';
    } else if (this.newProduct.price === null || this.newProduct.price === '' || this.newProduct.price === undefined) {
      this.message = 'Please fill Price';
    } else if (!FieldValidation.isValidCurrency(this.newProduct.price)) {
      this.message = 'Please fill valid Price';
    } else if ((this.newProduct.price).length > 8) {
      this.message = 'Price exceeds 1 crore';
    } else if (!FieldValidation.isEmpty(this.newProduct.productDescription) && (this.newProduct.productDescription).length > 200) {
      this.message = 'Please fill valid Product Description';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner2.show();
        this.productService.postProducts(this.newProduct).subscribe(data => {
          this.idd = data.partCode;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onProductCreated.emit(data);
          this.spinner2.hide();
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner2.show();
        this.productService.putProducts(this.newProduct.partCode, this.newProduct).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner2.hide();
          this.popupSuccessModel.show();
          this.onProductCreated.emit(data);
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      }
    }
    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }

  }

  cancelData() {
    this.newProduct = new Product();
    this.onCancle.emit('cancled');
  }

  getproductFamily() {
    this.productService.getProductFamilyService().subscribe(data => {
      this.productFamily = data;
    });
  }

  getproductSubFamily() {
    this.spinner.show();
    this.productService.getProductSubFamily(this.newProduct.productFamilyId).subscribe(data => {
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
