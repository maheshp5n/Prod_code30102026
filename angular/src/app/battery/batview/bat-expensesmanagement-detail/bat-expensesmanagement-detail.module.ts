import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AccordionModule, BsDropdownModule, ModalModule, TabsModule} from 'ngx-bootstrap';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ExpenseDeleteModule } from '../../../DirectivesView/expense-confirm-delete/expense-confirm-delete.module';
import { BtCreateexpensesManagementModule } from '../../batDirectiveView/bt-createexpenses-management/bt-createexpenses-management.module';
import { BatExpensesmanagementDetailComponent } from './bat-expensesmanagement-detail.component';
import { createAcountOnlyModule } from '../../../DirectivesView/create-account-only/create-account-only.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ItemapprovalModule } from '../../../DirectivesView/itemapproval/itemapproval.module';
import { BLDAdetailMoule } from '../../../DirectivesView/boding-lodging-da-detail/boding-lodging-da-detail.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BatExpensesmanagementDetailRouting } from './bat-expensesmanagement-detail.routing';
import { BtCreateBoardingModule } from '../../batDirectiveView/bt-create-boarding/bt-create-boarding.module';
import { BtCreateLodgingModule } from '../../batDirectiveView/bt-create-lodging/bt-create-lodging.module';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtboardingExpenseService } from '../../bat_services/btboarding-expense.service';
import { BtlocalConveyanceExpenseService } from '../../bat_services/btlocal-conveyance-expense.service';
import { BtotherChargesExpenseService } from '../../bat_services/btother-charges-expense.service';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BttourExpenseService } from '../../bat_services/bttour-expense.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BtCreateOtherChargesModule } from '../../batDirectiveView/bt-create-other-charges/bt-create-other-charges.module';
import { BtCreateLocalConveyanceExpenseModule } from '../../batDirectiveView/bt-create-local-conveyance-expense/bt-create-local-conveyance-expense.module';
import { BtCreateTourExpensesModule } from '../../batDirectiveView/bt-create-tour-expenses/bt-create-tour-expenses.module';
import { BtCreateExpenseserviceappointmentModule } from '../../batDirectiveView/bt-create-expenseserviceappointment/bt-create-expenseserviceappointment.module';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtExpenseCreateAccountOnlyModule } from '../../batDirectiveView/bt-expense-create-account-only/bt-expense-create-account-only.module';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';


@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    BtCreateexpensesManagementModule,
    BsDropdownModule,
    BtCreateTourExpensesModule,
    BtCreateLodgingModule,
    BtCreateBoardingModule,
    BtCreateLocalConveyanceExpenseModule,
    BtCreateOtherChargesModule,
    AccordionModule,
    ReactiveFormsModule,
    BtExpenseCreateAccountOnlyModule,
    TabsModule,
    PdfViewerModule,
    ItemapprovalModule,
    BLDAdetailMoule,
    BtActionhistoryModule,
    ExpenseDeleteModule,
    BtCreateExpenseserviceappointmentModule,
    BatExpensesmanagementDetailRouting,
    BatTobeapprovedDrModule
  ],
  declarations: [BatExpensesmanagementDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [BttourExpenseService, BtexpensemanagementService, BtaccountService,
    ApprovalHistoryService,Loginservices,
    BtlegalentityService, NgxSpinnerService, BtboardingExpenseService, BtlocalConveyanceExpenseService, BtotherChargesExpenseService]
})
export class BatExpensesmanagementDetailModule{}