import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { DisplayTableHeaders, SelectedDropdown, DisplayDropdownList, LocationProductTransferItemResponse, Page } from '../../services/model';
import { PagefilterService } from '../../services/pagefilterservice';
import { ProductTransferServices } from '../../services/producttransfer.service';

@Component({
  selector: 'app-producttransfer',
  templateUrl: './producttransfer.component.html',
  styleUrls: ['./producttransfer.component.scss']
})
export class ProducttransferComponent implements OnInit {
  searchkey: string;
  dropdownsList = [{
    sqId: 1,
    name: 'All Product Transfer',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }, {
    sqId: 2,
    name: 'My Product Transfer',
    clickEventParam: 'myproducttransfer',
    class: 'dropdown-item',
    pined: false
  }, {
    sqId: 3,
    name: 'Product Transfer for Me',
    clickEventParam: 'producttransferforme',
    class: 'dropdown-item',
    pined: false
  }
  ]
  // dropdownSelected: SelectedDropdown = {
  //   sqId: 1,
  //   name: 'All Product Transfer',
  //   filtreparam: 'all',
  //   sortname: 'lpTranfer.locationProductTransferManualId',
  //   sortorder: 'desc'
  // }
  
  dropdownSelected = {
    sqId: 1,
    name: 'All Product Transfer',
    filtreparam: 'all',
    sortname: 'location_product_transfer_id',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };
  requestTableHeaders= [
    {
      id: 1,
      name: 'Product Transfer Number',
      sortColumnName: 'location_product_transfer_id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Product Request',
      sortColumnName: 'location_product_request_location_product_request_id',
      icon: ''
    },
    {
      id: 8,
      name: 'Status',
      sortColumnName: '',
      icon: ''
    },
    {
      id: 2,
      name: 'Destination Warehouse',
      sortColumnName: 'dl.location_name',
      icon: ''
    },
    {
      id: 3,
      name: 'Source Warehouse',
      sortColumnName: 'sl.location_name',
      icon: ''
    },
    {
      id:7,
      name:'Delivery Date',
      sortColumnName:'actual_delivery_date',
      icon:''
    },
    {
      id:8,
      name:'Received Date',
      sortColumnName:'received_date',
      icon:''
    },
    {
      id: 4,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: ''
    },
    {
      id: 5,
      name: 'Tracking Number',
      sortColumnName: 'tracking_number',
      icon: ''
    }, {
      id: 6,
      name: 'Created By',
      sortColumnName: 'cby.first_name',
      icon: ''
    }
  ];
  _cFlag:string;
  today = new Date();
  dateCustomClasses: DatepickerDateCustomClasses[];
  pageProducttransferItems: Page<any> = new Page<any>();
  constructor(private _activatedRoute:ActivatedRoute,private _global:GlobalserviceService,private pagefilterservice:PagefilterService,private _ngxspinner: NgxSpinnerService,
     private _producttransferServices: ProductTransferServices,
     private datePipe:DatePipe) {
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
    this.pageProducttransferItems.content=[];
    // this.getProductTransferItemList(`filtername=${this.dropdownSelected.filtreparam}`, `page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('producttransfer').subscribe(data => {
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
      this.getProductTransferItemList();
 
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
       this.getProductTransferItemList()
      
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
      this.getProductTransferItemList();
 
    } else {
      tbl.icon = '';
    }
  });
}
  getProductTransferItemList() {
    this._ngxspinner.show();
    this.pageProducttransferItems.content=[];
    this._producttransferServices.getPTListPageable(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`).subscribe(data => {
      this._ngxspinner.hide();
      this.pageProducttransferItems = data;
    }, error => {
       this._ngxspinner.hide();
       this._global.alert({type:'alert',message:error.error.message});
      });
  }
  pindtselection=false;
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
    }, error => {this._ngxspinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }
  // goToNext(pageNumber) {
  //   this.getProductTransferItemList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${pageNumber + 1}&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  // }
  // goToPrevious(pageNumber) {
  //   this.getProductTransferItemList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${pageNumber - 1}&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  // }
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pageProducttransferItems.totalPages< pagenumber){
  
   this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getProductTransferItemList()}
}

advancesearch = {
  manualId: '',
  productRequestManualId:'',
  sourceLocation: '',
  destinationLocation: '',
  status:'',
  deliveryDate:'',
  receivedDate:''
 
  
};
searchPT() {
  if( this.advancesearch.manualId== '' &&
  this.advancesearch. productRequestManualId=='' &&
  this.advancesearch.sourceLocation== '' &&
  this.advancesearch.destinationLocation== ''&&
  this.advancesearch.status =='' &&
  this.advancesearch.receivedDate==''&&
  this.advancesearch.deliveryDate==''){
    this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });

  }
  else{
    if(this.advancesearch.deliveryDate!=''){
      this.advancesearch.deliveryDate= this.datePipe.transform(this.advancesearch.deliveryDate, 'yyyy-MM-dd');
    }
    if(this.advancesearch.receivedDate!=''){
      this.advancesearch.receivedDate=this.datePipe.transform(this.advancesearch.receivedDate,'yyyy-MM-dd');
    }
    this._ngxspinner.show();
  this._producttransferServices.getPTSearchResult(this.advancesearch).subscribe(data => {
    this._ngxspinner.hide();
    this.pageProducttransferItems.content = data;
  
  },error=>{
    this._ngxspinner.hide();
    this._global.alert({type:'alert',message:error.error.message});

  });

  }
  
}
reserchsearch(){
  this.advancesearch.manualId= ''
  this.advancesearch.productRequestManualId=''
  this.advancesearch.sourceLocation=''
  this.advancesearch.destinationLocation= ''
  this.advancesearch.deliveryDate=''
  this.advancesearch.receivedDate=''
  this.advancesearch.status=''
}
}
