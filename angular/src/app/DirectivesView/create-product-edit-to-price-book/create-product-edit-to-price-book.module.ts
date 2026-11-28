import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateProductEditToPriceBookComponent } from './create-product-edit-to-price-book.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ProductsService } from '../../services/products.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  declarations: [CreateProductEditToPriceBookComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateProductEditToPriceBookComponent],
  providers: [ProductsService]
})
export class CreateProductEditToPriceBookModule { }
