import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { CreateExpensesReportComponent } from './create-expenses-report.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  declarations: [CreateExpensesReportComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateExpensesReportComponent],
  providers:[]
})
export class CreateExpensesReportModule { }
