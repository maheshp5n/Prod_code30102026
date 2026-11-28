import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { Page } from '../../../services/model';
import { BtMyInventoryService } from '../../bat_services/bt-my-inventory.service';
import { BtMyInventoryResponse } from '../../bt_model';

@Component({
  selector: 'app-bat-my-inventory',
  templateUrl: './bat-my-inventory.component.html',
  styleUrls: ['./bat-my-inventory.component.css']
})
export class BatMyInventoryComponent implements OnInit {
  page: number = 0;
  btInventoryList: Page<BtMyInventoryResponse> = new Page<BtMyInventoryResponse>();
  openModal = {
    partcodechangeModal: false,
    partstatuschange: false
  };
  @ViewChild('partcodechangeModal') partcodechangeModal: ModalDirective;
  @ViewChild('partstatuschange') partstatuschange: ModalDirective;
  tableHeader: Array<TableHeaderDetails> = [{
    id: 1,
    titleName: 'Warehouse',
    orderby: 'loc.location_name',
    sort: '',
    icon: '',
    propertiesBinding: 'locationName',

  },
  {
    id: 2,
    titleName: 'Product',
    orderby: 'btp.manual_id',
    sort: 'asc',
    icon: 'fa fa-arrow-up',
    propertiesBinding: 'productPartcode',
    type: 'link',
    linkurl: '/bat_product',
    linkid: 'productId'

  },
  {
    id: 3,
    titleName: 'Product Description',
    orderby: 'btp.product_description',
    sort: '',
    icon: '',
    propertiesBinding: 'productDescription',

  },
  {
    id: 4,
    titleName: 'Product Type',
    orderby: 'btp.product_type',
    sort: '',
    icon: '',
    propertiesBinding: 'productType'
  }, {
    id: 5,
    titleName: 'Exi Total Quantity',
    orderby: 'exicom_total_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'exicomTotalQuantity'
  }, {
    id: 6,
    titleName: 'Exi Total Ok Quantity',
    orderby: 'exicom_total_ok_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'exicomTotalOkQuantity'
  }, {
    id: 7,
    titleName: 'Exi Total Faulty Quantity',
    orderby: 'exicom_total_faulty_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'exicomTotalFaultyQuantity'
  },
  {
    id: 8,
    titleName: 'Exi Total Ber Quantity',
    orderby: 'exicom_total_ber_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'exicomTotalBerQuantity'
  },
   {
    id: 9,
    titleName: 'Customer Total Quantity',
    orderby: 'customer_total_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'customerTotalQuantity',

  }, {
    id: 10,
    titleName: 'Customer Total Ok Quantity',
    orderby: 'customer_total_ok_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'customerTotalOkQuantity'
  }, {
    id: 11,
    titleName: 'Customer Total Faulty Quantity',
    orderby: 'customer_total_faulty_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'customerTotalFaultyQuantity'
  },
  {
    id: 12,
    titleName: 'Customer Total Ber Quantity',
    orderby: 'customer_total_ber_quantity',
    sort: '',
    icon: '',
    propertiesBinding: 'customerTotalBerQuantity'
  }
];

  currentpage = {
    size: 20,
    page: 0,
    order: 'btp.manual_id',
    sort: 'asc'
  }

  constructor(private _btInventorySerice: BtMyInventoryService, private _spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _global: GlobalserviceService, private loginservice: Loginservices) {
    this._activatedRoute.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.currentpage.page = Number(param['page']);

      }
      if (param['size'] != undefined && param['size'] != null) {
        this.currentpage.size = Number(param['size']);
      }
      if (param['order'] != undefined && param['order'] != null) {
        this.currentpage.order = param['order'];
      }
      if (param['sort'] != undefined && param['sort'] != null) {
        this.currentpage.sort = param['sort'];
      }

      this.getAllInventoryList();

    });

  }

  ngOnInit(): void {

  }

  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  getAllInventoryList() {
    this._spinner.show();
    this.btInventoryList.content = [];
    this._btInventorySerice.btAllInventory(`page=${(this.currentpage.page)}&size=${this.currentpage.size}&sort=${this.currentpage.order},${this.currentpage.sort}`).subscribe(data => {
      this._spinner.hide();
      this.btInventoryList = data;
      this.btInventoryList.totalElements = data.totalElements;
      this.btInventoryList.totalPages = data.totalPages;


    }, error => {
      this._spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }

  changeOrder(item) {
    const lt = this.tableHeader.length;
    for (let i = 0; i < lt; i++) {
      if (this.tableHeader[i].id == item.id) {

        this.currentpage.order = this.tableHeader[i].orderby;
        this.tableHeader[i].sort = this.tableHeader[i].sort === 'asc' ? 'desc' : 'asc';
        this.tableHeader[i].icon = this.tableHeader[i].sort === 'asc' ? 'fa fa-arrow-up' : 'fa fa-arrow-down';
        this.currentpage.sort = this.tableHeader[i].sort;

      }
      else {
        this.tableHeader[i].sort = '';
        this.tableHeader[i].icon = '';
      }
    }
    this.getAllInventoryList();
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

  pagenumber: any;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill A Valid Input' });

    }
    else if (this.btInventoryList.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else {
      this.currentpage.page = pagenumber - 1;
      this.getAllInventoryList();
    }

  }

  onPartcodeChange($event) {
    this.partcodechangeModal.hide();
    this.getAllInventoryList();
  }

  onPartcodeStatusChange($event) {
    this.partstatuschange.hide();
    this.getAllInventoryList();
  }

  advancesearch = {
    productPartcode: '',
    productDescription: ''
  };

  searchMyInventory() {
    if (this.advancesearch.productPartcode == '' && this.advancesearch.productDescription == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinner.show();
      this._btInventorySerice.getSearchResult(this.advancesearch).subscribe(data => {
        this._spinner.hide();
        this.btInventoryList.content = data;
        this.page = 0;
        this.btInventoryList.size = data.length;
        this.btInventoryList.first = true;
        this.btInventoryList.last = true;
      }, error => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message:error.error.message});
      });
    }
  }
  resetsearch() {
    this.advancesearch.productPartcode = ''
    this.advancesearch.productDescription = ''   
    this.getAllInventoryList();
  }
}

interface TableHeaderDetails {
  id: Number,
  titleName: string,
  orderby: string,
  sort: string,
  icon: string,
  propertiesBinding: string,
  type?: string,
  linkurl?: string,
  linkid?: any
}
