import { ModalDirective } from 'ngx-bootstrap/modal';
import { GlobalserviceService } from './../../../globalservice.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ReportwizardService } from './../../../services/reportwizard.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportLink, ReportWizard } from '../../../services/model';

@Component({
  selector: 'app-reportwizard',
  templateUrl: './reportwizard.component.html',
  styleUrls: ['./reportwizard.component.scss']
})
export class ReportwizardComponent implements OnInit {

  // Reportwizardresponse wizards = [];
  wizards:Array<ReportWizard>=[]
  reporLinkArra:Array<ReportLink>=[];
  nwiard = {
    id:'',
    reportName: '',
    reportQuery: '',
    reportDescription: '',
    reportType: '',
    presentationType: '',
    params: [],
    executers: [],
    reportLinkId:'',
    report_name:'',
    report_description:'',
    bothvalues:''
  };
  nwizardParam = {
    paramIdentifier: '',
    paramDisplay: '',
    paramType: 'Text'
  };
  nwizardExccec = {
    executerType: 'SELECT',
    executer: ''
  };
  edit = false;
  execList = [];
  id = 0;

  @ViewChild('reportWizard', { static: false }) reportWizard: ModalDirective;

  constructor(private _report: ReportwizardService, private _ngx: NgxSpinnerService, private _global: GlobalserviceService) { }

  ngOnInit() {
    this.getWizard();
    this._edit=false
  }
  onPoupclose() {
    this.id = 0;
    this.edit = false;
    this.nwiard = {
      id:'',
      reportName: '',
      reportQuery: '',
      reportDescription: '',
      reportType: '',
      presentationType: '',
      params: [],
      executers: [],
      reportLinkId:'',
      report_name:'',
      report_description:'',
      bothvalues:''
    };
    this.nwizardParam = {
      paramIdentifier: '',
      paramDisplay: '',
      paramType: 'Text'
    };
    this.nwizardExccec = {
      executerType: 'SELECT',
      executer: ''
    };
  }

  getWizard() {
    this._ngx.show();
   this.wizards=[];
    this._report.getWizard().subscribe(data => {
      this.wizards = data;
      this._ngx.hide();
    }, error => {
      this._ngx.hide();
    });
  }
  _edit:boolean=false;
  editrecord(w) {
    this._edit=true;
    this.nwiard = w;
    this.id = w.id;
    this.getParams(w.id);
    this.getExecuters(w.id);
    this.getReportwizards(w.reportLink)
    this.reportWizard.show();
    this.edit = true;
  }
  getParams(id) {
    this._report.getParams(id).subscribe(data => this.nwiard.params = data);
  }
  getExecuters(id) {
    this._report.getExecuter(id).subscribe(data => this.nwiard.executers = data);
  }
  addParam() {
    const len = this.nwiard.params.length;
    let f = false;
    for (let i = 0; i < len; i++) {
      if (this.nwiard.params[i].paramIdentifier == this.nwizardParam.paramIdentifier) {
        f = true;
        break;
      }
    }
    if (!f) {
      this.nwiard.params.push(
        {
          paramIdentifier: this.nwizardParam.paramIdentifier,
          paramDisplay: this.nwizardParam.paramDisplay,
          paramType: this.nwizardParam.paramType
        }
      );
    }
  }
  addExecuter() {
    const ln = this.nwiard.executers.length;
    let f = false;
    for (let i = 0; i < ln; i++) {
      if (this.nwiard.executers[i].executerType == this.nwizardExccec.executerType && this.nwiard.executers[i].executer == this.nwizardExccec.executer) {
        f = true;
      }
    }
    if (!f) {
      this.nwiard.executers.push({
        executerType: this.nwizardExccec.executerType,
        executer: this.nwizardExccec.executer
      });
    }
  }
  removeParam(param) {
    const temp = [];
    const len = this.nwiard.params.length;
    for (let i = 0; i < len; i++) {
      if (this.nwiard.params[i].paramIdentifier != param.paramIdentifier) {
        temp.push(this.nwiard.params[i]);
      }
    }
    this.nwiard.params = temp;
  }
  removeExecuter(rpt) {
    const len = this.nwiard.executers.length;
    const temp = [];
    for (let i = 0; i < len; i++) {
      if (this.nwiard.executers[i].executerType == rpt.executerType && this.nwiard.executers[i].executer == rpt.executer) {

      } else {
        temp.push(this.nwiard.executers[i]);
      }
    }
    this.nwiard.executers = temp;
  }
  saveDetails() {
   
    if (!this.edit) {
      // this._ngx.show();
      // this._report.postReportWizard(this.nwiard).subscribe(data => {
      //   this._ngx.hide();
      //   this.getWizard();
      //   this._global.alert({ type: '', message: "Successfully Submitted" });
      //   this.reportWizard.hide();
     

      // }, error => {
      //   this._ngx.hide();
      //   this._global.alert({ type: 'alert', message: error.error.message });
      // });
    } else {
      this._ngx.show();
      this._report.editReportWizard(this.id, this.nwiard).subscribe(data => {
        this._ngx.hide();
        this.getWizard();
        this._global.alert({ type: '', message: "Successfully Update" });
        this.reportWizard.hide();
      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
  }

  onDropDownChange() {
    this._report.getUserRoleAndUsers(this.nwizardExccec.executerType).subscribe(data => this.execList = data);
  }

  getLabelName(id) {
    const len = this.execList.length;
    for (let i = 0; i < len; i++) {
      if (this.execList[i].id == id) {
        return this.execList[i].value;
      }
    }
  }
  deleteReport(w){
    if(confirm('Are you sure?')){
      this._ngx.show();
      this._report.deletereport(w.id).subscribe(data=>{
        this._ngx.hide();
        this.getWizard();
      },error=>{
        this._ngx.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
  }
  isDashborad:boolean=false;
  getReportwizards(reportype){
    console.log(reportype)
    if(reportype=="Dashboard"){
      this.isDashborad=true;
      this.reporLinkArra=[];
      this._ngx.show();
      this._report.getTypes().subscribe(data=>{
        this._ngx.hide();
        const temp=[];
      
        data.forEach(e=>{
          let rp=new ReportLink();
          rp.id=e.id;
          rp.report_name=e.report_name;
          rp.report_description=e.report_description
          rp.bothvalues=rp.report_name+' '+rp.report_description;
          temp.push(rp);
         
        });
        this.reporLinkArra=temp;
        console.log(this.reporLinkArra)
     
      })
    }
    else{
      this.isDashborad=false;
    }
  

  }
  reportWizardHide(){
    this.reportWizard.hide()
  }
  CreatedRepoted($event){
    this.getWizard();
  }
}
