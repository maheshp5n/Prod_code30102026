import { NgSelectModule } from '@ng-select/ng-select';
import { WarehouseService } from './../../services/warehouse.service';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactcreateComponent } from './contactcreate.component';
import { FormsModule } from '@angular/forms';
import { ContactsService } from '../../services/contacts.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { CreateaccountModule } from '../createaccount/createaccount.module';
import { createboardingModule } from '../createboarding/createboarding.module';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    CreateLegalEntityModule,
    CreateaccountModule,
    RouterModule,
    NgSelectModule,
    createboardingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ContactcreateComponent],
  exports: [ContactcreateComponent],
  providers: [
    CountryDetailsServices,
    ligalEntityService,
    ContactsService,
    Loginservices,
    NgxSpinnerService,
    BoardingLoadingService,
    WarehouseService, BoardingLoadingService
  ]
})
export class ContactcreateModule { }
