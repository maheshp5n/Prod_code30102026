import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateNewCaseComponent } from './create-new-case.component';
import { ModalModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule
  ],
  declarations: [CreateNewCaseComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewCaseComponent],

})
export class CreateNewCaseModule { }
