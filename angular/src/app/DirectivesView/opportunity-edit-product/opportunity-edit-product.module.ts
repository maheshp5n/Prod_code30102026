import { SearchfiltPipe } from '../opportunity-edit-product/searchfilt.pipe';
import { OpportunityEditProductComponent } from './opportunity-edit-product.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
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
  declarations: [OpportunityEditProductComponent, SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [OpportunityEditProductComponent, SearchfiltPipe],
  providers: [NgxSpinnerService, OpportunityService
  ]

})
export class OpportunityEditProductModule { }
