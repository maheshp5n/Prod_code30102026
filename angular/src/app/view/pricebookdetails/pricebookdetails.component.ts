import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { PriceBookService } from '../../services/pricebook.service';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { PriceBookResponse, PriceBookEntriesResponse, ProductsResponse, PriceBookEntriesRequest, Product, ActionHistoryObjectAndId } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ProductsService } from '../../services/products.services';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-pricebookdetails',
  templateUrl: './pricebookdetails.component.html',
  styleUrls: ['./pricebookdetails.component.scss']
})
export class PricebookdetailsComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createpriceBookModel') createpriceBookModel: ModalDirective;
  @ViewChild('productListModel') productListModel:ModalDirective;
  @ViewChild('selectedItemModel') selectedItemModel:ModalDirective;
id:number;
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
priceEntrieResponse: PriceBookEntriesResponse=new PriceBookEntriesResponse();
pricENtriesreq:PriceBookEntriesRequest=new PriceBookEntriesRequest();
selectedProductList:Array<PriceBookEntriesResponse>;
isCaseModelOpen:boolean;
popupNgClass: string = 'alert alert-danger center center-block';
// priceBookColItem
priceBookCol=[]
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
_cFlag:string;
priceResponse:PriceBookResponse=new PriceBookResponse();
priceResp: PriceBookResponse = new PriceBookResponse();
actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private loginservice:Loginservices ,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,private pricebooservice:PriceBookService,private productService:ProductsService) { 
    this._activatedRoute.params.subscribe(pram => {
      this.id = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.id;
      this.actionHistoryObjectAndId.objectName = 'PriceBook';
      this.priceBookCol=["S.No","Part Code", "List Price",	"Product Type",	"Description", "Active","Action"];
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  ngOnInit() {
    this.priceEntrieResponse=new PriceBookEntriesResponse();
    this.getPriceBookDetails();

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
      this.getPriceBookDetails();
    }
    else{
      this.ngClassDetails='';
      this.ngClassReleated='active';
      this.vDetails=false;
      this.getEntries();
    }
    return false;
  }
  getPriceBookDetails(){
    this.spinner.show();
    this.pricebooservice.getDetailById(this.id).subscribe(data=>{
      this.spinner.hide()
      this.priceResponse=data;
    },Error=>{
      this.spinner.hide();
      this.message=Error.error.message;
      

    })
  }
  createpricebookModelshow(res:PriceBookResponse){
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING;
    this.createpriceBookModel.show();
    this.spinner.show();
    this.pricebooservice.getDetailById(this.id).subscribe(data=>{
      this.spinner.hide()
      this.priceResp=data;
    },Error=>{
      this.spinner.hide();
      this.message=Error.error.message;
      

    })
  }
  cancelPricebookData(){
    this.priceResp=new PriceBookResponse();
    this.createpriceBookModel.hide();
  }

  getEntries(){
    this.spinner.show();
    this.size=5;
  this.pricebooservice.getAllEntries(this.page,this.id,this.size).subscribe(data=>{
    this.spinner.hide();
  this.entrieTable=data.content;
  this.totalEntries=data.totalElements;
  this.first = data.first;
  this.last = data.last;
  this.size = data.size;
//  this.serialNumber = (((this.page) * this.size));
  this.serialNumber=((this.page) * data.size);
  
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();

    })
  }
  
  getAllProducts(){
    this.spinner.show();
    this.productService.getProducts().subscribe(data => {
      this.productList = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + ' # ' + err.error.message;
      this.popupSuccessModel.show();
     
    });

  }
  addProductsModelshow(){
    this.spinner.show();
    this.heading="Add Products"
    this.productListModel.show();
    this.spinner.hide()
   }
  cancelproductListModel(){
      this.productListModel.hide();

    
  }
  cancelselectedData() {

    this.selectedItemModel.hide();
    // this.selectedProductList =[];

  }
  onProductSelected($event){
  
  this.entrieTable.unshift($event);
  this.getEntries();
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
      this.getEntries();
  }

  setPage(event: any) {
    event.preventDefault();
   this.getEntries();
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
  this.getEntries();
  }
  onpricebookCreated($event:PriceBookResponse){
    this.priceResponse=$event;
    this.getEntries();
    this.getPriceBookDetails();
  }
  productPrices:number;
  editpriceEntriesModelshow(partCode:PriceBookEntriesResponse){
   this.spinner.show()
   this.heading="Edit Products"
   this.selectedItemModel.show();
    this.pricebooservice.getDetailByentriesId(partCode.id).subscribe(data=>{
    this.spinner.hide();
    this.priceEntrieResponse=data;
    this.productPrices= data.partCodePrice;
     
    })
   

  }
  //edit Pricebookentries

  

  onProductUpdated($event:PriceBookEntriesResponse){
    this.entrieTable.unshift($event)
    this.getEntries();
  }
  popupClose(){
    this.popupSuccessModel.hide();
    this.productListModel.hide();
  }

}
