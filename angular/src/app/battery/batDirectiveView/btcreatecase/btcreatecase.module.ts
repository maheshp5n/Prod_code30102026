import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgSelectModule } from '@ng-select/ng-select';
import { BTCreatecaseComponent } from './btcreatecase.component';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { Loginservices } from '../../../services/login.services';
import { AccountService } from '../../../services/accounts.services';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BtCreateSitemodule } from '../bt-createsite/bt-createsite.module';
import { BtCreateAssetModule } from '../bt-createasset/bt-createasset.module';
import { BtCreatecontactsModule } from '../bt-createcontacts/bt-createcontacts.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule, TypeaheadModule,
    NgxSpinnerModule, BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),ReactiveFormsModule,
    RouterModule,NgSelectModule,BtCreateSitemodule,BtCreateAssetModule,BtCreatecontactsModule

  ],
  declarations: [BTCreatecaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BTCreatecaseComponent],
  providers: [NgxSpinnerService, BtCaseserviceService, Loginservices, DatePipe,
    BtsiteserviceService,AccountService]
})
export class BTCreateCaseModule{

}