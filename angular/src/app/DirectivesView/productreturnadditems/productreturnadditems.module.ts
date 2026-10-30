import { ModalModule } from 'ngx-bootstrap/modal';
import { LocationService } from './../../services/location.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ProductreturnadditemsComponent } from './productreturnadditems.component';
import { FormsModule } from '@angular/forms';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [ProductreturnadditemsComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ProductreturnadditemsComponent],
  providers: [LocationService, NgxSpinnerService]
})
export class ProductreturnadditemsModule { }
