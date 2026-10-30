import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { Loginservices } from '../../services/login.services';
import { Page, ServiceOrderReturnResponse } from '../../services/model';
import { PagefilterService } from '../../services/pagefilterservice';
import { ServiceorderreturnService } from '../../services/serviceorderreturn.service';

@Component({
  selector: 'app-service-order-return',
  templateUrl: './service-order-return.component.html',
  styleUrls: ['./service-order-return.component.css']
})
export class ServiceOrderReturnComponent implements OnInit {
  pagenumber:any=null;
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 0,
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  },
  {
    sqId: 2,
    name: 'Received',
    clickEventParam: 2,
    class: 'dropdown-item',
    pined: false
  },
  {
    sqId: 3,
    name: 'Partial Received',
    clickEventParam: 3,
    class: 'dropdown-item'
  },
  {
    sqId: 5,
    name: 'Pending to Receive',
    clickEventParam: 4,
    class: 'dropdown-item'
  },
  {
    sqId: 6,
    name: 'Closed',
    clickEventParam: 5,
    class: 'dropdown-item'
  }];


  dropdownSelected = {
    sqId:1,
    name: 'All',
    filtreparam: 0,
    sortname: 'created_date',
    sortorder: 'desc',
    querypram:false,
    page:0,
    size:20

  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Service Order Return',
      sortColumnName: 'manual_id',
      icon: 'fa fa-caret-down pull-right'
    },
    {
      id: 2,
      name: 'Source Location',
      sortColumnName: 'sl.location_name',
      icon: ''
    },
    {
      id: 3,
      name: 'Destination Location',
      sortColumnName: 'dl.location_name',
      icon: ''
    },
    {
      id: 8,
      name: 'Status',
      sortColumnName: '',
      icon: ''
    },
    {
      id: 4,
      name: 'Recieved Quantity',
      sortColumnName: '',
      icon: ''
    },
    {
      id: 5,
      name: 'Return Quantity',
      sortColumnName: '',
      icon: ''
    },
    {
      id: 6,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: ''
    },
    {
      id: 7,
      name: 'Created By',
      sortColumnName: 'created_by_email_id',
      icon: ''
    }
  ];
  _cFlag:any;
  advancesearch = {
    manualId: '',
    sourceLocation: '',
    destinationLocation: '',
    status: '',
    
  };
  constructor(private sorservice:ServiceorderreturnService,
    private _gloabl: GlobalserviceService,
    private _activatedRoute:ActivatedRoute,
    private _ngxspinner: NgxSpinnerService,
    private loginservice: Loginservices,
    private pagefilterservice:PagefilterService, ) {
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
    this.sorResp.content=[];
  }
  sorResp:Page<ServiceOrderReturnResponse>=new Page<ServiceOrderReturnResponse>()
  serialNumber:any;
  page=0;
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.getsorTable();
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
getsorTable(){
  this.sorResp.content=[];
  this._ngxspinner.show();
  this.sorservice.getSOR(`filters=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
  this._ngxspinner.hide();
    this.sorResp=data;
    this.records=this.sorResp.content;
    this.serialNumber = (((this.page) * this.dropdownSelected.size));
    
  },error=>{
    this._ngxspinner.hide();
    this._gloabl.alert({ type: 'alert', message:error.error.message });

  })
}
pindtselection=false;
getFilterList() {
  this.pagefilterservice.getPagefilter('soreturn').subscribe(data => {
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
    this.getsorTable();
   
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
searchSO() {
  if(this.advancesearch.manualId== ''&&
  this.advancesearch.sourceLocation== '' &&
  this.advancesearch.destinationLocation== ''&&
  this.advancesearch.status== ''){
    this._gloabl.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
  }
  else{
    this._ngxspinner.show();
    this.sorResp.content=[];
  this.sorservice.getSearchResult(this.advancesearch).subscribe(data => {
    this._ngxspinner.hide();
    this.sorResp.content = data;
  
  },
  error => {this._ngxspinner.hide();
    this._gloabl.alert({ type: 'alert', message: error.error.message});
  });

  }
  
}
resetsearch(){
  this.advancesearch.manualId=''
  this.advancesearch.sourceLocation= ''
  this.advancesearch.destinationLocation= ''
  this.advancesearch.status=''

}
records:Array<any>=[];
getSortOrder(tblId) {
  this.requestTableHeaders.forEach((tbl) => {
    if (tbl.id == tblId) {
      this.dropdownSelected.sortname = tbl.sortColumnName;
      this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
      tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
      this.getsorTable();
    } else {
      tbl.icon = '';
    }
  });
}

jumpPage(pagenumber){
if(this.sorResp.totalPages< pagenumber){
this._gloabl.alert({ type: 'alert', message:"Enter Valid Input" });
}
else{
  this.dropdownSelected.page=pagenumber-1;
 this.getsorTable();
}
}

}
