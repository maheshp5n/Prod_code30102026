import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { Loginservices } from '../../../services/login.services';
import { CirclesRequest, CirclesResponse, ContactForList, CountryDetails, LegaLEntityType, Page, ServiceCenterForList, Zonedetails } from '../../../services/model';
import { UserforlistService } from '../../../services/userforlist.service';
import { FieldValidation } from '../../../services/validation.services';
import { ZoneService } from '../../../services/zone.service';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BTCaseResponse, BtCirclesRequest, BtCirclesResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-circle',
  templateUrl: './bt-create-circle.component.html',
  styleUrls: ['./bt-create-circle.component.css']
})
export class BtCreateCircleComponent implements OnInit {

  constructor(
    private zoneService: ZoneService,
    private _countryDetailsService: CountryDetailsServices,
    private loginService: Loginservices,
    private spinner: NgxSpinnerService,
    private _circlesService: BtcircleService,
    private _btLegalEntityService : BtlegalentityService,
    private _global: GlobalserviceService
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
  set editCircleData(CirclesRequest: BtCirclesRequest) {
    // this.popupNgClass = 'alert alert-danger center center-block';
    this.newcircles = CirclesRequest;
    this.newcircles.legalEntity=CirclesRequest.legalEntity;
    console.log(this.newcircles);
    this.newcircles.countryCode='In'

   }

  get editCircleData(): BtCirclesRequest {
    console.log(this.newcircles)
    return this.newcircles;
  }
  // @ViewChild('popupSuccessModel', { static: false })
  // popupSuccessModel: ModalDirective;

  newcircles: BtCirclesRequest = new BtCirclesRequest();
  circlesResponse: BtCirclesResponse = new BtCirclesResponse();
  flag: boolean;
  message: string;
  userforlist: Array<ContactForList> = [];
  loginData;
  _isEditMode: boolean;
  _countryDetails: Array<CountryDetails> = [];
  _zoneDetails: Page<Zonedetails>;
  _zoneDetailsForList: Array<Zonedetails>;
  leaglEntityList: Array<ServiceCenterForList> = [];
  res: BTCaseResponse = new BTCaseResponse(); ///

  // popupNgClass: string = 'alert alert-danger center center-block';

  @Output()
  onCircleCreated: EventEmitter<BtCirclesResponse> = new EventEmitter<
  BtCirclesResponse
  >();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  tempId;

  ngOnInit() {
    this.get();
    this.getAllCountryDetails();
    this.getUserForList();
    // this.getZoneDetailsForList();
    this.getLegalENtityForList();
    this.getBTZOneList();
  }
  getLegalENtityForList() {
    this._btLegalEntityService.getLegalEntityForList().subscribe(data => {
      this.leaglEntityList = data;

    })
  }
  getBTZOneList(){
    this._circlesService.getBatteryZoneForList(1).subscribe(data=>{
      console.log(data);
      const temp=[];
      for(let i=0;i<data.length;i++)
      {
      let lis=new Zonedetails;
      lis.zoneName=data[i]
      temp.push(lis);
      this._zoneDetailsForList=temp;
      }
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

  getServiceResForList(serviceCenterId: number) {
    if (serviceCenterId != null) {
      // this.spinner.show();
      // this.siteservice.getServiceResourcesList(serviceCenterId).subscribe(data => {
      //   this.spinner.hide();
      //   this.serviceResourcesList = data;
      //   data.forEach(e=>{
      //     // if(this.res.serviceResourceId==null || this.res.serviceResourceId==undefined){
      //       // this.res.serviceResourceId=e.serviceResourceId;
      //       // this.res.serviceResourceName=e.name;
          
         
      //   })
      // });
    }
  }
  createCircle() {
    this.flag = false;
    if (FieldValidation.isEmpty(this.newcircles.circleName)) {
      this.message = 'Please Fill Circle Name';
    } else if ((this.newcircles.circleName).length > 200) {
      this.message = 'Circle Name Exceeds 200 Characters';
    } else if (FieldValidation.isEmpty(this.newcircles.zoneName)) {
      this.message = 'Please Fill Zone Name';
    } else if (FieldValidation.isEmpty(this.newcircles.countryCode)) {
      this.message = 'Please Fill Country';
    } 
    else if (this.newcircles.legalEntity=='' || this.newcircles.legalEntity==null) {
      this.message = 'Please Fill Legal Entity';
    } 

    // else if (
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
            this.message = data.circleName+' Created Successfully';
            // this.popupNgClass = 'alert alert-success center center-block';
            this.onCircleCreated.emit(data);
              this.spinner.hide();
              this._global.alert({ type: '', message: this.message });
              this.onCancle.emit('cancled');
            },          err => {
            this.spinner.hide();
            this.message = err.error.message;
            this._global.alert({ type: 'alert', message: this.message });
          }        );
      } else {
        this.spinner.show();
        this._circlesService
          .putCircles(this.newcircles.circleName, this.newcircles)
          .subscribe(
            data => {
              this.message = data.circleName+' Updated Successfully';
             
              // this.popupNgClass = 'alert alert-success center center-block';
              this.onCircleCreated.emit(data);
              this.spinner.hide();
              this._global.alert({ type: '', message: this.message });
              this.onCancle.emit('cancled');
            },            err => {
              this.spinner.hide();
              this.message = err.error.message;
              this._global.alert({ type: 'alert', message: this.message });
            }          );
      }
    }

    if (this.flag !== true) {
      this._global.alert({ type: 'alert', message: this.message });
    }
  }

  cancelData() {
    this.newcircles = new BtCirclesRequest();
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

  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onCancle.emit('cancled');
  //   }
  // }

}
