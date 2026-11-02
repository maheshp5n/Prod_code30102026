import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../../services/pagefilterservice';
import { Loginservices } from '../../../services/login.services';
import { ProductsService } from '../../../services/products.services';
import { Product, ProductSubFamily } from '../../bt_model';
import { BtproductService } from '../../bat_services/btproduct.service';
import { ProductDetailResponse, Page } from '../../bt_model'
import { GlobalserviceService } from '../../../globalservice.service';
import { FieldValidation } from '../../../services/validation.services';



@Component({
  selector: 'app-bat-product',
  templateUrl: './bat-product.component.html',
  styleUrls: ['./bat-product.component.css']
})
export class BatProductComponent implements OnInit {
  isProductOpen: boolean;
  isEditMode: boolean = false;
  heading: string;
  CREATE_PRODUCT_HEADING: string = 'New Product';
  EDIT_PRODUCT_HEADING: string = 'Edit Product';
  productList: Page<ProductDetailResponse> = new Page<ProductDetailResponse>();
  isSelected = false;
  reverse: boolean = false;
  recentlyView: boolean = false;
  searchitem: string = '';
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  recently: boolean = false;
  ngClassRelated: string = 'dropdown-item';
  public page: number = 0;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  message: string;
  productId;
  productById: ProductDetailResponse = new ProductDetailResponse();
  productSubFamily: Array<ProductSubFamily> = [];
  newProduct: Product = new Product();
  filterParam : string;
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'All',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Product',
    clickEventParam: 'Product',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Sub Product',
    clickEventParam: 'Sub Product',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 4,
    name: 'Component',
    clickEventParam: 'Component',
    class: 'dropdown-item',
    pined: false
  }];
  dropdownSelected = {
    sqId: 1,
    name: 'All PR',
    filtreparam: 'all',
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Part Code',
      sortColumnName: 'productPartCode',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Product Family',
      sortColumnName: 'subProductFamily.productFamily.productFamilyName',
      icon: ''
    },
    {
      id: 3,
      name: 'Product Sub Family',
      sortColumnName: 'subProductFamily.productFailySubProductName',
      icon: ''
    },
    {
      id: 4,
      name: 'Type',
      sortColumnName: 'productType',
      icon: ''
    },
    {
      id: 5,
      name: 'Price',
      sortColumnName: 'productPrice',
      icon: ''
    },
    {
      id: 6,
      name: 'Product Description',
      sortColumnName: 'pdoductDescription',
      icon: ''
    },
    {
      id: 7,
      name: 'Created Date',
      sortColumnName: 'createddate',
      icon: ''
    }
  ];
  pindtselection = false;
  _cFlag: string;

  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createProductModel') createProductModel: ModalDirective;

  constructor(private pagefilterservice: PagefilterService, private spinner: NgxSpinnerService, private productService: ProductsService, private _activatedRoute: ActivatedRoute,
    private loginService: Loginservices, private btproductService: BtproductService, private _global: GlobalserviceService) {
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.size = Number(param['size']);
      }
      this.getFilterList();
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    // this.setOrder('createddate', 7);
    //this.getAllProductList();
  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('Bat_Product').subscribe(data => {
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.dropdownSelected.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.dropdownSelected.name = d.filterName;
            this.dropdownSelected.filtreparam = d.filterParam;
            this.dropdownSelected.sqId = d.userSummaryPageFilterId;
            this.pindtselection = true;
            this.dropdownSelected.page = 0;
          }

          else {
            if (d.sqId == this.dropdownSelected.sqId) {
              this.pindtselection = true;
            }
          }

        }
        this.dropdownsList.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }
      this.getBtProductListByFilterWise();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getBtProductListByFilterWise();
        if (this.dropdownSelected.filtreparam == 'Sub Product') {
          this.dropdownSelected.filtreparam = 'Sub-Product';
          this.getBtProductListByFilterWise();
        } 
        if (item.pined) {    
          this.pindtselection = true;
        } else {
          this.pindtselection = false;
        }
      } else {
        item.class = 'dropdown-item';
      }
    });
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
        this.dropdownSelected.page = 0;
        if(this.dropdownSelected.filtreparam == 'All'){
          this.getBtProductListByFilterWise(); 
        }
        if (this.dropdownSelected.filtreparam == 'Product') { 
          this.getBtProductListByFilterWise();
        }
        if (this.dropdownSelected.filtreparam == 'Sub Product') {
          this.dropdownSelected.filtreparam = 'Sub-Product';
          this.getBtProductListByFilterWise();
        } 
         if (this.dropdownSelected.filtreparam == 'Component') {
          this.getBtProductListByFilterWise();
        }

      } else {
        tbl.icon = '';
      }
    }); 
  }
  pinFilter() {
    this.spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this.spinner.hide();
    }, error => this.spinner.hide());
  }

getBtProductListByFilterWise(){
  this.productList.content = [];
  this.spinner.show();
  // this.btproductService.getBtProductByFilter(this.dropdownSelected.filtreparam,this.dropdownSelected.page).subscribe(data=>{
  this.btproductService.getBtProductByFilter(`filterParam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{

  this.spinner.hide();
  this.productList.content = data.content; 
  this.productList.first = data.first;
  this.productList.last = data.last;
  this.productList.totalPages = data.totalPages;
  this.productList.totalElements = data.totalElements;
  this.productList.number = data.number;
  },error=>{
  this.spinner.hide();     
  this._global.alert({type:'alert',message:error.error.message});

  })
  
}
  onClickEdit(i) {
    this.spinner.show();
    this.btproductService.getDataByProductId(i).subscribe(data => {
      this.newProduct = new Product();
      this.newProduct.id = data.id;
      this.newProduct.productPrice = data.productPrice;
      this.newProduct.productType = data.productType;
      this.newProduct.productFamilyId = data.productFamilyId;
      this.newProduct.subProductFamily = data.subProductFamily;
      this.newProduct.partcode = data.partCode;
      this.newProduct.productDescription = data.productDescription;
      console.log(this.newProduct);
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_PRODUCT_HEADING;
    this.createProductModel.show();
  }


  showCreateProductView() {
    this.newProduct = new Product();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.createProductModel.show();
  }

  cancelData() {
    this.createProductModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.newProduct = new Product();
  }

  onProductCreated($event: ProductDetailResponse) {
    if (!this.isEditMode) {
      this.productList.content.unshift($event);
    } else {
      let index = 0;
      this.productList.content.forEach(element => {
        if (element.partCode === $event.partCode) {
          this.productList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();

  }
  pagenumber: any = null;
  jumpPage(pagenumber) {
    if(pagenumber == null || pagenumber == 0 || pagenumber == ""){    
      this._global.alert({type:'alert',message:"Please Enter A Valid Input"}); 
    }
    else if (this.productList.totalPages < pagenumber) {
       this._global.alert({type:'alert',message:"Page Count Should Be Less Then Total Count"});
    }
    else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
    else {
     this.dropdownSelected.page = pagenumber - 1; 
     this.dropdownSelected.filtreparam = 'All';  
      this.getBtProductListByFilterWise();
    }
  }
  advancesearch = {
    partCode: '',
    productFamily: '',
    productSubFamily: '',
    productType: '',
    productDescription: '',

  };

  searchProduct() {
    if (this.advancesearch.productSubFamily == '' && this.advancesearch.productType == ''
      && this.advancesearch.productDescription == '' && this.advancesearch.partCode == '' && this.advancesearch.productFamily == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this.spinner.show();
      this.btproductService.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.productList.content = data;
        this.page = 0;
        this.size = data.length;
        this.first = true;
        this.last = true;
        this.productList.first = true;
        this.productList.last = true;
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message:error.error.message});
      });
    }
  }
  resetsearch() {
    this.advancesearch.partCode = ''
    this.advancesearch.productFamily = ''
    this.advancesearch.productSubFamily = ''
    this.advancesearch.productType = ''
    this.advancesearch.productDescription = ''
    this.getBtProductListByFilterWise();

  }
   
  LimteExceed:boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(this.LimteExceed==true){
      return false;
    }
    return true;

  }

}
