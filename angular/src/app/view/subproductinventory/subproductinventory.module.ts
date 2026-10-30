import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubproductinventoryRouting } from './subproductinventory.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { SubproductinventoryComponent } from './subproductinventory.component';
import { SearchfiltPipe } from '../subproductinventory/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { CreateSubProductInventoryModule } from '../../DirectivesView/create-sub-product-inventory/create-sub-product-inventory.module';

@NgModule({
  imports: [
    CommonModule,
    SubproductinventoryRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateSubProductInventoryModule
  ],
  declarations: [ SubproductinventoryComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class SubproductinventoryModule { }
