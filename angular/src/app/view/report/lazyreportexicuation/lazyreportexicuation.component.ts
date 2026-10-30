import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ReportwizardService } from '../../../services/reportwizard.service';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-lazyreportexicuation',
  templateUrl: './lazyreportexicuation.component.html',
  styleUrls: ['./lazyreportexicuation.component.scss']
})
export class LazyreportexicuationComponent implements OnInit {

  constructor(
    private spinner: NgxSpinnerService,
    private _report: ReportwizardService,private _global:GlobalserviceService) {

  }

  @ViewChild('reportsWindows', { static: false }) reportsWindows: ModalDirective;
  @ViewChild('reportTable', { static: false }) reportTable: ElementRef;
  @ViewChild('reportParam', { static: false }) reportParam: ModalDirective;

  reports = [];
  params = [];
  rpt;
  rptObjectKey = [];
  rptObjectList = [];
  searchitem = '';
  advancesearch = {
    report_name: '',
    report_description: '',
  
  };
  ngOnInit() {
    this.getReportWizards();
  }

  getReportWizards() {
    this.spinner.show();
    this._report.getAllReport().subscribe(data => {
      this.spinner.hide();
      this.reports = data;
    }, error => this.spinner.hide());
  }
  startExecutation(report) {
    this.params = [];
    this.rptObjectKey = [];
    this.rptObjectList = [];
    this.rpt = report;
    this.spinner.show();

    this._report.getParams(this.rpt.id).subscribe(data => {
      if (data.length == 0) {
        this._report.executeReport(this.rpt.id, []).subscribe(_data => {
          if (_data.length > 0) {
            this.rptObjectKey = Object.keys(_data[0]);
          }
          this.rptObjectList = _data;
          this.spinner.hide();
          this.reportsWindows.show();
        }, error => this.spinner.hide());
      }
      else {
        this.spinner.hide();
        this.params = data;
        this.reportParam.show();
      }
    }, error => this.spinner.hide());
  }
  exportToExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.reportTable.nativeElement);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    XLSX.writeFile(workbook, 'report.xlsx');
  }
  fetchReport() {
    this.spinner.show();
    this._report.executeReport(this.rpt.id, this.params).subscribe(_data => {
      this.reportParam.hide();
      if (_data.length > 0) {
        this.rptObjectKey = Object.keys(_data[0]);
      }
      this.rptObjectList = _data;
      this.spinner.hide();
      this.reportsWindows.show();
    }, error =>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    } );
  }
  searchReport(){
    if(this.advancesearch.report_name=='' && this.advancesearch.report_description==''){
      this._global.alert({type:'alert',message:"Please Enter valid Key for Search"}); 
    }else{
      this.spinner.show();
      this._report.getSearchResult(this.advancesearch).subscribe(data=>{
        this.reports=data;
        this.spinner.hide();
      })
    }
 
   
  }
  resetSearch(){
    this.advancesearch.report_name=''
    this.advancesearch.report_description=''
  }
}
