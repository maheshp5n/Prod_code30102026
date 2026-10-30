import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { LocationForList } from '../../../services/model';
import { BtProductReturnService } from '../../bat_services/bt-product-return.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BtMyInventoryResponse, BtProductReturnRequest, ProductReturnItems } from '../../bt_model';

@Component({
  selector: 'app-bt-create-product-return',
  templateUrl: './bt-create-product-return.component.html',
  styleUrls: ['./bt-create-product-return.component.css']
})
export class BtCreateProductReturnComponent implements OnInit {

  @Output()
  onSaveBtProductReturn: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  dateCustomClasses: DatepickerDateCustomClasses[];
  today = new Date();
  loginData;
  parentLocationName: string = '';
  locationForList: Array<LocationForList> = [];
  productReturnItemsList: Array<BtMyInventoryResponse> = [];
  btreturnItems: Array<ProductReturnItems> = [];
  createBtProductReturnReq: BtProductReturnRequest = new BtProductReturnRequest();
  constructor(private _spinnerService: NgxSpinnerService, private _btWareHouseservice: BtwarehouseService,
    private loginservice: Loginservices, private _btProductReturnService: BtProductReturnService, private _global: GlobalserviceService) { }


  ngOnInit(): void {
    this.getLoggedInUser();
    this.getParentLocation();
    this.getLIstOfLocation();
    this.getListOfPendingItemToReturn();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  getParentLocation() {
    this._spinnerService.show();
    this._btWareHouseservice.getLocationById(this.loginData.locationId).subscribe(data => {
      this._spinnerService.hide();
      this.createBtProductReturnReq.returnOrderDestinationLocationId = data.parentLocationId;
    }, error => {
      this._spinnerService.hide();
    })

  }

  getLIstOfLocation() {
    this._spinnerService.show();
    this._btWareHouseservice.getLocationParentSiblingAndChilds().subscribe(data => {
      this._spinnerService.hide();
      this.locationForList = data;
    }, error => this._spinnerService.hide());

  }
  getListOfPendingItemToReturn() {
    this._spinnerService.show();
    this._btProductReturnService.btPendingItemToReturn().subscribe(data => {
      this._spinnerService.hide();
      this.productReturnItemsList = data;

    }, error => {
      this._spinnerService.hide();
    })
  }
  getLocationId(locId) {
    this.createBtProductReturnReq.returnOrderDestinationLocationId = locId;
    console.log(this.createBtProductReturnReq.returnOrderDestinationLocationId);

  }
  saveProductReturn() {
    if (this.createBtProductReturnReq.returnOrderShippingMode == '' || this.createBtProductReturnReq.returnOrderShippingMode == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Shipping Mode' });
    }
    else if (this.createBtProductReturnReq.returnOrderShiptoName == '' || this.createBtProductReturnReq.returnOrderShiptoName == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Shipping Name' });
    }
    else if (this.createBtProductReturnReq.returnOrderTrackingNumber == '' || this.createBtProductReturnReq.returnOrderTrackingNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Tracking Number' });
    }
    else if (this.createBtProductReturnReq.returnOrderDate == null || this.createBtProductReturnReq.returnOrderDate == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Expected Delivery Date' });
    }
    else {
      this.btreturnItems= [];
      for (let i = 0; i < this.productReturnItemsList.length; i++) {
        let items = new ProductReturnItems();
        items.productId = this.productReturnItemsList[i].productId;
        items.customerInventory = this.productReturnItemsList[i].customerTotalFaultyQuantity;
        items.exicomIvnentory = this.productReturnItemsList[i].exicomTotalFaultyQuantity;
        this.btreturnItems.push(items);
      }
      this.createBtProductReturnReq.items = this.btreturnItems;
      this._spinnerService.show();
      this._btProductReturnService.btPostProductReturnOrder(this.createBtProductReturnReq).subscribe(data => {
        this._spinnerService.hide();
        this._global.alert({type:'',message:`Product Return ${data.returnOrderManualId} is Created`})
        this.onSaveBtProductReturn.emit(data);
        // this.createBtProductReturnReq  = new BtProductReturnRequest();
      }, error => {
        this._spinnerService.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      })

    }

  }

  cancelProductReturn() {
    this.onCancle.emit('cancled');
    // this.createBtProductReturnReq  = new BtProductReturnRequest();
  }

  //Validation for numric tye value
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

}
