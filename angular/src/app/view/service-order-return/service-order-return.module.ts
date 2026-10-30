import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { Loginservices } from '../../services/login.services';
import { ServiceOrderReturnRouting } from './service-order-return.routing';
import { ServiceOrderReturnComponent } from './service-order-return.component';


@NgModule({
  imports: [
    CommonModule,
    ServiceOrderReturnRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule,
    OrderModule,BsDatepickerModule.forRoot()
  ],
  providers: [NgxSpinnerService,Loginservices,DatePipe],
  declarations: [ServiceOrderReturnComponent]
//   exports: [SearchfiltPipe]
})
export class ServiceOrderReturnModule{

}