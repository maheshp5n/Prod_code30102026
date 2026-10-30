import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { BoardingLoadingDetailsResponse, ModeOfTravel, OwnConveyance, TourExpenseRequest, TourExpenseResponse } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlodgingExpenseService } from '../../bat_services/btlodging-expense.service';
import { BttourExpenseService } from '../../bat_services/bttour-expense.service';
import { BtTourExpenseRequest, BtTourExpenseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-tour-expenses',
  templateUrl: './bt-create-tour-expenses.component.html',
  styleUrls: ['./bt-create-tour-expenses.component.css']
})
export class BtCreateTourExpensesComponent implements OnInit {

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
  set editTourExpenseData(tourExpenseRequest: BtTourExpenseRequest) {
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

  get editTourExpenseData(): BtTourExpenseRequest {
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

  constructor(private _serviceAppointment: BtServiceappointmentService, private datePipe:DatePipe, private spinner: NgxSpinnerService,
    private tourExpenseService: BttourExpenseService, private _activatedRoute: ActivatedRoute,private boadingservice:BtboardingLoadingExpenseService, private _global: GlobalserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.params.subscribe(pram => {
      this.expenseManagementId = pram['id'];
    });
  }

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onTourExpensesCreated: EventEmitter<BtTourExpenseResponse> = new EventEmitter<BtTourExpenseResponse>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleTourExpense: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel', { static: false })
  createContactModel: ModalDirective;
  @ViewChild('timedisplay', { static: false })
  timedisplay: ModalDirective;
  @ViewChild('borloddtailModel',{static:false})
  borloddtailModel:ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeTourExpense: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  // popupNgClass: string = 'alert alert-danger center center-block';
  tourExpenseRequest: BtTourExpenseRequest = new BtTourExpenseRequest();
  tourExpenseResponse: BtTourExpenseResponse = new BtTourExpenseResponse();
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


    // console.log(id);
    // if(id!=8){
    //   this.tourExpenseRequest.fare='';

    // }

  
 
    this.tourExpenseService.getOwnConveyance(id).subscribe(data => {
      this.ownConveyance = data;
    }, err => {
      this.spinner.hide();
    });
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.message === 'Updated Successfully') {
  //     // this.onCancleTourExpense.emit('cancled');
  //   }
  // }

  cancelTourExpense() {
    this.onCancleTourExpense.emit('cancled');
  }

  createTourExpense() {
    const tempArrDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');
    const tempDepDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');
    this.tourExpenseRequest.departureDate = this.datePipe.transform(this.tourExpenseRequest.departureDate, 'yyyy-MM-dd');
    this.tourExpenseRequest.arrivalDate = this.datePipe.transform(this.tourExpenseRequest.arrivalDate, 'yyyy-MM-dd');

      

    if (!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) {
      this.tourExpenseRequest.fare = this.tourExpenseRequest.fare + '';
    }
    if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount)) {
      this.tourExpenseRequest.approvedAmount = this.tourExpenseRequest.approvedAmount + '';
    }
    if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance)) {
      this.tourExpenseRequest.distance = this.tourExpenseRequest.distance + '';
    }
    this.tourExpenseRequest.btExpenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.tourExpenseRequest.departureDate) === null || (this.tourExpenseRequest.departureDate) === '') {
      this.message = 'Please Fill Departure Date';
    } else if ((this.tourExpenseRequest.departureTime) === null || (this.tourExpenseRequest.departureTime) === '') {
      this.message = 'Please Fill Departure Time';
    } else if ((this.tourExpenseRequest.arrivalDate) === null || (this.tourExpenseRequest.arrivalDate) === '') {
      this.message = 'Please Fill Arrival Date';
    } else if ((this.tourExpenseRequest.arrivalDate) < (this.tourExpenseRequest.departureDate)) {
      this.message = 'Arrival Date Cannot be Less Than Departure Date';
    } else if ((this.tourExpenseRequest.arrivalTime) === null || (this.tourExpenseRequest.arrivalTime) === '') {
      this.message = 'Please Fill Arrival Time';
    } else if ((this.tourExpenseRequest.arrivalDate === this.tourExpenseRequest.departureDate) &&
      (this.tourExpenseRequest.arrivalTime < this.tourExpenseRequest.departureTime)) {
      this.message = 'Arrival Cannot Be Before Departure';
    } else if (FieldValidation.isEmpty(this.tourExpenseRequest.fromPlace) || (this.tourExpenseRequest.fromPlace===undefined  || this.tourExpenseRequest.fromPlace==null)) {
      this.message = 'Please Fill From Place';
    } else if (FieldValidation.isEmpty(this.tourExpenseRequest.toPlace) || (this.tourExpenseRequest.toPlace===undefined || this.tourExpenseRequest.toPlace===undefined)) {
      this.message = 'Please Fill To Place';
    } else if (this.tourExpenseRequest.modeOfTravelId === 0) {
      this.message = 'Please Fill Mode Of Travel';
    }else if((this.tourExpenseRequest.modeOfTravelId===8 )&& (this.tourExpenseRequest.ownConveyanceId==0)){
   this.message='Please Fill Own Conveyance'
    } 
     else if ((this.tourExpenseRequest.modeOfTravelId===8) && (!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) &&
      (!FieldValidation.isValidCurrency(this.tourExpenseRequest.fare))  && this.farerequired=="col-md-6 col-sm-6 form-group required" )  {
      this.message = 'Please Fill Valid Fare';
    // }else if(this.tourExpenseRequest.modeOfTravelId!=8) 
    // {
    //   if(this.tourExpenseRequest.fare=="0")
    //   {
    //     this.message = 'Fare Can Not Be Smaller Then 1';
    //   }
    }
    else if ((this.tourExpenseRequest.modeOfTravelId===8)&&(!FieldValidation.isEmpty(this.tourExpenseRequest.fare)) && (this.tourExpenseRequest.fare).length > 8) {
      this.message = 'Fare Exceeds Maximum Limit';
    }else if((this.tourExpenseRequest.modeOfTravelId===8)&&(!FieldValidation.isEmpty(this.tourExpenseRequest.distance)) && (this.tourExpenseRequest.distance===undefined)){
     this.message='Please Fill Distance';
    }
   
     else if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance) &&
      !FieldValidation.isValidCurrency(this.tourExpenseRequest.distance)) {
      this.message = 'Please Fill Valid Distance';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.distance) && (this.tourExpenseRequest.distance).length > 8) {
      this.message = 'Distance Exceeds Maximum Limit';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount) &&
      (!FieldValidation.isValidCurrency(this.tourExpenseRequest.approvedAmount))) {
      this.message = 'Please Fill Valid Approved Amount';
    } else if (!FieldValidation.isEmpty(this.tourExpenseRequest.approvedAmount) &&
      (this.tourExpenseRequest.approvedAmount).length > 8) {
      this.message = 'Approved Amount Exceeds Maximum Limit';
    } else {
      this.flag = true;
      if (this._isEditModeTourExpense === false) {
        this.spinner.show();
        // this.tourExpenseRequest.departureTime = this.datePipe.transform(this.tourExpenseRequest.departureTime, 'h:mm:ss');
        // this.tourExpenseRequest.arrivalTime = this.datePipe.transform(this.tourExpenseRequest.arrivalTime, 'h:mm:ss');
        this.tourExpenseService.postTourExpense(this.tourExpenseRequest).subscribe(data => {
          this.message = data.manual_id+' Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onTourExpensesCreated.emit(data);
          this.spinner.hide();
          // this.onExpensesAdded.emit('str');
          this.tourExpenseRequest = new BtTourExpenseRequest();
          this._global.alert({ type: '', message: this.message });
          this.onCancleTourExpense.emit('cancled');
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
      } else {
        this.spinner.show();
        this.tourExpenseService.putTourExpense(this.tourExpenseRequest).subscribe(data => {
          this.message = data.manual_id+' Updated Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.onTourExpensesCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });
          this.onCancleTourExpense.emit('cancled');
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
    this.message="More Than 5 Digits Can Not Be Accepted";
    this._global.alert({ type: 'alert', message: this.message });
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


