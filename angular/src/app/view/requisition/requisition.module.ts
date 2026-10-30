import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RequisitionRouting } from './requisition.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { RequisitionComponent } from './requisition.component';
import { SearchfiltPipe } from '../requisition/searchfilt.pipe';
import { CreateRequisitionModule } from '../../DirectivesView/create-requisition/create-requisition.module';
import { RequisitionService } from '../../services/requisition.services';

@NgModule({
  imports: [
    CommonModule,
    RequisitionRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateRequisitionModule
  ],
  declarations: [ RequisitionComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [NgxSpinnerService,RequisitionService]  
})
export class RequisitionModule { }
