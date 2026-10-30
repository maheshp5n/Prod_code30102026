import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { BatProductrequestComponent } from './bat-productrequest.component';
import { BatProuductRequestRouting } from './bat-productrequest.routing';

@NgModule({
  declarations: [BatProductrequestComponent],
  imports: [
    CommonModule,
    BatProuductRequestRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
  ],
  providers:[]
})
export class BatProductrequestModule { }
