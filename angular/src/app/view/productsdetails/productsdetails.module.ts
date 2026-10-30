import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/accounts.services';
import { ProductsDetailsRouting } from './productsdetails.routing';
import { ProductsdetailsComponent } from './productsdetails.component';
import { ProductsService } from '../../services/products.services';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { CreateNewProductModule } from '../../DirectivesView/create-new-product/create-new-product.module';
import { CreatePricebookModule } from '../../DirectivesView/createpricebook/createpricebook.module';
import {
  CreateProductAddToPriceBookModule
} from '../../DirectivesView/create-product-add-to-price-book/create-product-add-to-price-book.module';
import {
  CreateProductEditToPriceBookModule
} from '../../DirectivesView/create-product-edit-to-price-book/create-product-edit-to-price-book.module';
import { PrductItemModule } from '../../DirectivesView/prout-lin-itm/prout-lin-itm.module';
import { BillOfmaterialService } from '../../services/billOfMaterial.service';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    ProductsDetailsRouting,
    ContactcreateModule,
    CreateNewProductModule,
    BsDropdownModule,
    CreatePricebookModule,
    CreateProductAddToPriceBookModule,
    CreateProductEditToPriceBookModule,
    PrductItemModule,
    ActionhistoryModule
  ],
  declarations: [ProductsdetailsComponent],
  providers: [ProductsService, AccountService, NgxSpinnerService, Loginservices,BillOfmaterialService]
})
export class ProductsdetailsModule { }
