import { Component, OnInit, OnDestroy } from '@angular/core';
import { LocationForList, LocationProductItemResponse, Page } from '../../services/model';
import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-inventorymonitoring',
  templateUrl: './inventorymonitoring.component.html',
  styleUrls: ['./inventorymonitoring.component.scss']
})
export class InventorymonitoringComponent implements OnInit, OnDestroy {
  menueCards = [{
    id: 1,
    name: 'Inventory-Monitoring and Planing',
    cssClass: 'm-card-menue m-card-menue-selected'
  }, {
    id: 2,
    name: 'Manage Inventory Threshold',
    cssClass: 'm-card-menue'
  }, {
    id: 3,
    name: 'PAN India Stock Status-Summary',
    cssClass: 'm-card-menue'
  }, {
    id: 3,
    name: 'Stock Updated Date',
    cssClass: 'm-card-menue'
  }]

  currentDateTime: Date = new Date();
  interval;
  warehouseList: Array<LocationForList> = [];
  locationProductItems: Page<LocationProductItemResponse> = new Page<LocationProductItemResponse>();
  locationId: number = 0;
  searchWarehouseKey: string;
  config = {
    allWarehouse: true,
    individualWarehouse: false,
    allItem: true,
    serial: false,
    nonserial: false
  }

  constructor(private _locationService: LocationService, private _ngxSpinner: NgxSpinnerService, private _activatedroute: ActivatedRoute, private _router: Router) {
    this.interval = setInterval(() => {
      this.currentDateTime = new Date();
    }, 1000);
  }
  getClass(id): any {
    for (let i = 0; i < this.menueCards.length; i++) {
      if (this.menueCards[i].id == id) {
        return this.menueCards[i].cssClass;
      }
    }
    return 'm-card-menue';
  }
  getPillsClass(type): any {
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
        return 'm-pills m-pills-selected'
      }
      else {
        return 'm-pills';
      }
    }
    return 'm-pills'
  }
  ngOnInit() {
    //this.getLocationList();
    //this.getLocationProductItems();
    this._activatedroute.params.subscribe(pram => {
      this.locationId = pram['id'];
      if (this.locationId != undefined && this.locationId != null && this.locationId != 0) {
        this.config.allItem = true;
        this.config.individualWarehouse = true;
        this.config.allWarehouse = false;
        this.config.nonserial = false;
        this.config.serial = false;

        //  this.getLocationProductItemsById(this.locationId);
      }
    });
  }
  getLocationList() {
    this._ngxSpinner.show();
    this._locationService.locationForlist("Warehouse").subscribe(data => {
      try {
        this.warehouseList = data;
      }
      catch (e) { }
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  getLocationProductItemsById(locationId: number, page?: number) {
    this._ngxSpinner.show();
    this._locationService.getLocationProductItems(locationId, page, 20, 'all').subscribe(data => {
      this._ngxSpinner.hide();
      this.locationProductItems = data
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
      this._router.navigate(['/inventorymonitoringandplanning'])
    }
    else {
      this.config.allWarehouse = false;
      if (!this.config.individualWarehouse) {
        this.config.individualWarehouse = true;
        this._router.navigate(['/inventorymonitoringandplanning', this.warehouseList[0].locationId]);
      }
    }
  }
  ngOnDestroy() {
    clearInterval(this.interval);
  }
}
