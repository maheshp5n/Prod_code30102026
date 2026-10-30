import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RequisitionindetailsRouting } from './requisitionindetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { CreateRequisitionModule } from '../../DirectivesView/create-requisition/create-requisition.module';
import { RequisitionindetailsComponent } from './requisitionindetails.component';
import { RequisitionService } from '../../services/requisition.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RequisitionindetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateRequisitionModule
],
  declarations: [ RequisitionindetailsComponent ],
  providers: [ RequisitionService, NgxSpinnerService]
})
export class RequisitionindetailsModule { }
