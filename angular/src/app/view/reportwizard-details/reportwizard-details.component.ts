import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { ReportLink, ReportWizard } from '../../services/model';
import { ReportwizardService } from '../../services/reportwizard.service';


@Component({
  selector: 'app-reportwizard-details',
  templateUrl: './reportwizard-details.component.html',
  styleUrls: ['./reportwizard-details.component.css']
})
export class ReportwizardDetailsComponent implements OnInit {
  
  @ViewChild('reportWizard') reportWizard: ModalDirective;
   wizards:any;
  execList=[];
  reportid:any;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  parameters:Array<any>=[];
  Executers:Array<any>=[];
  message:string;
  // CREATE_ACCOUNT_HEADING: string = 'Create City';
  EDIT_HEADING: string = 'Edit Report'
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  queryinfoDisplay={
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  nwizardParam = {
    paramIdentifier: '',
    paramDisplay: '',
    paramType: 'Text'
  };
  nwizardExccec = {
    executerType: 'SELECT',
    executer: ''
  };
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
  // reportresponse={
  //   id:'',
  //   reportName: '',
  //   reportQuery: '',
  //   reportDescription: '',
  //   reportType: '',
  //   presentationType: '',
  //   params: [],
  //   executers: [],
  //   reportLinkId:'',
  //   report_name:'',
  //   report_description:''
  // }
  wizardDetail:ReportWizard=new ReportWizard();
  constructor(private _report:ReportwizardService,private route:Router,private _activatedRoute: ActivatedRoute,
    private _ngx: NgxSpinnerService, private _global: GlobalserviceService) {
    this._activatedRoute.params.subscribe(pram => {
      this.reportid = pram['id'];
    });
    this.getWizard();
    this.getExecuters(this.reportid);
    this.getParams(this.reportid);
   
   }

  ngOnInit() {
    
  }
  
  getWizard() {
    // this._ngx.show();
    this._report.getWizard().subscribe(data => {
     data.forEach(e=>{
       if(this.reportid==e.id){
         console.log(e.report_description);
        this.wizardDetail=e;
        console.log(this.wizardDetail);
        this.getExecuters(e.id);
        this.getParams(e.id);
     
       }

     })
    
    
    
      // this._ngx.hide();
    }, error => {
      // this._ngx.hide();
    });
  }
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getWizard();
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
  
    }
    return false;
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  queryInfoDisplay(){
    this.queryinfoDisplay.open = !this.queryinfoDisplay.open
    if (this.queryinfoDisplay.open) {
      this.queryinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.queryinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

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
  _edit:boolean;
  editrecord(w) {
    this._edit=true;
    this.nwiard = w;
    console.log(w);
    this.reportid=w.id;
    this.nwiard.report_name=w.report_name;
    this.nwiard.report_description=w.report_description
    this.nwiard.bothvalues=this.nwiard.report_name+' '+this.reportid.report_description;
   w.reportDescription;
     w.reportLinkId;
     w.reportName;
    this._ngx.show();
  //  this._report.getParams(w.id).subscribe(data=> this.nwiard.params = data);
  
  //     this._report.getExecuter(w.id).subscribe(data => 
  //       this.nwiard.executers = data
  //       );
       
    this.reportWizard.show();
    this._ngx.hide();
    this.edit = true;
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
  // saveDetails(){
  //   this._ngx.show();
  //   this._report.editReportWizard(this.reportid, this.nwiard).subscribe(data => {
  //     this._ngx.hide();
  //     this._global.alert({ type: '', message: "Successfully Updated" });
  //     this.reportWizard.hide();
  //     this.getWizard();
  
  //   }, error => {
  //     this._ngx.hide();
  //     this._global.alert({ type: 'alert', message: error.error.message });
  //   });
  // }
  onPoupclose() {
    // this.reportid = 0;
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
  deleteReport(w){
    if(confirm('Are you sure?')){
      this._ngx.show();
      this._report.deletereport(w.id).subscribe(data=>{
        this._ngx.hide();
        // this.getWizard();
        this.route.navigate(['/qbrowser']);
      },error=>{
        this._ngx.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
  }
  reportWizardHide(){
    this.reportWizard.hide()
  }
  CreatedRepoted($event){
    console.log($event)
    this.getWizard()
  }
}
