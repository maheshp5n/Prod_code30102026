import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateServiceAppoinmentBycaseComponent } from './create-service-appoinment-bycase.component';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule,    
    NgSelectModule
  ],
  declarations: [CreateServiceAppoinmentBycaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateServiceAppoinmentBycaseComponent],
  providers: [NgxSpinnerService]
  
})
export class CreateServiceAppoinmentBycaseModule { }
