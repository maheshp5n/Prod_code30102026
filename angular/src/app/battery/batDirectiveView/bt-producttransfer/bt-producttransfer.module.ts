import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import {
  AccordionModule,
  AlertModule,
  ModalModule,
  BsDatepickerModule,
  TooltipModule
} from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../../pipe/global-search-pipe.module';
import { BtProducttransferComponent } from './bt-producttransfer.component';



@NgModule({
  declarations: [BtProducttransferComponent],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NgSelectModule,
    AccordionModule,
    NgxSpinnerModule,
    AlertModule,
    ModalModule,
    BsDatepickerModule,
    TooltipModule,
    GlobalSearchPipeModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtProducttransferComponent],
  providers: [NgxSpinnerService]
})
export class BtProducttransferModule { }
