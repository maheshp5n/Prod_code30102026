import { OppertunityForecastCategory, OppertunityRequest, OppertunityResponse, OppertunityStage, Page } from './../../services/model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { OpportunityService } from '../../services/Opportunity.services';
import { Loginservices } from '../../services/login.services';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';
import { DatePipe } from '@angular/common';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';

@Component({
  selector: "app-Opportunities",
  templateUrl: './opportunities.component.html',
  styleUrls: ['./opportunities.component.css']
})
export class OpportunitiesComponent implements OnInit {
  
  popupNgClass: string = 'alert alert-danger center center-block';
  //filter
  today = new Date();
   dateCustomClasses: DatepickerDateCustomClasses[];
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
    sortname: 'createDate',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Opportunity Name',
      sortColumnName: 'ownerDetails.firstName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Account Name',
      sortColumnName: 'accounts.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Stage',
      sortColumnName: 'oppertunityStage.oppertunityStageName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Close Date',
      sortColumnName: 'closeDate',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Opportunity Owner Name',
      sortColumnName: 'ownerDetails.firstName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Forecast Category',
      sortColumnName: 'forcastCategory.oppertunityForecastCategoryName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Amount',
      sortColumnName: 'ammount',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Created Date',
      sortColumnName: 'createDate',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];

  //done
  constructor(private pagefilterservice:PagefilterService,private _opportunityService: OpportunityService,private _globa:GlobalserviceService, private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService, 
    private loginService: Loginservices,private datepipe:DatePipe) {
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

  @ViewChild('popupSuccessModel')
  popupSuccessModel: ModalDirective;
  @ViewChild('createNewOpportunity')
  createNewOpportunity: ModalDirective;

  isOpportunityOpen: boolean;
  stage: string;
  isEditMode: boolean = false;
  heading: string;
  CREATE_Opportunity_HEADING: string = 'New Opportunity';
  EDIT_Opportunity_HEADING: string = 'Edit Opportunity';
  isSelected = false;
  reverse: boolean = false;
  recentlyView: boolean = true;
  searchitem: string = '';
  ngClassClosedLost: string;
  ngClassClosedWon: string;
  ngClassOppoPipeline: string;
  ngClassQuote: string;
  recently: boolean = true;
  ngClassRelated: string = 'dropdown-item dropdown-item-checked';
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  message: string;
  opportunityId;
  opportunityList: Page<OppertunityResponse> =new Page<OppertunityResponse>();

  newOpportunity: OppertunityRequest = new OppertunityRequest();
  i: number;
  _cFlag: string;

  ngOnInit() {
    this.getStageList();
    this.getforcasetCat();
  }


  cancelData() {
    this.createNewOpportunity.hide();
    this.isEditMode = false;
    this.heading = this.CREATE_Opportunity_HEADING;
    this.newOpportunity = new OppertunityRequest();
  }

  onClickEdit(oppo) {
    this.spinner.show();
    this.isEditMode = true;
    this.heading = this.EDIT_Opportunity_HEADING;
    this.newOpportunity.oppertunityId = oppo.oppertunityId;
    this.newOpportunity.oppertunityNumber = oppo.oppertunityNumber;
    this.newOpportunity.caseId = oppo.caseId;
    // this.newOpportunity.caseNumber = oppo.caseNumber;
    this.newOpportunity.caseManualId = oppo.caseNumber;

    this.newOpportunity.accountName = oppo.accountName;
    this.newOpportunity.oppertunityStageId = oppo.stageId;
    this.newOpportunity.oppertunityTypeId = oppo.typeId;
    this.newOpportunity.caseManualId = oppo.caseNumber;
    this.newOpportunity.forcastCategoryId = oppo.forcastCategoryId;
    this.newOpportunity.accountId = oppo.accountId;
    this.newOpportunity.probability = oppo.probability;
    this.newOpportunity.ammount = oppo.ammount;
    this.newOpportunity.closeDate = oppo.closeDate;
    this.newOpportunity.expectedRevenue = oppo.expectedRevenue;
    // this.newOpportunity.createdByFirstName = oppo.createdByFirstName;
    // this.newOpportunity.createdByMiddleName = oppo.createdByMiddleName;
    // this.newOpportunity.createByLastName = oppo.createByLastName;
    // this.newOpportunity.createdDate = oppo.createdDate;
    // this.newOpportunity.modifiedByFirstName = oppo.modifiedByFirstName;
    // this.newOpportunity.modifiedByMiddleName = oppo.modifiedByMiddleName;
    // this.newOpportunity.modifiedByLastName = oppo.modifiedByLastName;
    // this.newOpportunity.modifiedDate = oppo.modifiedDate;
    this.heading = this.EDIT_Opportunity_HEADING;
    this.spinner.hide();
    this.createNewOpportunity.show();
  }

  getRecentOpportunities() {
    this.spinner.show();
    this.opportunityList.content=[];
    this._opportunityService.getRecentOpportunities(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.opportunityList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  onOpportunityCreated($event: OppertunityResponse) {
    if (!this.isEditMode) {
      this.opportunityList.content.unshift($event);
    } else {
      let index = 0;
      this.opportunityList.content.forEach(element => {
        if (element.oppertunityId === $event.oppertunityId) {
          this.opportunityList[index] = $event;
        } else {
          index++;
        }
      });
    }
  }

  // fetchData(stage) {
  //   if (this.recentlyView) {
  //     this.getRecentOpportunities();
  //   } else {
  //     this.getRecords();
  //   }
  // }

  getRecords() {
    this.spinner.show();
    this.opportunityList.content=[];
    this._opportunityService.getRecords(`stage=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.opportunityList = data;
      // this.first = data.first;
      // this.last = data.last;
      // this.size = data.size;
      // this.serialNumber = ((this.page) * this.size);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });
  }

  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('Opportunities').subscribe(data => {
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
 
      this.getRecords();
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
        this.getRecentOpportunities();
      }
      else
     { 
       this.getRecords();
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
        this.getRecentOpportunities();
      }
      else
     { 
       this.getRecords();}
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
  // getRecordsForForecast() {
  //   this.spinner.show();
  //   this._opportunityService.getRecordsForForecast(`stage=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
  //     this.opportunityList = data;
  //     this.first = data.first;
  //     this.last = data.last;
  //     this.size = data.size;
  //     this.serialNumber = ((this.page) * this.size);
  //     this.spinner.hide();
  //   }, err => {
  //     this.spinner.hide();
  //     this.message = err.error.message;
  //     this.popupSuccessModel.show();
  //   });
  // }

  popupClose() {
    this.popupSuccessModel.hide();
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.opportunityList.totalPages< pagenumber){
    this.message="Enter Valid input";
this.popupSuccessModel.show();
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getRecords();
}
}
searchContent={
  manualId:'',
  account:'',
  stage:'',
  forecastCategory:'',
  owner:'',
  closedDate:''
 
};
resetsearch(){
  this.searchContent.manualId=''
  this.searchContent.account=''
  this.searchContent.stage=''
  this.searchContent.forecastCategory=''
  this.searchContent.owner=''
  this.searchContent.closedDate=''
}
getAdvanceSearchresult(){
  
  if(this.searchContent.manualId=="" &&  this.searchContent.manualId==''&&
  this.searchContent.account=='' &&
  this.searchContent.stage=='' &&
  this.searchContent.forecastCategory==''&&
  this.searchContent.owner=='' && this.searchContent.closedDate=='')
    {
      this._globa.alert({ type: 'alert', message: "Please Enter valid Input to Search" });
    }
    else{
    
      if(this.searchContent.closedDate!='' ){
        this.searchContent.closedDate = this.datepipe.transform(this.searchContent.closedDate, 'yyyy-MM-dd');
       
      }
    
      this.spinner.show();
      this._opportunityService.getSearchResult(this.searchContent).subscribe(data=>{
        this.spinner.hide();
        this.opportunityList.content=data;
        // this.siteList.first=true;
        // this.siteList.last=true;
      },error=>{
        this.spinner.hide();
      });
    }

}
stageList:Array<OppertunityStage>=[];
getStageList(){
  this._opportunityService.getOppertunityStage().subscribe(data=>{
    this.stageList=data;
  })
}
forCastcat:Array<OppertunityForecastCategory>=[];
getforcasetCat(){
  this._opportunityService.getOppertunityForecastCategory().subscribe(data=>{
    this.spinner.hide();
    this.forCastcat=data;
  })
}

}


