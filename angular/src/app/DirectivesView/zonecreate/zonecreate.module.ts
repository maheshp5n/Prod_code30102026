import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../services/accounts.services';
import { ContactsService } from '../../services/contacts.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { ZonecreateComponent } from './zonecreate.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule
    
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ZonecreateComponent],
  exports: [ZonecreateComponent],
  providers: [ContactsService, Loginservices, NgxSpinnerService]
})
export class ZonecreateModule { }
