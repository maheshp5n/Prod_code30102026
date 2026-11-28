import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalflowComponent } from './bat-approvalflow.component';
import { FormsModule } from '@angular/forms';
import { BatApprovalFlowRoutingModule } from './bat-approvalflow.routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BatApprovalCreditModule } from '../../batDirectiveView/bat-approval-credit/bat-approval-credit.module';
import { BatApprovalStapecredModule } from '../../batDirectiveView/bat-approval-stapecred/bat-approval-stapecred.module';
import { BatApprovalDataFlowService } from './bat-approvalflow-data';



@NgModule({
  declarations: [BatApprovalflowComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatApprovalFlowRoutingModule,
    ModalModule,
    BatApprovalCreditModule,
    BatApprovalStapecredModule
  ],
  providers:[BatApprovalDataFlowService]
})
export class BatApprovalflowModule { }
