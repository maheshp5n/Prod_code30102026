import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtCreateExpenseserviceappointmentComponent } from './bt-create-expenseserviceappointment.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [BtCreateExpenseserviceappointmentComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    RouterModule
  ],
  exports: [BtCreateExpenseserviceappointmentComponent],

})
export class BtCreateExpenseserviceappointmentModule { }
