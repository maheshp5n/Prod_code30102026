import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { PriceBookService } from '../../services/pricebook.service';
import { SearchfiltPipe } from './searchfilt.pipe';
import { ProductlistComponent } from './productlist.component';
import { AddproductEntriesModule } from '../addproductlistpricebookntries/addproductlistpricebookntries.module';
import { ProductsModule } from "../../view/products/products.module";



@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,AddproductEntriesModule,ProductsModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    declarations: [SearchfiltPipe,ProductlistComponent],
    exports: [SearchfiltPipe,ProductlistComponent],
    providers: [NgxSpinnerService,PriceBookService, Loginservices]
  })
export class productListModule{

}