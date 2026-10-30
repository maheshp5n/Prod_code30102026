import { WarehouseService } from './../../services/warehouse.service';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule, TypeaheadModule } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { RouterModule } from '@angular/router';
import { AddproductlistpricebookntriesComponent } from './addproductlistpricebookntries.component';
import { PriceBookService } from '../../services/pricebook.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [AddproductlistpricebookntriesComponent],
  exports: [AddproductlistpricebookntriesComponent],
  providers: [
     PriceBookService,
    Loginservices,
    NgxSpinnerService,
    BoardingLoadingService,
    WarehouseService
  ]
})
export class AddproductEntriesModule { }
