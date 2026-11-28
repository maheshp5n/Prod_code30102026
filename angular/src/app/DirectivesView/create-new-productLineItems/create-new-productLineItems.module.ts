import { PopupSuccessModule } from './../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateNewProductLineItemsComponent } from './create-new-productLineItems.component';
import { ProductLineItemsService } from '../../services/product-line-items.service';
import { ProductsService } from '../../services/products.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule

  ],
  declarations: [CreateNewProductLineItemsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewProductLineItemsComponent],
  providers: [ProductLineItemsService, NgxSpinnerService, ProductsService]
})
export class CreateNewProductLineItemsModule { }
