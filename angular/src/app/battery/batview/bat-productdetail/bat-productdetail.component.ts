import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { ActionHistoryObjectAndId, BillOfMaterialRequest, BillOfMaterialResponse, PriceBookEntriesRequestInProducts, PriceBookEntriesResponse, PriceBookResponse, ProductsResponse, ProductSubFamily } from '../../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { BillOfmaterialService } from '../../../services/billOfMaterial.service';
import { ProductsService } from '../../../services/products.services';
import { PriceBookService } from '../../../services/pricebook.service';
import { Loginservices } from '../../../services/login.services';
import { BtproductService } from '../../bat_services/btproduct.service';
import {Product, ProductDetailResponse} from '../../bt_model';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bat-productdetail',
  templateUrl: './bat-productdetail.component.html',
  styleUrls: ['./bat-productdetail.component.css']
})
export class BatProductdetailComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel', { static: false }) createProductModel: ModalDirective;
  @ViewChild('createpriceBookModel', { static: false }) createpriceBookModel: ModalDirective;
  @ViewChild('createaddProductModel', { static: false }) createaddProductModel: ModalDirective;
  @ViewChild('createeditProductModel', { static: false }) createeditProductModel: ModalDirective;
  @ViewChild('creatitmModel',{static:false}) creatitmModel:ModalDirective;
  iseditProductOpen: boolean;
  isProductOpen: boolean;
  productid: string;
  product: ProductDetailResponse = new ProductDetailResponse();
  flag: boolean;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  productsList: Array<Product> = [];
  productFamily;
  subProductname;
  putProduct: Product = new Product();
  message: string;
  newProduct: Product = new Product();
  sapId: string = '';
  isEditMode: boolean = false;
  heading: string;
  CREATE_PRODUCT_HEADING: string = 'New Product';
  EDIT_PRODUCT_HEADING: string = 'Edit Product';
  productList: Array<ProductsResponse> = [];
  productId;
  productSubFamily: Array<ProductSubFamily> = [];
  productById: ProductsResponse = new ProductsResponse();
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  priceResp: PriceBookResponse = new PriceBookResponse();
  isEditModePriceBook: boolean = false;
  firstPriceBook: boolean;
  lastPriceBook: boolean;
  sizePriceBook: number;
  serialNumberPriceBook: number;
  public pagePriceBook: number = 0;
  totalElementsPriceBook: number;
  isPriceBook: boolean;
  headingaddPro: string = '';
  viewAddPriceBook: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  priceBookResponseList: Array<PriceBookResponse> = [];
  isaddProductOpen: boolean;
  isEditModeAddProduct: boolean;
  Id : number
  priceBookEntriesRequestInProducts: PriceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
  _cFlag: string;
  bomList:Array<BillOfMaterialResponse>=[];

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

  ngOnInit() {
    this.getProductDetails();
    this.itemsList();
  }

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private bomservice:BillOfmaterialService,private _productService: ProductsService, private pricebooservice: PriceBookService,
    private loginService: Loginservices, private btProductService :BtproductService, private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.productid = pram['id'];
      this.Id = pram['id']
      console.log(this.Id);
      this.actionHistoryObjectAndId.objectId = this.productid;
      this.actionHistoryObjectAndId.objectName = 'bat_product';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
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
      this.vDetails = true;
      this.getProductDetails();
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.itemsList();
      this.getByProductPartCode(this.productid);
      // this.getproducts();
    }
    return false;
  }

  // getproductFamily() {
  //   this._productService.getProductFamilyService().subscribe(data => {
  //     this.productFamily = data;
  //   });
  // }

  getproductSubFamily() {
    this.spinner.show();
    this._productService.getProductSubFamily(this.putProduct.productFamilyId).subscribe(data => {
      this.productSubFamily = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }


  getSubProduct(subId) {
    this._productService.getSubproduct(subId).subscribe(data => {
      this.subProductname = data;
    }, err => {
      this.spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});
    });
  }

  putProductDetails() {
    this.btProductService.updateProductReq(this.newProduct).subscribe(
      data => {
        this.spinner.hide();
        this._global.alert({type:'',message:'Updated Successfully'});
      }, err => {
        this.spinner.hide();
        this._global.alert({type:'alert',message:err.error.message});
      }
    );
  }
  
  ////// currently used method//////
  getProductDetails() {
    this.spinner.show();
    this.btProductService.getDataByProductId(this.Id).subscribe(data => {
      this.product = data;
      this.partcode= data.partCode;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();     
      this._global.alert({type:'alert',message:error.error.message});
    });
  }
  onClickEdit(y,i) {
    this.spinner.show();    
    this.newProduct.productFamilyId = y.productFamilyId;
    this.newProduct.subProductFamilyId = y.productSubFamilyId;
    this.btProductService.getDataByProductId(i).subscribe(data => {
      this.newProduct.id = data.id;
      this.newProduct.partcode = data.partCode;
      this.newProduct.productPrice = data.productPrice;
      this.newProduct.productType = data.productType;
      this.newProduct.productFamilyId = data.productFamilyId;
      this.newProduct.subProductFamily = data.subProductFamily;
      this.newProduct.productDescription = data.productDescription;
      this.spinner.hide();
      console.log(this.newProduct);
    },error =>{ 
      this.spinner.hide();     
      this._global.alert({type:'alert',message:error.error.message});
    });
    this.isEditMode = true;
    this.heading = this.EDIT_PRODUCT_HEADING;
    this.createProductModel.show();
  }

  cancelData() {
    this.createProductModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.newProduct = new Product();
  }

  onProductCreated($event: ProductDetailResponse) {
    this.product = $event;
  }

  ////////////////////////////////////


  getByProductPartCode(partCode: string) {
    this.spinner.show();
    this._productService.getPriceBookByProductPartCode(partCode, this.pagePriceBook).subscribe(data => {
      this.priceBookResponseList = data.content;
      this.firstPriceBook = data.first;
      this.lastPriceBook = data.last;
      this.sizePriceBook = data.size;
      this.totalElementsPriceBook = data.totalElements;
      this.serialNumberPriceBook = ((this.pagePriceBook) * this.sizePriceBook);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});
    });
  }

  // PriceBook Starts
  setPagePreviousPriceBook(event: any) {
    event.preventDefault();
    this.pagePriceBook = this.pagePriceBook - 1;
    this.getByProductPartCode(this.productid);
  }

  setPagePriceBook(event: any) {
    event.preventDefault();
    this.getByProductPartCode(this.productid);
  }

  setPageNextPriceBook(event: any) {
    event.preventDefault();
    this.pagePriceBook = this.pagePriceBook + 1;
    this.getByProductPartCode(this.productid);
  }

  onEditPriceBookEntry(idd) {
    console.log(idd);
    this.spinner.show();
    this.pricebooservice.getDetailByentriesId(idd).subscribe(data => {
      this.priceBookEntriesRequestInProducts = data;
      this.spinner.hide();
    });
    this.isEditModeAddProduct = true;
    this.createeditProductModel.show();
  }

  onpricebookCreated($event) {
    if (!this.isEditModePriceBook) {
      this.priceBookResponseList.unshift($event);
      this.totalElementsPriceBook = this.totalElementsPriceBook + 1;
    } else {
      let index = 0;
      this.priceBookResponseList.forEach(element => {
        if (element.id === $event.id) {
          this.priceBookResponseList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  cancelPricebookData() {
    this.createpriceBookModel.hide();
    this.priceResp = new PriceBookResponse();
  }

  addProduct() {
    this.headingaddPro = 'Add to Price Book';
    this.viewAddPriceBook = true;
    this.createaddProductModel.show();
    this.isEditModeAddProduct = false;
  }

  cancelDataaddProduct() {
    this.createaddProductModel.hide();
  }

  cancelDataeditProduct() {
    this.createeditProductModel.hide();
  }
  popupClose() {
    this.popupSuccessModel.hide();
    // if (this.popupNgClass === 'alert alert-success center center-block') {
    //
    // }
  }

  onaddProductCreated($event: PriceBookEntriesResponse) {
    let temp = new PriceBookResponse();
    temp.id = $event.priceBookId;
    temp.name = $event.priceBookName;
    temp.listPrice = $event.listPrice;
    temp.active = $event.active;
    if (!this.isEditMode) {
      this.priceBookResponseList.unshift(temp);
    } else {
      let index = 0;
      this.priceBookResponseList.forEach(element => {
        if (element.id === $event.id) {
          this.priceBookResponseList[index] = temp;
        } else {
          index++;
        }
      });
    }
  }

  // onaddProductCreated($event) {
  //   this.getProductDetails();
  //   let index = 0;
  //   this.priceBookResponseList.forEach(element => {
  //     if (element.id === $event[0].id) {
  //       this.priceBookResponseList[index] = $event[0];
  //     } else {
  //       index++;
  //     }
  //   });

  //   if (!this.isEditMode) {
  //     this.priceBookResponseList.unshift(temp);
  //   } else {
  //     let index = 0;
  //     this.priceBookResponseList.forEach(element => {
  //       if (element.id === $event.id) {
  //         this.priceBookResponseList[index] = temp;
  //       } else {
  //         index++;
  //       }
  //     });
  //   }
  // }

  // onaddProductCreated($event) {
  //   this.getByProductPartCode(this.productid);
  //   this.itemsList();
  //   this.getProductDetails();
  // }
  partcode:string;
  billReq:BillOfMaterialRequest=new BillOfMaterialRequest();
  additems(){
    this.billReq=new BillOfMaterialRequest();
    this.isEditMode=false;
    this.locationName='';
    this.creatitmModel.show();
  }
  cancelDataBOM(){
    this.billReq=new BillOfMaterialRequest();
    this.creatitmModel.hide();
  }
  onBOMCreated($event){
   this.itemsList();
   this.getProductDetails();
   this.getByProductPartCode(this.productid);

  }
  locationName:string;
  drawingRef:string;
  onEditItem(billReq:BillOfMaterialRequest){
    this.isEditMode=true;
    this.creatitmModel.show();
    this.billReq=billReq;
    this.bomservice.getBOM(this.productid).subscribe(data=>{
      this.bomList=data;
      
      data.forEach((e)=>{
        this.billReq.productPartCode=e.rawProductPartCode;
        this.billReq.requiredQuantity=e.requiredQuantity;
      })
        })
  }

  itemsList(){
    this.bomservice.getBOM(this.productid).subscribe(data=>{
  this.bomList=data;
    })
  }

}
