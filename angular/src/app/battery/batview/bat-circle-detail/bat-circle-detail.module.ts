import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BatCircleDetailRouting } from './bat-circle-detail.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreateCircleModule } from '../../batDirectiveView/bt-create-circle/bt-create-circle.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { BatCircleDetailComponent } from './bat-circle-detail.component';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { Loginservices } from '../../../services/login.services';
import { UserforlistService } from '../../../services/userforlist.service';
import { ZoneService } from '../../../services/zone.service';
import { CountryDetailsServices } from '../../../services/countrydetails.services';



@NgModule({
  declarations: [BatCircleDetailComponent],
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BatCircleDetailRouting,
    NgxSpinnerModule,
    BtCreateCircleModule,
    BsDropdownModule,
    ActionhistoryModule
  ],
  providers: [BtcircleService, NgxSpinnerService, Loginservices, UserforlistService, ZoneService, CountryDetailsServices]

})
export class BatCircleDetailModule { }
