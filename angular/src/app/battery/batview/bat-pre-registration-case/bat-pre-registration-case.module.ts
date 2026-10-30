import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatPreRegistrationCaseComponent } from './bat-pre-registration-case.component';
import { BatPreRegistrationCaseRouting } from './bat-pre-registration-case.routing';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreatePreRegistrationCaseModule } from '../../batDirectiveView/bt-create-pre-registration-case/bt-create-pre-registration-case.module';
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { FieldValidation } from '../../../services/validation.services';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';



@NgModule({
  declarations: [BatPreRegistrationCaseComponent],
  imports: [
    CommonModule,
    BatPreRegistrationCaseRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    BtCreatePreRegistrationCaseModule,
    OrderModule,ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  // declarations: [CasePreRegisComponent,SearchfiltPipe],
//  exports: [SearchfiltPipe],
  providers: [PagefilterService,BtCaseserviceService, NgxSpinnerService, FieldValidation,BtPreRegistrationCaseService]
})
export class BatPreRegistrationCaseModule { }
