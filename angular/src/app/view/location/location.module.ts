import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ligalEntityService } from "../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { OrderModule } from 'ngx-order-pipe';
import { locationRouting } from './location.routing';
import { createlocationModule } from '../../DirectivesView/create-location/create-location.module';
import { LocationComponent } from './location.component';
import { LocationService } from '../../services/location.service';
import { PagefilterService } from '../../services/pagefilterservice';
@NgModule({
    imports: [
      CommonModule,
      locationRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      createlocationModule,OrderModule
    ],
    declarations: [LocationComponent,SearchfiltPipe],
    exports: [SearchfiltPipe],
    providers:[ligalEntityService,NgxSpinnerService,Loginservices,LocationService,PagefilterService]
  })
export class LocationModule{

}