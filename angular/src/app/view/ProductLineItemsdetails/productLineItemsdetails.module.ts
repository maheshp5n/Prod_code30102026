import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ProductLineItemsdetailsRouting } from './productLineItemsdetails.routing';
import { ProductLineItemsdetailsComponent } from './productLineItemsdetails.component';
import { UserforlistService } from '../../services/userforlist.service';
import { CreateNewProductLineItemsModule } from '../../DirectivesView/create-new-productLineItems/create-new-productLineItems.module';
import { ProductLineItemsService } from '../../services/product-line-items.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    ProductLineItemsdetailsRouting,
    NgxSpinnerModule,
    CreateNewProductLineItemsModule
  ],
  declarations: [ProductLineItemsdetailsComponent],
  providers: [ProductLineItemsService, NgxSpinnerService, Loginservices, UserforlistService]
})
export class ProductLineItemsdetailsModule { }
