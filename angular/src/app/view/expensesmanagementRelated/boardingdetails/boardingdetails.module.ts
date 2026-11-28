import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { FormsModule } from '@angular/forms';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BoardingDetailsRouting } from './boardingdetails.routing';
import { BoardingdetailsComponent } from './boardingdetails.component';
import { CreateNewBoardingModule } from '../../../DirectivesView/ExpensemanagementRelated/create-new-boarding/create-new-boarding.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    BoardingDetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewexpensesmanagementModule,
    CreateNewBoardingModule,
    ActionhistoryModule
  ],
  declarations: [BoardingdetailsComponent],
  providers: [NgxSpinnerService]
})
export class BoardingdetailsModule { }
