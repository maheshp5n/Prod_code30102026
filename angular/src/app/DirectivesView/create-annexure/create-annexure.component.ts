import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { ContactsService } from './../../services/contacts.services';
import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import {
  ResourceType,
  LocationForList,
  UserForList,
  AnnexureRequest,
  AnnexureResponse,
  AnnexureApprovalStatus
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { AnnexureService } from '../../services/annexure.services';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-create-annexure',
  templateUrl: './create-annexure.component.html',
  styleUrls: ['./create-annexure.component.scss']
})
export class CreateAnnexureComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  _isEditMode: boolean;
  annexureRequest: AnnexureRequest = new AnnexureRequest();
  message: string;
  istrue: boolean;
  loginData;
  userForList: Array<UserForList> = [];
  resourceTypeList: Array<ResourceType> = [];
  locationTypeList: Array<LocationForList> = [];
  approvalStatusList: Array<AnnexureApprovalStatus> = [];
  faPlus: string = 'btn btn-primary fa fa-plus';
  flag: boolean;
  noMatchLocation: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  tempServiceId: number;
  tempServiceName: string;
  noMatchLegalEntity: string;
  today = new Date();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editAnnexureData(annexureRequest: AnnexureRequest) {
    this.spinner.show();
    this.popupNgClass = 'alert alert-danger center center-block';
    this.annexureRequest = annexureRequest;
    if (!this._isEditMode) {
      this.annexureRequest.approvalStatusId = 1;
      this.spinner.hide();
    }
  }

  get editAnnexureData(): AnnexureRequest {
    return this.annexureRequest;
  }

  @Output()
  onAnnexureCreated: EventEmitter<AnnexureResponse> = new EventEmitter<AnnexureResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  dateCustomClasses: DatepickerDateCustomClasses[];

  constructor(private spinner: NgxSpinnerService, private loginService: Loginservices,
    private _annexureService: AnnexureService, private _contactsService: ContactsService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  ngOnInit() {
    this.ownerName();
    this.getApprovalStatus();
    const loginDetails = JSON.parse(sessionStorage.getItem('login'));
    this.annexureRequest.businessDomainId = loginDetails.emptype['businessDomainId'];
    if (loginDetails.emailId !== null) {
      this._contactsService.getContactsByEmployeeEmailId(loginDetails.emailId).subscribe(data => {
        if (data !== null) {
          this.annexureRequest.vendorCode = data.employeeCode;
          // this.annexureRequest.circle = 'NEW DELHI';
          // this.annexureRequest.owner = data.contactOwnerFirstName + ' ' + data.contactOwnerLastName;
        } else {
          this.spinner.hide();
        }
      }, err => {
        this.spinner.hide();
        this.message = err.status + ' : ' + err.message;
        this.popupSuccessModel.show();
      });
    }
  }

  ownerName() {
    this.loginData = this.loginService.getLoggedInUser();
    // this.annexureRequest.owner = this.loginData.firstName + ' ' + this.loginData.lastName;
  }

  cancelData() {
    this.annexureRequest = new AnnexureRequest();
    this.onCancle.emit('cancled');
  }

  cancelData2() {
    this.isEditMode = false;
  }

  postCreateAnnexure() {
    let temp = new AnnexureResponse();
    this.flag = false;
    if (FieldValidation.isEmpty(this.annexureRequest.invoiceNumber)) {
      this.message = 'Please fill Invoice Number';
    } else if (this.annexureRequest.invoiceDate === '' || this.annexureRequest.invoiceDate === null) {
      this.message = 'Please fill Invoice Date';
    }else if(this.annexureRequest.poNumber==null || this.annexureRequest.poNumber==undefined){
      this.message='Please fill PO Number';
    }else if(this.annexureRequest.poDate==null|| this.annexureRequest.poDate==undefined){
     this.message='Please fill PO Date'
    }
     else {
      // else if (this.annexureRequest.approvalStatusId === null || this.annexureRequest.approvalStatusId === 0) {
      //   this.message = 'Please fill Approval Status';
      // }
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this._annexureService.postAnnexure(this.annexureRequest).subscribe(data => {
          this.tempServiceId = data.id;
          this.tempServiceName = data.manualId;
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onAnnexureCreated.emit(data);
          this.popupSuccessModel.show();
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      } else {
        this.spinner.show();
        this._annexureService
          .putAnnexure(this.annexureRequest).subscribe(data => {
            this._annexureService.getAnnexureById(this.annexureRequest.id).subscribe(data2 => {
              temp = data2;
              this.spinner.hide();
            }, err => {
              this.spinner.hide();
            });
            this.message = 'Updated Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.spinner.hide();
            this.popupSuccessModel.show();
            this.onAnnexureCreated.emit(data);
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

  getApprovalStatus() {
    this._annexureService.getApprovalStatus().subscribe(data => {
      this.approvalStatusList = data;
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

  cancelDataAnnexureLineItem() {
    this.onCancle.emit('can');
  }

  // setAmountByGl(){
  //   glcodeli
  // }

}
