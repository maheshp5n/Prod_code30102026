import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagecomponentRouting } from './managecomponent.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { ManagecomponentComponent } from './managecomponent.component';
import { AssetsService } from '../../services/assets.services';
import { SearchfiltPipe } from '../managecomponent/searchfilt.pipe';
import { CreateManagecomponentModule } from '../../DirectivesView/create-managecomponent/create-managecomponent.module';

@NgModule({
  imports: [
    CommonModule,
    ManagecomponentRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateManagecomponentModule
  ],
  declarations: [ ManagecomponentComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class ManagecomponentModule { }
