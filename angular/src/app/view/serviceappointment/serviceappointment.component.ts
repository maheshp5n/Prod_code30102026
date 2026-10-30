import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { CaseReasonForList, CaseTypeForList, ClaimStatus, Page, SAAdvanceSearchData, SAStatus, ServiceAppointmentResponse } from '../../services/model';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';
import { IfStmt } from '@angular/compiler';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { DatePipe } from '@angular/common';
import { caseService } from '../../services/caseService.service';

@Component({
  selector: 'app-serviceappointment',
  templateUrl: './serviceappointment.component.html',
  styleUrls: ['./serviceappointment.component.scss']
})
export class ServiceappointmentComponent implements OnInit {

  @ViewChild('createServiceAppointmentModel', { static: false }) createServiceAppointmentModel: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  advanceSearchResult:SAAdvanceSearchData=new SAAdvanceSearchData();
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  loginData;
  searchitem: string = '';
  recentlyView: boolean = false;
  related: boolean = false;

  allView: boolean = true;
  all: boolean = true;

  claimed: boolean = false;
  claimView: boolean = false;
  claimStatus: string = 'Claimed';

  postedInSapView: boolean = false;
  postedinsap: boolean = false;
  postedStatus: string = 'Posted in SAP';
  approvedView:boolean=false;
  approvedStatus:string='Approved';
  rejectView:boolean=false;
  rejectStatus:string='Rejected';

  tobeClaimView: boolean = false;
  tobeClaimed: boolean = false;
  tobeClaimedStatus: string = 'To be Claimed';

  open: boolean = false;
  openView: boolean = false;
  opneStatus: string = 'Open Service Appointments';

  closeView: boolean = false;
  close: boolean = false;
  closeStatus: string = 'Close Service Appointments';

  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  ngClassClaimed: string;
  ngClassClosed: string;
  ngClassOpen: string;
  ngClassPostinSap: string;
  ngClassTobeClaimed: string;
  ngClassApproved:string;
  ngClassRejected:string;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  caseReasonForList: Array<CaseReasonForList> = [];
  sastatusList:Array<SAStatus>=[]
  claimstatuslist:Array<ClaimStatus>=[];
  caseTypeList: Array<CaseTypeForList> = [];
  CREATE_ACCOUNT_HEADING: string = 'New Service Appointment';
  EDIT_ACCOUNT_HEADING: string = 'Edit Service Appointment';

  page: number = 0;
  i: number;

  message: string = '';
  appointmentList: Page<ServiceAppointmentResponse> = new Page<ServiceAppointmentResponse>();
  serviceAppointmentResponse: ServiceAppointmentResponse = new ServiceAppointmentResponse();
  popupNgClass: string = 'alert alert-danger center center-block';
  isModelShow: boolean = false;
  _cFlag: string;
  dropdownsList = [{
    sqId: 1,
    name: 'All ',
    clickEventParam: 'All',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  
  {
    sqId: 2,
    name: 'Recently viewed',
    clickEventParam: 'Recently viewed',
    class: 'dropdown-item',
    pined: false
  },
  ];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'createDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Appointment Number',
      sortColumnName: 'appointmentNumber',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Site ID',
      sortColumnName: 'caseDetails.assets.site.manualSiteId',
      icon: ''
    },
    {
      id: 6,
      name: 'Site Name',
      sortColumnName: 'caseDetails.assets.site.siteName',
      icon: ''
    },
    {
      id: 9,
      name: 'Asset Serial Number',
      sortColumnName: 'caseDetails.assets.serialNumber',
      icon: ''
    },
    {
      id: 3,
      name: 'Case Number',
      sortColumnName: 'caseDetails.manualId',
      icon: ''
    },
    {
      id: 4,
      name: 'Case Type',
      sortColumnName: 'caseDetails.caseType.caseTypesName',
      icon: ''
    },
    {
      id: 5,
      name: 'Case Reason',
      sortColumnName: 'caseDetails.caseReason.caseReasonDetailsName',
      icon: ''
    },
   
    {
      id: 7,
      name: 'Circle',
      sortColumnName: 'circle.circleName',
      icon: ''
    },
    {
      id: 8,
      name: 'Contact',
      sortColumnName: 'caseDetails.contactPerson.firstName',
      icon: ''
    },
  
    {
      id: 10,
      name: 'Status',
      sortColumnName: 'status.statusName',
      icon: ''
    },
    {
      id: 11,
      name: 'Date Of Visit',
      sortColumnName: 'visitDate',
      icon: ''
    },
    {
      id: 12,
      name: 'Claim Status',
      sortColumnName: 'claimStatus.claimStatusName',
      icon: ''
    },
    {
      id: 13,
      name: 'Service Resource',
      sortColumnName: 'serviceResource.name',
      icon: ''
    },
  
    {
      id: 14,
      name: 'Created Date',
      sortColumnName: 'createDate',
      icon: ''
    },
    {
      id: 15,
      name: 'Wcc Created',
      sortColumnName: 'validityDays',
      icon: ''
    },
   // {
    //   id: 7,
    //   name: 'Expiry Date',
    //   sortColumnName: 'expiryDate',
    //   icon: ''
    // }, {
    //   id: 8,
    //   name: 'Status',
    //   sortColumnName: 'quoteStatus.name',
    //   icon: ''
    // }
  ];
  pindtselection = false;
  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService,private _activatedRoute: ActivatedRoute, private _serviceAppointment: ServiceAppointmentService, private loginService: Loginservices,
    private _global:GlobalserviceService,private datePipe:DatePipe,
    private case_service:caseService) {
    this.advanceSearchResult.show=false;
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.size = Number(param['size']);
      }
      this.getFilterList();
    });
  }

  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.appointmentList.content=[];
    this.getCaseTypeList();
    this.getCaseReson();
    this.getStatusForList();
    this.getClaimStatusForList();
    // this.getAllServiceAppointMent(this.sortColumnName, this.sortOrder);
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('serviceappointment').subscribe(data => {
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.dropdownSelected.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.dropdownSelected.name = d.filterName;
            this.dropdownSelected.filtreparam = d.filterParam;
            this.dropdownSelected.sqId = d.userSummaryPageFilterId;
            this.pindtselection = true;
            this.dropdownSelected.page = 0;
          }
 
          else {
            if (d.sqId == this.dropdownSelected.sqId) {
              this.pindtselection = true;
            }
          }
 
        }
        this.dropdownsList.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }
 
      this.getAllServiceAppointMent();
    });
  }
  dropdownChange(sqId) {
   this.dropdownsList.forEach(item => {
     if (item.sqId == sqId) {
       item.class = 'dropdown-item dropdown-item-checked';
       this.dropdownSelected.sqId = sqId;
       this.dropdownSelected.name = item.name;
       this.dropdownSelected.filtreparam = item.clickEventParam;
       console.log(this.dropdownSelected.filtreparam);
       this.dropdownSelected.page = 0;
       if(this.dropdownSelected.filtreparam=='all'){
         this.getAllServiceAppointMent();
       }
      else if(this.dropdownSelected.filtreparam=='related'){
         this.getRecentlyViewed();
       }
       else if(this.dropdownSelected.filtreparam=='open'){
         this.getServiceAppointmentListByStatus();
       }
       else if(this.dropdownSelected.filtreparam=='close'){
         this.getServiceAppointmentListByStatus();
       }
       else
      { 
        this.getServiceAppointmentListByClaimStatus();}
      
       if (item.pined) {
         this.pindtselection = true;
       } else {
         this.pindtselection = false;
       }
     } else {
       item.class = 'dropdown-item';
     }
   });
 }
 getSortOrder(tblId) {
   this.requestTableHeaders.forEach((tbl) => {
     if (tbl.id == tblId) {
       this.dropdownSelected.sortname = tbl.sortColumnName;
       this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
       tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
       this.dropdownSelected.page = 0;
       if(this.dropdownSelected.filtreparam=='all'){
        this.getAllServiceAppointMent();
      }
     else if(this.dropdownSelected.filtreparam=='related'){
        this.getRecentlyViewed();
      }
      else if(this.dropdownSelected.filtreparam=='open'){
        this.getServiceAppointmentListByStatus();
      }
      else if(this.dropdownSelected.filtreparam=='close'){
        this.getServiceAppointmentListByStatus();
      }
      else
     { 
       this.getServiceAppointmentListByClaimStatus();}
     } else {
       tbl.icon = '';
     }
   });
 }
  pinFilter() {
    this.spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this.spinner.hide();
    }, error => this.spinner.hide());
  }

  getAllServiceAppointMent() {
    this.spinner.show();
    this.appointmentList.content=[];
    this._serviceAppointment.getAllServiceAppointMent(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;
      // this.spinner.hide()
      // this.totalPages = data.totalPages;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  

  getRecentlyViewed() {
    this.spinner.show();
    this.appointmentList.content=[];
    this._serviceAppointment.getRecentServiceAppointMent(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;
      // this.totalPages = data.totalPages;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  getServiceAppointmentListByClaimStatus() {
    this.spinner.show();
    this.appointmentList.content=[];
    this._serviceAppointment.getServiceAppointmentListByClaimStatus(`claimstatus=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.appointmentList = data;
      // this.totalPages = data.totalPages;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));

    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }
  getServiceAppointmentListByStatus() {
    this.spinner.show();
    this.appointmentList.content=[];
    this._serviceAppointment.getServiceAppointmentListByStatus(`status=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      if(data!=null)
      {
      this.appointmentList = data;}
      else{
        this.appointmentList.content=[];
      }
      // this.totalPages = data.totalPages;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = (((this.page) * this.size));

      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

 

  showCreateServiceAppointmentView() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createServiceAppointmentModel.show();
  }

  createServiceAppointmentModelhide() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.createServiceAppointmentModel.hide();
  }

  createEditModelshow(servicAppointmentResponse: ServiceAppointmentResponse) {
    this.spinner.show();
    this._serviceAppointment.getServiceAppointmentById(servicAppointmentResponse.serviceAppointmentId).subscribe(data => {
      this.serviceAppointmentResponse = data;
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createServiceAppointmentModel.show();
  }

  cancelData() {
    this.serviceAppointmentResponse = new ServiceAppointmentResponse();
    this.createServiceAppointmentModel.hide();
  }




  onServiceAppointmentCreated($event: ServiceAppointmentResponse) {
    if (!this.isEditMode) {
      this.appointmentList.content.unshift($event);
    } else {
      let index = 0;
      this.appointmentList.content.forEach(element => {
        if (element.serviceAppointmentId === $event.serviceAppointmentId) {
          this.appointmentList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  popupClose() {
    this.popupSuccessModel.hide();
  }
  resetsearch(){
    this.advanceSearchResult.assets=undefined;
    this.advanceSearchResult.casenumber=undefined;
      this.advanceSearchResult.circle=undefined 
       this.advanceSearchResult.crdf=undefined
      this.advanceSearchResult.crdt=undefined 
       this.advanceSearchResult.creason=''
      this.advanceSearchResult.cstatus=''
      this.advanceSearchResult.ctype=''
      this.advanceSearchResult.sanumber==undefined
      this.advanceSearchResult.site=undefined 
      this.advanceSearchResult.srs=undefined 
       this.advanceSearchResult.status=undefined
  }
  getAdvanceSearchResult(){
      
       if(this.advanceSearchResult.assets==undefined){
        this.advanceSearchResult.assets='';
       }
        if(this.advanceSearchResult.casenumber==undefined){
        this.advanceSearchResult.casenumber='';
       }
       if(this.advanceSearchResult.circle==undefined){
        this.advanceSearchResult.circle='';
       }
       if(this.advanceSearchResult.crdf==undefined){
        this.advanceSearchResult.crdf='';
       }
       if( this.advanceSearchResult.crdt==undefined){
        this.advanceSearchResult.crdt='';
       }
     
       if(this.advanceSearchResult.cstatus==undefined){
        this.advanceSearchResult.cstatus='';
       }
       if(this.advanceSearchResult.ctype==undefined){
        this.advanceSearchResult.ctype='';
       }
       if(this.advanceSearchResult.sanumber==undefined){
        this.advanceSearchResult.sanumber='';
       }
       if(this.advanceSearchResult.site==undefined){
        this.advanceSearchResult.site='';
       }
       if(this.advanceSearchResult.srs==undefined){
        this.advanceSearchResult.srs='';
       }
       if(this.advanceSearchResult.status==undefined){
        this.advanceSearchResult.status='';
       }

    if(this.advanceSearchResult.assets=='' && this.advanceSearchResult.casenumber=='' &&
      this.advanceSearchResult.circle=='' && this.advanceSearchResult.crdf=='' &&
      this.advanceSearchResult.crdt=='' && this.advanceSearchResult.creason=='' &&
      this.advanceSearchResult.cstatus=='' && this.advanceSearchResult.ctype=='' &&
      this.advanceSearchResult.sanumber=='' &&this.advanceSearchResult.site=='' &&
      this.advanceSearchResult.srs=='' && this.advanceSearchResult.status==''){
        this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    }
  
  
    else{
      if(this.advanceSearchResult.crdf!=''){
        this.advanceSearchResult.crdf = this.datePipe.transform(this.advanceSearchResult.crdf, 'yyyy-MM-dd');
      }
    
      if(this.advanceSearchResult.crdt!='' ){
        this.advanceSearchResult.crdt= this.datePipe.transform(this.advanceSearchResult.crdt, 'yyyy-MM-dd');
       
      }
      this.spinner.show();
      this._serviceAppointment.getAdvanceSearchResult(this.advanceSearchResult).subscribe(data=>{
        this.spinner.hide();
        this.appointmentList.content=data;
        this.appointmentList.first=true;
        this.appointmentList.last=true;
      },error=>{
        this.spinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
   
  }

  encodeurlc(urlc):string{
    return encodeURIComponent(urlc);
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.appointmentList.totalPages< pagenumber){
  this.message="Enter Valid Input";
  this.popupSuccessModel.show();
  //  this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
   this.getAllServiceAppointMent()
    // this.serialNumber = (((this.dropdownSelected.page) * this.size));
  }
}

getCaseTypeList(){
  this.caseTypeList=[];
  this.case_service.getCaseTypeForlist().subscribe(data=>{
    const temp=[];
   for(let i=0;i<data.length;i++)
   {
   let lis=new CaseTypeForList();
   lis.caseTypeName=data[i]
   temp.push(lis);
   this.caseTypeList=temp;
   }
  });
}
getCaseReson(){
  this.caseReasonForList=[];
  this.case_service.getCaseReasonAdvFORList().subscribe(data=>{
    const temp=[];
    for(let j=0; j<data.length;j++){
      let lis=new CaseReasonForList;
      lis.caseReasonName=data[j]
      temp.push(lis);
      this.caseReasonForList=temp;
    }
    
      

  })
}
getStatusForList(){
  this._serviceAppointment.getStatusForList().subscribe(data=>{
    const temp=[]
      for(let j=0; j<data.length;j++){
      let lis=new SAStatus;
      lis.statusname=data[j]
      temp.push(lis);
      this.sastatusList=temp;
    }  })
}
getClaimStatusForList(){
  this.claimstatuslist=[];
  this._serviceAppointment.getClaimStatus().subscribe(data=>{
  this.claimstatuslist=data;
  })
}
}
