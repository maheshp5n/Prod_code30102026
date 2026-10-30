import { CreateNewexpensesmanagementModule } from './../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { CirclesService } from './../../services/circles.services';
import { Loginservices } from './../../services/login.services';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDatepickerModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ExpensesmanagementRouting } from './expensesmanagement.routing';
import { ligalEntityService } from '../../services/ligalentity.services';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { ExpenseManagementService } from '../../services/expenses-management.service';
import { ExpensesmanagementComponent } from './expensesmanagement.component';
import { TourdetailsModule } from '../expensesmanagementRelated/tourdetails/tourdetails.module';
import { PagefilterService } from '../../services/pagefilterservice';
import { ExpenseDeleteModule } from '../../DirectivesView/expense-confirm-delete/expense-confirm-delete.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ExpensesmanagementRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    CreateNewexpensesmanagementModule,
    TourdetailsModule,ExpenseDeleteModule, BsDatepickerModule.forRoot()
  ],
  declarations: [ExpensesmanagementComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ExpenseManagementService, ligalEntityService, NgxSpinnerService, Loginservices, CirclesService,PagefilterService],
})
export class ExpensesmanagementModule { }
