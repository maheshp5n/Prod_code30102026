import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-accountscasedetails',
  templateUrl: './accountscasedetails.component.html',
  styleUrls: ['./accountscasedetails.component.scss']
})
export class AccountscasedetailsComponent implements OnInit {

  queryparam = {
    page: 0,
    size: 20,
    sort: 'asc',
    clm: 1,
    sortc: ''
  };
  tblheader = [{
    id:1,
    name: 'S.NO',
    sort: 'caseId'
  },
  {
    id:2,
    name: 'Case Number',
    sort: 'caseId'
  },
  {
    id:3,
    name: 'Site Id',
    sort: 'site.siteName'
  },
  { id:4,
    name: 'Assets Serial Number',
    sort: 'assets.serialNumber'
  },
  {
    id:5,
    name: 'Type',
    sort: 'caseType.caseTypesName'
  },
  {
    id:6,
    name: 'Case Reason',
    sort: 'caseReason.caseReasonDetailsName'
  },
  {
    id:7,
    name: 'Status',
    sort: 'status.caseStatusName'
  },
  {
    id:8,
    name: 'Stage',
    sort: 'stage.caseStageName'
  },
  {
    id:9,
    name: 'Date/Time Opened',
    sort: 'createDate'
  },
  {
    id:10,
    name: 'Date/Time Closed',
    sort: 'closeDate'
  },
  {
    id:11,
    name: 'Service Resource',
    sort: 'engineer.name'
  }
  ];

  accountsapnumber;
  accountdetail;
  casedetails;
  constructor(
    private _account: AccountService,
    private _ngSpinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute
  ) {
    this._activatedRoute.params.subscribe(param => {
      this.accountsapnumber = param['accountsap'];
    });
    this._activatedRoute.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.queryparam.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.queryparam.size = Number(param['size']);
      }
      if (param['sort'] != undefined && param['sort'] != null) {
        this.queryparam.sort = param['sort'];
      }
      if (param['clm'] != undefined && param['clm'] != null) {
        this.queryparam.clm = Number(param['clm']);
      }
      if (param['sortc'] != undefined && param['sortc'] != null) {
        this.queryparam.sortc = param['sortc'];
      }
      this.getAccountCaseDetails();
    });

  }

  ngOnInit() {
    this.getAccount();
  }

  sortOrder(i) {
    if (this.queryparam.clm == i) {
      this.queryparam.sort == 'asc' ? 'desc' : 'asc';
    } else {
      this.queryparam.page = 0;
      this.queryparam.clm = i;
      this.queryparam.sortc = this.tblheader[i].sort;
    }
    this.getAccountCaseDetails();
  }

  getAccountCaseDetails() {
    this._ngSpinner.show();
    this._account.getCaseListS(this.accountsapnumber, `page=${this.queryparam.page}&size${this.queryparam.size}=&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
      this._ngSpinner.hide();
      this.casedetails = data;
    }, error => this._ngSpinner.hide());
  }
  getAccount() {
    this._account.getAccount(this.accountsapnumber).subscribe(data => this.accountdetail = data);
  }
}
