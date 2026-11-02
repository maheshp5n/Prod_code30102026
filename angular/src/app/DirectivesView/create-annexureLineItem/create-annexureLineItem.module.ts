import { ContactsService } from '../../services/contacts.services';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateAnnexureLineItemComponent } from './create-annexureLineItem.component';
import { Loginservices } from '../../services/login.services';
import { AnnexureLineItemService } from '../../services/annexureLineItem.services';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateAnnexureLineItemComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateAnnexureLineItemComponent],
  providers: [NgxSpinnerService, Loginservices, AnnexureLineItemService, ContactsService]
})
export class CreateAnnexureLineItemModule { }
