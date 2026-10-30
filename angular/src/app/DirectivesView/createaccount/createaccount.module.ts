import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateaccountComponent } from './createaccount.component';
import { FormsModule } from '@angular/forms';

import { AccountsTypeService } from '../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';
import { AccountService } from '../../services/accounts.services';
import { ModalModule } from 'ngx-bootstrap';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { CustomerService } from '../../services/customers.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateCustomerModule } from '../create-customer/create-customer.module';
import { Loginservices } from '../../services/login.services';
import { CreateparentAccountModule } from '../createparent-account/createparent-account.module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    CreateCustomerModule,
    CreateparentAccountModule,
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateaccountComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateaccountComponent],
  providers: [NgxSpinnerService, AccountsTypeService, AccountsCatagoryServices,
    CountryDetailsServices, AccountService, CustomerService, Loginservices]
})
export class CreateaccountModule { }
