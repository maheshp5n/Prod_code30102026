import { URLSafeModule } from './../../../pipe/url-safe-pipe.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NewInvoiceModule } from '../../../DirectivesView/new-invoice/new-invoice.module';
import { CommercialVendorInvoiceModule } from '../../../DirectivesView/commercial-vendor-invoice/commercial-vendor-invoice.module';
import { VendorInvoiceService } from '../../../services/vendorinvoice.services';
import { Loginservices } from '../../../services/login.services';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { ItemapprovalModule } from '../../../DirectivesView/itemapproval/itemapproval.module';
import { SAPNumberModule } from '../../../DirectivesView/update-sap-number/update-sap-number.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {
  EditvendorinvoiceModule
} from '../../../DirectivesView/editvendorinvoice/editvendorinvoice.module';
import { BatInvoiceDetailComponent } from './bat-invoice-detail.component';
import { BatInvoicesDetailRouting } from './bat-invoice-detail.routing';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtEditVendorInvoiceModule } from '../../batDirectiveView/bt-edit-vendor-invoice/bt-edit-vendor-invoice.module';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';
import { BtUpdateSapNumberModule } from '../../batDirectiveView/bt-update-sap-number/bt-update-sap-number.module';
import { BtCommercialVendorInvoiceModule } from '../../batDirectiveView/bt-commercial-vendor-invoice/bt-commercial-vendor-invoice.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    NewInvoiceModule, ItemapprovalModule, PdfViewerModule,
    BsDropdownModule, BatInvoicesDetailRouting, BtCommercialVendorInvoiceModule, BtUpdateSapNumberModule,
    URLSafeModule,
    BtActionhistoryModule,
    BtEditVendorInvoiceModule,
    BatTobeapprovedDrModule
  ],
  declarations: [BatInvoiceDetailComponent],
  providers: [NgxSpinnerService, VendorInvoiceService, Loginservices, ApprovalHistoryService]
})
export class BatInvoicesDetailsModule {

}