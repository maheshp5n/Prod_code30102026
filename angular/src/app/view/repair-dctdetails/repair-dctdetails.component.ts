import { GlobalserviceService } from './../../globalservice.service';
import { ServiceOrderJobCardService } from './../../services/serviceorderjobcardservice';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { LocationFaultyLineItemResponse, RepairDCTResponse, DCTRepairRequest, DctRepairConsumedItemsResponse, ActionHistoryObjectAndId, LPRequest, LPItemRequest } from '../../services/model';
import { LocationService } from '../../services/location.service';

@Component({
  selector: 'app-repair-dctdetails',
  templateUrl: './repair-dctdetails.component.html',
  styleUrls: ['./repair-dctdetails.component.scss']
})
export class RepairDCTDetailsComponent implements OnInit {

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  // req: SSARequest = new SSARequest()
  // res: SSAResponse = new SSAResponse()
  // ssares: SSAResponse = new SSAResponse()
  @ViewChild('createRpairModel') createRpairModel: ModalDirective
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  sapId: string = '';
  message: string;
  tabledata: any
  isEditMode: boolean = false;
  heading: string;
  page: number = 0;
  // siteList:Array<SiteDetailsResponse>=[];
  serialNumber: number;
  totalSite: number;
  first: boolean;
  last: boolean;
  edit: boolean = false;
  locationItemResponse = new LocationFaultyLineItemResponse();
  CREATE_ACCOUNT_HEADING: string = 'Create SSA';
  EDIT_ACCOUNT_HEADING: string = 'Repair DCT';
  popupNgClass: string = 'alert alert-danger center center-block';
  dctreq = new DCTRepairRequest();
  Description: string;
  isModelOpen: boolean = false;
  productPartCOde: string;
  locationItemId: number;
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  ConsumeItemDetaiDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: false
  }


  loginData: any;
  _cFlag: string;
  Id: any;
  jobCardData: any = {};
  consumedItemList: Array<DctRepairConsumedItemsResponse> = [];
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(
    private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute,
    private loginservice: Loginservices,
    private dctrepairservice: DCTRepairservice,
    private _serviceorderjobcard: ServiceOrderJobCardService,
    private _global: GlobalserviceService,private _locationservice:LocationService
  ) {
    this._activatedRoute.params.subscribe(pram => {
      this.Id = pram['id'];
      this.actionHistoryObjectAndId.objectId = this.Id;
      this.actionHistoryObjectAndId.objectName = 'DctRepair';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getDetailsById();
    this.getLoggedInUser();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  getDetailsById() {
    this.spinner.show();
    this._serviceorderjobcard.getJobCard(this.Id).subscribe(data => {
      this.spinner.hide();
      this.jobCardData = data;
    }, error => this.spinner.hide());
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
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getDetailsById();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getRpair();
    }
    return false;
  }

  createDCTRepairModel() {
    this.isEditMode = false;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.dctreq.partCode = this.jobCardData.partCode;
    this.dctreq.serialNumber = this.jobCardData.partCodeSerialNumber;
    this.Description = this.jobCardData.productDescription;
    this.createRpairModel.show();
  }
  onDCTRCancel() {
    this.createRpairModel.hide();
  }
  onDCTRCreated($event) {
    this.getDetailsById();
    this._global.alert({ type: '', message: 'Submitted Successfully' });
    this.createRpairModel.hide();
    this.getRpair();
  }
  size: number;
  RepairDCTres: Array<RepairDCTResponse> = [];
  pageSerialNumbr: number;

  getRpair() {
    this.spinner.show();
    this.dctrepairservice.getRepairBySerialNumbrAndPartCode(this.page, this.jobCardData.partCodeSerialNumber,
      this.jobCardData.partCode).subscribe(data => {
        this.spinner.hide();
        this.RepairDCTres = data.content;
        this.first = data.first;
        this.last = data.last;
        this.size = data.size;
        this.pageSerialNumbr = (((this.page) * this.size));
      }, error => {
        this.spinner.hide();
        this.message = error.error.message;
        this.popupSuccessModel.show();
      });

  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getRpair();
  }

  setPage(event: any) {
    event.preventDefault();
    this.getRpair();
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.getRpair();
  }

  ConsumeItmDesiplay(id) {
    this.spinner.show();
    this.ConsumeItemDetaiDisplay.open = !this.ConsumeItemDetaiDisplay.open;
    this.dctrepairservice.getRepairItmonsumdResponse(id, this.page).subscribe(data => {
      this.spinner.hide();
      this.consumedItemList = data.content;
    });
  }

 

}
