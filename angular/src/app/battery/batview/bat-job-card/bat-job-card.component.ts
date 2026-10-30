import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { BtJobCardServiceService } from '../../bat_services/bt-job-card-service.service';
import { PagefilterService } from '../../../services/pagefilterservice';
import { Page } from '../../bt_model';
import { GlobalserviceService } from '../../../globalservice.service';
import { FieldValidation } from '../../../services/validation.services';

@Component({
  selector: 'app-bat-job-card',
  templateUrl: './bat-job-card.component.html',
  styleUrls: ['./bat-job-card.component.css']
})
export class BatJobCardComponent implements OnInit {
  btJobCardList: Page<any> = new Page();
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
  requestTableHeaders = [
    {
      id: 1,
      name: 'Job Card Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 2,
      name: 'Service Order Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 3,
      name: 'BRC Case Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 4,
      name: 'Asset Serial Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 5,
      name: 'BRC Case Stage',
      sortColumnName: 'btin.customer_inventory_stage',
      icon: ''
    },
    {
      id: 6,
      name: 'Customer Challan Number',
      sortColumnName: 'btin.challan_number',
      icon: ''
    },
    {
      id: 7,
      name: 'Assigned To',
      sortColumnName: 'aemp.first_name',
      icon: ''
    },
    {
      id: 8,
      name: 'Created Date',
      sortColumnName: 'crd.created_date',
      icon: ''
    },
    {
      id: 9,
      name: 'Modified Date',
      sortColumnName: 'crd.last_modified_date',
      icon: ''
    }
  ]
  dropdownsList = [
    {
      sqId: 1,
      name: 'All',
      clickEventParam: 0,
      class: 'dropdown-item dropdown-item-checked',
      pined: false
    }
  ]

  constructor(private _activatedRoute: ActivatedRoute, private _spinner: NgxSpinnerService, private _jobCardService: BtJobCardServiceService,
    private pagefilterservice: PagefilterService, private _global: GlobalserviceService) {
    this._activatedRoute.queryParams.subscribe(param => {
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

  ngOnInit(): void {
  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_job_card').subscribe(data => {
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
      this.getbtJobCardList();
    });
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getbtJobCardList();
      } else {
        tbl.icon = '';
      }
    });
  }
  pindtselection = false;

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getbtJobCardList();
        if (item.pined) {
          this.pindtselection = true;
        } else {
          this.pindtselection = false;
        }
      } else {
        item.class = 'dropdown-item';
      }
    });
    console.log(this.dropdownsList);

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
  serialNumber: number;
  getbtJobCardList() {
    this._spinner.show();
    this.btJobCardList.content = [];
    this._jobCardService.getBtJobCardList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this._spinner.hide();
      this.btJobCardList = data;
      this.serialNumber = (((this.dropdownSelected.page) * this.dropdownSelected.size));
    }, err => {
      this._spinner.hide();
    })
  }
  // advance search
  advancesearch = {
    jobCardManualId: '',
    serviceOrderManualId: '',
    customerChallanNumber :'',
    assignTo: '',
    serailNo:''

  };
  searchJC() {
    if (this.advancesearch.jobCardManualId == '' &&
      this.advancesearch.serviceOrderManualId == '' &&
      this.advancesearch.customerChallanNumber == '' &&
      this.advancesearch.assignTo == ''&&
      this.advancesearch.serailNo=='') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinner.show();
      this.btJobCardList.content = [];
      this._jobCardService.getBtSearchResult(this.advancesearch).subscribe(data => {
        this._spinner.hide();
        this.btJobCardList.content = data;
      },
        error => {
          this._spinner.hide()
          this.message = error.error.message;
          this._global.alert({ type: 'alert', message: this.message });
        });
    }
  }
  resetsearch() {
    this.advancesearch.jobCardManualId = ''
    this.advancesearch.serviceOrderManualId = ''  
    this.advancesearch.customerChallanNumber = ''
    this.advancesearch.assignTo = ''
    this.advancesearch.serailNo = ''
    this.getbtJobCardList();

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

  pagenumber: any = null;
  message: string;
  jumpPage(pagenumber) {

    let pageno = pagenumber;
    if (pageno.match(/[a-z]/i)) {
      pagenumber = null;
      this.message = "Enter Valid Input";
      this._global.alert({ type: 'alert', message: this.message });
    }

    if (pagenumber == null || pagenumber == 0) {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Input" });

    }
    else if (this.btJobCardList.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Then Total Page" });
    } else if (FieldValidation.isLetter(pagenumber)) {
      this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
      pagenumber = null;
    }

    else {
      this.dropdownSelected.page = pagenumber - 1;
      this.getbtJobCardList()
    }
  }
}
