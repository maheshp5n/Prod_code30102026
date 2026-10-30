import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { Loginservices } from '../../services/login.services';
import { Page } from '../../services/model';
import { PagefilterService } from '../../services/pagefilterservice';
import { ServiceResourceService } from '../../services/serviceresource.services';

@Component({
  selector: 'app-engineer-rating',
  templateUrl: './engineer-rating.component.html',
  styleUrls: ['./engineer-rating.component.css']
})
export class EngineerRatingComponent implements OnInit {
  isEditMode: boolean = false;
  heading: string;
  CREATE_PRODUCT_HEADING: string = 'New Zone Details';
  EDIT_PRODUCT_HEADING: string = 'Edit Zone Details';
  isSelected = false;
  order: string = ''; // first column variable
  reverse: boolean = false;
  flag: boolean;
  serialNumber: number;
  searchitem: string = '';
  ngClassAll: string;
  ngClassRelated: string = 'fa fa-check';
  first: boolean;
  last: boolean;
  size: number;
  i: number;
  message: string;
  productId;
  sortColumn: number = 3;
  sortColumnName: string = ''; // first column variable
  sortOrder: string = 'desc';
  ASCENDING: string = 'asc';
  DESCENDING: string = 'desc';
  newZone;
  pindtselection:boolean=false;
   //filter
   dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: '0',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }
  ];


  dropdownSelected = {
    sqId: 0,
    name: 'All',
    filtreparam: '0',
    sortname: '',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'Engineer Name',
      sortColumnName: 'sr.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Service Center',
      sortColumnName: 'lg.legal_entity_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Engineer Rating',
      sortColumnName: '',
      icon: 'fa fa-angle-down pull-right'
    }
   
  ];

  //done
  _cFlag:string;
  constructor(private pagefilterservice:PagefilterService,private _activatedRoute:ActivatedRoute,private spinner: NgxSpinnerService, 
    private loginService: Loginservices,private global:GlobalserviceService,
     private servicerecourseservice:ServiceResourceService) {
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
    // this.setOrder('createdDate', 3);
  }
   
  getFilterList() {
    this.pagefilterservice.getPagefilter('rating').subscribe(data => {
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

      this.getRating();
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
    }, error => {this.spinner.hide();
      this.global.alert({type:'alert',message:error.error.message});
    }
    );
  }

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getRating();
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
        this.getRating();
      } else {
        tbl.icon = '';
      }
    });
  }
ratingList:Page<any>=new Page<any>();
  getRating() {
    this.spinner.show();
    this.ratingList.content=[];
    this.servicerecourseservice.getServiceResourceCustomerRating(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
        this.spinner.hide();
      this.ratingList=data;

    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.global.alert({type:'alert',message:error.error.message});
    });
  }

  jumpPage(pagenumber){
  if(this.ratingList.totalPages< pagenumber){
    this.message="Enter Valid Input";
  }
  else{
    this.dropdownSelected.page=pagenumber-1;
    this.getRating();
}
}
advancesearch={
  eng_name:'',
  legal_entity:''
}
  

ResetSearchresult(){
  this.advancesearch.eng_name=''
   this.advancesearch.legal_entity=''
 
}
getAdvanceSearchresult(){
  if(this.advancesearch.eng_name=='' && this.advancesearch.legal_entity==''){
    this.message="Enter Key for Search";
    this.global.alert({type:'alert',message:this.message});

  }

  else{
    this.spinner.show();
    // this.advancesearch.documentDate = this.datePipe.transform(this.advancesearch.documentDate, 'yyyy-MM-dd');
    this.servicerecourseservice.getRatingSearchResult(this.advancesearch).subscribe(data=>{
      this.spinner.hide();
      this.ratingList.content = data;
   
    },error=>{
      this.spinner.hide();
      this.global.alert({type:'alert',message:error.error.message});
    })
  }
 
}

}
