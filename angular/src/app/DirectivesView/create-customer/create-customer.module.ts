import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateCustomerComponent } from './create-customer.component';
import { CustomerService } from '../../services/customers.services';
import { Loginservices } from '../../services/login.services';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule,
    RouterModule
  ],
  declarations: [CreateCustomerComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateCustomerComponent],
  providers: [NgxSpinnerService, CustomerService, Loginservices, TypeaheadConfig]
})

export class CreateCustomerModule { }
