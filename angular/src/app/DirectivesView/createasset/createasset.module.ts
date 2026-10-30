import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateassetComponent } from './createasset.component';
import { BsDatepickerModule, TimepickerModule, ModalModule } from 'ngx-bootstrap';
import { AssetsService } from '../../services/assets.services';
import { ProductsService } from '../../services/products.services';
import { AccountService } from '../../services/accounts.services';
import { PlantService } from '../../services/plants.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateSiteModule } from '../create-site/create-site.module';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    ModalModule,
    NgxSpinnerModule,
    CreateSiteModule,
    CreateNewProductModule,
    CreateaccountModule,
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateassetComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateassetComponent],
  providers: [NgxSpinnerService, AssetsService, ProductsService, AccountService, PlantService, DatePipe]
})
export class CreateassetModule { }
