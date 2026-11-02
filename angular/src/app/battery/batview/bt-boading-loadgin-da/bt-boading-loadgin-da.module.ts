import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BtCreateAssetModule } from '../../batDirectiveView/bt-createasset/bt-createasset.module';
import { BTBodingLodginDARouting } from './bt-boading-loadgin-da-routing';
import { BtBoadingLoadginDaComponent } from './bt-boading-loadgin-da.component';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { BTBodingLodginDACreateModule } from '../../batDirectiveView/bt-create-boading-lodgin-da/bt-create-boading-da-module';
import { BAT_BoardingLoadingService } from '../../bat_services/bt-boadigLoadgingDA.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,BTBodingLodginDARouting,
    BsDropdownModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,BTBodingLodginDACreateModule,
    BtCreateAssetModule
   
  ],
  declarations: [BtBoadingLoadginDaComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtBoadingLoadginDaComponent],
  providers: [NgxSpinnerService,DatePipe,BAT_BoardingLoadingService]
})
export class BTBodingLodginDAModule{

}