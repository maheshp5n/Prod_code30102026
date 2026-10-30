import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { Loginservices } from '../../../services/login.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BatServiceappointmentRouting } from './bat-serviceappointment.routing';
import { BatServiceappointmentComponent } from './bat-serviceappointment.component';
import { caseService } from '../../../services/caseService.service';


@NgModule({
  imports: [
    CommonModule,
    BatServiceappointmentRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule,
    OrderModule,BsDatepickerModule.forRoot()
  ],
  providers: [NgxSpinnerService,Loginservices,ServiceAppointmentService,DatePipe,caseService],
  declarations: [BatServiceappointmentComponent],
//   exports: [SearchfiltPipe]
schemas: [
  CUSTOM_ELEMENTS_SCHEMA
],
})
export class BatServiceappointmentModule { }
