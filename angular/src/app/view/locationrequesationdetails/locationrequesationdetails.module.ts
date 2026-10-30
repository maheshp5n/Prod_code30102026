import { LocationrequesationdetailsRouting } from './locationrequesationdetails.routing';
import { LocationrequesationdetailsComponent } from './locationrequesationdetails.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { LocationService } from '../../services/location.service';



@NgModule({
  declarations: [LocationrequesationdetailsComponent],
  imports: [
    CommonModule,FormsModule,
    LocationrequesationdetailsRouting,
    NgxSpinnerModule,BsDatepickerModule.forRoot()
  ],
  providers:[DatePipe,LocationService]
})
export class LocationrequesationdetailsModule { }
