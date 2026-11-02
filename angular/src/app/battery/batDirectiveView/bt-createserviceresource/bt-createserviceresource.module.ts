import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtCreateserviceresourceComponent } from './bt-createserviceresource.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { createlocationModule } from '../../../DirectivesView/create-location/create-location.module';
import { Loginservices } from '../../../services/login.services';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';
import { BtCreatewarehouseModule } from '../bt-createwarehouse/bt-createwarehouse.module';



@NgModule({
  declarations: [BtCreateserviceresourceComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    TypeaheadModule.forRoot(),
    RouterModule,
    NgSelectModule,
    BtCreatecontactsModule,
    BtCreatewarehouseModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateserviceresourceComponent],
  providers: [NgxSpinnerService, Loginservices, BtserviceresourseService]
})
export class BtCreateserviceresourceModule { }
