import { Component, OnInit, ViewChild } from '@angular/core';
import { Sysconfigparamservice } from '../../../services/sysconfig.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';


@Component({
  selector: 'app-smsserverconf',
  templateUrl: './smsserverconf.component.html',
  styleUrls: ['./smsserverconf.component.scss']
})
export class SmsserverconfComponent implements OnInit {

  smsSettings:Array<any>=[];
  @ViewChild('smsServerSetting',{static:false})smsServerSetting:ModalDirective;
  @ViewChild('alertModal',{static:false})alertModal:ModalDirective;

  nsmsSetting={
    url:'',
    method:'GET',
    headers:[],
    params:[],
    id:0,
    editMode:false
  }
  header={
    keyName:'',
    keyValue:''
  };
  param={
    keyValue:'',
    paramType:'static',
    keyName:''
  }
  alertTypeDetails={
    type:'danger',
    message:''
  }
  constructor(private _sysconfig:Sysconfigparamservice,private _ngx:NgxSpinnerService) { }

  ngOnInit() {
    this.getAllServerSetting();
  }

  getAllServerSetting(){
    this._ngx.show();
    this._sysconfig.getSmsSetting().subscribe(data=>
      {
        this.smsSettings=data;
        this._ngx.hide();
      }
      ,error=>this._ngx.hide());
  }
  addHeaders(){
    let found=false;
    for(let i=0;i<this.nsmsSetting.headers.length;i++){
      if(this.nsmsSetting.headers[i].keyName==this.header.keyName){
        found=true;
      }
    }
    if(!found){
      this.nsmsSetting.headers.push({keyName:this.header.keyName,keyValue:this.header.keyValue});
      this.header.keyName='';
      this.header.keyValue='';
    }
  }
  addParams(){
    let found=false;
    for(let i=0;i<this.nsmsSetting.params.length;i++){
      if(this.nsmsSetting.params[i].keyName==this.param.keyName){
        found=true;
      }
    }
    if(!found){
      if(this.param.paramType=='dyna'){
        this.param.keyValue=`{{${this.param.keyValue}}}`;
      }
      this.nsmsSetting.params.push({keyName:this.param.keyName,paramType:this.param.paramType,keyValue:this.param.keyValue});
      this.param.keyName='';
      this.param.keyValue='';
      this.param.paramType='static';
    }
  }
  removeHeader(keyName){
    let temp=[];
    for(let i=0;i<this.nsmsSetting.headers.length;i++){
      if(this.nsmsSetting.headers[i].keyName!=keyName){
        temp.push(this.nsmsSetting.headers[i]);
      }
    }
    this.nsmsSetting.headers=temp;
  }
  removeParam(keyName){
    let temp=[];
    for(let i=0;i<this.nsmsSetting.params.length;i++){
      if(this.nsmsSetting.params[i].keyName!=keyName){
        temp.push(this.nsmsSetting.params[i]);
      }
    }
    this.nsmsSetting.params=temp;
  }
  savesmsSetting(){
    if(this.nsmsSetting.url==undefined||this.nsmsSetting.url==null||this.nsmsSetting.url==''){
      this.showError(`Please provide url`);
      return;
    }
    this._ngx.show();
    if(!this.nsmsSetting.editMode){
      this._sysconfig.postSmsServer(this.nsmsSetting).subscribe(data=>{
        this._ngx.hide();
        this.smsServerSetting.hide();
        this.showMessage('SMS Server setting updated');
        this.getAllServerSetting();
      },error=>{
        this._ngx.hide();
        this.showError(`Error : ${error.error.message}`)
      });
    }
    if(this.nsmsSetting.editMode){
      this._sysconfig.updateSmsServersetting(this.nsmsSetting.id,this.nsmsSetting).subscribe(data=>{
        this._ngx.hide();
        this.smsServerSetting.hide();
        this.showMessage(`SMS Server setting updated`);
        this.getAllServerSetting();
      },error=>{
        this._ngx.hide();
        this.showMessage(`Error : ${error.error.message}`);
      });
    }
   
  }
  onHide(){
      this.nsmsSetting.headers=[];
      this.nsmsSetting.method='GET';
      this.nsmsSetting.params=[];
      this.nsmsSetting.url='';
      this.nsmsSetting.id=0;
      this.nsmsSetting.editMode=false;
      this.header.keyName='';
      this.header.keyValue='';
      this.param.keyName='';
      this.param.keyValue='';
      this.param.paramType='static';
  }
  showMessage(message){
    this.alertTypeDetails.message=message;
    this.alertTypeDetails.type='success'
    this.alertModal.show();
  }
  showError(message){
    this.alertTypeDetails.message=message;
    this.alertTypeDetails.type='danger'
    this.alertModal.show();
  }
  updateEnableDisable(id,flag){
    this._ngx.show();
    this._sysconfig.updateSmsEnableDisable(id,flag).subscribe(data=>{
      this._ngx.hide();
      this.getAllServerSetting();
    },error=>{
      this._ngx.hide();
      this.showError(`Error : ${error.error.message}`);
    });
  }
  getHeaders(id){
    this._ngx.show();
    this._sysconfig.getSmsSettingHeaders(id).subscribe(data=>{
      this.nsmsSetting.headers=[];
      this._ngx.hide();
      data.forEach(d=>{
        this.nsmsSetting.headers.push({keyName:d.key_header,keyValue:d.value_header});
      });
    },error=>this._ngx.hide());
  }
  getParams(id){
    this._ngx.show();
    this._sysconfig.getSmsSettingParams(id).subscribe(data=>{
      this.nsmsSetting.params=[];
      data.forEach(d=>{
        this.nsmsSetting.params.push({keyName:d.key_param,keyValue:d.value_param,paramType:d.param_type});
      });
      this._ngx.hide();
    },error=>this._ngx.hide());
  }
  showsmsSettingDetails(id){
    for(let i=0;i<this.smsSettings.length;i++){
      if(this.smsSettings[i].id==id){
        this.nsmsSetting.url=this.smsSettings[i].url;
        this.nsmsSetting.method=this.smsSettings[i].method;
        this.getHeaders(id);
        this.getParams(id);
        this.smsServerSetting.show();
        this.nsmsSetting.editMode=true;
        this.nsmsSetting.id=id;
        break;
      }
    }
  }
}
