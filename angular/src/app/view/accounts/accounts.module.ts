import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Accountsrouting } from './accounts.rougin';
import { AccountsComponent } from './accounts.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CreateaccountModule } from '../../DirectivesView/createaccount/createaccount.module';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SearchfiltPipe } from './searchfilt.pipe';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';
import { CountryDetailsServices } from '../../services/countrydetails.services';


@NgModule({
  imports: [
    CommonModule,
    Accountsrouting,
    FormsModule,
    BsDropdownModule,
    ModalModule,
    CreateaccountModule,
    BsDatepickerModule,
    NgxSpinnerModule,
    OrderModule
  ],
  providers: [NgxSpinnerService,Loginservices,CountryDetailsServices],
  declarations: [AccountsComponent, SearchfiltPipe],
  exports: [SearchfiltPipe]
})
export class AccountsModule {

}
