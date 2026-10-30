import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewbatproducttransferComponent } from './newbatproducttransfer.component';
import { NewBatProductTransferRouting } from './newbatproducttransfer.routing';
import { BtProducttransferModule } from '../../batDirectiveView/bt-producttransfer/bt-producttransfer.module';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, TooltipModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [NewbatproducttransferComponent],
  imports: [
    CommonModule,
    NewBatProductTransferRouting,
    BtProducttransferModule,
    FormsModule,  
    BsDatepickerModule,
    TooltipModule,
    NgxSpinnerModule
  ],
  
  providers: [NgxSpinnerService]
})
export class NewBatProductTransferModule { }