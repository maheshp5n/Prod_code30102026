import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtExpenseCreateAccountOnlyComponent } from './bt-expense-create-account-only.component';
import { Loginservices } from '../../../services/login.services';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';



@NgModule({
  declarations: [BtExpenseCreateAccountOnlyComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    RouterModule,
    NgSelectModule,
    BsDatepickerModule.forRoot()
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtExpenseCreateAccountOnlyComponent],
  providers: [
    Loginservices,
    NgxSpinnerService,BtexpensemanagementService
    
  ]
})
export class BtExpenseCreateAccountOnlyModule { }
