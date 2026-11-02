import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { BusinessDomain, cityDetailsReq, ModeOfTravel, OtherChargesExpenseRequest, OtherChargesExpenseResponse, OwnConveyance } from '../../../services/model';
import { OtherChargesService } from '../../../services/otherCharges.service';
import { FieldValidation } from '../../../services/validation.services';
import { BtotherChargesExpenseService } from '../../bat_services/btother-charges-expense.service';
import { BtOtherChargesExpenseRequest, BtOtherChargesExpenseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-other-charges',
  templateUrl: './bt-create-other-charges.component.html',
  styleUrls: ['./bt-create-other-charges.component.css']
})
export class BtCreateOtherChargesComponent implements OnInit {

  // Tour starts
  @Input()
  set isEditModeOtherCharges(isEditModeOtherCharges: boolean) {
    this._isEditModeOtherCharges = isEditModeOtherCharges;
  }

  get isEditModeOtherCharges(): boolean {
    return this._isEditModeOtherCharges;
  }

  @Input()
  set editOtherChargesData(otherChargesRequest: BtOtherChargesExpenseRequest) {
    if (this._isEditModeOtherCharges === false) {
      this.otherChargesRequest = new BtOtherChargesExpenseRequest();
      this.otherChargesRequest.btExpenseManagementId = otherChargesRequest.btExpenseManagementId;
    }
    this.otherChargesRequest = otherChargesRequest;
    this.otherChargesRequest.expenseManagementManualId = otherChargesRequest.btExpenseManagementManualId;


    if (this.otherChargesRequest.businessDomainId !== 0) {
      this.getOtherChargesTypeList(1);
    }
  }

  get editOtherChargesData(): BtOtherChargesExpenseRequest {
    return this.otherChargesRequest;
  }
  @Input()
set LocalamntToDisbursed(localamountToDisbursed:number){
  this.otherChargesRequest.amountToDisbursed=localamountToDisbursed;
}
get BodamntToDisbursed():number{
  return this.otherChargesRequest.amountToDisbursed;
}
  dateCustomClasses: DatepickerDateCustomClasses[];
  expenseManagementId: number;

  constructor(private spinner: NgxSpinnerService,private loginservice:Loginservices,private datePipe:DatePipe,
    private otherChargesService: BtotherChargesExpenseService, private _activatedRoute: ActivatedRoute, private _global: GlobalserviceService
  ) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.params.subscribe(pram => {
      this.expenseManagementId = pram['id'];
    });

  }

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onOtherChargesCreated: EventEmitter<BtOtherChargesExpenseResponse> = new EventEmitter<BtOtherChargesExpenseResponse>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleOtherCharges: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends

  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeOtherCharges: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  // popupNgClass: string = 'alert alert-danger center center-block';
  otherChargesRequest: BtOtherChargesExpenseRequest = new BtOtherChargesExpenseRequest();
  otherChargesResponse: BtOtherChargesExpenseResponse = new BtOtherChargesExpenseResponse();
  modeOfTravel: Array<ModeOfTravel> = [];
  ownConveyance: Array<OwnConveyance> = [];
  cityDetailsReq: cityDetailsReq = new cityDetailsReq();
  typeListOtherExpenses: Array<BusinessDomain> = [];
  today = new Date();
  loginData: any;

  ngOnInit() {
    this.loginData = this.loginservice.getLoggedInUser();
    this.otherChargesRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
    
    // this.loginData = JSON.parse(sessionStorage.getItem('login'));
    // this.otherChargesRequest.businessDomainId = this.loginData.emptype['businessDomainId'];
    if (this.otherChargesRequest.businessDomainId !== 0) {
      this.getOtherChargesTypeList(1);
    }
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.message === 'Updated Successfully') {
  //     // this.onCancleOtherCharges.emit('cancled');
  //   }
  // }

  cancelOtherCharges() {
    this.onCancleOtherCharges.emit('cancled');
  }

  getOtherChargesTypeList(id) {
    this.otherChargesService.getOtherChargesTypeList(id).subscribe(data => {
      this.typeListOtherExpenses = data;
    });
  }

  createOtherCharges() {

    
    this.otherChargesRequest.date = this.datePipe.transform(this.otherChargesRequest.date, 'yyyy-MM-dd');
    this.otherChargesRequest.amount = this.otherChargesRequest.amount + '';
    this.otherChargesRequest.btExpenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.otherChargesRequest.date) === null || (this.otherChargesRequest.date) === '') {
      this.message = 'Please Fill Date';
    } else if ((this.otherChargesRequest.billNo) === null || (this.otherChargesRequest.billNo) === '') {
      this.message = 'Please Fill Bill No.';
    } else if ((this.otherChargesRequest.billNo).length > 200) {
      this.message = 'Bill No. Exceeds Maximum Limit';
    } else if (this.otherChargesRequest.otherChargesTypeId === 0) {
      this.message = 'Please Fill Type';
    } else if (FieldValidation.isEmpty(this.otherChargesRequest.amount)) {
      this.message = 'Please Fill Amount';
    } else if (!FieldValidation.isValidCurrencyMandatory(this.otherChargesRequest.amount)) {
      this.message = 'Please Fill Valid Amount';
    } else if ((this.otherChargesRequest.amount).length > 8) {
      this.message = 'Amount Exceeds Maximum Limit';
    } else {
      this.flag = true;
      if (this._isEditModeOtherCharges === false) {
        this.spinner.show();
        this.otherChargesService.postOtherCharges(this.otherChargesRequest).subscribe(data => {
          this.message = data.manualId+' Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onOtherChargesCreated.emit(data);
          this.spinner.hide();
          this.onExpensesAdded.emit('str');
          this.otherChargesRequest = new BtOtherChargesExpenseRequest();
          this._global.alert({ type: '', message: this.message });
          this.onCancleOtherCharges.emit('cancled');

        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
      } else {
        this.spinner.show();
        this.otherChargesService.putOtherCharges(this.otherChargesRequest).subscribe(data => {
          this.message = data.manualId+' Updated Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.onOtherChargesCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });
          this.onCancleOtherCharges.emit('cancled');

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
   // shagufta
   onlyNumbersForRs(key): any {

    const charCode = (key.which) ? key.which : key.keyCode;
    console.log("Only number for rs function");
    if(charCode>31 && charCode==46){
      return true; 
     }
    else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
      return false;
    }
    else if(key.target.value.length>5){
      this.message="More Than 5 Digits Can Not Be Accepted";
      this._global.alert({ type: 'alert', message: this.message });
     return false;
   }
   
  }

}


