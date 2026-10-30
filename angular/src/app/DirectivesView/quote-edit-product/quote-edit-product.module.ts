import { SearchfiltPipe } from '../quote-edit-product/searchfilt.pipe';
import { QuoteEditProductComponent } from './quote-edit-product.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule, TypeaheadModule } from 'ngx-bootstrap';
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
  declarations: [QuoteEditProductComponent, SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [QuoteEditProductComponent, SearchfiltPipe],
  providers: [NgxSpinnerService, QuoteService
  ]

})
export class QuoteEditProductModule { }
