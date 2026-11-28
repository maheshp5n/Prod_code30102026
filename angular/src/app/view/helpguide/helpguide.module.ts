import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { cityService } from "../../services/cityService.service";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';
import { HelpguideComponent } from "./helpguide.component";
import { Helpguiderotuing } from "./helpguide.routing";



@NgModule({
  imports: [
    CommonModule,
    Helpguiderotuing,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule,
  ],
  declarations: [HelpguideComponent],
  providers: [cityService, NgxSpinnerService, FieldValidation,Loginservices,PagefilterService]
})
export class helpguideModule{}