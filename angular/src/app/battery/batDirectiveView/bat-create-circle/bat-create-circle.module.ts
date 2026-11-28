import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';
import { BatCreateCircleComponent } from './bat-create-circle.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule.forRoot(),
    PopupSuccessModule,
    RouterModule,
    NgSelectModule
  ],
  declarations: [BatCreateCircleComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatCreateCircleComponent],
  providers: [NgxSpinnerService]
})
export class BatCreateCircleModule { }
