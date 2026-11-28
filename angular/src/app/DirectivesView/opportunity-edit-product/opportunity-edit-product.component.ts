import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { OpportunityPriceBook, OppertunityLineItemRequest, OppertunityLineItemResponse } from '../../services/model';
import { OpportunityService } from '../../services/Opportunity.services';
import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';

@Component({
  selector: 'app-opportunity-edit-product',
  templateUrl: './opportunity-edit-product.component.html',
  styleUrls: ['./opportunity-edit-product.component.css']
})
export class OpportunityEditProductComponent implements OnInit {
  @ViewChild('editSelectedProducts') editSelectedProducts: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('editAllProducts') editAllProducts: ModalDirective;

  constructor(private spinner: NgxSpinnerService
    , private _opportunityService: OpportunityService) {
  }

  @Input()
  set opportunityId(_oppertunityId: number) {
    this._oppertunityId = _oppertunityId;
  }

  get opportunityId(): number {
    return this._oppertunityId;
  }

  @Input()
  set isEditAll(isEditAll: boolean) {
    this._isEditAll = isEditAll;
  }

  get isEditAll(): boolean {
    return this._isEditAll;
  }

  @Input()
  set editOppoLineItem(_oppertunityLineItemRequest: OppertunityLineItemRequest) {
    this.oppertunityLineItemRequest = _oppertunityLineItemRequest;
    // this.oppertunityLineItemRequest.partCode = _oppertunityLineItemRequest.productPartCode;
    // this.oppertunityLineItemRequest.priceBookEntryId = _oppertunityLineItemRequest.priceBookEntryId;
    // this.oppertunityLineItemRequest.priceBookId = _oppertunityLineItemRequest.priceBookId;
  }


  @Input()
  set editAllOppoLineItems(_oppertunityLineItemRequestArray: Array<OppertunityLineItemRequest>) {
    this.oppertunityLineItemRequestArrayEdit = _oppertunityLineItemRequestArray;
  }

  get editAllOppoLineItems(): Array<OppertunityLineItemRequest> {
    return this.oppertunityLineItemRequestArrayEdit;
  }

  opportunityPriceBook: OpportunityPriceBook = new OpportunityPriceBook();
  _isEditAll: boolean;
  viewEditPriceItems: boolean;
  searchitem: string = '';
  i: number;
  serialNumber: number;
  message: string;
  oppertunityLineItemRequestArray: Array<OppertunityLineItemRequest> = [];
  oppertunityLineItemRequestArrayEdit: Array<OppertunityLineItemRequest> = [];
  oppertunityLineItemRequestEdit: OppertunityLineItemRequest = new OppertunityLineItemRequest();
  priceBookId: number;
  oppertunityLineItemRequest: OppertunityLineItemRequest = new OppertunityLineItemRequest();
  _oppertunityId: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  doneFlag: boolean;

  @Output()
  headingPrice: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onEditProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onEditProductCreated: EventEmitter<OppertunityLineItemResponse> = new EventEmitter<OppertunityLineItemResponse>();

  @Output()
  _onEditAllProductCreated: EventEmitter<Array<OppertunityLineItemResponse>> = new EventEmitter<Array<OppertunityLineItemResponse>>();

  @Output()
  headingEditPrice: EventEmitter<string> = new EventEmitter<string>();

  newArray: any = [];

  ngOnInit() {
    this.searchitem = '';
    this.getOppoLineItemsList(this.opportunityId);
  }

  canceleditAllData() {
    this._onEditProCancle.emit('cancel');
    this.oppertunityLineItemRequestArray = [];
    // this._isEditAll = false;
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this._onEditProCancle.emit('cancel');
    }
  }

  editOpportunityLineItem() {
    this.spinner.show();
    if(this.oppertunityLineItemRequest.quantity!=0){
      this.priceBookId = this.oppertunityLineItemRequest.priceBookId;
      this._opportunityService.putOpportunityLineItemEdit(this.oppertunityLineItemRequest, this.priceBookId
        , this._oppertunityId, 2).subscribe(data => {
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
      this.spinner.hide();
      this.message="Quantity Can not be 0";
      this.popupSuccessModel.show();
    }
  
  }

  // getOppoLineItemsList(id: number) {
  //   this.spinner.show();
  //   this._opportunityService.getOppoLineItemsList(this._oppertunityId).subscribe(data => {
  //     for (const i in data) {
  //       const tempReq = new OppertunityLineItemRequest();
  //       tempReq.id = data[i].id;
  //       tempReq.listPrice = data[i].listPrice;
  //       tempReq.price = data[i].salePrice;
  //       tempReq.quantity = data[i].quantity;
  //       tempReq.priceBookEntryId = data[i].priceBookEntryId;
  //       tempReq.partCode = data[i].productPartCode;
  //       this.oppertunityLineItemRequestArray.push(tempReq);
  //       this.priceBookId = data[i].priceBookId;
  //     }
  //     this.spinner.hide();
  //   });
  // }

  cancelDataEditProduct() {
    this._onEditProCancle.emit('cancel');
  }
quatityzero:boolean;
  editAll() {
    for( const li of this.oppertunityLineItemRequestArray){
     
      if(li.quantity==0){
        this.quatityzero=true;
        break;
      }    
      else{
        this.quatityzero=false;
        console.log("not 0")
      } 
    }
    if(this.quatityzero==true){
      this.message="Quantity can not be 0";
        this.popupSuccessModel.show();
    }
   else{
        this.spinner.show();
      this._opportunityService.putOpportunityLineItemsEditAll(this.oppertunityLineItemRequestArray, this.priceBookId
      , this._oppertunityId, 2).subscribe(data => {
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
    this.oppertunityLineItemRequestArrayEdit = [];
    this.oppertunityLineItemRequestArray = [];
       
      }
    
    
    
    // this._isEditAll = false;
  }

  getOppoLineItemsList(id: number) {
    this.spinner.show();
    this.oppertunityLineItemRequestArray = [];
    this._opportunityService.getOppoLineItemsList(this._oppertunityId).subscribe(data => {
      // tslint:disable-next-line: forin
      for (const i in data) {
        const tempReq = new OppertunityLineItemRequest();
        tempReq.id = data[i].id;
        tempReq.listPrice = data[i].listPrice;
        tempReq.price = data[i].salePrice;
        tempReq.quantity = data[i].quantity;
        tempReq.priceBookEntryId = data[i].priceBookEntryId;
        tempReq.partCode = data[i].productPartCode;
        this.oppertunityLineItemRequestArray.push(tempReq);
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
    onlyNumbers(key): boolean {
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
      console.log("Charcode is=" +charCode);
      if(charCode>31 && charCode==46){
        return true; 
       }
      else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
        return false;
      }
     
    }
    LimteExceed:boolean;
    onKey(event):any {
       if(event.target.value.length>4){
         this.LimteExceed=true;
        
       }
       else{
         this.LimteExceed=false;
       }
      }

}
