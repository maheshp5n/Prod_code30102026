import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductrequestreceiveComponent } from './productrequestreceive.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AccordionModule } from 'ngx-bootstrap';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [ProductrequestreceiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    AccordionModule,
    GlobalSearchPipeModule,
    NgxSpinnerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports:[ProductrequestreceiveComponent],
  providers:[NgxSpinnerService]
})
export class ProductrequestreceiveModule { }
