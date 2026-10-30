import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtProductReturnReceiveComponent } from './bt-product-return-receive.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BtProductReturnReceiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule
  ],
  exports :[BtProductReturnReceiveComponent],
  providers :[NgxSpinnerService]
})
export class BtProductReturnReceiveModule { }
