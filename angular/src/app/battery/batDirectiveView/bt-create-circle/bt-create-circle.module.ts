import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateCircleComponent } from './bt-create-circle.component';
import { BtcircleService } from '../../bat_services/btcircle.service';



@NgModule({
  declarations: [BtCreateCircleComponent],
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
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateCircleComponent],
  providers: [BtcircleService, NgxSpinnerService]
})
export class BtCreateCircleModule { }
