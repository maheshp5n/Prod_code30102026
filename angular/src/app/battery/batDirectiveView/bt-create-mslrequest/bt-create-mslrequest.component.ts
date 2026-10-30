import { Component, Input, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { AssetsService } from '../../../services/assets.services';
import { Loginservices } from '../../../services/login.services';
import { LocationResponse, MSLItemRequestRequest, MSLRequest, MSLRequestResponse, MSLRequestStatus, ProductsForList } from '../../../services/model';
import { ServiceResourceService } from '../../../services/serviceresource.services';
import { BtMslrequestService } from '../../bat_services/bt-mslrequest.service';
import { BtproductService } from '../../bat_services/btproduct.service';
import { BtItemRequest, BTMslCreateRequest, BtProductList } from '../../bt_model';

@Component({
  selector: 'app-bt-create-mslrequest',
  templateUrl: './bt-create-mslrequest.component.html',
  styleUrls: ['./bt-create-mslrequest.component.css']
})
export class BtCreateMslrequestComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('MSLItemModel', { static: false }) MSLItemModel: ModalDirective;
  loc: LocationResponse = new LocationResponse();
  isEditMode: boolean = false;
  @Input()
  set location(loc: LocationResponse) {
    if (loc) {
      this.loc = loc;
      this._mslRequest.locationId = loc.locationId;
    }
    else {
      this.loc = new LocationResponse();
    }
  }
  get location(): LocationResponse {
    return this.loc;
  }
  @Input()
  set editMode(mode: boolean) {
    this.isEditMode = mode;
  }
  get editMode(): boolean {
    return this.isEditMode;
  }
  alertmessage = { class: 'alert alert-success center center-block', message: '' };
  loginData;
  products: Array<BtProductList> = [];
  mslStatus: Array<MSLRequestStatus> = [];
  _mslRequest: BTMslCreateRequest = new BTMslCreateRequest();
  _mslItemRequest: BtItemRequest = new BtItemRequest();


  @Output()
  onMSLCreated: EventEmitter<MSLRequestResponse> = new EventEmitter<MSLRequestResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor(private spinner: NgxSpinnerService, private loginservice: Loginservices, private _btmslreqservice: BtMslrequestService, private assetserive: AssetsService, private serviceResourceservice: ServiceResourceService,
    private btProductSevice: BtproductService) { }

  ngOnInit() {
    if (this._mslRequest.btItemRequest == null || this._mslRequest.btItemRequest == undefined) {
      this._mslRequest.btItemRequest = [];
    }
    this.getLoggedInUser()
    this.getStatus();
    this.getProductsList("");
    this._mslRequest.StatusId = "1";
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  cancelData() {
    this._mslRequest = new BTMslCreateRequest();
    this._mslRequest.btItemRequest = [];
    this._mslRequest.StatusId = "1";
    this._mslRequest.locationId = this.loc.locationId;
    this.onCancle.emit('cancled');
  }

  getStatus() {
    this.spinner.show()
    this._btmslreqservice.getMSLRequestForList().subscribe(data => {
      this.spinner.hide();
      this.mslStatus = data;
      console.log(this.mslStatus)
    }, err => this.spinner.hide());
  }

  getProductsList(serchKey: string) {
    this.btProductSevice.fetchProductList(serchKey).subscribe(data => {
      this.products = data
    })
  }
  productPartCode: string;
  getValueOfPartCode(returnVal) {
    for (let i = 0; i < this.products.length; i++) {
      if (returnVal == this.products[i].id) {
        this.productPartCode = this.products[i].partCode;
      }
    }

  }
  addProductList() {
    if (this._mslItemRequest.btProductId == '' || this._mslItemRequest.btProductId == undefined) {
      this.showAlert('Please Select Part Code');
      return;
    }

    else if (this._mslItemRequest.mslQty == '' || this._mslItemRequest.mslQty == undefined) {
      this._mslItemRequest.mslQty = "";
      return;
    }
    else if (this._mslItemRequest.hslQty == '' || this._mslItemRequest.hslQty == undefined) {
      this._mslItemRequest.hslQty = "";
      return;
    }
    else if (this._mslItemRequest.hslQty < this._mslItemRequest.mslQty) {
      this.showAlert('HSL Quantity Can Not Be Less Than MSL Quantity');
      return;
    }
    let alreadyExists: boolean = false;
    this._mslRequest.btItemRequest.forEach((item) => {
      if (item.btProductId == this._mslItemRequest.btProductId) {
        alreadyExists = true;
      }
    });
    if (alreadyExists) {
      this.showAlert(`Please choose different part code, ${this._mslItemRequest.productPartCode} already selected`);
      return;
    }
    let _item = new BtItemRequest();
    _item.productPartCode = this.productPartCode;
    _item.btProductId = this._mslItemRequest.btProductId;
    _item.hslQty = this._mslItemRequest.hslQty;
    _item.mslQty = this._mslItemRequest.mslQty;
    this._mslRequest.btItemRequest.push(_item);
    console.log(this._mslRequest.btItemRequest)
    this._mslItemRequest.btProductId = '';
    this._mslItemRequest.hslQty = '';
    this._mslItemRequest.mslQty = '';
  }
  removeItems(index: number) {
    let temp = [];
    for (let i = 0; i < this._mslRequest.btItemRequest.length; i++) {
      if (i != index) {
        temp.push(this._mslRequest.btItemRequest[i]);
      }
    }
    this._mslRequest.btItemRequest = temp;
  }
  saveMSLData() {
    if (this._mslRequest.locationId == '' || this._mslRequest.locationId == undefined) {
      this.showAlert('Warehouse Not Found');
      return;
    }
    if (this._mslRequest.StatusId == '' || this._mslRequest.StatusId == undefined) {
      this.showAlert('Status Not Found');
      return;
    }
    if (this._mslRequest.btItemRequest.length == 0 || this._mslRequest.btItemRequest == undefined) {
      this.showAlert('Product Items Not Found');
      return;
    }
    console.log(this._mslRequest)
    this._btmslreqservice.saveMsl(this._mslRequest).subscribe(data => {
      this.onMSLCreated.emit(data);
    }, err => this.showAlert(err.error.message));
  }
  showAlert(message: string) {
    this.alertmessage.class = 'alert alert-danger center center-block';
    this.alertmessage.message = message;
    this.popupSuccessModel.show();
  }
  showMessage(message: string) {
    this.alertmessage.class = 'alert alert-success center center-block';
    this.alertmessage.message = message;
    this.popupSuccessModel.show();
  }
}
