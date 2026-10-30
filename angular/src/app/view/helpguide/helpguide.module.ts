import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { cityService } from "../../services/cityService.service";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { Loginservices } from '../../services/login.services';
import { PagefilterService } from '../../services/pagefilterservice';
import { HelpguideComponent } from "./helpguide.component";
import { Helpguiderotuing } from "./helpguide.routing";
import { EmbedVideo } from "ngx-embed-video";



@NgModule({
  imports: [
    CommonModule,
    Helpguiderotuing,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    OrderModule,
    EmbedVideo.forRoot()
  ],
  declarations: [HelpguideComponent],
  providers: [cityService, NgxSpinnerService, FieldValidation,Loginservices,PagefilterService]
})
export class helpguideModule{}