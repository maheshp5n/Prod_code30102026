import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { LocationProductTransferItemResponse, Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { ProductTransferServices } from '../../../services/producttransfer.service';
import { FieldValidation } from '../../../services/validation.services';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtProductTransferResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-product-transfer',
  templateUrl: './bat-product-transfer.component.html',
  styleUrls: ['./bat-product-transfer.component.scss']
})
export class BatProductTransferComponent implements OnInit {


  @ViewChild('transferRecieve') transferRecieve: ModalDirective;
  searchkey: string;
  today = new Date();
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
    name: 'Product Transfer For Me',
    clickEventParam: 'producttransferforme',
    class: 'dropdown-item',
    pined: false
  }
  ]

  dropdownSelected = {
    sqId: 1,
    name: 'All Product Transfer',
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
      name: 'Product Transfer Number',
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
    },
    // {
    //   id: 7,
    //   name: 'Product Request',
    //   sortColumnName: 'location_product_request_location_product_request_id',
    //   icon: ''
    // },
    {
      id: 8,
      name: 'Status',
      sortColumnName: 'transfer_status',
      icon: ''
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
      sortColumnName: 'dloc.location_name',
      icon: ''
    },
    {
      id: 7,
      name: 'Delivery Date',
      sortColumnName: 'actual_delivery_date',
      icon: ''
    },
    {
      id: 8,
      name: 'Received Date',
      sortColumnName: 'received_date',
      icon: ''
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
      sortColumnName: 'created_by_email_id',
      icon: ''
    }
  ];
  _cFlag: string;
  heading: string;
  modalView = false;
  btProductTransferItems: Page<BtProductTransferResponse> = new Page<BtProductTransferResponse>();
  pageProducttransferItems: Page<LocationProductTransferItemResponse> = new Page<LocationProductTransferItemResponse>();
  constructor(private _activatedRoute: ActivatedRoute, private _global: GlobalserviceService, private pagefilterservice: PagefilterService, private _ngxspinner: NgxSpinnerService, private _producttransferServices: ProductTransferServices,
    private _btPtService: BatproducttransferService,private datePipe:DatePipe) {
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
  }
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_product_transfer').subscribe(data => {
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
      this.getBtProductTransferListItems();

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
        this.getBtProductTransferListItems()

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
        this.getBtProductTransferListItems();

      } else {
        tbl.icon = '';
      }
    });
  }

  getOpenProductRecieve() {
    this.transferRecieve.show();
    this.heading = 'Product Receive';

  }
  firstPage: boolean;
  lastPage: boolean;
  getBtProductTransferListItems() {
    this._ngxspinner.show();
    this.btProductTransferItems.content = [];
    this._btPtService.getProductTransferLIstByFilter(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this._ngxspinner.hide();
      this.btProductTransferItems.content = data.content;
      this.btProductTransferItems.number = data.number;
      this.btProductTransferItems.totalPages = data.totalPages;
      this.btProductTransferItems.totalElements = data.totalElements;
      this.firstPage = data.first;
      this.lastPage = data.last;
    }, error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }
  pindtselection = false;
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
    }, error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == "") {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Input" });
    }
    else if (this.btProductTransferItems.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else if (FieldValidation.isLetter(pagenumber)) {
      this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
      pagenumber = null;
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getBtProductTransferListItems();
    }
  }

  advancesearch = {
    productTransferManualId: '',
    productRequestSourceLocationName: '',
    productRequestDestinationLocationName: '',
    productTranferTrackingNumber: '',
    productTransferShippingMode: '',
    productTransferShippingToName: '',
    productTransferStatus: '',
    productTransferDeliveryDate: '',
    productTransferReceiveDate: ''
  };
  // searchPT() {
  //   if (this.advancesearch.productTransferManualId == '' &&
  //     this.advancesearch.productRequestSourceLocationName == '' &&
  //     this.advancesearch.productRequestDestinationLocationName == '' &&
  //     this.advancesearch.productTranferTrackingNumber == '' &&
  //     this.advancesearch.productTransferShippingMode == '' &&
  //     this.advancesearch.productTransferShippingToName == '' &&
  //     this.advancesearch.productTransferStatus == '' &&
  //     this.advancesearch.productTransferDeliveryDate == '' &&
  //     this.advancesearch.productTransferReceiveDate == ''
  //   ) {
  //     this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });

  //   }
  //   else {
  //     if(this.advancesearch.productTransferDeliveryDate!=''){
  //       this.advancesearch.productTransferDeliveryDate= this.datePipe.transform(this.advancesearch.productTransferDeliveryDate, 'yyyy-MM-dd');
  //     }
  //     if(this.advancesearch.productTransferReceiveDate!=''){
  //       this.advancesearch.productTransferReceiveDate=this.datePipe.transform(this.advancesearch.productTransferReceiveDate,'yyyy-MM-dd');
  //     }
  //     this._ngxspinner.show();
  //     this._btPtService.fetchdataByAdvanceSearch(this.advancesearch).subscribe(data => {
  //       this._ngxspinner.hide();
  //       this.btProductTransferItems.content = data;
  //       this.btProductTransferItems.first = true;
  //       this.btProductTransferItems.last = true;


  //     }, error => {
  //       this._ngxspinner.hide();
  //       this._global.alert({ type: 'alert', message: error.error.message });

  //     });

  //   }

  

  searchPT() {
  if (
    this.advancesearch.productTransferManualId === '' &&
    this.advancesearch.productRequestSourceLocationName === '' &&
    this.advancesearch.productRequestDestinationLocationName === '' &&
    this.advancesearch.productTranferTrackingNumber === '' &&
    this.advancesearch.productTransferShippingMode === '' &&
    this.advancesearch.productTransferShippingToName === '' &&
    this.advancesearch.productTransferStatus === '' &&
    this.advancesearch.productTransferDeliveryDate === '' &&
    this.advancesearch.productTransferReceiveDate === ''
  ) {
    this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    return;
  }

  if (this.advancesearch.productTransferDeliveryDate !== '') {
    this.advancesearch.productTransferDeliveryDate =
      this.datePipe.transform(this.advancesearch.productTransferDeliveryDate, 'yyyy-MM-dd');
  }

  if (this.advancesearch.productTransferReceiveDate !== '') {
    this.advancesearch.productTransferReceiveDate =
      this.datePipe.transform(this.advancesearch.productTransferReceiveDate, 'yyyy-MM-dd');
  }

  const payload: any = {};
  Object.keys(this.advancesearch).forEach(key => {
    const value = this.advancesearch[key];
    if (value !== null && value !== undefined && value !== '') {
      payload[key] = value;
    }
  });

  this._ngxspinner.show();
  this._btPtService.fetchdataByAdvanceSearch(payload).subscribe(
    data => {
      this._ngxspinner.hide();
      this.btProductTransferItems.content = data;
      this.btProductTransferItems.first = true;
      this.btProductTransferItems.last = true;
    },
    error => {
      this._ngxspinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    }
  );
}

  reserchsearch() {
    this.advancesearch.productTransferManualId = '',
      this.advancesearch.productRequestSourceLocationName = ''
    this.advancesearch.productRequestDestinationLocationName = ''
    this.advancesearch.productTranferTrackingNumber = ''
    this.advancesearch.productTransferShippingMode = ''
    this.advancesearch.productTransferShippingToName = ''
    this.advancesearch.productTransferStatus = ''
    this.advancesearch.productTransferDeliveryDate = ''
    this.advancesearch.productTransferReceiveDate = ''
    this.getBtProductTransferListItems();
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
  onPtrCreated($event) {
    this._global.alert({ type: '', message: 'Product Received Succesfully!' });
    this.transferRecieve.hide();
    this.getBtProductTransferListItems();
  }
  cancelData($event) {
    this.transferRecieve.hide();

  }
  getClose() {
    this.transferRecieve.hide();
  }


}
