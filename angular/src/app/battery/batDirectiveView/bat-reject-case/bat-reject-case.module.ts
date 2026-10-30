import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import {
  AccordionModule,
  AlertModule,
  ModalModule,
} from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BatRejectCaseComponent } from './bat-reject-case.component';
@NgModule({
  declarations: [BatRejectCaseComponent
],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ModalModule,
    NgSelectModule,
    AccordionModule,
    NgxSpinnerModule,
    AlertModule,
    ModalModule,ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatRejectCaseComponent],
  providers: [NgxSpinnerService,BtCaseserviceService]
})
export class BtCaseRejectModule{

}