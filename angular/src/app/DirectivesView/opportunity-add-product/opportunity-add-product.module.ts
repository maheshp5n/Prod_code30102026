import { SearchfiltPipe } from './../../DirectivesView/opportunity-add-product/searchfilt.pipe';
import { OpportunityAddProductComponent } from './opportunity-add-product.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { OpportunityService } from '../../services/Opportunity.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule
  ],
  declarations: [OpportunityAddProductComponent, SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [OpportunityAddProductComponent, SearchfiltPipe],
  providers: [NgxSpinnerService, OpportunityService
  ]

})
export class OpportunityAddProductModule { }
