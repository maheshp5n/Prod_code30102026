import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { FieldValidation } from './../../../services/validation.services';
import { cityDetailsReq, BusinessDomain } from '../../../services/model';
import { OtherChargesService } from '../../../services/otherCharges.service';
import { OtherChargesExpenseRequest, OtherChargesExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';

@Component({
  selector: 'app-create-new-otherCharges',
  templateUrl: './create-new-otherCharges.component.html',
  styleUrls: ['./create-new-otherCharges.component.css']
})


export class CreateNewOtherChargesComponent implements OnInit {
  // Tour starts
  @Input()
  set isEditModeOtherCharges(isEditModeOtherCharges: boolean) {
    this._isEditModeOtherCharges = isEditModeOtherCharges;
  }

  get isEditModeOtherCharges(): boolean {
    return this._isEditModeOtherCharges;
  }

  @Input()
  set editOtherChargesData(otherChargesRequest: OtherChargesExpenseRequest) {
    if (this._isEditModeOtherCharges === false) {
      this.otherChargesRequest = new OtherChargesExpenseRequest();
      this.otherChargesRequest.expenseManagementId = otherChargesRequest.expenseManagementId;
    }
    this.otherChargesRequest = otherChargesRequest;

    if (this.otherChargesRequest.businessDomainId !== 0) {
      this.getOtherChargesTypeList(this.otherChargesRequest.businessDomainId);
    }
  }

  get editOtherChargesData(): OtherChargesExpenseRequest {
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

  constructor(private spinner: NgxSpinnerService,private loginservice:Loginservices,
    private otherChargesService: OtherChargesService, private _activatedRoute: ActivatedRoute
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
  onOtherChargesCreated: EventEmitter<OtherChargesExpenseRequest> = new EventEmitter<OtherChargesExpenseRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleOtherCharges: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends

  @ViewChild('popupSuccessModel', { static: false })
  popupSuccessModel: ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeOtherCharges: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  otherChargesRequest: OtherChargesExpenseRequest = new OtherChargesExpenseRequest();
  otherChargesResponse: OtherChargesExpenseResponse = new OtherChargesExpenseResponse();
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
      this.getOtherChargesTypeList(this.otherChargesRequest.businessDomainId);
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.message === 'Updated Successfully') {
      // this.onCancleOtherCharges.emit('cancled');
    }
  }

  cancelOtherCharges() {
    this.onCancleOtherCharges.emit('cancled');
  }

  getOtherChargesTypeList(id) {
    this.otherChargesService.getOtherChargesTypeList(id).subscribe(data => {
      this.typeListOtherExpenses = data;
    });
  }

  createOtherCharges() {
    this.otherChargesRequest.amount = this.otherChargesRequest.amount + '';
    this.otherChargesRequest.expenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.otherChargesRequest.date) === null || (this.otherChargesRequest.date) === '') {
      this.message = 'Please fill Date';
    } else if ((this.otherChargesRequest.billNo) === null || (this.otherChargesRequest.billNo) === '') {
      this.message = 'Please fill Bill No.';
    } else if ((this.otherChargesRequest.billNo).length > 200) {
      this.message = 'Bill No. exceeds maximum limit';
    } else if (this.otherChargesRequest.otherChargesTypeId === 0) {
      this.message = 'Please fill Type';
    } else if (FieldValidation.isEmpty(this.otherChargesRequest.amount)) {
      this.message = 'Please fill Amount';
    } else if (!FieldValidation.isValidCurrencyMandatory(this.otherChargesRequest.amount)) {
      this.message = 'Please fill valid Amount';
    } else if ((this.otherChargesRequest.amount).length > 8) {
      this.message = 'Amount exceeds maximum limit';
    } else {
      this.flag = true;
      if (this._isEditModeOtherCharges === false) {
        this.spinner.show();
        this.otherChargesService.postOtherCharges(this.otherChargesRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onOtherChargesCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onExpensesAdded.emit('str');
          this.otherChargesRequest = new OtherChargesExpenseRequest();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner.show();
        this.otherChargesService.putOtherCharges(this.otherChargesRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onOtherChargesCreated.emit(data);
          this.onExpensesAdded.emit('str');
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
      this.message="More than 5 digits can not be accepted";
        this.popupSuccessModel.show();
     return false;
   }
   
  }

}


