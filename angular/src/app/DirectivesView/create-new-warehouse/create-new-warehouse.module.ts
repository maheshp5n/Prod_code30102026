import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { WarehouseService } from '../../services/warehouse.service';
import { CreateNewWarehouseComponent } from './create-new-warehouse.component';
import { ligalEntityService } from '../../services/ligalentity.services';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    CreateLegalEntityModule,
    TypeaheadModule.forRoot(),
    RouterModule
  ],
  declarations: [CreateNewWarehouseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewWarehouseComponent],
  providers: [WarehouseService, NgxSpinnerService, ligalEntityService]
})
export class CreateNewWarehouseModule { }
