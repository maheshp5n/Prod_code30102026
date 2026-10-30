import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BulkUtilsDetailsRouting } from './bulkutilsdetails.routing';
import { BulkutilsdetailsComponent } from './bulkutilsdetails.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BulkutilsdetailsComponent],
  imports: [
    CommonModule,
    BulkUtilsDetailsRouting,
    NgxSpinnerModule
  ],
  providers:[NgxSpinnerService]
})
export class BulkutilsdetailsModule { }
