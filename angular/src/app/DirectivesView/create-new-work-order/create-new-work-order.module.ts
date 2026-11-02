import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateNewWorkOrderComponent } from './create-new-work-order.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { WorkOrderService } from '../../services/workoders.services';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule
  ],
  declarations: [CreateNewWorkOrderComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateNewWorkOrderComponent],
  providers:[NgxSpinnerService,Loginservices,WorkOrderService,TypeaheadConfig]

})
export class CreateNewWorkOrderModule { }
