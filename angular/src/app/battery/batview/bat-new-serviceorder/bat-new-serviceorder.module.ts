import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatNewServiceorderComponent } from './bat-new-serviceorder.component';
import { BatNewserviceOrderRouting } from './bat-new-serviceorder.routing';
import { FormsModule } from '@angular/forms';
import { AccordionModule } from 'ngx-bootstrap';

@NgModule({
  declarations: [BatNewServiceorderComponent],
  imports: [
    CommonModule,
    BatNewserviceOrderRouting,
    NgxSpinnerModule,
    FormsModule,
    AccordionModule
  ],
  exports: [BatNewServiceorderComponent],
  providers:[NgxSpinnerService]
})
export class BatNewserviceOrderModule{

}