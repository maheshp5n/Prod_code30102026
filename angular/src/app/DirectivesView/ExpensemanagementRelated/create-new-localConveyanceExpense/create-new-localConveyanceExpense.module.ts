import { ligalEntityService } from '../../../services/ligalentity.services';
import { CirclesService } from '../../../services/circles.services';
import { ContactcreateModule } from '../../contactcreate/contactcreate.module';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { CreateNewLocalConveyanceExpenseComponent } from './create-new-localConveyanceExpense.component';
import { Loginservices } from '../../../services/login.services';
import { ContactsService } from '../../../services/contacts.services';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, TypeaheadModule, BsDatepickerModule, TimepickerModule, DatepickerModule, TooltipModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { TourExpenseService } from '../../../services/tourExpense.service';
import { RouterModule } from '@angular/router';
import { BLDAdetailMoule } from '../../boding-lodging-da-detail/boding-lodging-da-detail.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    ContactcreateModule,
    TypeaheadModule.forRoot(),
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    RouterModule,
    TooltipModule,BLDAdetailMoule
  ],
  declarations: [CreateNewLocalConveyanceExpenseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewLocalConveyanceExpenseComponent],
  providers: [DatePipe, ExpenseManagementService, NgxSpinnerService,
    TourExpenseService, ligalEntityService, ContactsService, CirclesService, Loginservices]
})
export class CreateNewLocalConveyanceExpenseModule { }
