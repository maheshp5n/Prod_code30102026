import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { ReportLink, reportRequest, ReportWizard } from '../../services/model';
import { ReportService } from '../../services/report.service';
import { ReportwizardService } from '../../services/reportwizard.service';

@Component({
  selector: 'app-createreport',
  templateUrl: './createreport.component.html',
  styleUrls: ['./createreport.component.css']
})
export class CreatereportComponent implements OnInit {
  Executers:Array<any>=[];
  parameters:Array<any>=[];
  _isEdit:boolean;
  @Input()
  set reportResp(res: ReportWizard) {
    // this.reportres = res;
    this.nwiard.id=res.id;
    this.nwiard.executers=res.executers;
    this.nwiard.params=res.params;
    this.nwiard.reportQuery=res.reportQuery;
    this.nwiard.reportName=res.reportName;
    this.nwiard.reportDescription=res.reportDescription;
    this.nwiard.reportType=res.reportType;
    this.nwiard.presentationType=res.presentationType;
    this.nwiard.reportLinkId=res.reportLinkId;
    this._report.getParams(this.nwiard.id).subscribe(data=> this.nwiard.params = data);
  
    this._report.getExecuter(this.nwiard.id).subscribe(data => 
      this.nwiard.executers = data
      );
    if(this.nwiard.reportType=="Dashboard"){
      this._report.getTypes().subscribe(data=>{
        this._ngx.hide();
        const temp=[];
        data.forEach(e=>{
          this.reporLinkArra.push({id:e.id,report_name:e.report_name,report_description:e.report_description,bothvalues:e.bothvalues});
          let rp=new ReportLink();
          rp.report_name=e.report_name;
          rp.report_description=e.report_description
          rp.bothvalues=rp.report_name+' '+rp.report_description;
          temp.push(rp);
         
        });
        this.reporLinkArra=temp;
        console.log(this.reporLinkArra)
     
      });
      
      this.reporLinkArra.forEach(e=>{
        if(this.nwiard.reportLinkId==e.id){
          let rp=new ReportLink();
          rp.id=e.id
          rp.report_name=e.report_name;
          rp.report_description=e.report_description
          rp.bothvalues=rp.report_name+' '+rp.report_description;
          this.reporLinkArra.push(rp); }
       
      });
    }
  }

  @Output() onReportCreated: EventEmitter<string> = new EventEmitter<string>();

  @Output() onCancle: EventEmitter<string> = new EventEmitter<string>();
  get reportResp(): ReportWizard {
    return this.reportres;
  }
  @Input()
  set EditMode(isedit:boolean){
    this._isEdit=isedit;
  }
  get EditMode():boolean{
    return this._isEdit;
  }
  constructor(private _report:ReportwizardService,private _ngx:NgxSpinnerService,private _global:GlobalserviceService) { }
  nwiard:reportRequest=new reportRequest();
  _isEditMode:boolean;
  reportres:ReportWizard=new ReportWizard();
  execList=[];
  reportId:number;
  nwizardParam = {
    paramIdentifier: '',
    paramDisplay: '',
    paramType: 'Text'
  };
  nwizardExccec = {
    executerType: 'SELECT',
    executer: ''
  };
  ngOnInit(): void {
  }
  isDashborad:boolean=false;
  reporLinkArra:Array<ReportLink>=[];
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
          rp.id=e.id
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
  saveDetails(){
    console.log(this.nwiard);
    if(!this._isEdit){
      this._ngx.show();
      
      this._report.postReportWizard(this.nwiard).subscribe(data => {
        this._ngx.hide();
        this.onReportCreated.emit('')
        this._global.alert({ type: '', message: "Successfully Submitted" });
     

      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      });
    }
    else{
      this._ngx.show();
      this._report.editReportWizard(this.nwiard.id, this.nwiard).subscribe(data => {
        this._ngx.hide();
        this.onReportCreated.emit('')
        this._global.alert({ type: '', message: "Successfully Updated" });
  
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
  addParam() {
    // this.nwiard.params=[];
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
    // this.nwiard.executers=[];
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
  getParams(id) {
    this._ngx.show();
    this._report.getParams(id).subscribe(data => this.parameters = data);
    this._ngx.hide();
  }
  getExecuters(id) {
    this._ngx.show();
    this._report.getExecuter(id).subscribe(data => this.Executers = data);
    this._ngx.hide();
  }
  cancelData() {
    this.onCancle.emit('cancled');
  }

}
