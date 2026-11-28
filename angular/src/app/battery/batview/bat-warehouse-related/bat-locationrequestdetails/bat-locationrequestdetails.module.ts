import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BtwarehouseService } from '../../../bat_services/btwarehouse.service';
import { BatLocationrequestdetailsRouting } from './bat-locationrequestdetails.routing';
import { BatLocationrequestdetailsComponent } from './bat-locationrequestdetails.component';



@NgModule({
  declarations: [BatLocationrequestdetailsComponent],
  imports: [
    CommonModule,FormsModule,
    BatLocationrequestdetailsRouting,
    NgxSpinnerModule,BsDatepickerModule.forRoot()
  ],
  providers:[DatePipe,BtwarehouseService]
})
export class BatLocationrequestdetailsModule { }
