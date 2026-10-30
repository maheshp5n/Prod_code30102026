import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { LegalEntityResponse, LocationResponse, Contacts, ActionHistoryObjectAndId } from '../../services/model';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ContactsService } from '../../services/contacts.services';
@Component({
  selector: 'app-legalentityview',
  templateUrl: './legalentityview.component.html',
  styleUrls: ['./legalentityview.component.css']
})
export class LegalentityviewComponent implements OnInit {

  ngClassDetails:string='active';
  ngClassReleated:string='';
  vDetails:boolean=true;
   name:string;
   private Id:number;
   isEditMode:boolean=false;
   heading : string;
   edit:boolean=false;
   CREATE_ACCOUNT_HEADING:string= 'Create Legal Entity';
   EDIT_ACCOUNT_HEADING:string= 'Edit Legal Entity'
  req:LegalEntityResponse=new LegalEntityResponse();
  response:LegalEntityResponse=new LegalEntityResponse();
  responseEntity:Array<LegalEntityResponse>
  message:string="";
  sortColumnName:string;
  sortOrder:string;
  loginData:any;
  totalPagesloc:any;
  firstloc: boolean;
  lastloc: boolean;
  serialNumberloc: number;
  sizeloc:number;
  totalPageseng:any;
  firsteng: boolean;
  lasteng: boolean;
  serialNumbereng: number;
  sizeeng:number;
  pageLoc:number=0;
  pageEng:number=0;
  location:Array<LocationResponse>=[];
  totallocation:any;
  enginr:Array<Contacts>=[];
  totalEng:any;
  localres: LocationResponse = new LocationResponse()
  @ViewChild('createLegalENtityModel',{static:false}) createLegalENtityModel:ModalDirective
  @ViewChild('deleteLegalENtityModel',{static:false}) deleteLegalENtityModel:ModalDirective
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective
  @ViewChild('createContactModel',{static:false}) createContactModel:ModalDirective;
  @ViewChild('creatLocation',{static:false}) creatLocation:ModalDirective;
  sapId:string='';
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

  _cFlag:string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor( private ligalentitservice:ligalEntityService,private _activatedRoute:ActivatedRoute,private spinner: NgxSpinnerService,private loginservice: Loginservices,private contactservice:ContactsService) {
   
    this._activatedRoute.params.subscribe(pram=>{
      this.Id=pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.Id;
      this.actionHistoryObjectAndId.objectName = 'LegalEntity';
     });
     this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

   }

   rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
    ngOnInit() {
      this.getLoggedInUser();
     this.showLigalEntityResponse();
     this.getLocations(this.sortColumnName, this.sortOrder);
     this.getEngineer(this.sortColumnName, this.sortOrder)
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
  subTabChange(tabName:string){
    if(tabName=='details'){
      this.ngClassDetails='active';
      this.ngClassReleated='';
      this.vDetails=true;
      this.showLigalEntityResponse();
    }
    else{
      this.ngClassDetails='';
      this.ngClassReleated='active';
      this.vDetails=false;
      this.getLocations(this.sortColumnName, this.sortOrder);
      this.getEngineer(this.sortColumnName, this.sortOrder);
      // this.getContacts();
    }
    return false;
  }
  createLegalENtityModelshow(req:LegalEntityResponse){
    this.showLigalEntity();
    this.isEditMode = true;
    this.heading =this.EDIT_ACCOUNT_HEADING;
 this.req=req
  this.createLegalENtityModel.show();
}
 
  showLigalEntity()
  {
    this.spinner.show();
   this.ligalentitservice.getLigalENtityById(this.Id).subscribe(data=>{
      this.spinner.hide();
      this.req.ownerAccountId=data.ownerAccountId;
      this.req=data
   },error=>{
    this.spinner.hide()
    this.message=error.error.message;
    this.popupSuccessModel.show()
    setTimeout(() => {
    this.popupSuccessModel.hide();
    }, 2000
  );
   });
  }
  showLigalEntityResponse()
  {
    this.spinner.show();
   this.ligalentitservice.getLigalENtityById(this.Id).subscribe(data=>{
    if(data!=null){
      this.spinner.hide();
    }
    this.response=data;
   },error=>{
    this.spinner.hide()
    this.message=error.error.message;
    this.popupSuccessModel.show()
  
   });
  }

  getLocations(column:string,order:string){
    this.spinner.show();
    this.ligalentitservice.getLocationById(this.Id,this.pageLoc,column,order).subscribe(data=>{
      this.spinner.hide();
      this.location=data.content;
      this.totallocation=data.totalElements;
      this.totalPagesloc = data.totalPages;
        this.firstloc = data.first;
       this.lastloc = data.last;
       this.sizeloc = data.size;
      this.serialNumberloc = (((this.pageLoc) * this.sizeloc));
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
    
    })

  }


  getEngineer(column:string,order:string){
    this.spinner.show();
    this.ligalentitservice.getEngById(this.Id,this.pageEng,column,order).subscribe(data=>{
     this.spinner.hide();
      this.enginr=data.content;
      this.totalEng=data.totalElements;
      this.totalPageseng = data.totalPages;
        this.firsteng = data.first;
       this.lasteng = data.last;
       this.sizeeng = data.size;
      this.serialNumbereng = (((this.pageEng) * this.sizeeng));

    },error=>
    {
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
    
    })
  }


  onEntityCreated($event:LegalEntityResponse){
    this.response = $event;
    this.showLigalEntityResponse();
    // this.showLigalEntityResponse();
    this.getLocations(this.sortColumnName, this.sortOrder);
    this.getEngineer(this.sortColumnName, this.sortOrder)
  }
  cancelData() {
  this.createLegalENtityModel.hide();
  this.popupSuccessModel.hide();
  }
  newcontact: Contacts = new Contacts();
  editEngineer(cont:Contacts){
    this.spinner.show();
    this.newcontact = cont;
    this.contactservice.getContact(this.newcontact.contactid).subscribe((data) => {
      this.spinner.hide();
      this.newcontact = data;
    });
    this.isEditMode = true;
    this.heading ="Edit Contact";
    this.createContactModel.show();
  }
  legalENtityName:string;
  legalENtityId:number;
  createLocation(){
    this.isEditMode=false;
    this.heading="Create Location";
    this.creatLocation.show()
    this.ligalentitservice.getLigalENtityById(this.Id).subscribe(data=>{
      this.spinner.hide();
      this.localres.legalEntityName=data.legalEntityName;
      this.legalENtityName=data.legalEntityName;
      this.legalENtityId=data.legalentityId;
    })
   
  }
  editLocation(loc:LocationResponse){
    this.localres=loc;
    this.localres.legalEntityName=this.response.legalEntityName;
    this.isEditMode=true;
    this.heading="Edit Location"
    this.creatLocation.show()

  }
  cancelcontactData(){
    this.createContactModel.hide()
  }

  cancelLocationData(){
    this.creatLocation.hide()

  }
  //pageAble  Location
  setPagePrevious(event: any) {
    event.preventDefault();
    this.pageLoc = this.pageLoc - 1;
      this.getLocations(this.sortColumnName, this.sortOrder);
    
  
  }

  setPage(event: any) {
    event.preventDefault();
    this.getLocations(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.pageLoc = this.pageLoc + 1;
    this.getLocations(this.sortColumnName, this.sortOrder);
  }

  //page able engineer
  
  setPagePreviousEng(event: any) {
    event.preventDefault();
    this.pageEng = this.pageEng - 1;
      this.getEngineer(this.sortColumnName, this.sortOrder);
    
  
  }

  setPageEng(event: any) {
    event.preventDefault();
    this.getEngineer(this.sortColumnName, this.sortOrder);
  }

  setPageNextEng(event: any) {
    event.preventDefault();
    this.pageEng = this.pageEng + 1;
    this.getEngineer(this.sortColumnName, this.sortOrder);
  }
  onLocationCreated($event){
    this.location.unshift($event);
    this.getLocations(this.sortColumnName, this.sortOrder);
    this.showLigalEntityResponse();
    // this.getLocations(this.sortColumnName, this.sortOrder);
    this.getEngineer(this.sortColumnName, this.sortOrder)
  }

}
