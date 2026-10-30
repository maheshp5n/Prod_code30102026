import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { SecondServiceAppointmentRequest, ServiceResourseForList } from '../../../services/model';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';

@Component({
  selector: 'app-bt-create-new-service-appointment-by-case',
  templateUrl: './bt-create-new-service-appointment-by-case.component.html',
  styleUrls: ['./bt-create-new-service-appointment-by-case.component.scss']
})
export class BtCreateNewServiceAppointmentByCaseComponent implements OnInit {
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
    console.log(secondServiceAppointmentRequest)
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

  constructor(private spinner: NgxSpinnerService, private _btresourceService : BtserviceresourseService, private _btserviceAppointment: BtServiceappointmentService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getServiceResourceList();
  }
  getServiceResourceList() {
    this.spinner.show();
    this._btresourceService.getServiceResourceForList().subscribe(data => {
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
      this._btserviceAppointment.postSecondServiceAppoinment(this.secondServiceAppointmentRequest).subscribe(data => {
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
