import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ServiceappointmentindetailsRouting } from './serviceappointmentindetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { ServiceappointmentindetailsComponent } from './serviceappointmentindetails.component';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceappointmentProductconsumedModule } from '../../DirectivesView/serviceappointment-productconsumed/serviceappointment-productconsumed.module';
import { CreateServiceresourceModule } from '../../DirectivesView/create-serviceresource/create-serviceresource.module';
import { CreatebatteryFSRCBMSLilonbatteryModule } from '../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.module';
import { ReassignServiceAppointmentModule } from '../../DirectivesView/reassign-service-appointment/reassign-service-appointment.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { AgmCoreModule } from '@agm/core';
import { URLSafeModule } from '../../pipe/url-safe-pipe.module';
import { platformBrowser } from '@angular/platform-browser';
@NgModule({  
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    ServiceappointmentindetailsRouting,
    CreateNewServiceAppointmentsModule,
    NgxSpinnerModule,
    ServiceappointmentProductconsumedModule,
    CreateServiceresourceModule,
    CreatebatteryFSRCBMSLilonbatteryModule,
    ReassignServiceAppointmentModule,
    ReactiveFormsModule,
    PdfViewerModule,
    ItemapprovalModule,
    ActionhistoryModule, AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDt8jbYUC6XPSh-avvQhs7vU8Iuw9E2vfE'
    }),
 
    
  ],
  declarations: [ServiceappointmentindetailsComponent],
  providers: [ NgxSpinnerService,Loginservices,ServiceAppointmentService,DatePipe]
})
export class ServiceappointmentindetailsModule { }
