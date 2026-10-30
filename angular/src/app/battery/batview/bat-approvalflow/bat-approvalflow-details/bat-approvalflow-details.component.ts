import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BatApprovalDataFlowService } from '../bat-approvalflow-data';

@Component({
  selector: 'app-bat-approvalflow-details',
  templateUrl: './bat-approvalflow-details.component.html',
  styleUrls: ['./bat-approvalflow-details.component.scss']
})
export class BatApprovalflowDetailsComponent implements OnInit {

  approvaldata:any;

  constructor(private dataservice:BatApprovalDataFlowService) {
    
   }

  ngOnInit() {
    this.approvaldata=this.dataservice.$data;
  }

}
