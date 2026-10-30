import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { CustomerInventoryServices } from '../../../services/customerinventory.service';
import { Loginservices } from '../../../services/login.services';
import { Page } from '../../../services/model';
import * as fileSaver from 'file-saver';
import { BtCustomerInventoryService } from '../../bat_services/bt-customer-inventory.service';
import { ModalDirective } from 'ngx-bootstrap';
import { BtCustomerInventoryOutRequest } from '../../bt_model';
import { ThrowStmt } from '@angular/compiler';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../../services/pagefilterservice';

@Component({
  selector: 'app-bat-customer-inventory',
  templateUrl: './bat-customer-inventory.component.html',
  styleUrls: ['./bat-customer-inventory.component.css']
})
export class BatCustomerInventoryComponent implements OnInit {

  searchitem: string;
  isShowInventoryOut = [];
  isShowInventoryOutBtn = [];
  isShowGRNBtn = [];
  pindtselection = false;
  customerInventoryOutRes;
  firstPage: boolean;
  lastPage: boolean;
  grnDetail;
  _cFlag: string;
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked',
    pined: false

  }];
  dropdownSelected = {
    sqId: 1,
    name: 'All',
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
      name: 'S.No',
      sortColumnName: '',
      icon: ''

    },
    {
      id: 2,
      name: 'BRC Case No.',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 3,
      name: 'Dynamic Warrenty',
      sortColumnName: 'dynamic_warranty',
      icon: ''
    },
    {
      id: 4,
      name: 'Serial Number',
      sortColumnName: 'assets_serial_number',
      icon: ''

    },
    {
      id: 5,
      name: 'Part Code',
      sortColumnName: 'product_partcode',
      icon: ''

    },
    {
      id: 6,
      name: 'Stage',
      sortColumnName: 'customer_inventory_stage',
      icon: ''

    },
    {
      id: 7,
      name: 'Type',
      sortColumnName: 'jc.repair_product_state',
      icon: ''

    },
    {
      id: 8,
      name: 'Customer',
      sortColumnName: 'account_sap_number',
      icon: ''

    },
    {
      id: 9,
      name: 'Challan No',
      sortColumnName: 'challan_number',
      icon: ''

    },
    {
      id: 10,
      name: 'Challan Date',
      sortColumnName: 'receive_challan_date',
      icon: ''

    },
    {
      id: 11,
      name: 'Circle Name',
      sortColumnName: 'circle_name',
      icon: ''

    },
    {
      id: 12,
      name: 'Receive Date',
      sortColumnName: 'in_date',
      icon: ''

    }
  ];

  @ViewChild('inputfile', { static: false }) inputfile: ElementRef;
  @ViewChild('outputFile', { static: false }) outputfile: ElementRef;

  @ViewChild('customerInventoryIn', { static: false }) customerInventoryIn: ModalDirective;
  @ViewChild('CustomerInventoryOut', { static: false }) CustomerInventoryOut: ModalDirective;
  @ViewChild('editGRN', { static: false }) editGRN: ModalDirective;
  pagedata: Page<any> = new Page<any>();
  openCustomerInventoryIn = { customerInventoryIn: false };
  openCustomerInventoryOut = { CustomerInventoryOut: false }
  openGRN = { editGRN: false }


  constructor(
    private pagefilterservice: PagefilterService,
    private spinner: NgxSpinnerService,
    private loginservice: Loginservices,
    private _global: GlobalserviceService,
    private _btCustomerInventoryService: BtCustomerInventoryService,
    private _activatedRoute: ActivatedRoute
  ) {
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
      // this.getCustomerInventory();
      this.getFilterList();
    })
  }
  resetFileValue() {
    this.inputfile.nativeElement.value = '';
    this.outputfile.nativeElement.value = '';
  }
  ngOnInit() {
    this.pagedata.content = [];
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  getCustomerInventory() {
    this.spinner.show();
    this.pagedata.content = [];
    this.isShowInventoryOut = [];
    this._btCustomerInventoryService.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.spinner.hide();
      this.isShowInventoryOutBtn = [];
      this.isShowGRNBtn = [];
      this.pagedata = data;
      this.firstPage = data.first;
      this.lastPage = data.last;
      this.serialNumber = (((this.dropdownSelected.page) * this.size));
      for (let i = 0; i < this.pagedata.content.length; i++) {
        if (!this.pagedata.content[i].customerInventoryReturnedToCustomer) {
          this.isShowInventoryOutBtn.push(true);
          this.isShowGRNBtn.push(false);
        }
        else {
          this.isShowInventoryOutBtn.push(false);
          this.isShowGRNBtn.push(true);
        }
      }
    },
      error => this.spinner.hide());
  }


  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_brc_case').subscribe(data => {
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
        console.log(this.dropdownsList);

      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }


      this.getCustomerInventory();
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
        this.getCustomerInventory();

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
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getCustomerInventory();
      } else {
        tbl.icon = '';
      }
    });
  }
  customerInventoryInUpload($event) {
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this._btCustomerInventoryService.postCustomerInventoryIn(fd).subscribe(data => {
      this.spinner.hide();
      this.resetFileValue();
      this.getCustomerInventory();
    }, error => {
      this.spinner.hide();
      this.resetFileValue();
      this._global.alert({ type: 'alert', message: error.message });
    });

  }

  customerInventoryOUt($event) {
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this._btCustomerInventoryService.postOutInventory(fd).subscribe(data => {
      this.spinner.hide();
      this.resetFileValue();
      this.getCustomerInventory();
    }, error => {
      this.spinner.hide();
      this.resetFileValue();
      this._global.alert({ type: 'alert', message: error.message });
    });

  }
  contactSearch = {
    caseManualId: '',
    serviceAppointmentManualId: '',
    customerInventoryAssetsSerialNumber: '',
    customerInventoryProductPartcode: '',
    rma_status: '',
    rmaManualId: ''
  };
  advanceSearch() {
    if (this.contactSearch.caseManualId == '' && this.contactSearch.serviceAppointmentManualId == ''
      && this.contactSearch.customerInventoryAssetsSerialNumber == '' &&
      this.contactSearch.customerInventoryProductPartcode == '' && this.contactSearch.customerInventoryProductPartcode == '' && this.contactSearch.rmaManualId == ''
      && this.contactSearch.rma_status == '') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Input To Search" });
    }
    else {
      this.spinner.show();
      this.pagedata.content = [];
      this._btCustomerInventoryService.getAdvanceSearch(this.contactSearch).subscribe(data => {
        this.spinner.hide();
        this.isShowInventoryOutBtn = [];
        this.isShowGRNBtn = [];
        this.pagedata.content = data;
        for (let i = 0; i < this.pagedata.content.length; i++) {
          if (!this.pagedata.content[i].customerInventoryReturnedToCustomer) {
            this.isShowInventoryOutBtn.push(true);
            this.isShowGRNBtn.push(false);
          }
          else {
            this.isShowInventoryOutBtn.push(false);
            this.isShowGRNBtn.push(true);
          }
        }
      }, error => {
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message });

      });
    }

  }
  reset() {
    this.contactSearch.caseManualId = '';
    this.contactSearch.serviceAppointmentManualId = '';
    this.contactSearch.serviceAppointmentManualId = '';
    this.contactSearch.customerInventoryProductPartcode = '';
    this.contactSearch.customerInventoryAssetsSerialNumber = '';
    this.contactSearch.customerInventoryProductPartcode = '';
    this.contactSearch.rma_status = '';
    this.contactSearch.rmaManualId = '';
    this.getCustomerInventory();
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
  serialNumber: number;
  size: number = 20;
  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == undefined) {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Input" });
    }
    else if (this.pagedata.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getCustomerInventory();

      this.serialNumber = (((this.dropdownSelected.page) * this.size));
    }
  }
  createCustomerInventoryOut(inventoryRes) {
    this.customerInventoryOutRes = inventoryRes;
    this.CustomerInventoryOut.show();
  }
  editGrnDetail(returnVal) {
    this.grnDetail = returnVal;
    this.editGRN.show();
  }

  onSaveCustomerInventoryIn() {
    this.customerInventoryIn.hide();
    this.getCustomerInventory();
  }
  cancelInventoryIn() {
    this.customerInventoryIn.hide();
  }
  onSaveCustomerInventoryOut() {
    this._global.alert({ type: '', message: "Successfully Updated" });
    this.CustomerInventoryOut.hide();
    this.getCustomerInventory();
  }
  cancelInventoryOut() {
    this.CustomerInventoryOut.hide();
  }

  onSaveGrnDetail() {
    this._global.alert({ type: '', message: 'Successfully Updated' });
    this.editGRN.hide();
    this.getCustomerInventory();
  }
  CancelGrn() {
    this.editGRN.hide();

  }

}

