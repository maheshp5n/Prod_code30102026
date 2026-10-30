import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Loginservices } from '../../../services/login.services';
import { VendorInvoiceService } from '../../../services/vendorinvoice.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtNewInvoiceComponent } from './bt-new-invoice.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,ReactiveFormsModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),NgSelectModule,
  
    ],
    declarations: [BtNewInvoiceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA,NO_ERRORS_SCHEMA],
    exports: [BtNewInvoiceComponent],
    providers: [NgxSpinnerService, Loginservices,DatePipe,VendorInvoiceService]
  })
export class BtNewInvoiceModule{
      
}