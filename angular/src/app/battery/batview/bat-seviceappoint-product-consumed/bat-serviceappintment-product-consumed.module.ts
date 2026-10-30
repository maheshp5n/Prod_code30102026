import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { AssetsService } from '../../../services/assets.services';
import { NgSelectModule } from '@ng-select/ng-select';
import { CreateNewProductModule } from '../../../DirectivesView/create-new-product/create-new-product.module';
import { BatSeviceappointProductConsumedComponent } from './bat-seviceappoint-product-consumed.component';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule,
    CreateNewProductModule,
    NgSelectModule
  ],
  declarations: [BatSeviceappointProductConsumedComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatSeviceappointProductConsumedComponent],
  providers: [NgxSpinnerService,AssetsService,TypeaheadConfig]
})
export class BatServiceappointmentProductconsumedModule { }
