import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateRequisitionComponent } from './create-requisition.component';
import { Loginservices } from '../../services/login.services';
import { RequisitionService } from '../../services/requisition.services';
import { TypeaheadConfig, TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { AssetsService } from '../../services/assets.services';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { CreateNewWarehouseModule } from '../create-new-warehouse/create-new-warehouse.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    CreateNewProductModule,
    CreateNewWarehouseModule,
    TypeaheadModule,
    RouterModule

  ],
  declarations: [CreateRequisitionComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateRequisitionComponent],
  providers: [NgxSpinnerService, Loginservices, RequisitionService, AssetsService, TypeaheadConfig]
})
export class CreateRequisitionModule { }
