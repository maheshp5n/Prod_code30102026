//import { ReportwizardService } from './../../services/reportwizard.service';
import { Component, OnInit } from '@angular/core';
// import { ModalDirective } from 'ngx-bootstrap/modal';
// import { NgxSpinnerService } from 'ngx-spinner';
// import { ExpenseManagementStatus, ObjectDefinition } from '../../services/model';
// import { ExpenseManagementService } from '../../services/expenses-management.service';
// import { ReportService } from '../../services/report.service';
// import { DatePipe } from '@angular/common';
// import * as fileSaver from 'file-saver'; // npm i --save file-saver
// import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker/models';
// import * as XLSX from 'xlsx';
// import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  constructor(
    // private spinner: NgxSpinnerService,
    // private _report: ReportwizardService,private _global:GlobalserviceService
    
    ) {

  }

  // @ViewChild('reportsWindows', { static: false }) reportsWindows: ModalDirective;
  // @ViewChild('reportTable', { static: false }) reportTable: ElementRef;
  // @ViewChild('reportParam', { static: false }) reportParam: ModalDirective;

  // reports = [];
  // params = [];
  // rpt;
  // rptObjectKey = [];
  // rptObjectList = [];
  // searchitem = '';
  ngOnInit() {
    //this.getReportWizards();
  }

  // getReportWizards() {
  //   this.spinner.show();
  //   this._report.getAllReport().subscribe(data => {
  //     this.spinner.hide();
  //     this.reports = data;
  //   }, error => this.spinner.hide());
  // }
  // startExecutation(report) {
  //   this.params = [];
  //   this.rptObjectKey = [];
  //   this.rptObjectList = [];
  //   this.rpt = report;
  //   this.spinner.show();

  //   this._report.getParams(this.rpt.id).subscribe(data => {
  //     if (data.length == 0) {
  //       this._report.executeReport(this.rpt.id, []).subscribe(_data => {
  //         if (_data.length > 0) {
  //           this.rptObjectKey = Object.keys(_data[0]);
  //         }
  //         this.rptObjectList = _data;
  //         this.spinner.hide();
  //         this.reportsWindows.show();
  //       }, error => this.spinner.hide());
  //     }
  //     else {
  //       this.spinner.hide();
  //       this.params = data;
  //       this.reportParam.show();
  //     }
  //   }, error => this.spinner.hide());
  // }
  // exportToExcel() {
  //   const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.reportTable.nativeElement);
  //   const workbook: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
  //   XLSX.writeFile(workbook, 'report.xlsx');
  // }
  // fetchReport() {
  //   this.spinner.show();
  //   this._report.executeReport(this.rpt.id, this.params).subscribe(_data => {
  //     this.reportParam.hide();
  //     if (_data.length > 0) {
  //       this.rptObjectKey = Object.keys(_data[0]);
  //     }
  //     this.rptObjectList = _data;
  //     this.spinner.hide();
  //     this.reportsWindows.show();
  //   }, error =>{
  //     this.spinner.hide();
  //     this._global.alert({type:'alert',message:error.error.message});
  //   } );
  // }

}
