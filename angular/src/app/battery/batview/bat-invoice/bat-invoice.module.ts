import { GlobalSearchPipeModule } from './../../../pipe/global-search-pipe.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { OrderModule } from 'ngx-order-pipe';
import { RouterModule } from '@angular/router';
import { NewInvoiceModule } from '../../../DirectivesView/new-invoice/new-invoice.module';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatInvoiceRouting } from './bat-invoice.routing';
import { BatInvoiceComponent } from './bat-invoice.component';
import { BtNewInvoiceModule } from '../../batDirectiveView/bt-new-invoice/bt-new-invoice.module';
@NgModule({
  imports: [
    CommonModule,
    BatInvoiceRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule, NewInvoiceModule, RouterModule,
    GlobalSearchPipeModule,BsDatepickerModule.forRoot(),
    BtNewInvoiceModule
  ],
  declarations: [BatInvoiceComponent],
//   exports: [SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [NgxSpinnerService, Loginservices,PagefilterService,DatePipe]
})
export class BatInvoiceModule {

}