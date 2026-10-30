import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatProductDetailRouting } from './bat-productdetail.routing';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { CreatePricebookModule } from '../../../DirectivesView/createpricebook/createpricebook.module';
import { CreateProductAddToPriceBookModule } from '../../../DirectivesView/create-product-add-to-price-book/create-product-add-to-price-book.module';
import { CreateProductEditToPriceBookModule } from '../../../DirectivesView/create-product-edit-to-price-book/create-product-edit-to-price-book.module';
import { PrductItemModule } from '../../../DirectivesView/prout-lin-itm/prout-lin-itm.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { ProductsService } from '../../../services/products.services';
import { AccountService } from '../../../services/accounts.services';
import { Loginservices } from '../../../services/login.services';
import { BillOfmaterialService } from '../../../services/billOfMaterial.service';
import { BatProductdetailComponent } from './bat-productdetail.component';
import { BtCreateProductModule } from '../../batDirectiveView/bt-creteproduct/bt-createproduct.module';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    BatProductDetailRouting,
    ContactcreateModule,
    BsDropdownModule,
    CreatePricebookModule,
    CreateProductAddToPriceBookModule,
    CreateProductEditToPriceBookModule,
    PrductItemModule,
    ActionhistoryModule,
    BtCreateProductModule,
    BtActionhistoryModule
  ],
  declarations: [BatProductdetailComponent],
  providers: [ProductsService, AccountService, NgxSpinnerService, Loginservices,BillOfmaterialService]
})
export class ProductsdetailsModule { }
