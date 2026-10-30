import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertModule, ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import {BatProductReturnRouting } from './bat-product-return.routing';
import { BatProductReturnComponent } from './bat-product-return.component';
import { BtCreateProductReturnModule } from '../../batDirectiveView/bt-create-product-return/bt-create-product-return.module';
import { BtProductReturnReceiveModule } from '../../batDirectiveView/bt-product-return-receive/bt-product-return-receive.module';
@NgModule({
  declarations: [BatProductReturnComponent],
  imports: [
    CommonModule,
    BatProductReturnRouting,
    AlertModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreateProductReturnModule,
    BtProductReturnReceiveModule
  ],
  providers:[NgxSpinnerService]
})
export class BatProductReturnModule { }

