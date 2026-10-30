import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { BRCRepariInventoryProductConsumedResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ManageInventoryService } from '../../services/manageinventory.services';

@Component({
  selector: 'app-batteryinventoryconsumption',
  templateUrl: './batteryinventoryconsumption.component.html',
  styleUrls: ['./batteryinventoryconsumption.component.scss']
})
export class BatteryinventoryconsumptionComponent implements OnInit {
  @ViewChild('createAcountModel', { static: false }) createAcountModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  searchitem: string = '';
  recentlyView: boolean = false;
  related: boolean = false;
  all: boolean = true;
  allView: boolean = true;
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;

  message: string;
  public page: number = 0;
  pages: Array<number>;
  totalPages;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  i: number;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Battery Inventory Consumption';
  EDIT_ACCOUNT_HEADING: string = 'Edit Battery Inventory Consumption';

  sortColumnName: string = 'consumeddate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false,false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false,false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc'
  popupNgClass: string = 'alert alert-danger center center-block';
  consumedList: Array<BRCRepariInventoryProductConsumedResponse> = [];

  constructor(private spinner: NgxSpinnerService, private manageInventoryService: ManageInventoryService) { }

  ngOnInit() {
    this.getAll(this.sortColumnName, this.sortOrder);
  }
  getAll(column: string, order: string) {
    this.spinner.show();
    this.manageInventoryService.getProductConsumed(this.page, column, order).subscribe(data => {
      this.consumedList = data.content;
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecent(column: string, order: string) {
    this.spinner.show();
    this.manageInventoryService.getProductConsumed(this.page, column, order).subscribe(data => {
      this.consumedList = data.content;
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  dropdownChange(tabName: string) {
    if (tabName === 'all') {
      this.all = true;
      this.allView = true;
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.related = false;
      this.recentlyView = false;
      this.sortColumnName = 'consumeddate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getAll(this.sortColumnName, this.sortOrder);
      this.serialNumber = (((this.page) * this.size));

    } else {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.related = true;
      this.sortColumnName = 'consumeddate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getRecent(this.sortColumnName, this.sortOrder);
      this.recentlyView = true;
      this.serialNumber = (((this.page) * this.size));
      this.all = false;
      this.allView = false;
    }
    return false;
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    if (!this.all) {
      this.getRecent(this.sortColumnName, this.sortOrder);
    } else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (!this.all) {
      this.getRecent(this.sortColumnName, this.sortOrder);
    } else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    if (!this.all) {
      this.getRecent(this.sortColumnName, this.sortOrder);
    } else {
      this.getAll(this.sortColumnName, this.sortOrder);
    }
  }
  
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(8).fill(false);
          this.columnSort[7] = true;
        }
        break;
    }

    if (this.all) {
      this.getAll(this.sortColumnName, this.sortOrder);
    } else {
      this.getRecent(this.sortColumnName, this.sortOrder);
    }
  }
  popupClose() {
    this.popupSuccessModel.hide();
  }

}


