import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductrequestdirectiveComponent } from './productrequestdirective.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { ModalModule,BsDatepickerModule } from 'ngx-bootstrap';



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
