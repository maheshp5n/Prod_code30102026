import { FormsModule } from '@angular/forms';
import { ServiceorderdirectiveComponent } from './serviceorderdirective.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule, TooltipModule } from 'ngx-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [ServiceorderdirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,TooltipModule,NgSelectModule,ModalModule,NgxSpinnerModule
  ],
  
  exports: [ServiceorderdirectiveComponent],
  providers:[NgxSpinnerService]
})
export class ServiceorderdirectiveModule { }
