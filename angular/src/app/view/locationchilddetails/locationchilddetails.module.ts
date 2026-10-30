import { LocationchilddetailsComponent } from './locationchilddetails.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { LocationchilddetailsRouting } from './locationchilddetails.routing';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';



@NgModule({
  declarations: [LocationchilddetailsComponent],
  imports: [
    CommonModule,FormsModule,ModalModule,
    LocationchilddetailsRouting,
    NgxSpinnerModule
  ]
})
export class LocationchilddetailsModule { }
