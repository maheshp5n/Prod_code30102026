import { RouterModule } from '@angular/router';


import { CommonModule } from "@angular/common";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { FieldValidation } from "../../services/validation.services";
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateLocationComponent } from './create-location.component';
import { LocationService } from '../../services/location.service';
import { siteService } from '../../services/siteService.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule, TypeaheadModule.forRoot(),
    ModalModule,
    NgxSpinnerModule,NgSelectModule,
    RouterModule,CreateLegalEntityModule
  ],
  declarations: [CreateLocationComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateLocationComponent],
  providers: [TypeaheadConfig, Loginservices, FieldValidation, NgxSpinnerService, LocationService, siteService],
})
export class createlocationModule { }