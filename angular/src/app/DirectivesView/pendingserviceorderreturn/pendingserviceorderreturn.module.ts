import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule, TooltipModule } from 'ngx-bootstrap';
import { PendingserviceorderreturnComponent } from './pendingserviceorderreturn.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [PendingserviceorderreturnComponent],
  imports: [
    CommonModule,
    ModalModule,
    NgxSpinnerModule,
    FormsModule,
    RouterModule,TooltipModule
  ],
  exports: [PendingserviceorderreturnComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PendingserviceorderreturnModule { }
