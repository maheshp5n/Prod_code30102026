import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { cityService } from "../../services/cityService.service";
import { CreateinvoiceComponent } from './createinvoice.component';
import { CommercialService } from '../../services/commercial.services';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),

  ],
  declarations: [CreateinvoiceComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateinvoiceComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe,CommercialService]
})
export class CreateInvoiceModule{

}