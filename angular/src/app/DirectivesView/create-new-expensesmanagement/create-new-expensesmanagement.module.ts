import { NgSelectModule } from '@ng-select/ng-select';
import { PopoverModule } from 'ngx-bootstrap';
import { ContactcreateModule } from './../contactcreate/contactcreate.module';
import { Loginservices } from './../../services/login.services';
import { CirclesService } from './../../services/circles.services';
import { ContactsService } from './../../services/contacts.services';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { CreateNewexpensesmanagementComponent } from './create-new-expensesmanagement.component';
import { AmazingTimePickerModule } from 'amazing-time-picker';
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
    AmazingTimePickerModule,
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
