import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule, AlertModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NewInvoiceModule } from '../../../DirectivesView/new-invoice/new-invoice.module';
import { VendorInvoiceService } from '../../../services/vendorinvoice.services';
import { ItemapprovalModule } from '../../../DirectivesView/itemapproval/itemapproval.module';
import { BatWccDetailComponent } from './bat-wcc-detail.component';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BatWCCDetailsRouting } from './bat-wcc-detail.routing';
import { BatcreateWCCModule } from '../../batDirectiveView/bt-create-wcc/bt-create-wcc.module';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtNewInvoiceModule } from '../../batDirectiveView/bt-new-invoice/bt-new-invoice.module';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';

@NgModule({
  imports: [
    CommonModule,
    BatWCCDetailsRouting,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    BatcreateWCCModule,
    BsDropdownModule,
    NewInvoiceModule,
    ItemapprovalModule,
    AlertModule,
    BtActionhistoryModule,
    BtNewInvoiceModule,
    BatTobeapprovedDrModule
  ],
  declarations: [BatWccDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [NgxSpinnerService, VendorInvoiceService, ApprovalHistoryService]
})
export class BatWCCDetailsModule {

}