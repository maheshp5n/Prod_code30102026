import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApprovalRouting } from './approval.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
// import { CreateApprovalModule } from '../../DirectivesView/create-approval/create-approval.module';
import { ApprovalComponent } from './approval.component';
import { SearchfiltPipe } from '../approval/searchfilt.pipe';
import { ApprovalService } from '../../services/approval.services';
import { AnnexureResponse } from '../../services/model';
import { AnnexureService } from '../../services/annexure.services';
import { ApprovaldetailsModule } from '../approvalDetails/approvaldetails.module';
import {
  CreateApprovalModule
} from '../../DirectivesView/create-approvalWorkflow/create-approvalWorkflow.module';

@NgModule({
  imports: [
    CommonModule,
    ApprovalRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateApprovalModule,
    PopupSuccessModule
  ],
  declarations: [ApprovalComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ApprovalService, NgxSpinnerService]
})
export class ApprovalModule { }
