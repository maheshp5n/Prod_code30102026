import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtServiceOrderReturnService } from '../../bat_services/bt-service-order-return.service';
import { Page } from '../../bt_model';

@Component({
  selector: 'app-bat-service-order-return',
  templateUrl: './bat-service-order-return.component.html',
  styleUrls: ['./bat-service-order-return.component.scss']
})
export class BatServiceOrderReturnComponent implements OnInit {

  pagenumber = 1;
  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 0,
    sortname: 'id',
    sortorder: 'desc',
    page: 0,
    size: 20,
    querypram: false

  };

  openModal = {
    returnso: false,
    returnsoreceive: false
  };
  dropdownsList = [
    {
      sqId: 1,
      name: 'All',
      clickEventParam: 0,
      class: 'dropdown-item dropdown-item-checked',
      pined: false
    }
  ]
  tableHeader = [{
    id: 1,
    titleName: 'So Return Number',
    orderby: 'id',
    sort: 'asc',
    icon: '',
    propertiesBinding: 'sorManualId',
    type: 'link',
    linkurl: '/batsoreturn',
    linkid: 'sorId'
  }, {
    id: 2,
    titleName: 'So Return Date',
    orderby: 'created_date',
    sort: '',
    icon: '',
    propertiesBinding: 'sorCreatedDate',
    type: 'date',
    dateformat: ''
  }, {
    id: 3,
    titleName: 'Destination Location',
    orderby: 'dloc.location_name',
    sort: '',
    icon: '',
    propertiesBinding: 'destinationLocationName',

  },
  {
    id: 4,
    titleName: 'Source Location',
    orderby: 'sloc.location_name',
    sort: '',
    icon: '',
    propertiesBinding: 'sourceLoccationName',

  }, {
    id: 5,
    titleName: 'Shipping To',
    orderby: 'shipping_to',
    sort: '',
    icon: '',
    propertiesBinding: 'sorShippingTo',

  },
  {
    id: 6,
    titleName: 'Tracking Number',
    orderby: 'tracking_number',
    sort: '',
    icon: '',
    propertiesBinding: 'sorTrackingNumber',
  }
    ,
  {
    id: 7,
    titleName: 'Total Item Returned',
    orderby: 'total_number_of_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'sorTotalNumberOfQuantityReturned',
  }, {
    id: 8,
    titleName: 'Total Item Received',
    orderby: 'total_number_of_quantity_received',
    sort: '',
    icon: '',
    propertiesBinding: 'sorTotalNumberOfQuantityReceived',
  }

  ];

  soReturnOrder: Page<any> = new Page<any>();

  @ViewChild('soReturn') soReturn: ModalDirective;
  @ViewChild('soReturnReceive') soReturnReceive: ModalDirective;

  constructor(private _btsoreturnService: BtServiceOrderReturnService,
    private _global: GlobalserviceService,
    private _activeRoute: ActivatedRoute,
    private _spinner: NgxSpinnerService,
    private loginservice: Loginservices,
    private pagefilterservice: PagefilterService,
  ) {
    this._activeRoute.queryParams.subscribe(param => {
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
    })
  }
  ngOnInit() {

  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('batsoreturn').subscribe(data => {
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
      this.getServiceOrderDetails();
    });
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getServiceOrderDetails();
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

  getServiceOrderDetails() {
    this._spinner.show();
    this._btsoreturnService.getPage(`filterparam=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      try {
        this.soReturnOrder = data;
      } catch (error) {

      }
      this._spinner.hide();
    }, error => {
      this._spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }
  getSortOrder(tblId) {
    this.tableHeader.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.orderby;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getServiceOrderDetails();
      } else {
        tbl.icon = '';
      }
    });
  }
  pinFilter() {
    this._spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this._spinner.hide();
    }, error => this._spinner.hide());
  }


  rights(object: string, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  onReturnSO($event) {
    this._global.alert({ type: '', message: 'SO Return Created Successfully' });
    this.soReturn.hide();
  }

  onReturnSOReceive($event) {
    this._global.alert({ type: '', message: 'SO Return Received Successfully ' });
    this.soReturnReceive.hide();
  }

  LimteExceed: boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if (this.LimteExceed) {
      return false;
    }
    return true;

  }
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill A Valid Input' });

    }
    else if (this.soReturnOrder.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getServiceOrderDetails();
    }

  }
}
