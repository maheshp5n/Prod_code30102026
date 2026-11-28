import { ReportwizardService } from '../../services/reportwizard.service';
import { Input, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from '../../globalservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboardreport',
  templateUrl: './dashboardreport.component.html',
  styleUrls: ['./dashboardreport.component.scss']
})
export class DashboardreportComponent implements OnInit {

  @ViewChild('reportsWindows') reportsWindows: ModalDirective;
  @ViewChild('reportParam') reportParam: ModalDirective;

  objKeys = [];
  objectValues = [];
  chartValeu = [];
  isExecute:boolean=false;
  _repoWizard;
  @Input() set report(_report) {
    if(_report.reportLinkId!==null){
    this.isExecute=true;
    }
    else{
      this.isExecute=false;
    }

    this._repoWizard = _report;
    this.getReportResult();

  }
  get report() {
    return this._repoWizard;
  }

  constructor(private _report: ReportwizardService,private spinner:NgxSpinnerService,
    private _global:GlobalserviceService,private _router: Router) { }

  ngOnInit() {

  }
  getReportResult() {
    this.objKeys = [];
    this.objectValues = [];
    this.chartValeu = [];
    this._report.executeReport(this._repoWizard.id, []).subscribe(data => {
      if (data.length > 0) {
        this.objKeys = Object.keys(data[0]);
      }
      this.objectValues = data;
      // if (this._repoWizard.presentationType == 'ColumnChart') {
      data.forEach(d => {
        let tem = [];
        this.objKeys.forEach(o => {
          tem.push(d[o]);
        });
        this.chartValeu.push(tem);
      });
      //console.log(this.chartValeu);
      // }

    }, error => { });
  }
  rpt;
  params=[];
  rptObjectKey = [];
  rptObjectList = [];
  searchitem = '';
  startExecutation(report) {
    this.params = [];
    this.rptObjectKey = [];
    this.rptObjectList = [];
    this.rpt = report;
    this.spinner.show();
    this._report.getParams(this.rpt.reportLinkId).subscribe(data => {
      if (data.length == 0) {
        this._report.executeReport(this.rpt.reportLinkId, []).subscribe(_data => {
          if (_data.length > 0) {
            this.rptObjectKey = Object.keys(_data[0]);
            this._router.navigate(['/report/reportp']);
          }
          this.rptObjectList = _data;
         
          this.spinner.hide();
          // this.reportsWindows.show();
        }, error =>{ 
          this.spinner.hide();
          this._global.alert({type:'alert',message:error.error.message});
        }
        
        );
      }
      else {
        this.spinner.hide();
        this.params = data;
        this.reportParam.show();
      }
    }, error =>{ this.spinner.hide()
      this._global.alert({type:'alert',message:error.error.message});}
    
    );
  }
  fetchReport() {
    this.spinner.show();
    this._report.executeReport(this.rpt.reportLinkId, this.params).subscribe(_data => {
      this.reportParam.hide();
      if (_data.length > 0) {
        this.rptObjectKey = Object.keys(_data[0]);

      }
      this.rptObjectList = _data;
      this._router.navigate(['/report/reporte']);
      this.spinner.hide();
      this.reportsWindows.show();
    }, error =>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    } );
  }
}
