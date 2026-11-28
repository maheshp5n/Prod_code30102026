import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatTourdetailsComponent } from './bat-tourdetails.component';
import { BatTourdetailsRouting } from './bat-tourdetails.roting';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { BtCreateTourExpensesModule } from '../../../batDirectiveView/bt-create-tour-expenses/bt-create-tour-expenses.module';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';



@NgModule({
  declarations: [BatTourdetailsComponent],
  imports: [
    CommonModule,
    BatTourdetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BtCreateTourExpensesModule,
    ActionhistoryModule
  ],
  providers: [NgxSpinnerService]
})
export class BatTourdetailsModule { }
