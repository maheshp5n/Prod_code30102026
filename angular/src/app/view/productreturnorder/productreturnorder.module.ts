import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductreturnOrderRouting } from './productreturnorder.routing';
import { AlertModule, ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { ProductreturnorderComponent } from './productreturnorder.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [ProductreturnorderComponent],
  imports: [
    CommonModule,
    ProductreturnOrderRouting,
    AlertModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    GlobalSearchPipeModule
  ],
  providers:[NgxSpinnerService]
})
export class ProductreturnorderModule { }
