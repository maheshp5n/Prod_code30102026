import { PopupSuccessModule } from './../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateServiceresourceComponent } from './create-serviceresource.component';
import { Loginservices } from '../../services/login.services';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';
import { ContactcreateModule } from '../contactcreate/contactcreate.module';
import { createlocationModule } from '../create-location/create-location.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    PopupSuccessModule,
    TypeaheadModule.forRoot(),
    RouterModule,
    NgSelectModule,
    ContactcreateModule,
    createlocationModule
  ],
  declarations: [CreateServiceresourceComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateServiceresourceComponent],
  providers: [NgxSpinnerService, Loginservices, ServiceResourceService]
})
export class CreateServiceresourceModule { }
