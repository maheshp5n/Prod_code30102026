import { SearchfiltPipe } from './searchfilt.pipe';
import { QuoteAddProductComponent } from './quote-add-product.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { QuoteService } from '../../services/Quote.services';
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
  declarations: [QuoteAddProductComponent, SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [QuoteAddProductComponent, SearchfiltPipe],
  providers: [NgxSpinnerService, QuoteService
  ]

})
export class QuoteAddProductModule { }
