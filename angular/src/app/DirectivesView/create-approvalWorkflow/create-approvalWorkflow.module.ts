import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateApprovalComponent } from './create-approvalWorkflow.component';
import { Loginservices } from '../../services/login.services';
import { ApprovalService } from '../../services/approval.services';
import { AccountsCatagoryServices } from '../../services/catagory.services';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule,NgSelectModule
  ],
  declarations: [CreateApprovalComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateApprovalComponent],
  providers: [NgxSpinnerService, Loginservices, ApprovalService, AccountsCatagoryServices]
})
export class CreateApprovalModule { }
