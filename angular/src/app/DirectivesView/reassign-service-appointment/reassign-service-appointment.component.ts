import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentReAssignRequest, ServiceCenterForList, ServiceResourseForList } from '../../services/model';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { siteService } from '../../services/siteService.services';

@Component({
  selector: 'app-reassign-service-appointment',
  templateUrl: './reassign-service-appointment.component.html',
  styleUrls: ['./reassign-service-appointment.component.scss']
})
export class ReassignServiceAppointmentComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  @Output()
  onReassignAppointmentCreated: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }
  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editReassignAppointmentData(serviceAppointmentReAssignRequest: ServiceAppointmentReAssignRequest) {
    serviceAppointmentReAssignRequest = serviceAppointmentReAssignRequest;
    this.serviceAppointmentReAssignRequest.serviceAppointmentId = serviceAppointmentReAssignRequest.serviceAppointmentId;
    this.serviceAppointmentReAssignRequest.caseId = serviceAppointmentReAssignRequest.caseId;
    this.serviceAppointmentReAssignRequest.serviceCenterId = serviceAppointmentReAssignRequest.serviceCenterId;
    this.serviceAppointmentReAssignRequest.serviceCenterName = serviceAppointmentReAssignRequest.serviceCenterName;
    this.serviceAppointmentReAssignRequest.serviceResourceId = serviceAppointmentReAssignRequest.serviceResourceId;
    this.serviceAppointmentReAssignRequest.serviceResourceName = serviceAppointmentReAssignRequest.serviceResourceName;
    this.siteservice.getServiceResourcesList(this.serviceAppointmentReAssignRequest.serviceCenterId).subscribe(data => {
      this.spinner.hide();
      this.resourceList = data;
     
    });
  }
  get editReassignAppointmentData(): ServiceAppointmentReAssignRequest {
    return this.serviceAppointmentReAssignRequest;
  }


  _isEditMode: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  faPlus: string = 'btn btn-primary fa fa-plus';

  legalEntityList: Array<ServiceCenterForList> = [];
  legalEntityForList: ServiceCenterForList = new ServiceCenterForList();
  resourceForList: ServiceResourseForList = new ServiceResourseForList();
  resourceList: Array<ServiceResourseForList> = [];
  
  serviceAppointmentReAssignRequest: ServiceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();

  constructor(private spinner: NgxSpinnerService, private legalEntityService: ligalEntityService,
    private siteservice:siteService,private resourceService: ServiceResourceService, private serviceAppointment: ServiceAppointmentService) { }

  ngOnInit() {
    this.getLegalEntityList();
    // this.getServiceResourceList();
    this.faPlus;
  }

  getLegalEntityList() {
    this.spinner.show();
    this.legalEntityService.getLegalEntityForList().subscribe(data => {
      this.legalEntityList = data;
      if (data != null) {
        this.spinner.hide();
      }      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  getServiceResForList(serviceCenterId: number) {
    if (serviceCenterId != null) {
      this.spinner.show();
      this.resourceList=[];
      this.serviceAppointmentReAssignRequest.serviceResourceId=null;
      this.siteservice.getServiceResourcesList(serviceCenterId).subscribe(data => {
        this.spinner.hide();
        this.resourceList = data;
       
      });
    }
  }

  getServiceResourceList() {
    this.spinner.show();
    this.resourceService.getServiceResourceForList().subscribe(data => {
      this.resourceList = data;
      if (data != null) {
        this.spinner.hide();
      }      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });    
  }


  postCreate() {
    if (this.serviceAppointmentReAssignRequest.serviceCenterId == null || this.serviceAppointmentReAssignRequest.serviceCenterId === 0) {
      this.message = 'Please fill the service center'
    }
    else if (this.serviceAppointmentReAssignRequest.serviceResourceId == null || this.serviceAppointmentReAssignRequest.serviceResourceId === 0) {
      this.message = 'Please fill the service resource'
    }
    else {
      this.spinner.show();
      this.serviceAppointment.reAssignServiceAppoinment(this.serviceAppointmentReAssignRequest).subscribe(data => {
        this.message = "Updated Successfully";
        this.popupNgClass = "alert alert-success center center-block";
        this.onReassignAppointmentCreated.emit(data);
        if (data != null) {
          this.spinner.hide();
        }
        this.popupSuccessModel.show();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }

  }

  cancelData() {
    this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
