import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { BRCInventoryReceiveResponse, BRCRepairInventoryReceiveItemRequest, BRCRepairInventoryDispatchRequest, BRCRepairInventoryReceiveRequest, BRCRepariInventoryProductConsumedRequest, BRCRepariInventoryProductConsumedResponse, BRCRepiarResolutionMethodChoosen } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-manageinventorydetails',
  templateUrl: './manageinventorydetails.component.html',
  styleUrls: ['./manageinventorydetails.component.scss']
})
export class ManageinventorydetailsComponent implements OnInit {
  @ViewChild('ManageInventoryModel', { static: false }) ManageInventoryModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('AssignToEngModel', { static: false }) AssignToEngModel: ModalDirective;
  @ViewChild('ReceivFromEngModel', { static: false }) ReceivFromEngModel: ModalDirective;
  @ViewChild('DispatchCustomerModel', { static: false }) DispatchCustomerModel: ModalDirective;
  @ViewChild('ProductConsumedModel', { static: false }) ProductConsumedModel: ModalDirective;

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: number;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_ACCOUNT_HEADING: string = 'Edit Manage Inventory';
  ASSIGN_TO_ENG: string = 'Assign To Engineer';
  RECEIVE_FROM_ENG: string = 'Receive From Engineer';

  isModelShow: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  message: string = '';

  bRCInventoryReceiveResponse: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();

  inventoryList: Array<BRCInventoryReceiveResponse> = [];
  inventory: BRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();

  brcRepairInventoryReceiveItemRequest: BRCRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
  dispatchRequest: BRCRepairInventoryDispatchRequest = new BRCRepairInventoryDispatchRequest();

  consumedResponse: BRCRepariInventoryProductConsumedResponse = new BRCRepariInventoryProductConsumedResponse();
  consumedList: Array<BRCRepariInventoryProductConsumedResponse> = [];

  consumePage: number = 0;
  consumeFirst: boolean;
  consumeLast: boolean;
  consumeSize: number;
  totalconsumed: number;
  consumeSerial: number;

  isAssignToEngModel: boolean = false;
  isReceivFromEngModel: boolean = false;
  isDispatchCustomerModel: boolean = false;
  isProductConsumedModel: boolean = false;
  resolutionMehod: Array<BRCRepiarResolutionMethodChoosen> = [];
  _cFlag: string;

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private inventoryService: ManageInventoryService, private loginService: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getInventory(this.sapId);
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
  }

  getInventory(id: number) {
    this.spinner.show();
    this.inventoryService.getMIByMiId(id).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.inventory = data;
      this.resolutionMehod = data.brcRepairResolutionMethod;
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getConsumedList(sapId: number, consumePage: number) {
    this.spinner.show();
    this.inventoryService.getConsumeRelatedList(sapId, consumePage).subscribe(data => {
      this.consumedList = data.content;
      this.totalconsumed = data.totalElements;
      this.consumeFirst = data.first;
      this.consumeLast = data.last;
      this.consumeSize = data.size;
      this.consumeSerial = (((consumePage) * this.consumeSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  purchaseDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  warrantyDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  amcDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  systemDisplay = {
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
  changePurchaseDisplay() {
    this.purchaseDisplay.open = !this.purchaseDisplay.open
    if (this.purchaseDisplay.open) {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeWarrantyDisplay() {
    this.warrantyDisplay.open = !this.warrantyDisplay.open
    if (this.warrantyDisplay.open) {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeAmcDisplay() {
    this.amcDisplay.open = !this.amcDisplay.open
    if (this.amcDisplay.open) {
      this.amcDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.amcDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.spinner.hide();
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getInventory(this.sapId);
    }
    else {
      this.getConsumedList(this.sapId, this.consumePage);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }

  ManageInventoryModelhide() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.ManageInventoryModel.hide();
  }
  createEditModelshow(inventroy: BRCInventoryReceiveResponse) {
    this.spinner.show();
    this.inventoryService.getMIByMiId(inventroy.receivingId).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.bRCInventoryReceiveResponse = data;
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.ManageInventoryModel.show();
  }

  cancelData() {
    this.bRCInventoryReceiveResponse = new BRCInventoryReceiveResponse();
    this.ManageInventoryModel.hide();
  }

  onManageInventoryCreated($event) {
    let inventoryEvent = new BRCInventoryReceiveResponse();
    this.spinner.show();
    this.inventoryService.getMIByMiId($event.receivingId).subscribe(data => {
      this.spinner.hide()
      inventoryEvent = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
    this.inventoryList.push(inventoryEvent);
    this.inventory = $event;
    this.getConsumedList(this.sapId,this.consumePage);
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }

  engToAssignView(inventory: BRCInventoryReceiveResponse) {
    this.brcRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
    this.brcRepairInventoryReceiveItemRequest.receiveDetailsId = inventory.receivingId;
    this.brcRepairInventoryReceiveItemRequest.receivingName = inventory.receivingName;
    this.brcRepairInventoryReceiveItemRequest.legalEntityId = inventory.legalEntityId;

    this.isEditMode = false;
    this.heading = this.ASSIGN_TO_ENG;
    this.AssignToEngModel.show();
  }
  AssignToEngModelhide() {
    this.brcRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
    this.AssignToEngModel.hide();
  }
  onAssignToEngCreated($event: BRCInventoryReceiveResponse) {
    let inventoryEvent = new BRCInventoryReceiveResponse();
    this.spinner.show();
    this.inventoryService.getMIByMiId($event.receivingId).subscribe(data => {
      this.spinner.hide()
      inventoryEvent = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
    });
    this.inventoryList.push(inventoryEvent);
    this.inventory = $event;

  }
  cancelDataAssignToEng() {
    this.brcRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
    this.AssignToEngModel.hide();
  }

  receiveFromEngView(inventory: BRCInventoryReceiveResponse) {
    this.brcRepairInventoryReceiveItemRequest = new BRCRepairInventoryReceiveItemRequest();
    this.brcRepairInventoryReceiveItemRequest.receiveDetailsId = inventory.receivingId;
    this.brcRepairInventoryReceiveItemRequest.receivingName = inventory.receivingName;
    this.brcRepairInventoryReceiveItemRequest.repairId = inventory.repairId;
    this.brcRepairInventoryReceiveItemRequest.serviceResourceId = inventory.engineerId;
    this.brcRepairInventoryReceiveItemRequest.engineerName = inventory.engineerName;

    this.brcRepairInventoryReceiveItemRequest.brcRepariType = inventory.repairTypeId;
    this.brcRepairInventoryReceiveItemRequest.engineerIssuedDate = inventory.engineerIssuedDate;
    this.brcRepairInventoryReceiveItemRequest.materialSortage = inventory.materialSortage;

    this.isEditMode = true;
    this.heading = this.RECEIVE_FROM_ENG;
    this.AssignToEngModel.show();
  }

  dispatchToCustomerView(inventory: BRCInventoryReceiveResponse) {
    this.dispatchRequest = new BRCRepairInventoryDispatchRequest();
    this.dispatchRequest.repariItemId = inventory.repairId;
    this.dispatchRequest.dispatcherName = inventory.engineerName;
    this.isEditMode = false;
    this.DispatchCustomerModel.show();
  }
  dispatchCustomerModelhide() {
    this.dispatchRequest = new BRCRepairInventoryDispatchRequest();
    this.DispatchCustomerModel.hide();
  }
  onDispatchCustomerCreated($event: BRCInventoryReceiveResponse) {
    let inventoryEvent = new BRCInventoryReceiveResponse();
    this.spinner.show();
    this.inventoryService.getMIByMiId($event.receivingId).subscribe(data => {
      this.spinner.hide()
      inventoryEvent = data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
    this.inventoryList.push(inventoryEvent);
    this.inventory = $event;

  }
  cancelDataDispatchCustomer() {
    this.dispatchRequest = new BRCRepairInventoryDispatchRequest();
    this.DispatchCustomerModel.hide();
  }

  showProductConsumedView(inventory: BRCInventoryReceiveResponse) {
    this.consumedResponse = new BRCRepariInventoryProductConsumedResponse();
    this.consumedResponse.miCode = inventory.receivingId;
    this.consumedResponse.miManualId = inventory.receivingName;
    this.consumedResponse.repairId = inventory.repairId;
    this.isEditMode = false;
    this.ProductConsumedModel.show();
  }
  productConsumedModelhide() {
    this.consumedResponse = new BRCRepariInventoryProductConsumedResponse();
    this.ProductConsumedModel.hide();
  }
  cancelDataProductConsumed() {
    this.consumedResponse = new BRCRepariInventoryProductConsumedResponse();
    this.ProductConsumedModel.hide();
  }
  onProductConsumedCreated($event: BRCRepariInventoryProductConsumedResponse) {
    this.consumedList.unshift($event);
  }

  setPagePreviousConsume(event: any) {
    event.preventDefault();
    this.consumePage = this.consumePage - 1;
    this.getConsumedList(this.sapId, this.consumePage);
  }
  setPageConsume(event: any) {
    event.preventDefault();
    this.getConsumedList(this.sapId, this.consumePage);
  }
  setPageNextConsume(event: any) {
    event.preventDefault();
    this.consumePage = this.consumePage + 1;
    this.getConsumedList(this.sapId, this.consumePage);
  }
}
