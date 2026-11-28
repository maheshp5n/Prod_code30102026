import { CreateNewexpensesmanagementModule } from './../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ContactsdetailsComponent } from './contactsdetails.component';
import { ContactDetailsRouting } from './contactdetails.routing';
import { ContactsService } from '../../services/contacts.services';
import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { FormsModule } from '@angular/forms';
import { Loginservices } from '../../services/login.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { RouterModule } from '@angular/router';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    ContactDetailsRouting,
    ContactcreateModule,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewexpensesmanagementModule,
    RouterModule,
    ActionhistoryModule
  ],
  declarations: [ContactsdetailsComponent],
  providers: [ContactsService, AccountService, NgxSpinnerService, CountryDetailsServices, Loginservices, ExpenseManagementService,
    ligalEntityService]
})
export class ContactsdetailsModule { }
