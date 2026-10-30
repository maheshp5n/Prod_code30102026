import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateparentAccountComponent } from './createparent-account.component';
import { AccountsTypeService } from '../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { AccountService } from '../../services/accounts.services';
import { CustomerService } from '../../services/customers.services';
import { Loginservices } from '../../services/login.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateCustomerModule } from '../create-customer/create-customer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,
    NgSelectModule,
    CreateCustomerModule
  ],
  declarations: [CreateparentAccountComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateparentAccountComponent],
  providers: [NgxSpinnerService, AccountsTypeService, AccountsCatagoryServices,
    CountryDetailsServices, AccountService, CustomerService, Loginservices]
})
export class CreateparentAccountModule { }
