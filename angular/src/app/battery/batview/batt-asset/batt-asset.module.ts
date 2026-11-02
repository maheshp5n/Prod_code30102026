import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { BattAssetComponent } from './batt-asset.component';
import { BatAssetReouting } from './batt-asset.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtCreateAssetModule } from '../../batDirectiveView/bt-createasset/bt-createasset.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,BatAssetReouting,
    BsDropdownModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    BtCreateAssetModule
   
  ],
  declarations: [BattAssetComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BattAssetComponent],
  providers: [NgxSpinnerService,DatePipe,PagefilterService,BTassetserviceService]
})
export class BattAssetModule{}