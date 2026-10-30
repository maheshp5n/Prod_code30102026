import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CirclesService } from '../../../services/circles.services';
import { ContactsService } from '../../../services/contacts.services';
import { Loginservices } from '../../../services/login.services';
import { ContactForList, Contacts, ExpenseManagementBusinessArea, ExpenseManagementBusinessPlace, ExpenseManagementCostCenter, ExpenseManagementExpensePurpose, ExpenseManagementExpenseType, ExpenseManagementRequest, ExpenseManagementStatus } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BatteryExpenseManagementRequest, BatteryExpenseManagementResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-createexpenses-management',
  templateUrl: './bt-createexpenses-management.component.html',
  styleUrls: ['./bt-createexpenses-management.component.css']
})
export class BtCreateexpensesManagementComponent implements OnInit {

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editexpensesmanagementData(_expenseManagementRequest: BatteryExpenseManagementRequest) {
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
      // console.log("exp")

      // console.log(this.newexpensesmanagement)
      // this.contactId = this.newexpensesmanagement.contactId;
    }
  }

  get editexpensesmanagementData(): BatteryExpenseManagementRequest {
    return this.newexpensesmanagement;
  }
  dateCustomClasses: DatepickerDateCustomClasses[];
  constructor(private _serviceAppointment: BtServiceappointmentService,
    private spinner: NgxSpinnerService, private _contactsService: BtcontactsService,
    private _circlesService: CirclesService, private _expenseManagementService: BtexpensemanagementService, private _global:GlobalserviceService,
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
  newexpensesmanagement: BatteryExpenseManagementRequest = new BatteryExpenseManagementRequest();
  // editexpensesmanagement: BatteryExpenseManagementEditRequest = new BatteryExpenseManagementEditRequest();
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
  onexpensesmanagementCreated: EventEmitter<BatteryExpenseManagementRequest> = new EventEmitter<BatteryExpenseManagementRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  // tslint:disable-next-line: no-output-on-prefix
  // @Output()
  // onNext: EventEmitter<string> = new EventEmitter<string>();

  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
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
    //   this.message = 'Please Fill Document Date';
    // } else if (this.newexpensesmanagement.tourStartDate !== null && this.newexpensesmanagement.tourStartDate !== ''
    //   && this.newexpensesmanagement.tourEndDate !== null && this.newexpensesmanagement.tourEndDate !== ''
    //   && this.newexpensesmanagement.tourStartDate > this.newexpensesmanagement.tourEndDate) {
    //   this.message = 'Tour Start Date cannot be more than Tour End Date';
    // } else
    if (this.newexpensesmanagement.expensePurposeId === null || this.newexpensesmanagement.expensePurposeId === 0) {
      this.message = 'Please Fill Expense Purpose';
    } else if (this.newexpensesmanagement.expenseTypeId === null || this.newexpensesmanagement.expenseTypeId === 0) {
      this.message = 'Please Fill Expense Type';
    } else if (this.newexpensesmanagement.costCenterId === null || this.newexpensesmanagement.costCenterId === 0) {
      this.message = 'Please Fill Cost Center / WBS Element';
    } else if (this.newexpensesmanagement.purposeOfVisit === null || this.newexpensesmanagement.purposeOfVisit === '') {
      this.message = 'Please Fill Purpose';
    } else if ((this.newexpensesmanagement.purposeOfVisit).length > 255) {
      this.message = 'Purpose Exceeds 255 Characters';
    } else if (!FieldValidation.isEmpty(this.newexpensesmanagement.remark) && (this.newexpensesmanagement.remark).length > 255) {
      this.message = 'Remark Exceeds 255 Characters ';
    } else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        // this.newexpensesmanagement.tourStartTime = this.datePipe.transform(this.newexpensesmanagement.tourStartTime, 'h:mm:ss');
        // this.newexpensesmanagement.tourEndTime = this.datePipe.transform(this.newexpensesmanagement.tourEndTime, 'H:mm:ss');
        this._expenseManagementService.postexpensesmanagement(this.newexpensesmanagement)
          .subscribe(data => {
            this.tempEmpId = data.id;
            this.tempEmpManId = data.manualId;
            this.message = this.tempEmpManId+' Created Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.onexpensesmanagementCreated.emit(data);
            this.spinner.hide();
            this._global.alert({ type: '', message: this.message });
            this.onCancle.emit('cancled');

          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          });
      } else {
      
        // let exp: any;
        // exp=
        this.newexpensesmanagement;

        // this.editexpensesmanagement.id=exp.id;
        // this.editexpensesmanagement.advanceAmount=exp.advanceAmount;
        // this.editexpensesmanagement.businessAreaId=exp.businessAreaId;
        // this.editexpensesmanagement.businessDomainID=exp.businessDomainId;
        // this.editexpensesmanagement.businessPlaceId=exp.businessPlaceId;
        // this.editexpensesmanagement.contactId=exp.contactId;
        // this.editexpensesmanagement.costCenterId=exp.costCenterId;
        // this.editexpensesmanagement.document_date=exp.documentDate;
        // this.editexpensesmanagement.expensePurposeId=exp.expensePurposeId;
        // this.editexpensesmanagement.expenseTypeId=exp.expenseTypeId;
        // this.editexpensesmanagement.isExpenseSynced=exp.isExpenseSynced;
        // this.editexpensesmanagement.isPost=exp.isPost;
        // this.editexpensesmanagement.isSynced=exp.isSynced;
        // this.editexpensesmanagement.narration=exp.narration;
        // this.editexpensesmanagement.purposeOfVisit=exp.purposeOfVisit;
        // this.editexpensesmanagement.remark=exp.remark;
        // this.editexpensesmanagement.statusId=exp.statusId;
        // this.editexpensesmanagement.visibleToFinance=exp.visibleToFinance;
        // console.log(this.editexpensesmanagement)

        this.spinner.show();
        this._expenseManagementService.putexpensesmanagement(this.newexpensesmanagement).subscribe(data => {
          this.message = data.manualId +' Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this._global.alert({ type: '', message: this.message });
          this.onexpensesmanagementCreated.emit(data);
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
    // this.newexpensesmanagement.next = false;
  }

  cancelData() {
    this.newexpensesmanagement = new BatteryExpenseManagementRequest();
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
        this._global.alert({ type: 'alert', message: this.message });
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
        this._global.alert({ type: 'alert', message: this.message });
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
        this._global.alert({ type: 'alert', message: this.message });
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
        this._global.alert({ type: 'alert', message: this.message });
      })
    }


      getCostCenterList() {
        this._expenseManagementService
          .getCostCenterList(this.newexpensesmanagement.businessDomainId)
          .subscribe(data => {
            this.costcenterForList = data;
          }, err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
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
      this._global.alert({ type: 'alert', message: this.message });
    });
  }

  getTimeList() {
    this._serviceAppointment.getTimeList().subscribe(data => {
      this.timeList = data;
    }, err => {
    });
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');
  //   }
  // }

}