import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatOtherChargesdetailsComponent } from './bat-other-chargesdetails.component';
import { BatOtherChargesdetailsRouting } from './bat-other-chargesdetails.roting';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';
import { BtCreateOtherChargesModule } from '../../../batDirectiveView/bt-create-other-charges/bt-create-other-charges.module';



@NgModule({
  declarations: [BatOtherChargesdetailsComponent],
  imports: [
    CommonModule,
    BatOtherChargesdetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    ActionhistoryModule,
    BtCreateOtherChargesModule
  ],
  providers: [NgxSpinnerService]

})
export class BatOtherChargesdetailsModule { }
