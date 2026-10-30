import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { AlertModule, BsDatepickerModule, BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import {BatProductTransferRouting } from './bat-product-transfer.routing';
import { BatProductTransferComponent } from './bat-product-transfer.component';
import { GlobalSearchPipeModule } from '../../../pipe/global-search-pipe.module';
import { BtProductTransferReceiveModule } from '../../batDirectiveView/bt-product-transfer-receive/bt-product-transfer-receive.module';
import { BtEwaybillModule } from '../../batDirectiveView/bt-ewaybill/bt-ewaybill.module';

@NgModule({
  declarations: [BatProductTransferComponent],
  imports: [
    CommonModule,
    BatProductTransferRouting,
    AlertModule,
    FormsModule,
    NgxSpinnerModule,
    CommonModule,
    AlertModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    BsDropdownModule,
    GlobalSearchPipeModule,
    BsDatepickerModule.forRoot(),
    BtProductTransferReceiveModule,
    BtEwaybillModule
  ],
  providers:[NgxSpinnerService,DatePipe]
})
export class BatProductTransferModule { }
