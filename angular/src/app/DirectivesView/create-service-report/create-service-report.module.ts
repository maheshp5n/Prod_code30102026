import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateServiceReportComponent } from './create-service-report.component';
import { Loginservices } from '../../services/login.services';
import { ServiceReportService } from '../../services/servicereport.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),   
    NgxSpinnerModule
  ],
  declarations: [CreateServiceReportComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateServiceReportComponent],
  providers:[NgxSpinnerService,Loginservices,ServiceReportService]  
})
export class CreateServiceReportModule { }
