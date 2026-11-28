import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ObjectServices } from '../../../services/objecct.service';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';
import { BtApprovalService } from '../../bat_services/btapprovalservice';
import { BtuserroleService } from '../../bat_services/btuserrole.service';

@Component({
  selector: 'app-bat-approval-credit',
  templateUrl: './bat-approval-credit.component.html',
  styleUrls: ['./bat-approval-credit.component.scss']
})
export class BatApprovalCreditComponent implements OnInit {

  userlist=[];
  objList=[];
  approvalObject={
    object_class_name:undefined,
    init_submitters:undefined,
    action_cat:undefined,
    description:undefined,
    object_name:undefined,
    stapes:1,
    id:undefined,
    rltype:'00'
  };
  editmode=false;
  _floe;

  @Input() set approvalflowdata(floe){

    if(floe!=undefined&&floe!=null){
      this._floe=floe;
      this.editmode=true;
      this.approvalObject.object_class_name=floe.object_class_name;
      this.approvalObject.init_submitters=floe.initial_submitters;
      this.approvalObject.action_cat=floe.ct_on_action;
      this.approvalObject.description=floe.description;
      this.approvalObject.object_name=floe.object_name;
      this.approvalObject.stapes=floe.total_stapes;
      this.approvalObject.id=floe.id;
      if(this.approvalObject.init_submitters.startsWith('ROLE:')){
        this.approvalObject.rltype='ROLE';
        this.getRoleList();
      }
      else if(this.approvalObject.init_submitters.startsWith('USER:')){
        this.approvalObject.rltype='USER';
        this.getUserList();
      }
      else{
        this.approvalObject.rltype='00';
      }
    }
  }
  get approvalflowdata(){
    return this._floe;
  }

  @Output() onCancel:EventEmitter<any>=new EventEmitter();
  @Output() onSave:EventEmitter<any>=new EventEmitter();

  initList=[];

  constructor(private approvalservice:BtApprovalService,
    private batuserregistrate:BatuserregistrationService,
    private objectService:ObjectServices,
    private userroleservice:BtuserroleService,
    private globalservice:GlobalserviceService,private ngx:NgxSpinnerService) { }

  ngOnInit() {
    this.getObjectList();
  }


  onChangeMode($event){
    const role=$event.target.value;
    if(role==='ROLE'){
      this.getRoleList();
    }
    else if(role==='USER'){
      this.getUserList();
    }
    else{
      this.initList=[];
    }
  }

  getUserList(){
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

  getRoleList(){
    this.userroleservice.getRoles().subscribe(data=>{
      const tempd=[];
      data.forEach(d=>{
        tempd.push({initvalue:`ROLE:${d.authorities}`,initlavel:d.authorities});
      });
      this.initList=tempd;
    });
  }

  getObjectList(){
    this.objectService.getObjectForList().subscribe(data=>{
      this.objList=data;
    });
  }

  cancelButtonClicked(){
    this.onCancel.emit('cancled');
  }
  saveDetails(){
    if(!this.editmode){
      this.ngx.show();
      this.approvalservice.createApproval(this.approvalObject).subscribe(data=>{
        this.ngx.hide();
        this.onSave.emit(data);
      },error=>{
        this.ngx.hide();
        this.globalservice.alert({type:'alert',message:error.error.message});
      });
    }
    else{
      this.ngx.show();
      this.approvalservice.updateApproval(this.approvalObject.id,this.approvalObject).subscribe(data=>{
        this.ngx.hide();
        this.onSave.emit(data);
      },error=>{
        this.ngx.hide();
        this.globalservice.alert({type:'alert',message:error.error.message});
      });
    }
    
  }
}
