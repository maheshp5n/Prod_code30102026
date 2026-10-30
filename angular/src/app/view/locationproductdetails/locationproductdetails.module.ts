import { LocationproductdetailsRouting } from './locationproductdetails.routing';
import { LocationproductdetailsComponent } from './locationproductdetails.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';



@NgModule({
  declarations: [LocationproductdetailsComponent],
  imports: [
    CommonModule,FormsModule,
    ModalModule,
    LocationproductdetailsRouting,
    NgxSpinnerModule
  ]
})
export class LocationproductdetailsModule { }
