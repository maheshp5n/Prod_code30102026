import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { siteService } from '../../services/siteService.services';
import { Loginservices } from '../../services/login.services';
import { ModalModule } from 'ngx-bootstrap/modal';

import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { CreateNewCircleModule } from '../create-new-circle/create-new-circle.module';

import { NgSelectModule } from '@ng-select/ng-select';
import { CreateSSAComponent } from './create-ssa.component';
import { ssaService } from '../../services/ssa.services';
import { ZoneService } from '../../services/zone.service';
import { CirclesService } from '../../services/circles.services';


@NgModule({
  imports: [
    CommonModule,
    FormsModule, ModalModule, TypeaheadModule, CreateaccountModule, CreateNewCircleModule, CreateLegalEntityModule,
    RouterModule,NgSelectModule
  ],
  declarations: [CreateSSAComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateSSAComponent],
  providers: [NgxSpinnerService, siteService, Loginservices,ssaService,ZoneService,CirclesService]
})
export class createSSAModule{

}