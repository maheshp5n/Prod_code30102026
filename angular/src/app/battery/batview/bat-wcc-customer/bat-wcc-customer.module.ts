import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BatWccCustomerComponent } from './bat-wcc-customer.component';
import { BatWccCustomerRouting } from './bat-wcc-customer.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { PagefilterService } from '../../../services/pagefilterservice';



@NgModule({
  declarations: [BatWccCustomerComponent],
  imports: [
    CommonModule,
    BatWccCustomerRouting,
    FormsModule,ReactiveFormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    // ContactcreateModule,
    BsDatepickerModule.forRoot()
  ],
  providers: [Loginservices, NgxSpinnerService,DatePipe,PagefilterService]
  
})
export class BatWccCustomerModule { }
