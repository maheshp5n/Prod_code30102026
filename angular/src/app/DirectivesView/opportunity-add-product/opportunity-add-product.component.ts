import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { PriceBookForList, OpportunityPriceBook, PriceBookEntriesResponse, OppertunityLineItemRequest, OppertunityLineItemResponse } from './../../services/model';
import { PriceBookService } from './../../services/pricebook.service';
import { OpportunityService } from '../../services/Opportunity.services';
import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';

@Component({
  selector: 'app-opportunity-add-product',
  templateUrl: './opportunity-add-product.component.html',
  styleUrls: ['./opportunity-add-product.component.css']
})
export class OpportunityAddProductComponent implements OnInit {
  @ViewChild('addPriceItems') addPriceItems: ModalDirective;
  @ViewChild('editSelectedProducts') editSelectedProducts: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  // @ViewChild('editAllProducts', { static: false }) editAllProducts: ModalDirective;

  constructor(private spinner: NgxSpinnerService, private _priceBookService: PriceBookService
    , private _opportunityService: OpportunityService) {
  }

  @Input()
  set viewAddPriceBook(viewAddPriceBook: boolean) {
    this._viewAddPriceBook = viewAddPriceBook;
  }

  get viewAddPriceBook(): boolean {
    return this._viewAddPriceBook;
  }

  @Input()
  set opportunityId(_oppertunityId: number) {
    this._oppertunityId = _oppertunityId;
  }

  get opportunityId(): number {
    return this._oppertunityId;
  }

  priceBookForList: Array<PriceBookForList> = [];
  opportunityPriceBook: OpportunityPriceBook = new OpportunityPriceBook();
  _viewAddPriceBook: boolean;
  viewEditPriceItems: boolean;
  searchitem: string = '';
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  sortColumn: number = 12;
  sortColumnName: string = 'createdOn'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false];
  i: number;
  serialNumber: number;
  message: string;
  priceBookEntries: Array<PriceBookEntriesResponse>=[];
  oppertunityLineItemRequestArray: Array<OppertunityLineItemRequest> = [];

  oppertunityLineItemRequest: OppertunityLineItemRequest = new OppertunityLineItemRequest();
  oppertunityLineItemRequestArrayEdit: Array<OppertunityLineItemRequest> = [];
  oppertunityLineItemRequestEdit: OppertunityLineItemRequest = new OppertunityLineItemRequest();
  priceBookId: number;
  _oppertunityId: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  @Output()
  _onAddProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onAddProductCreated: EventEmitter<Array<OppertunityLineItemResponse>> = new EventEmitter<Array<OppertunityLineItemResponse>>();

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
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(4).fill(false);
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
    this.getPriceBookEntries(this.opportunityPriceBook.id, this.sortColumnName, this.sortOrder);
  }
  // SORT ENDS

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getPriceBookEntries(this.opportunityPriceBook.id, this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();
    this.getPriceBookEntries(this.opportunityPriceBook.id, this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    // if (!this.recently) {
    //   this.getContacts(this.sortColumnName, this.sortOrder);
    // } else {
    //   this.getRecentContacts(this.sortColumnName, this.sortOrder);
    // }
    this.getPriceBookEntries(this.opportunityPriceBook.id, this.sortColumnName, this.sortOrder);
  }
  setPageNextInt(){
    this.page=this.page+1;

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
    this.setOrder('createdDate', 4);
    this._viewAddPriceBook = false;
    this.addPriceItems.show();
  }

  getPriceBookEntries(id: number, column: string, order: string) {


    this.spinner.show();
    this._priceBookService.getpriceBookEntriesList(this.opportunityPriceBook.id).subscribe(data=>{
      this.priceBookEntries=data;
      this.first=true;
      this.last=true;
      this.page=0;
      this.size=data.length;
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this.message = error.error.message;
    });
/*
    this._priceBookService.getpriceBookEntries(this.opportunityPriceBook.id, this.page, column, 'desc').subscribe(data => {
      data.content.forEach(d=>{
        this.priceBookEntries.push(d);
      });
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
      if(!data.last){
        this.setPageNextInt();
        this.getPriceBookEntries(this.opportunityPriceBook.id,column,'desc');
      }
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

  setOpportunityLineRequest() {
    for (const t of this.newArray) {
      let temp: any = [];
      temp = t;
      // tslint:disable-next-line: forin
      for (const o in temp) {
        this.oppertunityLineItemRequest.id = temp[o].id;
        this.oppertunityLineItemRequest.partCode = temp[o].partCode;
        this.oppertunityLineItemRequest.listPrice = temp[o].listPrice;
        this.oppertunityLineItemRequest.price = temp[o].listPrice;
        this.oppertunityLineItemRequest.priceBookEntryId = temp[o].priceBookId;
        this.oppertunityLineItemRequestArray.push(this.oppertunityLineItemRequest);
        this.oppertunityLineItemRequest = new OppertunityLineItemRequest();
      }
    }
  }

  addProducts() {
    this.serialNumber = 0;
    this.setOpportunityLineRequest();
    this.addPriceItems.hide();
    this.editSelectedProducts.show();
  }

  // saveList() {
  //   let flag = true;
  //   for (const t of this.newArray) {
  //     let temp: any = [];
  //     temp = t;
  //     if (flag) {
  //       for (const o in temp) {
  //         let index1 = 0;
  //         for (const p of this.oppertunityLineItemRequestArray) {
  //           let index2 = 0;
  //           if (index2 === index1) {
  //             this.oppertunityLineItemRequest.id = temp[o].id;
  //             this.oppertunityLineItemRequest.partCode = temp[o].partCode;
  //             this.oppertunityLineItemRequest.price = p.price;
  //             this.oppertunityLineItemRequest.quantity = p.quantity;
  //             this.oppertunityLineItemRequest.priceBookEntryId = temp[o].priceBookId;
  //             this.priceBookId = this.oppertunityLineItemRequest.priceBookEntryId;
  //             this.oppertunityLineItemRequestArrayEdit.push(this.oppertunityLineItemRequest);
  //             this.oppertunityLineItemRequest = new OppertunityLineItemRequest();
  //           }
  //           index2++;
  //         }
  //         index1++;
  //       }
  //     }
  //     flag = false;
  //   }
  //   this.spinner.show();
  //   this._opportunityService.postOpportunityLineItem(this.oppertunityLineItemRequestArrayEdit, this.priceBookId
  //     , this._oppertunityId, 1).subscribe(data => {
  //       this.spinner.hide();
  //     });
  //   this.oppertunityLineItemRequestArrayEdit = [];
  //   this.oppertunityLineItemRequestArray = [];
  // }
quantityZro:boolean;
  saveList() {
    for (const p of this.oppertunityLineItemRequestArray) {
      this.oppertunityLineItemRequest = p;
      if(p.quantity!=0){

        this.quantityZro=false;
        this.oppertunityLineItemRequest.priceBookEntryId = p.id;
      this.priceBookId = this.opportunityPriceBook.id;
      this.oppertunityLineItemRequestArrayEdit.push(this.oppertunityLineItemRequest);
      this.oppertunityLineItemRequest = new OppertunityLineItemRequest();
      console.log(this.oppertunityLineItemRequestArrayEdit)
      }else{
        this.quantityZro=true;
        break;
        
      }
     
    }
    if(this.quantityZro===true){
      this.message="Quantity can not be 0";
        this.popupSuccessModel.show()
    }
    else{
      this.spinner.show();
      this._opportunityService.postOpportunityLineItem(this.oppertunityLineItemRequestArrayEdit, this.priceBookId
        , this._oppertunityId, 1).subscribe(data => {
          this.spinner.hide();
          this.message = 'Product added successfully !';
          this.popupNgClass = 'alert alert-success center center-block';
          this._onAddProductCreated.emit(data);
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      this.oppertunityLineItemRequestArrayEdit = [];
      this.oppertunityLineItemRequestArray = [];
    }
   
  }

  cancelAddPriceItems() {
    this.addPriceItems.hide();
    this._onAddProCancle.emit('cancel');

  }

  cancelselectedData() {
    this.addPriceItems.show();
    this.editSelectedProducts.hide();
    this.oppertunityLineItemRequestArray = [];
  }
  // canceleditAllData() {
  //   this.addPriceItems.show();
  //   this.editAllProducts.hide();
  //   this.oppertunityLineItemRequestArray = [];
  // }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this._onAddProCancle.emit('cancel');
    }
  }
  // shagufta
  onlyNumbers(key): any {
    const charCode = (key.which) ? key.which : key.keyCode;
    console.log("Charcode"+charCode);
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    if(key.target.value.length>4){
      this.message="More then 5 digits can not be accepted";
      this.popupSuccessModel.show();
      return false;
    }
    return true;

  }
   // shagufta
   onlyNumbersForRs(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    if(charCode>31 && charCode==46){
      return true; 
     }
    else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
      return false;
    }
   
  }
  // LimteExceed:boolean;
  // onKey(event):any {
  //    if(event.target.value.length>4){
  //      this.LimteExceed=true;
      
  //    }
  //    else{
  //      this.LimteExceed=false;
  //    }
  //   }
}
