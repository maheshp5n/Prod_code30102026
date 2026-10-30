import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatProductTransferLineItemsDetailRouting } from './bat-product-transfer-line-items-detial.routing';
import { BatProductTransferLineItemsDetailComponent } from './bat-product-transfer-line-items-detail.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      BatProductTransferLineItemsDetailRouting,
      NgxSpinnerModule,
      
    ],
    declarations: [BatProductTransferLineItemsDetailComponent],
    providers: [NgxSpinnerService]
  })
export class BatProductTransferLineItemsDetailModule{

}