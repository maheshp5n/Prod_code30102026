import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatJObCardQcService } from '../../bat_services/bat-job-card-qc.service';
import { Page } from '../../bt_model';

@Component({
  selector: 'app-bat-job-card-qc',
  templateUrl: './bat-job-card-qc.component.html',
  styleUrls: ['./bat-job-card-qc.component.css']
})
export class BatJobCardQcComponent implements OnInit {
  btJobCardQcList: Page<any> = new Page();

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
      name: 'QC Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 2,
      name: 'Job Card Number',
      sortColumnName: 'id',
      icon: ''
    },
    {
      id: 3,
      name: 'QC Engineer Name',
      sortColumnName: 'oemp.first_name',
      icon: ''
    },
    {
      id: 4,
      name: 'Created By',
      sortColumnName: 'cemp.first_name',
      icon: ''
    },
    {
      id: 5,
      name: 'Created Date',
      sortColumnName: 'created_date',
      icon: ''
    },
    {
      id: 6,
      name: 'Modified Date',
      sortColumnName: 'last_modified_date',
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

  constructor(private _activatedRoute: ActivatedRoute, private _spinner: NgxSpinnerService, private _btJobCardQcService: BatJObCardQcService,
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
    });

  }

  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_job_card_qc').subscribe(data => {
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

    });
    this.getBtJobCardQCList();
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getBtJobCardQCList();
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
        this.getBtJobCardQCList();
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

  ngOnInit(): void {
  }
  serialNumber: number;
  getBtJobCardQCList() {
    this._spinner.show();
    this._btJobCardQcService.getBtJobCardQCList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this._spinner.hide();
      this.btJobCardQcList = data;
      this.serialNumber = (((this.dropdownSelected.page) * this.dropdownSelected.size));
    }, err => {
      this._spinner.hide();
    })
  }
  // advance search
  advancesearch = {
    qcManualId: '',
    jobCardManualId: '',
    assignToQC :''

  };
  searchJCQC() {
    if (this.advancesearch.qcManualId == '' &&
      this.advancesearch.jobCardManualId == ''&&
      this.advancesearch.assignToQC =='') {
      this._global.alert({ type: 'alert', message: "Please Enter Valid Key For Search" });
    }
    else {
      this._spinner.show();
      this.btJobCardQcList.content = [];
      this._btJobCardQcService.getBtSearchResult(this.advancesearch).subscribe(data => {
        this._spinner.hide();
        this.btJobCardQcList.content = data;
      },
        error => {
          this._spinner.hide()
          this._global.alert({ type: 'alert', message: error.error.message });
        });
    }
  }
  resetsearch() {
    this.advancesearch.qcManualId = ''
    this.advancesearch.jobCardManualId = ''
    this.advancesearch.assignToQC = ''
    this.getBtJobCardQCList();
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
  jumpPage(pagenumber: number) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == undefined) {
      this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    }
    else if (this.btJobCardQcList.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else {
      this.dropdownSelected.page = pagenumber - 1;
      this._spinner.show();
      this.btJobCardQcList.content = [];
      this._btJobCardQcService.getBtJobCardQCList(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
        this.btJobCardQcList = data;
        this._spinner.hide();
      }, err => {
        this._spinner.hide();
        this._global.alert({ type: 'alert', message: err.error.message });
      });
    }
  }


}
