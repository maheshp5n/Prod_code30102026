import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { Loginservices } from '../../../services/login.services';
import { FieldValidation } from '../../../services/validation.services';
import { BAT_BoardingLoadingService } from '../../bat_services/bt-boadigLoadgingDA.service';
import { Bat_BoardingLoadingDetailsRequest, Bat_BoardingLoadingDetailsResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-create-boading-lodgin-da',
  templateUrl: './bt-create-boading-lodgin-da.component.html',
  styleUrls: ['./bt-create-boading-lodgin-da.component.scss']
})
export class BtCreateBoadingLodginDaComponent implements OnInit {
  res: Bat_BoardingLoadingDetailsResponse = new Bat_BoardingLoadingDetailsResponse();
  req: Bat_BoardingLoadingDetailsRequest = new Bat_BoardingLoadingDetailsRequest();
  loginData: any;
  boadingNameTamp: string;
  boadingIdTamp: number;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editEntityData(res: Bat_BoardingLoadingDetailsResponse) {
    this.res = res;
  }

  get editEntityData(): Bat_BoardingLoadingDetailsResponse {
    return this.res;
  }

  @Output()
  onBoadingCreated: EventEmitter<Bat_BoardingLoadingDetailsRequest> = new EventEmitter<Bat_BoardingLoadingDetailsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  constructor(private loginservice: Loginservices, private bordingservice: BAT_BoardingLoadingService, 
    private spinner: NgxSpinnerService,private _global:GlobalserviceService) { }

  ngOnInit() {
    this.getLoggedInUser();
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  postBodingDetails(){
    if( this.res.boardingName===undefined ||this.res.boardingName===null|| this.res.boardingName===''){
      this.message="Fill the Boarding Name";
    }
   else if((this.res.boardingName).length>200){
    this.message='Boarding Name Exceeds 200 Characters';
     }
   else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.boardingClassACity))){
      this.message = 'Please Fill Only Amount in Lodging Class City A';
    
    }
    else if(this.res.boardingClassACity!==undefined && this.res.boardingClassACity.length>8){
      this.message="Class A City  Amount Exceed Maximum Limit";
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.boardingClassBCity))){
      this.message = 'Please Fill Only Amount In Lodging Class City B';
     
    }
    else if(this.res.boardingClassBCity.length!==undefined && this.res.boardingClassBCity.length>8){
      this.message='Class B City  Amount Exceeds Maximum Limit'
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.boardingClassCCity))){
      // console.log(FieldValidation.isValidCurrency(this.res.boardingClassCCity))
      this.message = 'Please Fill Only Amount In Lodging Class City C';
    }
    else if(this.res.boardingClassCCity.length!==undefined && this.res.boardingClassCCity.length>8){
      this.message='Class C City  Amount Exceeds Maximum Limit';
    }
    // add new 
    else if( this.res.lodgingClassACitySelf!==undefined && this.res.lodgingClassACitySelf.length>8){
      this.message=" Lodging Self Class A City  Amount Exceed Maximum Limit";
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.lodgingClassACitySelf))){
      this.message = 'Please Fill Only Amount In  Lodging Self Class City A';
     
    }
    else if(this.res.lodgingClassBCitySelf!==undefined  && this.res.lodgingClassBCitySelf.length>8){
      this.message=' Lodging Self Class B City  Amount Exceeds Maximum Limit'
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.lodgingClassBCitySelf))){
      // console.log(FieldValidation.isValidCurrency(this.res.boardingClassCCity))
      this.message = 'Please Fill Only Amount In Lodging Self Class City B';
    }
    else if(this.res.lodgingClassCCitySelf!==undefined && this.res.lodgingClassCCitySelf.length>8){
      this.message=' Lodging Self Class C City  Amount Exceeds Maximum Limit';
    }
    // 
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.lodgingClassCCitySelf))){
      this.message = 'Please Fill Only Amount In Loadging Self Amount City C';
    }
    else if( this.res.boardingAmountCityA!==undefined && this.res.boardingAmountCityA.length>8){
 this.message='Class Boarding Amount City A Exceeds Maximum Limit';
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.boardingAmountCityB))){
      this.message = 'Please Fill Only Amount In Boarding Amount City B';
    }
    else if(this.res.boardingAmountCityB.length!==undefined  && this.res.boardingAmountCityB.length>8){
    this.message='Class Boarding Amount City B Exceed Maximum Limit'
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.boardingAmountCityC))){
      this.message = 'Please Fill Only Amount In Boarding Amount City C';
    }
   else if(this.res.boardingAmountCityC.length!==undefined && this.res.boardingAmountCityC.length>8){
   this.message='Class Boarding Amount City C Exceed Maximum Limit'
   }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.twoWheelerChargesParKilo))){
      this.message = 'Please Fill Only Amount In Boarding Two Wheeler Charge';
    }
    else if(this.res.twoWheelerChargesParKilo.length!==undefined && this.res.twoWheelerChargesParKilo.length>8){
      this.message='Two Wheeler Amount Chareges Exceed Maximum Limit';
    }
    else if((!/(?=.)((?:0|(?:[1-9](?:\d*|\d{0,2}(?:,\d{3})*)))?(?:\.\d*[1-9])?)(?!\S)/.test(this.res.fourWheelChargesParkilo))){
      this.message = 'Please Fill Only Amount in Boarding Four Wheeler Charge';
    }
    else if(this.res.fourWheelChargesParkilo.length!==undefined && this.res.fourWheelChargesParkilo.length>8){
   this.message='Four Wheeler Amount Charges Exceed Maximum Limit';
    }
    else if(!FieldValidation.isEmpty(this.res.twoWheelerMaxDistance) && (this.res.twoWheelerMaxDistance).length>8){
      this.message='Please Two Wheeler Max Distance In Exceed Limit'
    } else if(!FieldValidation.isEmpty(this.res.fourWheelMaxDistance) && (this.res.fourWheelMaxDistance).length>8){
      this.message='Please four Wheeler Max Distance In Exceed Limit'
    }
    else{
      this.istrue=true;
      if(!this.isEditMode){
    
        this.req.boardingName=this.res.boardingName;
        this.req.boardingClassACity=this.res.boardingClassACity;
        this.req.boardingClassBCity=this.res.boardingClassBCity;
        this.req.boardingClassCCity=this.res.boardingClassCCity;
        this.req.boardingAmountCityA=this.res.boardingAmountCityA;
        this.req.boardingAmountCityB=this.res.boardingAmountCityB;
        this.req.boardingAmountCityC=this.res.boardingAmountCityC;
        this.req.twoWheelerChargesParKilo=this.res.twoWheelerChargesParKilo;
        this.req.fourWheelChargesParkilo=this.res.fourWheelChargesParkilo;
        this.req.twoWheelerMaxDistance=this.res.twoWheelerMaxDistance;
        this.req.fourWheelerMaxDistance=this.res.fourWheelMaxDistance;
        this.req.lodgingClassACitySelf=this.res.lodgingClassACitySelf;
        this.req.lodgingClassBCitySelf=this.res.lodgingClassBCitySelf;
        this.req.lodgingClassCCitySelf=this.res.lodgingClassCCitySelf;
        this.spinner.show()
        this.bordingservice.saveBoading(this.req).subscribe(data=>{
          this.boadingNameTamp=data.boardingName
          this.message = this.boadingNameTamp+' Created Successfully';

          this.onBoadingCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
            this.res = new Bat_BoardingLoadingDetailsResponse();
          }
          this._global.alert({ type: '', message: this.message });
          this.onCancle.emit('cancled');


        }, error => {
          this.spinner.hide();
          this.message = error.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        
        })
      }
      else{
        // not edit

        this.req.boardingName = this.res.boardingName;
        this.req.boardingClassACity = this.res.boardingClassACity;
        this.req.boardingClassBCity = this.res.boardingClassBCity;
        this.req.boardingClassCCity = this.res.boardingClassCCity;
        this.req.boardingAmountCityA = this.res.boardingAmountCityA;
        this.req.boardingAmountCityB = this.res.boardingAmountCityB;
        this.req.boardingAmountCityC = this.res.boardingAmountCityC;
        this.req.twoWheelerChargesParKilo = this.res.twoWheelerChargesParKilo;
        this.req.fourWheelChargesParkilo = this.res.fourWheelChargesParkilo;
        this.req.twoWheelerMaxDistance=this.res.twoWheelerMaxDistance;
        this.req.fourWheelerMaxDistance=this.res.fourWheelMaxDistance;
        this.req.lodgingClassACitySelf=this.res.lodgingClassACitySelf;
        this.req.lodgingClassBCitySelf=this.res.lodgingClassBCitySelf;
        this.req.lodgingClassCCitySelf=this.res.lodgingClassCCitySelf;
  
        this.spinner.show();
        this.bordingservice.updateBoading(this.req).subscribe(data => {
          this.boadingNameTamp = data.boardingName;
          this.message = this.boadingNameTamp+' Updated Successfully';
          this.onBoadingCreated.emit(data);
          this.spinner.hide();
          this.res = new Bat_BoardingLoadingDetailsResponse();
          this._global.alert({ type: '', message: this.message });
          this.onCancle.emit('cancled');

        }, error => {
          this.spinner.hide();
          this.message = error.error.message;
          this._global.alert({ type: 'alert', message: this.message });
         

        });

      }

    }

    if (!this.istrue) {
      this._global.alert({ type: 'alert', message: this.message });
    }
  }
  cancelData() {
    this.onCancle.emit('cancled');
  }

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
