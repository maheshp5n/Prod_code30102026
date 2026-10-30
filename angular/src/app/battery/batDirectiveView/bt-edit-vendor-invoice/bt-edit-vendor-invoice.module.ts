import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { BtEditVendorInvoiceComponent } from './bt-edit-vendor-invoice.component';


@NgModule({
  declarations: [BtEditVendorInvoiceComponent],
  imports: [
    CommonModule,
    FormsModule,
    BsDatepickerModule
  ],
  providers: [NgxSpinnerService,DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtEditVendorInvoiceComponent]
})
export class BtEditVendorInvoiceModule { }
