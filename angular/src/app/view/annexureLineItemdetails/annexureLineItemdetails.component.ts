import { AnnexureLineItemRequest, AnnexureLineItemResponse } from './../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ServiceResourseResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AnnexureLineItemService } from '../../services/annexureLineItem.services';

@Component({
  selector: 'app-annexureLineItemdetails',
  templateUrl: './annexureLineItemdetails.component.html',
  styleUrls: ['./annexureLineItemdetails.component.scss']
})

export class AnnexureLineItemdetailsComponent implements OnInit {
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _annexureLineItemService: AnnexureLineItemService) {
    this._activatedRoute.params.subscribe(pram => {
      this.annexureLineItemId = pram['id2'];
    });
  }

  @ViewChild('createAnnexureLineItemModel') createAnnexureLineItemModel: ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<ServiceResourseResponse> = new EventEmitter<ServiceResourseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  isAnnexureLineItemOpen: boolean;
  locationListByAnnexureLineItem: number;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  annexureLineItemId: number = 0;
  message: string;
  isEditMode: boolean = false;
  edit: boolean = false;
  annexureLineItemRequest: AnnexureLineItemRequest = new AnnexureLineItemRequest();
  annexureLineItemResponse: AnnexureLineItemResponse = new AnnexureLineItemResponse();
  annexureLineItemResponseList: Array<AnnexureLineItemResponse> = [];
  CREATE_AnnexureLineItem_HEADING: string = 'New Annexure Line Item';
  EDIT_AnnexureLineItem_HEADING: string = 'Edit Annexure Line Item';
  headingAnnexureLineItem: string;
  isEditModeAnnexureLineItem: boolean;
  firstAnnexureLineItem: boolean;
  lastAnnexureLineItem: boolean;
  sizeAnnexureLineItem: number;
  serialNumberAnnexureLineItems: number;
  public pageAnnexureLineItem: number = 0;
  totalElementsAnnexureLineItem: number;
  popupNgClass: string = 'alert alert-danger center center-block';

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  purchaseDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  warrantyDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  amcDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  ngOnInit() {
    if (this.annexureLineItemId !== 0) {
      this.getAnnexureLineItemById(this.annexureLineItemId);
    }
  }

  getAnnexureLineItemById(annexureLineItemId) {
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemById(this.annexureLineItemId).subscribe(data => {
      this.annexureLineItemResponse = data;
      this.spinner.hide();
    });
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  changePurchaseDisplay() {
    this.purchaseDisplay.open = !this.purchaseDisplay.open;
    if (this.purchaseDisplay.open) {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeWarrantyDisplay() {
    this.warrantyDisplay.open = !this.warrantyDisplay.open;
    if (this.warrantyDisplay.open) {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeAmcDisplay() {
    this.amcDisplay.open = !this.amcDisplay.open;
    if (this.amcDisplay.open) {
      this.amcDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.amcDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open;
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    this.spinner.show();
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.spinner.hide();
    } else {
      this.getAnnexureLineItem(this.annexureLineItemId);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.spinner.hide();
    }
    return false;
  }

  // onClickEditAnnexureLineItem(annexureLineItemResponse) {
  //   this.spinner.show();
  //   this._annexureLineItemService.getAnnexureLineItemById(annexureLineItemResponse.id).subscribe(data => {
  //     this.annexureLineItemRequest.id = data.id;
  //     this.annexureLineItemRequest.serviceAppointmentId = data.serviceAppointmentId;
  //     this.annexureLineItemRequest.glCodeId = data.glCodeId;
  //     this.annexureLineItemRequest.workCompletionDate = data.workCompletionDate;
  //     this.annexureLineItemRequest.activity = data.activity;
  //     this.annexureLineItemRequest.workStatus = data.workStatus;
  //     this.annexureLineItemRequest.exicomInvoiceDate = data.exicomInvoiceDate;
  //     this.annexureLineItemRequest.exicomInvoiceNumber = data.exicomInvoiceNumber;
  //     this.annexureLineItemRequest.serviceAppointmentName = data.serviceAppointmentName;
  //     this.annexureLineItemRequest.glCodeName = data.glCodeName;
  //     this.annexureLineItemRequest.annexureId = data.annexureId;
  //     this.annexureLineItemRequest.annexureManualId = data.annexureManualId;
  //     this.annexureLineItemRequest.caseId = data.caseId;
  //     this.annexureLineItemRequest.remark = data.remark;
  //     this.annexureLineItemRequest.amount = data.amount;
  //     this.annexureLineItemRequest.circleName = data.circleName;
  //     this.annexureLineItemRequest.siteId = data.siteId;
  //     this.annexureLineItemRequest.siteName = data.siteName;
  //     this.annexureLineItemRequest.caseIdManual = data.caseIdManual;
  //     this.spinner.hide();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //   });
  //   this.isEditMode = true;
  //   this.headingAnnexureLineItem = this.EDIT_AnnexureLineItem_HEADING;
  //   this.createAnnexureLineItemModel.show();
  // }

  onClickEditAnnexureLineItem(c) {
    // this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemById(c.id).subscribe((data) => {
      this.annexureLineItemRequest = data;
      this.annexureLineItemRequest.serviceAppointmentId = data.serviceAppointmentId;
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
    this.isEditModeAnnexureLineItem = true;
    this.headingAnnexureLineItem = 'Edit Annexure Line Item';
    this.createAnnexureLineItemModel.show();
  }

  cancelData() {
    this.createAnnexureLineItemModel.hide();
    this.onCancle.emit('cancled');
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
  }

  onAnnexureLineItemCreated($event: AnnexureLineItemResponse) {

    this.annexureLineItemResponse = $event;
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemById($event.id).subscribe((data) => {
      this.annexureLineItemRequest = data;
      this.annexureLineItemRequest.serviceAppointmentId = data.serviceAppointmentId;
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  deleteAnnexureLineItemrtunityLineItem(id) {
  }


  setPagePreviousAnnexureLineItem(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem - 1;
    this.getAnnexureLineItem(this.annexureLineItemId);
  }

  setPageAnnexureLineItem(event: any) {
    event.preventDefault();
    this.getAnnexureLineItem(this.annexureLineItemId);
  }

  setPageNextAnnexureLineItem(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem + 1;
    this.getAnnexureLineItem(this.annexureLineItemId);
  }

  getAnnexureLineItem(id) {
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemByAnnexId(this.annexureLineItemId, this.pageAnnexureLineItem).subscribe(data => {
      this.annexureLineItemResponseList = data.content;
      this.firstAnnexureLineItem = data.first;
      this.lastAnnexureLineItem = data.last;
      this.sizeAnnexureLineItem = data.size;
      this.serialNumberAnnexureLineItems = ((this.pageAnnexureLineItem) * this.sizeAnnexureLineItem);
      this.spinner.hide();
    });
  }

  newAnnexureLineItem() {
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.isEditModeAnnexureLineItem = false;
    this.headingAnnexureLineItem = 'New AnnexureLineItem Line Item';

    this.createAnnexureLineItemModel.show();
  }

  openEditAnnexureLineItem(c) {
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemById(c.id).subscribe((data) => {
      // this.annexureLineItemRequest = data;
      // this.annexureLineItemRequest.id = data.id;
      // this.annexureLineItemRequest.annexureLineItemId = data.annexureLineItemId;
      // this.annexureLineItemRequest.serviceAppointmentId = data.serviceAppointmentId;
      // this.annexureLineItemRequest.glCodeId = data.glCodeId;
      // this.annexureLineItemRequest.workCompletionDate = data.workCompletionDate;
      // this.annexureLineItemRequest.activity = data.activity;
      // this.annexureLineItemRequest.workStatus = data.workStatus;
      // this.annexureLineItemRequest.exicomInvoiceDate = data.exicomInvoiceDate;
      // this.annexureLineItemRequest.exicomInvoiceNumber = data.exicomInvoiceNumber;
      // this.annexureLineItemRequest.remark = data.remark;
      // this.annexureLineItemRequest.serviceAppointmentName = data.serviceAppointmentName;
      // this.annexureLineItemRequest.glCodeName = data.glCodeName;
      // this.annexureLineItemRequest.annexureLineItemId = data.annexureLineItemId;
      // this.annexureLineItemRequest.annexureLineItemManualId = data.annexureLineItemManualId;
      // this.annexureLineItemRequest.caseId = data.caseId;
      // this.annexureLineItemRequest.remark = data.remark;
      this.spinner.hide();
    });
    this.isEditModeAnnexureLineItem = true;
    this.headingAnnexureLineItem = 'Edit AnnexureLineItem Line Item';
    this.createAnnexureLineItemModel.show();
  }


}



