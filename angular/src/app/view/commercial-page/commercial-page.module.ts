import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ModalModule, BsDropdownModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { Loginservices } from '../../services/login.services';
import { CommercialPageRouting } from './commercial-page.routing';
import { CommercialPageComponent } from './commercial-page.component';
import { caseService } from '../../services/caseService.service';
import { SearchfiltPipe } from './searchfilt.pipe';
import { OrderModule } from 'ngx-order-pipe';
import { CommercialService } from '../../services/commercial.services';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,ReactiveFormsModule,
    CommercialPageRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    ContactcreateModule,
    PopupSuccessModule,OrderModule,BsDatepickerModule.forRoot()
  ],
  declarations: [CommercialPageComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [Loginservices, NgxSpinnerService,DatePipe,caseService,CommercialService,PagefilterService]
})
export class CommercialPageModule{

}