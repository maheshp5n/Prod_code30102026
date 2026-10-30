import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { cityService } from '../../../services/cityService.service';
import { CityForListReponse, CityCatagory, cityDetailsReq, BoardingLoadingDetailsResponse } from '../../../services/model';
import { DatePipe } from '@angular/common';
import { FieldValidation } from '../../../services/validation.services';
import { BoardingExpenseRequest, BoardingExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';

import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective, TypeaheadMatch } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { BoardingExpenseService } from '../../../services/boardingExpense.service';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';

@Component({
  selector: 'app-create-new-boarding',
  templateUrl: './create-new-boarding.component.html',
  styleUrls: ['./create-new-boarding.component.css']
})

export class CreateNewBoardingComponent implements OnInit {
  timeList:Array<string>=[];
  response=new BoardingLoadingDetailsResponse
  // Tour starts
  @Input()
  set isEditModeBoarding(isEditModeBoarding: boolean) {
    this._isEditModeBoarding = isEditModeBoarding;
  }

  get isEditModeBoarding(): boolean {
    return this._isEditModeBoarding;
  }
  @Input()
set BodamntToDisbursed(bodamountToDisbursed:number){
  this.boardingRequest.amountToDisbursed=bodamountToDisbursed;
}
get BodamntToDisbursed():number{
  return this.boardingRequest.amountToDisbursed;
}

  @Input()
  set editBoardingData(
    boardingRequest: BoardingExpenseRequest
  ) {
    if (this._isEditModeBoarding === false) {
      this.boardingRequest = new BoardingExpenseRequest();
      this.boardingRequest.expenseManagementId = boardingRequest.expenseManagementId;
    }
    this.boardingRequest = boardingRequest;
  }

  get editBoardingData(): BoardingExpenseRequest {
    return this.boardingRequest;
  }
  @Input()
set BordingRes(res: BoardingLoadingDetailsResponse) {
  this.response = res;
}

get BordingRes(): BoardingLoadingDetailsResponse {
  return this.response;
}

@Input()
set BordingName(grade: string) {
  this.boardingName = grade;
  console.log(this.boardingName);
}

get BordingName(): string {
  return this.boardingName;
}


  constructor(private datePipe: DatePipe,private _serviceAppointment: ServiceAppointmentService,
    private spinner: NgxSpinnerService,
    private boardingService: BoardingExpenseService,private boadingservice:BoardingLoadingService,
    private _cityService: cityService, private _activatedRoute: ActivatedRoute
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
  onBoardingCreated: EventEmitter<BoardingExpenseRequest> = new EventEmitter<BoardingExpenseRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleBoarding: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popupSuccessModel', { static: false })
  popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel', { static: false })
  createContactModel: ModalDirective;
  @ViewChild('timedisplay', { static: false })
  timedisplay: ModalDirective;
  @ViewChild('createCityModel', { static: false })
  createCityModel: ModalDirective;
  @ViewChild('borloddtailModel',{static:false})
  borloddtailModel:ModalDirective;
  public _isEditMode: boolean = false;
  public _isEditModeBoarding: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  boardingRequest: BoardingExpenseRequest = new BoardingExpenseRequest();
  boardingResponse: BoardingExpenseResponse = new BoardingExpenseResponse();
  modeOfTravel: Array<ModeOfTravel> = [];
  ownConveyance: Array<OwnConveyance> = [];
  CREATE_CITY_HEADING = 'New City';
  timepickerVisible = false;
  mytime: Date;
  noMatchCity: string;
  cityDetailsReq: cityDetailsReq = new cityDetailsReq();
  cityList: Array<CityForListReponse> = [];
  cityCategory: Array<CityCatagory> = [];
  _isCreateCityBox: boolean = false;
  today = new Date();
  boardingName:string;
  dateCustomClasses: DatepickerDateCustomClasses[];
  expenseManagementId: number;
  getBoardingById() {
    this.spinner.show();
    // this.boardingService.getBoardingById(this.expensesmanagement.expenseManagementId).subscribe((data) => {
    //   this.boardingResponse = data;
    //   this.spinner.hide();
    // });
  }

  ngOnInit() {
    this.getCityList();
    this.getTimeList();
  }
// by shagufta
getTimeList() {
  this.spinner.show();
  this._serviceAppointment.getTimeList().subscribe(data => {
    this.timeList = data;
    this.spinner.hide();
  }, err => {
    this.spinner.hide();
  });
}
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.message === 'Updated Successfully' && this._isCreateCityBox !== true) {
      // this.onCancleBoarding.emit('cancled');
    }
    this._isCreateCityBox = false;
  }

  cancelBoarding() {
    this.onCancleBoarding.emit('cancled');
  }

  createBoarding() {
    this.boardingRequest.expenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.boardingRequest.fromDate) === null || (this.boardingRequest.fromDate) === '') {
      this.message = 'Please fill From Date';
    } 
    else if ((this.boardingRequest.fromTime) === null || (this.boardingRequest.fromTime) === '') {
      this.message = 'Please fill From Time';
    } else if ((this.boardingRequest.toDate) === null || (this.boardingRequest.toDate) === '') {
      this.message = 'Please fill To Date';
    } 
    else if ((this.boardingRequest.toTime) === null || (this.boardingRequest.toTime) === '') {
      this.message = 'Please fill To Time';
    }else if ((this.boardingRequest.fromDate) > (this.boardingRequest.toDate)) {
      this.message = 'From Date cannot be more than To Date';
    } else if (FieldValidation.isEmpty(this.boardingRequest.cityName)) {
      this.message = 'Please fill City';
    } else if ((this.boardingRequest.cityName).length > 200) {
      this.message = 'City exceeds 200 characters';
    } 
    // else if (this.boardingRequest.noOfDayes === 0 || this.boardingRequest.noOfDayes === null) {
    //   this.message = 'Please fill Number of days';
    // } 
     else {
      this.flag = true;
      if (this._isEditModeBoarding === false) {
        this.spinner.show();
        this.boardingService.postBoardingExpense(this.boardingRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onBoardingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.boardingRequest = new BoardingExpenseRequest();
        },
          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          }
        );
      } else {
        this.spinner.show();
        this.boardingService.putBoardingExpense(this.boardingRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onBoardingCreated.emit(data);
          this.onExpensesAdded.emit('str');
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
        );
      }
    }
    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }

  addNewCity() {
    this.spinner.show();
    // this.boardingRequest = new BoardingExpenseRequest();
    this._isEditModeBoarding = false;
    this.heading = this.CREATE_CITY_HEADING;
    this.createCityModel.show();
    this.getCityCategory();
    this.spinner.hide();
  }

  // cancelData2() {
  //   this.boardingRequest = new BoardingExpenseRequest();
  //   this._isEditModeBoarding = false;
  //   this.heading = this.CREATE_CITY_HEADING;
  // }

  getCityList() {
    this.boardingService.getCityList().subscribe(data => {
      this.cityList = data;
    });
  }

  getCityCategory() {
    this._cityService.getcityCatg().subscribe(data => {
      this.cityCategory = data;
    });
  }

  cancelCity() {
    this.cityDetailsReq = new cityDetailsReq();
    this.createCityModel.hide();
  }

  postCity() {
    this._isCreateCityBox = true;
    this.spinner.show();
    this._cityService
      .saveCity(this.cityDetailsReq)
      .subscribe(
        data => {
          this.message = 'Submitted Successfully !';
          const temp = new cityDetailsReq();
          temp.cityName = data.cityName;
          this.cityList.push(this.cityDetailsReq);
          this.popupSuccessModel.show();
          this.createCityModel.hide();
          this.boardingRequest.cityName = this.cityDetailsReq.cityName;
          this.cityDetailsReq = new cityDetailsReq();
          this.spinner.hide();
        },
        err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        }
      );
  }
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

}


