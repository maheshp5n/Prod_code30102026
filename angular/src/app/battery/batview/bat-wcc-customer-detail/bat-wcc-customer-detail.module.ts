import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BatWccCustomerDetailComponent } from './bat-wcc-customer-detail.component';
import { BatWccCustomerDetailRouting } from './bat-wcc-customer-detail.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { caseService } from '../../../services/caseService.service';
import { CommercialService } from '../../../services/commercial.services';
import { Loginservices } from '../../../services/login.services';
import { BtCreateWccCustomerApproveModule } from '../../batDirectiveView/bt-create-wcc-customer-approve/bt-create-wcc-customer-approve.module';
import { BtCreatePODModule } from '../../batDirectiveView/bt-create-pod/bt-create-pod.module';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';



@NgModule({
  declarations: [BatWccCustomerDetailComponent],
  imports: [
    CommonModule,
    BatWccCustomerDetailRouting,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PdfViewerModule,
    BtActionhistoryModule,
    BtCreateWccCustomerApproveModule,BtCreatePODModule,
    BatTobeapprovedDrModule
  ],
  providers: [caseService, NgxSpinnerService, Loginservices,CommercialService,DatePipe,ApprovalHistoryService]
})
export class BatWccCustomerDetailModule { }
