import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { CreatecaseComponent } from './createcase.component';
import { caseService } from '../../services/caseService.service';
import { AccountService } from '../../services/accounts.services';
import { siteService } from '../../services/siteService.services';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ligalEntityService } from '../../services/ligalentity.services';
import { CreateSiteModule } from '../create-site/create-site.module';
import { CreateassetModule } from '../createasset/createasset.module';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { ContactcreateModule } from '../contactcreate/contactcreate.module';
import { ContactsService } from '../../services/contacts.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { PreRegCaseService } from '../../services/preregcaseservice.services';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,ReactiveFormsModule,
    ModalModule, TypeaheadModule, CreateLegalEntityModule, CreateassetModule, ContactcreateModule,
    NgxSpinnerModule, BsDatepickerModule.forRoot(), CreateSiteModule,
    TimepickerModule.forRoot(),
    RouterModule,NgSelectModule

  ],
  declarations: [CreatecaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreatecaseComponent],
  providers: [NgxSpinnerService, caseService,PreRegCaseService, Loginservices, siteService, AccountService, TypeaheadConfig, ligalEntityService, DatePipe, ContactsService]
})
export class CreatecaseModule {

}