import { CommonModule } from "@angular/common";
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule } from "ngx-spinner";
import { AssignToServiceAppointmentComponent } from "./assign-to-service-appointment.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,    
    NgSelectModule
  
  
  ],
  declarations: [AssignToServiceAppointmentComponent],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [AssignToServiceAppointmentComponent]
})
export class AssignToServiceAppointmentModule { }