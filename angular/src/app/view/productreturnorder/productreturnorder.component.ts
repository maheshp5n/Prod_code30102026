import { Component, OnInit } from '@angular/core';
import { Page, LpReturnOrderResponse, LPrStatusForList } from '../../services/model';
import { ProductReturnService } from '../../services/productreturnservice';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-productreturnorder',
  templateUrl: './productreturnorder.component.html',
  styleUrls: ['./productreturnorder.component.scss']
})
export class ProductreturnorderComponent implements OnInit {
  searchkey: string;
  lprstatusl:Array<LPrStatusForList>=[]
  dropdownsList = [{
    sqId: 1,
    name: 'All Return Orders',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }, {
    sqId: 2,
    name: 'Receipt Pending Return Orders',
    clickEventParam: 'open',
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Send By Me',
    clickEventParam: 'sendbyme',
    class: 'dropdown-item',
    pined: false
  }, {
    sqId: 4,
    name: 'Send To Me',
    clickEventParam: 'sendtome',
    class: 'dropdown-item',
    pined: false
  }, {
    sqId: 5,
    name: 'Pending To Receive for Me',
    clickEventParam: 'penditoreceiveforme',
    class: 'dropdown-item',
    pined: false
  }
  ]
  dropdownSelected = {
    sqId: 1,
    name: 'All Return Orders',
    filtreparam: 'all',
    sortname: 'lprOrderManualId',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  }
  requestTableHeaders = [
    {
      id: 1,
      name: 'Product Return Number',
      sortColumnName: 'lprOrderManualId',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Destination Warehouse',
      sortColumnName: 'destinationLocation.locationName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Source Warehouse',
      sortColumnName: 'sourceLocation.locationName',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Status',
      sortColumnName: 'locationReturnOrderStatus',
      icon :'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Created Date',
      sortColumnName: 'createdDate',
      icon: 'fa fa-angle-down pull-right'
    }
  ];
  _cFlag:string;
  pageLpReturnOrderResponse: Page<LpReturnOrderResponse> = new Page<LpReturnOrderResponse>();
  constructor(private pagefilterservice:PagefilterService,private _global: GlobalserviceService,private _activatedRoute:ActivatedRoute,private _productreturnservice: ProductReturnService, private _ngxspinner: NgxSpinnerService) {
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

  ngOnInit() {
    this.getStatusList();
    this.pageLpReturnOrderResponse.content=[];
    // this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }

  
  pindtselection=false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('productreturnorder').subscribe(data => {
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
 
      this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
 
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
       this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
 
      
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
       this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=0&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
 
     } else {
       tbl.icon = '';
     }
   });
 }
  pinFilter() {
    this._ngxspinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
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
  getReturnOrderList(filtername?: string, pageinfo?: string) {
    this._ngxspinner.show();
    this.pageLpReturnOrderResponse.content=[];
    this._productreturnservice.getReturnOrders(filtername, pageinfo).subscribe(data => {
      this._ngxspinner.hide();
      this.pageLpReturnOrderResponse = data;
    
      this.pageLpReturnOrderResponse.pageable
    }, error => { this._ngxspinner.hide() });
  }
  goToNext(pageNumber) {
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${pageNumber + 1}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  goToPrevious(pageNumber) {
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${pageNumber - 1}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pageLpReturnOrderResponse.totalPages< pagenumber){
    this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
}
}
advancesearch = {
  manualId: '',
  sourceLocation: '',
  destinationLocation: '',
  status: '',
  
};
searchPR() {
  if ( this.advancesearch.manualId== ''&&
  this.advancesearch.sourceLocation==''&&
  this.advancesearch.destinationLocation== ''&&
  this.advancesearch.status== ''){
    this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });

  }
  else{
    this._ngxspinner.show();
    this._productreturnservice.getSearchResult(this.advancesearch).subscribe(data => {
      this._ngxspinner.hide();
      this.pageLpReturnOrderResponse.content = data;
    
    });
  }

}
resetreturn(){
  this.advancesearch.manualId= ''
  this.advancesearch.sourceLocation= ''
  this.advancesearch.destinationLocation=''
  this.advancesearch.status=''

}

getStatusList(){
  this._productreturnservice.getStatusForList().subscribe(data=>{
    const temp=[];
    for(let i=0;i<data.length;i++){
      let lis =new  LPrStatusForList();
      lis.name=data[i];
      temp.push(lis);
      this.lprstatusl=temp;
    }
  })
}
}
