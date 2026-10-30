import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ModalModule } from 'ngx-bootstrap';

import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { CreateNewCircleModule } from '../create-new-circle/create-new-circle.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateRpairDCTComponent } from './create-rpair-dct.component';
import { caseService } from '../../services/caseService.service';
import { AssetsService } from '../../services/assets.services';
import { BillOfmaterialService } from '../../services/billOfMaterial.service';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { ServiceOrderJobCardService } from '../../services/serviceorderjobcardservice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule, ModalModule, TypeaheadModule, CreateaccountModule, CreateNewCircleModule, CreateLegalEntityModule,
    RouterModule,NgSelectModule
  ],
  declarations: [CreateRpairDCTComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateRpairDCTComponent],
  providers: [ServiceOrderJobCardService,NgxSpinnerService, Loginservices,caseService,AssetsService,BillOfmaterialService,DCTRepairservice]
})
export class createRpairModule{

}