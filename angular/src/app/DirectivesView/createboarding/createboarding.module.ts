import { RouterModule } from '@angular/router';

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateboardingComponent } from './createboarding.component';
import { BoardingLoadingService } from '../../services/boarding-loading.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule, TypeaheadModule,
    NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    RouterModule

  ],
  declarations: [CreateboardingComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateboardingComponent],
  providers: [NgxSpinnerService, BoardingLoadingService, Loginservices, TypeaheadConfig]
})
export class createboardingModule {

}