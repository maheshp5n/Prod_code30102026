import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { PdfServiceService } from '../../../services/pdf-service.service';
import { ServiceOrderService } from '../../../services/serviceorder.servvices';
import { BtServiceOrderService } from '../../bat_services/bt-service-order.service';

@Component({
  selector: 'app-bat-serviceorderdetails',
  templateUrl: './bat-serviceorderdetails.component.html',
  styleUrls: ['./bat-serviceorderdetails.component.scss']
})
export class BatServiceorderdetailsComponent implements OnInit {
  serviceOrderId: any;
  serviceOrderDetails: any = {};

  navigationMenue = {
    details: {
      ngclass: '',
      view: false
    },
    releted: {
      ngclass: '',
      view: false
    }
  };

  openModal = {
    receive: false, 
    return : false
  };
  openModel = {
    assign:false
  }

  @ViewChild('receiveModal') receiveModal: ModalDirective;
  @ViewChild('confirmationPopup') confirmationPopup:ModalDirective; 
  @ViewChild('soReturn') soReturn:ModalDirective;

  constructor(
    private _serviceOrderService: ServiceOrderService,
    private _ngx: NgxSpinnerService,
    private _global: GlobalserviceService,
    private _actRoute: ActivatedRoute,
    private loginservice: Loginservices,
    private pdfservice:PdfServiceService,
    private _btServiceOrder: BtServiceOrderService
  ) { }

  ngOnInit() {
    this._actRoute.params.subscribe(param => {
      this.serviceOrderId = param['id'];
      this.getServiceOrderDetails(this.serviceOrderId);
    });
    this._actRoute.queryParams.subscribe(v => {
      const _view = v['view'];
      if (_view === 'details' || _view === undefined || _view === null || _view === '') {
        this.subTabChange('details');
      }
      if (_view === 'related') {
        this.subTabChange('related');
      }
    });
  }

  getServiceOrderDetails(id) {
    this._ngx.show();
    this._btServiceOrder.getServiceOrderByID(id).subscribe(data => {
      this._ngx.hide();
      this.serviceOrderDetails = data;
    }, error => this._ngx.hide());
  }

  subTabChange(tabname) {
    if (tabname === 'details') {
      this.navigationMenue.details.ngclass = 'active';
      this.navigationMenue.releted.ngclass = '';
      this.navigationMenue.details.view = true;
      this.navigationMenue.releted.view = false;
      this.getServiceOrderDetails(this.serviceOrderId);
    } else {
      this.navigationMenue.details.ngclass = '';
      this.navigationMenue.releted.ngclass = 'active';
      this.navigationMenue.details.view = false;
      this.navigationMenue.releted.view = true;
      
    }
  }

  onReceived() {
    this.receiveModal.hide();
    this._global.alert({ type: '', message: "Service Order Received Successfully!"});
    this.getServiceOrderDetails(this.serviceOrderId);
  }

  onAssignSO(){
    
  }
  rights(object: string, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  createPDF() {
    this._ngx.show();
    this.pdfservice.createPDF('api/serviceorder', 'serviceOrderId',
    this.serviceOrderId).subscribe(data => {
        const file = new Blob([data], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this._ngx.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: '', message: "Created successfully"});
      }, err => {
        this._ngx.hide();
        this.confirmationPopup.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
  }
  onReturnSO(){
    
  }
}
