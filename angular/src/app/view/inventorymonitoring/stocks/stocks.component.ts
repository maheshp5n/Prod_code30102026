import { Component, OnInit, ViewChild } from '@angular/core';
import { LocationForList, LocationProductItemResponse, Page } from '../../../services/model';
import { LocationService } from '../../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { config } from 'rxjs';

@Component({
  selector: 'app-stocks',
  templateUrl: './stocks.component.html',
  styleUrls: ['./stocks.component.scss']
})
export class StocksComponent implements OnInit {
  warehouseList: Array<LocationForList> = [];
  locationProductItems: Page<LocationProductItemResponse> = new Page<LocationProductItemResponse>();
  locationId: number = 0;
  searchWarehouseKey: string;
  searchproductkey: string;
  config = {
    allWarehouse: true,
    individualWarehouse: false,
    allItem: true,
    serial: false,
    nonserial: false
  }
  allselection: boolean = false;
  tempReorderDetails = [];
  alertDetails = {
    type: 'danger',
    message: ''
  }
  indiname: string = '';
  @ViewChild('alertWindow') alertWindow: ModalDirective;
  @ViewChild('reorderModal') reorderModal: ModalDirective;
  constructor(private _locationService: LocationService, private _ngxSpinner: NgxSpinnerService, private _activatedroute: ActivatedRoute, private _router: Router) { }

  ngOnInit() {
    this.getLocationList();
    this.getLocationProductItems();
    this._activatedroute.params.subscribe(pram => {
      this.locationId = pram['id'];
      if (this.locationId != undefined && this.locationId != null && this.locationId != 0) {
        this.config.allItem = true;
        this.config.individualWarehouse = true;
        this.config.allWarehouse = false;
        this.config.nonserial = false;
        this.config.serial = false;

        this.getLocationProductItemsById(this.locationId);
      }
    });
  }
  getPillsClass(type): any {
    this.indiname = '';
    if (type == 'all') {
      if (this.config.allWarehouse) {
        return 'm-pills m-pills-selected';
      }
      else {
        return 'm-pills';
      }
    }
    if (type == 'war') {
      if (this.config.individualWarehouse) {
        this.indiname = this.getLocationNameById(this.locationId);
        return 'm-pills m-pills-selected'
      }
      else {
        return 'm-pills';
      }
    }
    return 'm-pills'
  }
  getLocationNameById(id): string {
    for (let i = 0; i < this.warehouseList.length; i++) {
      if (this.warehouseList[i].locationId == id) {
        return this.warehouseList[i].locationName;
      }
    }
    return '';
  }
  getLocationList() {
    this._ngxSpinner.show();
    this._locationService.locationForlist('warehouse').subscribe(data => {
      try {
        this.warehouseList = data;
      }
      catch (e) { }
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  getLocationProductItemsById(locationId: number, page?: number) {
    this._ngxSpinner.show();
    this._locationService.getLocationProductItems(locationId, page, 20).subscribe(data => {
      try {
        this.locationProductItems = data;
      } catch (e) { }
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  getLocationProductItems(page?: number) {
    this._ngxSpinner.show();
    this._locationService.getAllLocationProductItems('warehouse', page, 'all').subscribe(data => {
      this._ngxSpinner.hide();
      if (this.locationId == undefined || this.locationId == null || this.locationId == 0) {
        this.locationProductItems = data
      }
    }, error => this._ngxSpinner.hide());
  }
  changeWarehouse(war) {
    if (war == 'all') {
      this.config.allWarehouse = true;
      this.config.individualWarehouse = false;
      this._router.navigate(['/inventorymonitoringandplanning', 'stocks']);
    }
    else {
      this.config.allWarehouse = false;
      if (!this.config.individualWarehouse) {
        this.config.individualWarehouse = true;
        //this._router.navigate(['/inventorymonitoringandplanning','stocks',this.warehouseList[0].locationId]);
      }
    }
  }
  selectAllDeslect() {
    for (let i = 0; i < this.locationProductItems.content.length; i++) {
      if (this.allselection) {
        this.locationProductItems.content[i].selected = false;
      }
      else {
        this.locationProductItems.content[i].selected = true;
      }
    }
  }
  onReorderclick() {
    this.tempReorderDetails = [];
    for (let i = 0; i < this.locationProductItems.content.length; i++) {
      if (this.locationProductItems.content[i].selected) {
        this.tempReorderDetails.push(
          {
            'locationId': this.locationProductItems.content[i].locationId,
            'locationName': this.locationProductItems.content[i].locationName,
            'productPartCode': this.locationProductItems.content[i].productPartCode,
            'productDescription': this.locationProductItems.content[i].productDescription,
            'reorderQuantity': (Number(this.locationProductItems.content[i].mslQuantity) - Number(this.locationProductItems.content[i].okQuantity))
          });
      }
    }
    this.reorderModal.show();
  }
  onReorderHide() {
    this.tempReorderDetails = [];
  }
  changeInventoryType(type) {
    this.allselection = false;
    if (type == 'all') {
      this.config.allItem = true;
      this.config.nonserial = false;
      this.config.serial = false;
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, 0, 'all');
      }
      else {
        this.getAllLocationProductItemsByProductType(0, 'all');
      }
    }
    if (type == 'serialized') {
      this.config.allItem = false;
      this.config.serial = true;
      this.config.nonserial = false;
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, 0, 'serialiezed');
      }
      else {
        this.getAllLocationProductItemsByProductType(0, 'serialiezed');
      }
    }
    if (type == 'nonserialized') {
      this.config.allItem = false;
      this.config.serial = false;
      this.config.nonserial = true;
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, 0, 'nonserialiezed');
      }
      else {
        this.getAllLocationProductItemsByProductType(0, 'nonserialiezed');
      }
    }
  }
  getLocationProductItemByLocationidAndItemType(locationId, pageNumber, type) {
    this._ngxSpinner.show();
    this._locationService.getLocationProductItems(locationId, pageNumber, type).subscribe(data => {
      this.locationProductItems = data;
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  getAllLocationProductItemsByProductType(pageNumer, type) {
    this._ngxSpinner.show();
    this._locationService.getAllLocationProductItems('warehouse', pageNumer, type).subscribe(data => {
      this.locationProductItems = data;
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  submitReorder() {
    this._ngxSpinner.show();
    this._locationService.submitreporder(this.tempReorderDetails).subscribe(data => {
      this.reorderModal.hide();
      this._ngxSpinner.hide();
      this.showMessage('Re-Order has been submitted');
    }, error => {
      this._ngxSpinner.hide()
      this.showAlert(`Error: ${error.error.message}`);
    });
  }
  showAlert(message) {
    this.alertDetails.type = 'danger';
    this.alertDetails.message = message;
    this.alertWindow.show();
  }
  showMessage(message) {
    this.alertDetails.type = 'success';
    this.alertDetails.message = message;
    this.alertWindow.show();
  }
  onPageChanged($event) {
    if (this.config.allItem) {
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, ($event.page - 1), 'all');
      }
      else {
        this.getAllLocationProductItemsByProductType(($event.page - 1), 'all');
      }
    }
    if (this.config.serial) {
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, ($event.page - 1), 'serialiezed');
      }
      else {
        this.getAllLocationProductItemsByProductType(($event.page - 1), 'serialiezed');
      }
    }
    if (this.config.nonserial) {
      if (this.locationId && this.locationId > 0) {
        this.getLocationProductItemByLocationidAndItemType(this.locationId, ($event.page - 1), 'nonserialiezed');
      }
      else {
        this.getAllLocationProductItemsByProductType(($event.page - 1), 'nonserialiezed');
      }
    }
  }
  getMslPercent(okQty, msl): number {
    return (okQty * 100) / msl;
  }
}
