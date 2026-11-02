import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ProductLineItemsComponent } from './ProductLineItems.component';
import { ProductLineItemsRouting } from './ProductLineItems.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { SearchfiltPipe } from './searchfilt.pipe';
import { CreateNewProductLineItemsModule } from '../../DirectivesView/create-new-productLineItems/create-new-productLineItems.module';
import { ProductLineItemsService } from '../../services/product-line-items.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    CreateNewProductLineItemsModule,
    ProductLineItemsRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule
  ],
  declarations: [ProductLineItemsComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ProductLineItemsService, NgxSpinnerService]
})
export class ProductLineItemsModule { }
