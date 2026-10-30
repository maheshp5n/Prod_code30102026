import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { basename } from 'path';
import { GlobalserviceService } from '../../../globalservice.service';
import { LazyreportdownloadService } from '../../../services/lazyreportdownload.service';
import { Page } from '../../../services/model';

@Component({
  selector: 'app-lazyreportpersentation',
  templateUrl: './lazyreportpersentation.component.html',
  styleUrls: ['./lazyreportpersentation.component.scss']
})
export class LazyreportpersentationComponent implements OnInit {

  params = {
    page: 0,
    size: 20,
    sort: 'request_date,desc',
    report_date: undefined,
    report_name: undefined
  }
  bsLocation=`${window.location.protocol}//${window.location.host}/`;
  pageData: Page<any> = new Page();
  constructor(
    private _reportservice: LazyreportdownloadService,
    private _global: GlobalserviceService,
    private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.queryParams.subscribe(param => {
      if (param['page'] != undefined) {
        this.params.page = Number(param['page']);
      }
      if (param['size'] != undefined) {
        this.params.size = Number(param['size']);
      }
      if (param['sort'] != undefined) {
        this.params.sort = param['sort'];
      }
      if (param['report_date'] != undefined) {
        this.params.report_date = param['report_date'];
      }
      if (param['report_name'] != undefined) {
        this.params.report_name = param['report_name'];
      }
      this.getData();
     
    });
  }

  ngOnInit() {
  }
  getData() {
    if (this.params.report_name == undefined || this.params.report_name == '') {
      this._reportservice.getPendingReport({ page: this.params.page, size: this.params.size, sort: this.params.sort }).subscribe(data => {
        this.pageData = data;
       
      });
    }
    else {
      this._reportservice.searchReport({ page: this.params.page, size: this.params.size, sort: this.params.sort, report_name: this.params.report_name, report_date: this.params.report_date }).subscribe(data => {
        this.pageData = data;
      });
    }
  }

  getSerachResult(){
    this.params.page=0;
    this.getData();
  }

  openReportLink(item){
    //href="{{bsLocation}}api/asyreport/{{item.id}}"
    window.open(`${this.bsLocation}api/asyreport/${item.id}`,`Reprot_${item.id}`,'menubar=0,resizable=0,scrollbars=0,titlebar=0,toolbar=0,top=0,left=0');
  }
}
