import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { FormsModule } from '@angular/forms';
import { Homerouting } from './home.routing.module';
import { HomeComponent } from './home.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { DashboardreportModule } from '../../DirectivesView/dashboardreport/dashboardreport.module';

@NgModule({
  imports: [
    CommonModule,
    Homerouting,
    FormsModule,
    ModalModule,
    TooltipModule,
    NgxSpinnerModule,
    DashboardreportModule
  ],
  providers: [NgxSpinnerService],
  declarations: [HomeComponent]
})
export class HomeModule { }
