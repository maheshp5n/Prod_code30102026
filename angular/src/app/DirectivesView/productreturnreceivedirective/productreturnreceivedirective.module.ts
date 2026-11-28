import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductreturnreceivedirectiveComponent } from './productreturnreceivedirective.component';
import { FormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';



@NgModule({
  declarations: [ProductreturnreceivedirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    AlertModule,
    ModalModule,
    AccordionModule,
    NgxSpinnerModule,
    GlobalSearchPipeModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers:[NgxSpinnerService],
  exports:[ProductreturnreceivedirectiveComponent]
})
export class ProductreturnreceivedirectiveModule { }
