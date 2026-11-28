import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { RejectcaseComponent } from './rejectcase.component';
import { caseService } from '../../services/caseService.service';
@NgModule({
  declarations: [RejectcaseComponent],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ModalModule,
    NgSelectModule,
    AccordionModule,
    NgxSpinnerModule,
    AlertModule,
    ModalModule,ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [RejectcaseComponent],
  providers: [NgxSpinnerService,caseService]
})
export class RejectCaseCOmModule{

}