import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtProductTransferReceiveComponent } from './bt-product-transfer-receive.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [BtProductTransferReceiveComponent],
  exports : [BtProductTransferReceiveComponent],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class BtProductTransferReceiveModule { }
