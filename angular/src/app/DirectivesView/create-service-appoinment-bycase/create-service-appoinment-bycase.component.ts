import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { SecondServiceAppointmentRequest, ServiceResourseForList } from '../../services/model';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceResourceService } from '../../services/serviceresource.services';

@Component({
  selector: 'app-create-service-appoinment-bycase',
  templateUrl: './create-service-appoinment-bycase.component.html',
  styleUrls: ['./create-service-appoinment-bycase.component.scss']
})
export class CreateServiceAppoinmentBycaseComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onSecondServiceAppointmentCreated: EventEmitter<any> = new EventEmitter<any>();
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
  set editSecodServiceAppointmentData(secondServiceAppointmentRequest: SecondServiceAppointmentRequest) {
    this.secondServiceAppointmentRequest = secondServiceAppointmentRequest;
    this.secondServiceAppointmentRequest.caseId = secondServiceAppointmentRequest.caseId;
    this.secondServiceAppointmentRequest.caseManaualId = secondServiceAppointmentRequest.caseManaualId;
    this.secondServiceAppointmentRequest.serviceResourceId = secondServiceAppointmentRequest.serviceResourceId;
    this.secondServiceAppointmentRequest.serviceResourceName = secondServiceAppointmentRequest.serviceResourceName;
  }
  get editSecodServiceAppointmentData(): SecondServiceAppointmentRequest {
    return this.secondServiceAppointmentRequest;
  }

  _isEditMode: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  faPlus: string = 'btn btn-primary fa fa-plus';

  secondServiceAppointmentRequest: SecondServiceAppointmentRequest = new SecondServiceAppointmentRequest();
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  resourceForList: ServiceResourseForList = new ServiceResourseForList();
  resourceList: Array<ServiceResourseForList> = [];

  tempManual: string;
  tempId: number;

  constructor(private spinner: NgxSpinnerService, private resourceService: ServiceResourceService, private serviceAppointment: ServiceAppointmentService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getServiceResourceList();
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
    if (this.secondServiceAppointmentRequest.serviceResourceId == null || this.secondServiceAppointmentRequest.serviceResourceId === 0) {
      this.message = 'Please fill the service resource';
      this.popupSuccessModel.show();
    }
    else {
      this.spinner.show();
      this.serviceAppointment.postSecondServiceAppoinment(this.secondServiceAppointmentRequest).subscribe(data => {
        this.message = "Submitted Successfully";
        this.popupNgClass = "alert alert-success center center-block";
        this.onSecondServiceAppointmentCreated.emit(data);
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
    this.secondServiceAppointmentRequest = new SecondServiceAppointmentRequest();
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }
}
