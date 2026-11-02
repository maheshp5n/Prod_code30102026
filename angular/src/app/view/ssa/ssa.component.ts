import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ssaService } from '../../services/ssa.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Loginservices } from '../../services/login.services';
import { OrderPipe } from 'ngx-order-pipe';
import { Page, SSAResponse } from '../../services/model';
import { GlobalserviceService } from '../../globalservice.service';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-ssa',
  templateUrl: './ssa.component.html',
  styleUrls: ['./ssa.component.scss']
})
export class SsaComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createSSAModel') createSSAModel: ModalDirective;

  recentlyView: boolean = true;
  searchitem: string = '';
  ngClassAll: string='dropdown-item dropdown-item-checked';
  recently: boolean = true;
  ngClassRelated: string;
  related: boolean = true;
  datainput: boolean = false
  ngClassTable: string = 'fa fa-check';
  values: string;
SSATableList:Page<SSAResponse>=new Page<SSAResponse>();
  isVisible: boolean = false;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'Create SSA ';
  EDIT_ACCOUNT_HEADING: string = 'Edit SSA '
  order: string = 'ssaName'; // first column variable
  reverse: boolean = false;
  page: number = 0;
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size: number;
  showFilter:boolean=false
  showHide:boolean=false
  sortColumnName: string = 'createdDate'; // first column variable
  sortOrder: string = 'desc';
  sortColumn: number = 3;
  columnVisibility: Array<boolean> = [false, false, false, false];
  columnSort: Array<boolean> = [false, false, false, true];
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  i:any
  message:string="";
  loginInfo:any;
  ssaId:number;
  domainId: number;
  popupNgClass: string = 'alert alert-danger center center-block';
  ssares: SSAResponse=new SSAResponse();
  _cFlag:string;
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
      name: ' Name',
      sortColumnName: 'name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: ' Circle',
      sortColumnName: 'circle.circleName',
      icon: 'fa fa-angle-down pull-right'
    },
    
    
    {
      id: 3,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    },
   
  ];

  //done
  constructor(private pagefilterservice:PagefilterService,private _gloabl:GlobalserviceService,private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,private loginservice: Loginservices,private orderPipe: OrderPipe,private ssaservice:ssaService) {  
   
    this._activatedRoute.params.subscribe(pram => {
      this.ssaId = pram['siteId'];
    
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
    this.SSATableList = orderPipe.transform(this.SSATableList, this.order);
   }
   rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
   ngOnInit() {
    // this.getSSA(this.sortColumnName, this.sortOrder)
    this.loginInfo = this.loginservice.getLoggedInUser();
    this.domainId = this.loginInfo.emptype['businessDomainId']
  
    
  }
  pindtselection = false;
getFilterList() {
  this.pagefilterservice.getPagefilter('ssa').subscribe(data => {
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

    this.getSSA();
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
  this.getSSA();
    
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
    this.getSSA();
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
  getSSA(){
    this.spinner.show();
    this.SSATableList.content=[];
 this.ssaservice.getSSA(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
   this.spinner.hide();
   this.SSATableList=data
  //  this.first=data.first;
  //  this.last=data.last;
  //  this.serialNumber = (((this.page) * data.size));
 },error=>{
  this.spinner.hide();
  this.message=error.error.message;
  this.popupSuccessModel.show();
})
  }

  CreateSSA(){
    this.isEditMode=false;
    this.ssares=new SSAResponse();
    this.heading=this.CREATE_ACCOUNT_HEADING
    this.createSSAModel.show();

  }
  editSSA(ssalist:SSAResponse){
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING;
    this.createSSAModel.show();
    this.ssaservice.getSSAById(ssalist.id).subscribe(data=>{
     this.ssares=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    })
  }
  onSSACreated($event:SSAResponse){
   
    // this.SSATableList.content.unshift($event);
    if(!this.isEditMode){
      this._gloabl.alert({ type: '', message: 'Created Successfully'});
      this.SSATableList.content.unshift($event);
    }else{
      this._gloabl.alert({ type: '', message: 'Updated Successfully'});
      let index = 0;
      this.SSATableList.content.forEach(element => {
        if(element.id == $event.id){
          this.SSATableList[index]=$event;
        }else{
          index++;
        }
      });
    }
  }
  cancelSSAData(){
    this.createSSAModel.hide();
    this.ssares=new SSAResponse();
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.SSATableList.totalPages< pagenumber){
  this.message="Enter Valid Input";
   this._gloabl.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
   this.getSSA();
    // this.serialNumber = (((this.dropdownSelected.page) * this.size));
  }
}
searchContent={
  name:'',
  circle:'',
 
};
getAdvanceSearchresult(){
  
  if(this.searchContent.name=="" &&this.searchContent.circle=="")
    {
      this.message="Please Enter valid Input to Search";
      this.popupSuccessModel.show();
    }
    else{
      this.spinner.show();
      this.ssaservice.getSearchResult(this.searchContent).subscribe(data=>{
        this.spinner.hide();
        this.SSATableList.content=data;
        // this.siteList.first=true;
        // this.siteList.last=true;
      },error=>{
        this.spinner.hide();
      });
    }

}


}
