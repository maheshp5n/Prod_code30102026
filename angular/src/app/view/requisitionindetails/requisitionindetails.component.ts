import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { RequisitionResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { RequisitionService } from '../../services/requisition.services';

@Component({
  selector: 'app-requisitionindetails',
  templateUrl: './requisitionindetails.component.html',
  styleUrls: ['./requisitionindetails.component.scss']
})
export class RequisitionindetailsComponent implements OnInit {
  @ViewChild('createRequisitionModel') createRequisitionModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  public message: string = '';

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: number;

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_ACCOUNT_HEADING: string = 'Edit Requisition';

  requisitionResponse: RequisitionResponse = new RequisitionResponse();
  requistionList: Array<RequisitionResponse> = [];
  requistion: RequisitionResponse = new RequisitionResponse();

  popupNgClass: string = 'alert alert-danger center center-block';


  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _requisitionServiece: RequisitionService) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];
      this.getRequisition(this.sapId);
    });
  }

  ngOnInit() {
  }

  getRequisition(sapId: number) {
    this.spinner.show();
    this._requisitionServiece.getRequisitionById(sapId).subscribe(data => {
      if (data != null) {
        this.spinner.hide();        
      }
      this.requistion = data;
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
    this.spinner.show();
    if (tabName == 'details') {
      this.spinner.hide();
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getRequisition(this.sapId);
    }
    else {
      this.spinner.show();
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      //this.getContacts();
    }
    this.spinner.hide();
    return false;
  }

  createRequisitionModelhide() {
    this.requisitionResponse = new RequisitionResponse();
    this.createRequisitionModel.hide();
  }

  createEditModelshow(a: RequisitionResponse) {
    this.spinner.show();
    this._requisitionServiece.getRequisitionById(a.id).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.requisitionResponse = data;

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createRequisitionModel.show();
  }

  cancelData() {
    this.requisitionResponse = new RequisitionResponse();
    this.createRequisitionModel.hide();
  }

  onRequisitionCreated($event: RequisitionResponse) {
    let eventRequisition = new RequisitionResponse();
    this._requisitionServiece.getRequisitionById($event.id).subscribe(data => {
      eventRequisition = data;
    });
    this.requistionList.push(eventRequisition);
    this.requistion = $event;
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }

}
