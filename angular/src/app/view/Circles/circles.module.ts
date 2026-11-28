import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CirclesComponent } from './circles.component';
import { CirclesRouting } from './circles.routing';
import { CirclesService } from '../../services/circles.services';
import { CountryDetailsServices } from '../../services/countrydetails.services';
import { ZoneService } from '../../services/zone.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { UserforlistService } from '../../services/userforlist.service';
import { Loginservices } from '../../services/login.services';
import { SearchfiltPipe } from '../Circles/searchfilt.pipe';
import { CreateNewCircleModule } from '../../DirectivesView/create-new-circle/create-new-circle.module';
import { PagefilterService } from '../../services/pagefilterservice';
import { OrderModule } from 'ngx-order-pipe';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ContactcreateModule,
    CreateNewCircleModule,
    CirclesRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,OrderModule
  ],
  declarations: [CirclesComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [PagefilterService,CirclesService, CountryDetailsServices, ZoneService, UserforlistService, Loginservices, NgxSpinnerService]
})
export class CirclesModule { }
