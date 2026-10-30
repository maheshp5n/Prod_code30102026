import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AccountsTypeService } from '../../../services/accountstype.services';
import { AccountsCatagoryServices } from '../../../services/catagory.services';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { AccountService } from '../../../services/accounts.services';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateParentAccountComponent} from '../../batDirectiveView/bt-create-parent-account/bt-create-parent-account.component';
import { BtCreateParentCustomerModule } from '../bt-create-parent-customer/bt-create-parent-customer.module';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,
    NgSelectModule,BtCreateParentCustomerModule
  ],
  declarations: [BtCreateParentAccountComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateParentAccountComponent],
  providers: [NgxSpinnerService, AccountsTypeService, AccountsCatagoryServices,
    CountryDetailsServices, AccountService, CustomerService, Loginservices]
})
export class BtCreateParentAccountModule { }
