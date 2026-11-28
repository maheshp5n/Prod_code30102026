import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatServiceresourceDetailsComponent } from './bat-serviceresource-details.component';
import { BatServiceresourceDetailsRouting } from './bat-serviceresource-details.routing';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreateserviceresourceModule } from '../../batDirectiveView/bt-createserviceresource/bt-createserviceresource.module';
import { CreateNewServiceAppointmentsModule } from '../../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { RouterModule } from '@angular/router';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { AssetsService } from '../../../services/assets.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtCreateNewServiceAppointmentsModule } from '../../batDirectiveView/bt-create-new-service-appointment/bt-create-new-service-appointment.module';



@NgModule({
  declarations: [BatServiceresourceDetailsComponent],
  imports: [
    CommonModule,
    BatServiceresourceDetailsRouting,
    FormsModule,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    BtCreateserviceresourceModule,
    BsDropdownModule,
    RouterModule,
    BtCreateNewServiceAppointmentsModule,
    ActionhistoryModule
  ],
  providers: [BTassetserviceService, NgxSpinnerService, BtServiceappointmentService]
})
export class BatServiceresourceDetailsModule { }
