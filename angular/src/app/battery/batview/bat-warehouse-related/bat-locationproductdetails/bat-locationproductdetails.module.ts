import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatLocationproductdetailsRouting } from './bat-locationproductdetails.routing';
import { BatLocationproductdetailsComponent } from './bat-locationproductdetails.component';



@NgModule({
  declarations: [BatLocationproductdetailsComponent],
  imports: [
    CommonModule,FormsModule,
    ModalModule,
    BatLocationproductdetailsRouting,
    NgxSpinnerModule
  ],
  providers:[NgxSpinnerService]
})
export class BatLocationproductdetailsModule { }
