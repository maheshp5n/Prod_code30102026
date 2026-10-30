import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceappointmentProductconsumedModule } from '../../DirectivesView/serviceappointment-productconsumed/serviceappointment-productconsumed.module';
import { CreateServiceresourceModule } from '../../DirectivesView/create-serviceresource/create-serviceresource.module';
import { CreatebatteryFSRCBMSLilonbatteryModule } from '../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.module';
import { ReassignServiceAppointmentModule } from '../../DirectivesView/reassign-service-appointment/reassign-service-appointment.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { AgmCoreModule } from '@agm/core';
import { VendorServiceAppointmentDetailRouting } from './vendor-service-appointment-detail.routing';
import { VendorServiceAppointmentDetailComponent } from './vendor-service-appointment-detail.component';
import { AssignToServiceAppointmentComponent } from '../../DirectivesView/assign-to-service-appointment/assign-to-service-appointment.component';
import { AssignToServiceAppointmentModule } from '../../DirectivesView/assign-to-service-appointment/assign-to-service-appointment.module';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    VendorServiceAppointmentDetailRouting,
    CreateNewServiceAppointmentsModule,
    NgxSpinnerModule,
    ServiceappointmentProductconsumedModule,
    CreateServiceresourceModule,
    CreatebatteryFSRCBMSLilonbatteryModule,
    ReassignServiceAppointmentModule,
    ReactiveFormsModule,
    PdfViewerModule,
    ItemapprovalModule,
    // AssignToServiceAppointmentComponent,
    AssignToServiceAppointmentModule,
    ActionhistoryModule, AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDt8jbYUC6XPSh-avvQhs7vU8Iuw9E2vfE'
    }),
 
    
  ],
  declarations: [VendorServiceAppointmentDetailComponent],
  providers: [ NgxSpinnerService,Loginservices,ServiceAppointmentService,DatePipe]
})
export class VendorServiceAppointmentDetailModule { }
