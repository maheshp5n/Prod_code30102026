import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateLegalEntityModule } from '../../../DirectivesView/createlegalentity/createlegalentity.module';
import { CreateaccountModule } from '../../../DirectivesView/createaccount/createaccount.module';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { createboardingModule } from '../../../DirectivesView/createboarding/createboarding.module';
import { BtCreatecontactsComponent } from './bt-createcontacts.component';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { Loginservices } from '../../../services/login.services';
import { BoardingLoadingService } from '../../../services/boarding-loading.service';
import { WarehouseService } from '../../../services/warehouse.service';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { BtboardingLoadingExpenseService } from '../../bat_services/btboarding-loading-expense.service';
import { BtlegalentityService } from '../../bat_services/btlegalentity.service';
import { BtCreateLegalEntityModule } from '../bt-create-legalentity/bt-create-legalentity.module';
import { BtCreateBoardingModule } from '../bt-create-boarding/bt-create-boarding.module';
import { BTCreateaccountModule } from '../bt-createaccount/bt-createaccount.module';
import { BtCreateBoadingLodginDaComponent } from '../bt-create-boading-lodgin-da/bt-create-boading-lodgin-da.component';
import { BTBodingLodginDACreateModule } from '../bt-create-boading-lodgin-da/bt-create-boading-da-module';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreateLegalEntityModule,
    BTCreateaccountModule,
    RouterModule,
    NgSelectModule,
    BTBodingLodginDACreateModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [BtCreatecontactsComponent],
  exports: [BtCreatecontactsComponent],
  providers: [
    CountryDetailsServices,
    BtlegalentityService,
    BtcontactsService,
    Loginservices,
    NgxSpinnerService,
    BoardingLoadingService,
    BtwarehouseService
  ]
})
export class BtCreatecontactsModule { }
