import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { AnnexureGLCode, ServiceAppointmentResponse } from './../../services/model';
import { ContactsService } from '../../services/contacts.services';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { UserForList, AnnexureLineItemRequest, AnnexureLineItemResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { AnnexureLineItemService } from '../../services/annexureLineItem.services';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';

@Component({
  selector: 'app-create-annexureLineItem',
  templateUrl: './create-annexureLineItem.component.html',
  styleUrls: ['./create-annexureLineItem.component.scss']
})
export class CreateAnnexureLineItemComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  _isEditMode: boolean;
  annexureLineItemRequest: AnnexureLineItemRequest = new AnnexureLineItemRequest();
  message: string;
  istrue: boolean;
  loginData;
  userForList: Array<UserForList> = [];
  faPlus: string = 'btn btn-primary fa fa-plus';
  flag: boolean;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempServiceId: number;
  tempServiceName: string;
  gLCodeForList: Array<AnnexureGLCode> = [];
  serviceAppointmentList: Array<ServiceAppointmentResponse> = [];
  today = new Date();
  serFlag: boolean = false;
  id: number;

  @Input()
  set isEditModeAnnexure(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditModeAnnexure(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAnnexureLineItemData(annexureLineItemRequest: AnnexureLineItemRequest) {
    // this.spinner.show();
    this.popupNgClass = 'alert alert-danger center center-block';
    this.annexureLineItemRequest = annexureLineItemRequest;
    // this.annexureLineItemRequest.circleName = annexureLineItemRequest.circleName;
    // this.annexureLineItemRequest.siteId = annexureLineItemRequest.siteId;
    // this.annexureLineItemRequest.caseId = annexureLineItemRequest.caseId;
    // this.annexureLineItemRequest.siteName = annexureLineItemRequest.siteName;
    // this.annexureLineItemRequest.caseIdManual = annexureLineItemRequest.caseIdManual;
    // this.annexureLineItemRequest.workStatus = annexureLineItemRequest.workStatus;
    // this.annexureLineItemRequest.activity = annexureLineItemRequest.activity;
    this.annexureLineItemRequest.serviceAppointmentId = annexureLineItemRequest.serviceAppointmentId;
    this.id = annexureLineItemRequest.serviceAppointmentId;
    // this.spinner.hide();
  }

  get editAnnexureLineItemData(): AnnexureLineItemRequest {
    return this.annexureLineItemRequest;
  }

  @Output()
  onAnnexureLineItemCreated: EventEmitter<AnnexureLineItemResponse> = new EventEmitter<AnnexureLineItemResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  dateCustomClasses: DatepickerDateCustomClasses[];

  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices,
    private _annexureLineItemService: AnnexureLineItemService,private serviceSA:ServiceAppointmentService, private _contactsService: ContactsService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.getClosedServiceAppointmentForAnnexure();
    this.getGLCode();
    this.ownerName();
    const loginDetails = JSON.parse(sessionStorage.getItem('login'));
    if (loginDetails.emailId !== null) {
      this._contactsService.getContactsByEmployeeEmailId(loginDetails.emailId).subscribe(data => {
      });
    }
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
    // this.annexureLineItemRequest.owner = this.loginData.firstName + ' ' + this.loginData.lastName;
  }

  cancelData() {
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
    this.onCancle.emit('cancled');
  }

  cancelData2() {
    this.isEditModeAnnexure = false;
  }

  postCreateAnnexureLineItem() {
    let temp = new AnnexureLineItemResponse();
    this.flag = false;
    if ((this.annexureLineItemRequest.serviceAppointmentId === undefined) ||
      (this.annexureLineItemRequest.serviceAppointmentId === null) || (this.annexureLineItemRequest.serviceAppointmentId === 0)) {
      this.message = 'Please fill Service Appointment';
    } else if (this.annexureLineItemRequest.glCodeId === 0 || this.annexureLineItemRequest.glCodeId === null) {
      this.message = 'Please fill GL Code';
    } else if (this.annexureLineItemRequest.workCompletionDate === undefined || this.annexureLineItemRequest.workCompletionDate === null) {
      this.message = 'Please fill Work Completion Date';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this._annexureLineItemService.postAnnexureLineItem(this.annexureLineItemRequest).subscribe(
          data => {
            this._annexureLineItemService.getAnnexureLineItemById(data.id).subscribe(data2 => {
              this.tempServiceId = data2.id;
              this.tempServiceName = data2.manualId;
              temp = data2;
              this.message = 'Submitted Successfully !';
              this.onAnnexureLineItemCreated.emit(data);
              this.popupNgClass = 'alert alert-success center center-block';
              this.spinner.hide();
              this.popupSuccessModel.show();
            }, err => {
              this.spinner.hide();
            });
          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          });
      } else {
        this.spinner.show();
        this._annexureLineItemService
          .putAnnexureLineItem(this.annexureLineItemRequest)
          .subscribe(
            data => {
              this._annexureLineItemService.getAnnexureLineItemById(this.annexureLineItemRequest.id).subscribe(data2 => {
                temp = data2;
                this.onAnnexureLineItemCreated.emit(data2);
                this.spinner.hide();
              }, err => {
                this.spinner.hide();
              });
              this.message = 'Updated Successfully';
              this.popupNgClass = 'alert alert-success center center-block';
              this.spinner.hide();
              this.popupSuccessModel.show();
            }, err => {
              this.spinner.hide();
              this.message = err.error.message;
              this.popupSuccessModel.show();
            });
      }
    }

    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  getGLCode() {
    this._annexureLineItemService.getGLCode().subscribe(data => {
      this.gLCodeForList = data;
    });
  }

  getClosedServiceAppointmentForAnnexure() {
    this.spinner.show();
    this._annexureLineItemService.getClosedServiceAppointmentForAnnexure().subscribe(data => {
      this.serviceAppointmentList = data;
      this.annexureLineItemRequest.serviceAppointmentId = this.id;
      // this.serFlag = true;
      // if (this.serFlag === true) {
      //   this.annexureLineItemRequest.serviceAppointmentId = this.id;
      // }
      this.spinner.hide();
    }, err => {
      this.message = err.error.message;
      this.spinner.hide();
      this.popupSuccessModel.show();
    });
  }

  getDataFromSA() {
    this.spinner.show();
    this.serviceAppointmentList.forEach(data => {
      if (this.annexureLineItemRequest.serviceAppointmentId === data.serviceAppointmentId) {
        this.annexureLineItemRequest.circleName = data.circleName;
        this.annexureLineItemRequest.siteId = data.siteId;
        this.annexureLineItemRequest.caseId = data.caseId;
        this.annexureLineItemRequest.siteName = data.siteName;
        this.annexureLineItemRequest.caseIdManual = data.caseIdManual;
        this.annexureLineItemRequest.workStatus = data.caseStatus;
        this.annexureLineItemRequest.activity = data.caseType;
      }
      this.spinner.hide();
    });
  }

  setAmount() {
    this.gLCodeForList.forEach(data => {
      if (this.annexureLineItemRequest.glCodeId === data.id) {
        this.annexureLineItemRequest.amount = data.amount;
      }
    });
  }

  cancelDataAnnexureLineItem() {
    this.onCancle.emit('can');
    this.annexureLineItemRequest = new AnnexureLineItemRequest();
  }
  warrentyStatus:string;
  customerName:string;
  onclickSA(id){
    this.spinner.show();
    this.serviceSA.getServiceAppointmentById(id).subscribe(data=>{ 
  this.annexureLineItemRequest.caseId=data.caseId;
  this.annexureLineItemRequest.caseIdManual=data.caseIdManual
  this.annexureLineItemRequest.workStatus=data.caseStatus;
  this.annexureLineItemRequest.activity=data.caseReason;
  this.customerName=data.accountName;
  this.warrentyStatus=data.caseType;
  this.annexureLineItemRequest.workCompletionDate=data.caseCloserDate;  
  this.spinner.hide();
})
  }

}
