import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../services/model';
import { BAT_BoardingLoadingService } from '../../bat_services/bt-boadigLoadgingDA.service';
import { Bat_BoardingLoadingDetailsResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-boading-loadgin-da-details',
  templateUrl: './bt-boading-loadgin-da-details.component.html',
  styleUrls: ['./bt-boading-loadgin-da-details.component.scss']
})
export class BtBoadingLoadginDaDetailsComponent implements OnInit {

  ngClassDetails:string='active';
  ngClassReleated:string='';
  vDetails:boolean=true;
  private name:string;
   private Id:number;
   isEditMode:boolean=false;
   heading : string;
   edit:boolean=false;
   CREATE_ACCOUNT_HEADING:string= 'Create Legal Entity';
   EDIT_ACCOUNT_HEADING:string= 'Edit Boarding/Loadging'
  res:Bat_BoardingLoadingDetailsResponse=new Bat_BoardingLoadingDetailsResponse();
  response:Bat_BoardingLoadingDetailsResponse=new Bat_BoardingLoadingDetailsResponse();
  responseEntity:Array<Bat_BoardingLoadingDetailsResponse>
  message:string="";
  loginData;
  @ViewChild('createBoardingModel') createBoardingModel:ModalDirective
 
  sapId:string='';
  addressDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  };
  infoDisplay={
    angleIcon:'fa fa-angle-down',
    open:true
  }
  lodgingSelfDisplay={
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
_cFlag:string;
actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor( private _activatedRoute: ActivatedRoute,private loginservice:Loginservices,
    private boadingservice: BAT_BoardingLoadingService,private spinner:NgxSpinnerService,
    private _global:GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.boardingName = pram['boardingName'];
      this.actionHistoryObjectAndId.objectId = this.boardingName;
      this.actionHistoryObjectAndId.objectName = 'BoardingLoadingDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });
   }
   rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  ngOnInit() {
  this.getLoggedInUser()
  this.res=new Bat_BoardingLoadingDetailsResponse();
  this.getBoadingDetails()
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
    
  }
  getBoadingDetails(){
      this.spinner.show();
    this.boadingservice.getDetailByboadingName(this.boardingName).subscribe(data=>{
      this.spinner.hide();
      this.response=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({type:'alert',message:this.message})
    });
  }
  boardingEditModelshow(res:Bat_BoardingLoadingDetailsResponse){
    this.res=res;
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING
    this.spinner.show();
    
    this.boadingservice.getDetailByboadingName(this.res.boardingName).subscribe(data=>{
      this.spinner.hide();
      this.res=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({type:'alert',message:this.message})
    });
    this.createBoardingModel.show();

  }
  cancelData() {
    this.isEditMode=false;
     this.createBoardingModel.hide();
  
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
  LodgingSelfInfoDisplay(){
    this.lodgingSelfDisplay.open=!this.lodgingSelfDisplay.open
    if(this.lodgingSelfDisplay.open){
      this.lodgingSelfDisplay.angleIcon='fa fa-angle-down';
    }
    else{
      this.lodgingSelfDisplay.angleIcon='fa fa-angle-right';
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
  subTabChange(tabName:string){
    if(tabName=='details'){
      this.ngClassDetails='active';
      this.ngClassReleated='';
      this.vDetails=true;
    }
    else{
      this.ngClassDetails='';
      this.ngClassReleated='active';
      this.vDetails=false;
      // this.getContacts();
    }
    return false;
  }
  print(){
    window.print();
  }
  onBoadingCreated($event:Bat_BoardingLoadingDetailsResponse){
    this.response=$event
    this.getBoadingDetails();

  }


}
