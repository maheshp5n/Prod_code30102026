import { NgSelectModule } from '@ng-select/ng-select';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { ContactcreateModule } from './../contactcreate/contactcreate.module';
import { Loginservices } from './../../services/login.services';
import { CirclesService } from './../../services/circles.services';
import { ContactsService } from './../../services/contacts.services';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { CreateNewexpensesmanagementComponent } from './create-new-expensesmanagement.component';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    ContactcreateModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    PopoverModule.forRoot(),
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateNewexpensesmanagementComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewexpensesmanagementComponent],
  providers: [DatePipe, ExpenseManagementService, NgxSpinnerService, ligalEntityService,
    ContactsService, CirclesService, Loginservices, ServiceAppointmentService]
})
export class CreateNewexpensesmanagementModule { }
