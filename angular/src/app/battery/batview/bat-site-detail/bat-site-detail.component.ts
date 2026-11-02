import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { AssetsService } from '../../../services/assets.services';
import { Loginservices } from '../../../services/login.services';
import { AccountCreate, ActionHistoryObjectAndId, AssetsResponse, Page, PreRegCaseResponse, ServiceAppointmentResponse, SiteDetailsResponse } from '../../../services/model';
import { siteService } from '../../../services/siteService.services';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTassetResp, BTCaseResponse, BtPreRegCaseResponse, GetAllBTsiteResp } from '../../bt_model';

@Component({
  selector: 'app-bat-site-detail',
  templateUrl: './bat-site-detail.component.html',
  styleUrls: ['./bat-site-detail.component.css']
})
export class BatSiteDetailComponent implements OnInit {

  sortDisplay: boolean = true;

  siteList: Array<SiteDetailsResponse> = [];
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create Site ';
  EDIT_ACCOUNT_HEADING: string = 'Edit Site '
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  msg: string
  isSiteModelShow: boolean;
  @ViewChild('createAcountModel') createAcountModel: ModalDirective;
  @ViewChild('editSiteModel') editSiteModel: ModalDirective;
  @ViewChild('createServiceAppointmentModel') createServiceAppointmentModel: ModalDirective;
  @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('createCaseModel') createCaseModel: ModalDirective;
  // @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @ViewChild('createPRCModel') createPRCModel :ModalDirective
  isDCTCaseModelShow: boolean = false;
  account: AccountCreate = new AccountCreate();
  sapId: string = '';
  siteId: number;
  isInstallcase:boolean=true;
  // siteres: SiteDetailsResponse = new SiteDetailsResponse()
  resp: GetAllBTsiteResp = new GetAllBTsiteResp()
  caseresp: BTCaseResponse = new BTCaseResponse();
  cases: Array<BTCaseResponse>
  res: BTCaseResponse = new BTCaseResponse();
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  sortColumnName: string = 'created_date'; // first column variable
  sortOrder: string = 'desc';
  page: number = 0;
  pageAsset:number=0;
  casedetails: Array<BTCaseResponse> = [];
  totalCase: any
  serviceappoint: Array<ServiceAppointmentResponse>=[];
  total_service: any;
  asset: Array<BTassetResp> =[];
  totalasset: any
  assetResponse: BTassetResp = new BTassetResp()
  serviceAppointmentResponse: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  isCaseModelShow: boolean;
  serviceCenterId:number;
  isClone:string='';
pageserv:number=0;
firstserv: boolean;
lastserv: boolean;
serialNumberserv: number;
sizeserv: number;
serialNumberAst:number;
cmponent:string="case"
cmponentprc:string="prc";
cmponentsiteprc:string="siteprc"
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
  sitesList: Array<any> = [];
   columns =  ["S.No", "Case ID", "Case Type","Case Reason","Status"] ;
   caseCoulms:any=[];
   siteColumnsprc:any=[];
   sitePrcHeader=["S.No","Prc No","Type","Case Reason","Action","Case Number"];



  date: Date;
  _cFlag:string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private _global:GlobalserviceService,private btSiteservice:BtsiteserviceService, private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, 
     private loginservice: Loginservices, private assetsService: BTassetserviceService, private preregcaseservice:BtPreRegistrationCaseService,private router:Router) {
    this._activatedRoute.params.subscribe(pram => {
      this.siteId = pram['siteId'];
      this.showSiteDetailsResp();
      this.getLoggedInUser();
      this.actionHistoryObjectAndId.objectId = '' + this.siteId;
      this.actionHistoryObjectAndId.objectName = 'BtSiteDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
       this.caseCoulms=this.columns;
       this.siteColumnsprc=this.sitePrcHeader;
      this._cFlag = pram['_c'];
    });
    this.date = new Date()
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  ngOnInit() {
    // this.showSiteDetailsResp()
    this.casedetails=[];
    this.prcdata.content=[];
    this.asset=[];
  }
  getServiceApointment(column: string, order: string) {
    this.spinner.show();
    this.btSiteservice.getServiceAppointmentBySiteId(this.siteId, this.pageserv, column, order).subscribe(data => {
      this.spinner.hide();
      this.serviceappoint = data.content;
      this.total_service = data.totalElements;
      this.firstserv = data.first;
      this.lastserv = data.last;
      this.sizeserv = data.size;
      this.serialNumberserv = (((this.pageserv) * this.sizeserv));
    })
  }
  
  getAssetBySiteid(column: string, order: string) {
    this.spinner.show();
    this.btSiteservice.getAssetBySiteId(this.siteId,`page=${this.page}&size=${this.size}`).subscribe(data => {
     this.spinner.hide();
      this.asset = data.content;
      this.totalasset=data.totalElements;
      this.firstserv=data.first;
      this.lastserv=data.last;
      this.serialNumberAst=(((this.pageAsset)*data.size));
    })
  }


  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  loginData;


message:string;
  showSiteDetails() {
    this.spinner.show()
    this.btSiteservice.getBtSitedataById(this.res.siteId).subscribe(data=>{
      this.spinner.hide()
      this.resp=data
      
  
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
  
    });
    // this.spinner.show();
    // this.siteservice.getDetailBysiteId(this.siteId).subscribe(data => {
    //   this.spinner.hide()
    //   this.resp = data

    // },error=>{
    //   this.spinner.hide()
    //   this.message=error.error.message;
    //   this.popupSuccessModel.show()
    //   setTimeout(() => {
    //   this.popupSuccessModel.hide();
    //   }, 2000
    // );
    // });

  }
 
  showSiteDetailsResp() {
    this.spinner.show();
    this.btSiteservice.getBtSitedataById(this.siteId).subscribe(data => {
      this.spinner.hide()
      this.resp = data
      this.siteId=data.siteId;
      this.serviceCenterId=data.serviceCenterId;
     
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this._global.alert({type: 'alert',message: this.message})

    });

  }

  getCase(column: string, order: string) {
    this.spinner.show();
    const size=5;
    
    this.btSiteservice.getCaseBySiteId(this.siteId, this.page, column, order,size).subscribe(data => {
      this.spinner.hide();
      this.casedetails = data.content;
      this.totalCase = data.totalElements;
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this._global.alert({type: 'alert',message: this.message})

    })
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
    this.getCase(this.sortColumnName, this.sortOrder);
  }

  setPage(event: any) {
    event.preventDefault();
    this.getCase(this.sortColumnName, this.sortOrder);
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
    this.getCase(this.sortColumnName, this.sortOrder);
  }
  // serviceAppointment pagebleMe
  setPagePreviousserv(event: any) {
    event.preventDefault();
    this.pageserv = this.pageserv - 1;
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  }

  setPageserv(event: any) {
    event.preventDefault();
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  }

  setPageNextserv(event: any) {
    event.preventDefault();
    this.pageserv = this.pageserv + 1;
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  }
  // Asset pageABle
  setPagePreviousAst(event: any) {
    event.preventDefault();
    this.pageAsset = this.pageAsset - 1;
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
  }

  setPageAst(event: any) {
    event.preventDefault();
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
  }

  setPageNextAst(event: any) {
    event.preventDefault();
    this.pageAsset = this.pageAsset + 1;
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
  }

  onSiteCreated($event: GetAllBTsiteResp) {
    //  console.log('event',$event);
    this.resp = $event
    this.siteId=this.resp.siteId;
  if(this.isClone=="clone"){
      this.isClone="edit";
      this.showSiteDetailsResp();
      this.router.navigate(['/bat_site/'+this.siteId])

    }

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
      this.showSiteDetailsResp();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      // this.getServiceApointment(this.sortColumnName, this.sortOrder)
      this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
      this.getCase(this.sortColumnName, this.sortOrder);
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
      this.getPrcBysite();
      // this.getContacts();
    }
    return false;
  }

  cancelData() {
    this.editSiteModel.hide();
    if(this.isClone=="clone"){
      this.isClone="edit";
      this.showSiteDetailsResp();

    }

  }

  editSiteModelshow(res: string) {
  
      this.isClone=res;
 
    this.isEditMode = true;
    this.editSiteModel.show();
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.btSiteservice.getBtSitedataById(this.siteId).subscribe(data => {
      this.spinner.hide()
      this.resp = data

    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this._global.alert({type: 'alert',message: this.message})
    });

  }


  myFunction() {
    window.print();

  }
  cancelDataservice() {
    // this.createServiceAppointmentModel.hide()
  }
  onServiceAppointmentCreated($event) {
    this.serviceappoint.push($event)
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  this.getCase(this.sortColumnName, this.sortOrder);
  }
  onAssetCreated($event) {
    this.asset.push($event);
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  this.getCase(this.sortColumnName, this.sortOrder);
  }
  onCaseCreated($event: BTCaseResponse) {
    this._global.alert({type:'',message:`${$event.caseManualId} Created Successfully`});
    if (!this.isEditMode) {
      this.casedetails.unshift($event);
      // this.totalCase=this.totalCase+1;
    } else {
      let index = 0;
      this.casedetails.forEach(element => {

        if (element.id == $event.id) {
          this.casedetails[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
    this.getCase(this.sortColumnName, this.sortOrder);
  }
  editserviceModelshow(service: ServiceAppointmentResponse) {
    this.serviceAppointmentResponse = service
    this.isEditMode = true;
    this.heading = "Edit Service Appointment";
    this.createServiceAppointmentModel.show()
  }

  editassetModelshow(assetresp: BTassetResp) {
    this.assetResponse = assetresp;
    this.spinner.show();
    this.assetsService.getAssetsBySerialNumber(this.assetResponse.serialNumber).subscribe(data => {
      this.assetResponse = data
      if (data != null) {
        this.spinner.hide();
      }
      if (data.siteId == null) {
        this.assetResponse.siteId = 0;
      }
    });
    this.isEditMode = true;
    this.heading = "Edit Asset"
    this.createAssetModel.show()
  }
  cancelDataasset() {
    this.createAssetModel.hide();
  }
  siteseviceResourceID:number;
  createCaseModelshow() {
    this.isEditMode=false;
    this.heading = "Create Case"
    
    this.spinner.show();
        this.btSiteservice.getBtSitedataById(this.resp.siteId).subscribe(data => {
          this.spinner.hide();
          this.res.siteId = data.siteId;
          this.res.siteName = data.siteName;
          this.res.siteManualId = data.siteManualId;
          this.res.serviceCenterId = data.serviceCenterId;
          this.serviceCenterId=data.serviceCenterId;
          this.res.serviceCenterName = data.serviceCenterName;
          this.res.serviceResourceId = data.serviceResourceId;
          this.siteseviceResourceID=data.serviceResourceId;
          // this.res.serviceResourceName = data.serviceResourceName;
        },error=>{
          this.spinner.hide()
          this.message=error.error.message;
          this._global.alert({type: 'alert',message: this.message})

        })

    // this.res.caseReasonVerticalId = verticalId;
 this.createCaseModel.show();
     
    
   
  }

  cancelDatacase() {
    this.createCaseModel.hide();
     this.res = new BTCaseResponse();
  }

  urlencode(urlc):string{
    return encodeURIComponent(urlc);
  }
  preres: PreRegCaseResponse = new PreRegCaseResponse();
createPRCModelshow(siter:SiteDetailsResponse){
// this.res=asst;
this.preres.siteId=siter.siteId;
this.preres.siteManualId=siter.siteManualId;
this.preres.accountName=siter.accountName;
this.preres.accountSapNumber=siter.accountSapNumber;
// this.res.assetsPartCode=siter.part;
this.heading="Create Pre-Registration";
  // console.log(asst);
  this.createPRCModel.show();

}
prcModelhide(){
  this.createPRCModel.hide();
}
onPRCCreated(){
  this.showSiteDetailsResp();
  this.getAssetBySiteid(this.sortColumnName, this.sortOrder);
      this.getCase(this.sortColumnName, this.sortOrder);
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
      this.getPrcBysite();
}
prcdata:Page<BtPreRegCaseResponse>=new Page<BtPreRegCaseResponse>()
prcserialNumber:any;
getPrcBysite(){
  this.spinner.show();
  this.prcdata.content=[];
  const size=5;
  this.preregcaseservice.getPRCBySite(this.siteId,`page=`+this.page+`&size=`+size).subscribe(data=>{
  this.spinner.hide();
  this.prcdata=data;
  console.log(this.prcdata)
  this.prcserialNumber=(((this.page)*data.size));
  })
}
}