import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as XLSX from 'xlsx';
import { ElementRef } from '@angular/core';
import { ViewChild } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { Loginservices } from '../../services/login.services';

@Component({
  selector: 'app-qbrowser',
  templateUrl: './qbrowser.component.html',
  styleUrls: ['./qbrowser.component.scss']
})
export class QbrowserComponent implements OnInit {

  tblList = [];

  qry = '';
  dbkeyname = '';

  queryresult = [];
  queryheaders = [];
  tblsearch = '';

  lm = {
    limit: true,
    limitValue: 1000
  };
  lastQuery = '';
  usr;
  error = '';
  @ViewChild('reportTable') reportTable: ElementRef;

  constructor(private http: HttpClient, private login: Loginservices) {
    this.geturrentUser();
  }

  geturrentUser() {
    this.usr = this.login.getLoggedInUser();
  }

  ngOnInit() {
    this.getAllTables();
  }
  getAllTables() {
    this.exeQuery('show tables').subscribe(data => {
      if (data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach(d => {
          this.tblList.push({ tblname: d[keys[0]], exp: false, clm: [] });
        });
      }
    }, error => this.error = error.error.message);
  }
  describeTable(tablename) {
    this.exeQuery(`describe ${tablename}`).subscribe(data => {
      this.tblList.forEach(d => {
        if (d.tblname == tablename) {
          d.exp = true;
          d.clm = data;
        }
      });
    }, error => this.error = error.error.message);

  }

  getTableData(tblname) {
    this.queryheaders = [];
    this.queryresult = [];
    let qr = `select * from ${tblname}`;
    this.lastQuery = qr;
    if (this.lm.limit) {
      qr += ` limit ${this.lm.limitValue}`;
    }
    this.exeQuery(qr).subscribe(data => {
      if (data.length > 0) {
        this.queryheaders = Object.keys(data[0]);
        this.queryresult = data;
      }
    }, error => this.error = error.error.message);
  }

  exeQuery(stringdata): Observable<Array<any>> {
    this.error = '';
    let s;
    try {
      const rawkey = 'raushan123452146';
      const iv = '1234567890123456';

      s = CryptoJS.AES.encrypt(stringdata, rawkey, { keySize: 16, iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 }).toString();
      console.log(s);
    } catch (e) {
      console.log(e);
    }
    return this.http.post<Array<any>>(`api/object/l`, { q: stringdata });
  }
  getQueryResult() {
    this.queryheaders = [];
    this.queryresult = [];
    this.lastQuery = this.qry;
    if (this.lm.limit) {
      if ((this.qry.startsWith('select') || this.qry.startsWith('SELECT')) && this.qry.indexOf(' limit ') == -1) {
        this.qry += ` limit ${this.lm.limitValue}`;
      }
    }
    this.exeQuery(this.qry).subscribe(data => {
      if (data.length > 0) {
        this.queryheaders = Object.keys(data[0]);
        this.queryresult = data;
      }
    }, error => this.error = error.error.message);
  }

  hidetableDetails(tblname) {
    this.tblList.forEach(d => {
      if (d.tblname == tblname) {
        d.exp = false;
      }
    });
  }
  exportToExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.reportTable.nativeElement);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    XLSX.writeFile(workbook, 'report.xlsx');
  }
  refreshQuery() {
    this.queryheaders = [];
    this.queryresult = [];

    let r = this.lastQuery;
    if (this.lm.limit) {
      if (r.indexOf(' limit ') == -1) {
        r += ` limit ${this.lm.limitValue}`;
      }
    }
    this.exeQuery(r).subscribe(data => {
      if (data.length > 0) {
        this.queryheaders = Object.keys(data[0]);
        this.queryresult = data;
      }
    }, error => this.error = error.error.message);
  }
}
