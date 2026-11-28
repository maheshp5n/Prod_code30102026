import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { RequisitionResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { RequisitionService } from '../../services/requisition.services';

@Component({
  selector: 'app-requisition',
  templateUrl: './requisition.component.html',
  styleUrls: ['./requisition.component.scss']
})
export class RequisitionComponent implements OnInit {
  public message: string = '';
  @ViewChild('createRequisitionModel') createRequisitionModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  requisitionResponse: RequisitionResponse = new RequisitionResponse
  requisitionList: Array<RequisitionResponse> = [];
  searchitem: string = '';

  recentlyView: boolean = true;
  ngClassAll: string;
  related: boolean = true;
  all: boolean = true;
  ngClassRelated: string = 'fa fa-check';

  public page: number = 0;
  pages: Array<number>;
  totalPages;
  first: boolean;
  last: boolean;
  serialNum: number;
  size: number;
  i: number;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Requisition';
  EDIT_ACCOUNT_HEADING: string = 'Edit Requisition';

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  serialNumber: number;

  popupNgClass: string = 'alert alert-danger center center-block';

  constructor(private spinner: NgxSpinnerService, private _requisitionServiece: RequisitionService) { }

  ngOnInit() {
    this.getRecent(this.sortColumnName, this.sortOrder);
  }
  getAllRequisition(column: string, order: string) {
    this.spinner.show();
    this._requisitionServiece.getAllRequisition(this.page, column, order).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.requisitionList = data.content;
      
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      // this.serialNumber = (((this.page)*5)-5)+1;
      this.serialNum = (((this.page) * this.size));
    },err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getRecent(column: string, order: string) {
    this.spinner.show();
    this._requisitionServiece.getRecentRequisition(this.page, column, order).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.requisitionList = data.content;
      
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      // this.serialNumber = (((this.page)*5)-5)+1;
      this.serialNum = (((this.page) * this.size));
    },err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }


  dropdownChange(tabName: string) {
    if (tabName == 'related') {
      this.ngClassAll = '';
      this.ngClassRelated = 'fa fa-check';
      this.related = true;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getRecent(this.sortColumnName, this.sortOrder);
      this.recentlyView = true;
      this.page = 0;
      this.serialNumber = (((this.page) * this.size));
    }
    else {
      this.ngClassAll = 'fa fa-check';
      this.ngClassRelated = '';
      this.related = false;
      this.recentlyView = false;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getAllRequisition(this.sortColumnName, this.sortOrder);
      this.page = 0;
    }
    return false;
  }

  
  showCreateRequisitionView() {
    this.requisitionResponse = new RequisitionResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createRequisitionModel.show();
  }

  createRequisitionModelhide() {
    this.requisitionResponse = new RequisitionResponse();
    this.createRequisitionModel.hide();
  }

  createEditModelshow(a: RequisitionResponse) {
    this.spinner.show();
    this._requisitionServiece.getRequisitionById(a.id).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.requisitionResponse = data;      
    },err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createRequisitionModel.show();
  }

  cancelData() {
    this.requisitionResponse = new RequisitionResponse();
    this.createRequisitionModel.hide();
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    //this.serialNum = (((this.page) * this.size));
    if (this.related == false) {
      this.getAllRequisition(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecent(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (this.related == false) {
      this.getAllRequisition(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecent(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    //this.serialNum = (((this.page) * this.size));
    if (this.related == false) {
      this.getAllRequisition(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getRecent(this.sortColumnName, this.sortOrder);
    }
  }

  isDisabledPrevious() {

    if (this.first == true) {
      return true;
    }
    else
      return false;
  }

  isDisabledNext() {
    if (this.last == true) {
      return true;
    }
    else
      return false;
  }

  onRequisitionCreated($event: RequisitionResponse) {
    //this.accountList.push($event);
    if (!this.isEditMode) {
      this.requisitionList.unshift($event);
    } else {
      let index = 0;
      this.requisitionList.forEach(element => {
        if (element.id == $event.id) {
          this.requisitionList[index] = $event;
        } else {
          index++;
        }
      });
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
    if (this.recentlyView) {
      this.getRecent(this.sortColumnName, this.sortOrder);
    } else {
      this.getAllRequisition(this.sortColumnName, this.sortOrder);
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();   
  }
}
