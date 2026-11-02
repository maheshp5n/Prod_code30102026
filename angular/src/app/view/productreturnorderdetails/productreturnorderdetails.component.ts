import { GlobalserviceService } from './../../globalservice.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductReturnService } from '../../services/productreturnservice';
import { ActionHistoryObjectAndId, LpReturnOrderResponse } from '../../services/model';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective, ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductreturnorderDetailsDataTransfer } from './productreturnorderdetails.datatransfer';
import { Loginservices } from '../../services/login.services';
import { PdfServiceService } from '../../services/pdf-service.service';

@Component({
  selector: 'app-productreturnorderdetails',
  templateUrl: './productreturnorderdetails.component.html',
  styleUrls: ['./productreturnorderdetails.component.scss']
})
export class ProductreturnorderdetailsComponent implements OnInit {

  @ViewChild('receiveOrder') receiveOrder: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup:ModalDirective
  openModels = { receiveOrder: false };
  productReturnOrderResponse: LpReturnOrderResponse = new LpReturnOrderResponse();
  _cFlag: string;
  loggedInuser;
  constructor(private _productReturnService: ProductReturnService,
    private _activatedRoute: ActivatedRoute,
    private _ngxspinner: NgxSpinnerService,
    private _router: Router,
    private _global: GlobalserviceService, private _pdfService:PdfServiceService,
    private _dataTransfer: ProductreturnorderDetailsDataTransfer, private _user: Loginservices) {
    this.loggedInuser = this._user.getLoggedInUser();
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  ngOnInit() {
    this._activatedRoute.params.subscribe(param => {
      const id = param['returnorderid'];
      this.getProductReturnOrder(id);
      this._dataTransfer.productReturnOrderResponse = new LpReturnOrderResponse();
      this._dataTransfer.productReturnOrderResponse.returnOrderId = id;
    });
  }
  getProductReturnOrder(returnorderId) {
    this._ngxspinner.show();
    this._productReturnService.getReturnOrder(returnorderId).subscribe(data => {
      this.productReturnOrderResponse = data;
      this._dataTransfer.productReturnOrderResponse = data;
      this._ngxspinner.hide();
    }, error => this._ngxspinner.hide());
  }
  onItemReceive($event) {
    this._global.alert({ type: '', message: `Updated successfully` });
    this.receiveOrder.hide();
  }
  onReceiveCancled($event) {
    this.receiveOrder.hide();
  }
  itemAdded() {
    this.getProductReturnOrder(this.productReturnOrderResponse.returnOrderId);
    this._router.navigate(['/productreturnorder', this.productReturnOrderResponse.returnOrderId]);
  }
  submitReturnORder() {
    this._productReturnService.submitReturnOrder(this.productReturnOrderResponse.returnOrderId).subscribe(data => {
      this.getProductReturnOrder(this.productReturnOrderResponse.returnOrderId);
    }, error => {
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  createPDF() {
    this._ngxspinner.show();
    this._pdfService.createPDF('api/reurnorder', 'productReturnId',
    this._dataTransfer.productReturnOrderResponse.returnOrderId).subscribe(data => {
        const file = new Blob([data], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this._ngxspinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: '', message: "Created successfully"});
      }, err => {
        this._ngxspinner.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
  }
}
