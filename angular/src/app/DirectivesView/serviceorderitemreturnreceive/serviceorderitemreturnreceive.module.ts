import { RouterModule } from '@angular/router';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { ServiceorderitemreturnreceiveComponent } from './serviceorderitemreturnreceive.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [ServiceorderitemreturnreceiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    RouterModule,
    NgxSpinnerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ServiceorderitemreturnreceiveComponent]
})
export class ServiceorderitemreturnreceiveModule { }
