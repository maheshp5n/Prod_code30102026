import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { Loginservices } from '../../services/login.services';
import { PriceBookEntriesResponse, ProductsResponse, Product, PriceBookEntriesRequest, ActionHistoryObjectAndId } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PriceBookService } from '../../services/pricebook.service';
import { ProductsService } from '../../services/products.services';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-pricebookproductsdetails',
  templateUrl: './pricebookproductsdetails.component.html',
  styleUrls: ['./pricebookproductsdetails.component.scss']
})
export class PricebookproductsdetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @ViewChild('createpriceBookModel',{static:false}) createpriceBookModel: ModalDirective;
  @ViewChild('productListModel',{static:false}) productListModel:ModalDirective;
  @ViewChild('selectedItemModel',{static:false}) selectedItemModel:ModalDirective;
  id:number;
  partCode:string
  ngClassDetails:string='active';
  ngClassReleated:string='';
  vDetails:boolean=true;
   isEditMode:boolean=false;
   heading : string;
   edit:boolean=false;
   CREATE_ACCOUNT_HEADING:string= 'Create Price Book';
   EDIT_ACCOUNT_HEADING:string= 'Edit Price Book';
  message:string="";
  loginData:any;
  totalPagesloc:any;
  first: boolean;
  last: boolean;
  size:number;
  totalPage:any;
  serialNumber: number;
  page: number = 0;
  entrieTable:Array<PriceBookEntriesResponse>=[];
  totalEntries:number;
  sortColumn: number = 7;
  sortColumnName: string = 'createddate';
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  isPriceBook: boolean;
  productList:Array<ProductsResponse>
  product: ProductsResponse = new ProductsResponse();
  newProduct: Product = new Product();
  productById:ProductsResponse=new ProductsResponse();
  isProductOpen:boolean;
  priceResponse: PriceBookEntriesResponse=new PriceBookEntriesResponse();
  pricENtriesreq:PriceBookEntriesRequest=new PriceBookEntriesRequest();
  selectedProductList:Array<PriceBookEntriesResponse>;
  isCaseModelOpen:boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  // @Output()
  // onProductSelected: EventEmitter<PriceBookEntriesResponse> = new EventEmitter<PriceBookEntriesResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  addressDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  };
  infoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  sysinfoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  // priceResponse:PriceBookResponse=new PriceBookResponse();
  // priceResp: PriceBookResponse = new PriceBookResponse();
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
    constructor(private loginservice:Loginservices ,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,private pricebooservice:PriceBookService,private productService:ProductsService) { 
      this._activatedRoute.params.subscribe(pram => {
        this.id = pram['id'];
        this.actionHistoryObjectAndId.objectId = '' + this.id;
      this.actionHistoryObjectAndId.objectName = 'PriceBookEntries';
      });
    }
    rights(object,right:string):boolean{
      return this.loginservice.isUserValidFor(object,right);
    }

  ngOnInit() {
    this.editpriceEntriesModelshow();
  }
  changeAddressDisplay(){
    this.addressDisplay.open=!this.addressDisplay.open
    if(this.addressDisplay.open){
      this.addressDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.addressDisplay.angleIcon='fa fa-angle-right';
    }
  }
  changeInfoDisplay(){
    this.infoDisplay.open=!this.infoDisplay.open
    if(this.infoDisplay.open){
      this.infoDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.infoDisplay.angleIcon='fa fa-angle-right';
    }

  }
  sysInfoDisplay(){
    this.sysinfoDisplay.open=!this.sysinfoDisplay.open
    if(this.sysinfoDisplay.open){
      this.sysinfoDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.sysinfoDisplay.angleIcon='fa fa-angle-right';
    }
    
  }
  subTabChange(tabName:string){
    if(tabName=='details'){
      this.ngClassDetails='active';
      this.ngClassReleated='';
      this.vDetails=true;
    }
    else{
      this.ngClassDetails='';
      this.ngClassReleated='active';
      this.vDetails=false;
      // this.getEntries();
    }
    return false;
  }
  productPrices:number;
  editpriceEntriesModelshow(){
   this.spinner.show()
    this.pricebooservice.getDetailByentriesId(this.id).subscribe(data=>{
      this.spinner.hide();
     this.priceResponse=data;
    //  this.productPrices= data.partCodePrice
     console.log(this.priceResponse);
     
    })
   

  }
  cancelPricebookData(){
    
  }

}
