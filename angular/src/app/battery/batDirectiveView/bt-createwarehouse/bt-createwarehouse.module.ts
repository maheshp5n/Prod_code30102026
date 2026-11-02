import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtCreatewarehouseComponent } from './bt-createwarehouse.component';
import { FormsModule } from '@angular/forms';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateLegalEntityModule } from '../../../DirectivesView/createlegalentity/createlegalentity.module';
import { RouterModule } from '@angular/router';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { Loginservices } from '../../../services/login.services';
import { siteService } from '../../../services/siteService.services';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtCreateLegalEntityModule } from '../bt-create-legalentity/bt-create-legalentity.module';



@NgModule({
  declarations: [BtCreatewarehouseComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    BtCreateLegalEntityModule,
    TypeaheadModule.forRoot(),
    RouterModule,NgSelectModule

  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[BtCreatewarehouseComponent],
  providers:[BtwarehouseService,NgxSpinnerService, BtlegalentityService, TypeaheadConfig, Loginservices,BtsiteserviceService]
})
export class BtCreatewarehouseModule { }
