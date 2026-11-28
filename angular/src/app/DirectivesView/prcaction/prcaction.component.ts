import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { PrcActionRequest, PreRegCaseResponse } from '../../services/model';
import { PreRegCaseService } from '../../services/preregcaseservice.services';

@Component({
  selector: 'app-prcaction',
  templateUrl: './prcaction.component.html',
  styleUrls: ['./prcaction.component.css']
})
export class PrcactionComponent implements OnInit {
  
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;
  prcreq:PrcActionRequest=new PrcActionRequest();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onGenerated: EventEmitter<PreRegCaseResponse> = new EventEmitter<PreRegCaseResponse>();
  caseid:any;
  actionmessg:string;
  @Input()
  set CaseId(caseid: number) {
    this.prcreq.caseid = caseid;
  }
  get CaseId(): number {
    return this.prcreq.caseid;
  }
  @Input()
  set ActionReq(actionreq:PrcActionRequest){
    this.actionreq=actionreq;

  }
  get ActionReq():PrcActionRequest{
    return this.actionreq;
  }
  @Input()
  set Action(ActionName: string) {
    this.prcreq.action = ActionName;

  }
  get Action(): string {
    return this.prcreq.action;
  }
  @Input()
  set ActionMsg(actionMessage:string){
    this.actionmessg=actionMessage;
  }
  get ActionMsg():string{
    return this.actionmessg;
  }
 

  constructor(private _activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService,
    private preregservice:PreRegCaseService) { }


  ngOnInit(): void {
  }
  actionreq:PrcActionRequest=new PrcActionRequest();
  message:string;
  generateCase(){
    this.actionreq.caseid=this.prcreq.caseid;
    // if(this.prcreq.action=='Generate Case'){
      this.actionreq.action=this.prcreq.action;

     this.spinner.show();
     this.preregservice.RRCaseActionByIdCmnt(this.actionreq).subscribe(data=>{
     this.spinner.hide();
     this.message="Successfully Updated";
     this.popupNgClass = 'alert alert-success center center-block';
     this.onGenerated.emit(data);
     this.spinner.hide();
     this.popupSuccessModel.show();
     },error=>{
       this.spinner.hide();
       this.message=error.error.message;
       this.popupSuccessModel.show();

     })
  }
 
  closeIgnor(){
    // this.prcreq=new PrcActionRequest();
    this.actionreq=new PrcActionRequest();
    this.onCancle.emit('cancel');
  }

  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.prcreq=new PrcActionRequest();
      this.actionreq=new PrcActionRequest();
      this.onCancle.emit('cancel');
    }
  }
}
