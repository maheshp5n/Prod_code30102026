import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateNewCircleModule } from '../../../DirectivesView/create-new-circle/create-new-circle.module';
import { BtCreatecontactsModule } from '../../batDirectiveView/bt-createcontacts/bt-createcontacts.module';
import { BatCircleRouting } from './bat-circle.routing';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { BatCircleComponent } from './bat-circle.component';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { CountryDetailsServices } from '../../../services/countrydetails.services';
import { ZoneService } from '../../../services/zone.service';
import { UserforlistService } from '../../../services/userforlist.service';
import { Loginservices } from '../../../services/login.services';
import { SearchfiltPipe } from './searchfilt.pipe';
import { BtCreateCircleModule } from '../../batDirectiveView/bt-create-circle/bt-create-circle.module';



@NgModule({
  declarations: [BatCircleComponent, SearchfiltPipe],
  imports: [
    CommonModule,
    FormsModule,
    BtCreateCircleModule,
    BatCircleRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,OrderModule
  ],
  // declarations: [CirclesComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [PagefilterService,BtcircleService, CountryDetailsServices, ZoneService, UserforlistService, Loginservices, NgxSpinnerService]
})
export class BatCircleModule { }
