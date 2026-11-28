import { Component, OnInit, ViewChild } from '@angular/core';
import { AccountCreate, SiteDetailsResponse, CaseResponse, ServiceAppointmentResponse, AssetsResponse, SiteDetailsReq, Page, ActionHistoryObjectAndId, PreRegCaseResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ActivatedRoute } from '@angular/router';
import { siteService } from '../../services/siteService.services';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { AssetsService } from '../../services/assets.services';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { HttpParams } from '@angular/common/http';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-sitedetails',
  templateUrl: './sitedetails.component.html',
  styleUrls: ['./sitedetails.component.css']
})
export class SitedetailsComponent implements OnInit {

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
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createPRCModel') createPRCModel :ModalDirective
  isDCTCaseModelShow: boolean = false;
  account: AccountCreate = new AccountCreate();
  sapId: string = '';
  siteId: number;
  siteres: SiteDetailsResponse = new SiteDetailsResponse()
  resp: SiteDetailsResponse = new SiteDetailsResponse()
  caseresp: CaseResponse = new CaseResponse();
  cases: Array<CaseResponse>
  res: CaseResponse = new CaseResponse();
  totalPages: any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  sortColumnName: string = 'createDate'; // first column variable
  sortOrder: string = 'desc';
  page: number = 0;
  pageAsset:number=0;
  casedetails: Array<CaseResponse> = [];
  totalCase: any
  serviceappoint: Array<ServiceAppointmentResponse>=[];
  total_service: any;
  asset: Array<AssetsResponse> =[];
  totalasset: any
  assetResponse: AssetsResponse = new AssetsResponse()
  serviceAppointmentResponse: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  isCaseModelShow: boolean;
  serviceCenterId:number;
pageserv:number=0;
firstserv: boolean;
lastserv: boolean;
serialNumberserv: number;
sizeserv: number;
serialNumberAst:number;
cmponent:string="case"
cmponentsiteast:string="asset"
sortArr:string;
cmponentprc:string="prc";
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
   columns =  ["S.No","Case ID","Case Type","Case Reason","Status"]
   sortCaseColumnsBlnk=[];
   sortCaseColumns=["","caseId","caseType.caseTypesName","caseReason.caseReasonDetailsName","status.caseStatusName"]
   columnSort=[]
   caseCoulms:any=[];
   siteColumnsprc:any=[];
   siteAssetColmnBlank:any=[];
   siteAssetColumn=["S.No","Serial Number","Account Name","Part Code","Product Description"]
   sitePrcHeader=["S.No","Prc No","Type","Case Reason","Action","Case Number"];
   PRCsortArr=["","id","caseType.caseTypesName","caseReason.caseReasonDetailsName","","cd.caseId"]
   prcSortArrBlank=[];
   assetArraySort:any=[];
   date: Date;
  _cFlag:string;
  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, 
    private prcservice:PreRegCaseService,private siteservice: siteService, private loginservice: Loginservices, private assetsService: AssetsService,
    private _global:GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.siteId = pram['siteId'];
      this.showSiteDetailsResp();
      this.getLoggedInUser();
      this.actionHistoryObjectAndId.objectId = '' + this.siteId;
      this.actionHistoryObjectAndId.objectName = 'SiteDetails';
    });
    this._activatedRoute.queryParams.subscribe(pram => {
   
       this.caseCoulms=this.columns;
       this.sortCaseColumnsBlnk=this.sortCaseColumns;
       this.prcSortArrBlank=this.PRCsortArr;
       this.assetArraySort=["","serialNumber","account.name","product.productPartCode","product.pdoductDescription"]

       this.siteColumnsprc=this.sitePrcHeader;
       this.siteAssetColmnBlank=this.siteAssetColumn
       console.log(this.siteAssetColumn);
      // this._cFlag = pram['_c'];
      // pram = new HttpParams().set('case','CaseId');
      // console.log(pram.toString());
    });
    this.date = new Date()
  }
  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }

  ngOnInit() {
    // this.showSiteDetailsResp()
  }
  getServiceApointment(column: string, order: string) {
    this.spinner.show();
    this.siteservice.getServiceAppointmentBySiteId(this.siteId, this.pageserv, column, order).subscribe(data => {
      this.spinner.hide();
      this.serviceappoint = data.content;
      this.total_service = data.totalElements;
      this.firstserv = data.first;
      this.lastserv = data.last;
      this.sizeserv = data.size;
      this.serialNumberserv = (((this.pageserv) * this.sizeserv));
    })
  }
  
  getAssetBySiteid() {
    this.spinner.show();
    this.siteservice.getAssetBySiteId(this.siteId,`this.page=`+this.pageAsset+`&size=5`, ).subscribe(data => {
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
    this.spinner.show();
    this.siteservice.getDetailBysiteId(this.siteId).subscribe(data => {
      this.spinner.hide()
      this.resp = data

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
 
  showSiteDetailsResp() {
    this.spinner.show();
    this.siteservice.getDetailBysiteId(this.siteId).subscribe(data => {
      this.spinner.hide()
      this.resp = data
      this.siteId=data.siteId;
      this.serviceCenterId=data.serviceCenterId;
     
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

  getCase(column: string, order: string) {
    this.spinner.show();
    const size=5;
    this.siteservice.getCaseBySiteId(this.siteId, this.page, column,size, order).subscribe(data => {
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
      this.popupSuccessModel.show()
      setTimeout(() => {
      this.popupSuccessModel.hide();
      }, 2000
    );
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
    this.getAssetBySiteid();
  }

  setPageAst(event: any) {
    event.preventDefault();
    this.getAssetBySiteid();
  }

  setPageNextAst(event: any) {
    event.preventDefault();
    this.pageAsset = this.pageAsset + 1;
    this.getAssetBySiteid();
  }

  onSiteCreated($event: SiteDetailsResponse) {
    //  console.log('event',$event);
    this.resp = $event
    this.showSiteDetailsResp();
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
      this.getAssetBySiteid();
      this.getCase(this.sortColumnName, this.sortOrder);
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
      this.getPrcBysite();
      // this.getContacts();
    }
    return false;
  }

  cancelData() {
    this.editSiteModel.hide();

  }

  editSiteModelshow(res: SiteDetailsResponse) {
    this.isEditMode = true;
    this.editSiteModel.show();
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.spinner.show();
    this.siteservice.getDetailBysiteId(this.siteId).subscribe(data => {
      this.spinner.hide()
      this.siteres = data

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


  myFunction() {
    window.print();

  }
  cancelDataservice() {
    // this.createServiceAppointmentModel.hide()
  }
  onServiceAppointmentCreated($event) {
    this.serviceappoint.push($event)
    this.getAssetBySiteid();
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  this.getCase(this.sortColumnName, this.sortOrder);
  }
  onAssetCreated($event) {
    this.asset.push($event);
    this.getAssetBySiteid();
    this.getServiceApointment(this.sortColumnName, this.sortOrder);
  this.getCase(this.sortColumnName, this.sortOrder);
  }
  onCaseCreated($event: CaseResponse) {
    this.message = 'Created Successfully';
    this._global.alert({ type: '', message: this.message });
    if (!this.isEditMode) {
      this.casedetails.unshift($event);
      // this.totalCase=this.totalCase+1;
    } else {
      let index = 0;
      this.casedetails.forEach(element => {

        if (element.caseId == $event.caseId) {
          this.casedetails[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.getAssetBySiteid();
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
    this.getCase(this.sortColumnName, this.sortOrder);
  }
  editserviceModelshow(service: ServiceAppointmentResponse) {
    this.serviceAppointmentResponse = service
    this.isEditMode = true;
    this.heading = "Edit Service Appointment";
    this.createServiceAppointmentModel.show()
  }

  editassetModelshow(assetresp: AssetsResponse) {
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
        this.siteservice.getDetailBysiteId(this.resp.siteId).subscribe(data => {
          this.spinner.hide();
          this.res.siteId = data.siteId;
          this.res.siteName = data.siteName;
          this.res.siteManualId = data.siteManualId;
          this.res.serviceCenterId = data.serviceCenterId;
          this.serviceCenterId=data.serviceCenterId;
          this.res.serviceCenterName = data.serviceCenterName;
          this.res.serviceResourceId = data.serviceResourceId;
          this.siteseviceResourceID=data.serviceResourceId;
          this.res.serviceResourceName = data.serviceResourceName;
        },error=>{
          this.spinner.hide()
          this.message=error.error.message;
          this.popupSuccessModel.show()
          setTimeout(() => {
          this.popupSuccessModel.hide();
          }, 2000
        );
        })

    // this.res.caseReasonVerticalId = verticalId;
 this.createCaseModel.show();
     
    
   
  }

  cancelDatacase() {
    this.createCaseModel.hide();
     this.res = new CaseResponse();
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
  this.getAssetBySiteid();
      this.getCase(this.sortColumnName, this.sortOrder);
      this.getServiceApointment(this.sortColumnName, this.sortOrder);
      this.getPrcBysite();
}
prcdata:Page<PreRegCaseResponse>=new Page<PreRegCaseResponse>()
prcserialNumber:any;
getPrcBysite(){
  this.spinner.show();
  this.prcdata.content=[];
  const size=5;
  this.prcservice.getPRCBySite(this.siteId,`page=`+this.page+`&size=`+size).subscribe(data=>{
  this.spinner.hide();
  this.prcdata=data;
  this.prcserialNumber=(((this.page)*data.size));
  })
}
}
