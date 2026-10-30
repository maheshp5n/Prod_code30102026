import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, ModalModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { Loginservices } from '../../../services/login.services';
import { BtInvoiceService } from '../../bat_services/bt-invoice.service';
import { BtCommercialVendorInvoiceComponent } from './bt-commercial-vendor-invoice.component';



@NgModule({
  declarations: [BtCommercialVendorInvoiceComponent],
  imports: [
    CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),NgSelectModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCommercialVendorInvoiceComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe,BtInvoiceService]
})
export class BtCommercialVendorInvoiceModule { }
