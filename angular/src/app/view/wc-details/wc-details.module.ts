import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule, AlertModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { WCDetailsRouting } from './wc-details.routing';
import { WcDetailsComponent } from './wc-details.component';
import { createWCCModule } from '../../DirectivesView/createwcc/createwcc.module';
import { NewInvoiceModule } from '../../DirectivesView/new-invoice/new-invoice.module';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    WCDetailsRouting,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    createWCCModule,
    BsDropdownModule,
    NewInvoiceModule,
    ItemapprovalModule,
    AlertModule,
    ActionhistoryModule
  ],
  declarations: [WcDetailsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [NgxSpinnerService, VendorInvoiceService, ApprovalHistoryService]
})
export class WCDetailsModule {

}