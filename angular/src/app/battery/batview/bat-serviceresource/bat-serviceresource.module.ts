import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatServiceresourceComponent } from './bat-serviceresource.component';
import { BatServiceresourceRouting } from './bat-serviceresource.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule, BsDropdownModule, ModalModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BtCreateserviceresourceModule } from '../../batDirectiveView/bt-createserviceresource/bt-createserviceresource.module';
import { BtserviceresourseService } from '../../bat_services/btserviceresourse.service';
import { SearchfiltPipe } from './searchfilt.pipe';



@NgModule({
  declarations: [BatServiceresourceComponent, SearchfiltPipe],
  imports: [
    CommonModule,
    BatServiceresourceRouting,
 
      FormsModule,
      BsDropdownModule,
      ModalModule.forRoot(),
      NgxSpinnerModule,
      ReactiveFormsModule,
      BsDatepickerModule.forRoot(),
      TimepickerModule.forRoot(),
      OrderModule,
      BtCreateserviceresourceModule,
    ],
    exports: [SearchfiltPipe],
    providers: [BtserviceresourseService, NgxSpinnerService]
})
export class BatServiceresourceModule { }
