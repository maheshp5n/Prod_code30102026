import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalFlowReletatedRoutingModule } from './bat-approvalflow-releated.routing';
import { BatApprovalflowReleatedComponent } from './bat-approvalflow-releated.component';
import { BatApprovalStapecredModule } from '../../../batDirectiveView/bat-approval-stapecred/bat-approval-stapecred.module';
import { ModalModule } from 'ngx-bootstrap/modal';



@NgModule({
  declarations: [BatApprovalflowReleatedComponent],
  imports: [
    CommonModule,
    BatApprovalFlowReletatedRoutingModule,
    BatApprovalStapecredModule,
    ModalModule
  ]
})
export class BatApprovalflowReleatedModule { }
