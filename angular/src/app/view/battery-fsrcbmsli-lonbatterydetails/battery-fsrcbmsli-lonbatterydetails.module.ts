import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { CreatebatteryFSRCBMSLilonbatteryModule } from '../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.module';
import { BatteryFSRCBMSLiLonbatterydetailsComponent } from './battery-fsrcbmsli-lonbatterydetails.component';
import { AssetsService } from '../../services/assets.services';
import { BatteryFSRCBMSLiLonbatterydetailsRouting } from './battery-fsrcbmsli-lonbatterydetails.routing';
import { CreateBatteryinventoryConsumptionModule } from '../../DirectivesView/create-batteryinventory-consumption/create-batteryinventory-consumption.module';
import { Loginservices } from '../../services/login.services';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BatteryFSRCBMSLiLonbatterydetailsRouting,
    NgxSpinnerModule,PdfViewerModule,
    ModalModule.forRoot(),
    CreatebatteryFSRCBMSLilonbatteryModule,
    CreateBatteryinventoryConsumptionModule,ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [BatteryFSRCBMSLiLonbatterydetailsComponent],
  providers: [ AssetsService, NgxSpinnerService,Loginservices]
})
export class BatteryFSRCBMSLiLonbatterydetailsModule { }
