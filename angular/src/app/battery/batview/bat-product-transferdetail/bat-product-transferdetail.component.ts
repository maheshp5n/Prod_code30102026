import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { PrudcttransferDataService } from '../../../view/producttransferdetails/producttransferdata.service';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtProductTransferResponse } from '../../bt_model';
import { BtProductTransferDataService } from './bat-product-transfer.data.serice';

@Component({
  selector: 'app-bat-product-transferdetail',
  templateUrl: './bat-product-transferdetail.component.html',
  styleUrls: ['./bat-product-transferdetail.component.css']
})
export class BatProductTransferdetailComponent implements OnInit {

  transferIItemId: number;
  heading;
  logedInUser;
  btProductTransferDetails: BtProductTransferResponse = new BtProductTransferResponse();

  @ViewChild('transferRecieve')
  transferRecieve: ModalDirective;

  @ViewChild('ewayBillModal')
  ewayBillModal!: ModalDirective;

  modalView = false;
  isStoDocEnabled: boolean = false;

  constructor(
    private _ngxspinnnerService: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute,
    private _dataservice: PrudcttransferDataService,
    private _btTransferService: BatproducttransferService,
    private _global: GlobalserviceService,
    private _user: Loginservices,
    private _btProuctTransferData: BtProductTransferDataService
  ) {

    this._activatedRoute.params.subscribe(param => {
      this.transferIItemId = param['transferitemid'];
      this.getBtTransferItems(this.transferIItemId);
      this.checkEwayBillStatus(this.transferIItemId);

      this._btProuctTransferData.btProductTransferData = new BtProductTransferResponse();
      this._btProuctTransferData.btProductTransferData.productTrnsferId = this.transferIItemId;

      this.logedInUser = _user.getLoggedInUser();
    });
  }

  ngOnInit() {
  }

  getOpenProductRecieve() {
    this.transferRecieve.show();
    this.heading = 'Product Receive';
  }

  getBtTransferItems(tranferId: number) {
    this._ngxspinnnerService.show();
    this._btTransferService.getProductTransferByPtId(tranferId).subscribe(data => {
      this._ngxspinnnerService.hide();
      this.btProductTransferDetails = data;
      this._btProuctTransferData.btProductTransferData = data;
    }, error => {
      this._ngxspinnnerService.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  onPtrCreated($event) {
    this._global.alert({ type: '', message: 'Product Received Succesfully!' });
    this.transferRecieve.hide();
    this.getBtTransferItems(this.transferIItemId);
  }

  cancelData($event) {
    this.transferRecieve.hide();
  }

  getClose() {
    this.transferRecieve.hide();
  }

  openEwayBillPopup() {
    this.heading = 'E-Way Bill Details';
    this.ewayBillModal.show();
    this.modalView = true;

    this._btTransferService
      .getEwayBillByTransferId(this.transferIItemId)
      .subscribe(res => {
        if (res && res.number === 1) {
          this._global.alert({
            type: '',
            message: 'E-Way Bill already saved'
          });
        }
      });
  }

  closeEwayBillPopup() {
    this.ewayBillModal.hide();
    this.modalView = false;
  }

  onEwayBillSaved(event: any) {
    this.checkEwayBillStatus(this.transferIItemId);
    this._global.alert({ type: '', message: 'E-Way Bill Saved Successfully!' });
    this.closeEwayBillPopup();
  }

  ngAfterViewInit() {
  }

  checkEwayBillStatus(transferId: number) {
    this._btTransferService
      .getEwayBillByTransferId(transferId)
      .subscribe(res => {
        this.isStoDocEnabled = res.number === 1;

        setTimeout(() => {
        }, 0);
      });
  }
}
