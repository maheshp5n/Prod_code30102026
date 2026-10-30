import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentReAssignRequest, ServiceCenterForList, ServiceResourseForList } from '../../../services/model';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';

@Component({
  selector: 'app-bat-reassignserviceappointment',
  templateUrl: './bat-reassignserviceappointment.component.html',
  styleUrls: ['./bat-reassignserviceappointment.component.scss']
})
export class BatReassignserviceappointmentComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

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
    console.log(this.serviceAppointmentReAssignRequest.serviceCenterId)
    console.log(this.serviceAppointmentReAssignRequest.serviceResourceId)
    this.serviceAppointmentReAssignRequest.serviceResourceName = serviceAppointmentReAssignRequest.serviceResourceName;
    this._btsiteservice.getServiceResourcesList(this.serviceAppointmentReAssignRequest.serviceCenterId).subscribe(data => {
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

  constructor(private spinner: NgxSpinnerService, private _btlegalEntityService: BtlegalentityService,
    private _btsiteservice:BtsiteserviceService,private btresourceService: BtserviceresourseService, private _btserviceAppointment: BtServiceappointmentService) { }

  ngOnInit() {
    this.getLegalEntityList();
    // this.getServiceResourceList();
    this.faPlus;
  }

  getLegalEntityList() {  
    this.spinner.show();
    this._btlegalEntityService.getLegalEntityForList().subscribe(data => {
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
      this._btsiteservice.getServiceResourcesList(serviceCenterId).subscribe(data => {
        this.spinner.hide();
        this.resourceList = data;
       
      });
    }
  }

  getServiceResourceList() {
    this.spinner.show();
    this.btresourceService.getServiceResourceForList().subscribe(data => {
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
      this._btserviceAppointment.reAssignServiceAppoinment(this.serviceAppointmentReAssignRequest).subscribe(data => {
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
