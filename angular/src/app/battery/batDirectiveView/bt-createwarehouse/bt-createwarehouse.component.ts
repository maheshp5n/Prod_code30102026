import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective, TypeaheadMatch } from 'ngx-bootstrap';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { Loginservices } from '../../../services/login.services';
import { LegalEntityResponse, LocationForList, LocationRequest, LocationResponse, LocationType, ServiceCenterForList, WarehousedetailsRequest } from '../../../services/model';
import { siteService } from '../../../services/siteService.services';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';

@Component({
  selector: 'app-bt-createwarehouse',
  templateUrl: './bt-createwarehouse.component.html',
  styleUrls: ['./bt-createwarehouse.component.css']
})
export class BtCreatewarehouseComponent implements OnInit {

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createLegalENtityModel', { static: false }) createLegalENtityModel: ModalDirective;
  public _isEditMode: boolean;
  istrue: boolean;
  loginData: any;
  message: string;
  localres: LocationResponse = new LocationResponse()
  localReq: LocationRequest = new LocationRequest();
  isLegalEntitySelected: string;
  noMatchserviceCenter: string;
  locationList: Array<LocationForList>
  locationType: Array<LocationType>;
  isParentlocSelected: string;
  noMatchParentLocation: string;
  parentLoation: Array<LocationForList> = [];
  locationNameTamp: string;
  locationIdTamp: number;
  req: LegalEntityResponse = new LegalEntityResponse();
  popupNgClass: string = 'alert alert-danger center center-block';
  faPlus: string = "btn btn-primary fa fa-plus";
  getTypeaheadConfig(): TypeaheadConfig {
    return Object.assign(new TypeaheadConfig(), { hideResultsOnBlur: false });
  }
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set legalENtityName(legalENtityName: string) {
    this.localres.legalEntityName = legalENtityName;
  }
  get legalENtityName(): string {
    return this.localres.legalEntityName;
  }
  @Input()
  set legalENtityId(legalEntityId: number) {
    this.localres.legalEntityId = legalEntityId;
  }
  get legalENtityId(): number {
    return this.localres.legalEntityId;
  }


  @Input()
  set editEntityData(localres: LocationResponse) {
    this.localres = localres;
    console.log(this.localres);
    this.locationservice.locationForlist().subscribe(data => {
      this.parentLoation = data;
    });


  }

  get editEntityData(): LocationResponse {
    return this.localres;
  }

  @Output()
  onLocationCreated: EventEmitter<LocationRequest> = new EventEmitter<LocationRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private loginservice: Loginservices, private locationservice: BtwarehouseService, private siteservice: BtsiteserviceService, private spinner: NgxSpinnerService,private _global:GlobalserviceService) { }

  ngOnInit() {
    if (!this.isEditMode) {
      this.localres = new LocationResponse();
      this.localres.locationTypeId = 0;
    }

    this.getLoggedInUser();
    this.LocationForList();
    this.getServiceCenter();
    this.getLocatinType();
    if (this.localres != null) {
      this.isLegalEntitySelected = "fa fa-search";
      this.isParentlocSelected = "fa fa-search";
    }
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  // getRoot($event:LocationResponse){
  //   this.locationservice.getRootLocation($event.parentLocationId).subscribe(data=>{
  //     this.parentLoation=data;
  //   })
  // }
  getLocatinType() {
    this.locationservice.locationTypeForlist().subscribe(data => {
      this.locationType = data;
    })
  }
  serviceCenterList: Array<ServiceCenterForList> = []
  getServiceCenter() {
    this.siteservice.getserviceCenterForList().subscribe(data => {
      this.serviceCenterList = data;
    })
  }
  getProductByLocationId() {
    this.locationservice.getProductItemByLocation(this.localres.locationId).subscribe(data => {

    })
  }
  // getRootLoc(){
  //   this.locationservice.getRootLocation(this.localres.locationId).subscribe(data=>{
  //     console.log(data)
  //   })
  // }
  LocationForList() {
    this.locationservice.locationForlist().subscribe(data => {
      this.locationList = data;
    })
  }
  saveMslItemLocation() {
    if (this.localres.locationName == null || this.localres.locationName == '') {
      this.message = 'Please Fill The Field Warehouse Name';
    }
    else if (this.localres.locationTypeId == null || this.localres.locationTypeId == 0) {
      this.message = 'Please Fill The Warehouse Type Id';
    } else if (this.localres.legalEntityId == undefined || this.localres.legalEntityId == null || this.localres.legalEntityId == 0) {
      this.message = 'Please Choose Legal Entity';
    }
    else {
      this.istrue = true;
      if (this.isEditMode == false) {
        this.localReq.locationId = this.localres.locationId;
        this.localReq.locationName = this.localres.locationName;
        this.localReq.locationTypeId = this.localres.locationTypeId;
        this.localReq.mobileLocation = this.localres.mobileLocation;
        this.localReq.parentLocationId = this.localres.parentLocationId;
        this.localReq.recordType = this.localres.recordType;
        this.localReq.inventoryLocation = this.localres.inventoryLocation;
        this.localReq.legalEntityId = this.localres.legalEntityId;
        this.localReq.plant = this.localres.plant;
        this.spinner.show();
        this.locationservice.saveLocation(this.localReq).subscribe(data => {
          this.locationIdTamp = data.locationId;
          this.locationNameTamp = data.locationName;
          this.message = this.locationNameTamp+' Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onLocationCreated.emit(data);
          this.spinner.hide();
          this._global.alert({type:'',message:this.message});
          this.onCancle.emit('cancled');

        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this._global.alert({type:'alert',message:this.message})
        })

      }
      else {

        this.localReq.locationId = this.localres.locationId;
        this.localReq.locationName = this.localres.locationName;
        this.localReq.locationTypeId = this.localres.locationTypeId;
        this.localReq.mobileLocation = this.localres.mobileLocation;
        this.localReq.parentLocationId = this.localres.parentLocationId;
        this.localReq.recordType = this.localres.recordType;
        this.localReq.inventoryLocation = this.localres.inventoryLocation;
        this.localReq.legalEntityId = this.localres.legalEntityId;
        this.localReq.plant = this.localres.plant;
        this.spinner.show();
        this.locationservice.updateLocation(this.localReq, this.localReq.locationId).subscribe(data => {

          this.message = data.locationName+' Updated Successfully';
          this.onLocationCreated.emit(data);
          this.spinner.hide();
          this._global.alert({type:'',message:this.message});
          this.onCancle.emit('cancled');

        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this._global.alert({type:'alert',message:this.message})
        
        })
      }
    }
    if (!this.istrue) {
      // this.popupSuccessModel.show();
      this._global.alert({type:'alert',message:this.message})

    
    }
  }
  noMatchServicecenterCheck() {
    if (this.localres.legalEntityName == null || this.localres.legalEntityName == '') {
      this.noMatchserviceCenter = '';
    }
  }
  typeaheadNoResultsServiceCenter($event) {
    if ($event) {
      this.noMatchserviceCenter = " ** Record Not Found";
    }
  }

  LegalEntityTimes() {
    this.isLegalEntitySelected = "fa fa-search";
    this.localres.legalEntityName = null;
    this.localres.legalEntityId = null;
    this.noMatchserviceCenter = '';
  }

  onKeydownLegalEntity($event) {
    this.isLegalEntitySelected = "fa fa-times";
  }
  legalEntityName
  legalEntitySelect($event) {
    this.localres.legalEntityId = $event.legalEntityId
    this.noMatchserviceCenter = '';



  }

  onKeydownParentLocation($event) {
    this.isParentlocSelected = "fa fa-times";
  }

  noMatchParentLocCheck() {
    if (this.localres.parentLocationName == null || this.localres.parentLocationName == '') {
      this.noMatchParentLocation = '';
    }

  }
  typeaheadNoResultsParentLoc($event) {
    if ($event) {
      this.noMatchParentLocation = " ** Record Not Found";
    }

  }
  parentLocTimes() {
    this.isParentlocSelected = "fa fa-search";
    this.localres.parentLocationId = null;
    this.localres.parentLocationName = null;
    this.noMatchParentLocation = '';

  }
  cancelLocationData() {
    if (this.isEditMode != true) {
      this.localres = new LocationResponse();
      this.onCancle.emit('cancled');

    }

    this.onCancle.emit('cancled');
  }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');

  //   }
  // }
  heading: string;
  addNewServiceCenter() {
    this.isEditMode = false;
    this.heading = "Create Legal Entity";
    this.createLegalENtityModel.show();

  }
  onEntityCreated($event: LegalEntityResponse) {
    this.getServiceCenter();
    this.localres.legalEntityName = $event.legalEntityName;
    this.localres.legalEntityId = $event.legalentityId;

  }
  cancelData() {
    this.req = new LegalEntityResponse();
    this.isLegalEntitySelected = "fa fa-times";
    this.createLegalENtityModel.hide();
  }


}
