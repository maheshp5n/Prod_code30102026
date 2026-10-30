import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule } from 'ngx-spinner';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { PopupSuccessModule } from '../popupSuccess/popupSuccess.module';
import { PrcactionComponent } from './prcaction.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,
      PopupSuccessModule,
    ],
    declarations: [PrcactionComponent],
    // schemas:[CUSTOM_ELEMENTS_SCHEMA],
    exports:[PrcactionComponent],
    providers:[PreRegCaseService]
  })
export class prcActionModule{

}