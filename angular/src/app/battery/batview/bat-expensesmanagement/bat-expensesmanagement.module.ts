import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatExpensesmanagementRouting } from './bat-expensesmanagement.routing';
import { BatExpensesmanagementComponent } from './bat-expensesmanagement.component';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { ExpenseDeleteModule } from '../../../DirectivesView/expense-confirm-delete/expense-confirm-delete.module';
import { TourdetailsModule } from '../../../view/expensesmanagementRelated/tourdetails/tourdetails.module';
import { CirclesService } from '../../../services/circles.services';
import { PagefilterService } from '../../../services/pagefilterservice';
import { SearchfiltPipe } from './searchfilt.pipe';
import { BtCreateexpensesManagementModule } from '../../batDirectiveView/bt-createexpenses-management/bt-createexpenses-management.module';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BatExpensesmanagementRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    BtCreateexpensesManagementModule,
    TourdetailsModule,ExpenseDeleteModule, BsDatepickerModule.forRoot()
   
  ],
  declarations: [BatExpensesmanagementComponent, SearchfiltPipe],
  // schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [SearchfiltPipe],
  providers: [BtexpensemanagementService, BtlegalentityService, NgxSpinnerService, Loginservices, CirclesService,PagefilterService]
})
export class BatExpensesmanagementModule{}