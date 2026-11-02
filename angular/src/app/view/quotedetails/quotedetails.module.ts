import { QuoteEditProductModule } from './../../DirectivesView/quote-edit-product/quote-edit-product.module';
import { QuoteAddProductModule } from './../../DirectivesView/quote-add-product/quote-add-product.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/accounts.services';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { QuotedetailsComponent } from './quotedetails.component';
import { QuoteDetailsRouting } from './quotedetails.routing';
import { CreateNewQuoteModule } from '../../DirectivesView/create-new-quote/create-new-quote.module';
import { QuoteService } from '../../services/Quote.services';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { WorkOrderForQuoteModule } from '../../DirectivesView/work-order-for-quete/work-order-for-quete.module';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { AreYouSureModule } from '../../DirectivesView/are-you-sure/are-you-sure.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    QuoteDetailsRouting,
    ContactcreateModule,
    CreateNewQuoteModule,
    BsDropdownModule,
    QuoteAddProductModule,
    QuoteEditProductModule,
    PdfViewerModule,
    AreYouSureModule,
    AngularEditorModule,
    WorkOrderForQuoteModule,
    ItemapprovalModule,
    ActionhistoryModule
  ],
  declarations: [QuotedetailsComponent],
  providers: [AccountService, NgxSpinnerService, Loginservices, QuoteService, ApprovalHistoryService]
})
export class QuotedetailsModule { }
