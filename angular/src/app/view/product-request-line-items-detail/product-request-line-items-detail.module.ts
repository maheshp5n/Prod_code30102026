import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ProductRequestLineItemsDetailComponent } from './product-request-line-items-detail.component';
import { ProductRequestLineItemRouting } from './product-request-line-items-detail.routing';
import { Loginservices } from '../../services/login.services';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      ProductRequestLineItemRouting,
      NgxSpinnerModule,
      
    ],
    declarations: [ProductRequestLineItemsDetailComponent],
    providers: [ NgxSpinnerService, Loginservices]
  })
export class ProductRequestLineItemsDetailModule{

}