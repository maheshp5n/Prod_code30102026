import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ProductTransferRouting } from './producttransfer.routing';
import { ProducttransferComponent } from './producttransfer.component';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [ProducttransferComponent],
  imports: [
    CommonModule,
    ProductTransferRouting,
    AlertModule,
    FormsModule,
    NgxSpinnerModule,
    BsDropdownModule,GlobalSearchPipeModule,BsDatepickerModule.forRoot()
  ],
  providers:[NgxSpinnerService,DatePipe]
})
export class ProducttransferModule { }
