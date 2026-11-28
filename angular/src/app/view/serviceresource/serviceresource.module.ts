import { PopupSuccessModule } from './../../DirectivesView/popupSuccess/popupSuccess.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceresourceRouting } from './serviceresource.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateServiceresourceModule } from '../../DirectivesView/create-serviceresource/create-serviceresource.module';
import { ServiceresourceComponent } from './serviceresource.component';
import { SearchfiltPipe } from '../serviceresource/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { ServiceResourceService } from '../../services/serviceresource.services';

@NgModule({
  imports: [
    CommonModule,
    ServiceresourceRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateServiceresourceModule,
    PopupSuccessModule
  ],
  declarations: [ServiceresourceComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ServiceResourceService, NgxSpinnerService]
})
export class ServiceresourceModule { }
