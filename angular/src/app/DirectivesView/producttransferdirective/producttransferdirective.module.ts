import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProducttransferdirectiveComponent } from './producttransferdirective.component';
import {
  AccordionModule,
  AlertModule,
  ModalModule,
  BsDatepickerModule,
  TooltipModule
} from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [ProducttransferdirectiveComponent],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NgSelectModule,
    AccordionModule,
    NgxSpinnerModule,
    AlertModule,
    ModalModule,
    BsDatepickerModule,
    TooltipModule,
    GlobalSearchPipeModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ProducttransferdirectiveComponent],
  providers: [NgxSpinnerService]
})
export class ProducttransferdirectiveModule { }
