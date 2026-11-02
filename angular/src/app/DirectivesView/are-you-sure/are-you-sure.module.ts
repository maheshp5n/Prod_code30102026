import { NgSelectModule } from '@ng-select/ng-select';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { Loginservices } from '../../services/login.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { RouterModule } from '@angular/router';
import { AreYouSureComponent } from './are-you-sure.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    RouterModule,
    NgSelectModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [AreYouSureComponent],
  exports: [AreYouSureComponent],
  providers: [
    Loginservices,
    NgxSpinnerService,
  ]
})
export class AreYouSureModule{

}