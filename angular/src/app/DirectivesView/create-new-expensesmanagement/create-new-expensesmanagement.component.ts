import { DatePipe } from '@angular/common';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Loginservices } from './../../services/login.services';
import { ContactsService } from './../../services/contacts.services';
import { CirclesService } from './../../services/circles.services';
import { ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseManagementCostCenter, Contacts } from './../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import {
  ExpenseManagementRequest, ExpenseManagementExpensePurpose, ExpenseManagementExpenseType, ExpenseManagementStatus, ContactForList
} from '../../services/model';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-create-new-expensesmanagement',
  templateUrl: './create-new-expensesmanagement.component.html',
  styleUrls: ['./create-new-expensesmanagement.component.css']
})
export class CreateNewexpensesmanagementComponent implements OnInit {

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editexpensesmanagementData(_expenseManagementRequest: ExpenseManagementRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
    // this.newexpensesmanagement.divCostCenters = _expenseManagementRequest.divCostCenters;
    this.getLoggedInUser();
    this.newexpensesmanagement.divCostCenters = this.loginData.emptype['businessDomainId'];
    this.newexpensesmanagement.businessDomainId = this.newexpensesmanagement.divCostCenters;
    if (this.newexpensesmanagement.businessDomainId !== 0) {
      // this.getStatusForList();
      this.getPurposeForList();
      this.getTypeList();
      this.getBusinessPlaceList();
      this.getBusinessAreaList();
      this.getCostCenterList();
      // this.getUserForList();
      // this.newexpensesmanagement.contactId = _expenseManagementRequest.contactId;
    }
    if (this._isEditMode) {
      this.newexpensesmanagement = _expenseManagementRequest;

      // this.contactId = this.newexpensesmanagement.contactId;
    }
  }

  get editexpensesmanagementData(): ExpenseManagementRequest {
    return this.newexpensesmanagement;
  }
  dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(private _serviceAppointment: ServiceAppointmentService,
    private spinner: NgxSpinnerService, private _contactsService: ContactsService,
    private _circlesService: CirclesService, private _expenseManagementService: ExpenseManagementService,
    private loginservice: Loginservices, private datePipe: DatePipe) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
  }

  contactId;
  isContactOpen: boolean;
  public _isEditMode: boolean;
  productFamily;
  flag: boolean;
  message: string;
  newexpensesmanagement: ExpenseManagementRequest = new ExpenseManagementRequest();
  purposeForList: Array<ExpenseManagementExpensePurpose> = [];
  typeList: Array<ExpenseManagementExpenseType> = [];
  statusForList: Array<ExpenseManagementStatus> = [];
  businessAreaForList: Array<ExpenseManagementBusinessArea> = [];
  businessPlaceForList: Array<ExpenseManagementBusinessPlace> = [];
  costcenterForList: Array<ExpenseManagementCostCenter> = [];
  contactsForlist: Array<ContactForList> = [];
  userforlist: Array<ContactForList> = [];
  loginData: any;
  newcontact: Contacts = new Contacts();
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  today = new Date();
  timeList: Array<string> = [];
  tempEmpId: number;
  tempEmpManId: string;

  @Output()
  onexpensesmanagementCreated: EventEmitter<ExpenseManagementRequest> = new EventEmitter<ExpenseManagementRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  // tslint:disable-next-line: no-output-on-prefix
  // @Output()
  // onNext: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel', { static: false }) createContactModel: ModalDirective;
  @ViewChild('timepop', { static: false }) timepop: ModalDirective;

  ngOnInit() {
    this.getTimeList();
    this.getLoggedInUser();
  }

  getContactsForList() {
    this._contactsService.getcontactsForList().subscribe(data => {
      this.contactsForlist = data;
    });
  }

  createexpensesmanagement() {
    // this.newexpensesmanagement.tourStartDate = this.datePipe.transform(this.newexpensesmanagement.tourStartDate, 'yyyy-MM-dd');
    // this.newexpensesmanagement.tourEndDate = this.datePipe.transform(this.newexpensesmanagement.tourEndDate, 'yyyy-MM-dd');
    this.newexpensesmanagement.businessDomainID = this.newexpensesmanagement.divCostCenters;
    this.flag = false;
    // if (this.newexpensesmanagement.documentDate === null || this.newexpensesmanagement.documentDate === '') {
    //   this.message = 'Please fill Document Date';
    // } else if (this.newexpensesmanagement.tourStartDate !== null && this.newexpensesmanagement.tourStartDate !== ''
    //   && this.newexpensesmanagement.tourEndDate !== null && this.newexpensesmanagement.tourEndDate !== ''
    //   && this.newexpensesmanagement.tourStartDate > this.newexpensesmanagement.tourEndDate) {
    //   this.message = 'Tour Start Date cannot be more than Tour End Date';
    // } else
    if (this.newexpensesmanagement.expensePurposeId === null || this.newexpensesmanagement.expensePurposeId === 0) {
      this.message = 'Please fill Expense Purpose';
    } else if (this.newexpensesmanagement.expenseTypeId === null || this.newexpensesmanagement.expenseTypeId === 0) {
      this.message = 'Please fill Expense Type';
    } else if (this.newexpensesmanagement.costCenterId === null || this.newexpensesmanagement.costCenterId === 0) {
      this.message = 'Please fill Cost Center / WBS Element';
    } else if (this.newexpensesmanagement.purposeOfVisit === null || this.newexpensesmanagement.purposeOfVisit === '') {
      this.message = 'Please fill Purpose';
    } else if ((this.newexpensesmanagement.purposeOfVisit).length > 255) {
      this.message = 'Purpose exceeds 255 characters';
    } else if (!FieldValidation.isEmpty(this.newexpensesmanagement.remark) && (this.newexpensesmanagement.remark).length > 255) {
      this.message = 'Remark exceeds 255 characters ';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        // this.newexpensesmanagement.tourStartTime = this.datePipe.transform(this.newexpensesmanagement.tourStartTime, 'h:mm:ss');
        // this.newexpensesmanagement.tourEndTime = this.datePipe.transform(this.newexpensesmanagement.tourEndTime, 'H:mm:ss');
        this._expenseManagementService.postexpensesmanagement(this.newexpensesmanagement)
          .subscribe(data => {
            this.tempEmpId = data.expenseManagementId;
            this.tempEmpManId = data.expenseManagementManualId;
            this.message = 'Created Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.onexpensesmanagementCreated.emit(data);
            this.spinner.hide();
            this.popupSuccessModel.show();
          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          });
      } else {
        this.spinner.show();
        this._expenseManagementService.putexpensesmanagement(this.newexpensesmanagement).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onexpensesmanagementCreated.emit(data);
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
    // this.newexpensesmanagement.next = false;
  }

  cancelData() {
    this.newexpensesmanagement = new ExpenseManagementRequest();
    this.onCancle.emit('cancled');
    // this.newexpensesmanagement.next = false;
  }

  // cancelDataNew() {
  //   this.onCancle.emit('cancled');
  // }

  // nextexpensesmanagement() {
  //   this.spinner.show();
  //   if (this.newexpensesmanagement.businessDomainId === 0) {
  //     this.spinner.hide();
  //     this.message = 'Please select a record type';
  //     this.popupSuccessModel.show();
  //   } else {
  //     this.newexpensesmanagement.next = true;
  //     this.onNext.emit('next');
  //     this.getStatusForList();
  //     this.getPurposeForList();
  //     this.getTypeList();
  //     this.getBusinessPlaceList();
  //     this.getBusinessAreaList();
  //     this.getCostCenterList();
  //     this.getUserForList();
  //     this.spinner.hide();
  //   }
  // }

  // saveDivision(i) {
  //   this.newexpensesmanagement.businessDomainId = i; 
  //   this.newexpensesmanagement.businessDomainID = i; 
  // }

  getPurposeForList() {
    this._expenseManagementService
      .getPurposeForList(this.newexpensesmanagement.businessDomainId)
      .subscribe(data => {
        this.purposeForList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  getTypeList() {
    this._expenseManagementService
      .getTypeList(this.newexpensesmanagement.businessDomainId)
      .subscribe(data => {
        this.typeList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  // getStatusForList() {
  //   this._expenseManagementService
  //     .getStatusForList(this.newexpensesmanagement.businessDomainId)
  //     .subscribe(data => {
  //       this.statusForList = data;
  //     }, err => {
  //       this.spinner.hide();
  //       this.message = err.error.message;
  //       this.popupSuccessModel.show();
  //     });
  // }

  getBusinessAreaList() {
    this._expenseManagementService
      .getBusinessAreaList(this.newexpensesmanagement.businessDomainId)
      .subscribe(data => {
        this.businessAreaForList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  getBusinessPlaceList() {
    this._expenseManagementService
      .getBusinessPlaceList(2)
      .subscribe(data => {
        this.businessPlaceForList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  getCostCenterList() {
    this._expenseManagementService
      .getCostCenterList(this.newexpensesmanagement.businessDomainId)
      .subscribe(data => {
        this.costcenterForList = data;
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }

  addNewEmployee() {
    this.newcontact = new Contacts();
    this.heading = 'Create Contact';
    this.createContactModel.show();
  }

  cancelContact() {
    this.newcontact = new Contacts();
    this.isEditMode = this._isEditMode;
    this.heading = 'Create Contact';
    this.createContactModel.hide();
  }

  getUserForList() {
    this._circlesService.getContactListForCircle().subscribe(data => {
      this.userforlist = data;
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getTimeList() {
    this._serviceAppointment.getTimeList().subscribe(data => {
      this.timeList = data;
    }, err => {
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}

