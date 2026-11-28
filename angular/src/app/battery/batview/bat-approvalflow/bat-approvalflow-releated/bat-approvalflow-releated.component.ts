import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { BtApprovalService } from '../../../bat_services/btapprovalservice';
import { BatApprovalDataFlowService } from '../bat-approvalflow-data';

@Component({
  selector: 'app-bat-approvalflow-releated',
  templateUrl: './bat-approvalflow-releated.component.html',
  styleUrls: ['./bat-approvalflow-releated.component.scss']
})
export class BatApprovalflowReleatedComponent implements OnInit {

  aprdata:any;

  initt:any;
  stapesdata=[];
  
  @ViewChild('stapes')stapes:ModalDirective;

  stapedata;
  constructor(private batdtaservice:BatApprovalDataFlowService,private approvalflowservice:BtApprovalService) { }

  ngOnInit() {
    this.aprdata=this.batdtaservice.$data;
    this.initt=setTimeout(()=>{
      
      if(this.aprdata!=undefined){
        this.getApprovalStapes();
        clearTimeout(this.initt);
      }
    },100);
  }

  getApprovalStapes(){
    this.approvalflowservice.getStepsByApprovalId(this.aprdata.id).subscribe(data=>{
      this.stapesdata=data;
    });
  }

  openEditModal(item){
    this.stapedata=item;
    this.stapes.show();
  }

  onModalSaev(){
    this.stapes.hide();
    this.getApprovalStapes();
  }

}
