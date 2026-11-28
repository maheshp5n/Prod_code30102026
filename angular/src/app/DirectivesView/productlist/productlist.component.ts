import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductsService } from '../../services/products.services';
import { ProductsResponse, PriceBookEntriesResponse, PriceBookEntriesRequest, ProductFamily, ProductSubFamily, ProductType } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { PriceBookService } from '../../services/pricebook.service';
import { element } from 'protractor';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.scss']
})
export class ProductlistComponent implements OnInit {
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, true];
  pricebookreq: PriceBookEntriesRequest = new PriceBookEntriesRequest();
  productFamilyList:Array<ProductFamily>=[];
  productSubFamList:Array<ProductSubFamily>=[];
  proType:Array<ProductType>=[]
  page: number = 0;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  message: string;
  productId;
  searchitem: string = '';
  sortColumn: number = 7;
  sortColumnName: string = 'createddate';
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  productList: Array<ProductsResponse> = [];
  selectedProductList: Array<PriceBookEntriesRequest> = [];
  i: number;
  id: number
  isCaseModelOpen: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  advancesearch = {
    partCode: '',
    productFamily: '',
    productSubFamily: '',
    type: '',
    description: '',
  
  };
  advancesearchEncode = {
    partCode: '',
    productFamily: '',
    productSubFamily: '',
    type: '',
    description: '',
  
  };
  @Output()
  onProductSelected: EventEmitter<PriceBookEntriesResponse> = new EventEmitter<PriceBookEntriesResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('selectedItemModel') selectedItemModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @Input()
  set priceBookId(id: number) {
    this.id = id;

  }
  get priceBookId(): number {
    return this.id;
  }

  constructor(private spinner: NgxSpinnerService, private productService: ProductsService, private pricebookservice: PriceBookService) { }

  ngOnInit() {
    this.getProducts();
    this.getProductFamilyForList();
    this.getProductSubFamilyForList();
    this.getProductType();
  }

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getProducts();

  }

  setPage(event: any) {
    event.preventDefault();
    this.getProducts();

  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.getProducts();

  }

  // PAGINATION ENDS

  getProducts() {
    this.spinner.show();
    this.productService.getProducts(`filtername='All'`, `page=${this.page}&sort=${this.sortColumnName},${this.sortOrder}&size=20`).subscribe(data => {
      this.spinner.hide();
      this.productList = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 3000);
    });
  }
  AddSelected() {
    this.selectedItemModel.show();
    this.selectedProductList;

  }
  openSelected() {

  }
  checked:boolean;
  onSelect($event, productlist: ProductsResponse) {
  
    this.checked=true;
    if ($event.target.checked != false) {
      this.pricebookreq = new PriceBookEntriesRequest();
      this.pricebookreq.listPrice = productlist.productPrice;
      this.pricebookreq.partCode = productlist.partCode;
      this.pricebookreq.priceBookId = this.id
      this.selectedProductList.push(this.pricebookreq);
    }
    else {
      this.selectedProductList = [];
    }

  }
  priceEntrieResponse: PriceBookEntriesResponse
  saveList() {
    this.spinner.show();
    this.pricebookservice.saveProductsById(this.id, this.selectedProductList).subscribe(data => {
      this.spinner.hide();
    this.selectedProductList.forEach(element=>{
      this.priceEntrieResponse=new PriceBookEntriesResponse();
     this.priceEntrieResponse.active=element.active;
      this.priceEntrieResponse.partCode=element.partCode;
      this.priceEntrieResponse.priceBookId=element.priceBookId;
      this.priceEntrieResponse.listPrice=element.listPrice;
     
      // this.priceEntrieResponse.description=element.
 
      this.onProductSelected.emit(this.priceEntrieResponse)
      
    }); 
    if(this.selectedProductList!=null){
      this.selectedProductList =[];
      this.priceEntrieResponse=new PriceBookEntriesResponse();
      this.message="Submitted Successfully"; 
      this.popupNgClass = 'alert alert-success center center-block'; 
      this.popupSuccessModel.show();
    }
  
    
    },err=>{
      this.spinner.hide();
      this.message=err.error.message; 
      this.popupNgClass = 'alert alert-danger center center-block'; 
      this.popupSuccessModel.show();
    })



  }
  cancelPricebookData() {
    this.onCancle.emit('cancled');
    this.selectedProductList =[];

  }
  cancelselectedData() {
     this.checked=false;
    this.selectedItemModel.hide();
    this.selectedProductList =[];
  
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      this.selectedItemModel.hide();
    }
  }
  // SORT
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(7).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[7] = true;
        }
        break;
    }
    this.getProducts();
  }



  searchProduct() {
   
    if(this.advancesearch.partCode==''){
     this.message="Please Enter valid Key for Search";
    //  this._global.alert({type:'alert',message:this.message});
    this.popupSuccessModel.show();
    }
    else{
      this.productList=[];
      this.spinner.show();
     
    
      this.productService.getSearchResult(this.advancesearch).subscribe(data => {
        this.spinner.hide();
        this.productList= data;
        // this.productList.last= true;
        // this.productList.first= true;
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
    this.getProducts();
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
