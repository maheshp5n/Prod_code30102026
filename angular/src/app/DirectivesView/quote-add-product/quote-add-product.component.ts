import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { PriceBookForList, PriceBookEntriesResponse, QuoteLineItemResponse, QuoteLineItemRequest, ApprovalStepRequest } from '../../services/model';
import { PriceBookService } from '../../services/pricebook.service';
import { QuoteService } from '../../services/Quote.services';
import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';
// QuotePriceBook,
@Component({
  selector: 'app-quote-add-product',
  templateUrl: './quote-add-product.component.html',
  styleUrls: ['./quote-add-product.component.css']
})
export class QuoteAddProductComponent implements OnInit {
  @ViewChild('addPriceItems', { static: false }) addPriceItems: ModalDirective;
  @ViewChild('editSelectedProducts', { static: false }) editSelectedProducts: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  // @ViewChild('editAllProducts', { static: false }) editAllProducts: ModalDirective;

  constructor(private spinner: NgxSpinnerService, private _priceBookService: PriceBookService
    , private _quoteService: QuoteService) {
  }

  @Input()
  set viewAddPriceBook(viewAddPriceBook: boolean) {
    this._viewAddPriceBook = viewAddPriceBook;
  }

  get viewAddPriceBook(): boolean {
    return this._viewAddPriceBook;
  }

  @Input()
  set quoteId(_quoteId: number) {
    this._quoteId = _quoteId;
  }

  get quoteId(): number {
    return this._quoteId;
  }
  @Input()
  set approvelreq(approveREQ:boolean){
    this.approveREQ=approveREQ;
    console.log(approveREQ);
  }
  get approvelreq(): boolean {
    return this.approveREQ;
  }

  priceBookForList: Array<PriceBookForList> = [];
  quotePriceBook: QuoteLineItemRequest = new QuoteLineItemRequest();
  _viewAddPriceBook: boolean;
  viewEditPriceItems: boolean;
  searchitem: string = '';
  public page: number = 0;
  approveREQ:boolean;
  first: boolean;
  last: boolean;
  size: number;
  sortColumn: number = 12;
  sortColumnName: string = 'createdOn'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false, false];
  i: number;
  serialNumber: number;
  message: string;
  priceBookEntries: Array<PriceBookEntriesResponse>;
  quoteLineItemRequestArray: Array<QuoteLineItemRequest> = [];

  quoteLineItemRequest: QuoteLineItemRequest = new QuoteLineItemRequest();
  quoteLineItemRequestArrayEdit: Array<QuoteLineItemRequest> = [];
  quoteLineItemRequestEdit: QuoteLineItemRequest = new QuoteLineItemRequest();
  priceBookId: number;
  _quoteId: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  @Output()
  _onAddProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onAddProductCreated: EventEmitter<Array<QuoteLineItemResponse>> = new EventEmitter<Array<QuoteLineItemResponse>>();

  @Output()
  headingPrice: EventEmitter<string> = new EventEmitter<string>();
  newArray: any = [];

  ngOnInit() {
    this.searchitem = '';
    this.getAllPriceBookList();
  }

  // SORT
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
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
    }
    this.getPriceBookEntries(this.quotePriceBook.id, this.sortColumnName, this.sortOrder);
  }
  // SORT ENDS

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getPriceBookEntries(this.quotePriceBook.id, this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();
    this.getPriceBookEntries(this.quotePriceBook.id, this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    // if (!this.recently) {
    //   this.getContacts(this.sortColumnName, this.sortOrder);
    // } else {
    //   this.getRecentContacts(this.sortColumnName, this.sortOrder);
    // }
    this.getPriceBookEntries(this.quotePriceBook.id, this.sortColumnName, this.sortOrder);
  }

  // PAGINATION ENDS

  getAllPriceBookList() {
    this.spinner.show();
    this._priceBookService.getAllPriceBookList().subscribe(data => {
      this.priceBookForList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }

  cancelAddPriceBook() {
    this._onAddProCancle.emit('cancel');
  }

  postPriceBook() {
    // this.headingPrice.emit('Add Products');
    if (this.quotePriceBook.id !== 0) {
      this.setOrder('createdDate', 4);
      this._viewAddPriceBook = false;
      this.addPriceItems.show();
    } else {
      this.message = 'Please choose a price book';
      this.popupSuccessModel.show();
    }
  }

  getPriceBookEntries(id: number, column: string, order: string) {
    this.spinner.show();
    this._priceBookService.getpriceBookEntriesList(this.quotePriceBook.id).subscribe(data=>{
      this.priceBookEntries=data;
      this.spinner.hide();
      this.page=0;
      this.size=data.length;
      this.first=true;
      this.last=true;
    },err=>{
      this.spinner.hide();
      this.message = err.error.message;
    });
    /*
    this._priceBookService.getpriceBookEntries(this.quotePriceBook.id, this.page, column, order).subscribe(data => {
      this.priceBookEntries = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
    });*/
  }

  getCheckboxValues(ev, data) {
    const obj = {
      'order': data
    };
    if (ev.target.checked) {
      this.newArray.push(obj);
    } else {
      const removeIndex = this.newArray.findIndex(itm => itm.order === data);
      if (removeIndex !== -1) {
        this.newArray.splice(removeIndex, 1);
      }
    }
  }

  setQuoteLineRequest() {
    for (const t of this.newArray) {
      let temp: any = [];
      temp = t;
      // tslint:disable-next-line: forin
      for (const o in temp) {
        this.quoteLineItemRequest.id = temp[o].id;
        this.quoteLineItemRequest.partCode = temp[o].partCode;
        this.quoteLineItemRequest.listPrice = temp[o].listPrice;
        this.quoteLineItemRequest.price = temp[o].listPrice;
        this.quoteLineItemRequest.priceBookEntryId = temp[o].priceBookId;
        this.quoteLineItemRequestArray.push(this.quoteLineItemRequest);
        this.quoteLineItemRequest = new QuoteLineItemRequest();
      }
    }
  }

  // saveList() {
  //   let flag = true;
  //   for (const t of this.newArray) {
  //     let temp: any = [];
  //     temp = t;
  //     if (flag) {
  //       for (const o in temp) {
  //         let index1 = 0;
  //         for (const p of this.quoteLineItemRequestArray) {
  //           let index2 = 0;
  //           if (index2 === index1) {
  //             this.quoteLineItemRequest.id = temp[o].id;
  //             this.quoteLineItemRequest.partCode = temp[o].partCode;
  //             this.quoteLineItemRequest.price = p.price;
  //             this.quoteLineItemRequest.quantity = p.quantity;
  //             this.quoteLineItemRequest.priceBookEntryId = temp[o].priceBookId;
  //             this.priceBookId = this.quoteLineItemRequest.priceBookEntryId;
  //             this.quoteLineItemRequestArrayEdit.push(this.quoteLineItemRequest);
  //             this.quoteLineItemRequest = new QuoteLineItemRequest();
  //           }
  //           index2++;
  //         }
  //         index1++;
  //       }
  //     }
  //     flag = false;
  //   }
  //   this.spinner.show();
  //   this._quoteService.postQuoteLineItem(this.quoteLineItemRequestArrayEdit, this.priceBookId
  //     , this._quoteId, 1).subscribe(data => {
  //       this.spinner.hide();
  //     });
  //   this.quoteLineItemRequestArrayEdit = [];
  //   this.quoteLineItemRequestArray = [];
  // }
quantityZro:boolean;
  saveList() {
    if(this.approveREQ==true){
      const apreq=new ApprovalStepRequest()
    }
    for (const p of this.quoteLineItemRequestArray) {
      this.quoteLineItemRequest = p;
      if(p.quantity!=0){
        this.quantityZro=false;
        this.quoteLineItemRequest.priceBookEntryId = p.id;
        this.priceBookId = this.quotePriceBook.id;
        this.quoteLineItemRequest.priceBookId = this.priceBookId;
        this.quoteLineItemRequestArrayEdit.push(this.quoteLineItemRequest);
        this.quoteLineItemRequest = new QuoteLineItemRequest();
      }
      else{
        this.quantityZro=true;
        this.message="Quantity can not be 0";
        this.popupSuccessModel.show();
        break;
      }
      
     
     
    }
    if(this.quantityZro==true){

    }
    else{
      this.spinner.show();
      this._quoteService.postQuoteLineItem(this.quoteLineItemRequestArrayEdit, this.priceBookId
        , this._quoteId, 1).subscribe(data => {
          this.spinner.hide();
          this.message = 'Product added successfully !';
          this.popupNgClass = 'alert alert-success center center-block';
          this._onAddProductCreated.emit(data);
          this.popupSuccessModel.show();
        },
          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          });
      this.quoteLineItemRequestArrayEdit = [];
      this.quoteLineItemRequestArray = [];
    }
   
  }

  cancelAddPriceItems() {
    this.addPriceItems.hide();
    this._onAddProCancle.emit('cancel');
  }

  cancelselectedData() {
    this.addPriceItems.show();
    this.editSelectedProducts.hide();
    this.quoteLineItemRequestArray = [];
  }
  // canceleditAllData() {
  //   this.addPriceItems.show();
  //   this.editAllProducts.hide();
  //   this.quoteLineItemRequestArray = [];
  // }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this._onAddProCancle.emit('cancel');
    }
  }

  addProducts() {
    this.serialNumber = 0;
    this.setQuoteLineRequest();
    this.addPriceItems.hide();
    this.editSelectedProducts.show();
  }
  // shagufta
  onlyNumbers(key): any {
    console.log("Only numbers");
    const charCode = (key.which) ? key.which : key.keyCode;
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
    onlyNumbersForRs(key): any {

      const charCode = (key.which) ? key.which : key.keyCode;
      console.log("Only number for rs function");
      if(charCode>31 && charCode==46){
        return true; 
       }
      else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
        return false;
      }
      else if(key.target.value.length>5){
        this.message="More than 5 digits can not be accepted";
          this.popupSuccessModel.show();
       return false;
     }
    }

    // LimteExceed:boolean=false;
    // onKey(event):any {
    //    console.log("change function")
    //    console.log(event.target.value);
    //    if(event.target.value!=''){
    //     if( event.target.value.length>3){
    //       this.LimteExceed=true;
         
    //     }
    //     else{
    //       this.LimteExceed=false;
    //     }
    //    }
    //    else{
    //      this.LimteExceed=false;
    //    }
     
    //   }
    
}
