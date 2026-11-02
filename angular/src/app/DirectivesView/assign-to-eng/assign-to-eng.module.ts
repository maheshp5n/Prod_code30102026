import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AssignToEngComponent } from './assign-to-eng.component';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CreateManageinventoryModule } from '../create-manageinventory/create-manageinventory.module';
import { CreateServiceresourceModule } from '../create-serviceresource/create-serviceresource.module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    TypeaheadModule,
    CreateServiceresourceModule,
    CreateManageinventoryModule,
    NgSelectModule
  ],
  declarations: [AssignToEngComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [AssignToEngComponent],
  providers: [NgxSpinnerService, ManageInventoryService, TypeaheadConfig]
})

export class AssignToEngModule { }
