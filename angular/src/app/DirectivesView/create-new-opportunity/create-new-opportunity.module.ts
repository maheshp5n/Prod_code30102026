import { NgSelectModule } from '@ng-select/ng-select';
import { CreatecaseModule } from './../createcase/createcase.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateNewOpportunityComponent } from './create-new-opportunity.component';
import { ModalModule, BsDropdownModule, BsDatepickerModule, TimepickerModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { Loginservices } from '../../services/login.services';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { caseService } from '../../services/caseService.service';
import { OpportunityService } from '../../services/Opportunity.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    CreatecaseModule,
    CreateaccountModule,
    TypeaheadModule.forRoot(),
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateNewOpportunityComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewOpportunityComponent],
  providers: [NgxSpinnerService, Loginservices, caseService, OpportunityService, DatePipe]

})
export class CreateNewOpportunityModule { }
