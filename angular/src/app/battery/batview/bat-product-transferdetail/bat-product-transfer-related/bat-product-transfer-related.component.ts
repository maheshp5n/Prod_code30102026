import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { ActionHistoryObjectAndId, LocationProductTransferLineItemResponse } from '../../../../services/model';
import { ProductTransferServices } from '../../../../services/producttransfer.service';
import { PrudcttransferDataService } from '../../../../view/producttransferdetails/producttransferdata.service';
import { BatproducttransferService } from '../../../bat_services/batproducttransfer.service';
import { BtProductTransferDataService } from '../bat-product-transfer.data.serice';

@Component({
  selector: 'app-bat-product-transfer-related',
  templateUrl: './bat-product-transfer-related.component.html',
  styleUrls: ['./bat-product-transfer-related.component.css']
})
export class BatProductTransferRelatedComponent implements OnInit {
  productTransferColumn=["S.No.","Product Request Number","Product Part code","Product Description","Producyt Type","Quantity Issued","Quantity Recieved"];
  proRoCol = [];
  objectId: any;
  page: number = 0;
  transferId: number;
  btProductTransferLineItems = [];
  ptid: any;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(
    private _activatedroute: ActivatedRoute, private _global: GlobalserviceService, private _btProductService: BatproducttransferService,
    private _spinnerservice: NgxSpinnerService, private _btProductTransferService: BatproducttransferService, private _btData: BtProductTransferDataService,
    private _spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.getBtProductTransferItems(this._btData.btProductTransferData.productTrnsferId);
    this.actionHistoryObjectAndId.objectId = '' + this._btData.btProductTransferData.productTrnsferId;
    this.actionHistoryObjectAndId.objectName = 'BtProductTransfer';
  }
  getBtProductTransferItems(itemId) {
    this.ptid = itemId;
    this._spinner.show();
    let page=0;
    this._btProductService.getProuctTransferItems(itemId,page).subscribe(data => {
      this.btProductTransferLineItems = data.content;
      this._spinner.hide();
    }, error => {
      this._spinner.hide();
    })


  }

  getProductTranfer
  advancesearchPT = {
    productTransferManualId :'',
    productPartcode: '',
    productTranferTrackingNumber:'',
    productDescription: '',
    productTransferShippingMode:'',
    productTransferShippingToName :''

  };
  resetPTItems() {
    this.advancesearchPT.productTransferManualId = '';
    this.advancesearchPT.productPartcode = '';
    this.advancesearchPT.productTranferTrackingNumber = '';
    this.advancesearchPT.productDescription = '';
    this.getBtProductTransferItems(this._btData.btProductTransferData.productTrnsferId);
  }
  searchPTitems() {
    if (this.advancesearchPT.productTransferManualId == '' &&
      this.advancesearchPT.productPartcode == '' &&
      this.advancesearchPT.productDescription == '' &&
      this.advancesearchPT.productTranferTrackingNumber == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinnerservice.show();
      this.btProductTransferLineItems = [];
      this._btProductTransferService.fetchProductTransferItemsByAdvanceSearch(this._btData.btProductTransferData.productTrnsferId, this.advancesearchPT,).subscribe(data => {
        this._spinnerservice.hide();
        this.btProductTransferLineItems = data;
      }, error => {
        this._spinnerservice.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      })

    }


  }


}
