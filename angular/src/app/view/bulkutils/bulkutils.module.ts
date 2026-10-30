import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BulkutilsRouting } from './bulkutils.router';
import { BulkutilsComponent } from './bulkutils.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BulkutilsComponent],
  imports: [
    CommonModule,
    BulkutilsRouting,
    NgxSpinnerModule
  ],
  providers:[NgxSpinnerService]
})
export class BulkutilsModule { }
