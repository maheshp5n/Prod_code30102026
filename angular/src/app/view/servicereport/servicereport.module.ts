import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicereportRouting } from './servicereport.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { ServicereportComponent } from './servicereport.component';
import { SearchfiltPipe } from '../servicereport/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { CreateServiceReportModule } from '../../DirectivesView/create-service-report/create-service-report.module';

@NgModule({
  imports: [
    CommonModule,
    ServicereportRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateServiceReportModule
  ],
  declarations: [ ServicereportComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class ServicereportModule { }
