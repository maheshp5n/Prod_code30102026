import { AnnexureLineItemService } from './../../services/annexureLineItem.services';
import {
  AnnexureResponse, AnnexureRequest, AnnexureLineItemRequest, AnnexureLineItemResponse,
  ServiceAppointmentResponse
} from './../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ServiceResourseResponse, ToBeApprovedHistoryResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { AnnexureService } from '../../services/annexure.services';
import { Loginservices } from '../../services/login.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { PdfServiceService } from '../../services/pdf-service.service';

@Component({
  selector: 'app-annexuredetails',
  templateUrl: './annexuredetails.component.html',
  styleUrls: ['./annexuredetails.component.scss']
})

export class AnnexuredetailsComponent implements OnInit {
  @ViewChild('createAnnexureModel', { static: false }) createAnnexureModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createAnnexureLineItemModel', { static: false }) createAnnexureLineItemModel: ModalDirective;
  @ViewChild('confirmationPopup', { static: false }) confirmationPopup: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<ServiceResourseResponse> = new EventEmitter<ServiceResourseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  isAnnexureOpen: boolean;
  locationListByAnnexure: number;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  annexureId: number = 0;
  message: string;
  isEditMode: boolean = false;
  edit: boolean = false;
  annexureRequest: AnnexureRequest = new AnnexureRequest();
  annexureResponse: AnnexureResponse = new AnnexureResponse();
  annexureLineItemResponseList: Array<AnnexureLineItemResponse> = [];
  CREATE_Annexure_HEADING: string = 'New Annexure';
  EDIT_Annexure_HEADING: string = 'Edit Annexure';
  headingAnnexure: string;
  isEditModeAnnexure: boolean;
  isEditModeAnnexureLineItem: boolean;
  firstAnnexure: boolean;
  lastAnnexure: boolean;
  sizeAnnexure: number;
  serialNumberAnnexureLineItems: number;
  public pageAnnexureLineItem: number = 0;
  totalElementsAnnexure: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  totalAnnexureLineitems: number;
  headingAnnexureLineItem: string;
  annexureLineItemRequest: AnnexureLineItemRequest = new AnnexureLineItemRequest();
  annexureLineItemResponse: AnnexureLineItemResponse = new AnnexureLineItemResponse();
  isAnnexureLineItemOpen: boolean;
  serviceAppointmentList: Array<ServiceAppointmentResponse> = [];
  approvalHistoryList: Array<ToBeApprovedHistoryResponse>;
  objectName: string = 'Annexure';
  _cFlag: string;

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
  loginData: any;

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _annexureService: AnnexureService, private _annexureLineItemService: AnnexureLineItemService,
    private loginService: Loginservices, private _approvalHistoryService: ApprovalHistoryService
    , private _pdfService: PdfServiceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.annexureId = pram['id'];
    });
    this._activatedRoute.queryParams.subscribe(queryParam => {
      this._cFlag = queryParam['_c'];
    });
    // this._activatedRoute.snapshot.queryParamMap.get('_c');
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    if (this.annexureId !== 0) {
      this.getAnnexureById(this.annexureId);
    }
    this.loginData = JSON.parse(sessionStorage.getItem('login'));
    this.annexureLineItemRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
  }

  getAnnexureById(annexureId) {
    this.spinner.show();
    this._annexureService.getAnnexureById(this.annexureId).subscribe(data => {
      this.annexureResponse = data;
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
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
    
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getAnnexureById(this.annexureId);
    } else {
      this.getAnnexureLineItem(this.annexureId);
      this.getApprovalHistoryList();
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.spinner.hide();
    }
    return false;
  }

  onClickEditAnnexure(annexureResponse) {
    this.spinner.show();
    this._annexureService.getAnnexureById(this.annexureResponse.id).subscribe(data => {
      this.annexureRequest.id = data.id;
      this.annexureRequest.invoiceNumber = data.invoiceNumber;
      this.annexureRequest.invoiceDate = data.invoiceDate;
      this.annexureRequest.submittedDate = data.submittedDate;
      // this.annexureRequest.approvedByCommercialDate = data.approvedByCommercialDate;
      // this.annexureRequest.approvedByCircleDate = data.approvedByCircleDate;
      this.annexureRequest.isInvoiceReceived = data.isInvoiceReceived;
      this.annexureRequest.approvalStatusId = data.approvalStatusId;
      // this.annexureRequest.businessDomainId = 2;

      this.annexureRequest.poNumber = data.poNumber;
      this.annexureRequest.poDate = data.poDate;
      // this.annexureRequest.createdByFirstName = data.createdByFirstName;
      // this.annexureRequest.createdByMiddleName = data.createdByMiddleName;
      // this.annexureRequest.createdByLastName = data.createdByLastName;
      // this.annexureRequest.createdDate = data.createdDate;
      // this.annexureRequest.modifiedByFirstName = data.modifiedByFirstName;
      // this.annexureRequest.modifiedByMiddleName = data.modifiedByMiddleName;
      // this.annexureRequest.modifiedByLastName = data.modifiedByLastName;
      // this.annexureRequest.modifiedDate = data.modifiedDate;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
    });
    this.isEditMode = true;
    this.headingAnnexure = this.EDIT_Annexure_HEADING;
    this.createAnnexureModel.show();
  }

  cancelData() {
    this.createAnnexureModel.hide();
    this.createAnnexureLineItemModel.hide();
    this.onCancle.emit('cancled');
    this.annexureRequest = new AnnexureRequest();
  }

  onAnnexureCreated($event: AnnexureResponse) {
    this.annexureResponse = $event;
    this.getAnnexureById(this.annexureId);
    this.getAnnexureLineItem(this.annexureId);
    this.getApprovalHistoryList()
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  setPagePreviousAnnexure(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem - 1;
    this.getAnnexureById(this.annexureId);
  }

  setPageAnnexure(event: any) {
    event.preventDefault();
    this.getAnnexureById(this.annexureId);
  }

  setPageNextAnnexure(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem + 1;
    this.getAnnexureById(this.annexureId);
  }

  deleteAnnexurertunityLineItem(id) {
  }

  setPagePreviousAnnexureLineItem(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem - 1;
    this.getAnnexureLineItem(this.annexureId);
  }

  setPageAnnexureLineItem(event: any) {
    event.preventDefault();
    this.getAnnexureLineItem(this.annexureId);
  }

  setPageNextAnnexureLineItem(event: any) {
    event.preventDefault();
    this.pageAnnexureLineItem = this.pageAnnexureLineItem + 1;
    this.getAnnexureLineItem(this.annexureId);
  }

  getAnnexureLineItem(id) {
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemByAnnexId(this.annexureId, this.pageAnnexureLineItem).subscribe(data => {
      this.annexureLineItemResponseList = data.content;
      this.firstAnnexure = data.first;
      this.lastAnnexure = data.last;
      this.sizeAnnexure = data.size;
      this.totalAnnexureLineitems = data.totalElements;
      this.serialNumberAnnexureLineItems = ((this.pageAnnexureLineItem) * this.sizeAnnexure);
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  onAnnexureLineItemCreated($event) {
    if (!this.isEditModeAnnexureLineItem) {
      this.annexureLineItemResponseList.unshift($event);
      this.totalElementsAnnexure = this.totalElementsAnnexure + 1;
    } else {
      let index = 0;
      this.annexureLineItemResponseList.forEach(element => {
        if (element.id === $event.id) {
          this.annexureLineItemResponseList[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getAnnexureById(this.annexureId);
    this.getAnnexureLineItem(this.annexureId);
    this.getApprovalHistoryList()
  }

  newAnnexureLineItem() {
    this.spinner.show();
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.isEditModeAnnexureLineItem = false;
    this.headingAnnexureLineItem = 'New Annexure Line Item';
    this._annexureService.getAnnexureById(this.annexureId).subscribe((data) => {
      this.annexureLineItemRequest.annexureId = data.id;
      this.annexureLineItemRequest.annexureManualId = data.manualId;
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
    this.createAnnexureLineItemModel.show();
  }

  openEditAnnexureLineItem(c) {
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.spinner.show();
    this._annexureLineItemService.getAnnexureLineItemById(c.id).subscribe((data) => {
      this.annexureLineItemRequest = data;
      // this.annexureLineItemRequest.id = data.id;
      // this.annexureLineItemRequest.annexureId = data.annexureId;
      this.annexureLineItemRequest.serviceAppointmentId = data.serviceAppointmentId;
      // this.annexureLineItemRequest.glCodeId = data.glCodeId;
      // this.annexureLineItemRequest.workCompletionDate = data.workCompletionDate;
      // this.annexureLineItemRequest.activity = data.activity;
      // this.annexureLineItemRequest.workStatus = data.workStatus;
      // this.annexureLineItemRequest.exicomInvoiceDate = data.exicomInvoiceDate;
      // this.annexureLineItemRequest.exicomInvoiceNumber = data.exicomInvoiceNumber;
      // this.annexureLineItemRequest.siteId = data.siteId;
      // this.annexureLineItemRequest.remark = data.remark;
      // this.annexureLineItemRequest.serviceAppointmentName = data.serviceAppointmentName;
      // this.annexureLineItemRequest.glCodeName = data.glCodeName;
      // this.annexureLineItemRequest.annexureId = data.annexureId;
      // this.annexureLineItemRequest.annexureManualId = data.annexureManualId;
      // this.annexureLineItemRequest.caseId = data.caseId;
      // this.annexureLineItemRequest.remark = data.remark;
      // this.annexureLineItemRequest.circleName = data.circleName;
      // this.annexureLineItemRequest.siteId = data.siteId;
      // this.annexureLineItemRequest.caseId = data.caseId;
      // this.annexureLineItemRequest.siteName = data.siteName;
      // this.annexureLineItemRequest.caseIdManual = data.caseIdManual;
      // this.annexureLineItemRequest.amountWithTax = data.amountWithTax;
      // this.annexureLineItemRequest.amount = data.amount;
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

  getClosedServiceAppointmentForAnnexure() {
    this._annexureLineItemService.getClosedServiceAppointmentForAnnexure().subscribe(data => {
      this.serviceAppointmentList = data;
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  getDataFromSA() {
    this.spinner.show();
    this.getClosedServiceAppointmentForAnnexure();
    this.serviceAppointmentList.forEach(data => {
      if (this.annexureLineItemRequest.serviceAppointmentId === data.serviceAppointmentId) {
        this.annexureLineItemRequest.circleName = data.circleName;
        this.annexureLineItemRequest.siteId = data.siteId;
        this.annexureLineItemRequest.caseId = data.caseId;
        this.annexureLineItemRequest.siteName = data.siteName;
        this.annexureLineItemRequest.caseIdManual = data.caseIdManual;
        this.annexureLineItemRequest.workStatus = data.caseStatus;
        this.annexureLineItemRequest.activity = data.caseType;
        this.spinner.hide();
      }
    });
  }

  submitForApproval() {
    this.spinner.show();
    this._annexureService.submitForApproval(this.annexureId, this.annexureLineItemRequest.businessDomainId).subscribe(data => {
      this.message = 'Request Submitted for Approval';
      this.popupNgClass = 'alert alert-success center center-block';
      this.spinner.hide();
      this.popupSuccessModel.show();
    }, err => {
      this.popupNgClass = 'alert alert-danger center center-block';
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  getApprovalHistoryList() {
    this.spinner.show();
    this._approvalHistoryService.getApprovalStatus(this.objectName, this.annexureId).subscribe(data => {
      this.approvalHistoryList = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }

  createPDF() {
    this.spinner.show();
    this._pdfService.createPDF('api/annexure', 'annexureId', this.annexureId).subscribe(data => {
      const file = new Blob([data], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
      this.spinner.hide();
      this.message = 'Created Successfully';
      this.confirmationPopup.hide();
      this.popupNgClass = 'alert alert-success center center-block';
      this.popupSuccessModel.show();
    }, error => {
      this.spinner.hide();
      this.confirmationPopup.hide();
      this.message = error.message;
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
    });
  }

}

