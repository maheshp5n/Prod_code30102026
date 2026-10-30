import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule, BsDatepickerModule } from 'ngx-bootstrap';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { QuoteRouting } from './quote.routing';
import { QuoteComponent } from './quote.component';
import { CreateNewQuoteModule } from '../../DirectivesView/create-new-quote/create-new-quote.module';
import { QuoteService } from '../../services/Quote.services';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    QuoteRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    CreateNewQuoteModule,BsDatepickerModule.forRoot()
  ],
  declarations: [QuoteComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ FieldValidation, NgxSpinnerService, QuoteService,PagefilterService,DatePipe]
})

export class QuoteModule { }
