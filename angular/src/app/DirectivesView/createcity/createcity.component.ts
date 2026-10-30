import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { cityDetailsReq, cityDetailsResponse, CityCatagory } from '../../services/model';
import { cityService } from '../../services/cityService.service';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';

@Component({
  selector: 'app-createcity',
  templateUrl: './createcity.component.html',
  styleUrls: ['./createcity.component.css']
})
export class CreatecityComponent implements OnInit {
  res: cityDetailsResponse = new cityDetailsResponse();
  request: cityDetailsReq = new cityDetailsReq();
  catRes: CityCatagory = new CityCatagory();
  cityList: Array<cityDetailsReq> = [];
  Allcity: Array<cityDetailsResponse> = [];
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  tabledata: Array<CityCatagory> = [];
  cityNameTamp:string;
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editEntityData(res: cityDetailsResponse) {
    this.res = res;
  }

  get editEntityData(): cityDetailsResponse {
    return this.res;
  }

  @Output()
  onCityCreated: EventEmitter<cityDetailsReq> = new EventEmitter<cityDetailsReq>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  constructor(private cityservice: cityService, private spinner: NgxSpinnerService) { }


  ngOnInit() {
 
    this.showCategory();
    if (!this._isEditMode) {
      this.res = new cityDetailsResponse();
    }
  }


  showCategory() {
    this.spinner.show()
    this.cityservice.getcityCatg().subscribe((data) => {
      if(data!=null){
        this.spinner.hide()
      }
     this.tabledata=data;
     console.log(this.tabledata);
     
  
    },error=>{
      this.spinner.hide()
           this.message=error.error.message;
           this.popupSuccessModel.show()
           setTimeout(() => {
           this.popupSuccessModel.hide();
           }, 2000
         );
    })
  }


  postCity() {
     if (this.res.cityName == null || this.res.cityName == '' || !FieldValidation.isValidName(this.res.cityName)) {
      this.message='Please Fill City Name';
    }
    else if((this.res.cityName.length>200)){
   this.message='City Name Exceeds 200 Characters';
    }
     else if (this.res.cityCategoryId == null || this.res.cityCategoryId == 0) {
      this.message='Please Select Category Name';
    }
  
    else {
      this.istrue = true;
      if(this._isEditMode!=true)
       {
         this.request.cityCategoryId=this.res.cityCategoryId;
       // this.request.catagoryName = this.res.cityCatagoryName;
         this.request.cityName = this.res.cityName;
        this.spinner.show()
        this.cityservice.saveCity(this.request).subscribe(data => {
          this.cityNameTamp=data.cityName
        this.onCityCreated.emit(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.message = 'Created Successfully';
        this.spinner.hide();
        
        this.popupSuccessModel.show();
        // setTimeout(() => {
        //   this.popupSuccessModel.hide();
        //   this.onCancle.emit('cancled');
        //   this.res=new cityDetailsResponse();
        // }, 2000
        // );
      }, error => {
          this.spinner.hide()
          this.message=error.error.message;
          this.popupSuccessModel.show()
          setTimeout(() => {
            this.popupSuccessModel.hide();
          }, 2000
          );}
      
      );
       } else {
         this.request.cityCategoryId=this.res.cityCategoryId;
        this.request.cityName = this.res.cityName;
        this.spinner.show()
        this.cityservice.updateCity(this.request, this.request.cityName).subscribe(data => {
          this.cityNameTamp=data.cityName
          this.onCityCreated.emit(data);
          this.popupNgClass = 'alert alert-success center center-block';
          this.message = 'Updated Successfully'
          this.spinner.hide();
           this.popupSuccessModel.show();
          // setTimeout(() => {
          //   this.popupSuccessModel.hide();
          //   this.onCancle.emit('cancled');
          //   this.res=new cityDetailsResponse();
          // }, 2000
          // );
        }, error => {
           this.spinner.hide()
           this.message=error.error.message;
           this.popupSuccessModel.show()
           setTimeout(() => {
           this.popupSuccessModel.hide();
           }, 2000
         );
        });
      }
    }
    if (!this.istrue) {
      this.popupSuccessModel.show();
      setTimeout(() => {
        this.popupSuccessModel.hide();
      }, 2000
      );
    }
  }




  cancelData() {
    if (this.isEditMode != true) {
      this.res = new cityDetailsResponse();
    }
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
      
    }
  }

}
