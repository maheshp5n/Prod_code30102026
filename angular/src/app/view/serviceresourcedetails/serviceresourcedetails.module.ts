import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServiceresourcedetailsRouting } from './serviceresourcedetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CreateServiceresourceModule } from '../../DirectivesView/create-serviceresource/create-serviceresource.module';
import { ServiceresourcedetailsComponent } from './serviceresourcedetails.component';
import { AssetsService } from '../../services/assets.services';
import {
  CreateNewServiceAppointmentsModule
} from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { RouterModule } from '@angular/router';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ServiceresourcedetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateServiceresourceModule,
    BsDropdownModule,
    RouterModule,
    CreateNewServiceAppointmentsModule,
    ActionhistoryModule
  ],
  declarations: [ServiceresourcedetailsComponent],
  providers: [AssetsService, NgxSpinnerService, ServiceAppointmentService]
})
export class ServiceresourcedetailsModule { }
