import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BtCreateProductReturnComponent} from './bt-create-product-return.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

@NgModule({
  declarations: [BtCreateProductReturnComponent],
  imports: [
    CommonModule,
    FormsModule, 
    BsDatepickerModule,
  ],
  exports :[BtCreateProductReturnComponent],
  providers : []
})
export class BtCreateProductReturnModule { }
