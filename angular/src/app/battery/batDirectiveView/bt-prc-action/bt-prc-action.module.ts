import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtPrcActionComponent } from './bt-prc-action.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,
    ],
    declarations: [BtPrcActionComponent],
    // schemas:[CUSTOM_ELEMENTS_SCHEMA],
    exports:[BtPrcActionComponent],
    providers:[BtPreRegistrationCaseService]
  })
export class BtPrcActionModule{

}