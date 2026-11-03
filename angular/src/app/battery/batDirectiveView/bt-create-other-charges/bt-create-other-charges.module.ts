import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BtCreateOtherChargesComponent } from './bt-create-other-charges.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { RouterModule } from '@angular/router';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [BtCreateOtherChargesComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    RouterModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateOtherChargesComponent],
  providers: [DatePipe, NgxSpinnerService,Loginservices]
})
export class BtCreateOtherChargesModule { }
