import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { CreateServiceresourceModule } from '../../../DirectivesView/create-serviceresource/create-serviceresource.module';
import { CreatebatteryFSRCBMSLilonbatteryModule } from '../../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ItemapprovalModule } from '../../../DirectivesView/itemapproval/itemapproval.module';
import { AgmCoreModule } from '@agm/core';
import { BatServiceappointmentindetailsRouting } from './bat-serviceappointmentdetails.routing';
import { BatServiceappointmentdetailsComponent } from './bat-serviceappointmentdetails.component';
import { BatReassignServiceAppointmentModule } from '../../batDirectiveView/bat-reassignserviceappointment/bat-reassignserviceappointment.module';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtCreateNewServiceAppointmentsModule } from '../../batDirectiveView/bt-create-new-service-appointment/bt-create-new-service-appointment.module';
import { BatServiceappointmentProductconsumedModule } from '../bat-seviceappoint-product-consumed/bat-serviceappintment-product-consumed.module';
import {
  BtCreateFsrServiceModule
} from '../../batDirectiveView/bt-create-fsr-service/bt-create-fsr-service.module';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    BatServiceappointmentindetailsRouting,
    BtCreateNewServiceAppointmentsModule,
    NgxSpinnerModule,
    BatServiceappointmentProductconsumedModule,
    CreateServiceresourceModule,
    CreatebatteryFSRCBMSLilonbatteryModule,
    BatReassignServiceAppointmentModule,
    ReactiveFormsModule,
    PdfViewerModule,
    ItemapprovalModule,
    BtCreateFsrServiceModule,
    BatTobeapprovedDrModule,
    BtActionhistoryModule, AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDt8jbYUC6XPSh-avvQhs7vU8Iuw9E2vfE'
    }),


  ],
  declarations: [BatServiceappointmentdetailsComponent],
  providers: [NgxSpinnerService, Loginservices, ServiceAppointmentService, DatePipe, ApprovalHistoryService]
})
export class BatServiceappointmentindetailsModule { }
