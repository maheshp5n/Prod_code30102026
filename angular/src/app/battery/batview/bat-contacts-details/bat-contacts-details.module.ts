import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatContactsDetailsRouting } from './bat-contacts-details.routing';
import { BatContactsDetailsComponent } from './bat-contacts-details.component';
import { BtCreatecontactsModule } from '../../batDirectiveView/bt-createcontacts/bt-createcontacts.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { RouterModule } from '@angular/router';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { AccountService } from '../../../services/accounts.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { Loginservices } from '../../../services/login.services';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { BtCreateexpensesManagementModule } from '../../batDirectiveView/bt-createexpenses-management/bt-createexpenses-management.module';



@NgModule({
  imports: [
     CommonModule,
     BatContactsDetailsRouting,
      BtCreatecontactsModule,
      NgxSpinnerModule,
      ModalModule,
      FormsModule,
      BsDropdownModule,
      BtCreateexpensesManagementModule,
      RouterModule,
      ActionhistoryModule
    ],
    declarations: [BatContactsDetailsComponent],
    providers: [BtcontactsService, AccountService, NgxSpinnerService, CountryDetailsServices, Loginservices, ExpenseManagementService,
      ligalEntityService]
})
export class BatContactsDetailsModule { }
