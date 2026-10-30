import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule, BsDatepickerModule, TimepickerModule, TooltipModule } from "ngx-bootstrap";
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