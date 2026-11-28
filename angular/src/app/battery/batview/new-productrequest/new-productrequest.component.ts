import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LocationForList, LocationResponse, LPItemRequest, LPRequest, ProductsForList } from '../../../services/model';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BatNewProductRequest, BtProductList, ProductRequestItem } from '../../bt_model';

@Component({
  selector: 'app-new-productrequest',
  templateUrl: './new-productrequest.component.html',
  styleUrls: ['./new-productrequest.component.css']
})
export class NewProductrequestComponent implements OnInit {
  batProductResponse;
  _location: LocationResponse = new LocationResponse();
  dateCustomClasses: DatepickerDateCustomClasses[];
  locationId: number;
  today = new Date();
  productItemList: Array<ProductsForList> = [];
  locationForList: Array<LocationForList> = [];
  partcodeDescription: string;
  _lpitems: LPItemRequest = new LPItemRequest();
  _lprequest: LPRequest = new LPRequest();
  createPrReq: BatNewProductRequest = new BatNewProductRequest();
  btProductList: Array<BtProductList> = [];
  prItemReq: ProductRequestItem = new ProductRequestItem();
  parentLocationRes: LocationResponse = new LocationResponse();
  btProductId : number;
  alertMessage = { alertClass: 'alert alert-success center center-block', alertMessage: '' };
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  avilableStockAtSource: any = {
    exicomTotalOkQuantity: 0,
    quantity_in_on_hand: 0
  };
  avilabStockAtDestination: any = {
    exicomTotalOkQuantity: 0,
    quantity_in_on_hand: 0
  };
  constructor(
    private _btWareHouseservice: BtwarehouseService,
    private _spinnerService: NgxSpinnerService,
    private _btProdReqService: BtproductrequestService,
    private _activatedRoute: ActivatedRoute,
    private _global: GlobalserviceService,
    private _router: Router) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.queryParams.subscribe(param => {
      if (param != undefined) {
        this.batProductResponse = param;
      }
    });
  }

  ngOnInit() {
    this.getSiblingsLocation();
    this.getParentLocation();
  }

  getParentLocation() {
    this._spinnerService.show();
    this._btWareHouseservice.getLocationById(this.batProductResponse.locationId).subscribe(data => {
      this.parentLocationRes = data;
      this.createPrReq.sourceLocationId = this.parentLocationRes.parentLocationId;
      this._spinnerService.hide();
    }, error => {
      this._spinnerService.hide();
    })

  }
  getSourceLocationId($event) {
    this.createPrReq.sourceLocationId = $event.locationId;
  }
  destinationLocation: string;
  destinationId : number;
  getSiblingsLocation() {
    this._spinnerService.show();
    this._btWareHouseservice.getLocationParentSiblingAndChilds().subscribe(data => {
      this._spinnerService.hide();
      this.locationForList = data;
      this.destinationLocation = this.locationForList[0].locationName;
      this.destinationId = this.locationForList[0].locationId;
    }, error => this._spinnerService.hide());
  }

  getLocations() {
    this._spinnerService.show();
    this._btWareHouseservice.locationForlist().subscribe(data => {
      this.locationForList = data;
      this._spinnerService.hide();
    }, err => this._spinnerService.hide());
  }

  searchBtProductList($event) {
    this._btProdReqService.fetchProductList($event.term).subscribe(data => {
      const temp = [];
      data.forEach(d => {
        let btPrList = new BtProductList();
        btPrList.id = d.id;
        btPrList.partCode = d.partCode;
        btPrList.productType = d.productType;
        btPrList.bothvalues = d.partCode + ' ' + d.productType;
        temp.push(btPrList);
      })
      this.btProductList = temp;
    })
  }
  productPartCode: string;
  getProductPartCode(value) {
    this.btProductList.forEach(d => {
      if (value == d.id) {
        this.productPartCode = d.partCode;
      }
    })
    this.btProductId = value;
    this.getAvailableStock();

  }
  getAvailableStock(){
    if(this.btProductId!=null){
      this.getAvilableStockAtSource();
      this.getAvilableStockAtDestination();
    }
  }
   getAvilableStockAtSource() {
    this.avilableStockAtSource = {
      exicomTotalOkQuantity: 0,
      quantity_in_on_hand: 0
    };
    this._btWareHouseservice.getAvailableStock(
      this.createPrReq.sourceLocationId,
      this.btProductId)
      .subscribe(data => this.avilableStockAtSource = data);
  }
  getAvilableStockAtDestination() {
    this.avilabStockAtDestination = {
      exicomTotalOkQuantity: 0,
      quantity_in_on_hand: 0
    };
    this._btWareHouseservice.getAvailableStock(
      this.destinationId,
      this.btProductId)
      .subscribe(data => this.avilabStockAtDestination = data);
  }
  addItemInList() {
    if (this.prItemReq.btProductId == null || this.prItemReq.btProductId == undefined) {
      this.showAlert('Please Select Partcode');
      return;
    }
    else if (this.prItemReq.requiredQty == null || this.prItemReq.requiredQty==0|| this.prItemReq.requiredQty == undefined) {
      this.showAlert('Please Provide Valid Quantity');
      return;
    }
    let tempItemVar = new ProductRequestItem();
    tempItemVar.requiredQty = this.prItemReq.requiredQty;
    tempItemVar.btProductId = this.prItemReq.btProductId;
    tempItemVar.partCode = this.productPartCode;
    if (this.createPrReq.items == null || this.createPrReq.items == undefined) {
      this.createPrReq.items = [];
    }
    let alreadyExist = false;
    for (let i = 0; i < this.createPrReq.items.length; i++) {
      if (this.createPrReq.items[i].btProductId == tempItemVar.btProductId) {
        alreadyExist = true;
        break;
      }
    }
    if (alreadyExist) {
      this.showAlert(`Part code ${tempItemVar.partCode} Already Exists,Please Select Different`);
      return;
    }
    this.createPrReq.items.push(tempItemVar);
    this.prItemReq.btProductId = null;
    this.prItemReq.requiredQty = null;
  }
  removeItem(indexNumber) {
    let counter = this.createPrReq.items.length;
    let temp = [];
    for (let i = 0; i < counter; i++) {
      if (i != indexNumber) {
        temp.push(this.createPrReq.items[i]);
      }
    }
    this.createPrReq.items = temp;
  }
  // for bat product request 
  saveProductRequest() {
    if (this.createPrReq.sourceLocationId == null || this.createPrReq.sourceLocationId == undefined || this.createPrReq.sourceLocationId == 0) {
      this.showAlert('Source Warehouse Details Not Found');
      return;
    }
    if (this.createPrReq.needByTime == null || this.createPrReq.needByTime == undefined) {
      this.showAlert('Expected Date Not Found');
      return;
    }
    if (this.createPrReq.description != null && this.createPrReq.description.length > 500) {
      this.showAlert('Description Must Be Less Than 500 Characters');
      return;
    }
    if (this.createPrReq.items == null || this.createPrReq.items.length < 1) {
      this.showAlert('Please Fill Product Items');
      return;
    }
    this._spinnerService.show();
    this.createPrReq.destinationLocationId = this.batProductResponse.locationId;
    this._btProdReqService.CreateNewProductRequest(this.createPrReq).subscribe(data => {
      this._spinnerService.hide();
      this._global.alert({type:'',message:`Product Request ${data.manualId} Is Created!`});
      this._router.navigate(['/bat_product_request', data.productRequestId]);
    }, error => {
      this._spinnerService.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }
  showAlert(message: string) {
    this.alertMessage.alertClass = 'alert alert-danger center center-block';
    this.alertMessage.alertMessage = message;
    this.popupSuccessModel.show();
  }
  showMessage(message: string) {
    this.alertMessage.alertClass = 'alert alert-success center center-block';
    this.alertMessage.alertMessage = message;
    this.popupSuccessModel.show();
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }


}
