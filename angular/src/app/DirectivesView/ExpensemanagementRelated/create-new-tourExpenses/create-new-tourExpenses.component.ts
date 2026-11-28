import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import { FieldValidation } from './../../../services/validation.services';
import { TourExpenseService } from './../../../services/tourExpense.service';
import { TourExpenseRequest, TourExpenseResponse, ModeOfTravel, OwnConveyance, BoardingLoadingDetailsResponse } from './../../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';

@Component({
  selector: 'app-create-new-tourExpenses',
  templateUrl: './create-new-tourExpenses.component.html',
  styleUrls: ['./create-new-tourExpenses.component.css']
})

export class CreateNewTourExpensesComponent implements OnInit {

  @Input()
  set isEditModeTourExpense(isEditModeTourExpense: boolean) {
    this._isEditModeTourExpense = isEditModeTourExpense;
  }

  get isEditModeTourExpense(): boolean {
    return this._isEditModeTourExpense;
  }

  arrivalTimeSaved: string;
  depTimeSaved: string;

  @Input()
  set editTourExpenseData(tourExpenseRequest: TourExpenseRequest) {
    // if (this._isEditModeTourExpense) {
    //   this.tourExpenseRequest = new TourExpenseRequest();
    //   this.tourExpenseRequest.expenseManagementId = tourExpenseRequest.expenseManagementId;
    //   this.tourExpenseRequest.expenseManagementManualId = tourExpenseRequest.expenseManagementManualId;
    // this.tourExpenseRequest.manualId = tourExpenseRequest.manualId;
    //   this.arrivalTimeSaved = tourExpenseRequest.arrivalTime;
    //   this.depTimeSaved = tourExpenseRequest.departureTime;
    //   this.tourExpenseRequest.arrivalTime = tourExpenseRequest.arrivalTime;
    //   this.tourExpenseRequest.departureTime = tourExpenseRequest.departureTime;
    //   this.getModeOfTravel();
    // }
    this.tourExpenseRequest = tourExpenseRequest;
  }

  get editTourExpenseData(): TourExpenseRequest {
    return this.tourExpenseRequest;
  }
  // shagufta
  touramountToDisbursed:string;
boardingName:string;
@Input()
set BordingName(grade: string) {
  this.boardingName = grade;
}

get BordingName(): string {
  return this.boardingName;
}
@Input()
set TouramntToDisbursed(touramountToDisbursed:number){
  this.tourExpenseRequest.amountToDisbursed=touramountToDisbursed;
}
get TouramntToDisbursed():number{
  return this.tourExpenseRequest.amountToDisbursed;
}

  dateCustomClasses: DatepickerDateCustomClasses[];
  expenseManagementId: number;

  constructor(private _serviceAppointment: ServiceAppointmentService, private datePipe: DatePipe, private spinner: NgxSpinnerService,
    private tourExpenseService: TourExpenseService, private _activatedRoute: ActivatedRoute,private boadingservice:BoardingLoadingService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.params.subscribe(pram => {
      this.expenseManagementId = pram['id'];
    });
  }

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onTourExpensesCreated: EventEmitter<TourExpenseRequest> = new EventEmitter<TourExpenseRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleTourExpense: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popupSuccessModel')
  popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel')
  createContactModel: ModalDirective;
  @ViewChild('timedisplay')
  timedisplay: ModalDirective;
  @ViewChild('borloddtailModel')
  borloddtailModel:ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeTourExpense: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  tourExpenseRequest: TourExpenseRequest = new TourExpenseRequest();
  tourExpenseResponse: TourExpenseResponse = new TourExpenseResponse();
  modeOfTravel: Array<ModeOfTravel> = [];
  ownConveyance: Array<OwnConveyance> = [];
  timepickerVisible = false;
  mytime: Date;
  timeList: Array<string> = [];
  today = new Date();
  response=new BoardingLoadingDetailsResponse()
 farerequired:string="col-md-6 col-sm-6 form-group required";
  getTourExpenseById() {
    this.spinner.show();
  }

  ngOnInit() {
    this.getModeOfTravel();
    this.getTimeList();
  }

  getModeOfTravel() {
    this.tourExpenseService.getModeOfTravel().subscribe(data => {
      this.modeOfTravel = data;
      if ((this.modeOfTravel.length !== 0) && (this.tourExpenseRequest.modeOfTravelId) !== 0) {
        this.getOwnConveyance(this.tourExpenseRequest.modeOfTravelId);
      }
    }, err => {
      this.spinner.hide();
    });
  }
  classRequiredownConv:string='col-md-6 col-sm-6 form-group required';
  getOwnConveyance(id) {
 
    this.tourExpenseService.getOwnConveyance(id).subscribe(data => {
      this.ownConveyance = data;
    }, err => {
      this.spinner.hide();
    });
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.message === 'Updated Successfully') {
      // this.onCancleTourExpense.emit('cancled');
    }
  }

  cancelTourExpense() {
    this.onCancleTourExpense.emit('cancled');
  }

  createTourExpense() {
    // const tempArrDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');
    // const tempDepDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    // this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    // this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');
    // this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    // this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');

    if (!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) {
      this.tourExpenseRequest.fare = this.tourExpenseRequest.fare + '';
    }
    if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount)) {
      this.tourExpenseRequest.approvedAmount = this.tourExpenseRequest.approvedAmount + '';
    }
    if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance)) {
      this.tourExpenseRequest.distance = this.tourExpenseRequest.distance + '';
    }
    this.tourExpenseRequest.expenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.tourExpenseRequest.departureDate) === null || (this.tourExpenseRequest.departureDate) === '') {
      this.message = 'Please fill Departure Date';
    } else if ((this.tourExpenseRequest.departureTime) === null || (this.tourExpenseRequest.departureTime) === '') {
      this.message = 'Please fill Departure Time';
    } else if ((this.tourExpenseRequest.arrivalDate) === null || (this.tourExpenseRequest.arrivalDate) === '') {
      this.message = 'Please fill Arrival Date';
    } else if ((this.tourExpenseRequest.arrivalDate) < (this.tourExpenseRequest.departureDate)) {
      this.message = 'Arrival Date cannot be less than Departure date';
    } else if ((this.tourExpenseRequest.arrivalTime) === null || (this.tourExpenseRequest.arrivalTime) === '') {
      this.message = 'Please fill Arrival Time';
    } else if ((this.tourExpenseRequest.arrivalDate === this.tourExpenseRequest.departureDate) &&
      (this.tourExpenseRequest.arrivalTime < this.tourExpenseRequest.departureTime)) {
      this.message = 'Arrival cannot be before Departure';
    } else if (FieldValidation.isEmpty(this.tourExpenseRequest.fromPlace) && this.tourExpenseRequest.fromPlace===undefined) {
      this.message = 'Please fill From Place';
    } else if (FieldValidation.isEmpty(this.tourExpenseRequest.toPlace) && (this.tourExpenseRequest.toPlace===undefined)) {
      this.message = 'Please fill To Place';
    } else if (this.tourExpenseRequest.modeOfTravelId === 0) {
      this.message = 'Please fill Mode Of Travel';
    }else if((this.tourExpenseRequest.modeOfTravelId===8 )&& (this.tourExpenseRequest.ownConveyanceId==0)){
   this.message='Please fill Own Conveyance'
    } 
     else if ((this.tourExpenseRequest.modeOfTravelId===8) && (!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) &&
      (!FieldValidation.isValidCurrency(this.tourExpenseRequest.fare)) && this.farerequired=="col-md-6 col-sm-6 form-group required") {
      this.message = 'Please fill valid fare';
    } else if ((this.tourExpenseRequest.modeOfTravelId===8)&&(!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) && (this.tourExpenseRequest.fare).length > 8) {
      this.message = 'Fare exceeds maximum limit';
    }else if((this.tourExpenseRequest.modeOfTravelId===8)&&(!FieldValidation.isEmpty(this.tourExpenseRequest.distance)) && (this.tourExpenseRequest.distance===undefined)){
     this.message='Please fill Distance';
    }
     else if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance) &&
      !FieldValidation.isValidCurrency(this.tourExpenseRequest.distance)) {
      this.message = 'Please fill valid distance';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance) && (this.tourExpenseRequest.distance).length > 8) {
      this.message = 'Distance exceeds maximum limit';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount) &&
      (!FieldValidation.isValidCurrency(this.tourExpenseRequest.approvedAmount))) {
      this.message = 'Please fill valid approved amount';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount) &&
      (this.tourExpenseRequest.approvedAmount).length > 8) {
      this.message = 'Approved amount exceeds maximum limit';
    } else {
      this.flag = true;
      if (this._isEditModeTourExpense === false) {
        this.spinner.show();
        // this.tourExpenseRequest.departureTime = this.datePipe.transform(this.tourExpenseRequest.departureTime, 'h:mm:ss');
        // this.tourExpenseRequest.arrivalTime = this.datePipe.transform(this.tourExpenseRequest.arrivalTime, 'h:mm:ss');
        this.tourExpenseService.postTourExpense(this.tourExpenseRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onTourExpensesCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();
          // this.onExpensesAdded.emit('str');
          this.tourExpenseRequest = new TourExpenseRequest();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner.show();
        this.tourExpenseService.putTourExpense(this.tourExpenseRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onTourExpensesCreated.emit(data);
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
    // this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'dd-MM-yyyy');
    // this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'dd-MM-yyyy');

  }

  getTimeList() {
    this.spinner.show();
    this._serviceAppointment.getTimeList().subscribe(data => {
      this.timeList = data;
      // if (this._isEditModeTourExpense === true) {
      //   this.tourExpenseRequest.departureTime = this.depTimeSaved;
      //   this.tourExpenseRequest.arrivalTime = this.arrivalTimeSaved;
      // }
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });
  }
// shagufta

OpnGrade(bordingName){
  console.log(bordingName);
  this.borloddtailModel.show();
  this.heading=bordingName;
  this.spinner.show();
  this.boadingservice.getDetailByboadingName(bordingName).subscribe(data=>{
    this.spinner.hide();
    this.response=data;
  });

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
companyPaid($event){
  if(this.tourExpenseRequest.companyPaid!=true){
    this.farerequired="col-md-6 col-sm-6 form-group required";
  }
  else
  {
    this.farerequired="col-md-6 col-sm-6 form-group";
  }
}
}


