import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { CreatecityComponent } from "./createcity.component";
import { Loginservices } from "../../services/login.services";
import { cityService } from "../../services/cityService.service";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule

  ],
  declarations: [CreatecityComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreatecityComponent],
  providers: [NgxSpinnerService, cityService, Loginservices]
})
export class CreatecityModule {

}