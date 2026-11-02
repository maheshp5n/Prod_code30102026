import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductrequestdirectiveComponent } from './productrequestdirective.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';



@NgModule({
  declarations: [ProductrequestdirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    NgSelectModule,
    ModalModule,
    BsDatepickerModule

  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports:[ProductrequestdirectiveComponent],
  providers:[NgxSpinnerService]
})
export class ProductrequestdirectiveModule { }
