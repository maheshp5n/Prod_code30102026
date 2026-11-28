import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ReceiveFromEngComponent } from './receive-from-eng.component';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),   
    NgxSpinnerModule,
    NgSelectModule
    
  ],
  declarations: [ReceiveFromEngComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[ReceiveFromEngComponent],
  providers:[NgxSpinnerService,ManageInventoryService,DatePipe]  
})
export class ReceiveFromEngModule { }
