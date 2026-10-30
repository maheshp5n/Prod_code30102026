import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { Loginservices } from '../../services/login.services';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommercialVendorInvoiceComponent } from './commercial-vendor-invoice.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),NgSelectModule,
  
    ],
    declarations: [CommercialVendorInvoiceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CommercialVendorInvoiceComponent],
    providers: [NgxSpinnerService, Loginservices,DatePipe,VendorInvoiceService]
  })
export class CommercialVendorInvoiceModule{

}