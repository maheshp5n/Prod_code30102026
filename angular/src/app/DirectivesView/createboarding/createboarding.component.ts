import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { BoardingLoading, BoardingLoadingDetailsRequest, BoardingLoadingDetailsResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { isObjectEmpty } from 'ngx-bootstrap/chronos/utils/type-checks';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-createboarding',
  templateUrl: './createboarding.component.html',
  styleUrls: ['./createboarding.component.scss']
})
export class CreateboardingComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  res: BoardingLoadingDetailsResponse = new BoardingLoadingDetailsResponse();
  req: BoardingLoadingDetailsRequest = new BoardingLoadingDetailsRequest();
  loginData: any;
  boadingNameTamp: string;
  boadingIdTamp: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }

  @Input()
  set editEntityData(res: BoardingLoadingDetailsResponse) {
    this.res = res;
  }

  get editEntityData(): BoardingLoadingDetailsResponse {
    return this.res;
  }

  @Output()
  onBoadingCreated: EventEmitter<BoardingLoadingDetailsRequest> = new EventEmitter<BoardingLoadingDetailsRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  constructor(private loginservice: Loginservices, private bordingservice: BoardingLoadingService, private spinner: NgxSpinnerService) { }

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
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';

          this.onBoadingCreated.emit(data);
          if (data != null) {
            this.spinner.hide();
            this.res = new BoardingLoadingDetailsResponse();
          }
          this.popupSuccessModel.show();


        }, error => {
          this.spinner.hide();
          this.message = error.error.message;
          this.popupSuccessModel.show();
        
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
          this.message = 'Updated Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          this.onBoadingCreated.emit(data);
          this.spinner.hide();
          this.res = new BoardingLoadingDetailsResponse();
          this.popupSuccessModel.show();
        }, error => {
          this.spinner.hide();
          this.message = error.error.message;
          this.popupSuccessModel.show();
         

        });

      }

    }

    if (!this.istrue) {
      this.popupSuccessModel.show();
    }
  }
  cancelData() {
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');

    }
  }

}
