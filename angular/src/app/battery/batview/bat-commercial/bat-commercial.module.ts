import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../../../DirectivesView/popupSuccess/popupSuccess.module';
import { Loginservices } from '../../../services/login.services';
import { caseService } from '../../../services/caseService.service';
import { OrderModule } from 'ngx-order-pipe';
import { CommercialService } from '../../../services/commercial.services';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatCommercialPageRouting } from './bat-commercial.routing';
import { BatCommercialComponent } from './bat-commercial.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,ReactiveFormsModule,
    BatCommercialPageRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    ContactcreateModule,
    PopupSuccessModule,OrderModule,BsDatepickerModule.forRoot()
  ],
  declarations: [BatCommercialComponent],
  providers: [Loginservices, NgxSpinnerService,DatePipe,caseService,CommercialService,PagefilterService]
})
export class BatCommercialPageModule{

}