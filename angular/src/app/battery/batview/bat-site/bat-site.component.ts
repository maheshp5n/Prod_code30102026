import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { OrderPipe } from 'ngx-order-pipe';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { Page, SiteDetailsReq, SiteDetailsResponse, SitesForList } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { siteService } from '../../../services/siteService.services';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTsiteCreateReq, GetAllBTsiteResp } from '../../bt_model';

@Component({
  selector: 'app-bat-site',
  templateUrl: './bat-site.component.html',
  styleUrls: ['./bat-site.component.css']
})
export class BatSiteComponent implements OnInit {

  btSitelistResp:Page<GetAllBTsiteResp> = new Page<GetAllBTsiteResp>();
  res: GetAllBTsiteResp = new GetAllBTsiteResp();
  req: Page<BTsiteCreateReq> = new Page<BTsiteCreateReq>()

   

  //////////////////////////////////////////////////////////////////////////////////////
  @ViewChild('createSiteModel') createSiteModel:ModalDirective;

  // @ViewChild('editSitesModel',{static:false}) editSitesModel:ModalDirective;
  // @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;

  @ViewChild('sitebulkupload') sitebulkupload:ElementRef;

  searchContent={
    // site_id:'',
    // site_name:'',
    // infra_id:'',
    // account:'',
    // circle:'',
    // service_center:'',
        // boxstate:false

    manualId:'',
    siteName:'',
    infraId:'',
    account:'',
    circle:'',
    serviceCenter:''
  };

  message:string='';
  // @ViewChild('agGrid') agGrid: AgGridAngular;
  popupNgClass: string = 'alert alert-danger center center-block';
  sitelist:Array<SitesForList>=[];
  dataStyle:string;
  inboundClick:boolean=true;
  showHide:boolean=false;
  showFilter:boolean=false;
  // ress: SiteDetailsResponse = new SiteDetailsResponse();
  sortDisplay: boolean = true;
  table:boolean=true;
  // reqs: SiteDetailsReq = new SiteDetailsReq();
  // siteList: Page<SiteDetailsResponse> = new Page<SiteDetailsResponse>()
  isEditMode:boolean=false;
  heading : string;
  edit:boolean=false;
  CREATE_ACCOUNT_HEADING:string= 'Create Site ';
  EDIT_ACCOUNT_HEADING:string= 'Edit Site '
  order: string = 'siteId'; // first column variable
  contactPage: Page<GetAllBTsiteResp> = new Page<GetAllBTsiteResp>();
  page: number = 0;
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size:number;
  recentUser:boolean=false
  siteId:number;
  related:boolean=false;
  recentlyView: boolean = false;
  editRowId:boolean=false;
  loginData:any;
  searchitem:string;
  pindtselection = false;
  i:any
  isModelOpen:boolean=false;
  //filter
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  // {
  //   sqId: 2,
  //   name: 'Recently Viewed',
  //   clickEventParam: 'recentvisit',
  //   class: 'dropdown-item',
  //   pined: false
  // },
  
 
 ];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'created_date',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Site ID',
      sortColumnName: 'manual_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Site Name',
      sortColumnName: 'site_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Infra ID',
      sortColumnName: 'infra_id',
      icon: ''
    },
    {
      id: 4,
      name: 'Account',
      sortColumnName: 'account.name',
      icon: ''
    },
    {
      id: 5,
      name: 'Circle',
      sortColumnName: 'circ.circle_name',
      icon: ''
    }, {
      id: 6,
      name: 'SSA',
      sortColumnName: 'ssa.name',
      icon: ''
    },
    {
      id: 7,
      name: 'District',
      sortColumnName: 'cluster_district',
      icon: ''
    },
    {
      id:8,
      name:'Customer',
      sortColumnName:'customer',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];

  //done
  _cFlag:string;
 constructor(private pagefilterservice:PagefilterService,
    private spinner: NgxSpinnerService,
     private _activatedRoute: ActivatedRoute,
     private loginservice: Loginservices,private orderPipe: OrderPipe,
     private _global:GlobalserviceService,private btSiteservice:BtsiteserviceService) {  
   
  this._activatedRoute.params.subscribe(pram => {
    this.siteId = pram['siteId'];
  
  });
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
  // this.btSitelistResp = orderPipe.transform(this.btSitelistResp, this.order);
 }
 rights(object,right:string):boolean{
  return this.loginservice.isUserValidFor(object,right);
}

getFilterList() {
  this.pagefilterservice.getPagefilter('bat_site').subscribe(data => {
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

    this.allBtSiteList();
  });
}
dropdownChange(sqId) {
 this.dropdownsList.forEach(item => {
   if (item.sqId == sqId) {
     item.class = 'dropdown-item dropdown-item-checked';
     this.dropdownSelected.sqId = sqId;
     this.dropdownSelected.name = item.name;
     this.dropdownSelected.filtreparam = item.clickEventParam;
     this.dropdownSelected.page = 0;
   if(this.dropdownSelected.filtreparam=='related'){
    //  this.recentVisit();
   }
   else{
    //  this.getsites();
     this.allBtSiteList();

   }
    
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
    if(this.dropdownSelected.filtreparam=='related'){
      // this.recentVisit();
    }
    else{
      this.allBtSiteList();
    }
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
  changeShowStatus(){
    
    if(this.showHide==true){                                                                      
       this.showHide=false;
    }
    else{
      this.showHide=true;
    }
  }

// getsites(){
//   this.spinner.show()
//   this.siteList.content=[];
//   this.siteservice.getSites(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
//    this.spinner.hide();
//     this.contactPage = data;
//     this.siteList=data
 
//   },error=>{
//     this.spinner.hide();
//     this.message=error.error.message;
//     this.popupSuccessModel.show();
//   })
 
// }
// recentVisit(){
//   this.spinner.show();
//   this.siteList.content=[];
//   this.siteservice.getRecentVisit(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
   
//       this.spinner.hide()
   
//     this.contactPage = data;
//     this.siteList=data
  
//   },error=>{
//     this.spinner.hide();
//   this.message=error.error.message;
//   this.popupSuccessModel.show();
//   })
 
// }


  ngOnInit() {
    this.getLoggedInUser();
  
    }

    allBtSiteList()
    {
      this.spinner.show();
      this.btSitelistResp.content=[];

      // ?filtername=All&page=0&size=20&sort=created_date,desc
      this.btSiteservice.getAllBtSiteList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`).subscribe(data=>{
        this.spinner.hide();
        this.contactPage = data;
        this.btSitelistResp=data;
      },err=>{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });

      })
    }
    ////////////////////////////////////////////////////////////////////////
    getLoggedInUser() {
      this.loginData = this.loginservice.getLoggedInUser();
    }
    

   
  
createShowModal(){
  this.res=new GetAllBTsiteResp();
  this.isEditMode = false;
  this.heading =this.CREATE_ACCOUNT_HEADING;
  this.createSiteModel.show();
}
 id:number
editCityModelshow(Siteres:GetAllBTsiteResp){
  this.res = Siteres;
  this.spinner.show()
  this.btSiteservice.getBtSitedataById(this.res.siteId).subscribe(data=>{
    this.spinner.hide()
    this.res=data
    

  },error=>{
    this.spinner.hide();
    this._global.alert({ type: 'alert', message: error.error.message });

  });
  
  this.res=Siteres
  this.isEditMode = true;
  this.heading =this.EDIT_ACCOUNT_HEADING;
  this.createSiteModel.show();
}
  
cancelData() {
  this.isEditMode=false;
  this.heading =this.CREATE_ACCOUNT_HEADING;
   this.createSiteModel.hide();
  
   }
 
   onSiteCreated($event:GetAllBTsiteResp){
    if(!this.isEditMode){
      this.btSitelistResp.content.unshift($event);
    }else{
      let index = 0;
      this.btSitelistResp.content.forEach(element => {
        if(element.siteId == $event.siteId){
          this.btSitelistResp[index]=$event;
        }else{
          index++;
        }
      });
    }
  }

  getAdvanceSearchresult(){
  
    
    // if(this.searchContent.account=="" &&this.searchContent.site_id=="" && this.searchContent.site_name=="" &&
    // this.searchContent.infra_id==""&&this.searchContent.service_center==""&& this.searchContent.circle==""  )
    if(this.searchContent.account=="" &&this.searchContent.manualId=="" && this.searchContent.siteName=="" &&
    this.searchContent.infraId==""&&this.searchContent.serviceCenter==""&& this.searchContent.circle==""  )
      {
       this._global.alert({type:'alert',message:'Please Enter Valid Input To Search'});
      }
      else{
        this.spinner.show();
        this.btSiteservice.getBtSiteAdvanceSearch(this.searchContent).subscribe(data=>{
          this.spinner.hide();
          this.btSitelistResp.content=data;
          this.btSitelistResp.first=true;
          this.btSitelistResp.last=true;
        },error=>{
          this.spinner.hide();
          this._global.alert({type:'alert',message: error.error.message});

        });
      }
 
  }

  onBulkUploadchange($event){
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this.btSiteservice.uploadBulkDataBt(fd).subscribe(data=>{
      this.sitebulkupload.nativeElement.value='';
      this.spinner.hide();
      this._global.alert({ type: '', message: data.message });

      // this.allBtSiteList();
      // this.downLoadFile(data);

    },error=>{
      this.spinner.hide();
      this.sitebulkupload.nativeElement.value='';
      this._global.alert({ type: 'alert', message: error.message });
    });
  }

  downLoadFile(data: any) {
    let blob = new Blob([data], { type: data.type });
    let url = window.URL.createObjectURL(blob);
    let pwa = window.open(url);
    if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
      alert('Please Disable Your Pop-Up Blocker And Try Again.');
    }
  }
  LimteExceed:boolean;

  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;
    
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if(this.LimteExceed==true){
      return false;
    }
    return true;
  
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    if(pagenumber!=null && pagenumber!=''){
      if(pagenumber>this.btSitelistResp.totalPages){
        this._global.alert({type:'alert',message:'Page Count Should Be Less Then Total Page'});
      }
      else{ 
        this.btSitelistResp.number=this.pagenumber-1;
       this.dropdownSelected.page=pagenumber-1;
       this.btSitelistResp.content=[];
       this.allBtSiteList();
  }

  }
  else{
       this._global.alert({type:'alert',message:'Please Enter A Valid Number'});
      }
}

resetSite(){
  //  this.searchContent.site_name=''
  // this.searchContent.site_id='' 
  // this.searchContent.infra_id=''
  // this.searchContent.account=''
  //  this.searchContent.circle=''
  // this.searchContent.service_center=''
  this.searchContent.infraId=''
  this.searchContent.siteName=''
  this.searchContent.manualId=''
  this.searchContent.account=''
  this.searchContent.circle=''
  this.searchContent.serviceCenter=''
  this.allBtSiteList();
}
}
