import { TourdetailsComponent } from './tourdetails.component';
import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { FormsModule } from '@angular/forms';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { TourDetailsRouting } from './tourdetails.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import {
  CreateNewTourExpensesModule
} from '../../../DirectivesView/ExpensemanagementRelated/create-new-tourExpenses/create-new-tourExpenses.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    TourDetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewTourExpensesModule,
    ActionhistoryModule
  ],
  declarations: [TourdetailsComponent],
  providers: [NgxSpinnerService]
})
export class TourdetailsModule { }
