import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatteryFSRCBMSLiLonbatteryRouting } from './battery-fsrcbmsli-lonbattery.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreatebatteryFSRCBMSLilonbatteryComponent } from '../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.component';
import { CreatebatteryFSRCBMSLilonbatteryModule } from '../../DirectivesView/createbattery-fsrcbmslilonbattery/createbattery-fsrcbmslilonbattery.module';
import { BatteryFSRCBMSLiLonbatteryComponent } from './battery-fsrcbmsli-lonbattery.component';
import { SearchfiltPipe } from '../battery-fsrcbmsli-lonbattery/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    BatteryFSRCBMSLiLonbatteryRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreatebatteryFSRCBMSLilonbatteryModule
  ],
  declarations: [ BatteryFSRCBMSLiLonbatteryComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService,BatrryFSRService,Loginservices,PagefilterService]  
})
export class BatteryFSRCBMSLiLonbatteryModule { }
