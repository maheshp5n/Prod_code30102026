import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { Loginservices } from '../../../services/login.services';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';
import { BtUpdateSapNumberComponent } from './bt-update-sap-number.component';



@NgModule({
  declarations: [BtUpdateSapNumberComponent],
  imports: [
    CommonModule,
    FormsModule,ReactiveFormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),NgSelectModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA,NO_ERRORS_SCHEMA],
  exports:[BtUpdateSapNumberComponent],
  providers:[NgxSpinnerService,Loginservices,DatePipe,BtInvoiceService]
})
export class BtUpdateSapNumberModule { }
