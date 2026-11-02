import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ServiceResourceService } from '../../../services/serviceresource.services';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { siteService } from '../../../services/siteService.services';
import { BatReassignserviceappointmentComponent } from './bat-reassignserviceappointment.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,    
    NgSelectModule
  ],
  declarations: [BatReassignserviceappointmentComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatReassignserviceappointmentComponent],
  providers: [NgxSpinnerService,ligalEntityService,ServiceResourceService,siteService]
})
export class BatReassignServiceAppointmentModule { }
