import { LodgingdetailsComponent } from './lodgingdetails.component';
import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { FormsModule } from '@angular/forms';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { LodgingDetailsRouting } from './lodgingdetails.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import {
  CreateNewLodgingModule
} from '../../../DirectivesView/ExpensemanagementRelated/create-new-lodging/create-new-lodging.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    LodgingDetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewexpensesmanagementModule,
    CreateNewLodgingModule,
    ActionhistoryModule
  ],
  declarations: [LodgingdetailsComponent],
  providers: [NgxSpinnerService]
})
export class LodgingdetailsModule { }
