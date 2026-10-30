import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { AssetsService } from '../../services/assets.services';
import { BatteryinventoryconsumptionindetailsComponent } from './batteryinventoryconsumptionindetails.component';
import { Batteryinventoryconsumptionindetailsrouting } from './batteryinventoryconsumptionindetails.routing';
import { CreateBatteryinventoryConsumptionModule } from '../../DirectivesView/create-batteryinventory-consumption/create-batteryinventory-consumption.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    Batteryinventoryconsumptionindetailsrouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateBatteryinventoryConsumptionModule
  ],
  declarations: [BatteryinventoryconsumptionindetailsComponent],
  providers: [ AssetsService, NgxSpinnerService]
})
export class BatteryinventoryconsumptionindetailsModule { }
