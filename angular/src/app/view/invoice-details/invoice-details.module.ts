import { URLSafeModule } from './../../pipe/url-safe-pipe.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { InvoicesDetailRouting } from './invoice-details.routing';
import { InvoiceDetailsComponent } from './invoice-details.component';
import { NewInvoiceModule } from '../../DirectivesView/new-invoice/new-invoice.module';
import { CommercialVendorInvoiceModule } from '../../DirectivesView/commercial-vendor-invoice/commercial-vendor-invoice.module';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { Loginservices } from '../../services/login.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { SAPNumberModule } from '../../DirectivesView/update-sap-number/update-sap-number.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {
  EditvendorinvoiceModule
} from '../../DirectivesView/editvendorinvoice/editvendorinvoice.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    NewInvoiceModule, ItemapprovalModule, PdfViewerModule,
    BsDropdownModule, InvoicesDetailRouting, CommercialVendorInvoiceModule, SAPNumberModule,
    EditvendorinvoiceModule,
    URLSafeModule,
    ActionhistoryModule
  ],
  declarations: [InvoiceDetailsComponent],
  providers: [NgxSpinnerService, VendorInvoiceService, Loginservices, ApprovalHistoryService]
})
export class InvoicesDetailsModule {

}