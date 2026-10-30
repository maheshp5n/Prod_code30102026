import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
// import { CreateApprovalModule } from '../../DirectivesView/create-approval/create-approval.module';
import { AssetsService } from '../../services/assets.services';
import { ApprovaldetailsRouting } from './approvaldetails.routing';
import { ApprovaldetailsComponent } from './approvaldetails.component';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { QuoteAddProductModule } from '../../DirectivesView/quote-add-product/quote-add-product.module';
import { AddApprovelLineItemModule } from '../../DirectivesView/add-approvel-line-item/add-approvel-line-item.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ApprovaldetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    // CreateApprovalModule,
    QuoteAddProductModule,
    BsDropdownModule,
    AddApprovelLineItemModule,
    ActionhistoryModule
  ],
  declarations: [ApprovaldetailsComponent],
  providers: [AssetsService, ApprovalHistoryService, NgxSpinnerService]
})
export class ApprovaldetailsModule { }
