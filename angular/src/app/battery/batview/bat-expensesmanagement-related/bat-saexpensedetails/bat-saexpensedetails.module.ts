import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatSAExpensedetailsRouting } from './bat-saexpensedetails.roting';
import { BatSAExpensedetailsComponent } from './bat-saexpensedetails.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { BtCreateExpenseserviceappointmentModule } from '../../../batDirectiveView/bt-create-expenseserviceappointment/bt-create-expenseserviceappointment.module';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';



@NgModule({
  declarations: [BatSAExpensedetailsComponent],
  imports: [
    CommonModule,
    BatSAExpensedetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BtCreateExpenseserviceappointmentModule,
    ActionhistoryModule
  ],
  providers: [NgxSpinnerService]
})
export class BatSAExpensedetailsModule { }
