import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import {LPrStatusForList, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { ProductReturnService } from '../../../services/productreturnservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtProductReturnService } from '../../bat_services/bt-product-return.service';
import { BtProductReturnResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-product-return',
  templateUrl: './bat-product-return.component.html',
  styleUrls: ['./bat-product-return.component.scss']
})
export class BatProductReturnComponent implements OnInit {
  searchkey: string;
  lprstatusl: Array<LPrStatusForList> = []
  isShowProductReturn: boolean = false;
  openModels = { receiveOrder: false };
  openProductReturnModel = {btProductReturn:false}
  heading: string;
  returnId : string;
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
    sortname: 'id',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  }
  requestTableHeaders = [
    {
      id: 1,
      name: 'Product Return Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Destination Warehouse',
      sortColumnName: 'dloc.location_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 3,
      name: 'Source Warehouse',
      sortColumnName: 'sloc.location_name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Status',
      sortColumnName: 'return_order_status',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: 'fa fa-angle-down pull-right'
    }
  ];
  _cFlag: string;
  btProductReturnOrderRes: Page<BtProductReturnResponse> = new Page<BtProductReturnResponse>();
  @ViewChild('btProductReturn', { static: false }) btProductReturn: ModalDirective;
  @ViewChild('receiveOrder', { static: false }) receiveOrder: ModalDirective;
  constructor(private pagefilterservice: PagefilterService, private _global: GlobalserviceService, private _activatedRoute: ActivatedRoute, private _productreturnservice: ProductReturnService, private _ngxspinner: NgxSpinnerService,
    private _btPrductReturnOrderService: BtProductReturnService) {
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
    this.btProductReturnOrderRes.content = [];
  }


  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_product_returnOrder').subscribe(data => {
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
    this.btProductReturnOrderRes.content = [];
    this._btPrductReturnOrderService.btProductReturnList(filtername, pageinfo).subscribe(data => {
      this._ngxspinner.hide();
      this.btProductReturnOrderRes = data;
    },
      error => {
        this._ngxspinner.hide()
      });
  }

  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == "") {
      this._global.alert({ type: 'alert', message: "Please Enter Valid input" });
    }
    if (this.btProductReturnOrderRes.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Please Enter Valid input" });
    }
    else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
    }
  }
  advancesearch = {
    returnOrderManualId: '',
    returnOrderStatus:'',
    returnOrderShipToName : '',
    returnOrderShippingMode :'',
    returnOrderTrackingOrder : '',
    returnOrderSouceLocationName : '',
    returnOrderDestinationalLocationName :'',
    fromDate : '',
    toDate: ''

  };
  searchPR() {
    if ( this.advancesearch.returnOrderManualId== ''&&
    this.advancesearch.returnOrderSouceLocationName==''&&
    this.advancesearch.returnOrderDestinationalLocationName== ''&&
    this.advancesearch.returnOrderStatus== ''){
      this._global.alert({ type: 'alert', message:"Please Enter Valid Key For Search" });
    }
    else{
      this._ngxspinner.show();
      this.btProductReturnOrderRes.content = []
      this._btPrductReturnOrderService.btProductRetrunAdvanceSearch(this.advancesearch).subscribe(data => {
        this._ngxspinner.hide();
         this.btProductReturnOrderRes.content = data;
         this.btProductReturnOrderRes.first = true;
         this.btProductReturnOrderRes.last = true;

      },error=>{     
        this._ngxspinner.hide(); 
      this._global.alert({ type: 'alert', message:error.error.message});
      });
    }
  }
  resetreturn() {
    this.advancesearch.returnOrderManualId = ''
    this.advancesearch.returnOrderSouceLocationName = ''
    this.advancesearch.returnOrderDestinationalLocationName = ''
    this.advancesearch.returnOrderStatus = ''
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }

  getStatusList() {
    this._productreturnservice.getStatusForList().subscribe(data => {
      const temp = [];
      for (let i = 0; i < data.length; i++) {
        let lis = new LPrStatusForList();
        lis.name = data[i];
        temp.push(lis);
        this.lprstatusl = temp;
      }
    })
  }
  createReturnModelhide() {
    this.btProductReturn.hide();
  }

  saveBtProductReturn() {
    this.btProductReturn.hide();   
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }

  cancelProductReturn() {
    this.btProductReturn.hide();
  }
  onItemReceive($event) {
    this._global.alert({ type: '', message: `Updated Successfully` });
    this.receiveOrder.hide();
    this.getReturnOrderList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&size=${this.dropdownSelected.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  onReceiveCancled($event) {
    this.receiveOrder.hide();
  }
}
