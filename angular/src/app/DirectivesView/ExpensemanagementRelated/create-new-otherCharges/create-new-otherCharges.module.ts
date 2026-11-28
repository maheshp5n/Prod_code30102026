import { CreateNewOtherChargesComponent } from './create-new-otherCharges.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { Loginservices } from '../../../services/login.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    RouterModule
  ],
  declarations: [CreateNewOtherChargesComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewOtherChargesComponent],
  providers: [DatePipe, NgxSpinnerService,Loginservices]
})
export class CreateNewOtherChargesModule { }
