import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ReassignServiceAppointmentComponent } from './reassign-service-appointment.component';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { siteService } from '../../services/siteService.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,    
    NgSelectModule
  ],
  declarations: [ReassignServiceAppointmentComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ReassignServiceAppointmentComponent],
  providers: [NgxSpinnerService,ligalEntityService,ServiceResourceService,siteService]
})
export class ReassignServiceAppointmentModule { }
