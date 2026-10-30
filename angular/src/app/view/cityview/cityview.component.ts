import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { cityDetailsResponse, cityDetailsReq, ActionHistoryObjectAndId } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { cityService } from '../../services/cityService.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-cityview',
  templateUrl: './cityview.component.html',
  styleUrls: ['./cityview.component.css']
})
export class CityviewComponent implements OnInit {
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  req: cityDetailsReq = new cityDetailsReq()
  res: cityDetailsResponse = new cityDetailsResponse()
  cityres: cityDetailsResponse = new cityDetailsResponse()
  cityList: Array<cityDetailsResponse> = [];
  @ViewChild('createCityyModel',{static:false}) createCityyModel: ModalDirective
  @ViewChild('deleteLegalENtityModel',{static:false}) deleteLegalENtityModel: ModalDirective
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  private cityName: string
  sapId: string = '';
  message: string;
  tabledata: any
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create City';
  EDIT_ACCOUNT_HEADING: string = 'Edit City'
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }

  _cFlag:string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private ligalentitservice: cityService, private _activatedRoute: ActivatedRoute, private loginservice: Loginservices) {

    this._activatedRoute.params.subscribe(pram => {
      this.cityName = pram['cityname'];
      this.actionHistoryObjectAndId.objectId = this.cityName;
      this.actionHistoryObjectAndId.objectName = 'CityDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  loginData;
  ngOnInit() {
    this.getLoggedInUser()
    this.showLcityResponce()
    this.showCategory();
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }

  editLegalENtityModelshow(res: cityDetailsResponse) {
    this.res = res
    this.showCity();
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createCityyModel.show();
  }
 showCity() {
    this.spinner.show();
    this.ligalentitservice.getCityByCityname(this.cityName).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.res = data

    });

  }

  showLcityResponce() {
    this.spinner.show();
    this.ligalentitservice.getCityByCityname(this.cityName).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.cityres = data

    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
    });

  }

  onCityCreated($event: cityDetailsResponse) {
    this.cityres = $event;
    this.showLcityResponce();
  }
  showCategory() {
    this.spinner.show();
    this.ligalentitservice.getcityCatg().subscribe(data => {
      this.tabledata = data
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
     
    })


  }
  reset() {
    this.req = new cityDetailsReq();
  }
  cancelData() {
    this.isEditMode = false;
    this.createCityyModel.hide();
    this.popupSuccessModel.hide();
  }


}
