import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { TermsAndConditionsComponent } from './terms-and-conditions.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule
  ],
  declarations: [TermsAndConditionsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [TermsAndConditionsComponent],
  providers: [NgxSpinnerService]
})
export class TermsAndConditionsModule { }
