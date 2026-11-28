import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatBoardingdetailsComponent } from './bat-boardingdetails.component';
import { BatBoardingdetailsRouting } from './bat-boardingdetails.roting';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { BtCreateexpensesManagementModule } from '../../../batDirectiveView/bt-createexpenses-management/bt-createexpenses-management.module';
import { BtCreateBoardingModule } from '../../../batDirectiveView/bt-create-boarding/bt-create-boarding.module';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';



@NgModule({
  declarations: [BatBoardingdetailsComponent],
  imports: [
    CommonModule,
    BatBoardingdetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BtCreateexpensesManagementModule,
    BtCreateBoardingModule,
    ActionhistoryModule
  ],
  providers: [NgxSpinnerService]
})
export class BatBoardingdetailsModule { }
