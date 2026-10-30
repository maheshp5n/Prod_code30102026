import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalflowDetailsComponent } from './bat-approvalflow-details.component';
import { BatApprovalflowDetailsRoutingModule } from './bat-approvalflow-details.routing';



@NgModule({
  declarations: [BatApprovalflowDetailsComponent],
  imports: [
    CommonModule,
    BatApprovalflowDetailsRoutingModule
  ]
})
export class BatApprovalflowDetailsModule { }
