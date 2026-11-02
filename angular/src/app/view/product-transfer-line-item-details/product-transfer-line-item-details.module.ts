import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ProductTransferLineItemDetailRoting } from './product-transfer-line-item-details.routing';
import { ProductTransferLineItemDetailsComponent } from './product-transfer-line-item-details.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      ProductTransferLineItemDetailRoting,
      NgxSpinnerModule,
      
    ],
    declarations: [ProductTransferLineItemDetailsComponent],
    providers: [ NgxSpinnerService, Loginservices]
  })
export class ProductTransferLineItemDetailModule{

}