import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FieldValidation } from '../../services/validation.services';
import { RepairDCTComponent } from './repair-dct.component';
import { SearchfiltPipe } from './searchfilt.pipe';
import { RepairDCTRouting } from './repair-dct.routing';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { Loginservices } from '../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    RepairDCTRouting, OrderModule, GlobalSearchPipeModule,
    BsDatepickerModule,ReactiveFormsModule
  ],
  declarations: [RepairDCTComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [FieldValidation, NgxSpinnerService,DatePipe, Loginservices, DCTRepairservice,PagefilterService]
})
export class RepairDCTModule {

}