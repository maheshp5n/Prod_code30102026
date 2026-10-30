import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BatteryinventoryconsumptionComponent } from './batteryinventoryconsumption.component';
import { SearchfiltPipe } from '../batteryinventoryconsumption/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { Batteryinventoryconsumptionrouting } from './batteryinventoryconsumption.routing';
import { CreateBatteryinventoryConsumptionModule } from '../../DirectivesView/create-batteryinventory-consumption/create-batteryinventory-consumption.module';
import { ManageInventoryService } from '../../services/manageinventory.services';


@NgModule({
  imports: [
    CommonModule,
    Batteryinventoryconsumptionrouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateBatteryinventoryConsumptionModule
  ],
  declarations: [ BatteryinventoryconsumptionComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [NgxSpinnerService,ManageInventoryService]
  
})
export class BatteryinventoryconsumptionModule { }
