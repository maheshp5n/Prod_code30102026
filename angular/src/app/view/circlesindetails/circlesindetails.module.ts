import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CirclesindetailsComponent } from './circlesindetails.component';
import { CirclesindetailsRouting } from './circlesindetails.routing';
import { CirclesService } from '../../services/circles.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateNewCircleModule } from '../../DirectivesView/create-new-circle/create-new-circle.module';
import { Loginservices } from '../../services/login.services';
import { UserforlistService } from '../../services/userforlist.service';
import { ZoneService } from '../../services/zone.service';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    CirclesindetailsRouting,
    NgxSpinnerModule,
    CreateNewCircleModule,
    BsDropdownModule,
    ActionhistoryModule
  ],
  declarations: [CirclesindetailsComponent],
  providers: [CirclesService, NgxSpinnerService, Loginservices, UserforlistService, ZoneService, CountryDetailsServices]

})
export class CirclesindetailsModule { }
