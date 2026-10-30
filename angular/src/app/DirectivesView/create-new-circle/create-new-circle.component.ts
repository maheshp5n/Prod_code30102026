import { ContactForList, ServiceCenterForList } from './../../services/model';
import {
  Component,
  OnInit,
  ViewChild,
  Output,
  EventEmitter,
  Input
} from '@angular/core';
import { ModalDirective, TypeaheadMatch } from 'ngx-bootstrap';
import {
  CirclesRequest,
  CountryDetails,
  UserForList,
  Zonedetails,
  Page,
  CirclesResponse
} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { CirclesService } from '../../services/circles.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ZoneService } from '../../services/zone.service';
import { UserforlistService } from '../../services/userforlist.service';
import { Loginservices } from '../../services/login.services';
import { ligalEntityService } from '../../services/ligalentity.services';

@Component({
  selector: 'app-create-new-circle',
  templateUrl: './create-new-circle.component.html',
  styleUrls: ['./create-new-circle.component.css']
})
export class CreateNewCircleComponent implements OnInit {

  constructor(
    private zoneService: ZoneService,
    private _countryDetailsService: CountryDetailsServices,
    private loginService: Loginservices,
    private _userforlistService: UserforlistService,
    private spinner: NgxSpinnerService,
    private _circlesService: CirclesService,
    private _legalentityservice:ligalEntityService
  ) { }

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  // tslint:disable-next-line: no-shadowed-variable
  set editCircleData(CirclesRequest: CirclesRequest) {
    this.popupNgClass = 'alert alert-danger center center-block';
    this.newcircles = CirclesRequest;
    // this.newcircles.legalEntity = CirclesRequest.legalEntity;
    this.newcircles.legalEntity=CirclesRequest.legalEntity;
    // this.newcircles.powerLegalEntityName=CirclesRequest.powerLegalEntityName;

   }

  get editCircleData(): CirclesRequest {
    return this.newcircles;
  }
  @ViewChild('popupSuccessModel', { static: false })
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
  onCircleCreated: EventEmitter<CirclesResponse> = new EventEmitter<
    CirclesResponse
  >();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  tempId;

  ngOnInit() {
    this.get();
    this.getAllCountryDetails();
    this.getUserForList();
    this.getZoneDetailsForList();
    this.getLegalEntity();
  }
  leaglEntityList:Array<ServiceCenterForList>=[];
  getLegalEntity(){
    this.spinner.show();
    this._legalentityservice.getLegalEntityForList().subscribe(data=>{
      this.spinner.hide();
      this.leaglEntityList=data;

    })
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
    //  else if (
    //   (this.newcircles.circleManagerBattryId === 0 || this.newcircles.circleManagerBattryId === null ||
    //     this.newcircles.circleManagerBattryId === undefined) &&
    //   (this.newcircles.circleManagerPowerId === 0 || this.newcircles.circleManagerPowerId ===  null ||
    //     this.newcircles.circleManagerPowerId === undefined) &&
    //   (this.newcircles.circleManagerEv === 0 || this.newcircles.circleManagerEv ===  null ||
    //   this.newcircles.circleManagerEv === undefined)
    // ) {
    //   this.message = 'Please fill atleast one Circle Manager';
    // } 
    else {
      this.flag = true;
      if (this._isEditMode === false) {
        this.spinner.show();
        this._circlesService.postCircles(this.newcircles).subscribe(
          data => {
            this.tempId = data.circleName;
            this.message = 'Created Successfully';
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
              this.message = 'Updated Successfully';
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
