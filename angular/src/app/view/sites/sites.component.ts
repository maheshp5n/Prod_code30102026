import { Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { SitesForList,  SiteDetailsReq, SiteDetailsResponse, Page } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { siteService } from '../../services/siteService.services';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';


@Component({
  selector: 'app-sites',
  templateUrl: './sites.component.html',
  styleUrls: ['./sites.component.css']
})

export class SitesComponent implements OnInit {
  // @ViewChild(MatSort) sort:MatSort;
  @ViewChild('createSiteModel') createSiteModel:ModalDirective;

  @ViewChild('editSitesModel') editSitesModel:ModalDirective;
  @ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;

  @ViewChild('sitebulkupload') sitebulkupload:ElementRef;

  searchContent={
    site_id:'',
    site_name:'',
    infra_id:'',
    account:'',
    circle:'',
    service_center:'',
    customer:'',
    indusId:'',
    boxstate:false
  };

  message:string='';
  // @ViewChild('agGrid') agGrid: AgGridAngular;
  popupNgClass: string = 'alert alert-danger center center-block';
  sitelist:Array<SitesForList>=[];
  dataStyle:string;
  inboundClick:boolean=true;
  showHide:boolean=false;
  showFilter:boolean=false;
  res: SiteDetailsResponse = new SiteDetailsResponse();
  sortDisplay: boolean = true;
  table:boolean=true;
  req: SiteDetailsReq = new SiteDetailsReq();
  siteList: Page<SiteDetailsResponse> = new Page<SiteDetailsResponse>()
  isEditMode:boolean=false;
  heading : string;
  edit:boolean=false;
  CREATE_ACCOUNT_HEADING:string= 'Create Site ';
  EDIT_ACCOUNT_HEADING:string= 'Edit Site '
  order: string = 'siteId'; // first column variable
  reverse: boolean = false;
  ngClassAll: string='dropdown-item dropdown-item-checked';
  ngClassRelated: string;
  contactPage: Page<SiteDetailsResponse> = new Page<SiteDetailsResponse>();
  page: number = 0;
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size:number;
  recentUser:boolean=false
  siteId:number;
  related:boolean=false
  recentlyView: boolean = false;
  editRowId:boolean=false;
  loginData:any
  searchitem:string
  
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
  {
    sqId: 2,
    name: 'Recently Viewed',
    clickEventParam: 'recentvisit',
    class: 'dropdown-item',
    pined: false
  },
  
 
 ];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: ' Site ID',
      sortColumnName: 'siteId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Site Name',
      sortColumnName: 'siteName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Infra ID',
      sortColumnName: 'infraId',
      icon: ''
    },
    {
      id: 10,
      name: 'Indus ID',
      sortColumnName: 'indusId',
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
      sortColumnName: 'circle.circleName',
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
      sortColumnName: 'clusterDistrict',
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
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];

  //done
  _cFlag:string;
 constructor(private pagefilterservice:PagefilterService,
    private spinner: NgxSpinnerService,
    private siteservice:siteService,
     private _activatedRoute: ActivatedRoute,
     private loginservice: Loginservices,private orderPipe: OrderPipe,private _global:GlobalserviceService) {  
   
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
  // this.siteList = orderPipe.transform(this.siteList, this.order);
 }
 rights(object,right:string):boolean{
  return this.loginservice.isUserValidFor(object,right);
}
pindtselection = false;
getFilterList() {
  this.pagefilterservice.getPagefilter('sites').subscribe(data => {
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

    this.getsites();
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
   if(this.dropdownSelected.filtreparam=='related'){
     this.recentVisit();
   }
   else{
     this.getsites();
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
      this.recentVisit();
    }
    else{
      this.getsites();
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

getsites(){
  this.spinner.show()
  this.siteList.content=[];
  this.siteservice.getSites(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
   this.spinner.hide();
    this.contactPage = data;
    this.siteList=data
    // this.totalPages = data.totalPages;
    // this.first = data.first;
    // this.last = data.last;
    // this.size = data.size;
    // this.serialNumber = (((this.page) * this.size));
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
  })
 
}
recentVisit(){
  this.spinner.show();
  this.siteList.content=[];
  this.siteservice.getRecentVisit(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
   
      this.spinner.hide()
   
    this.contactPage = data;
    this.siteList=data
    // this.totalPages = data.totalPages;
    // this.first = data.first;
    // this.last = data.last;
    // this.size = data.size;
    // this.serialNumber = (((this.page) * this.size));
  },error=>{
    this.spinner.hide();
  this.message=error.error.message;
  this.popupSuccessModel.show();
  })
 
}


  ngOnInit() {
    this.siteList.content=[];
    this.getLoggedInUser();
    }
    getLoggedInUser() {
      this.loginData = this.loginservice.getLoggedInUser();
    }
    

   
  
createShowModal(){
  this.res=new SiteDetailsResponse();
  this.isEditMode = false;
  this.heading =this.CREATE_ACCOUNT_HEADING;
  this.createSiteModel.show();
}
 id:number
editCityModelshow(Siteres:SiteDetailsResponse){
  this.res = Siteres;
  this.spinner.show()
  this.siteservice.getDetailBysiteId(this.res.siteId).subscribe(data=>{
    this.spinner.hide()
    this.res=data
    

  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
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
 
   onSiteCreated($event:SiteDetailsResponse){
    if(!this.isEditMode){
      this.siteList.content.unshift($event);
    }else{
      let index = 0;
      this.siteList.content.forEach(element => {
        if(element.siteId == $event.siteId){
          this.siteList[index]=$event;
        }else{
          index++;
        }
      });
    }
  }

  getAdvanceSearchresult(){
  
    if(this.searchContent.account=="" &&this.searchContent.site_id=="" && this.searchContent.site_name=="" &&
    this.searchContent.infra_id==""&&this.searchContent.service_center==""&& this.searchContent.circle=="" &&
    this.searchContent.customer=='' && this.searchContent.indusId=="")
      {
        this.message="Please Enter valid Input to Search";
        this.popupSuccessModel.show();
      }
      else{
        this.spinner.show();
        this.siteservice.getAdvanceSearchResult(this.searchContent).subscribe(data=>{
          this.spinner.hide();
          this.siteList.content=data;
          this.siteList.first=true;
          this.siteList.last=true;
        },error=>{
          this.spinner.hide();
        });
      }
 
  }

  onBulkUploadchange($event){
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this.siteservice.uploadBulkData(fd).subscribe(data=>{
      this.sitebulkupload.nativeElement.value='';
      this.spinner.hide();
      this.downLoadFile(data);

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
      alert('Please disable your Pop-up blocker and try again.');
    }
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.siteList.totalPages< pagenumber){
    this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getsites();
}
}
ResetSearchresult(){
  this.searchContent.site_id='';
  this.searchContent.site_name='';
  this.searchContent.infra_id='';
  this.searchContent.account='';
  this.searchContent.circle='';
  this.searchContent.service_center='';
  this.searchContent.customer='';
  this.searchContent.indusId='';

}
}
