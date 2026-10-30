import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobwarrantyRouting } from './jobwarranty.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateJobwarrantyModule } from '../../DirectivesView/create-jobwarranty/create-jobwarranty.module';
import { JobwarrantyComponent } from './jobwarranty.component';
import { AssetsService } from '../../services/assets.services';
import { SearchfiltPipe } from '../jobwarranty/searchfilt.pipe';

@NgModule({
  imports: [
    CommonModule,
    JobwarrantyRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateJobwarrantyModule
  ],
  declarations: [ JobwarrantyComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class JobwarrantyModule { }
