import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatReturnOrderReceiveDirComponent } from './bat-return-order-receive-dir.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BatReturnOrderReceiveDirComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule
  ],
  providers:[NgxSpinnerService],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BatReturnOrderReceiveDirComponent]
})
export class BatReturnOrderReceiveDirModule { }
