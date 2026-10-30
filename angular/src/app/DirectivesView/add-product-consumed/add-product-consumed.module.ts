import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { AddProductConsumedComponent } from './add-product-consumed.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule
  ],
  declarations: [AddProductConsumedComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[AddProductConsumedComponent],
  providers:[]

})
export class AddProductConsumedModule { }
