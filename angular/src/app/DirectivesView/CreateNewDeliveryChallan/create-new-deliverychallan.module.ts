import { PopupSuccessModule } from './../popupSuccess/popupSuccess.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateNewdeliverychallanComponent } from './create-new-deliverychallan.component';
import { ligalEntityService } from '../../services/ligalentity.services';
import { deliverychallanService } from '../../services/delivery-challan.service';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule.forRoot(),
    BsDatepickerModule.forRoot(),
    PopupSuccessModule,
    TimepickerModule.forRoot(),
    RouterModule
  ],
  declarations: [CreateNewdeliverychallanComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewdeliverychallanComponent],
  providers: [deliverychallanService, NgxSpinnerService, ligalEntityService]
})
export class CreateNewdeliverychallanModule { }
