import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateNewServiceAppointmentByCaseComponent } from './bt-create-new-service-appointment-by-case.component';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule,    
    NgSelectModule
  ],

  declarations: [BtCreateNewServiceAppointmentByCaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateNewServiceAppointmentByCaseComponent],
  providers: [NgxSpinnerService]
  
})
export class BtCreateServiceAppoinmentBycaseModule { }
