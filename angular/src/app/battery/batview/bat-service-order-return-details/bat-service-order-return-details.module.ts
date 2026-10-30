import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatServiceOrderReturnDetailsComponent } from './bat-service-order-return-details.component';
import { BatServiceOrderReturDetailsRouting } from './bat-service-order-return-details.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [BatServiceOrderReturnDetailsComponent],
  imports: [
    CommonModule,
    BatServiceOrderReturDetailsRouting,
    NgxSpinnerModule,
    FormsModule
  ],
  providers:[NgxSpinnerService]
})
export class BatServiceOrderReturnDetailsModule { }
