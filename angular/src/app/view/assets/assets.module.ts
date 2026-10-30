import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { AssetsComponent } from './assets.component';
import { Assetsrouting } from './assets.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CreateaccountModule } from '../../DirectivesView/createaccount/createaccount.module';
import { CreateassetModule } from '../../DirectivesView/createasset/createasset.module';
import { AssetsService } from '../../services/assets.services';
import { SearchfiltPipe } from '../assets/searchfilt.pipe';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../services/pagefilterservice';

@NgModule({
  imports: [
    CommonModule,
    Assetsrouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    CreateassetModule,
    CreateaccountModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule

  ],
  declarations: [AssetsComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService,DatePipe,PagefilterService]
  
})
export class AssetsModule { }
