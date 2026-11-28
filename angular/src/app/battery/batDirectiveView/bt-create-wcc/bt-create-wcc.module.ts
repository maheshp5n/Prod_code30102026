import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { Loginservices } from '../../../services/login.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateWccComponent } from './bt-create-wcc.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,NgSelectModule,
      RouterModule,NgxSpinnerModule, BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),
  
    ],
    declarations: [BtCreateWccComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [BtCreateWccComponent],
    providers: [NgxSpinnerService, Loginservices,DatePipe]
  })
  
export class BatcreateWCCModule{

}