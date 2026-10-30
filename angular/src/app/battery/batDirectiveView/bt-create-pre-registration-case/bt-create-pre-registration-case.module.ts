import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, ModalModule, TimepickerModule, TypeaheadModule } from 'ngx-bootstrap';
import { BtCreateLegalEntityModule } from '../bt-create-legalentity/bt-create-legalentity.module';
import { BtCreateAssetModule } from '../bt-createasset/bt-createasset.module';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';
import { BtCreateSitemodule } from '../bt-createsite/bt-createsite.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';
import { BtCreatePreRegistrationCaseComponent } from './bt-create-pre-registration-case.component';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { Loginservices } from '../../../services/login.services';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';



@NgModule({
  declarations: [BtCreatePreRegistrationCaseComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule, TypeaheadModule, BtCreateLegalEntityModule, BtCreateAssetModule, BtCreatecontactsModule,
    NgxSpinnerModule, BsDatepickerModule.forRoot(), BtCreateSitemodule,
    TimepickerModule.forRoot(),
    RouterModule,NgSelectModule

  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreatePreRegistrationCaseComponent],
  providers: [NgxSpinnerService, BtCaseserviceService,BtPreRegistrationCaseService, Loginservices, BtsiteserviceService, BtaccountService, TypeaheadConfig, BtlegalentityService, DatePipe, BtcontactsService]

})
export class BtCreatePreRegistrationCaseModule { }
