import { RouterModule } from '@angular/router';

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { Loginservices } from '../../../services/login.services';
import { BAT_BoardingLoadingService } from '../../bat_services/bt-boadigLoadgingDA.service';
import { BtCreateBoadingLodginDaComponent } from './bt-create-boading-lodgin-da.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    RouterModule

  ],
  declarations: [BtCreateBoadingLodginDaComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateBoadingLodginDaComponent],
  providers: [NgxSpinnerService, BAT_BoardingLoadingService, Loginservices]
})
export class BTBodingLodginDACreateModule{

}