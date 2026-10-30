import { FormsModule } from '@angular/forms';
import { ServiceorderreceivedirecComponent } from './serviceorderreceivedirec.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [ServiceorderreceivedirecComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ServiceorderreceivedirecComponent]
})
export class ServiceorderreceivedirecModule { }
