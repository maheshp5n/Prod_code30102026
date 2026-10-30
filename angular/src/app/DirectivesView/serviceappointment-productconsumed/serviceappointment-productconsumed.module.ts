import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ServiceappointmentProductconsumedComponent } from './serviceappointment-productconsumed.component';
import { TypeaheadConfig } from 'ngx-bootstrap/typeahead';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { AssetsService } from '../../services/assets.services';
import { NgSelectModule } from '@ng-select/ng-select';



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
  declarations: [ServiceappointmentProductconsumedComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ServiceappointmentProductconsumedComponent],
  providers: [NgxSpinnerService,AssetsService,TypeaheadConfig]
})
export class ServiceappointmentProductconsumedModule { }
