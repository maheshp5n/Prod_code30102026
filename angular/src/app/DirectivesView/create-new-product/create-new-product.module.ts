import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CreateNewProductComponent } from './create-new-product.component';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ProductsService } from '../../services/products.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule
  ],
  declarations: [CreateNewProductComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewProductComponent],
  providers: [ProductsService, NgxSpinnerService]
})
export class CreateNewProductModule { }
