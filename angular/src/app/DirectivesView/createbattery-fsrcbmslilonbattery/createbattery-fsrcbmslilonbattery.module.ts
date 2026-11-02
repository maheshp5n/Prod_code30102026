import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreatebatteryFSRCBMSLilonbatteryComponent } from './createbattery-fsrcbmslilonbattery.component';
import { Loginservices } from '../../services/login.services';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { caseService } from '../../services/caseService.service';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,TypeaheadModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),   
    NgxSpinnerModule,RouterModule
  ],
  declarations: [CreatebatteryFSRCBMSLilonbatteryComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreatebatteryFSRCBMSLilonbatteryComponent],
  providers:[NgxSpinnerService,Loginservices,BatrryFSRService,caseService,TypeaheadConfig,DatePipe]  
})
export class CreatebatteryFSRCBMSLilonbatteryModule { }
