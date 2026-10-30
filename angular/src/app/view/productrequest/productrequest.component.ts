import { PagefilterService } from './../../services/pagefilterservice';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';
import { LocationProductRequestResponse, LPRStatus, Page } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { GlobalserviceService } from '../../globalservice.service';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-productrequest',
  templateUrl: './productrequest.component.html',
  styleUrls: ['./productrequest.component.scss']
})
export class ProductrequestComponent implements OnInit {
  @ViewChild('sureModel',{static:false})sureModel:ModalDirective;
  prstatusli:Array<LPRStatus>=[]
  dropdownsList = [{
    sqId: 1,
    name: 'All PR',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Open PR',
    clickEventParam: 'open',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Receipt Pending',
    clickEventParam: 'receiptpending',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 5,
    name: 'Sent Request',
    clickEventParam: 'byme',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 6,
    name: 'Received Request',
    clickEventParam: 'forme',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 7,
    name: 'Draft',
    clickEventParam: 'Draf',
    class: 'dropdown-item',
    pined: false
  }];


  dropdownSelected = {
    sqId: 1,
    name: 'All PR',
    filtreparam: 'all',
    sortname: 'locationProductRequestId',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Product Request Number',
      sortColumnName: 'locationProductRequestId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Destination Warehouse',
      sortColumnName: 'destinationLocation.locationName',
      icon: ''
    },
    {
      id: 3,
      name: 'Source Warehouse',
      sortColumnName: 'sourceLocation.locationName',
      icon: ''
    },
    {
      id: 4,
      name: 'Status',
      sortColumnName: 'locationProductRequestStatus',
      icon: ''
    },
    {
      id: 5,
      name: 'Last Modified',
      sortColumnName: 'modifiedDate',
      icon: ''
    },
    {
      id: 6,
      name: 'Last Modified By',
      sortColumnName: 'modifiedBy.firstName',
      icon: ''
    }
    // {
    //   id: 6,
    //   name: 'Action',
    //   sortColumnName: '',
    //   icon: ''
      
    // }
  ];
  pindtselection = false;
  searchkey: string;
  productRequestList: Page<LocationProductRequestResponse> = new Page<LocationProductRequestResponse>();
  constructor(private _productRequestService: LocationProductRequestService,
    private _ngxspinner: NgxSpinnerService,
    private _pagefilter: PagefilterService,
    private _activateRoute: ActivatedRoute,private _global: GlobalserviceService,
    private _user:Loginservices
  ) {
    this._activateRoute.queryParams.subscribe(param => {
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
  loginData:any;
  rolGroup:string;
  ngOnInit() {
    this.productRequestList.content=[]
    this.getForListStatus();
    this.getLoggedInUser();
    //this.getProductRequestList(`filtername=${this.dropdownSelected.filtreparam}`, `page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
    //this.getFilterList();
  }
  getLoggedInUser() {
    this.loginData = this._user.getLoggedInUser();
    this.rolGroup = this.loginData.roleGroupAndAccount['roleGroup']
  }

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getProductRequestList();
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
        this.getProductRequestList();
      } else {
        tbl.icon = '';
      }
    });
  }
  issSortclose:boolean=false
  getProductRequestList() {
    this._ngxspinner.show();
    this.productRequestList.content = [];
    this._productRequestService.getProductRequest(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.productRequestList = data;
      data.content.forEach(e=>{
       
        this._productRequestService.getOwnerdetails(e.prId).subscribe(data=>{
          if(e.status!='Short Closed'){
            if(data!=null)
            {this.PROwnerRole=data.role;
            
              if(this.PROwnerRole=='DCT_MAIN_STORE' && this.rolGroup=='DCT_MAIN_STORE'){
           
                e.isShortclosed=true
            
            }
           else if(this.PROwnerRole=='SCH' && this.rolGroup=='DCT_MAIN_STORE'){
           
            e.isShortclosed=true
          
          }
            else if(this.PROwnerRole=='FIELD_SERVICE_ENGINEER' && this.rolGroup=='SCH'){
              e.isShortclosed=true
            }
        
      
            else{
              e.isShortclosed=false
            }
           
            }
          }
          else{
            e.isShortclosed=false;
          }
         
         
          })
       
    
      })


      this._ngxspinner.hide();
    }, error => this._ngxspinner.hide());
  }
  goToNext(pageNumber) {
    this.dropdownSelected.page = pageNumber + 1;
    this.getProductRequestList();
  }
  goToPrevious(pageNumber) {
    this.dropdownSelected.page = pageNumber - 1;
    this.getProductRequestList();
  }

  getFilterList() {
    this._pagefilter.getPagefilter('productrequest').subscribe(data => {
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

      this.getProductRequestList();
    });
  }
  pinFilter() {
    this._ngxspinner.show();
    this._pagefilter.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this._ngxspinner.hide();
    }, error => this._ngxspinner.hide());
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.productRequestList.totalPages< pagenumber){
    
    this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getProductRequestList();
}
}
advancesearch = {
  manualId: '',
  sourceLocation: '',
  destinationLocation: '',
  status: '',
  
};
searchPR() {
  if( this.advancesearch.manualId== ''&&
  this.advancesearch.sourceLocation== ''&&
  this.advancesearch.destinationLocation== ''&&
  this.advancesearch.status== ''){
    this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });

  }
  else{
    this._ngxspinner.show();
    this.productRequestList.content=[]
  this._productRequestService.getSearchResult(this.advancesearch).subscribe(data => {
    this._ngxspinner.hide();
    this.productRequestList.content = data;
    this.productRequestList.first = true;
    this.productRequestList.last = true;
  
  });
  }
 
}
resetsearch(){
  this.advancesearch.manualId= ''
  this.advancesearch.sourceLocation=''
  this.advancesearch.destinationLocation=''
  this.advancesearch.status=''
}

getForListStatus(){
  this._productRequestService.getPRStatusForList().subscribe(data=>{
    const temp=[];
    for(let i=0;i<data.length;i++){
      let lis =new  LPRStatus();
      lis.name=data[i];
      temp.push(lis);
      this.prstatusli=temp;
    }
  })
}
prId:number;
prManualId:string;
forPR:string;
selectedid:any=0;
onshortClose(item:LocationProductRequestResponse){
 
  this.forPR='allPR';
  this.prId=item.prId;
  this.prManualId=item.prManualId
  this.sureModel.show();
}
yesRequest(){
  console.log(this.forPR);
  this._ngxspinner.show();
  this._productRequestService.shortClose(this.prId,this.selectedid,this.forPR).subscribe(data=>{
    this._ngxspinner.hide();
    this.sureModel.hide();
    this._global.alert({ type: '', message:" Product Request has been Short Closed" });
    this.getProductRequestList();
  },error=>{
    this._ngxspinner.hide();
    this._global.alert({ type: 'alert', message:error.error.message });
  })
}
sureModelhide(){
  this.sureModel.hide();
  this.prId=null;
  this.prManualId=null;
}

PROwnerRole:string='';
  getOwners(prid){
    this._ngxspinner.show();
    this._productRequestService.getOwnerdetails(prid).subscribe(data=>{
    if(data!=null)
      {this.PROwnerRole=data.role;
      
        if(this.PROwnerRole=='DCT_MAIN_STORE' && this.rolGroup=='DCT_MAIN_STORE'){
     
          this.issSortclose=true
      
      }
     else if(this.PROwnerRole=='SCH' && this.rolGroup=='DCT_MAIN_STORE'){
     
        this.issSortclose=true
    
    }
      else if(this.PROwnerRole=='FIELD_SERVICE_ENGINEER' && this.rolGroup=='SCH'){
        this.issSortclose=true;
      }

      else{
        this.issSortclose=false
      }
     
      }
      else{
        this._global.alert({ type: 'alert', message:'This owner role group is missing'});
      }
    },error=>{
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
  
    })
  }
}
