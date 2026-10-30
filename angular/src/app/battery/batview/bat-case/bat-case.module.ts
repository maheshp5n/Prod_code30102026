import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule, BsDropdownModule, ModalModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatCaseRouting } from './bat-case.routing';
import { BatCaseComponent } from './bat-case.component';
import { caseService } from '../../../services/caseService.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BTCreateCaseModule } from '../../batDirectiveView/btcreatecase/btcreatecase.module';
import { BtCreatePODModule } from '../../batDirectiveView/bt-create-pod/bt-create-pod.module';
import { CasetypeCaseresnPopupComponent } from '../../batDirectiveView/casetype-caseresn-popup/casetype-caseresn-popup.component';
import { casetyperesnpopupModule } from '../../batDirectiveView/casetype-caseresn-popup/casetype-caseresn-popup.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BatCaseRouting,
    BsDropdownModule,
    NgxSpinnerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,ReactiveFormsModule,
    BsDatepickerModule.forRoot(),BTCreateCaseModule,casetyperesnpopupModule
   
  ],
  declarations: [BatCaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatCaseComponent],
  providers: [NgxSpinnerService,DatePipe,PagefilterService,caseService,BtCaseserviceService]
})
export class BatCaseModule{
   
}