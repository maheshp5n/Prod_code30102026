import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { cityService } from '../../../services/cityService.service';
import { BoardingExpenseRequest, BoardingExpenseResponse, BoardingLoadingDetailsResponse, CityCatagory, cityDetailsReq, CityForListReponse, ModeOfTravel, OwnConveyance } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtboardingExpenseService } from '../../bat_services/btboarding-expense.service';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlodgingExpenseService } from '../../bat_services/btlodging-expense.service';
import { BtBoardingExpenseRequest, BtBoardingExpenseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-boarding',
  templateUrl: './bt-create-boarding.component.html',
  styleUrls: ['./bt-create-boarding.component.css']
})
export class BtCreateBoardingComponent implements OnInit {

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
    boardingRequest: BtBoardingExpenseRequest
  ) {
    if (this._isEditModeBoarding === false) {
      this.boardingRequest = new BtBoardingExpenseRequest();
      this.boardingRequest.btExpenseManagementId = boardingRequest.btExpenseManagementId;
    }
    this.boardingRequest = boardingRequest;
    this.boardingRequest.from = this.datepipe.transform(boardingRequest.fromDate, 'yyyy-MM-dd');
    this.boardingRequest.to = this.datepipe.transform(boardingRequest.toDate, 'yyyy-MM-dd');
    this.boardingRequest.expenseManagementManualId = boardingRequest.btExpenseManagementManualId;


  }

  get editBoardingData(): BtBoardingExpenseRequest {
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


  constructor(private _serviceAppointment: BtServiceappointmentService,
    private spinner: NgxSpinnerService,    private datepipe: DatePipe,

    private boardingService: BtboardingExpenseService,private boadingservice:BtboardingLoadingExpenseService,
    private _cityService: cityService, private _activatedRoute: ActivatedRoute, private _global: GlobalserviceService
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
  onBoardingCreated: EventEmitter<BtBoardingExpenseResponse> = new EventEmitter<BtBoardingExpenseResponse>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleBoarding: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel')
  createContactModel: ModalDirective;
  @ViewChild('timedisplay')
  timedisplay: ModalDirective;
  @ViewChild('createCityModel')
  createCityModel: ModalDirective;
  @ViewChild('borloddtailModel')
  borloddtailModel:ModalDirective;
  public _isEditMode: boolean = false;
  public _isEditModeBoarding: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  // popupNgClass: string = 'alert alert-danger center center-block';
  boardingRequest: BtBoardingExpenseRequest = new BtBoardingExpenseRequest();
  boardingResponse: BtBoardingExpenseResponse = new BtBoardingExpenseResponse();
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
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.message === 'Updated Successfully' && this._isCreateCityBox !== true) {
  //     // this.onCancleBoarding.emit('cancled');
  //   }
  //   this._isCreateCityBox = false;
  // }

  cancelBoarding() {
    this.onCancleBoarding.emit('cancled');
  }

  createBoarding() {
    this.boardingRequest.btExpenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.boardingRequest.from) === null || (this.boardingRequest.from) === '') {
      this.message = 'Please Fill From Date';
    } 
    else if ((this.boardingRequest.fromTime) === null || (this.boardingRequest.fromTime) === '') {
      this.message = 'Please Fill From Time';
    } else if ((this.boardingRequest.to) === null || (this.boardingRequest.to) === '') {
      this.message = 'Please Fill To Date';
    } 
    else if ((this.boardingRequest.toTime) === null || (this.boardingRequest.toTime) === '') {
      this.message = 'Please Fill To Time';
    }else if ((this.boardingRequest.from) > (this.boardingRequest.to)) {
      this.message = 'From Date Cannot Be More Than To Date';
    } else if (FieldValidation.isEmpty(this.boardingRequest.cityName)) {
      this.message = 'Please Fill City';
    } else if ((this.boardingRequest.cityName).length > 200) {
      this.message = 'City Exceeds 200 Characters';
    } 
    // else if (this.boardingRequest.noOfDayes === 0 || this.boardingRequest.noOfDayes === null) {
    //   this.message = 'Please Fill Number of days';
    // } 
     else {
       console.log("hhhhhhhhhhhhdfgh")
       this.boardingRequest.to= this.datepipe.transform(this.boardingRequest.to, 'yyyy-MM-dd')
       this.boardingRequest.from= this.datepipe.transform(this.boardingRequest.from, 'yyyy-MM-dd')

      this.flag = true;
      if (this._isEditModeBoarding === false) {
        this.spinner.show();
        this.boardingService.postBoardingExpense(this.boardingRequest).subscribe(data => {
          this.message =data.manualId+' Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onBoardingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this.spinner.hide();
          this._global.alert({ type: '', message: this.message });
          this.boardingRequest = new BtBoardingExpenseRequest();
          this.onCancleBoarding.emit('cancled');
        },
          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          }
        );
      } else {
        this.boardingRequest.toDate= this.datepipe.transform(this.boardingRequest.to, 'yyyy-MM-dd')
        this.boardingRequest.fromDate= this.datepipe.transform(this.boardingRequest.from, 'yyyy-MM-dd')
        this.spinner.show();
        this.boardingService.putBoardingExpense(this.boardingRequest).subscribe(data => {
          this.message =data.manualId+ ' Updated Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.onBoardingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });
          this.onCancleBoarding.emit('cancled');

        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        }
        );
      }
    }
    if (this.flag !== true) {
      this._global.alert({ type: 'alert', message: this.message });
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
          this._global.alert({ type: '', message: this.message });
          this.createCityModel.hide();
          this.boardingRequest.cityName = this.cityDetailsReq.cityName;
          this.cityDetailsReq = new cityDetailsReq();
          this.spinner.hide();
        },
        err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
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
