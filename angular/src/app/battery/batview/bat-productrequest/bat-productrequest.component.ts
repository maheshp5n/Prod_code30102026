import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { LocationProductRequestResponse, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtProductRequestResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-productrequest',
  templateUrl: './bat-productrequest.component.html',
  styleUrls: ['./bat-productrequest.component.css']
})
export class BatProductrequestComponent implements OnInit {
  dropdownsList = [
    {
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
      sqId: 4,
      name: 'Sent Request',
      clickEventParam: 'byme',
      class: 'dropdown-item',
      pined: false
    },
    {
      sqId: 5,
      name: 'Received Request',
      clickEventParam: 'forme',
      class: 'dropdown-item',
      pined: false
    },
    {
      sqId: 6,
      name: 'Draft',
      clickEventParam: 'Draft',
      class: 'dropdown-item',
      pined: false
    },
    {
      sqId: 7,
      name: 'Short closed',
      clickEventParam: 'Short closed',
      class: 'dropdown-item',
      pined: false
    }
  ];


  dropdownSelected = {
    sqId: 1,
    name: 'All PR',
    filtreparam: 'all',
    sortname: 'id',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false
  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Product Request Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 2,
      name: 'Destination Warehouse',
      sortColumnName: 'dloc.location_name',
      icon: ''
    },
    {
      id: 3,
      name: 'Source Warehouse',
      sortColumnName: 'sloc.location_name',
      icon: ''
    },
    {
      id: 4,
      name: 'Status',
      sortColumnName: 'bt_product_request_status',
      icon: ''
    },
    {
      id: 5,
      name: 'Submitted Date',
      sortColumnName: 'submitted_date',
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
      name: 'Last Modified',
      sortColumnName: 'last_modified_date',
      icon: ''
    },
    {
      id: 8,
      name: 'Last Modified By',
      sortColumnName: 'last_name',
      icon: ''
    }
  ];
  pindtselection = false;
  searchkey: string;
  message: string;
  batProdutReqList: Page<BtProductRequestResponse> = new Page<BtProductRequestResponse>();
  locationId: number;
  loginData;
  filterName: string;
  page: number = 0;
  prStatusDropDownList = ['Draft','Submitted','Short Closed','Closed'];

  productRequestList: Page<LocationProductRequestResponse> = new Page<LocationProductRequestResponse>();
  constructor(
    private _batProductReqService: BtproductrequestService,
    private _ngxspinner: NgxSpinnerService,
    private _pagefilter: PagefilterService,
    private _activateRoute: ActivatedRoute, private _global: GlobalserviceService,
    private loginservice: Loginservices
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

  ngOnInit() {
    this.getLoggedInUser();
   }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();

  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getFilterProductRequestList();
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
        this.getFilterProductRequestList();
      } else {
        tbl.icon = '';
      }
    });
  }

  getFilterList() {
    this._pagefilter.getPagefilter('bat_product_request').subscribe(data => {
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
      this.getFilterProductRequestList();
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
  getFilterProductRequestList() {
    this._ngxspinner.show();
    this.batProdutReqList.content = [];
    this._batProductReqService.fetchBtProductReq(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.batProdutReqList.content = data.content;
      this._ngxspinner.hide();
      this.batProdutReqList.first = data.first;
      this.batProdutReqList.last = data.last;
      this.batProdutReqList.totalPages = data.totalPages;
      this.batProdutReqList.totalElements = data.totalElements;
      this.batProdutReqList.number = data.number;
    }, error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }
  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == "") {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Input" });
    }
    else if (this.batProdutReqList.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else if (FieldValidation.isLetter(pagenumber)) {
      this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
      pagenumber = null;
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getFilterProductRequestList();
    }
  }
  advancesearch = {
    productRequestManualId: '',
    productRequestStatus: '',
    productRequestSoruceLocationName: '',
    productRequestDestinationLocationName: ''

  };
  searchPR() {
    if (this.advancesearch.productRequestManualId == '' &&
      this.advancesearch.productRequestSoruceLocationName == '' &&
      this.advancesearch.productRequestDestinationLocationName == '' &&
      this.advancesearch.productRequestStatus == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });

    }
    else {
      this._ngxspinner.show();
      this._batProductReqService.fetchdataByAdvanceSearch(this.advancesearch).subscribe(data => {
        this._ngxspinner.hide();
        this.batProdutReqList.content = data;
        this.batProdutReqList.first = true;
        this.batProdutReqList.last = true;
      }, error => {
        this._ngxspinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }

  }
  resetsearch() {
    this.advancesearch.productRequestManualId = ''
    this.advancesearch.productRequestSoruceLocationName = ''
    this.advancesearch.productRequestDestinationLocationName = ''
    this.advancesearch.productRequestStatus = ''
    this.getFilterProductRequestList();
  }
  LimteExceed: boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if (this.LimteExceed == true) {
      return false;
    }
    return true;

  }


}
