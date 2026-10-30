import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { CreateReportsForExpenseComponent } from './create-reports-for-expense.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  declarations: [CreateReportsForExpenseComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateReportsForExpenseComponent],
  providers:[]
})
export class CreateReportsForExpenseModule { }
