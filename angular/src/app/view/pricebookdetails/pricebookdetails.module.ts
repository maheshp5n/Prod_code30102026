import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { AssetsService } from '../../services/assets.services';
import { pricebookDetailRoting } from './pricebookdetails.routing';
import { PricebookdetailsComponent } from './pricebookdetails.component';
import { PriceBookService } from '../../services/pricebook.service';
import { CreatePricebookModule } from '../../DirectivesView/createpricebook/createpricebook.module';
import { productListModule } from '../../DirectivesView/productlist/productlist.module';
import { CreateNewProductModule } from '../../DirectivesView/create-new-product/create-new-product.module';
import { AddproductEntriesModule } from '../../DirectivesView/addproductlistpricebookntries/addproductlistpricebookntries.module';
import { Loginservices } from '../../services/login.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    pricebookDetailRoting,
    NgxSpinnerModule,
    ModalModule,
    BsDropdownModule,
    CreatePricebookModule,
    AddproductEntriesModule,
    productListModule,
    CreateNewProductModule,
    ActionhistoryModule
],
  declarations: [PricebookdetailsComponent],
  providers: [ AssetsService, NgxSpinnerService,PriceBookService,Loginservices]
})
export class PricebookdetailsModule{

}