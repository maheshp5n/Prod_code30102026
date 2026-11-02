import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateSiteComponent } from './create-site.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { siteService } from '../../services/siteService.services';
import { Loginservices } from '../../services/login.services';
import { ModalModule } from 'ngx-bootstrap/modal';

import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { CreateNewCircleModule } from '../create-new-circle/create-new-circle.module';
import { ZoneService } from '../../services/zone.service';
import { UserforlistService } from '../../services/userforlist.service';
import { CirclesService } from '../../services/circles.services';
import { AccountService } from '../../services/accounts.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { createSSAModule } from '../create-ssa/create-ssa-module';
import { CreateCustomerModule } from '../create-customer/create-customer.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule, ModalModule, TypeaheadModule, CreateaccountModule, CreateNewCircleModule, CreateLegalEntityModule,
    RouterModule,NgSelectModule,createSSAModule, CreateCustomerModule

  ],
  declarations: [CreateSiteComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateSiteComponent],
  providers: [NgxSpinnerService, siteService, Loginservices, TypeaheadConfig, ZoneService, UserforlistService, CirclesService, AccountService]
})
export class CreateSiteModule { }
