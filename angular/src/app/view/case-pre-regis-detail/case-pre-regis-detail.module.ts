import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { caseService } from '../../services/caseService.service';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { CaseCreatePreRegisComponent } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.component';

import { CreatePreRegisCase } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.module';
import { CasePreRegisDetailComponent } from './case-pre-regis-detail.component';
import { PreRegisCaseDetailRouting } from './case-pre-regis-detail.routing';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { Loginservices } from '../../services/login.services';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { CreateNewOpportunityModule } from '../../DirectivesView/create-new-opportunity/create-new-opportunity.module';
import { CreateProoveOFDeliveryModule } from '../../DirectivesView/create-proove-of-delivery/create-proove-of-delivery.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { prcActionModule } from '../../DirectivesView/prcaction/prcaction.module';



@NgModule({
  imports: [
    CommonModule,
    PreRegisCaseDetailRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule,ReactiveFormsModule,CreatePreRegisCase,
    CreateNewServiceAppointmentsModule,
    CreateNewOpportunityModule,CreateProoveOFDeliveryModule,
    ActionhistoryModule,prcActionModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [CasePreRegisDetailComponent],
  providers: [caseService, NgxSpinnerService,ApprovalHistoryService,Loginservices,PreRegCaseService]
})
export class PreRegisCaseDetailModul{
    
}