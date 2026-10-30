import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, TypeaheadModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateNewCircleComponent } from './create-new-circle.component';
import { CirclesService } from '../../services/circles.services';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    TypeaheadModule.forRoot(),
    PopupSuccessModule,
    RouterModule,
    NgSelectModule
  ],
  declarations: [CreateNewCircleComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateNewCircleComponent],
  providers: [CirclesService, NgxSpinnerService]
})
export class CreateNewCircleModule { }
