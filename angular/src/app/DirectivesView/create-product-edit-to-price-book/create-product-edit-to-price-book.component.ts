import { PriceBookEntriesRequestInProducts } from '../../services/model';
import { PriceBookForList, PriceBookRequest } from '../../services/model';
import { PriceBookService } from '../../services/pricebook.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { Output } from '@angular/core';
import { ViewChild } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-product-edit-to-price-book',
  templateUrl: './create-product-edit-to-price-book.component.html',
  styleUrls: ['./create-product-edit-to-price-book.component.css']
})
export class CreateProductEditToPriceBookComponent implements OnInit {

  constructor(private spinner: NgxSpinnerService,
    private _priceBookService: PriceBookService, private _activatedRoute: ActivatedRoute) {
  }

  @Input()
  set viewEditPriceBook(viewEditPriceBook: boolean) {
    this._viewEditPriceBook = viewEditPriceBook;
  }

  get viewEditPriceBook(): boolean {
    return this._viewEditPriceBook;
  }
  @Input()
  set _isEditModeEditProduct(isEditModeEditProduct: boolean) {
    this.isEditModeEditProduct = isEditModeEditProduct;
  }

  get _isEditModeEditProduct(): boolean {
    return this.isEditModeEditProduct;
  }
  @Input()
  set _editPriceBookEntry(editPriceBookEntry: PriceBookEntriesRequestInProducts) {
    this.priceBookEntriesRequestInProducts = editPriceBookEntry;
  }

  get _editPriceBookEntry(): PriceBookEntriesRequestInProducts {
    return this.priceBookEntriesRequestInProducts;
  }

  isEditModeEditProduct: boolean;
  priceBookEntriesRequestInProducts: PriceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
  partCode: string;
  priceBookForList: Array<PriceBookForList> = [];
  searchitem: string = '';
  iseditProductOpen: boolean;
  _viewEditPriceBook: boolean;
  priceBookRequest: PriceBookRequest = new PriceBookRequest();
  priceBookEntriesRequestname: string;
  active: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  flag: boolean;

  @ViewChild('editPriceItems', { static: false }) editPriceItems: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  _onEditProCancle: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  _onEditProductCreated: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit() {
    this.searchitem = '';
  }

  canceleditProduct() {
    this._onEditProCancle.emit('can');
    this.priceBookEntriesRequestInProducts = new PriceBookEntriesRequestInProducts();
    this.priceBookRequest = new PriceBookRequest();
    this.viewEditPriceBook = false;
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.viewEditPriceBook = false;
      this._onEditProCancle.emit('can');
    }
  }

  createProduct() {
    this.flag = false;
    if (this.priceBookEntriesRequestInProducts.listPrice === 0 || this.priceBookEntriesRequestInProducts.listPrice === null) {
      this.message = 'Please fill List Price !';
    } else {
      this.flag = true;
      if (this._isEditModeEditProduct === false) {
        this.spinner.show();
        this._priceBookService.savePriceBookEntry(this.priceBookRequest.id, this.priceBookEntriesRequestInProducts).subscribe(data => {
          this.message = 'Submitted Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this._onEditProductCreated.emit('data');
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
          this._onEditProductCreated.emit('data');
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
