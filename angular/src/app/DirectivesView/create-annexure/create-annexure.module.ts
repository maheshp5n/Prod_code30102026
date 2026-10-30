import { ContactsService } from './../../services/contacts.services';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateAnnexureComponent } from './create-annexure.component';
import { Loginservices } from '../../services/login.services';
import { AnnexureService } from '../../services/annexure.services';
import { RouterModule } from '@angular/router';

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
    RouterModule
  ],
  declarations: [CreateAnnexureComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateAnnexureComponent],
  providers: [NgxSpinnerService, Loginservices, AnnexureService, ContactsService]
})
export class CreateAnnexureModule { }
