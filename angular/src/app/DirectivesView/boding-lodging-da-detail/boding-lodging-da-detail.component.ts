import { Component, OnInit, Input } from '@angular/core';
import { BoardingLoadingDetailsResponse } from '../../services/model';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-boding-lodging-da-detail',
  templateUrl: './boding-lodging-da-detail.component.html',
  styleUrls: ['./boding-lodging-da-detail.component.scss']
})
export class BodingLodgingDaDetailComponent implements OnInit {

  res:BoardingLoadingDetailsResponse=new BoardingLoadingDetailsResponse();
  response:BoardingLoadingDetailsResponse=new BoardingLoadingDetailsResponse();
  ngClassDetails: string = 'active';
  addressDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  };
  infoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  sysinfoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  informationDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  sysCreatInfoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  travellingDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
boardingName:string;
loginData
_cFlag:string;
boringName:string;
vDetails:boolean=true;

@Input()
set BordingRes(res: BoardingLoadingDetailsResponse) {
  this.response = res;
}

get BordingRes(): BoardingLoadingDetailsResponse {
  return this.response;
}
  constructor(private loginservice:Loginservices) {

   }


  ngOnInit() {
    this.getLoggedInUser()
    this.res=new BoardingLoadingDetailsResponse();
    // this.getBoadingDetails()
    }
    getLoggedInUser() {
      this.loginData = this.loginservice.getLoggedInUser();
      
    }
    changeAddressDisplay(){
      this.addressDisplay.open=!this.addressDisplay.open
      if(this.addressDisplay.open){
        this.addressDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.addressDisplay.angleIcon='fa fa-angle-right';
      }
    }
    changeInfoDisplay(){
      this.infoDisplay.open=!this.infoDisplay.open
      if(this.infoDisplay.open){
        this.infoDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.infoDisplay.angleIcon='fa fa-angle-right';
      }
  
    }
    sysInfoDisplay(){
      this.sysinfoDisplay.open=!this.sysinfoDisplay.open
      if(this.sysinfoDisplay.open){
        this.sysinfoDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.sysinfoDisplay.angleIcon='fa fa-angle-right';
      }
      
    }
    InfoDisplay(){
      this.informationDisplay.open=!this.informationDisplay.open
      if(this.informationDisplay.open){
        this.informationDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.sysinfoDisplay.angleIcon='fa fa-angle-right';
      }
      
    }
    sysCreatedByInfoDisplay(){
      this.sysCreatInfoDisplay.open=!this.sysCreatInfoDisplay.open
      if(this.sysCreatInfoDisplay.open){
        this.sysCreatInfoDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.sysCreatInfoDisplay.angleIcon='fa fa-angle-right';
      }
    }
    travelling(){
      this.travellingDisplay.open=!this.travellingDisplay.open
      if(this.travellingDisplay.open){
        this.travellingDisplay.angleIcon='fa fa-angle-down';
      }
      else{
        this.travellingDisplay.angleIcon='fa fa-angle-right';
      }
      
    }

}
