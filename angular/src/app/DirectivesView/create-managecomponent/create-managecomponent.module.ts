import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateManagecomponentComponent } from './create-managecomponent.component';
import { Loginservices } from '../../services/login.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),   
    NgxSpinnerModule
  ],
  declarations: [CreateManagecomponentComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateManagecomponentComponent],
  providers:[NgxSpinnerService,Loginservices]  
})
export class CreateManagecomponentModule { }
