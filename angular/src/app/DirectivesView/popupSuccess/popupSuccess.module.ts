import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../services/accounts.services';
import { ContactsService } from '../../services/contacts.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ModalModule } from 'ngx-bootstrap';
import { PopupSuccessComponent } from './popupSuccess.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [PopupSuccessComponent],
  exports: [PopupSuccessComponent],
  providers: [AccountService, ContactsService, CountryDetailsServices, ligalEntityService]
})
export class PopupSuccessModule { }
