import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductsService } from '../../services/products.services';
import { Page, Product, ProductFamily, ProductsResponse, ProductSubFamily, ProductType } from '../../services/model';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  isProductOpen: boolean;
  isEditMode: boolean = false;
  heading: string;
  productFamilyList:Array<ProductFamily>=[];
  productSubFamList:Array<ProductSubFamily>=[];
  proType:Array<ProductType>=[]
  CREATE_PRODUCT_HEADING: string = 'New Product';
  EDIT_PRODUCT_HEADING: string = 'Edit Product';
  productList: Page<ProductsResponse> = new Page<ProductsResponse>();
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
  productById: ProductsResponse = new ProductsResponse();
  productSubFamily: Array<ProductSubFamily> = [];
  newProduct: Product = new Product();
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  dropdownsList = [{
    sqId: 1,
    name: 'All PR',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Open PR',
    clickEventParam: 'open',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Receipt Pending',
    clickEventParam: 'receiptpending',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 5,
    name: 'Sent Request',
    clickEventParam: 'byme',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 6,
    name: 'Received Request',
    clickEventParam: 'forme',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 7,
    name: 'Draft',
    clickEventParam: 'Draf',
    class: 'dropdown-item',
    pined: false
  }];


  dropdownSelected = {
    sqId: 1,
    name: 'All PR',
    filtreparam: 'all',
    sortname: 'createddate',
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

  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService, private productService: ProductsService, private _activatedRoute: ActivatedRoute,
    private loginService: Loginservices) {
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
    this.getProductFamilyForList();
    this.getProductSubFamilyForList();
    this.getProductType();
    // this.setOrder('createddate', 7);
  }
  
  getFilterList() {
    this.pagefilterservice.getPagefilter('products').subscribe(data => {
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
 
      this.getProducts()
    });
  }
  dropdownChange(sqId) {
   this.dropdownsList.forEach(item => {
     if (item.sqId == sqId) {
       item.class = 'dropdown-item dropdown-item-checked';
       this.dropdownSelected.sqId = sqId;
       this.dropdownSelected.name = item.name;
       this.dropdownSelected.filtreparam = item.clickEventParam;
       console.log(this.dropdownSelected.filtreparam);
       this.dropdownSelected.page = 0;
       if(this.dropdownSelected.filtreparam=='related'){
         this.getRecentProducts();
       }
       else
      { 
        this.getProducts();}
      
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
       if(this.dropdownSelected.filtreparam=='related'){
        this.getRecentProducts();
      }
      else
     { 
       this.getProducts();
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


  

  // PAGINATION ENDS
  getProducts() {
    this.spinner.show();
    this.productList.content=[];
    this.productService.getProducts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.productList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecentProducts() {
    this.spinner.show();
    this.productList.content=[];
    this.productService.getRecentProducts(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.productList = data;
      // this.first = data['first'];
      // this.last = data['last'];
      // this.size = data['size'];
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  onClickEdit(i) {
    this.spinner.show();
    this.productService.getProductDetails(i).subscribe(data => {
      this.newProduct = new Product();
      this.newProduct.price = data.productPrice;
      this.newProduct.type = data.productType;
      this.newProduct.productFamilyId = data.productFamilyId;
      this.newProduct.subProductFamilyId = data.productSubFamilyId;
      this.newProduct.partCode = data.partCode;
      this.newProduct.productDescription = data.productDescription;

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

  onProductCreated($event: ProductsResponse) {
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
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.productList.totalPages< pagenumber){
    this.message="Enter Valid input";
    this.popupSuccessModel.show();
    // this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getProducts()}
}
advancesearch = {
  partCode: '',
  productFamily: '',
  productSubFamily: '',
  type: '',
  description: '',

};

searchProduct() {
   
  if(this.advancesearch.productSubFamily=='' && this.advancesearch.type==''
   && this.advancesearch.description=='' && this.advancesearch.partCode=='' &&this.advancesearch.productFamily==''){
   this.message="Please Enter valid Key for Search";
  //  this._global.alert({type:'alert',message:this.message});
  this.popupSuccessModel.show();
  }
  else{
    this.productList.content=[];
    this.spinner.show();
    this.productService.getSearchResult(this.advancesearch).subscribe(data => {
      this.spinner.hide();
      this.productList.content= data;
      this.productList.last= true;
      this.productList.first= true;
      this.page = 0;
      this.size = data.length;
      this.first = true;
      this.last = true;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
  }

}
resetsearch(){
  this.advancesearch.partCode=''
  this.advancesearch.productFamily= ''
  this.advancesearch.productSubFamily= ''
  this.advancesearch.type= ''
  this.advancesearch.description=''

}

getProductFamilyForList(){
  this.spinner.show();
  this.productService.getAdvSearchProductFamily().subscribe(data=>{
    this.spinner.hide();
    const temp=[];
    for(let i=0;i<data.length;i++){
      let lis = new ProductFamily();
      lis.name=data[i];
      temp.push(lis);
      this.productFamilyList=temp;
    }

  })

}
getProductSubFamilyForList(){
  this.spinner.show();
this.productService.getAdvSearchSubProductFamily().subscribe(data=>{
  this.spinner.hide();
  const temp=[];
  for(let i=0;i<data.length;i++){
    let lis = new ProductSubFamily();
    lis.productFailySubProductName=data[i];
    temp.push(lis);
    this.productSubFamList=temp;
  }

})
}
 getProductType(){
   this.productService.getTypeForAdv().subscribe(data=>{
    this.spinner.hide();
 this.proType=data;
   })

 }

}
