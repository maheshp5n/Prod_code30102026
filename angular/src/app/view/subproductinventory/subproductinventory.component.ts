import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AssetsResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { AssetsService } from '../../services/assets.services';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-subproductinventory',
  templateUrl: './subproductinventory.component.html',
  styleUrls: ['./subproductinventory.component.scss']
})
export class SubproductinventoryComponent implements OnInit {
  public message: string = '';
  @ViewChild('createAcountModel') createAcountModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel : ModalDirective;

  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  assetList: Array<AssetsResponse> = [];
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
  i: number

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Sub Product Inventory';
  EDIT_ACCOUNT_HEADING: string = 'Edit Sub Product Inventory';

  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 1;
  columnVisibility: Array<boolean> = [false, false, false, false, false, false, false, false, false];
  columnSort: Array<boolean> = [false, true, false, false, false, false, false, false, false];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  serialNumber: number;

  constructor(private _gloabl:GlobalserviceService,private spinner: NgxSpinnerService, private _assetsService: AssetsService) { }

  ngOnInit() {
    this.getAssetRecent(this.sortColumnName, this.sortOrder);
  }
  getAllAseets(column: string, order: string) {
    this.spinner.show();
    this._assetsService.getAllAssets().subscribe(data => {
      this.assetList = data.content;
        this.spinner.hide();
     this.pages = new Array(data['totalPages']);
      this.totalPages = data['totalPages'];
      this.first = data['first'];
      this.last = data['last'];
      this.size = data['size'];
      this.serialNum = (((this.page) * this.size));
    },err=>{
      this.spinner.hide();
      this.message=err.error.message;
      this.popupSuccessModel.show();
      
    });
  }

  getAssetRecent(column: string, order: string) {
    this.spinner.show();
    this._assetsService.getAssetRecent(this.page, column, order).subscribe(data => {
      this.assetList = data.content;
      if (data != null) {
        this.spinner.hide();
      }

      this.pages = new Array(data['totalPages']);
      this.totalPages = data['totalPages'];
      this.first = data['first'];
      this.last = data['last'];
      this.size = data['size'];
      this.serialNum = (((this.page) * this.size));
    })
  }

  assetResponse: AssetsResponse = new AssetsResponse();

  dropdownChange(tabName: string) {
    if (tabName == 'related') {
      this.ngClassAll = '';
      this.ngClassRelated = 'fa fa-check';
      this.related = true;
      this.sortColumnName = 'createdDate';
      this.sortOrder = 'desc';
      this.getAssetRecent(this.sortColumnName, this.sortOrder);
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
      this.getAllAseets(this.sortColumnName, this.sortOrder);
      this.page = 0;
    }
    return false;
  }

  createAcountModelhide() {
    this.assetResponse = new AssetsResponse();
    this.onCancle.emit('cancled');
    this.createAcountModel.hide();
  }
  showCreateAccountView() {
    this.assetResponse = new AssetsResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  createEditModelshow(a: AssetsResponse) {
    //let ob = a.serialNumber;
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(a.serialNumber).subscribe(data => {
      this.assetResponse = data;
      if (data != null) {
        this.spinner.hide();
      }
    });

    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  cancelData() {
    this.assetResponse = new AssetsResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.hide();
  }

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    //this.serialNum = (((this.page) * this.size));
    console.log(this.serialNum)
    if (this.related == false) {
      this.getAllAseets(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAssetRecent(this.sortColumnName, this.sortOrder);
    }
  }

  setPage(event: any) {
    event.preventDefault();
    if (this.related == false) {
      this.getAllAseets(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAssetRecent(this.sortColumnName, this.sortOrder);
    }
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    //this.serialNum = (((this.page) * this.size));
    if (this.related == false) {
      this.getAllAseets(this.sortColumnName, this.sortOrder);
    }
    else {
      this.getAssetRecent(this.sortColumnName, this.sortOrder);
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


  onAssetCreated($event: AssetsResponse) {
    if (!this.isEditMode) {
      this.assetList.unshift($event);
      this._gloabl.alert({ type: '', message: 'Created Successfully' });
    } else {
      this._gloabl.alert({ type: '', message: 'Updated Successfully' });
      let index = 0;
      this.assetList.forEach(element => {
        // console.log('element',element);
        if (element.serialNumber == $event.serialNumber) {
          this.assetList[index] = $event;
          //console.log('index',index);
        } else {
          index++;
        }
      });
    }
  }

  setOrder(value: string, column: number) {
    console.log(value, column);
    this.sortColumnName = value;
    switch (column) {
      case 1:
        if (this.columnSort[1]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[1] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[1] = true;
        }
        break;
      case 2:
        if (this.columnSort[2]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[2] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[2] = true;
        }
        break;
      case 3:
        if (this.columnSort[3]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[3] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[3] = true;
        }
        break;
      case 4:
        if (this.columnSort[4]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[4] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[4] = true;
        }
        break;
      case 5:
        if (this.columnSort[5]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[5] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[5] = true;
        }
        break;
      case 6:
        if (this.columnSort[6]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[6] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[6] = true;
        }
        break;
      case 7:
        if (this.columnSort[7]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[7] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[7] = true;
        }
        break;
      case 8:
        if (this.columnSort[8]) {
          this.sortOrder = this.ASCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[8] = false;
        } else {
          this.sortOrder = this.DESCENDING;
          this.columnSort = Array<boolean>(9).fill(false);
          this.columnSort[8] = true;
        }
        break;

    }

    console.log(this.columnSort[1], this.sortOrder);
    if (this.recentlyView) {
      this.getAssetRecent(this.sortColumnName, this.sortOrder);
    } else {
      this.getAllAseets(this.sortColumnName, this.sortOrder);
    }
  }

}
