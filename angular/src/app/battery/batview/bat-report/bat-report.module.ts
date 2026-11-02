import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BatReportRouting } from './bat-report.routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BatReportComponent } from './bat-report.component';
import { BatreportexecutionComponent } from './batreportexecution/batreportexecution.component';
import { BatreportpersentationComponent } from './batreportpersentation/batreportpersentation.component';



@NgModule({
  declarations: [BatReportComponent, BatreportexecutionComponent, BatreportpersentationComponent],
  imports: [
    CommonModule,
    FormsModule,ReactiveFormsModule,
    BatReportRouting,
    ModalModule,
    
  ],
})
export class BatReportModule { }
