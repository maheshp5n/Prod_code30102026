import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Loginservices } from '../../../services/login.services';
import { PartcodeChangeRequest } from '../../../services/model';
import { PartcodechangeService } from '../../../services/partcodechange.service';

@Component({
  selector: 'app-pendinglist',
  templateUrl: './pendinglist.component.html',
  styleUrls: ['./pendinglist.component.scss']
})
export class PendinglistComponent implements OnInit {

  global=false;
  data:Array<any>=[];
  modratedata=new PartcodeChangeRequest();

  search={
    partcode:'',
    serialnumber:'',
    refid:''
  };

  @ViewChild('modrateApprovalWindow')modrateApprovalWindow:ModalDirective;

  @ViewChild('newpartcodechangeWindow')newpartcodechangeWindow:ModalDirective;

  openWindow={
    modratewindow:false,
    newpartcodechange:false
  }

  users;

  constructor(
    private _partchangeservice:PartcodechangeService,
    private _global:GlobalserviceService,
    private _ngspinner:NgxSpinnerService,
    private _user:Loginservices
    ) { }

  ngOnInit() {
    this.users=this._user.getLoggedInUser();
    this.modratedata.open=false;
    this.getList();
  }

  getList(){
    this._ngspinner.show();
      this._partchangeservice.getpendingList().subscribe(data=>{
        this.data=data;
        this._ngspinner.hide();
      },error=>{
        this._ngspinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
  }

  changeGlobal(){
    this.data.forEach(d=>{
      if(d.initiated_by_email_id!==this.users.emailId){
        d.selected=this.global;
      }
      else{
        d.selected=false;
      }
    });
  }

  approvedSelected(){
    const tempIds=[];
    const len=this.data.length;
    for(let i=0;i<len;i++){
      if(this.data[i].selected){
        tempIds.push(this.data[i].id);
      }
    }

    if(tempIds.length==0){
      this._global.alert({type:'alert',message:'Please select item to approve'});
      return;
    }
    if(confirm('Are you sure to submit the records!')){
      this._ngspinner.show();
      this._partchangeservice.approveSelected(tempIds).subscribe(data=>{
        this.global=false;
        this._ngspinner.hide();
        this._global.alert({type:'',message:'Action performed successfully'});
        this.getList();  
      },error=>{
        this._ngspinner.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
    

  }

  getModrateApprovalModalOpen(){
    const temp=[];
    const len=this.data.length;
    for(let i=0;i<len;i++){
      if(this.data[i].selected){
        temp.push(this.data[i]);
      }
    }

    if(temp.length==0){
      this._global.alert({type:'alert',message:'Please select one item to change and approve'});
      return;
    }
    if(temp.length>1){
      this._global.alert({type:'alert',message:'Can not select multiple, Please select one item to change and approve'});
      return;
    }
    this.modratedata=temp[0];
    this.modratedata.open=true;
    this.modrateApprovalWindow.show();

  }
  cancelModreteModal(){
    this.modratedata=new PartcodeChangeRequest();
    this.modratedata.open=false;
    this.modrateApprovalWindow.hide();
  }

  selectOneItem(item){

    this.modratedata=item;
    // if(this.modratedata.flow_type!=='FLOW_TYPE_SA'){
    //   const ct=Object.assign({},item);
    //   this.modratedata.consumed_product_product_part_code=ct.faulty_product_product_part_code;
    //   this.modratedata.consumed_product_serial_number=ct.faulty_product_serial_number;
    // }
    this.modratedata.open=true;
    this.modrateApprovalWindow.show();
  }

  onModrateChangeDone(){
    this.cancelModreteModal();
    this._global.alert({type:'',message:'Submitted Successfully'});
    this.getList();
  }

  getSearchResult(){
    this._ngspinner.show();
    this._partchangeservice.searchPendingToApprove(this.search).subscribe(data=>{
      this._ngspinner.hide();
      this.data=data;
    },error=>{
      this._ngspinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

  onSaveOfNewworkflow(){
    
    this._global.alert({type:'',message:`Request Submitted Successfully`});
    //this.newpartcodechangeWindow.hide();
    this.openWindow.newpartcodechange=false;
    try{
      this.newpartcodechangeWindow.hide();
    }catch(e){
      console.log(e);
    }
   
  }
  reset(){
    this.search.partcode=''
    this.search.serialnumber=''
    this.search.refid=''
  }
}
