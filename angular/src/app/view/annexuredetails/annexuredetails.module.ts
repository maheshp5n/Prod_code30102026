import { CreateAnnexureLineItemModule } from './../../DirectivesView/create-annexureLineItem/create-annexureLineItem.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { CreateAnnexureModule } from '../../DirectivesView/create-annexure/create-annexure.module';
import { AssetsService } from '../../services/assets.services';
import { AnnexuredetailsRouting } from './annexuredetails.routing';
import { AnnexuredetailsComponent } from './annexuredetails.component';
import { ApprovalHistoryService } from '../../services/approvalHistory.service';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AnnexuredetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateAnnexureModule,
    BsDropdownModule,
    CreateAnnexureLineItemModule,ItemapprovalModule
  ],
  declarations: [AnnexuredetailsComponent],
  providers: [AssetsService, ApprovalHistoryService, NgxSpinnerService]
})
export class AnnexuredetailsModule { }
