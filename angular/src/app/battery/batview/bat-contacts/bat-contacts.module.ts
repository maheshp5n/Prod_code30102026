import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatContactsComponent } from './bat-contacts.component';
import { BatContactsRouting } from './bat-contacts.routing';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { Loginservices } from '../../../services/login.services';
import { AccountService } from '../../../services/accounts.services';
import { SearchfiltPipe } from './searchfilt.pipe';
import { BtCreatecontactsModule } from '../../batDirectiveView/bt-createcontacts/bt-createcontacts.module';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';



@NgModule({
  declarations: [BatContactsComponent,SearchfiltPipe],
  imports: [
    CommonModule,
    BatContactsRouting,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreatecontactsModule,
  ],
  exports: [SearchfiltPipe,BatContactsComponent],
  providers: [BtcontactsService, Loginservices, BoardingLoadingService, NgxSpinnerService]
})
export class BatContactsModule { }
