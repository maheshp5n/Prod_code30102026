import { Component, OnInit, ViewChild } from '@angular/core';
import { Sysconfigparamservice } from '../../../services/sysconfig.services';
import { ModalDirective } from 'ngx-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { error } from 'protractor';

@Component({
  selector: 'app-templateconf',
  templateUrl: './templateconf.component.html',
  styleUrls: ['./templateconf.component.scss']
})
export class TemplateconfComponent implements OnInit {

  templates:Array<any>=[];
  newtemplate={
    templateCode:'',
    templateType:'MAIL',
    body:''
  };
  @ViewChild('newtemplatewindow',{static:false})newtemplatewindow:ModalDirective;
  constructor(private _sysservice:Sysconfigparamservice,private _ngx:NgxSpinnerService) { }

  ngOnInit() {
    this.getTemplates();
  }
  getTemplates(){
    this._ngx.show();
    this._sysservice.getTemplates().subscribe(data=>{
      this.templates=data;
      this._ngx.hide();
    },error=>this._ngx.hide());
  }
  updateTemplate(tempid){
    for(let i=0;i<this.templates.length;i++){
      if(this.templates[i].id==tempid){
        this._ngx.show();
        this._sysservice.updateTemplate(this.templates[i].id,this.templates[i].templateBody).subscribe(data=>{
          this._ngx.hide();
        },errror=>{
          this._ngx.hide();
          alert(`Error ${errror.error.message}`);
        });
      }
    }
  }
  saveTemplate(){
    this._ngx.show();
    this._sysservice.saveTemplate(this.newtemplate).subscribe(data=>{
      this.newtemplatewindow.hide();
      this.getTemplates();
    },error=>
    {
      alert(`Error ${error.error.message}`);
      this._ngx.hide();
    });
  }
  onCreateNewHide(){
    this.newtemplate.body='';
    this.newtemplate.templateCode='';
    this.newtemplate.templateType='MAIL';
  }
}
