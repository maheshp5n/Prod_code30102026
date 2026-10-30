import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  WarehousedetailsRequest,
  WarehousedetailsResponse,
  ProductsResponse
} from '../../services/model';
import { WarehouseService } from '../../services/warehouse.service';
import { ModalDirective } from 'ngx-bootstrap';
import { ligalEntityService } from '../../services/ligalentity.services';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-warehousedetails',
  templateUrl: './warehousedetails.component.html',
  styleUrls: ['./warehousedetails.component.css']
})
export class WarehousedetailsComponent implements OnInit {

  warehouse: WarehousedetailsResponse = new WarehousedetailsResponse();
  isEditMode: boolean = false;
  heading: string;
  CREATE_Warehouse_HEADING: string = 'New Warehouse';
  EDIT_Warehouse_HEADING: string = 'Edit Warehouse';
  legalEntityList;
  warehouseListById;
  warehouseId;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: string = '';
  newWarehouse: WarehousedetailsRequest = new WarehousedetailsRequest();
  warehouseList: Array<WarehousedetailsResponse> = [];
  productList: Array<ProductsResponse> = [];
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  message: string;

  @ViewChild('createWarehouseModel', { static: false }) createWarehouseModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private _warehouseervice: WarehouseService, private ligalentitservice: ligalEntityService) {
    this._activatedRoute.params.subscribe(pram => {
      this.warehouseId = pram['id'];
    });
  }

  ngOnInit() {
    this.getWarehouseById();
  }

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getWarehouseById();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }

  getLegalEntityId() {
    this.ligalentitservice.getLegalEntityId().subscribe(data => {
      this.legalEntityList = data;
    });
  }

  showCreateWarehouseView() {
    this.isEditMode = false;
    this.heading = this.CREATE_Warehouse_HEADING;
    this.createWarehouseModel.show();
  }

  cancelData() {
    this.createWarehouseModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Warehouse_HEADING;
    this.newWarehouse = new WarehousedetailsRequest();
  }

  getWarehouseById() {
    this.spinner.show();
    this._warehouseervice.getWarehouseById(this.warehouseId).subscribe(data => {
      this.warehouse = data;
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
  }

  onClickEdit(warehouse, warehouseId) {
    this.spinner.show();
    this._warehouseervice.getWarehouseById(warehouseId).subscribe((data) => {
      this.newWarehouse.warehouseId = data.warehouseId;
      this.newWarehouse.warehouseName = data.warehouseName;
      this.newWarehouse.warehousePlant = data.warehousePlant;
      this.newWarehouse.warehouseEmail = data.warehouseEmail;
      this.newWarehouse.legalEntityId = data.legalEntityId;
      this.newWarehouse.legalEntityName = data.legalEntityName;
      this.newWarehouse.createdByFirstName = data.createdByFirstName;
      this.newWarehouse.createdByLastName = data.createdByLastName;
      this.newWarehouse.createdDateTime = data.createdDateTime;
      this.newWarehouse.modifiedByFirstName = data.modifiedByFirstName;
      this.newWarehouse.modifiedByLastName = data.modifiedByLastName;
      this.newWarehouse.modifiedDate = data.modifiedDate;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message=err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_Warehouse_HEADING;
    this.createWarehouseModel.show();
  }

  onWarehouseCreated($event: WarehousedetailsResponse) {
    this.getWarehouseById();
    this.warehouse = $event;
  }
}
