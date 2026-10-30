import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { PrcActionRequest } from '../../../services/model';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BTPrcActionRequest, BtPreRegCaseResponse } from '../../bt_model';

@Component({
  selector: 'app-bt-prc-action',
  templateUrl: './bt-prc-action.component.html',
  styleUrls: ['./bt-prc-action.component.scss']
})
export class BtPrcActionComponent implements OnInit {

  prcreq:PrcActionRequest=new PrcActionRequest();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onGenerated: EventEmitter<BtPreRegCaseResponse> = new EventEmitter<BtPreRegCaseResponse>();
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
  set ActionReq(actionreq:BTPrcActionRequest){
    this.actionreq=actionreq;

  }
  get ActionReq():BTPrcActionRequest{
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
    private preregservice:BtPreRegistrationCaseService,private _global:GlobalserviceService) { }


  ngOnInit(): void {
  }
  actionreq:BTPrcActionRequest=new BTPrcActionRequest();
  message:string;
  generateCase(){
    this.actionreq.caseId=Number(this.prcreq.caseid);
    // if(this.prcreq.action=='Generate Case'){
      if(this.actionreq.comment==null && this.actionreq.comment==undefined){
        this.message='Please fill comment';
        this._global.alert({type:'alert',message:this.message});
      }
      else{
        this.actionreq.action=this.prcreq.action;

     this.spinner.show();
     this.preregservice.RRCaseActionByIdCmnt(this.actionreq).subscribe(data=>{
     this.spinner.hide();
     this.message="Successfully Updated";
     this.onGenerated.emit(data);
     this.spinner.hide();
     this._global.alert({type:'',message:this.message});
     },error=>{
       this.spinner.hide();
       this.message=error.error.message;
       this._global.alert({type:'alert',message:this.message})

     })}
  }
 
  closeIgnor(){
    // this.prcreq=new PrcActionRequest();
    this.actionreq=new BTPrcActionRequest();
    this.onCancle.emit('cancel');
  }



}
