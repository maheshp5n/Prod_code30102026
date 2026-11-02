import { DashboardreportComponent } from './dashboardreport.component';
import { GoogleChartsModule } from 'angular-google-charts';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [DashboardreportComponent],
  imports: [
    CommonModule,
    GoogleChartsModule,ModalModule,FormsModule,NgxSpinnerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DashboardreportComponent],
  providers:[NgxSpinnerService]
})
export class DashboardreportModule { }
