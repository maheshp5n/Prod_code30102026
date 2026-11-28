import { Component, OnInit, ViewChild } from '@angular/core';
import { Sysconfigparamservice } from '../../../services/sysconfig.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-mailserverconf',
  templateUrl: './mailserverconf.component.html',
  styleUrls: ['./mailserverconf.component.scss']
})
export class MailserverconfComponent implements OnInit {

  allMailServers:Array<any>=[];
  @ViewChild('newmailServerModal')newmailServerModal:ModalDirective;
  nServerDetails={
    id:0,
    smtp:'',
    emailId:'',
    requiredAuth:true,
    requiredSSL:true,
    tslStart:false,
    password:'',
    confirmPassword:'',
    smtpPort:465,
    sslPort:465,
    otherPropertyes:[],
    editMode:false
  }
  property={
    propertyName:'',
    propertyValue:''
  }
  
  constructor(private _sysconfig:Sysconfigparamservice,private _ngx:NgxSpinnerService) { }

  ngOnInit() {
    this.getAllMailServer();
  }
  getAllMailServer(){
    this._ngx.show();
    this._sysconfig.getMailServers().subscribe(data=>{
      this.allMailServers=data;
      this._ngx.hide();
    },error=>{this._ngx.hide()});
  }
  addProperty(){
    if(this.property.propertyName==undefined||this.property.propertyName==null||this.property.propertyName==''){
      return;
    }
    if(this.property.propertyValue==undefined||this.property.propertyValue==null||this.property.propertyValue==''){
      return;
    }
    let found=false;
    for(let i=0;i<this.nServerDetails.otherPropertyes.length;i++){
      if(this.nServerDetails.otherPropertyes[i].propertyName==this.property.propertyName){
        found=true;
        break;
      }
    }
    if(!found){
      this.nServerDetails.otherPropertyes.push({propertyName:this.property.propertyName,propertyValue:this.property.propertyValue});
      this.property.propertyName='';
      this.property.propertyValue='';
    }
    
  }
  removeProperty(propertyName){
    let temp=[];
    for(let i=0;i<this.nServerDetails.otherPropertyes.length;i++){
      if(this.nServerDetails.otherPropertyes[i].propertyName!=propertyName){
        temp.push(this.nServerDetails.otherPropertyes[i]);
      }
    }
    this.nServerDetails.otherPropertyes=temp;
  }
  saveServerSetting(){
    if(this.nServerDetails.smtp==undefined||this.nServerDetails.smtp==null||this.nServerDetails.smtp==''){
      alert(`Please provide SMTP Server`);
      return;
    }
    if(this.nServerDetails.emailId==undefined||this.nServerDetails.emailId==null||this.nServerDetails.emailId==''){
      alert(`Please provide user email Id`);
      return;
    }
    if(this.nServerDetails.requiredAuth){
      if(this.nServerDetails.password==undefined||this.nServerDetails.password==null||this.nServerDetails.password==''){
        alert(`Please provide password`);
        return;
      }
      if(this.nServerDetails.password!=this.nServerDetails.confirmPassword){
        alert(`Password not matching to confirm password`);
        return;
      }
    }
    this._ngx.show();
    if(!this.nServerDetails.editMode){
      this._sysconfig.saveMailServer(this.nServerDetails).subscribe(data=>{
        this._ngx.hide();
        this.newmailServerModal.hide();
        this.getAllMailServer();
      },error=>{
        this._ngx.hide();
        alert(`Error ${error.error.message}`);
      });
    }
    if(this.nServerDetails.editMode){
      this._sysconfig.updateMailServer(this.nServerDetails.id,this.nServerDetails).subscribe(data=>{
        this._ngx.hide();
        this.newmailServerModal.hide();
        this.getAllMailServer();
      },error=>{
        this._ngx.hide();
        alert(`Error ${error.error.message}`);
      });
    }
  }
  enableDisable(id,flag){
    this._ngx.show();
    this._sysconfig.updateMailerActive(id,flag).subscribe(data=>{
      this._ngx.hide();
      this.getAllMailServer();
    },error=>this._ngx.hide());
  }
  onClickEdit(id){
    for(let i=0;i<this.allMailServers.length;i++){
      if(this.allMailServers[i].id==id){
        this.nServerDetails.id=id;
        this.nServerDetails.editMode=true;
        this.nServerDetails.emailId=this.allMailServers[i].default_user_name;
        this.nServerDetails.requiredAuth=this.allMailServers[i].required_authentication;
        this.nServerDetails.requiredSSL=this.allMailServers[i].required_ssl;
        this.nServerDetails.smtp=this.allMailServers[i].smtp_server;
        this.nServerDetails.smtpPort=this.allMailServers[i].smtp_port;
        this.nServerDetails.sslPort=this.allMailServers[i].ssl_port;
        this.nServerDetails.tslStart=this.allMailServers[i].required_tls_start;
        this.getMailserverOtherProperty(id);
        this.newmailServerModal.show();
        break;
      }
    }
  }
  getMailserverOtherProperty(id){
    this._ngx.show();
    this.nServerDetails.otherPropertyes=[];
    this._sysconfig.getMailerProperty(id).subscribe(data=>{
      this._ngx.hide();
      data.forEach(d=>{
        this.nServerDetails.otherPropertyes.push({propertyName:d.mailer_property_name,propertyValue:d.mailerproperty_value});
      });
    },error=>this._ngx.hide());
  }
  onHide(){
    this.nServerDetails.id=0;
    this.nServerDetails.tslStart=false;
    this.nServerDetails.sslPort=465;
    this.nServerDetails.smtpPort=465;
    this.nServerDetails.smtp='';
    this.nServerDetails.requiredSSL=true;
    this.nServerDetails.requiredAuth=true;
    this.nServerDetails.password='';
    this.nServerDetails.otherPropertyes=[];
    this.nServerDetails.emailId='';
    this.nServerDetails.confirmPassword='';
    this.nServerDetails.editMode=false;
    this.property.propertyName='';
    this.property.propertyValue='';
  }
}
