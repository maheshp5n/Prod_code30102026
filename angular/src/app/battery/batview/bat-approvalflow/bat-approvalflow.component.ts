import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtApprovalService } from '../../bat_services/btapprovalservice';
import { BatApprovalDataFlowService } from './bat-approvalflow-data';

@Component({
  selector: 'app-bat-approvalflow',
  templateUrl: './bat-approvalflow.component.html',
  styleUrls: ['./bat-approvalflow.component.scss']
})
export class BatApprovalflowComponent implements OnInit {
  approvalFlowdetails:any;

  openWindow={
    editapprovalflow:false,
    nweapprovalstapes:false,
    editapprovalstapes:false
  }

  @ViewChild('editapprovalflow',{static:false})editapprovalflow:ModalDirective;
  @ViewChild('stapes',{static:false})stapes:ModalDirective;

  constructor(private approval:BtApprovalService,private activatedRoute:ActivatedRoute,private global:GlobalserviceService,private _router:Router,private dataservice:BatApprovalDataFlowService) { 
    
    this.activatedRoute.params.subscribe(param=>{
      this.approval.getSingleByApprovalId(param['id']).subscribe(data=>{
        this.approvalFlowdetails=data;
        this.dataservice.$data=data;
      });
   });
  }

  

  ngOnInit() {
    
  }

  onEditApprovalSave(){
    this.editapprovalflow.hide();
  }
  onnewStapesaved(){
    this.stapes.hide();
  }

  navigateTo(type){
    if(type=='details'){
      this._router.navigate(['/batapprovalflow',this.approvalFlowdetails.id,'details'],{queryParams:{id:this.approvalFlowdetails.id}});
      return false;
    }
    else{
      this._router.navigate(['/batapprovalflow',this.approvalFlowdetails.id,'releted'],{queryParams:{id:this.approvalFlowdetails.id}});
    }
  }

  onStapeCreated(){
    this.stapes.hide();
    this.navigateTo('details');
  }

}
