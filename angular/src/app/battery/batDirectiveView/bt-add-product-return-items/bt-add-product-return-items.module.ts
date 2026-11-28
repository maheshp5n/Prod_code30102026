import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {BtAddProductReturnItemsComponent} from './bt-add-product-return-items.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@NgModule({
  declarations: [BtAddProductReturnItemsComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule
  ],
  exports :[BtAddProductReturnItemsComponent],
  providers :[NgxSpinnerService]
})
export class BtAddProductReturnItemsModule { }
