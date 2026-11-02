import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { SSARequest, SSAResponse, SiteDetailsResponse, ActionHistoryObjectAndId } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ssaService } from '../../services/ssa.services';

@Component({
  selector: 'app-ssadetails',
  templateUrl: './ssadetails.component.html',
  styleUrls: ['./ssadetails.component.scss']
})
export class SsadetailsComponent implements OnInit {
ssaId:number;
ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  req: SSARequest = new SSARequest()
  res: SSAResponse = new SSAResponse()
  ssares: SSAResponse = new SSAResponse()
  @ViewChild('createSSAModel') createSSAModel: ModalDirective
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  private cityName: string
  sapId: string = '';
  message: string;
  tabledata: any;
  loginInfo:any;
  domainId: number;
  isEditMode: boolean = false;
  heading: string;
  page:number=0;
  siteList:Array<SiteDetailsResponse>=[];
  serialNumber:number;
  totalSite:number;
  first: boolean;
  last: boolean;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create SSA';
  EDIT_ACCOUNT_HEADING: string = 'Edit SSA';
  popupNgClass: string = 'alert alert-danger center center-block';
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
loginData:any;
_cFlag:string;

actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private loginservice: Loginservices,private ssaservice:ssaService) {
    this._activatedRoute.params.subscribe(pram => {
      this.ssaId = pram['id'];
      this.actionHistoryObjectAndId.objectId = '' + this.ssaId;
      this.actionHistoryObjectAndId.objectName = 'SSA';
   });
   this._activatedRoute.queryParams.subscribe(pram => {
    this._cFlag = pram['_c'];
  });
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  ngOnInit() {
    this.getSSA();
    this.getLoggedInUser();
    this.loginInfo = this.loginservice.getLoggedInUser();
  
    this.domainId = this.loginInfo.emptype['businessDomainId']
    
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  getSSA(){
this.ssaservice.getSSAById(this.ssaId).subscribe(data=>{
  this.res=data;
})
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
      this.getSSA();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getSite();
    }
    return false;
  }
  editSSAModel(res:SSAResponse){
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING;
    this.createSSAModel.show();
    this.spinner.show();
 this.ssaservice.getSSAById(res.id).subscribe(data=>{
   this.spinner.hide();
   this.ssares=data;
 },error=>{
  this.spinner.hide();
  this.message=error.error.message;
  this.popupSuccessModel.show();
 })
  }
  cancelSSAData(){
    this.ssares=new SSAResponse();
    this.createSSAModel.hide();
  }
  onSSACreated($event){
    this.res=$event;
    this.getSSA();
    this.getSite();
  }
 
  getSite(){
    this.spinner.show();
    this.ssaservice.getSiteBySSAID(this.res.id,this.page).subscribe(data=>{
    this.spinner.hide();
      this.siteList=data.content;
    this.totalSite = data.totalElements;
    this.first = data.first;
    this.last = data.last;
    this.serialNumber = (((this.page) * data.size));
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getSite();
  }

  setPage(event: any) {
    event.preventDefault();
    this.getSite();
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.getSite()
  }

}
