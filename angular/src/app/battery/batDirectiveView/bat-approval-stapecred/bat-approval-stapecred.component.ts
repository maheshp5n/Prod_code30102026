import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';
import { BtApprovalService } from '../../bat_services/btapprovalservice';
import { BtuserroleService } from '../../bat_services/btuserrole.service';

@Component({
  selector: 'app-bat-approval-stapecred',
  templateUrl: './bat-approval-stapecred.component.html',
  styleUrls: ['./bat-approval-stapecred.component.scss']
})
export class BatApprovalStapecredComponent implements OnInit {


  @Output() onCancel:EventEmitter<any>=new EventEmitter();
  @Output() onSave:EventEmitter<any>=new EventEmitter();


  stapesDetails={
    assignedTo:undefined,
    approvalId:undefined,
    onApprove:undefined,
    onReject:undefined,
    stapesNumber:1,
    rltype:'00',
    stapeName:undefined,
    id:0

  }

  editMode=false;
  _stp;
  _aprdetails;
  initList=[];
  @Input() set approvaldetails(apr){
    this._aprdetails=apr;
    this.stapesDetails.approvalId=apr.id;
  }
  get approvaldetails(){
    return this._aprdetails;
  }

  @Input() set stapedata(stp){
    if(stp!=undefined){
      this.stapesDetails.assignedTo=stp.assigned_to;
      this.stapesDetails.approvalId=stp.approval_id;
      this.stapesDetails.onApprove=stp.on_approve;
      this.stapesDetails.onReject=stp.on_reject;
      this.stapesDetails.stapesNumber=stp.stape_number;
      this.stapesDetails.rltype=this.stapesDetails.assignedTo.startsWith('ROLE')?'ROLE':'USER';
      this.stapesDetails.stapeName=stp.stape_name;
      this.stapesDetails.id=stp.id;
      this.editMode=true;
      if(this.stapesDetails.rltype==='ROLE'){
        this.getRoles();

      }
      else{
        this.getuserList();
      }
    }
    this._stp=stp;
  }
  get stapedata(){
    return this._stp;
  }

  constructor(private approvalservice:BtApprovalService,
    private batuserregistrate:BatuserregistrationService,
    private userroleservice:BtuserroleService,
    private globalservice:GlobalserviceService,
    private ngx:NgxSpinnerService) { }

  ngOnInit() {
  }


  onChangeMode($event){
    const role=$event.target.value;
    if(role==='ROLE'){
      this.getRoles();
    }
    else if(role==='USER'){
      this.getuserList();
    }
    else{
      this.initList=[];
    }
  }


  cancelRequest(){
    this.onCancel.emit('');
  }
  saveDetails(){
    this.ngx.show();
    if(!this.editMode){
      this.approvalservice.createApprovalStep(this.stapesDetails.approvalId,this.stapesDetails).subscribe(data=>{
        this.ngx.hide();
        this.onSave.emit('');
      },error=>{
        this.ngx.hide();
        this.globalservice.alert({type:'alert',message:error.error.message});
      });
    }
    else{
      this.approvalservice.updateApprovalStep(this.stapesDetails.approvalId,this.stapesDetails.id,this.stapesDetails).subscribe(data=>{
        this.ngx.hide();
        this.onSave.emit('');
      },error=>{
        this.ngx.hide();
        this.globalservice.alert({type:'alert',message:error.error.message});
      })
    }
    
  }

  getuserList(){
    this.batuserregistrate.getUserForList2().subscribe(data=>{
      const tempd=[];
        data.forEach(d=>{
          d.userFirstName=d.userFirstName==null?'':d.userFirstName;
          d.userMiddleName=d.userMiddleName==null?'':d.userMiddleName;
          d.userLastName=d.userLastName==null?'':d.userLastName;
          tempd.push({initvalue:`USER:${d.userEmailId}`,initlavel:`${d.userFirstName} ${d.userMiddleName} ${d.userLastName} (${d.userEmailId})`});
        });
        this.initList=tempd;
    });
  }
  getRoles(){
    this.userroleservice.getRoles().subscribe(data=>{
      const tempd=[];
      data.forEach(d=>{
        tempd.push({initvalue:`ROLE:${d.authorities}`,initlavel:d.authorities});
      });
      this.initList=tempd;
    });
  }
}
