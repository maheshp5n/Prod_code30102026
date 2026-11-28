import { FieldValidation } from './../../services/validation.services';
import { PriceBookEntriesRequestInProducts, PriceBookEntriesResponse } from './../../services/model';
import { PriceBookForList, PriceBookRequest } from '../../services/model';
import { PriceBookService } from '../../services/pricebook.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ActivatedRoute } from '@angular/router';
import { Output } from '@angular/core';
import { ViewChild } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-product-add-to-price-book',
  templateUrl: './create-product-add-to-price-book.component.html',
  styleUrls: ['./create-product-add-to-price-book.component.css']
})
export class CreateProductAddToPriceBookComponent implements OnInit {

  constructor(private spinner: NgxSpinnerService,
    private _priceBookService: PriceBookService, private _activatedRoute: ActivatedRoute) {
  }

  @Input()
  set viewAddPriceBook(viewAddPriceBook: boolean) {
    this._viewAddPriceBook = viewAddPriceBook;
  }

  get viewAddPriceBook(): boolean {
    return this._viewAddPriceBook;
  }
  @Input()
  set _isEditModeAddProduct(isEditModeAddProduct: boolean) {
    this.isEditModeAddProduct = isEditModeAddProduct;
    // console.log(this.isEditModeAddProduct);
    // if (isEditModeAddProduct === true) {
    //   this.createaddProductModel.show();
    // }
  }

  get _isEditModeAddProduct(): boolean {
    return this.isEditModeAddProduct;
  }
  @Input()
  set _editPriceBookEntry(editPriceBookEntry: PriceBookEntriesRequestInProducts) {
    this.editPriceBookEntry = editPriceBookEntry;
  }

  get _editPriceBookEntry(): PriceBookEntriesRequestInProducts {
    return this.editPriceBookEntry;
  }

  isEditModeAddProduct: boolean;

  editPriceBookEntry: PriceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
  partCode: string;
  priceBookForList: Array<PriceBookForList> = [];
  searchitem: string = '';
  isaddProductOpen: boolean;
  _viewAddPriceBook: boolean;
  priceBookRequest: PriceBookRequest = new PriceBookRequest();
  priceBookEntriesRequestInProducts: PriceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
  priceBookEntriesRequestname: string;
  active: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';

  @ViewChild('addPriceItems') addPriceItems: ModalDirective;
  @ViewChild('createaddProductModel') createaddProductModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  @Output()
  _onAddProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onAddProductCreated: EventEmitter<string> =
    new EventEmitter<string>();
  flag: boolean;

  ngOnInit() {
    this.searchitem = '';
    this.getAllPriceBookList();
  }

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
    this.priceBookRequest = new PriceBookRequest();
    this.createaddProductModel.hide();
    this.viewAddPriceBook = false;
    this._onAddProCancle.emit('can');
  }

  postPriceBook() {
    this._viewAddPriceBook = false;
    this._activatedRoute.params.subscribe(pram => {
      this.priceBookEntriesRequestInProducts.partCode = pram['id'];
    });
    this.createaddProductModel.show();
  }

  getPriceBookById() {
    if (this.priceBookRequest.id !== 0) {
      this._priceBookService.getDetailById(this.priceBookRequest.id).subscribe(data => {
        this.priceBookEntriesRequestInProducts.priceBookId = this.priceBookRequest.id;
        this.priceBookEntriesRequestInProducts.priceBookName = data.name;
        // this.priceBookEntriesRequestInProducts.standardPriceBook = data.standardPriceBook;
        // this.priceBookEntriesRequestInProducts.isActive = data.active;
        this.active = data.active;
      });
    }
  }

  canceladdProduct() {
    this.createaddProductModel.hide();
    this.priceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
    // this._onAddProCancle.emit('can');
    // this._viewAddPriceBook = true;
    this.priceBookRequest = new PriceBookRequest();
    this.createaddProductModel.hide();
    this.viewAddPriceBook = false;
    this._onAddProCancle.emit('can');
  }



  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.createaddProductModel.hide();
      this.viewAddPriceBook = false;
      this._onAddProCancle.emit('can');
    }
  }
  // priceBookEntriesRequestInProducts.listPrice
  // createProduct() {

  //   this.spinner.show();
  //   this._priceBookService.savePriceBookEntry(this.priceBookRequest.id, this.priceBookEntriesRequestInProducts).subscribe(data => {
  //     this.message = 'Submitted Successfully';
  //     this.popupNgClass = 'alert alert-success center center-block';
  //     this._onAddProductCreated.emit('data');
  //     this.spinner.hide();
  //     this.popupSuccessModel.show();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //   });
  // }

  createProduct() {
    this.flag = false;
    if (this.priceBookEntriesRequestInProducts.listPrice === 0 || this.priceBookEntriesRequestInProducts.listPrice === null) {
      this.message = 'Please fill List Price !';
    } else {
      this.flag = true;
      if (this._isEditModeAddProduct === false) {
        this.spinner.show();
        this._priceBookService.savePriceBookEntry(this.priceBookRequest.id, this.priceBookEntriesRequestInProducts).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this._onAddProductCreated.emit('data');
          this.spinner.hide();
          this.popupSuccessModel.show();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner.show();
        this._priceBookService.updatePicebookentries(this.priceBookEntriesRequestInProducts).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this._onAddProductCreated.emit('data');
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      }
      if (this.flag !== true) {
        this.popupSuccessModel.show();
      }
    }
  }

}
