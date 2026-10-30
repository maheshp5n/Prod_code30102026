import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { cityRouting } from "./city.routing";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { CityComponent } from "./city.component";
import { cityService } from "../../services/cityService.service";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { CreatecityModule } from "../../DirectivesView/createcity/createcity.module";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';



@NgModule({
  imports: [
    CommonModule,
    cityRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreatecityModule,
    OrderModule
  ],
  declarations: [CityComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [cityService, NgxSpinnerService, FieldValidation,Loginservices,PagefilterService]
})
export class cityModule {

}