import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { siteService } from '../../../services/siteService.services';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { BtCreatesiteComponent } from './bt-createsite.component';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTCreateaccountModule } from '../bt-createaccount/bt-createaccount.module';
import { BtCreateLegalEntityModule } from '../bt-create-legalentity/bt-create-legalentity.module';
import { CreateNewCircleModule } from '../../../DirectivesView/create-new-circle/create-new-circle.module';
import { createSSAModule } from '../../../DirectivesView/create-ssa/create-ssa-module';
import { CreateCustomerModule } from '../../../DirectivesView/create-customer/create-customer.module';
import { BatCreateCircleModule } from '../bat-create-circle/bat-create-circle.module';
import { BtCreateCircleModule } from '../bt-create-circle/bt-create-circle.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,NgxSpinnerModule,
    RouterModule,NgSelectModule,TypeaheadModule,
    BTCreateaccountModule,
    BtCreateLegalEntityModule,
    CreateNewCircleModule,
    createSSAModule,
    CreateCustomerModule,
    BtCreateCircleModule

  ],
  declarations: [BtCreatesiteComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreatesiteComponent],
  providers: [Loginservices,NgxSpinnerService,OrderPipe,BtsiteserviceService]
})
export class BtCreateSitemodule{
    
}