import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { LodgingExpenseService } from './../../../services/LodgingExpenseService';
import { cityService } from './../../../services/cityService.service';
import { CityForListReponse, CityCatagory, cityDetailsReq, BoardingLoadingDetailsResponse } from './../../../services/model';
import { DatePipe } from '@angular/common';
import { FieldValidation } from '../../../services/validation.services';
import { LodgingExpenseRequest, LodgingExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';
import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';

@Component({
  selector: 'app-create-new-lodging',
  templateUrl: './create-new-lodging.component.html',
  styleUrls: ['./create-new-lodging.component.css']
})

export class CreateNewLodgingComponent implements OnInit {
  classRequiredHotel:string='col-md-6 col-sm-6 form-group required';
  classRequiredBillNo:string='col-md-6 col-sm-6 form-group required';
  classRequiredAmount:string='col-md-6 col-sm-6 form-group required';
  response=new BoardingLoadingDetailsResponse()
  selfArrngChecked:boolean;
  boardingName:string;
  @Input()
  set isEditModeLodging(isEditModeLodging: boolean) {
    this._isEditModeLodging = isEditModeLodging;
  }

  get isEditModeLodging(): boolean {
    return this._isEditModeLodging;
  }

  Datepicker: any;

  @Input()
  set editLodgingData(lodgingRequest: LodgingExpenseRequest) {
    if (this._isEditModeLodging === false) {
      this.lodgingRequest = new LodgingExpenseRequest();
      this.lodgingRequest.expenseManagementId = lodgingRequest.expenseManagementId;
    }
    this.lodgingRequest = lodgingRequest;
  }

  get editLodgingData(): LodgingExpenseRequest {
    return this.lodgingRequest;
  }
// shagufta

@Input()
set BordingName(grade: string) {
  this.boardingName = grade;
}

get BordingName(): string {
  return this.boardingName;
}
@Input()
set TouramntToDisbursed(lodamountToDisbursed:number){
  this.lodgingRequest.amountToDisbursed=lodamountToDisbursed;
}
get TouramntToDisbursed():number{
  return this.lodgingRequest.amountToDisbursed;
}
  dateCustomClasses: DatepickerDateCustomClasses[];
  expenseManagementId: number;

  constructor(private datePipe: DatePipe,private _serviceAppointment: ServiceAppointmentService,
    private spinner: NgxSpinnerService,
    private lodgingService: LodgingExpenseService,private boadingservice:BoardingLoadingService,
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
  onLodgingCreated: EventEmitter<LodgingExpenseRequest> = new EventEmitter<LodgingExpenseRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleLodging: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends

  @ViewChild('popupSuccessModel')
  popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel')
  createContactModel: ModalDirective;
  @ViewChild('timedisplay')
  timedisplay: ModalDirective;
  @ViewChild('createCityModel')
  createCityModel: ModalDirective;
  @ViewChild('borloddtailModel')
  borloddtailModel:ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeLodging: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  lodgingRequest: LodgingExpenseRequest = new LodgingExpenseRequest();
  lodgingResponse: LodgingExpenseResponse = new LodgingExpenseResponse();
  modeOfTravel: Array<ModeOfTravel> = [];
  ownConveyance: Array<OwnConveyance> = [];
  CREATE_CITY_HEADING = 'New City';
  timepickerVisible = false;
  mytime: Date;
  noMatchCity: string;
  cityDetailsReq: cityDetailsReq = new cityDetailsReq();
  // city typeahead closes
  cityList: Array<CityForListReponse> = [];
  cityCategory: Array<CityCatagory> = [];
  today = new Date();

  getLodgingById() {
    this.spinner.show();
    // this.lodgingService.getLodgingById(this.expensesmanagement.expenseManagementId).subscribe((data) => {
    //   this.lodgingResponse = data;
    //   this.spinner.hide();
    // });
  }

  ngOnInit() {
    this.getCityList();
    this.getTimeList();
  }
  // by shagufta
  timeList:Array<string>=[];
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
    if (this.message === 'Updated Successfully') {
      // this.onCancleLodging.emit('cancled');
    }
  }

  cancelLodging() {
    this.onCancleLodging.emit('cancled');
  }
  
  selfArrangements($event){
  this.selfArrngChecked=$event.target.checked;
  this.lodgingRequest.comapnyPaid=false;
  this.lodgingRequest.selfArrangement=$event.target.checked;
    
  if($event.target.checked!==true){
    (<HTMLInputElement>document.getElementById('dis1')).disabled=false;
    (<HTMLInputElement>document.getElementById('dis2')).disabled=false;
    (<HTMLInputElement>document.getElementById('dis3')).disabled=false;
    
    this.classRequiredHotel = 'col-md-6 col-sm-6 form-group required';
    this.classRequiredBillNo='col-md-6 col-sm-6 form-group required';
    this.classRequiredAmount='col-md-6 col-sm-6 form-group required';
  }else{
    (<HTMLInputElement>document.getElementById('dis1')).disabled=true;
    (<HTMLInputElement>document.getElementById('dis2')).disabled=true;
    (<HTMLInputElement>document.getElementById('dis3')).disabled=true;
    this.classRequiredHotel = 'col-md-6 col-sm-6 form-group';
    this.classRequiredBillNo='col-md-6 col-sm-6 form-group ';
    this.classRequiredAmount='col-md-6 col-sm-6 form-group';
  }
  }
  CompanyPaid($event){
    this.lodgingRequest.comapnyPaid=$event.target.checked;
    this.lodgingRequest.selfArrangement=false;
    if(this.lodgingRequest.comapnyPaid!=true){
      this.classRequiredHotel = 'col-md-6 col-sm-6 form-group required';
    this.classRequiredBillNo='col-md-6 col-sm-6 form-group required';
    this.classRequiredAmount='col-md-6 col-sm-6 form-group required';
    }
    else{
      this.classRequiredHotel = 'col-md-6 col-sm-6 form-group';
      this.classRequiredBillNo='col-md-6 col-sm-6 form-group ';
      this.classRequiredAmount='col-md-6 col-sm-6 form-group';
    }

  }

  createLodging() {
    if (!FieldValidation.isEmpty(this.lodgingRequest.amount)) {
      this.lodgingRequest.amount = this.lodgingRequest.amount + '';
    }
    if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount)) {
      this.lodgingRequest.approvedAmount = this.lodgingRequest.approvedAmount + '';
    }
    this.lodgingRequest.expenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.lodgingRequest.fromDate) === null || (this.lodgingRequest.fromDate) === '') {
      this.message = 'Please fill From Date';
    }
    else if ((this.lodgingRequest.fromTime) === null || (this.lodgingRequest.fromTime) === undefined) {
      this.message = 'Please fill From Time ';
    }  else if ((this.lodgingRequest.toDate) === null || (this.lodgingRequest.toDate) === '') {
      this.message = 'Please fill To Date';
    } 
    else if ((this.lodgingRequest.toTime) === null || (this.lodgingRequest.toTime) === undefined) {
      this.message = 'Please fill To Time';
    } else if ((this.lodgingRequest.fromDate) > (this.lodgingRequest.toDate)) {
      this.message = 'From Date cannot be more than To Date';
    } else if ((this.lodgingRequest.cityName) === null || (this.lodgingRequest.cityName) === '') {
      this.message = 'Please fill City';
    } else if((this.classRequiredHotel==='col-md-6 col-sm-6 form-group required')&& (FieldValidation.isEmpty(this.lodgingRequest.hotelName))){
 this.message='Please fill Hotel Name'
    }
    else if((this.classRequiredBillNo==='col-md-6 col-sm-6 form-group required')&& (FieldValidation.isEmpty(this.lodgingRequest.billNo))){
      this.message='Please fill Bill No'
         }
         else if ((this.classRequiredAmount==='col-md-6 col-sm-6 form-group required')&& (this.lodgingRequest.amount===undefined)) {
      this.message = 'Please fill amount';
    }else if(!FieldValidation.isEmpty(this.lodgingRequest.amount) && (!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.lodgingRequest.amount))){
       this.message='Please fill valid amount '
    }
     else if (!FieldValidation.isEmpty(this.lodgingRequest.amount) &&
      ((this.lodgingRequest.amount).length > 8)) {
      this.message = 'Amount exceeds limit';
    }
     else if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount) &&
    (!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.lodgingRequest.approvedAmount))) {
      this.message = 'Please fill valid approved amount';
    } else if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount) &&
      ((this.lodgingRequest.approvedAmount).length > 8)) {
      this.message = 'Approved Amount exceeds limit';
    } else {
      this.flag = true;
      if (this._isEditModeLodging === false) {
        this.spinner.show();
        this.lodgingService.postLodgingExpense(this.lodgingRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onLodgingCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onLodgingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this.lodgingRequest = new LodgingExpenseRequest();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
      } else {
        this.spinner.show();
        this.lodgingService.putLodgingExpense(this.lodgingRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onLodgingCreated.emit(data);
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

  addNewCity() {
    this.spinner.show();
    this.lodgingRequest = new LodgingExpenseRequest();
    this._isEditModeLodging = false;
    this.heading = this.CREATE_CITY_HEADING;
    this.createCityModel.show();
    this.getCityCategory();
    this.spinner.hide();
  }

  cancelData2() {
    this.lodgingRequest = new LodgingExpenseRequest();
    this._isEditModeLodging = false;
    this.heading = this.CREATE_CITY_HEADING;
    // this.createCityModel.hide();
  }

  getCityList() {
    this.lodgingService.getCityList().subscribe(data => {
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
          this.lodgingRequest.cityName = this.cityDetailsReq.cityName;
          this.cityDetailsReq = new cityDetailsReq();
          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this.popupSuccessModel.show();
        });
  }
// shagufta

OpnGrade(bordingName){
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
}


