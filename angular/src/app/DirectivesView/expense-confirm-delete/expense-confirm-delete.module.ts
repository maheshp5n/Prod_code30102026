
import { NgSelectModule } from '@ng-select/ng-select';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { ExpenseConfirmDeleteComponent } from './expense-confirm-delete.component';
import { ExpenseManagementService } from '../../services/expenses-management.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    RouterModule,
    NgSelectModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ExpenseConfirmDeleteComponent],
  exports: [ExpenseConfirmDeleteComponent],
  providers: [
    Loginservices,
    NgxSpinnerService,ExpenseManagementService
  ]
})
export class ExpenseDeleteModule{}