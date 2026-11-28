import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { CommercialService } from '../../services/commercial.services';
import { CreateProoveOfDeliveryComponent } from './create-proove-of-delivery.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    TooltipModule

  ],
  declarations: [CreateProoveOfDeliveryComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateProoveOfDeliveryComponent],
  providers: [NgxSpinnerService, Loginservices,DatePipe,CommercialService]
})
export class CreateProoveOFDeliveryModule{

}