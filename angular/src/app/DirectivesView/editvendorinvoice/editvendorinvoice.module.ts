import { EditvendorinvoiceComponent } from './editvendorinvoice.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';


@NgModule({
  declarations: [EditvendorinvoiceComponent],
  imports: [
    CommonModule,
    FormsModule,
    BsDatepickerModule
  ],
  providers: [NgxSpinnerService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [EditvendorinvoiceComponent]
})
export class EditvendorinvoiceModule { }
