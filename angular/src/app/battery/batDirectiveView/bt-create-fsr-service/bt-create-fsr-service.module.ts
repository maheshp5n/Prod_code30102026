import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BtCreateFsrServiceComponent } from './bt-create-fsr-service.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { BsDatepickerModule, BsDropdownModule, DatepickerModule, ModalModule, TypeaheadModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RouterModule } from '@angular/router';





@NgModule({
  declarations: [BtCreateFsrServiceComponent],
  imports: [
    CommonModule,
    NgSelectModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    TypeaheadModule.forRoot(),
    // PopupSuccessModule,
    RouterModule,
    BsDropdownModule,
    DatepickerModule.forRoot(),
    BsDatepickerModule.forRoot(),

  ], providers: [DatePipe, NgxSpinnerService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  exports: [BtCreateFsrServiceComponent],

})
export class BtCreateFsrServiceModule { }
