import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, CirclesRequest, CirclesResponse, SSAResponse, Zonedetails } from '../../../services/model';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtCirclesRequest, BtCirclesResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-circle-detail',
  templateUrl: './bat-circle-detail.component.html',
  styleUrls: ['./bat-circle-detail.component.css']
})
export class BatCircleDetailComponent implements OnInit {

 
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createCircleModel', { static: false }) createCircleModel: ModalDirective;

  circleName: string;
  circle: BtCirclesResponse = new BtCirclesResponse();
  circleList: Array<BtCirclesResponse> = [];
  isEditMode: boolean = false;
  heading: string;
  CREATE_CIRCLE_HEADING: string = 'New Circle';
  EDIT_CIRCLE_HEADING: string = 'Edit Circle';
  newcircles: BtCirclesRequest = new BtCirclesRequest();
  userforlist;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  zoneList: Array<Zonedetails> = [];
  public page: number = 0;
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  serialNumber: number;
  isBatterySelected: string = 'fa fa-search';
  isPowerSelected: string = 'fa fa-search';
  isEvSelected: string = 'fa fa-search';

  CREATE_Zones_HEADING: string = 'New Zone Expense';
  EDIT_Zones_HEADING: string = 'Edit Zone Expense';
  headingZones: string;
  isEditModeZone: boolean = false;
  firstZone: boolean;
  lastZone: boolean;
  sizeZone: number;
  serialNumberZone: number;
  public pageZone: number = 0;
  totalElementsZone: number;
  zoneRequest: Zonedetails = new Zonedetails();
  zoneResponse: Zonedetails = new Zonedetails();
  SSAListByCircleName: Array<SSAResponse> = [];
  ssaCircle=["S.No","Name"];
  _cFlag: string;

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private _circleService: BtcircleService, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute, private loginService: Loginservices,private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.circleName = pram['id'];
      this.actionHistoryObjectAndId.objectId = this.circleName;
      this.actionHistoryObjectAndId.objectName = 'CircleDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  ngOnInit() {
    this.getCircleDetails();

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
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getCircleDetails();
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getSSAListByCircleName(this.circleName);
    }
    return false;
  }

  // PAGINATION

  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    // this.getZone(this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();
    // this.getZone(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    // this.getZone(this.sortColumnName, this.sortOrder);
  }

  getZone(column: string, order: string) {
    // this.spinner.show();
    // this._zoneService.getZone(this.page, column, order).subscribe(data => {
    //   if (data != null) {
    //     this.spinner.hide();
    //   }
    //   this.zoneList = data.content;
    //   this.first = data.first;
    //   this.last = data.last;
    //   this.size = data.size;
    //   this.serialNumber = (((this.page) * this.size));
    // });
  }

  getCircleDetails() {
    this.spinner.show();
    this._circleService.getCircleById(this.circleName).subscribe(data => {
      this.circle = data;
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  cancelData() {
    this.createCircleModel.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_CIRCLE_HEADING;
    this.newcircles = new BtCirclesRequest();
  }

  onClickEdit(y, i) {
    this.spinner.show();
    // this.newcircles = y;
    // this.newcircles.circleName = y.circleName;
    // this.newcircles.zoneName = y.zone;
    // this.newcircles.countryCode = y.countryCode;
    // this.newcircles.circleManagerBattryId = y.circleManagerBattryId;
    // this.newcircles.circleManagerPowerId = y.circleManagerPowerId;
    // this.newcircles.circleManagerEv = y.circleManagerEvId;

    this._circleService.getCircleById(i).subscribe(data => {
      this.newcircles.circleName = data.circleName;
      this.newcircles.zoneName = data.zoneBattery;
      this.newcircles.countryCode = data.countryCode;
      this.newcircles.legalEntity = data.batteryLegalEntityId;

      // this.newcircles.circleManagerBattryId = data.circleManagerBattryId;
      // this.newcircles.circleManagerPowerId = data.circleManagerPowerId;
      // this.newcircles.circleManagerEv = data.circleManagerEvId;
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_CIRCLE_HEADING;
    this.createCircleModel.show();
  }

  onCircleCreated($event: BtCirclesResponse) {
    this.circle = $event;
    this.getCircleDetails();
    this.getSSAListByCircleName(this.circleName);
  }


  // Zone Starts

  setPagePreviousZone(event: any) {
    event.preventDefault();
    this.pageZone = this.pageZone - 1;
    this.getSSAListByCircleName(this.circleName);
  }

  setPageZone(event: any) {
    event.preventDefault();
    this.getSSAListByCircleName(this.circleName);
  }

  setPageNextZone(event: any) {
    event.preventDefault();
    this.pageZone = this.pageZone + 1;
    this.getSSAListByCircleName(this.circleName);
  }

  getSSAListByCircleName(circleName) {
    this.spinner.show();
    this._circleService.getSSAListByCircleName(circleName, this.pageZone).subscribe(
      data => {
        this.SSAListByCircleName = data.content;
        this.firstZone = data.first;
        this.lastZone = data.last;
        this.sizeZone = data.size;
        this.totalElementsZone = data.totalElements;
        this.serialNumberZone = ((this.pageZone) * this.sizeZone);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
  }

  showZones() {
    // this.isEditModeZone = false;
    // this.headingZones = this.CREATE_Zones_HEADING;
    // this.zoneResponse = new Zonedetails();
    //  this.createZoneModel.show();
  }

  // Zone Ends

}
