import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IssuedcomponentsComponent } from './issuedcomponents.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { SearchfiltPipe } from '../issuedcomponents/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { IssuedcomponentsRouting } from './issuedcomponents.routing';
import { CreateIssuedcomponentModule } from '../../DirectivesView/create-issuedcomponent/create-issuedcomponent.module';

@NgModule({
  imports: [
    CommonModule,
    IssuedcomponentsRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateIssuedcomponentModule
  ],
  declarations: [ IssuedcomponentsComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class IssuedcomponentsModule { }
