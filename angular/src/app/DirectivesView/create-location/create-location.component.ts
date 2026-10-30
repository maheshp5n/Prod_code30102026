import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { LocationResponse, MSLItemRequestRequest, MSLItemRequestResponse, LocationRequest, ServiceResourseForList, ServiceCenterForList, LocationForList, LocationType, LegalEntityResponse } from '../../services/model';
import { Loginservices } from '../../services/login.services';
import { LocationService } from '../../services/location.service';
import { siteService } from '../../services/siteService.services';
import { TypeaheadConfig, TypeaheadMatch } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.scss']
})
export class CreateLocationComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
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


  constructor(private loginservice: Loginservices, private locationservice: LocationService, private siteservice: siteService, private spinner: NgxSpinnerService) { }

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
      this.message = 'please fill the field Warehouse Name';
    }
    else if (this.localres.locationTypeId == null || this.localres.locationTypeId == 0) {
      this.message = 'please fill the Warehouse type Id';
    } else if (this.localres.legalEntityId == undefined || this.localres.legalEntityId == null || this.localres.legalEntityId == 0) {
      this.message = 'Plese choose legal entity';
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
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onLocationCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();

        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this.popupSuccessModel.show();
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

          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onLocationCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();

        }, error => {
          this.spinner.hide()
          this.message = error.error.message;
          this.popupSuccessModel.show()
        
        })
      }
    }
    if (!this.istrue) {
      this.popupSuccessModel.show();
    
    }
  }
  noMatchServicecenterCheck() {
    if (this.localres.legalEntityName == null || this.localres.legalEntityName == '') {
      this.noMatchserviceCenter = '';
    }
  }
  typeaheadNoResultsServiceCenter($event) {
    if ($event) {
      this.noMatchserviceCenter = " ** Record not found";
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
      this.noMatchParentLocation = " ** Record not found";
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
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');

    }
  }
  heading: string;
  addNewServiceCenter() {
    this.isEditMode = false;
    this.heading = "Create Legal Entity";
    this.createLegalENtityModel.show();

  }
  onEntityCreated($event: LegalEntityResponse) {
    this.getServiceCenter();
    this.localres.legalEntityName = $event.legalEntityName;
    this.localres.legalEntityId = $event.legalEntityTypeId;

  }
  cancelData() {
    this.req = new LegalEntityResponse();
    this.isLegalEntitySelected = "fa fa-times";
    this.createLegalENtityModel.hide();
  }


}
