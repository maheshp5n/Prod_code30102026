import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, ModalModule, PopoverModule, TimepickerModule, TypeaheadModule } from 'ngx-bootstrap';
import { siteService } from '../../../services/siteService.services';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateexpensesManagementComponent } from './bt-createexpenses-management.component';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { AmazingTimePickerModule } from 'amazing-time-picker';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { ServiceAppointmentService } from '../../../services/serviceappointment.services';
import { CirclesService } from '../../../services/circles.services';
import { ContactsService } from '../../../services/contacts.services';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    BtCreatecontactsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    AmazingTimePickerModule,
    PopoverModule.forRoot(),
    RouterModule,
    NgSelectModule
  ],
  declarations: [BtCreateexpensesManagementComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateexpensesManagementComponent],
  providers: [DatePipe, BtexpensemanagementService, NgxSpinnerService, BtlegalentityService,
    BtcontactsService, CirclesService, Loginservices, BtServiceappointmentService]
})
export class BtCreateexpensesManagementModule{
    
}