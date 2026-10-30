import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { NgxSpinnerService } from 'ngx-spinner';
import { DashboardService } from '../../services/dashoboard.services';
import { ReportwizardService } from '../../services/reportwizard.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  sigmentOnedata: Array<any> = [];
  sigmentOnetotal: any;
  qrcforactive: Array<any> = [];
  qrcforactiveChart: Array<any> = [];
  top5Complaintdata: Array<any> = [];
  top5ComplantGraphData: Array<any> = [];
  agingOfCloserData: Array<any> = [];
  agingOfCloserGraphData = [];
  paidRepairData: Array<any> = [];
  dashboardReport = [];
  columnChartOption = {
    colors: ['#e0440e', '#e6693e', '#ec8f6e', '#f3b49f', '#f6c7b6'],
    is3D: true
  }
  monthserise = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];

  totalforQrc = { mtd: 0, ytd: 0 };
  totalforcomp = { mtd: 0, ytd: 0 };
  totalAgingOfCloser = 0;
  totalPaidre = {
    totalMonthlyCount: 0,
    totalMonthlyRevenue: 0,
    totalYearlyCount: 0,
    totalYearlyRevenue: 0
  }

  currentMonth: string = '';

  constructor(private spinner: NgxSpinnerService, private _dashboardServce: DashboardService, private _report: ReportwizardService) { }

  ngOnInit() {
    // this.getSigmentOneData();
    // this.getActiveQrcCode();
    // this.getTop5Complaints();
    // this.getAgingOfCloser();
    // this.getPaidRepair();
    // let d = new Date();
    // this.currentMonth = `${this.monthserise[d.getMonth()]}'${d.getFullYear()}`;
    this.getDashboardReport();
  }

  getSigmentOneData() {
    this._dashboardServce.getCasefirst().subscribe(data => {
      this.sigmentOnedata = data;
      this.gettotalCaseRegistred();
    });
  }
  gettotalCaseRegistred(): any {
    let total = {
      current: 0,
      ytd: 0
    }
    for (let i = 0; i < this.sigmentOnedata.length; i++) {
      total.current += Number(this.sigmentOnedata[i].curretn_month);
      total.ytd += Number(this.sigmentOnedata[i].current_year);
    }
    this.sigmentOnetotal = total;
  }
  getActiveQrcCode() {
    this._dashboardServce.getQRCActiveCustomer().subscribe(data => {
      this.qrcforactive = data;
      data.forEach(d => {
        this.totalforQrc.mtd += Number(d.monthly);
        this.totalforQrc.ytd += Number(d.yearly);
        this.qrcforactiveChart.push([`${d.case_status_name}#${d.case_stage_name}`, d.yearly]);
      });
    });
  }
  getTop5Complaints() {
    this._dashboardServce.getTop5Complaints().subscribe(data => {
      let totalMonthly = 0;
      this.top5Complaintdata = [];
      this.top5ComplantGraphData = [];
      //  this.top5ComplantGraphData.push(['Problem Name', '%Age', { role: 'style' }]);
      for (let i = 0; i < data.length; i++) {
        totalMonthly += Number(data[i].monthly);
        this.totalforcomp.mtd += Number(data[i].monthly);
        this.totalforcomp.ytd += Number(data[i].YTD);
      }
      for (let i = 0; i < data.length; i++) {
        let per = ((data[i].monthly * 100) / totalMonthly);
        this.top5Complaintdata.push({ 'problem_name': data[i].problem_name, 'monthly': data[i].monthly, 'page': per, 'ytd': data[i].YTD });
        this.top5ComplantGraphData.push([data[i].problem_name, per]);
      }
    });
  }
  getAgingOfCloser() {
    this._dashboardServce.getAgingOfCloserComplaint().subscribe(data => {
      let total = 0;
      this.agingOfCloserData = [];
      for (let i = 0; i < data.length; i++) {
        total += data[i].no_of_case_registred_monthly;
      }
      this.totalAgingOfCloser = total;
      for (let i = 0; i < data.length; i++) {
        let per = ((data[i].no_of_case_registred_monthly * 100) / total);
        this.agingOfCloserData.push({ 'catagory': data[i].no_of_days, 'mtd': data[i].no_of_case_registred_monthly, 'age': per });
        this.agingOfCloserGraphData.push([data[i].no_of_days, per]);
      }
      console.log(this.agingOfCloserGraphData);
    });
  }
  getPaidRepair() {
    this._dashboardServce.getPaidRepairDetails().subscribe(data => {
      let lastcatname = '';
      this.paidRepairData = [];
      for (let i = 0; i < data.length; i++) {
        this.totalPaidre.totalMonthlyCount += Number(data[i].count_case_current_month);
        this.totalPaidre.totalMonthlyRevenue += Number(data[i].current_month_revenue);
        this.totalPaidre.totalYearlyCount += Number(data[i].count_case_current_year);
        this.totalPaidre.totalYearlyRevenue += Number(data[i].current_year_revenue);

        if (i == 0) {
          this.paidRepairData.push({ 'case_types_name': data[i].case_types_name, 'activity_name': data[i].activity_name, 'count_case_current_month': data[i].count_case_current_month, 'current_month_revenue': data[i].current_month_revenue, 'count_case_current_year': data[i].count_case_current_year, 'current_year_revenue': data[i].current_year_revenue });
        }
        else {
          if (lastcatname == data[i].case_types_name) {
            this.paidRepairData.push({ 'case_types_name': '', 'activity_name': data[i].activity_name, 'count_case_current_month': data[i].count_case_current_month, 'current_month_revenue': data[i].current_month_revenue, 'count_case_current_year': data[i].count_case_current_year, 'current_year_revenue': data[i].current_year_revenue });
          }
          else {
            this.paidRepairData.push({ 'case_types_name': data[i].case_types_name, 'activity_name': data[i].activity_name, 'count_case_current_month': data[i].count_case_current_month, 'current_month_revenue': data[i].current_month_revenue, 'count_case_current_year': data[i].count_case_current_year, 'current_year_revenue': data[i].current_year_revenue });
          }

        }
        lastcatname = data[i].case_types_name;
      }
    });
  }

  getDashboardReport() {
    this._report.getAllReport('Dashboard').subscribe(data => {
      this.dashboardReport = data;
    });
  }
}
