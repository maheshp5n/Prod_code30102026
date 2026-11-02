import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PartcodechangelistRouting } from './partcodechangelist.routing';
import { PartcodechangelistComponent } from './partcodechangelist.component';
import { ApprovedlistComponent } from './approvedlist/approvedlist.component';
import { PendinglistComponent } from './pendinglist/pendinglist.component';
import { FormsModule } from '@angular/forms';
import { PartchangemapprovalModule } from '../../DirectivesView/partchangemapproval/partchangemapproval.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PartcodechangenewModule } from '../../DirectivesView/partcodechangenew/partcodechangenew.module';
import { DragableModule } from '../../DirectivesView/dragable.module';



@NgModule({
  declarations: [PartcodechangelistComponent, ApprovedlistComponent, PendinglistComponent],
  imports: [
    CommonModule,
    PartcodechangelistRouting,
    FormsModule,
    PartchangemapprovalModule,
    ModalModule,
    NgxSpinnerModule,
    PartcodechangenewModule,
    DragableModule
  ],
  providers:[NgxSpinnerService]
})
export class PartcodechangelistModule { }
