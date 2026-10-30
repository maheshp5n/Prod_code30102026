import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { FieldValidation } from './../../../services/validation.services';
import { CityForListReponse, CityCatagory, cityDetailsReq, BoardingLoadingDetailsResponse } from '../../../services/model';
import { DatePipe } from '@angular/common';
import { LocalConveyanceExpenseService } from '../../../services/localConveyanceExpense.service';
import { LocalConveyanceExpenseRequest, LocalConveyanceExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';

import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { TourExpenseService } from '../../../services/tourExpense.service';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';

@Component({
  selector: 'app-create-new-localConveyanceExpense',
  templateUrl: './create-new-localConveyanceExpense.component.html',
  styleUrls: ['./create-new-localConveyanceExpense.component.css']
})

export class CreateNewLocalConveyanceExpenseComponent implements OnInit {
  // Tour starts
  @Input()
  set isEditModeLocalConveyance(isEditModeLocalConveyance: boolean) {
    this._isEditModeLocalConveyance = isEditModeLocalConveyance;
  }

  get isEditModeLocalConveyance(): boolean {
    return this._isEditModeLocalConveyance;
  }

  @Input()
  set editLocalConveyanceData(localConveyanceExpenseRequest: LocalConveyanceExpenseRequest) {
    this.spinner.show();
    if (this._isEditModeLocalConveyance === false) {
      this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
      this.localConveyanceExpenseRequest.expenseManagementId = localConveyanceExpenseRequest.expenseManagementId;
      this.spinner.hide();
    }
    this.localConveyanceExpenseRequest = localConveyanceExpenseRequest;
    if (this._isEditModeLocalConveyance === true && this.localConveyanceExpenseRequest.modeOfTravelId !== 0) {
      this.tourExpenseService.getOwnConveyance(this.localConveyanceExpenseRequest.modeOfTravelId).subscribe(data => {
        this.ownConveyance = data;
        this.spinner.hide();
      });
    }
    if (this._isEditModeLocalConveyance) {
      this.spinner.hide();
    }
  }

  get editLocalConveyanceData(): LocalConveyanceExpenseRequest {
    return this.localConveyanceExpenseRequest;
  }
  // shagufta
boardingName:string;
@Input()
set BordingName(grade: string) {
  this.boardingName = grade;
}

get BordingName(): string {
  return this.boardingName;
}
  localamountToDisbursed
  @Input()
set LocalamntToDisbursed(localamountToDisbursed:number){
  this.localConveyanceExpenseRequest.amountToDisbursed=localamountToDisbursed;
}
get BodamntToDisbursed():number{
  return this.localConveyanceExpenseRequest.amountToDisbursed;
}
  dateCustomClasses: DatepickerDateCustomClasses[];
  expenseManagementId: number;

  constructor(private datePipe: DatePipe,
    private spinner: NgxSpinnerService,private boadingservice:BoardingLoadingService,
    private localConveyanceExpenseService: LocalConveyanceExpenseService,
    private tourExpenseService: TourExpenseService, private _activatedRoute: ActivatedRoute) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.params.subscribe(pram => {
      this.expenseManagementId = pram['id'];
    });

  }

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onLocalConveyanceCreated: EventEmitter<LocalConveyanceExpenseRequest> = new EventEmitter<LocalConveyanceExpenseRequest>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleLocalConveyance: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends
  @ViewChild('popupSuccessModel', { static: false })
  popupSuccessModel: ModalDirective;
  @ViewChild('createContactModel', { static: false })
  createContactModel: ModalDirective;
  @ViewChild('timedisplay', { static: false })
  timedisplay: ModalDirective;
  @ViewChild('borloddtailModel',{static:false})
  borloddtailModel:ModalDirective;

  public _isEditMode: boolean = false;
  public _isEditModeLocalConveyance: boolean;
  productFamily;
  flag: boolean;
  message: string;
  heading: string;
  faPlus: string = 'btn btn-primary fa fa-plus';
  popupNgClass: string = 'alert alert-danger center center-block';
  localConveyanceExpenseRequest: LocalConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
  localConveyanceExpenseResponse: LocalConveyanceExpenseResponse = new LocalConveyanceExpenseResponse();
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

  ngOnInit() {
    this.getModeOfTravel();
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.message === 'Updated Successfully' && this._isCreateCityBox !== true) {
      // this.onCancleLocalConveyance.emit('cancled');
    }
    this._isCreateCityBox = false;
  }

  cancelLocalConveyance() {
    this.onCancleLocalConveyance.emit('cancled');
  }

  // else if (FieldValidation.isEmpty(this.localConveyanceExpenseRequest.distance)) {
  //   this.message = 'Please fill Distance';
  // } else if (!FieldValidation.isValidCurrencyMandatory(this.localConveyanceExpenseRequest.distance)) {
  //   this.message = 'Please fill valid distance';
  // } else if ((this.localConveyanceExpenseRequest.distance).length > 4) {
  //   this.message = 'Distance exceeds maximum limit';
  // } 

  createLocalConveyance() {
    this.localConveyanceExpenseRequest.expenseManagementId = this.expenseManagementId;
    this.flag = false;
    if (this.localConveyanceExpenseRequest.date === null || this.localConveyanceExpenseRequest.date === '') {
      this.message = 'Please select date';
    } else if (this.localConveyanceExpenseRequest.modeOfTravelId === 0 || this.localConveyanceExpenseRequest.modeOfTravelId === null) {
      this.message = 'Please fill Mode Of Travel';
    }else if((this.localConveyanceExpenseRequest.modeOfTravelId === 8) && (this.localConveyanceExpenseRequest.ownConveyanceId===0)){
      this.message='Please Select Own Conveyance';
    }
     else if ((this.localConveyanceExpenseRequest.fromPlace) === null || (this.localConveyanceExpenseRequest.fromPlace) === '') {
      this.message = 'Please fill From Place';
    } else if ((this.localConveyanceExpenseRequest.fromPlace).length > 256) {
      this.message = 'Place exceeds 256 characters';
    } else if ((this.localConveyanceExpenseRequest.toPlace) === null || (this.localConveyanceExpenseRequest.toPlace) === '') {
      this.message = 'Please fill To Place';
    } else if ((this.localConveyanceExpenseRequest.toPlace).length > 256) {
      this.message = 'Place exceeds 256 characters';
    } else if (!FieldValidation.isEmpty(this.localConveyanceExpenseRequest.amount) &&
      !FieldValidation.isValidCurrencyMandatory(this.localConveyanceExpenseRequest.amount)) {
      this.message = 'Please fill valid Amount';
    } else if ( !FieldValidation.isEmpty(this.localConveyanceExpenseRequest.amount)&& (this.localConveyanceExpenseRequest.amount).length > 8) {
      this.message = 'Amount exceeds maximum limit';
    }else if(this.localConveyanceExpenseRequest.modeOfTravelId === 8 && (this.localConveyanceExpenseRequest.distance==null ||this.localConveyanceExpenseRequest.distance==undefined)){
      this.message='Please fill distance';
    }
     else {
      this.flag = true;
      if (this._isEditModeLocalConveyance === false) {
        this.spinner.show();
        this.localConveyanceExpenseService.postLocalConveyanceExpense(this.localConveyanceExpenseRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onLocalConveyanceCreated.emit(data);
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onExpensesAdded.emit('str');
          this.localConveyanceExpenseRequest = new LocalConveyanceExpenseRequest();
        },
          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          }
        );
      } else {
        this.spinner.show();
        this.localConveyanceExpenseService.putLocalConveyanceExpense(this.localConveyanceExpenseRequest).subscribe(data => {
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
          this.onLocalConveyanceCreated.emit(data);
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

  getModeOfTravel() {
    this.spinner.show();
    this.tourExpenseService.getModeOfTravel().subscribe(data => {
      this.modeOfTravel = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
 kmrequired:string="col-md-6 col-sm-6 form-group"
  getOwnConveyance() {
    if (this.localConveyanceExpenseRequest.modeOfTravelId === 8) {
      this.spinner.show();
      // (<HTMLInputElement>document.getElementById('ownamount')).disabled=true;
      this.tourExpenseService.getOwnConveyance(this.localConveyanceExpenseRequest.modeOfTravelId).subscribe(data => {
        this.ownConveyance = data;
        this.spinner.hide();
        this.kmrequired="col-md-6 col-sm-6 form-group required";
        (<HTMLInputElement>document.getElementById('2')).disabled=true;
        (<HTMLInputElement>document.getElementById('3')).disabled=false;
     
        
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
    }
    else{
      this.kmrequired="col-md-6 col-sm-6 form-group";
      (<HTMLInputElement>document.getElementById('3')).disabled=true;
       (<HTMLInputElement>document.getElementById('2')).disabled=false;
    }
  }

// shagufta
response=new BoardingLoadingDetailsResponse()
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
}


