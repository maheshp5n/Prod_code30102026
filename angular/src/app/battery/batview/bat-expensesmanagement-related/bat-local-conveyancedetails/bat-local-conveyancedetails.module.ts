import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatLocalConveyancedetailsComponent } from './bat-local-conveyancedetails.component';
import { BatLocalConveyancedetailsRouting } from './bat-local-conveyancedetails.roting';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';
import { BtCreateLocalConveyanceExpenseModule } from '../../../batDirectiveView/bt-create-local-conveyance-expense/bt-create-local-conveyance-expense.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BatLocalConveyancedetailsComponent],
  imports: [
    CommonModule,
    BatLocalConveyancedetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BtCreateLocalConveyanceExpenseModule,
    ActionhistoryModule
  ],
  providers: [NgxSpinnerService]
})
export class BatLocalConveyancedetailsModule { }
