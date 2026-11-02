import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { cityService } from '../../../services/cityService.service';
import { BoardingLoadingDetailsResponse, CityCatagory, cityDetailsReq, CityForListReponse, LodgingExpenseRequest, LodgingExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtboardingExpenseService } from '../../bat_services/btboarding-expense.service';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlodgingExpenseService } from '../../bat_services/btlodging-expense.service';
import { BtLodgingExpenseRequest, BtLodgingExpenseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-lodging',
  templateUrl: './bt-create-lodging.component.html',
  styleUrls: ['./bt-create-lodging.component.css']
})
export class BtCreateLodgingComponent implements OnInit {

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
  set editLodgingData(lodgingRequest: BtLodgingExpenseRequest) {
    if (this._isEditModeLodging === false) {
      this.lodgingRequest = new BtLodgingExpenseRequest();
      this.lodgingRequest.btExpenseManagementId = lodgingRequest.btExpenseManagementId;
    }
   
    this.lodgingRequest = lodgingRequest;
    this.lodgingRequest.from = lodgingRequest.fromDate;
    this.lodgingRequest.to= lodgingRequest.toDate;
  }

  get editLodgingData(): BtLodgingExpenseRequest {
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

  constructor(private datepipe: DatePipe,private _serviceAppointment: BtServiceappointmentService,
    private spinner: NgxSpinnerService,
    private lodgingService: BtlodgingExpenseService,private boadingservice:BtboardingLoadingExpenseService,
    private _cityService: cityService, private _activatedRoute: ActivatedRoute,private _global: GlobalserviceService
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
  onLodgingCreated: EventEmitter<BtLodgingExpenseResponse> = new EventEmitter<BtLodgingExpenseResponse>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleLodging: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends

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
  public _isEditModeLodging: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  // popupNgClass: string = 'alert alert-danger center center-block';
  lodgingRequest: BtLodgingExpenseRequest = new BtLodgingExpenseRequest();
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

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.message === 'Updated Successfully') {
  //     // this.onCancleLodging.emit('cancled');
  //   }
  // }

  cancelLodging() {
    this.onCancleLodging.emit('cancled');
  }
  
  selfArrangements($event){
  this.selfArrngChecked=$event.target.checked;
  this.lodgingRequest.isComapnyPaid=false;
  this.lodgingRequest.isSelfArrangement=$event.target.checked;
    

  if($event.target.checked!==true){
   
    (<HTMLInputElement>document.getElementById('dis1')).disabled=false;
    (<HTMLInputElement>document.getElementById('dis2')).disabled=false;
    (<HTMLInputElement>document.getElementById('dis3')).disabled=false;
    
    this.classRequiredHotel = 'col-md-6 col-sm-6 form-group required';
    this.classRequiredBillNo='col-md-6 col-sm-6 form-group required';
    this.classRequiredAmount='col-md-6 col-sm-6 form-group required';
  }else{
    this.lodgingRequest.hotelName='';
    this.lodgingRequest.billNo='';
    // this.lodgingRequest.amount='';
    (<HTMLInputElement>document.getElementById('dis1')).disabled=true;
    (<HTMLInputElement>document.getElementById('dis2')).disabled=true;
    (<HTMLInputElement>document.getElementById('dis3')).disabled=true;
    this.classRequiredHotel = 'col-md-6 col-sm-6 form-group';
    this.classRequiredBillNo='col-md-6 col-sm-6 form-group ';
    this.classRequiredAmount='col-md-6 col-sm-6 form-group';
  }
  }
  CompanyPaid($event){
    this.lodgingRequest.isComapnyPaid=$event.target.checked;
    this.lodgingRequest.isSelfArrangement=false;
    if(this.lodgingRequest.isComapnyPaid!=true){
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

    this.lodgingRequest.to = this.datepipe.transform(this.lodgingRequest.to, 'yyyy-MM-dd');
    this.lodgingRequest.from = this.datepipe.transform(this.lodgingRequest.from, 'yyyy-MM-dd');
    this.lodgingRequest.toDate = this.datepipe.transform(this.lodgingRequest.toDate, 'yyyy-MM-dd');
    this.lodgingRequest.fromDate = this.datepipe.transform(this.lodgingRequest.fromDate, 'yyyy-MM-dd');
    if (!FieldValidation.isEmpty(this.lodgingRequest.amount)) {
      this.lodgingRequest.amount = this.lodgingRequest.amount + '';
    }
    if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount)) {
      this.lodgingRequest.approvedAmount = this.lodgingRequest.approvedAmount + '';
    }
    this.lodgingRequest.btExpenseManagementId = this.expenseManagementId;
    this.flag = false;
    if ((this.lodgingRequest.from) === null || (this.lodgingRequest.from) === '') {
      this.message = 'Please Fill From Date';
    }
    else if ((this.lodgingRequest.fromTime) === null || (this.lodgingRequest.fromTime) === undefined) {
      this.message = 'Please Fill From Time ';
    }  else if ((this.lodgingRequest.to) === null || (this.lodgingRequest.to) === '') {
      this.message = 'Please Fill To Date';
    } 
    else if ((this.lodgingRequest.toTime) === null || (this.lodgingRequest.toTime) === undefined) {
      this.message = 'Please Fill To Time';
    } else if ((this.lodgingRequest.from) > (this.lodgingRequest.to)) {
      this.message = 'From Date cannot be More Than To Date';
    } else if ((this.lodgingRequest.cityName) === null || (this.lodgingRequest.cityName) === '') {
      this.message = 'Please Fill City';
    } else if((this.classRequiredHotel==='col-md-6 col-sm-6 form-group required')&& (FieldValidation.isEmpty(this.lodgingRequest.hotelName))){
 this.message='Please Fill Hotel Name'
    }
    else if((this.classRequiredBillNo==='col-md-6 col-sm-6 form-group required')&& (FieldValidation.isEmpty(this.lodgingRequest.billNo))){
      this.message='Please Fill Bill No'
         }
         else if ((this.classRequiredAmount==='col-md-6 col-sm-6 form-group required')&& (this.lodgingRequest.amount===undefined)) {
      this.message = 'Please Fill Amount';
    }else if(!FieldValidation.isEmpty(this.lodgingRequest.amount) && (!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.lodgingRequest.amount))){
       this.message='Please Fill Valid Amount '
    }
     else if (!FieldValidation.isEmpty(this.lodgingRequest.amount) &&
      ((this.lodgingRequest.amount).length > 8)) {
      this.message = 'Amount exceeds limit';
    }
     else if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount) &&
    (!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.lodgingRequest.approvedAmount))) {
      this.message = 'Please Fill Valid Approved Amount';
    } else if (!FieldValidation.isEmpty(this.lodgingRequest.approvedAmount) &&
      ((this.lodgingRequest.approvedAmount).length > 8)) {
      this.message = 'Approved Amount Exceeds Limit';
    } else {  
      this.flag = true;
      if (this._isEditModeLodging === false) {
        this.spinner.show();
        this.lodgingService.postLodgingExpense(this.lodgingRequest).subscribe(data => {
          this.message = data.manualId+' Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onLodgingCreated.emit(data);
          this.spinner.hide();
          this.onLodgingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this.lodgingRequest = new BtLodgingExpenseRequest();
          this._global.alert({ type: '', message: this.message });
          this.onCancleLodging.emit('cancled');

        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
      } else {
        this.spinner.show();
        this.lodgingService.putLodgingExpense(this.lodgingRequest).subscribe(data => {
          this.message = data.manualId+' Updated Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.onLodgingCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });
          this.onCancleLodging.emit('cancled');

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

  addNewCity() {
    this.spinner.show();
    this.lodgingRequest = new BtLodgingExpenseRequest();
    this._isEditModeLodging = false;
    this.heading = this.CREATE_CITY_HEADING;
    this.createCityModel.show();
    this.getCityCategory();
    this.spinner.hide();
  }

  cancelData2() {
    this.lodgingRequest = new BtLodgingExpenseRequest();
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
          this.createCityModel.hide();
          this.lodgingRequest.cityName = this.cityDetailsReq.cityName;
          this.cityDetailsReq = new cityDetailsReq();
          this._global.alert({ type: '', message: this.message });

          this.spinner.hide();
        }, err => {
          this.spinner.hide();
          this.message = err.error.message;
          this._global.alert({ type: 'alert', message: this.message });
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
      this.message="More Than 5 Digits Can Not Be Accepted";
      this._global.alert({ type: 'alert', message: this.message });
     return false;
   }
   
  }
}
