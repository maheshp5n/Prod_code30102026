import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { InventroyProductConsumedComponent } from './inventroy-product-consumed.component';
import { AssetsService } from '../../services/assets.services';
import { CreateManageinventoryModule } from '../create-manageinventory/create-manageinventory.module';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    CreateNewProductModule,
    CreateManageinventoryModule,
    NgSelectModule
  ],
  declarations: [InventroyProductConsumedComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [InventroyProductConsumedComponent],
  providers: [NgxSpinnerService,AssetsService,ManageInventoryService]
})
export class InventroyProductConsumedModule { }
