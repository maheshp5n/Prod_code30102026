import { Component, OnInit, ViewChild } from '@angular/core';
import { AccountsForList, ProductLineItemsResponse, ProductLineItemsRequest } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductLineItemsService } from '../../services/product-line-items.service';
// This Module Not in use=====shagufta
@Component({
  selector: 'app-productLineItems',
  templateUrl: './ProductLineItems.component.html',
  styleUrls: ['./ProductLineItems.component.css']
})
export class ProductLineItemsComponent implements OnInit {
  message: string;
  productLineItemsList: Array<ProductLineItemsResponse> = [];
  searchitem: string;
  isSelected = false;
  isEditMode: boolean = false;
  heading: string;
  CREATE_PRODUCT_HEADING: string = 'New Product';
  EDIT_PRODUCT_HEADING: string = 'Edit Product';
  page: number = 0;
  order: string = 'createdDate'; // first column variable
  reverse: boolean = false;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  userforlist;
  recentlyView: boolean = true;
  ngClassAll: string = 'dropdown-item';
  recently: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  productlineitemsName;
  newProductLineItemsRequest: ProductLineItemsRequest = new ProductLineItemsRequest();
  productLineItemsListById: ProductLineItemsResponse = new ProductLineItemsResponse();
  newproductLineItems: ProductLineItemsRequest = new ProductLineItemsRequest();
  accountForList: Array<AccountsForList> = [];
  sortColumn: number = 6;
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false];
  i: number;
  popupNgClass: string = "alert alert-danger center center-block";

  @ViewChild('createProductLineItemsModel') createProductLineItemsModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  constructor(private spinner: NgxSpinnerService, private productLineItemsService: ProductLineItemsService) {
  }

  ngOnInit() {
    // this.dropdownChange('related');
    // this.setOrder("createdDate", 6);
  }

  dropdownChange(tabName: string) {
    if (tabName == 'related') {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.recently = true;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'asc';
      this.getRecentProductLineItems(this.sortColumnName, this.sortOrder);
      this.recentlyView = true
    }
    else {
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.recently = false;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getProductLineItems(this.sortColumnName, this.sortOrder);
      this.recentlyView = false
    }
    return false;
  }

  onClickEdit() {
    // this.spinner.show();
    // this.newproductLineItems = y;
    // this.newproductLineItems.serialNumber = y.serialNumber;
    // this.productLineItemsService.getproductLineItemsById(i).subscribe(data => {
    //   if (data != null) {
    //     this.spinner.hide();
    //   }
    //   this.productLineItemsListById = data;
    //   this.productlineitemsName = i;
    // });
    this.isEditMode = true;
    this.heading = this.EDIT_PRODUCT_HEADING;
    this.createProductLineItemsModel.show();
  }

  // SORT
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
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
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(6).fill(false);
          this.columnSort[6] = true;
        }
        break;
    }
    if (this.recentlyView) {
      this.getRecentProductLineItems(this.sortColumnName, this.sortOrder);
    } else {
      this.getProductLineItems(this.sortColumnName, this.sortOrder);
    }
  }
  // SORT ENDS

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    if (!this.recently) {
      this.getProductLineItems(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentProductLineItems(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (!this.recently) {
      this.getProductLineItems(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentProductLineItems(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    if (!this.recently) {
      this.getProductLineItems(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentProductLineItems(this.sortColumnName, this.sortOrder);
    }
  }

  // PAGINATION ENDS
  showCreateProductLineItemsView() {
    this.newproductLineItems = new ProductLineItemsRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.createProductLineItemsModel.show();
  }

  getProductLineItems(column: string, order: string) {
    this.spinner.show();
    this.productLineItemsService.getProductLineItems(this.page, column, order).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.productLineItemsList = data.content;
      this.first = data['first'];
      this.last = data['last'];
      this.size = data['size'];
      this.serialNumber = (((this.page) * this.size));
    }, err => {
      this.spinner.hide();
      this.message = err.status + " # " + err.error.message;
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 3000);
    });
  }

  getRecentProductLineItems(column: string, order: string) {
    this.spinner.show();
    this.productLineItemsService.getRecentProductLineItems(this.page, column, order).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.productLineItemsList = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
    }, err => {
      this.spinner.hide();
      this.message = err.status + " # " + err.error.message;
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 3000);
    });
  }

  cancelData() {
    this.createProductLineItemsModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_PRODUCT_HEADING;
    this.newproductLineItems = new ProductLineItemsRequest();
  }

  onProductLineItemsCreated($event: ProductLineItemsResponse) {
    let eventProductLineItems = new ProductLineItemsResponse();
    if (!this.isEditMode) {
      this.productLineItemsList.unshift($event);
    } else {
      let index = 0;
      this.productLineItemsList.forEach(element => {
        if (element.serialNumber == $event.serialNumber) {
          this.productLineItemsList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();

  }

}
