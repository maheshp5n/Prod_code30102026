import { Component, OnInit, EventEmitter, Output, ViewChild, Input } from '@angular/core';
import { PriceBookService } from '../../services/pricebook.service';
import { PriceBookEntriesResponse, PriceBookEntriesRequest } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-addproductlistpricebookntries',
  templateUrl: './addproductlistpricebookntries.component.html',
  styleUrls: ['./addproductlistpricebookntries.component.scss']
})
export class AddproductlistpricebookntriesComponent implements OnInit {
  selectedProductList: Array<PriceBookEntriesRequest> = [];
  i: number;
  id: number
  isCaseModelOpen: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  message:string;
  productPrices:number;
  priceEntrieResponse: PriceBookEntriesResponse=new PriceBookEntriesResponse();
pricENtriesreq:PriceBookEntriesRequest=new PriceBookEntriesRequest();
  @Output()
  onProductUpdated: EventEmitter<PriceBookEntriesResponse> = new EventEmitter<PriceBookEntriesResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('selectedItemModel', { static: false }) selectedItemModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @Input()
  set editEntityData(priceEntrieResponsen: PriceBookEntriesResponse) {
    this.priceEntrieResponse = priceEntrieResponsen;
  

  }
  get editEntityData(): PriceBookEntriesResponse {
    return this.priceEntrieResponse;
  }
  @Input()
 set productPrice(productPrice:number){
   this.productPrices=productPrice;
   console.log(this.productPrices);
 }
 get productPrice():number{
  console.log(this.productPrices);
   return this.productPrices;
 }

  constructor(private pricebookservice:PriceBookService,private spinner: NgxSpinnerService) { }
// productPrice:number;
  ngOnInit() {
  //  this.productPrice= this.priceEntrieResponse.listPrice
  }
 
  cancelselectedData() {
    this.onCancle.emit('cancled');
  }
  editentriesList() {
    console.log(this.priceEntrieResponse.listPrice.toLocaleString);
    if(this.priceEntrieResponse.listPrice>100000000){
      this.message="List Price Exceeds 10000000";
  this.popupSuccessModel.show();
  this.popupNgClass='alert alert-danger center center-block';

    }else{
      this.pricENtriesreq.id=this.priceEntrieResponse.id;
      this.pricENtriesreq.partCode=this.priceEntrieResponse.partCode;
      this.pricENtriesreq.active=this.priceEntrieResponse.active;
      this.pricENtriesreq.listPrice=this.priceEntrieResponse.listPrice;
       this.pricENtriesreq.priceBookId=this.priceEntrieResponse.priceBookId;
      this.spinner.show();
      this.pricebookservice.updatePicebookentries(this.pricENtriesreq).subscribe(data => {
    
        this.spinner.hide();
       this.priceEntrieResponse.active=data.active;
       this.priceEntrieResponse.partCode=data.partCode;
       this.priceEntrieResponse.listPrice=data.listPrice;
       this.priceEntrieResponse.priceBookId=data.priceBookId
       this.onProductUpdated.emit(this.priceEntrieResponse);
        this.message="Submitted Successfully"; 
        this.popupNgClass = 'alert alert-success center center-block'; 
        this.popupSuccessModel.show();
      
    
      
      });
  
  
  
    }
   
  }


  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      // this.selectedItemModel.hide();
    }
  }

}
