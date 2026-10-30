import { PopupSuccessModule } from './../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnexureRouting } from './annexure.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateAnnexureModule } from '../../DirectivesView/create-annexure/create-annexure.module';
import { AnnexureComponent } from './annexure.component';
import { SearchfiltPipe } from '../annexure/searchfilt.pipe';
import { AnnexureService } from '../../services/annexure.services';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    AnnexureRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateAnnexureModule,
    PopupSuccessModule
  ],
  declarations: [AnnexureComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [AnnexureService, NgxSpinnerService,PagefilterService]
})
export class AnnexureModule { }
