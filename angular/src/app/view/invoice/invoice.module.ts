import { GlobalSearchPipeModule } from './../../pipe/global-search-pipe.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { BsDatepickerModule, ModalModule } from "ngx-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { OrderModule } from 'ngx-order-pipe';
import { InvoiceRouting } from './invoice.routing';
import { InvoiceComponent } from './invoice.component';
import { SearchfiltPipe } from './searchfilt.pipe';
import { RouterModule } from '@angular/router';
import { NewInvoiceModule } from '../../DirectivesView/new-invoice/new-invoice.module';
import { PagefilterService } from '../../services/pagefilterservice';
@NgModule({
  imports: [
    CommonModule,
    InvoiceRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    OrderModule, NewInvoiceModule, RouterModule,
    GlobalSearchPipeModule,BsDatepickerModule.forRoot()
  ],
  declarations: [InvoiceComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [NgxSpinnerService, Loginservices,PagefilterService,DatePipe]
})
export class InvoiceModule {

}