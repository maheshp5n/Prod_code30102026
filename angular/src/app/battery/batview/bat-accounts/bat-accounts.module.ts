import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CreateaccountModule } from '../../../DirectivesView/createaccount/createaccount.module';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { BatAccountsComponent } from './bat-accounts.component';
import { BatAccountsRouting } from './bat-accounts.routing';
import { BTCreateaccountModule } from '../../batDirectiveView/bt-createaccount/bt-createaccount.module';


@NgModule({
  imports: [
    CommonModule,
    BatAccountsRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule,
    CreateaccountModule,
    BsDatepickerModule,
    NgxSpinnerModule,
    OrderModule,
    BTCreateaccountModule
  ],
  providers: [NgxSpinnerService,Loginservices,CountryDetailsServices],
  declarations: [BatAccountsComponent],
  exports: []
})
export class BatAccountsModule {

}
