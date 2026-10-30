import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { BRCInventoryReceiveResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { type } from 'os';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-repair-engineer',
  templateUrl: './repair-engineer.component.html',
  styleUrls: ['./repair-engineer.component.scss']
})
export class RepairEngineerComponent implements OnInit {
  @ViewChild('ManageInventoryModel', { static: false }) ManageInventoryModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  message: string = '';
  ngClassManage: string;
  i: number;

  bRCInventoryReceiveResponse: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
  manageInventoryList: Array<BRCInventoryReceiveResponse> = [];
  searchitem: string = '';

  recentlyView: boolean = false;
  related: boolean = false;
  ngClassRelated: string;

  allView: boolean = true;
  all: boolean = true;
  ngClassAll: string = 'dropdown-item dropdown-item-checked';

  underRepair: boolean = false;
  underRepairView: boolean = false;
  ngClassUnderRepair: string;
  repairType: string = 'Under Repair';

  repairedandReadyforDispatched: boolean = false;
  repairedandReadyforDispatchedView: boolean = false;
  ngClassRepairedandReadyforDispatched: string;
  repairedandReadyforDispatchedType: string = 'Repaired and Ready for Dispatched'

  repairedandDispatched: boolean = false;
  repairedandDispatchedView: boolean = false;
  ngClassRepairedandDispatched: string;
  repairedandDispatchedType: string = 'Repaired and Dispatched';

  brandReadyforDispatched: boolean = false;
  brandReadyforDispatchedView: boolean = false;
  ngClassBandReadyforDispatched: string;
  brandReadyforDispatchedType: string = 'BR and Ready for Dispatched';

  brandDispatched: boolean = false;
  brandDispatchedView: boolean = false;
  ngClassBrandDispatched: string;
  brandDispatchedType: string = 'BR and Dispatched';

  public page: number = 0;
  pages: Array<number>;
  totalPages;
  first: boolean;
  last: boolean;
  size: number;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Manage Inventory';
  EDIT_ACCOUNT_HEADING: string = 'Edit Manage Inventory';

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  serialNumber: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  isManageInventoryModel: boolean = false;
  _cFlag: string;
  
  constructor(private spinner: NgxSpinnerService,private _activatedRoute: ActivatedRoute, private manageInventoryService: ManageInventoryService) { 
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  ngOnInit() {
    this.getByUser(this.sortColumnName, this.sortOrder);
  }

  getByUser(column: string, order: string) {
    this.spinner.show();
    this.manageInventoryService.getByUser(this.page, column, order).subscribe(data => {
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));

      if (data != null) {
        this.spinner.hide();
      }
      this.manageInventoryList = data.content;
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  getByUserAndType(column: string, order: string, type: string) {
    this.spinner.show();
    this.manageInventoryService.getByUserAndType(this.page, column, order, type).subscribe(data => {
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));

      if (data != null) {
        this.spinner.hide();
      }
      this.manageInventoryList = data.content;
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
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUser(this.sortColumnName, this.sortOrder);

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.underRepair = false;
      this.underRepairView = false;
      this.ngClassUnderRepair = 'dropdown-item';

      this.repairedandReadyforDispatched = false;
      this.repairedandReadyforDispatchedView = false;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item';

      this.repairedandDispatched = false;
      this.repairedandDispatchedView = false;
      this.ngClassRepairedandDispatched = 'dropdown-item';

      this.brandReadyforDispatched = false;
      this.brandReadyforDispatchedView = false;
      this.ngClassBandReadyforDispatched = 'dropdown-item';

      this.brandDispatched = false;
      this.brandDispatchedView = false;
      this.ngClassBrandDispatched = 'dropdown-item';

    }
    else if (tabName === 'underRepair') {
      this.underRepair = true;
      this.underRepairView = true;
      this.ngClassUnderRepair = 'dropdown-item dropdown-item-checked';
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairType);

      this.all = false;
      this.allView = false;
      this.ngClassAll = 'dropdown-item';

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.repairedandReadyforDispatched = false;
      this.repairedandReadyforDispatchedView = false;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item';

      this.repairedandDispatched = false;
      this.repairedandDispatchedView = false;
      this.ngClassRepairedandDispatched = 'dropdown-item';

      this.brandReadyforDispatched = false;
      this.brandReadyforDispatchedView = false;
      this.ngClassBandReadyforDispatched = 'dropdown-item';

      this.brandDispatched = false;
      this.brandDispatchedView = false;
      this.ngClassBrandDispatched = 'dropdown-item';
    }
    else if (tabName === 'repairedandReadyforDispatched') {
      this.repairedandReadyforDispatched = true;
      this.repairedandReadyforDispatchedView = true;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item dropdown-item-checked';

      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairedandReadyforDispatchedType);

      this.all = false;
      this.allView = false;
      this.ngClassAll = 'dropdown-item';

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.underRepair = false;
      this.underRepairView = false;
      this.ngClassUnderRepair = 'dropdown-item ';

      this.repairedandDispatched = false;
      this.repairedandDispatchedView = false;
      this.ngClassRepairedandDispatched = 'dropdown-item';

      this.brandReadyforDispatched = false;
      this.brandReadyforDispatchedView = false;
      this.ngClassBandReadyforDispatched = 'dropdown-item';

      this.brandDispatched = false;
      this.brandDispatchedView = false;
      this.ngClassBrandDispatched = 'dropdown-item';
    }
    else if (tabName === 'repairedandDispatched') {
      this.repairedandDispatched = true;
      this.repairedandDispatchedView = true;
      this.ngClassRepairedandDispatched = 'dropdown-item dropdown-item-checked';

      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairedandDispatchedType);

      this.all = false;
      this.allView = false;
      this.ngClassAll = 'dropdown-item';

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.underRepair = false;
      this.underRepairView = false;
      this.ngClassUnderRepair = 'dropdown-item ';

      this.repairedandReadyforDispatched = false;
      this.repairedandReadyforDispatchedView = false;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item ';

      this.brandReadyforDispatched = false;
      this.brandReadyforDispatchedView = false;
      this.ngClassBandReadyforDispatched = 'dropdown-item';

      this.brandDispatched = false;
      this.brandDispatchedView = false;
      this.ngClassBrandDispatched = 'dropdown-item';
    }
    else if (tabName === 'brandReadyforDispatched') {
      this.brandReadyforDispatched = true;
      this.brandReadyforDispatchedView = true;
      this.ngClassBandReadyforDispatched = 'dropdown-item dropdown-item-checked';

      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.brandReadyforDispatchedType);
      this.all = false;
      this.allView = false;
      this.ngClassAll = 'dropdown-item';

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.underRepair = false;
      this.underRepairView = false;
      this.ngClassUnderRepair = 'dropdown-item ';

      this.repairedandReadyforDispatched = false;
      this.repairedandReadyforDispatchedView = false;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item ';

      this.repairedandDispatched = false;
      this.repairedandDispatchedView = false;
      this.ngClassRepairedandDispatched = 'dropdown-item ';


      this.brandDispatched = false;
      this.brandDispatchedView = false;
      this.ngClassBrandDispatched = 'dropdown-item';
    }
    else if (tabName === 'brandDispatched') {
      this.brandDispatched = true;
      this.brandDispatchedView = true;
      this.ngClassBrandDispatched = 'dropdown-item dropdown-item-checked';

      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.page = 0;
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.brandDispatchedType);

      this.all = false;
      this.allView = false;
      this.ngClassAll = 'dropdown-item';

      this.related = false;
      this.recentlyView = false;
      this.ngClassRelated = 'dropdown-item';

      this.underRepair = false;
      this.underRepairView = false;
      this.ngClassUnderRepair = 'dropdown-item ';

      this.repairedandReadyforDispatched = false;
      this.repairedandReadyforDispatchedView = false;
      this.ngClassRepairedandReadyforDispatched = 'dropdown-item ';

      this.repairedandDispatched = false;
      this.repairedandDispatchedView = false;
      this.ngClassRepairedandDispatched = 'dropdown-item ';

      this.brandReadyforDispatched = false;
      this.brandReadyforDispatchedView = false;
      this.ngClassBandReadyforDispatched = 'dropdown-item ';

    }
    return false;
  }

  ManageInventoryModelhide() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.ManageInventoryModel.hide();
  }
  showManageInventoryView() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.ManageInventoryModel.show();
  }
  cancelData() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.ManageInventoryModel.hide();
  }

  onManageInventoryCreated($event: BRCInventoryReceiveResponse) {
    if (!this.isEditMode) {
      this.manageInventoryList.unshift($event);
    } else {
      let index = 0;
      this.manageInventoryList.forEach(element => {
        if (element.receivingId === $event.receivingId) {
          this.manageInventoryList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }


  createEditModelshow(brcInventory: BRCInventoryReceiveResponse) {
    this.spinner.show();
    this.manageInventoryService.getMIByMiId(brcInventory.receivingId).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.bRCInventoryReceiveResponse = data;
    });

    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.ManageInventoryModel.show();
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    if (!this.all) {
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairType);
    } else {
      this.getByUser(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (!this.all) {
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairType);
    } else {
      this.getByUser(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    if (!this.all) {
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairType);
    } else {
      this.getByUser(this.sortColumnName, this.sortOrder);
    }
  }

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
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[7] = true;
        }
        break;
      case 8:
        if (this.columnSort[8]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[8] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[8] = true;
        }
        break;
      case 9:
        if (this.columnSort[9]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[9] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[9] = true;
        }
        break;
      case 10:
        if (this.columnSort[10]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[10] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[10] = true;
        }
        break;
      case 11:
        if (this.columnSort[11]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[11] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(12).fill(false);
          this.columnSort[11] = true;
        }
        break;
    }
    if (this.allView) {
      this.getByUser(this.sortColumnName, this.sortOrder);
    } else {
      this.getByUserAndType(this.sortColumnName, this.sortOrder, this.repairType);
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }
}
