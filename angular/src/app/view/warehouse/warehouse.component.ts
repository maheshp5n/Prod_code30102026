import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { WarehouseService } from '../../services/warehouse.service';
import { WarehousedetailsRequest, WarehousedetailsResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-warehouse',
  templateUrl: './warehouse.component.html',
  styleUrls: ['./warehouse.component.css']
})
export class WarehouseComponent implements OnInit {
  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_Warehouse_HEADING: string = 'New Warehouse';
  EDIT_Warehouse_HEADING: string = 'Edit Warehouse';
  warehouseList: Array<WarehousedetailsResponse> = [];
  searchitem: string = '';
  isSelected = false;
  newwarehouse: WarehousedetailsRequest = new WarehousedetailsRequest();
  warehouseid;
  recentlyView: boolean = true;
  ngClassAll: string = 'dropdown-item';
  serialNumber: number;
  recently: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  reverse: boolean = false;
  sortColumn: number = 5;
  sortColumnName: string = 'createdDateTime'; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  columnVisibility: Array<boolean> = [false, false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, false, false];
  page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  warehouseListById;
  i: number;
  popupNgClass: string = "alert alert-danger center center-block";

  @ViewChild('createWarehouseModel') createWarehouseModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  constructor(private _warehouseService: WarehouseService, private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.setOrder("createdDateTime", 5);
  }

  dropdownChange(tabName: string) {
    if (tabName == 'related') {
      this.ngClassAll = 'dropdown-item';
      this.ngClassRelated = 'dropdown-item dropdown-item-checked';
      this.sortColumnName = 'createdDateTime';
      this.recently = true;
      this.sortOrder = 'asc';
      this.getRecentWarehouse(this.sortColumnName, this.sortOrder);
      this.recentlyView = true
      this.page = 0;
    }
    else {
      this.ngClassAll = 'dropdown-item dropdown-item-checked';
      this.ngClassRelated = 'dropdown-item';
      this.recently = false;
      this.recentlyView = false
      this.sortColumnName = 'createdDateTime';
      this.sortOrder = 'desc';
      this.getWarehouse(this.sortColumnName, this.sortOrder);
      this.page = 0;
    }
    return false;
  }

  // PAGINATION
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    if (!this.recently) {
      this.getWarehouse(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentWarehouse(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (!this.recently) {
      this.getWarehouse(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentWarehouse(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    if (!this.recently) {
      this.getWarehouse(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecentWarehouse(this.sortColumnName, this.sortOrder);
    }
  }

  // PAGINATION ENDS

  getWarehouse(column: string, order: string) {
    this.spinner.show();
    this._warehouseService.getWarehouse(this.page, column, order).subscribe(data => {
      this.warehouseList = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + " # " + err.error.message;
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 3000);
    });
  }

  // recent;
  getRecentWarehouse(column: string, order: string) {
    this.spinner.show();
    this._warehouseService.getRecentWarehouse(this.page, column, order).subscribe(data => {
      this.warehouseList = data.content;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.status + " # " + err.error.message;
      this.popupSuccessModel.show();
      // setTimeout(() => {
      //   this.popupSuccessModel.hide();
      // }, 3000);
    });
  }

  onClickEdit(warehouse, warehouseId) {
    this.spinner.show();
    this._warehouseService.getWarehouseById(warehouseId).subscribe((data) => {
      this.newwarehouse = data;
      if (this.newwarehouse.legalEntityName != null || this.newwarehouse.legalEntityName != '') {
        this.newwarehouse.isLegalEntitySelected = "fa fa-times";
      }
      else {
        this.newwarehouse.isLegalEntitySelected = "fa fa-search";
      }
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_Warehouse_HEADING;
    this.createWarehouseModel.show();
  }

  // SORT
  setOrder(value: string, column: number) {
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
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
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(5).fill(false);
          this.columnSort[5] = true;
        }
        break;
    }
    if (this.recentlyView) {
      this.getRecentWarehouse(this.sortColumnName, this.sortOrder);
    } else {
      this.getWarehouse(this.sortColumnName, this.sortOrder);
    }
  }
  // SORT ENDS

  showCreateWarehouseView() {
    this.newwarehouse = new WarehousedetailsRequest();
    this.isEditMode = false;
    this.heading = this.CREATE_Warehouse_HEADING;
    this.createWarehouseModel.show();
  }

  cancelData() {
    this.createWarehouseModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Warehouse_HEADING;
    this.newwarehouse = new WarehousedetailsRequest();
  }

  onWarehouseCreated($event: WarehousedetailsResponse) {
    if (!this.isEditMode) {
      this.warehouseList.unshift($event);
    } else {
      let index = 0;
      this.warehouseList.forEach(element => {
        if (element.warehouseId == $event.warehouseId) {
          this.warehouseList[index] = $event;
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
