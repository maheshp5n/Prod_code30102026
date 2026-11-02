import { Component, Input, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { CirclesService } from '../../../services/circles.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { Loginservices } from '../../../services/login.services';
import { CirclesRequest, CirclesResponse, ContactForList, CountryDetails, Page, ServiceCenterForList, Zonedetails } from '../../../services/model';
import { UserforlistService } from '../../../services/userforlist.service';
import { FieldValidation } from '../../../services/validation.services';
import { ZoneService } from '../../../services/zone.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';

@Component({
  selector: 'app-bat-create-circle',
  templateUrl: './bat-create-circle.component.html',
  styleUrls: ['./bat-create-circle.component.css']
})
export class BatCreateCircleComponent implements OnInit {
  constructor(
    private zoneService: ZoneService,
    private _countryDetailsService: CountryDetailsServices,
    private loginService: Loginservices,
    private _userforlistService: UserforlistService,
    private spinner: NgxSpinnerService,
    private _btlegalentityservice:BtlegalentityService,  
    private _circlesService: CirclesService,
  ) { }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editCircleData(CirclesRequest: CirclesRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
    this.newcircles = CirclesRequest;
    this.newcircles.legalEntity=CirclesRequest.legalEntity;
   }

  get editCircleData(): CirclesRequest {
    return this.newcircles;
  }
  @ViewChild('popupSuccessModel')
  popupSuccessModel: ModalDirective;

  newcircles: CirclesRequest = new CirclesRequest();
  circlesResponse: CirclesResponse = new CirclesResponse();
  flag: boolean;
  message: string;
  userforlist: Array<ContactForList> = [];
  loginData;
  _isEditMode: boolean;
  _countryDetails: Array<CountryDetails> = [];
  _zoneDetails: Page<Zonedetails>;
  _zoneDetailsForList: Array<Zonedetails>;
  popupNgClass: string = 'alert alert-danger center center-block';

  @Output()
  onCircleCreated: EventEmitter<CirclesResponse> = new EventEmitter<CirclesResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  tempId;

  ngOnInit() {
    this.get();
    this.getAllCountryDetails();
    this.getUserForList();
    // this.getZoneDetailsForList();
    this.getLegalEntity();
    this.getBTZOneList();
  }
  leaglEntityList:Array<ServiceCenterForList>=[];
  getLegalEntity(){
    this.spinner.show();
    this._btlegalentityservice.getLegalEntityForList().subscribe(data=>{
      this.spinner.hide();
      this.leaglEntityList=data;

    })
  }
  // domainid:string='';
getBTZOneList(){
  // this._circlesService.getBatteryZoneForList(1).subscribe(data=>{
  //   console.log(data);
  // })
}
  getAllCountryDetails() {
    this._countryDetailsService
      .getAllCountry()
      .subscribe(data => (this._countryDetails = data));
  }

  getZoneDetailsForList() {
    this.zoneService
      .getZoneForList()
      .subscribe(data => (this._zoneDetailsForList = data));
  }

  createCircle() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newcircles.circleName)) {
      this.message = 'Please fill Circle Name';
    } else if ((this.newcircles.circleName).length > 200) {
      this.message = 'Circle Name exceeds 200 characters';
    } else if (FieldValidation.isEmpty(this.newcircles.zoneName)) {
      this.message = 'Please fill Zone Name';
    } else if (FieldValidation.isEmpty(this.newcircles.countryCode)) {
      this.message = 'Please fill Country';
    }
    else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this._circlesService.postCircles(this.newcircles).subscribe(
          data => {
            this.tempId = data.circleName;
            this.message = data.circleName+' Created Successfully';
            this.popupNgClass = 'alert alert-success center center-block';
            this.onCircleCreated.emit(data);
              this.spinner.hide();
            this.popupSuccessModel.show();
          },          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this.popupSuccessModel.show();
          }        );
      } else {
        this.spinner.show();
        this._circlesService
          .putCircles(this.newcircles.circleName, this.newcircles)
          .subscribe(
            data => {
              this.message = data.circleName+' Updated Successfully';
              this.popupNgClass = 'alert alert-success center center-block';
              this.onCircleCreated.emit(data);
              this.spinner.hide();
              this.popupSuccessModel.show();
            },            err => {
              this.spinner.hide();
              this.message = err.error.message;
              this.popupSuccessModel.show();
            }          );
      }
    }

    if (this.flag !== true) {
      this.popupSuccessModel.show();
    }
  }

  cancelData() {
    this.newcircles = new CirclesRequest();
    this.onCancle.emit('cancled');
  }

  getUserForList() {
    this._circlesService.getContactListForCircle().subscribe(data => {
      this.userforlist = data;
    });
  }

  get() {
    this.loginData = this.loginService.getLoggedInUser();
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}
