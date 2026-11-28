import { NgSelectModule } from '@ng-select/ng-select';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { RouterModule } from '@angular/router';
import { CreateAccountOnlyComponent } from './create-account-only.component';
import { ExpenseManagementService } from '../../services/expenses-management.service';

@NgModule({
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
  declarations: [CreateAccountOnlyComponent],
  exports: [CreateAccountOnlyComponent],
  providers: [
    Loginservices,
    NgxSpinnerService,ExpenseManagementService
    
  ]
})
export class createAcountOnlyModule{

}