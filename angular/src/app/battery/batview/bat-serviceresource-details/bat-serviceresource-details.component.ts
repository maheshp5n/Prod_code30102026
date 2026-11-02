import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId, LocationRequest, LocationResponse, ServiceAppointmentRequest, ServiceAppointmentResponse, ServiceResourseRequest, ServiceResourseResponse } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';
import { btServiceAppointmentResponseWithSCH } from '../../bt_model';

@Component({
  selector: 'app-bat-serviceresource-details',
  templateUrl: './bat-serviceresource-details.component.html',
  styleUrls: ['./bat-serviceresource-details.component.css']
})
export class BatServiceresourceDetailsComponent implements OnInit {

  @ViewChild('createServiceResourceModel') createServiceResourceModel: ModalDirective;
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createserviceAppointmentModel') createserviceAppointmentModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<ServiceResourseResponse> = new EventEmitter<ServiceResourseResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  isServiceResourceOpen: boolean;
  locationListByServiceResource: number;

  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  serviceResourceId: number;
  message: string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_ServiceResource_HEADING: string = 'Edit Service Report';
  serviceResourceRequest: ServiceResourseRequest = new ServiceResourseRequest();
  serviceResource: ServiceResourseResponse = new ServiceResourseResponse();
  EDIT_SERVICE_Resourse_HEADING: string = 'Edit Service Resource'; 

  CREATE_ServiceAppointmentss_HEADING: string = 'New Service Appointment';
  EDIT_ServiceAppointmentss_HEADING: string = 'Edit Service Appointment';
  isEditModeServiceAppointments: boolean = false;
  firstServiceAppointments: boolean;
  lastServiceAppointments: boolean;
  sizeServiceAppointments: number;
  serialNumberServiceAppointments: number;
  public pageServiceAppointments: number = 0;
  totalElementsServiceAppointments: number;
  serviceAppointmentsRequest: ServiceAppointmentRequest = new ServiceAppointmentRequest();
  serviceAppointmentResponse: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  serviceAppointmentsListByServiceResourceId: Array<ServiceAppointmentResponse> = [];

  CREATE_Locations_HEADING: string = 'New Location';
  EDIT_Locations_HEADING: string = 'Edit Location';
  headingLocations: string;
  isEditModeLocation: boolean = false;
  firstLocation: boolean;
  lastLocation: boolean;
  sizeLocation: number;
  serialNumberLocation: number;
  public pageLocation: number = 0;
  totalElementsLocation: number;
  locationRequest: LocationRequest = new LocationRequest();
  locationResponse: LocationResponse = new LocationResponse();
  locationListByServiceResourceId: Array<LocationResponse> = [];
  // popupNgClass: string = 'alert alert-danger center center-block';
  serviceAppointmentRequest: ServiceAppointmentRequest = new ServiceAppointmentRequest();
  isserviceAppointmentOpen: boolean;
  isEditModelShow:boolean;
  isEditModeServiceAppointment: boolean;
  _cFlag: string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();

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
    this.getServiceResourceById();
  }

  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _serviceResourceService: BtserviceresourseService, private serviceAppointmentService: BtServiceappointmentService
    , private loginService: Loginservices,private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.serviceResource.serviceResourceId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.serviceResource.serviceResourceId;
      this.actionHistoryObjectAndId.objectName = 'ServiceRecource';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  getServiceResourceById() {
    this.spinner.show();
    this._serviceResourceService.getServiceResourceById(this.serviceResource.serviceResourceId).subscribe(data => {
      this.serviceResource = data;
      this.locationListByServiceResource = data.serviceResourceId;
      // this.getLocationListByServiceResourceId(this.locationListByServiceResource);
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
    // this.spinner.show();
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getServiceResourceById();
      // this.spinner.hide();
    } else {
      this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.spinner.hide();
    }
    return false;
  }

  onClickEdit(serviceResource) {
    this.spinner.show();
    this._serviceResourceService.getServiceResourceById(this.serviceResource.serviceResourceId).subscribe(data => {
      this.serviceResourceRequest.serviceResourceId = data.serviceResourceId;
      this.serviceResourceRequest.name = data.name;
      this.serviceResourceRequest.userEmailId = data.userEmailId;
      this.serviceResourceRequest.locationId = data.locationId;
      this.serviceResourceRequest.recourceTypeId = data.resourceTypeId;
      this.serviceResourceRequest.contactId = data.contactId;
      this.serviceResourceRequest.active = data.active;
      this.serviceResourceRequest.locationName = data.locationName;
      this.serviceResourceRequest.createdByFirstName = data.createdByFirstName;
      this.serviceResourceRequest.createdByMiddleName = data.createdByMiddleName;
      this.serviceResourceRequest.createdByLastName = data.createdByLastName;
      this.serviceResourceRequest.createdDate = data.createdDate;
      this.serviceResourceRequest.modifiedByFirstName = data.modifiedByFirstName;
      this.serviceResourceRequest.modifiedByMiddleName = data.modifiedByMiddleName;
      this.serviceResourceRequest.modifiedByLastName = data.modifiedByLastName;
      this.serviceResourceRequest.modifiedDate = data.modifiedDate;

      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_SERVICE_Resourse_HEADING;
    this.createServiceResourceModel.show();
  }

  cancelData() {
    this.createServiceResourceModel.hide();
    this.onCancle.emit('cancled');
    this.serviceResourceRequest = new ServiceResourseRequest();
  }

  onServiceResourseCreated($event: ServiceResourseResponse) {
    this.serviceResource = $event;
    this.getServiceResourceById();
    this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
  }

  // ServiceAppointment Starts
  setPagePreviousServiceAppointments(event: any) {
    event.preventDefault();
    this.pageServiceAppointments = this.pageServiceAppointments - 1;
    this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
  }

  setPageServiceAppointments(event: any) {
    event.preventDefault();
    this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
  }

  setPageNextServiceAppointments(event: any) {
    event.preventDefault();
    this.pageServiceAppointments = this.pageServiceAppointments + 1;
    this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
  }

  getServiceAppointmentListByServiceResourceId(id) {
    this.spinner.show();
    this._serviceResourceService.getServiceAppointmentListByServiceResourceId(id,
      this.pageServiceAppointments).subscribe(
        data => {
          this.serviceAppointmentsListByServiceResourceId = data.content;
          this.firstServiceAppointments = data.first;
          this.lastServiceAppointments = data.last;
          this.sizeServiceAppointments = data.size;
          this.totalElementsServiceAppointments = data.totalElements;
          this.serialNumberServiceAppointments = ((this.pageServiceAppointments) * this.sizeServiceAppointments);
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
  }

  showServiceAppointment() {
    // this.isEditModeServiceAppointment = false;
    // this.serviceAppointmentResponse = new ServiceAppointmentdetails();
    this.createserviceAppointmentModel.show();
  }

  // Zone Ends



  // Location Starts

  // setPagePreviousLocation(event: any) {
  //   event.preventDefault();
  //   this.pageLocation = this.pageLocation - 1;
  //   this.getLocationListByServiceResourceId(this.serviceResource.serviceResourceId);
  // }

  // setPageLocation(event: any) {
  //   event.preventDefault();
  //   this.getLocationListByServiceResourceId(this.serviceResource.serviceResourceId);
  // }

  // setPageNextLocation(event: any) {
  //   event.preventDefault();
  //   this.pageLocation = this.pageLocation + 1;
  //   this.getLocationListByServiceResourceId(this.serviceResource.serviceResourceId);
  // }

  // getLocationListByServiceResourceId(id) {
  //   this.spinner.show();
  //   this._serviceResourceService.getLocationListByServiceResourceId(id,
  //     this.pageLocation).subscribe(
  //       data => {
  //         this.locationListByServiceResourceId = data.content;
  //         this.firstLocation = data.first;
  //         this.lastLocation = data.last;
  //         this.sizeLocation = data.size;
  //         this.totalElementsLocation = data.totalElements;
  //         this.serialNumberLocation = ((this.pageLocation) * this.sizeLocation);
  //         this.spinner.hide();
  //       }, err => {
  //         this.spinner.hide();
  //         this.message = err.error.message;
  //         this.popupSuccessModel.show();
  //       });
  // }

  // showLocation() {

  // }

  // Zone Ends
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     // this.onCancle.emit('cancled');
  //   }
  // }

  onServiceAppointmentCreated($event) {
    this.getServiceAppointmentListByServiceResourceId(this.serviceResource.serviceResourceId);
    this.getServiceResourceById();
  }

  cancelServiceAppointment() {
    this.createserviceAppointmentModel.hide();
  }

  onClickEditServiceAppointment(id) {
    this.spinner.show();
    this.serviceAppointmentService.getServiceAppointmentById(id).subscribe(data => {
      // this.serviceAppointmentRequest.serviceAppointmentId = data.serviceAppointmentId;
      // this.serviceAppointmentRequest.faultObserved = data.faultObserved;
      // this.serviceAppointmentRequest.caseId = data.caseId;
      // this.serviceAppointmentRequest.correctiveActions = data.correctiveActions;
      // this.serviceAppointmentRequest.punchPointExiom = data.punchPointExiom;
      // this.serviceAppointmentRequest.punchPointCustomer = data.punchPointCustomer;
      // this.serviceAppointmentRequest.visitDate = data.visitDate;
      // this.serviceAppointmentRequest.customerRepresentativeName = data.customerRepresentativeName;
      // this.serviceAppointmentRequest.customerRepresentativeContact = data.customerRepresentativeContact;
      // this.serviceAppointmentRequest.remark = data.remark;
      // this.serviceAppointmentRequest.statusId = data.serviceAppointmentStatusId;
      // this.serviceAppointmentRequest.pendingResponsibilityId = data.pendingResponsibilityId;
      // this.serviceAppointmentRequest.earliestStartPermittedDate = data.earliestStartPermittedDate;
      // this.serviceAppointmentRequest.earliestStartPermittedTime = data.earliestStartPermittedTime;
      // this.serviceAppointmentRequest.dueDate = data.dueDate;
      // this.serviceAppointmentRequest.dueTime = data.dueTime;
      // this.serviceAppointmentRequest.tFlag = data.tFlag;
      // this.serviceAppointmentRequest.ownerId = data.ownerEmailId;
      // this.serviceAppointmentRequest.assetsSerialNumber = data.assetsSapSerialNumber;
      // this.serviceAppointmentRequest.partCode = data.assetsPartCode;
      this.serviceAppointmentResponse=data;
  

      this.spinner.hide();
    });
this.isEditMode=true;
    // this.isEditModeServiceAppointment = true;
    this.createserviceAppointmentModel.show();
  }

}
