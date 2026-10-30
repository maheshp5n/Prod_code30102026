import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatRmaRouting } from './bat-rma.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BatRmaComponent } from './bat-rma.component';



@NgModule({
  declarations: [BatRmaComponent],
  imports: [
    CommonModule,
    BatRmaRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    ReactiveFormsModule
  ],
  providers:[NgxSpinnerService]
})
export class BatRmaModule { }
