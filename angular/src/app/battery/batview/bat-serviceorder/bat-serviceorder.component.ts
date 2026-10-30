import { GlobalserviceService } from './../../../globalservice.service';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Page } from './../../../services/model';
import { Loginservices } from '../../../services/login.services';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../../services/pagefilterservice';
import { FieldValidation } from '../../../services/validation.services';
import { BtServiceOrderService } from '../../bat_services/bt-service-order.service';

@Component({
  selector: 'app-bat-serviceorder',
  templateUrl: './bat-serviceorder.component.html',
  styleUrls: ['./bat-serviceorder.component.scss']
})
export class BatServiceorderComponent implements OnInit {

  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: '',
    class: 'dropdown-item dropdown-item-checked',
    pined: false
  }
];


  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: '',
    sortname: 'id',
    sortorder: 'desc',
    querypram: false,
    page: 0,
    size: 20

  };

  requestTableHeaders = [
    {
      id: 1,
      name: 'Service Order Number',
      sortColumnName: 'id',
      icon: 'fa fa-caret-down pull-right'
    },   
    {
      id: 2,
      name: 'Total Quantity',
      sortColumnName: 'total_number_of_item',
      icon: ''
    },
    {
      id: 3,
      name: 'Assign Quantity',
      sortColumnName: 'total_number_of_item_assigned',
      icon: ''
    },
    {
      id: 4,
      name: 'Repair Center',
      sortColumnName: 'dloc.location_name',
      icon: ''
    },
    {
      id: 5,
      name: 'Shipping Mode',
      sortColumnName: 'shipping_mode',
      icon: ''
    },
    {
      id: 6,
      name: 'Tracking Number',
      sortColumnName: 'tracking_number',
      icon: ''
    },
    {
      id: 7,
      name: 'Source Location',
      sortColumnName: 'sloc.location_name',
      icon: ''
    },
    {
      id: 8,
      name: 'Last Modified',
      sortColumnName: 'last_modified_date',
      icon: ''
    },
    {
      id: 9,
      name: 'Last Modified By',
      sortColumnName: 'lemp.first_name',
      icon: ''
    }
  ];

  serviceOrders: Page<any> = new Page<any>();
  searchkey: string;
  openModal = {
    create: false,
    receive: false,
    assign:false,
    returnso:false
  };

  @ViewChild('serviceOrderModal', { static: false }) serviceOrderModal: ModalDirective;
  @ViewChild('receiveModal', { static: false }) receiveModal: ModalDirective;
  @ViewChild('assignModel',{static:false}) assignModel:ModalDirective;
  @ViewChild('soReturn',{static:false})soReturn:ModalDirective;

  _cFlag: string;
  constructor(
    private _ngxspinner: NgxSpinnerService,
    private loginservice: Loginservices, private pagefilterservice: PagefilterService,
    private _gloabl: GlobalserviceService, private _activatedRoute: ActivatedRoute,
    private _btServiceOrderService: BtServiceOrderService) {
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

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam
        this.getServiceOrder();
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
  message: string;
  serialNumber: number;
  getServiceOrder() {
    this._ngxspinner.show();
    this.serviceOrders.content = [];
    this._btServiceOrderService.getBtServiceOrders(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this._ngxspinner.hide();
      this.serviceOrders = data;
      this.serialNumber = (((this.dropdownSelected.page) * this.dropdownSelected.size));
    }, error => {
      this._ngxspinner.hide()
      this.message = error.error.message;
      this._gloabl.alert({ type: 'alert', message: this.message });
    }
    );
  }

  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getServiceOrder();
      } else {
        tbl.icon = '';
      }
    });
  }
  pindtselection = false;
  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_serviceorder').subscribe(data => {
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
      this.getServiceOrder();

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
  goToPage(pageNumber) {
    this.getServiceOrder();
  }

  onCreated(data) {
    this.serviceOrderModal.hide();
    this._gloabl.alert({ type: '', message: 'Created Successfully' });
    this.getServiceOrder()
  }
  onReceived() {
    this.receiveModal.hide();
    this._gloabl.alert({ type: '', message: 'New Service order Received' });
    this.getServiceOrder();
  }
  rights(object: string, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  pagenumber: any = null;
  jumpPage(pagenumber) {
    if (pagenumber == 0 || pagenumber == '' || pagenumber == null) {
      this._gloabl.alert({ type: 'alert', message: "Please Enter A Valid Input" });

    }
    else if (this.serviceOrders.totalPages < pagenumber) {
      this.message = "Please Enter Valid Input";
      this._gloabl.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page" });
    }
    else if (FieldValidation.isLetter(pagenumber)) {
      this._gloabl.alert({ type: 'alert', message: "Please Enter A Valid Input" });
      this.pagenumber = null;
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getServiceOrder();
    }
  }
  // advance search

  advancesearch = {
    serviceOrderManualId: '',
    searviceOrderShippingMode: '',
    searviceOrderTrackingNumber: '',
    serviceOrderRepairCenterLocationName: '',
    serviceOrderSourceLocationName: ''

  };
  searchSO() {
    if (this.advancesearch.serviceOrderManualId == '' &&
      this.advancesearch.searviceOrderShippingMode == '' &&
      this.advancesearch.searviceOrderTrackingNumber == '' &&
      this.advancesearch.serviceOrderRepairCenterLocationName == '' &&
      this.advancesearch.serviceOrderSourceLocationName == '') {
      this._gloabl.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._ngxspinner.show();
      this.serviceOrders.content = [];
      this._btServiceOrderService.getBtSearchResult(this.advancesearch).subscribe(data => {
        this._ngxspinner.hide();
        this.serviceOrders.content = data;
      },
        error => {
          this._ngxspinner.hide()
          this.message = error.error.message;
          this._gloabl.alert({ type: 'alert', message: this.message });
        });
    }
  }
  resetsearch() {
    this.advancesearch.serviceOrderManualId = ''
    this.advancesearch.searviceOrderShippingMode = ''
    this.advancesearch.searviceOrderTrackingNumber = ''
    this.advancesearch.serviceOrderRepairCenterLocationName = ''
    this.advancesearch.serviceOrderSourceLocationName = ''
    this.getFilterList();

  }
 
  onAssignSO(){
    this._gloabl.alert({type:'',message:'Assigned'});
    this.assignModel.hide();
  }

  assigneOpenModel($event){
    this.openModal.assign=true;
    this.assignModel.show();
  }


  onReturnSO($event){
    this._gloabl.alert({type:'',message:'Updated Successfully'})
    this.soReturn.hide();
  }
}
