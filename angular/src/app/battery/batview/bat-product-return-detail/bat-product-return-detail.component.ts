import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { LpReturnOrderResponse } from '../../../services/model';
import { PdfServiceService } from '../../../services/pdf-service.service';
import { ProductReturnService } from '../../../services/productreturnservice';
import { BtProductReturnService } from '../../bat_services/bt-product-return.service';
import { BtProductReturnResponse } from '../../bt_model';
import { BtProductreturnorderDetailsDataTransfer } from './bat-product-return-detail.datatrasfer';

@Component({
  selector: 'app-bat-product-return-detail',
  templateUrl: './bat-product-return-detail.component.html',
  styleUrls: ['./bat-product-return-detail.component.scss']
})
export class BatProductReturnDetailComponent implements OnInit {
  @ViewChild('receiveOrder', { static: false }) receiveOrder: ModalDirective;
  @ViewChild('confirmationPopup',{static:false}) confirmationPopup:ModalDirective;
  @ViewChild('addReturnItems',{static:false}) addReturnItems:ModalDirective;
  openModels = { receiveOrder: false };
  openItemReturnModel = {addReturnItems : false};
  productReturnOrderResponse: LpReturnOrderResponse = new LpReturnOrderResponse(); 
  btProductOrderReturnRes : BtProductReturnResponse = new BtProductReturnResponse();
  _cFlag: string;
  returnId;
  loggedInuser;
  constructor(
    private _activatedRoute: ActivatedRoute,
    private _ngxspinner: NgxSpinnerService,
    private _router: Router,
    private _global: GlobalserviceService, private _pdfService:PdfServiceService,
    private _dataTransfer: BtProductreturnorderDetailsDataTransfer, private _user: Loginservices,    
   private _btProductReturnService :BtProductReturnService) {
    this.loggedInuser = this._user.getLoggedInUser();
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  ngOnInit() {
    this._activatedRoute.params.subscribe(param => {
      this.returnId = param['returnorderid'];
      this.getProductReturnOrder(this.returnId);
      this._dataTransfer.btProductReturnRes = new BtProductReturnResponse();
      this._dataTransfer.btProductReturnRes.id = this.returnId;
    });
  }
  getProductReturnOrder(returnorderId) {
    this._ngxspinner.show();
    this._btProductReturnService.getProductReturnOrderById(returnorderId).subscribe(data => {
      this.btProductOrderReturnRes = data;
      this._dataTransfer.btProductReturnRes = data;
      this._ngxspinner.hide();
    }, error => this._ngxspinner.hide());
  }
  onItemReceive($event) {
    this._global.alert({ type: '', message: `Updated successfully` });
    this.receiveOrder.hide();
    this.getProductReturnOrder(this.returnId); 
  }
  onReceiveCancled($event) {
    this.receiveOrder.hide();
  }
  submitReturnORder(status) {
    console.log(status)
    this._ngxspinner.show();
    this._btProductReturnService.changeStatusOfProductReturn(this.returnId,status).subscribe(data => {
      this._ngxspinner.hide();
      this.getProductReturnOrder(this.returnId);
    }, error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  addBtReturnItems(){  
    this._global.alert({type: '', message: 'Items Created Successfully'});
    this.addReturnItems.hide();  
    this.getProductReturnOrder(this.returnId);    
    this._router.navigate(['/bat_product_returnOrder', this.productReturnOrderResponse.returnOrderId]);
  }
  }


