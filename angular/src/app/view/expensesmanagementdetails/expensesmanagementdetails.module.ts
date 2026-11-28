import { BoardingExpenseService } from './../../services/boardingExpense.service';
import { CreateNewOtherChargesModule } from './../../DirectivesView/ExpensemanagementRelated/create-new-otherCharges/create-new-otherCharges.module';
import { OtherChargesService } from './../../services/otherCharges.service';
import { CreateNewLocalConveyanceExpenseModule } from './../../DirectivesView/ExpensemanagementRelated/create-new-localConveyanceExpense/create-new-localConveyanceExpense.module';
import { LocalConveyanceExpenseService } from './../../services/localConveyanceExpense.service';
import { CreateNewLodgingModule } from './../../DirectivesView/ExpensemanagementRelated/create-new-lodging/create-new-lodging.module';
import { TourExpenseService } from './../../services/tourExpense.service';
import { CreateNewTourExpensesModule } from './../../DirectivesView/ExpensemanagementRelated/create-new-tourExpenses/create-new-tourExpenses.module';
import { ExpenseManagementService } from './../../services/expenses-management.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/accounts.services';
import { ExpensesmanagementDetailsRouting } from './expensesmanagementdetails.routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { FormsModule } from '@angular/forms';
import { ligalEntityService } from '../../services/ligalentity.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateNewexpensesmanagementModule } from '../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { ExpensesmanagementdetailsComponent } from './expensesmanagementdetails.component';
import { CreateNewBoardingModule } from '../../DirectivesView/ExpensemanagementRelated/create-new-boarding/create-new-boarding.module';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ReactiveFormsModule } from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { BLDAdetailMoule } from '../../DirectivesView/boding-lodging-da-detail/boding-lodging-da-detail.module';
import { createAcountOnlyModule } from '../../DirectivesView/create-account-only/create-account-only.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { Loginservices } from '../../services/login.services';
import { ExpenseDeleteModule } from '../../DirectivesView/expense-confirm-delete/expense-confirm-delete.module';

@NgModule({
  imports: [
    CommonModule,
    ExpensesmanagementDetailsRouting,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreateNewexpensesmanagementModule,
    BsDropdownModule,
    CreateNewTourExpensesModule,
    CreateNewLodgingModule,
    CreateNewBoardingModule,
    CreateNewLocalConveyanceExpenseModule,
    CreateNewOtherChargesModule,
    AccordionModule,
    ReactiveFormsModule,
    createAcountOnlyModule,
    TabsModule,
    PdfViewerModule,
    ItemapprovalModule,
    BLDAdetailMoule,
    ActionhistoryModule,
    ExpenseDeleteModule

  ],
  declarations: [ExpensesmanagementdetailsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [TourExpenseService, ExpenseManagementService, AccountService,
    ApprovalHistoryService,Loginservices,
    ligalEntityService, NgxSpinnerService, BoardingExpenseService, LocalConveyanceExpenseService, OtherChargesService]
})
export class ExpensesmanagementdetailsModule { }
