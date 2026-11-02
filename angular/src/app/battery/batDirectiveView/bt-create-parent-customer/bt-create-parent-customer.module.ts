import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CustomerService } from '../../../services/customers.services';
import { Loginservices } from '../../../services/login.services';
import {BtCreateParentCustomerComponent} from './bt-create-parent-customer.component';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule
  ],
  declarations: [BtCreateParentCustomerComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateParentCustomerComponent],
  providers: [NgxSpinnerService, CustomerService, Loginservices]
})
export class BtCreateParentCustomerModule { }
