import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ProductReturnLineItemDetailRouting } from './product-return-line-item-detail.routing';
import { ProductReturnLineItemDetailComponent } from './product-return-line-item-detail.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      ProductReturnLineItemDetailRouting,
      NgxSpinnerModule,
      
    ],
    declarations: [ProductReturnLineItemDetailComponent],
    providers: [ NgxSpinnerService, Loginservices]
  })
export class ProductReturnLineItemDetailModule{

}