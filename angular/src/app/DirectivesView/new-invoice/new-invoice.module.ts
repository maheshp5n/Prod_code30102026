import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, FormControl, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Loginservices } from '../../services/login.services';
import { NewInvoiceComponent } from './new-invoice.component';
import { VendorInvoiceService } from '../../services/vendorinvoice.services';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,ReactiveFormsModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),NgSelectModule,
  
    ],
    declarations: [NewInvoiceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA,NO_ERRORS_SCHEMA],
    exports: [NewInvoiceComponent],
    providers: [NgxSpinnerService, Loginservices,DatePipe,VendorInvoiceService]
  })
export class NewInvoiceModule{
      
}