import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { ProductsService } from '../../../services/products.services';
import { BtCreteproductComponent } from './bt-creteproduct.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,  
    ModalModule,
    NgxSpinnerModule,
    RouterModule
  ],
  declarations: [BtCreteproductComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreteproductComponent],
  providers: [ProductsService, NgxSpinnerService]
})
export class BtCreateProductModule { }
