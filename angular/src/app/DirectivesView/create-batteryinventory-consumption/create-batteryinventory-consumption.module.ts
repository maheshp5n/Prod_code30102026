import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateBatteryinventoryConsumptionComponent } from './create-batteryinventory-consumption.component';
import { Loginservices } from '../../services/login.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),   
    NgxSpinnerModule
  ],
  declarations: [CreateBatteryinventoryConsumptionComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateBatteryinventoryConsumptionComponent],
  providers:[NgxSpinnerService,Loginservices]  
})

export class CreateBatteryinventoryConsumptionModule { }
