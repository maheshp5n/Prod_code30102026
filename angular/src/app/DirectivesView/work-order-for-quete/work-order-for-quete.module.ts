import { CommonModule, DatePipe } from "@angular/common";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule, BsDatepickerModule, TimepickerModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { FieldValidation } from "../../services/validation.services";
import { RouterModule } from '@angular/router';
import { WorkOrderForQueteComponent } from './work-order-for-quete.component';
import { workOrderForQuetService } from '../../services/workorderforquote.services';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(), 
  ],
  declarations: [WorkOrderForQueteComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [WorkOrderForQueteComponent],
  providers: [NgxSpinnerService, Loginservices, FieldValidation,workOrderForQuetService,DatePipe],
})
export class WorkOrderForQuoteModule{

  

}