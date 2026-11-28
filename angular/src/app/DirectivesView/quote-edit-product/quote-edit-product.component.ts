import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { QuotePriceBook, QuoteLineItemRequest, QuoteLineItemResponse } from '../../services/model';
import { QuoteService } from '../../services/Quote.services';
import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';

@Component({
  selector: 'app-quote-edit-product',
  templateUrl: './quote-edit-product.component.html',
  styleUrls: ['./quote-edit-product.component.css']
})
export class QuoteEditProductComponent implements OnInit {
  @ViewChild('editSelectedProducts') editSelectedProducts: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('editAllProducts') editAllProducts: ModalDirective;

  constructor(private spinner: NgxSpinnerService, private _quoteService: QuoteService) {
  }

  @Input()
  set quoteId(_quoteId: number) {
    this._quoteId = _quoteId;
  }

  get quoteId(): number {
    return this._quoteId;
  }

  @Input()
  set isEditAll(isEditAll: boolean) {
    this._isEditAll = isEditAll;
  }

  get isEditAll(): boolean {
    return this._isEditAll;
  }

  @Input()
  set editBusinessDomainId(businessDomainId: number) {
    this.businessDomainId = businessDomainId;
  }

  get editBusinessDomainId(): number {
    return this.businessDomainId;
  }

  @Input()
  set editQuoteLineItem(_quoteLineItemRequest: QuoteLineItemRequest) {
    this.quoteLineItemRequest = _quoteLineItemRequest;
  }

  get editQuoteLineItem(): QuoteLineItemRequest {
    return this.quoteLineItemRequest;
  }
  quoteLineItemRequestArray: Array<QuoteLineItemRequest> = [];

  @Input()
  set editAllQuoteLineItems(_quoteLineItemRequestArray: Array<QuoteLineItemResponse>) {
    // this.quoteLineItemRequestArrayEdit = _quoteLineItemRequestArray;


  }

  // get editAllQuoteLineItems(): Array<QuoteLineItemResponse> {
  //   return this.quoteLineItemRequestArrayEdit;
  // }

  quotePriceBook: QuotePriceBook = new QuotePriceBook();
  _isEditAll: boolean;
  viewEditPriceItems: boolean;
  searchitem: string = '';
  i: number;
  serialNumber: number;
  message: string;
  quoteLineItemRequestArrayEdit: Array<QuoteLineItemRequest> = [];
  quoteLineItemRequestEdit: QuoteLineItemRequest = new QuoteLineItemRequest();
  priceBookId: number;
  quoteLineItemRequest: QuoteLineItemRequest = new QuoteLineItemRequest();
  _quoteId: number;
  businessDomainId: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  doneFlag: boolean;

  @Output()
  headingPrice: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onEditProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onEditProductCreated: EventEmitter<QuoteLineItemResponse> = new EventEmitter<QuoteLineItemResponse>();

  @Output()
  _onEditAllProductCreated: EventEmitter<Array<QuoteLineItemResponse>> = new EventEmitter<Array<QuoteLineItemResponse>>();

  @Output()
  headingEditPrice: EventEmitter<string> = new EventEmitter<string>();

  newArray: any = [];

  ngOnInit() {
    this.searchitem = '';
    this.getQuoteLineItemsList(this.quoteId);
  }

  canceleditAllData() {
    this._onEditProCancle.emit('cancel');
    this.quoteLineItemRequestArray = [];
    // this._isEditAll = false;
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this._onEditProCancle.emit('cancel');
    }
  }

  editApiQuoteLineItem() {
    
    if(this.quoteLineItemRequest.quantity!=0){
  this.spinner.show();
    
    // this.priceBookId = this.quoteLineItemRequest.priceBookId;
    this._quoteService.putQuoteLineItemEdit(this.quoteLineItemRequest, this.priceBookId
      , this._quoteId, 1).subscribe(data => {
        this.message = 'Product editted successfully !';
        this.popupNgClass = 'alert alert-success center center-block';
        this._onEditProductCreated.emit(data);
        this.spinner.hide();
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
    else{
      this.message="Quantity Can Not be 0";
      this.popupSuccessModel.show();
    }
  }

  // getQuoteLineItemsList(id: number) {
  //   this.spinner.show();
  //   this._quoteService.getQuoteLineItemsList(this._quoteId).subscribe(data => {
  //     for (const i in data) {
  //       const tempReq = new QuoteLineItemRequest();
  //       tempReq.id = data[i].id;
  //       tempReq.listPrice = data[i].listPrice;
  //       tempReq.price = data[i].salePrice;
  //       tempReq.quantity = data[i].quantity;
  //       tempReq.priceBookEntryId = data[i].priceBookEntryId;
  //       tempReq.partCode = data[i].productPartCode;
  //       this.quoteLineItemRequestArray.push(tempReq);
  //       this.priceBookId = data[i].priceBookId;
  //     }
  //     this.spinner.hide();
  //   });
  // }

  cancelDataEditProduct() {
    this._onEditProCancle.emit('cancel');
  }
quantityzero:boolean
  editAll() {
    for( const li of this.quoteLineItemRequestArray){
     
      if(li.quantity==0){
        this.quantityzero=true;
        break;
      }    
      else{
        this.quantityzero=false;
      } 
    }
    if(this.quantityzero==true){
      this.message="Quantity can not be 0";
        this.popupSuccessModel.show();
    }
   else{
    this.spinner.show();
    this._quoteService.putQuoteLineItemsEditAll(this.priceBookId
      , this._quoteId, this.businessDomainId, this.quoteLineItemRequestArray).subscribe(data => {
        this.message = 'Product editted successfully !';
        this.popupNgClass = 'alert alert-success center center-block';

        this._onEditAllProductCreated.emit(data);
        this.spinner.hide();
        this.popupSuccessModel.show();
        // this._onEditProCancle.emit('cancel');
        this._onEditAllProductCreated.emit(data);
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    this.quoteLineItemRequestArrayEdit = [];
    this.quoteLineItemRequestArray = [];
   }
 
    // this._isEditAll = false;
  }

  // getQuoteLineItemsList(id: number) {
  //   this.spinner.show();
  //   this._quoteService.getQuoteLineItemsList(this._quoteId).subscribe(data => {
  //     for (const i in data) {
  //       const tempReq = new QuoteLineItemRequest();
  //       tempReq.id = data[i].id;
  //       tempReq.listPrice = data[i].listPrice;
  //       tempReq.price = data[i].listPrice;
  //       tempReq.quantity = data[i].quantity;
  //       tempReq.priceBookEntryId = data[i].priceBookEntryId;
  //       tempReq.partCode = data[i].productPartCode;
  //       this.quoteLineItemRequestArray.push(tempReq);
  //       this.priceBookId = data[i].priceBookId;
  //     }
  //     this.doneFlag = true;
  //     if (this.doneFlag) {
  //       this.spinner.hide();
  //     }
  //     this.doneFlag = false;
  //   });
  // }

  getQuoteLineItemsList(id: number) {
    this.spinner.show();
    this.quoteLineItemRequestArray = [];
    this._quoteService.getQuoteLineItemsList(this._quoteId).subscribe(data => {
      // tslint:disable-next-line: forin
      for (const i in data) {
        const tempReq = new QuoteLineItemRequest();
        tempReq.id = data[i].id;
        tempReq.listPrice = data[i].listPrice;
        tempReq.price = data[i].price;
        tempReq.quantity = data[i].quantity;
        tempReq.priceBookEntryId = data[i].priceBookEntryId;
        tempReq.partCode = data[i].productPartCode;
        this.quoteLineItemRequestArray.push(tempReq);
        this.priceBookId = data[i].priceBookId;
      }
      this.doneFlag = true;
      if (this.doneFlag) {
        this.spinner.hide();
      }
      this.doneFlag = false;
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
    // shagufta
    onlyNumbers(key): any {
      const charCode = (key.which) ? key.which : key.keyCode;
      console.log("Charcode"+charCode);
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
      }
      else if(key.target.value.length>4){
        this.message="More than 5 digits can not be accepted";
        this.popupSuccessModel.show();
        return false;
      }
      return true;
  
    }
      // shagufta
   onlyNumbersForRs(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    console.log("Charcode is=" +charCode);
    if(charCode>31 && charCode==46){
      return true; 
     }
    //  else if(this.LimteExceed==true){
    //    return false;
    //  }
    else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
      return false;
    }
   
  }
  LimteExceed:boolean;
  onKey(event):any {
     // without type info
     console.log(event.target.value.length)
     if(event.target.value.length>4){
       this.LimteExceed=true;
     
     }
     else{
       this.LimteExceed=false;
     }
  }


}
