import { QuoteService } from './../../services/Quote.services';
import { CreateNewexpensesmanagementModule } from './../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { OpportunityEditProductModule } from './../../DirectivesView/opportunity-edit-product/opportunity-edit-product.module';
import { OpportunityAddProductModule } from './../../DirectivesView/opportunity-add-product/opportunity-add-product.module';
import { PopupSuccessModule } from './../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/accounts.services';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { OpportunitiesdetailsComponent } from './opportunitiesdetails.component';
import { OpportunitiesDetailsRouting } from './opportunitiesdetails.routing';
import { CreateNewOpportunityModule } from '../../DirectivesView/create-new-opportunity/create-new-opportunity.module';

import { OpportunityService } from '../../services/Opportunity.services';
import {
  CreateNewQuoteModule
} from '../../DirectivesView/create-new-quote/create-new-quote.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OpportunitiesDetailsRouting,
    ContactcreateModule,
    PopupSuccessModule,
    CreateNewOpportunityModule,
    BsDropdownModule,
    OpportunityAddProductModule,
    OpportunityEditProductModule,
    CreateNewexpensesmanagementModule,
    CreateNewQuoteModule,
    ActionhistoryModule
  ],
  declarations: [OpportunitiesdetailsComponent],
  providers: [AccountService, NgxSpinnerService, Loginservices, OpportunityService, QuoteService]
})
export class OpportunitiesdetailsModule { }
