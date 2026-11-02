import { LocalConveyancedetailsComponent } from './localConveyancedetails.component';
import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { FormsModule } from '@angular/forms';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { LocalConveyanceDetailsRouting } from './localConveyancedetails.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import {
  CreateNewLocalConveyanceExpenseModule
} from '../../../DirectivesView/ExpensemanagementRelated/create-new-localConveyanceExpense/create-new-localConveyanceExpense.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    LocalConveyanceDetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewexpensesmanagementModule,
    CreateNewLocalConveyanceExpenseModule,
    ActionhistoryModule
  ],
  declarations: [LocalConveyancedetailsComponent],
  providers: [NgxSpinnerService]
})
export class LocalConveyancedetailsModule { }
