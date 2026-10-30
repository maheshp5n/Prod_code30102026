import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContactsRouting } from './contacts.routing';
import { ContactsComponent } from './contacts.component';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ContactsService } from '../../services/contacts.services';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ContactsRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    ContactcreateModule,
    PopupSuccessModule
  ],
  declarations: [ContactsComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ContactsService, Loginservices, AccountService, NgxSpinnerService]
})
export class ContactsModule { }
