import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../../services/pagefilterservice';
import { caseService } from '../../../services/caseService.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCreateCaseModule } from '../../batDirectiveView/btcreatecase/btcreatecase.module';
import { BatCaseDetailComponent } from './bat-case-detail.component';
import { BatCaseDetailRouting } from './bat-case-detail.roting';
import { Loginservices } from '../../../services/login.services';
import { ApprovalHistoryService } from '../../../services/approvalHistory.service';
import { BtCreatePODModule } from '../../batDirectiveView/bt-create-pod/bt-create-pod.module';
import { BtCreateServiceAppoinmentBycaseModule } from '../../batDirectiveView/bt-create-new-service-appointment-by-case/bt-create-new-service-appointment-by-case.module';
import { BtCaseRejectModule } from '../../batDirectiveView/bat-reject-case/bat-reject-case.module';
import { BtChangeCaseTypeModule } from '../../batDirectiveView/bt-change-case-type-commer/bt-change-case-type-commer.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BatCaseDetailRouting,
    BsDropdownModule,
    NgxSpinnerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,ReactiveFormsModule,
    BsDatepickerModule.forRoot(),BTCreateCaseModule,BtCreatePODModule,
    BtCreateServiceAppoinmentBycaseModule,BtCaseRejectModule,BtChangeCaseTypeModule
   
  ],
  declarations: [BatCaseDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatCaseDetailComponent],
  providers: [NgxSpinnerService,DatePipe,PagefilterService,caseService,BtCaseserviceService,
    Loginservices,ApprovalHistoryService]
})
export class BatCaseDetailModule{

}