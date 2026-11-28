import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { BtCreateWccCustomerApproveComponent } from './bt-create-wcc-customer-approve.component';
import { Loginservices } from '../../../services/login.services';



@NgModule({
  declarations: [BtCreateWccCustomerApproveComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    TooltipModule,ReactiveFormsModule,PdfViewerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateWccCustomerApproveComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe]
})
export class BtCreateWccCustomerApproveModule { }
