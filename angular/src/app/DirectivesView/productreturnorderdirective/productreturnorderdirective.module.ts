import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductreturnorderdirectiveComponent } from './productreturnorderdirective.component';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule, AlertModule, BsDatepickerModule, AccordionModule, TooltipModule } from 'ngx-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [ProductreturnorderdirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    NgSelectModule,
    BsDatepickerModule,
    AccordionModule,
    TooltipModule,
    GlobalSearchPipeModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[ProductreturnorderdirectiveComponent],
  providers:[NgxSpinnerService]
})
export class ProductreturnorderdirectiveModule { }
