import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BoardingLoadingDetailsResponse, CityCatagory, cityDetailsReq, CityForListReponse, LocalConveyanceExpenseRequest, LocalConveyanceExpenseResponse, ModeOfTravel, OwnConveyance } from '../../../services/model';
import { FieldValidation } from '../../../services/validation.services';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlocalConveyanceExpenseService } from '../../bat_services/btlocal-conveyance-expense.service';
import { BtlodgingExpenseService } from '../../bat_services/btlodging-expense.service';
import { BttourExpenseService } from '../../bat_services/bttour-expense.service';
import { BtLocalConveyanceExpenseRequest, BtLocalConveyanceExpenseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-local-conveyance-expense',
  templateUrl: './bt-create-local-conveyance-expense.component.html',
  styleUrls: ['./bt-create-local-conveyance-expense.component.css']
})
export class BtCreateLocalConveyanceExpenseComponent implements OnInit {

  // Tour starts
  @Input()
  set isEditModeLocalConveyance(isEditModeLocalConveyance: boolean) {
    this._isEditModeLocalConveyance = isEditModeLocalConveyance;
  }

  get isEditModeLocalConveyance(): boolean {
    return this._isEditModeLocalConveyance;
  }

  @Input()
  set editLocalConveyanceData(localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest) {
    this.spinner.show();
    if (this._isEditModeLocalConveyance === false) {
      this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
      this.localConveyanceExpenseRequest.btExpenseManagementId = localConveyanceExpenseRequest.btExpenseManagementId;
      this.spinner.hide();
    }
    this.localConveyanceExpenseRequest = localConveyanceExpenseRequest;
    this.localConveyanceExpenseRequest.date = this.datepipe.transform(localConveyanceExpenseRequest.date, 'yyyy-MM-dd');

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

  get editLocalConveyanceData(): BtLocalConveyanceExpenseRequest {
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

  constructor(
    private spinner: NgxSpinnerService,private boadingservice:BtboardingLoadingExpenseService,
    private localConveyanceExpenseService: BtlocalConveyanceExpenseService,
    private tourExpenseService: BttourExpenseService, private datepipe: DatePipe, private _activatedRoute: ActivatedRoute, private _global: GlobalserviceService) {
    this.dateCustomClasses = [
      { date: this.today, classes: ['bg-secondary'] }
    ];
    this._activatedRoute.params.subscribe(pram => {
      this.expenseManagementId = pram['id'];
    });

  }

  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onLocalConveyanceCreated: EventEmitter<BtLocalConveyanceExpenseResponse> = new EventEmitter<BtLocalConveyanceExpenseResponse>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onCancleLocalConveyance: EventEmitter<string> = new EventEmitter<string>();
  // tslint:disable-next-line: no-output-on-prefix
  @Output()
  onExpensesAdded: EventEmitter<string> = new EventEmitter<string>();

  // Tour ends
  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;
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
  // popupNgClass: string = 'alert alert-danger center center-block';
  localConveyanceExpenseRequest: BtLocalConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
  localConveyanceExpenseResponse: BtLocalConveyanceExpenseResponse = new BtLocalConveyanceExpenseResponse();
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

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.message === 'Updated Successfully' && this._isCreateCityBox !== true) {
  //     // this.onCancleLocalConveyance.emit('cancled');
  //   }
  //   this._isCreateCityBox = false;
  // }

  cancelLocalConveyance() {
    this.onCancleLocalConveyance.emit('cancled');
  }

  // else if (FieldValidation.isEmpty(this.localConveyanceExpenseRequest.distance)) {
  //   this.message = 'Please Fill Distance';
  // } else if (!FieldValidation.isValidCurrencyMandatory(this.localConveyanceExpenseRequest.distance)) {
  //   this.message = 'Please Fill valid distance';
  // } else if ((this.localConveyanceExpenseRequest.distance).length > 4) {
  //   this.message = 'Distance exceeds maximum limit';
  // } 

  createLocalConveyance() {
    this.localConveyanceExpenseRequest.btExpenseManagementId = this.expenseManagementId;
    this.flag = false;
    if (this.localConveyanceExpenseRequest.date === null || this.localConveyanceExpenseRequest.date === '') {
      this.message = 'Please Select Date';
    } else if (this.localConveyanceExpenseRequest.modeOfTravelId === 0 || this.localConveyanceExpenseRequest.modeOfTravelId === null) {
      this.message = 'Please Fill Mode Of Travel';
    }else if((this.localConveyanceExpenseRequest.modeOfTravelId === 8) && (this.localConveyanceExpenseRequest.ownConveyanceId===0)){
      this.message='Please Select Own Conveyance';
    }
     else if ((this.localConveyanceExpenseRequest.fromPlace) === null || (this.localConveyanceExpenseRequest.fromPlace) === '') {
      this.message = 'Please Fill From Place';
    } else if ((this.localConveyanceExpenseRequest.fromPlace).length > 256) {
      this.message = 'Place Exceeds 256 Characters';
    } else if ((this.localConveyanceExpenseRequest.toPlace) === null || (this.localConveyanceExpenseRequest.toPlace) === '') {
      this.message = 'Please Fill To Place';
    } else if ((this.localConveyanceExpenseRequest.toPlace).length > 256) {
      this.message = 'Place Exceeds 256 Characters';
    } else if (!FieldValidation.isEmpty(this.localConveyanceExpenseRequest.amount) &&
      !FieldValidation.isValidCurrencyMandatory(this.localConveyanceExpenseRequest.amount)) {
      this.message = 'Please Fill Valid Amount';
    } else if (this.localConveyanceExpenseRequest.amount=='0') {
      this.message = 'Amount is 0';
    } else if ( !FieldValidation.isEmpty(this.localConveyanceExpenseRequest.amount)&& (this.localConveyanceExpenseRequest.amount).length > 8) {
      this.message = 'Amount Exceeds Maximum Limit';
    }else if(this.localConveyanceExpenseRequest.modeOfTravelId === 8 && (this.localConveyanceExpenseRequest.distance==null ||this.localConveyanceExpenseRequest.distance==undefined)){
      this.message='Please Fill Distance';
    }
     else {
       this.localConveyanceExpenseRequest.date=this.datepipe.transform(this.localConveyanceExpenseRequest.date, 'yyyy-MM-dd')
      this.flag = true;
      if (this._isEditModeLocalConveyance === false) {
        this.spinner.show();
        this.localConveyanceExpenseService.postLocalConveyanceExpense(this.localConveyanceExpenseRequest).subscribe(data => {
          this.message = data.manualId+' Created Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.onLocalConveyanceCreated.emit(data);
          this.spinner.hide();
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });

          this.localConveyanceExpenseRequest = new BtLocalConveyanceExpenseRequest();
          this.onCancleLocalConveyance.emit('cancled');
        },
          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          }
        );
      } else {
        this.spinner.show();
        this.localConveyanceExpenseService.putLocalConveyanceExpense(this.localConveyanceExpenseRequest).subscribe(data => {
          this.message = data.manualId+' Updated Successfully';
          // this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.onLocalConveyanceCreated.emit(data);
          this.onExpensesAdded.emit('str');
          this._global.alert({ type: '', message: this.message });
          this.onCancleLocalConveyance.emit('cancled');

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

  getModeOfTravel() {
    this.spinner.show();
    this.tourExpenseService.getModeOfTravel().subscribe(data => {
      this.modeOfTravel = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
 kmrequired:string="col-md-6 col-sm-6 form-group"
  getOwnConveyance() {
    if (this.localConveyanceExpenseRequest.modeOfTravelId === 8) {
      this.localConveyanceExpenseRequest.amount='';
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
        this._global.alert({ type: 'alert', message: this.message });
      });
    }
    else{
      this.kmrequired="col-md-6 col-sm-6 form-group";
      this.localConveyanceExpenseRequest.ownConveyanceId=0;
      this.localConveyanceExpenseRequest.distance='';
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
      this.message="More Than 5 Digits Can Not Be Accepted";
      this._global.alert({ type: 'alert', message: this.message });
     return false;
   }
   
  }
}


