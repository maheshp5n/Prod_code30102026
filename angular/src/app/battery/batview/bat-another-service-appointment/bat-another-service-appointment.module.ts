import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatAnotherServiceAppointmentRouting } from './bat-another-service-appointment-routing';
import { BatAnotherServiceAppointmentComponent } from './bat-another-service-appointment.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BatAnotherServiceAppointmentComponent

  ],
  imports: [
    CommonModule,
    BatAnotherServiceAppointmentRouting,
    NgxSpinnerModule
  ],providers:[NgxSpinnerService]
})
export class BatAnotherServiceAppointmentModule { }
