import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { DispatchCustomerComponent } from './dispatch-customer.component';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    RouterModule
  ],
  declarations: [DispatchCustomerComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DispatchCustomerComponent],
  providers: [NgxSpinnerService, ManageInventoryService,DatePipe]
})
export class DispatchCustomerModule { }
