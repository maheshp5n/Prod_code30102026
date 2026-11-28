import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatPreRegistrationCaseDetailComponent } from './bat-pre-registration-case-detail.component';
import { BatPreRegistrationCaseDetailRouting } from './bat-pre-registration-case-detail.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BtCreatePreRegistrationCaseModule } from '../../batDirectiveView/bt-create-pre-registration-case/bt-create-pre-registration-case.module';
import { BtCreateNewServiceAppointmentsModule } from '../../batDirectiveView/bt-create-new-service-appointment/bt-create-new-service-appointment.module';
import { CreateNewOpportunityModule } from '../../../DirectivesView/create-new-opportunity/create-new-opportunity.module';
import { CreateProoveOFDeliveryModule } from '../../../DirectivesView/create-proove-of-delivery/create-proove-of-delivery.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { prcActionModule } from '../../../DirectivesView/prcaction/prcaction.module';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { Loginservices } from '../../../services/login.services';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtPrcActionModule } from '../../batDirectiveView/bt-prc-action/bt-prc-action.module';



@NgModule({
  declarations: [BatPreRegistrationCaseDetailComponent],
  imports: [
    CommonModule,
    BatPreRegistrationCaseDetailRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule,ReactiveFormsModule,BtCreatePreRegistrationCaseModule,
    BtCreateNewServiceAppointmentsModule,
    CreateNewOpportunityModule,CreateProoveOFDeliveryModule,
    ActionhistoryModule,BtPrcActionModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [BtCaseserviceService, NgxSpinnerService,ApprovalHistoryService,Loginservices,BtPreRegistrationCaseService]
  
})
export class BatPreRegistrationCaseDetailModule { }
