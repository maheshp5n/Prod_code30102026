import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductrequestRoutingModules } from './productrequest.routing';
import { ProductrequestComponent } from './productrequest.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ProductrequestComponent],
  imports: [
    CommonModule,
    ProductrequestRoutingModules,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
  ]
})
export class ProductrequestModule { }
