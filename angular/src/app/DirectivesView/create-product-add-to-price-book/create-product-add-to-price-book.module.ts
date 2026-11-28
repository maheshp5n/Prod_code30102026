import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateProductAddToPriceBookComponent } from './create-product-add-to-price-book.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ProductsService } from '../../services/products.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  declarations: [CreateProductAddToPriceBookComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateProductAddToPriceBookComponent],
  providers: [ProductsService]
})
export class CreateProductAddToPriceBookModule { }
