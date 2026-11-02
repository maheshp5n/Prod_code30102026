import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateNewQuoteComponent } from './create-new-quote.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { Loginservices } from '../../services/login.services';

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
    RouterModule
  ],
  declarations: [CreateNewQuoteComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewQuoteComponent],
  providers: [NgxSpinnerService, Loginservices]

})
export class CreateNewQuoteModule { }
