import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule, BsDatepickerModule, TimepickerModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { CommercialService } from '../../../services/commercial.services';
import { BtCreateInvoiceComponent } from './bt-create-invoice.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),

  ],
  declarations: [BtCreateInvoiceComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateInvoiceComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe,CommercialService]
})
export class BtCreateInvoiceModule{

}