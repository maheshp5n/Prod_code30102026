import { ItemapprovalComponent } from './itemapproval.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule, AlertModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [ItemapprovalComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ItemapprovalComponent]
})
export class ItemapprovalModule { }
