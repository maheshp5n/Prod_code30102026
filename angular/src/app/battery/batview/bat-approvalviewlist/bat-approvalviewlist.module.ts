import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatApprovalviewlistRoutingModule } from './bat-approvalviewlist.routing.module';
import { BatApprovalviewlistComponent } from './bat-approvalviewlist.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BatApprovalCreditModule } from '../../batDirectiveView/bat-approval-credit/bat-approval-credit.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';
import { OrderModule } from 'ngx-order-pipe';



@NgModule({
  declarations: [BatApprovalviewlistComponent],
  imports: [
    CommonModule,
    BatApprovalviewlistRoutingModule,
    ModalModule,OrderModule,
    BatApprovalCreditModule,
    NgxSpinnerModule,FormsModule
  ],
  providers:[NgxSpinnerService]
})
export class BatApprovalviewlistModule { }
