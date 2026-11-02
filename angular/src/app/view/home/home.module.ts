import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { FormsModule } from '@angular/forms';
//import { ChartsModule } from 'ng2-charts/ng2-charts';
import { ChartsModule } from 'ng2-charts';
import { Homerouting } from './home.routing.module';
import { HomeComponent } from './home.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GoogleChartsModule } from 'angular-google-charts';
import { DashboardreportModule } from '../../DirectivesView/dashboardreport/dashboardreport.module';

@NgModule({
  imports: [
    CommonModule,
    Homerouting,
    FormsModule,
    ChartsModule,
    ModalModule,
    TooltipModule,
    NgxSpinnerModule,
    GoogleChartsModule,
    DashboardreportModule
  ],
  providers: [NgxSpinnerService],
  declarations: [HomeComponent]
})
export class HomeModule { }
