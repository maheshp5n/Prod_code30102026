import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { deliverychallan } from '../../services/model';

@Component({
  selector: 'app-deliverychallan',
  templateUrl: './deliverychallan.component.html',
  styleUrls: ['./deliverychallan.component.css']
})
export class deliverychallanComponent implements OnInit {

  constructor() {
  }
  isEditMode: boolean = false;
  heading: string;
  CREATE_deliverychallan_HEADING: string = 'New Delivery Challan';
  EDIT_deliverychallan_HEADING: string = 'Edit Delivery Challan';
  isSelected = false;
  reverse: boolean = false;
  recentlyView: boolean = true;
  searchitem: string = '';
  ngClassAll: string;
  recently: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  message: string;
  productId;
  sortColumn: number = 6;
  sortColumnName: string = 'createddate';
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false, false];
  page: number = 0;
  newdeliverychallan: deliverychallan = new deliverychallan();
  i: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createNewdeliverychallan', { static: false }) createNewdeliverychallan: ModalDirective;

  ngOnInit() {
    // this.setOrder("createddate", 6);
    this.serialNumber = 1;
  }

  dropdownChange(tabName: string) {
    if (tabName === 'related') {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.recently = true;
      // this.sortColumnName = 'createddate';
      this.sortOrder = 'desc';
      // this.getRecentProducts(this.sortColumnName, this.sortOrder);
      this.recentlyView = true;
      this.page = 0;
    } else {
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.recently = false;
      this.recentlyView = false;
      // this.sortColumnName = 'createddate';
      this.sortOrder = 'desc';
      // this.getProducts(this.sortColumnName, this.sortOrder);
      this.page = 0;
    }
    return false;
  }

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    // if (!this.recently) {
    //   this.getProducts(this.sortColumnName, this.sortOrder);
    // }
    // else {
    //   this.getRecentProducts(this.sortColumnName, this.sortOrder);
    // }
  }

  setPage(event: any) {
    event.preventDefault();
    // if (!this.recently) {
    //   this.getProducts(this.sortColumnName, this.sortOrder);
    // }
    // else {
    //   this.getRecentProducts(this.sortColumnName, this.sortOrder);
    // }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    // if (this.recently == false) {
    //   this.getProducts(this.sortColumnName, this.sortOrder);
    // }
    // else {
    //   this.getRecentProducts(this.sortColumnName, this.sortOrder);
    // }
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
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort[7] = true;
        }
        break;
    }
    // if (this.recentlyView) {
    //   this.getRecentProducts(this.sortColumnName, this.sortOrder);
    // } else {
    //   this.getProducts(this.sortColumnName, this.sortOrder);
    // }
  }
  // SORT ENDS

  showCreatedeliverychallanView() {
    this.isEditMode = false;
    this.heading = this.CREATE_deliverychallan_HEADING;
    this.createNewdeliverychallan.show();
  }

  cancelData() {
    this.createNewdeliverychallan.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_deliverychallan_HEADING;
    this.newdeliverychallan = new deliverychallan();
  }

  ondeliverychallanCreated($event: deliverychallan) {

  }


  onClickEdit() {
    this.isEditMode = true;
    this.heading = this.EDIT_deliverychallan_HEADING;
    this.createNewdeliverychallan.show();
  }

  popupClose() {
    this.popupSuccessModel.hide();

  }

}
