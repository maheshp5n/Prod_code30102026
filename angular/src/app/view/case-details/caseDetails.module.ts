
import { NgModule } from "@angular/core";

import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { caseDetailsRouting } from './caseDetails.routing';
import { CaseDetailsComponent } from './case-details.component';
import { caseService } from '../../services/caseService.service';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { CreateServiceAppoinmentBycaseModule } from '../../DirectivesView/create-service-appoinment-bycase/create-service-appoinment-bycase.module';
import {CreateNewOpportunityModule
} from '../../DirectivesView/create-new-opportunity/create-new-opportunity.module';
import { CreateProoveOFDeliveryModule } from '../../DirectivesView/create-proove-of-delivery/create-proove-of-delivery.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';
import { ActionHistoryComponent } from '../../DirectivesView/action-history/action-history.component';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { RejectCaseCOmModule } from "../../DirectivesView/rejectcase/rejectcase.module";
import { ChangeCaseTypeModule } from "../../DirectivesView/change-casetype-commer/change_casetype-commer.module";
import { PreRegCaseService } from "../../services/preregcaseservice.services";


@NgModule({
  imports: [
    CommonModule,
    caseDetailsRouting,
    ModalModule,ReactiveFormsModule,
    FormsModule,PdfViewerModule,ItemapprovalModule,
    BsDropdownModule,
    NgxSpinnerModule, CreatecaseModule,
    CreateNewServiceAppointmentsModule,
    CreateServiceAppoinmentBycaseModule,
    CreateNewOpportunityModule,CreateProoveOFDeliveryModule,
    ActionhistoryModule,RejectCaseCOmModule,ChangeCaseTypeModule
  ],
  declarations: [CaseDetailsComponent],
 providers: [caseService, NgxSpinnerService, Loginservices,ApprovalHistoryService,PreRegCaseService]
})
export class caseDetailsModule {

}