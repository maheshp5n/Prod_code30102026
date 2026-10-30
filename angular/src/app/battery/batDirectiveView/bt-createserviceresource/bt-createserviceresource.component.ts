import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ContactForList, Contacts, LocationForList, LocationRequest, LocationResponse, ResourceType, ServiceResourseRequest, ServiceResourseResponse, UserForList } from '../../../services/model';
import { ServiceResourceService } from '../../../services/serviceresource.services';
import { FieldValidation } from '../../../services/validation.services';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';

@Component({
  selector: 'app-bt-createserviceresource',
  templateUrl: './bt-createserviceresource.component.html',
  styleUrls: ['./bt-createserviceresource.component.css']
})
export class BtCreateserviceresourceComponent implements OnInit {

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel', { static: false }) createContactModel: ModalDirective;
  @ViewChild('createLocation', { static: false }) createLocation: ModalDirective;

  _isEditMode: boolean;
  newServiceResource: ServiceResourseRequest = new ServiceResourseRequest();
  message: string;
  istrue: boolean;
  loginData;
  userForList: Array<UserForList> = [];
  resourceTypeList: Array<ResourceType> = [];ss
  locationTypeList: Array<LocationForList> = [];
  contactsForList: Array<ContactForList> = [];
  faPlus: string = 'btn btn-primary fa fa-plus';
  flag: boolean;
  noMatchLocation: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempServiceId: number;
  tempServiceName: string;
  ngClassLocation: string = 'col-md-6 col-sm-6 form-group';
  newcontact: Contacts;
  isEditModeContact: boolean;
  localres: LocationResponse;
  isEditModeLocation: boolean;
  isContactOpen: boolean;
  isModelOpen: boolean;

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editServiceResourseData(serviceResourseRequest: ServiceResourseRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
    this.newServiceResource = serviceResourseRequest;
    this.newServiceResource.active = true;
  }

  get editServiceResourseData(): ServiceResourseRequest {
    return this.newServiceResource;
  }

  @Output()
  onServiceResourseCreated: EventEmitter<ServiceResourseResponse> = new EventEmitter<ServiceResourseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices,
    private _serviceResourceService: BtserviceresourseService, private userRegistration:BatuserregistrationService, private _global:GlobalserviceService) {
  }

  ngOnInit() {
    this.ownerName();
    this.getUserForList();
    this.getResourceTypeForList();
    this.getLocationForList();
    this.getContactsForList();
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
    this.newServiceResource.owner = this.loginData.firstName + ' ' + this.loginData.lastName;
  }

  cancelData() {
    this.newServiceResource = new ServiceResourseRequest();
    this.onCancle.emit('cancled');
  }

  getUserForList() {
    this.userRegistration.getUserForList2().subscribe(data => {
      this.userForList = data;
    });
  }

  getResourceTypeForList() {
    this._serviceResourceService.getResourceTypeForList().subscribe(data => {
      this.resourceTypeList = data;
    });
  }

  getLocationForList() {
    this._serviceResourceService.getLocationForList().subscribe(data => {
      this.locationTypeList = data;
    });
  }

  addNewLocation() {
    this.localres = new LocationResponse();
    this.isEditModeLocation = false;
    this.createLocation.show();
  }

  cancelData2() {
    this.isEditMode = false;
  }

  postCreateServiceResourse() {
    let temp = new ServiceResourseResponse();
    this.flag = false;
    if (FieldValidation.isEmpty(this.newServiceResource.name)) {
      this.message = 'Please Fill Name';
    } else if (this.newServiceResource.recourceTypeId === null) {
      this.message = 'Please Fill Resource Type';
    } else if (FieldValidation.isEmpty(this.newServiceResource.userEmailId)) {
      this.message = 'Please Fill User';
    } else if ((this.newServiceResource.locationId === 0 ||
      this.newServiceResource.locationId === null)) {
      this.message = 'Please Fill Location';
    } else if (this.newServiceResource.contactId === 0 || this.newServiceResource.contactId === null) {
      this.message = 'Please Fill Contact';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this._serviceResourceService.postServiceResource(this.newServiceResource).subscribe(
          data => {
            this._serviceResourceService.getServiceResourceById(data.serviceResourceId).subscribe(data2 => {
              this.tempServiceId = data2.serviceResourceId;
              this.tempServiceName = data2.name;
              this.onServiceResourseCreated.emit(data2);
              temp = data2;
              this.spinner.hide();
            }, err => {
              this.spinner.hide();
            });
            this.tempServiceId = data.serviceResourceId;
            this.tempServiceName = data.name;
            this.message = this.tempServiceName+' Created Successfully !';
            this.popupNgClass = 'alert alert-success center center-block';
            this._global.alert({ type: '', message: this.message });
            this.onCancle.emit('cancled');
          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          }
        );
      } else {
        this.spinner.show();
        this._serviceResourceService
          .putServiceResource(this.newServiceResource.serviceResourceId, this.newServiceResource)
          .subscribe(
            data => {
              // this._serviceResourceService.getServiceResourceById(this.newServiceResource.serviceResourceId).subscribe(data2 => {
              //   temp = data2;
              this.spinner.hide();

              // }, err => {
              //   this.spinner.hide();
              // });
              this.message = data.name+' Updated Successfully';
              this.popupNgClass = 'alert alert-success center center-block';
              this._global.alert({ type: '', message: this.message });
              this.onServiceResourseCreated.emit(data);

              this.onCancle.emit('cancled');
            }, err => {
              this.spinner.hide();
              this.message = err.error.message;
              this._global.alert({ type: 'alert', message: this.message });

            });
      }
    }
    if (this.flag !== true) {
      this._global.alert({ type: 'alert', message: this.message });
    }
  }

  getContactsForList() {
    this._serviceResourceService.getContactsForList().subscribe(data => {
      this.contactsForList = data;
    });
  }

  addNewContact() {
    this.newcontact = new Contacts();
    this.isEditModeContact = false;
    this.createContactModel.show();
  }

  cancelContact() {
    this.createContactModel.hide();
    this.newcontact = new Contacts();
  }

  popupClose() {
    // this.popupSuccessModel.hide();
    // if (this.popupNgClass === 'alert alert-success center center-block') {
    //   this.onCancle.emit('cancled');
    // }
  }

  cancelLocation() {
    this.createLocation.hide();
    this.localres = new LocationResponse();
  }

  onLocationCreated($event: LocationRequest) {
    this.getLocationForList();
    this.newServiceResource.locationId = $event.locationId;
  }

  onContactCreated($event: Contacts) {
    // const newContactList = [];
    // this.contactsForList.forEach(data => {
    //   newContactList.push(data);
    // });
    // newContactList.push($event);
    // this.contactsForList = newContactList;
    this.getContactsForList();
    this.newServiceResource.contactId = $event.contactid;
  }

}
