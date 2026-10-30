import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap';
import { CustomerService } from '../../../services/customers.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateCustomerModule } from '../../../DirectivesView/create-customer/create-customer.module';
import { FormsModule } from '@angular/forms';
import { BtCreateaccountComponent } from './bt-createaccount.component';
import { AccountsTypeService } from '../../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../../services/catagory.services';
import { AccountService } from '../../../services/accounts.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
 import {BtCreateParentCustomerModule} from '../bt-create-parent-customer/bt-create-parent-customer.module';
import { BtCreateParentAccountModule } from '../bt-create-parent-account/bt-create-parent-account.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    CreateCustomerModule,
    BtCreateParentCustomerModule,
    RouterModule,
    NgSelectModule,
    BtCreateParentAccountModule
  ],
  declarations: [BtCreateaccountComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateaccountComponent],
  providers: [NgxSpinnerService, AccountsTypeService, AccountsCatagoryServices,
    CountryDetailsServices, AccountService, CustomerService, Loginservices]
})
export class BTCreateaccountModule { }
