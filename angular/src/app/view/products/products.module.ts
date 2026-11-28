import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ProductsComponent } from './products.component';
import { ProductsRouting } from './products.routing';
import { ProductsService } from '../../services/products.services';
import { SearchfiltPipe } from './searchfilt.pipe';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateNewProductModule } from '../../DirectivesView/create-new-product/create-new-product.module';
import { FieldValidation } from '../../services/validation.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ProductsRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    ContactcreateModule,
    CreateNewProductModule
  ],
  declarations: [ProductsComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ProductsService, FieldValidation, NgxSpinnerService]
})
export class ProductsModule { }
