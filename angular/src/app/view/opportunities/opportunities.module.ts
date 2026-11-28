import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { OpportunitiesRouting } from './opportunities.routing';
import { OpportunitiesComponent } from './opportunities.component';
import { CreateNewOpportunityModule } from '../../DirectivesView/create-new-opportunity/create-new-opportunity.module';
import { OpportunityService } from '../../services/Opportunity.services';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    OpportunitiesRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    CreateNewOpportunityModule,BsDatepickerModule.forRoot()
  ],
  declarations: [OpportunitiesComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ FieldValidation, NgxSpinnerService, OpportunityService,DatePipe, NgxSpinnerService,PagefilterService]
})

export class OpportunitiesModule { }
