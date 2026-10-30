import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxSpinnerModule } from 'ngx-spinner';

import { BtEwaybillComponent } from './bt-ewaybill.component';

@NgModule({
  declarations: [BtEwaybillComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    NgxSpinnerModule
  ],
  exports: [BtEwaybillComponent]
})
export class BtEwaybillModule {}
