import { CreateNewexpensesmanagementModule } from '../../../DirectivesView/create-new-expensesmanagement/create-new-expensesmanagement.module';
import { FormsModule } from '@angular/forms';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { OtherChargesDetailsRouting } from './otherChargesdetails.routing';
import { OtherChargesdetailsComponent } from './otherChargesdetails.component';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    OtherChargesDetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    PopupSuccessModule,
    FormsModule,
    BsDropdownModule,
    CreateNewexpensesmanagementModule,
    ActionhistoryModule
  ],
  declarations: [OtherChargesdetailsComponent],
  providers: [NgxSpinnerService]
})
export class OtherChargesdetailsModule { }
