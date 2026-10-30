import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { Loginservices } from '../../../services/login.services';
import { BtPendingItemLIstASPerLocation, PendingLocationLIstForTransfer, BtProductTransferRequest, BtProductTransferItems, AvailableStockInInventory } from '../../bt_model';

@Component({
  selector: 'app-newbatproducttransfer',
  templateUrl: './newbatproducttransfer.component.html',
  styleUrls: ['./newbatproducttransfer.component.css']
})
export class NewbatproducttransferComponent implements OnInit {
  pendingLocationTransferItem: Array<PendingLocationLIstForTransfer> = [];
  pendingITemsLIstByLocation: Array<BtPendingItemLIstASPerLocation> = [];
  btProductTransferReq: BtProductTransferRequest = new BtProductTransferRequest();
  btProductITemsReq: BtProductTransferItems = new BtProductTransferItems();
  btProductItemsList: Array<BtProductTransferItems> = [];
  show = false;
  destinationLocatonId: number = 0;
  issuingQty;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  loginData;
  availableStock : AvailableStockInInventory = new AvailableStockInInventory();

  constructor(private _global: GlobalserviceService, private _btPrService: BatproducttransferService,
    private _spinner: NgxSpinnerService, private router: Router, private _btWareHouseservice: BtwarehouseService,
    private loginservice: Loginservices) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit(): void {
    this.getPendingItemAsPerLocation(); 4
    this.getLoggedInUser();
  }

  getPendingItemAsPerLocation() {
    this._spinner.show();
    this._btPrService.getPendinglocationforitemtransfer().subscribe(data => {
      this.pendingLocationTransferItem = data;
      this._spinner.hide();
    }, error => {
      this._spinner.hide();
    })

  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    console.log(this.loginData);
  }

  getValueOfSourceLocation(value) {
    this.destinationLocatonId = value;
    this.getListOfITemsAsPerLocationId();
  }

  getListOfITemsAsPerLocationId() {
    this._spinner.show();
    this._btPrService.getPendignITemsForTransfer(this.destinationLocatonId).subscribe(data => {
      data.forEach(d => {
        d.issueingQuantity = d.productRequestItemRequiredQty - d.productRequestItemIssuedQty;
      });
      this.pendingITemsLIstByLocation = data;
      this._spinner.hide();
      for (let i = 0; i < this.pendingITemsLIstByLocation.length; i++) {
        this.getAvailableStock(this.loginData.locationId, this.pendingITemsLIstByLocation[i].productId);
      }
    }, error => {
      this._spinner.hide();
    })

  }
  getAvailableStock(locationId, productId) {
    this._spinner.show();
    this._btWareHouseservice.getAvailableStock(locationId, productId).subscribe(data => {
      this._spinner.hide();
      for(let i=0;i<this.pendingITemsLIstByLocation.length;i++){
        if(this.pendingITemsLIstByLocation[i].productId==data.productId){
          this.pendingITemsLIstByLocation[i].okQuantity=data.exicomTotalOkQuantity;
          this.pendingITemsLIstByLocation[i].productDescription=data.productDescription;
        }
      }
    }, err => {
      this._spinner.hide();
    })
  }
  transferItems() {
    if (this.btProductTransferReq.shippingMode == '' || this.btProductTransferReq.shippingMode == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Shipping Mode' });
      return;
    }
    else if (this.btProductTransferReq.shippingMode == '' || this.btProductTransferReq.shippingMode == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Shipping Name' });
      return;
    }
    else if (this.btProductTransferReq.trackingNumber == '' || this.btProductTransferReq.trackingNumber == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Tracking Number' });
      return;
    }
    else if (this.btProductTransferReq.actualDeliveryDate == null || this.btProductTransferReq.actualDeliveryDate == undefined) {
      this._global.alert({ type: 'alert', message: 'Please Fill Expected Delivery Date' });
      return;
    }
    for (let i = 0; i < this.pendingITemsLIstByLocation.length; i++) {
      if (this.pendingITemsLIstByLocation[i].issueingQuantity <= 0) {
        continue;
      }
      this.btProductTransferReq.items.push({ productRequestItemId: this.pendingITemsLIstByLocation[i].productRequestItemId, issueQuantity: this.pendingITemsLIstByLocation[i].issueingQuantity });
    }
    if (this.btProductTransferReq.items.length <= 0) {
      this._global.alert({ type: 'alert', message: 'Please Provide A Valid Quantity For Atleast One Item' });
      return;
    }
    this.btProductTransferReq.destinationLocationId = this.destinationLocatonId;
    this._spinner.show();
    this._btPrService.postRequestForProductTransfer(this.btProductTransferReq).subscribe(data => {
      this._spinner.hide();
      this._global.alert({ type: '', message: `Product Transfer ${data.manualId} Is Created!` })
      this.router.navigate(['/bat_product_transfer']);
    }, error => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })



  }


}
