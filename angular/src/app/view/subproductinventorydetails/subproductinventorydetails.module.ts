import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SubproductinventorydetailsRouting } from './subproductinventorydetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SubproductinventorydetailsComponent } from './subproductinventorydetails.component';
import { AssetsService } from '../../services/assets.services';
import { CreateSubProductInventoryModule } from '../../DirectivesView/create-sub-product-inventory/create-sub-product-inventory.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SubproductinventorydetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(), 
    CreateSubProductInventoryModule   
],
  declarations: [ SubproductinventorydetailsComponent ],
  providers: [ AssetsService, NgxSpinnerService]
})
export class SubproductinventorydetailsModule { }
