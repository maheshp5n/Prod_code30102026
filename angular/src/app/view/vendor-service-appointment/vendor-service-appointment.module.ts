import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { Loginservices } from '../../services/login.services';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { VendorServiceAppointmentRouting } from './vendor-service-appointment.routing';
import { VendorServiceAppointmentComponent } from './vendor-service-appointment.component';



@NgModule({
  imports: [
    CommonModule,
    VendorServiceAppointmentRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    CreateNewServiceAppointmentsModule,
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule,
    OrderModule,BsDatepickerModule.forRoot()
  ],
  providers: [NgxSpinnerService,Loginservices,ServiceAppointmentService,DatePipe],
  declarations: [VendorServiceAppointmentComponent],
  exports: []
})
export class VendorServiceAppointmentModule { }
