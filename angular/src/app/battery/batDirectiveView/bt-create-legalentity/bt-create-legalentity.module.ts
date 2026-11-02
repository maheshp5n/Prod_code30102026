import { CommonModule } from "@angular/common";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { ligalEntityService } from "../../../services/ligalentity.services";
import { Loginservices } from "../../../services/login.services";
import { FieldValidation } from "../../../services/validation.services";
import { RouterModule } from '@angular/router';
import { BtCreateLegalentityComponent } from "./bt-create-legalentity.component";
import { CirclesService } from "../../../services/circles.services";
import { NgSelectModule } from "@ng-select/ng-select";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    NgxSpinnerModule,
    RouterModule,
    BsDropdownModule,
    NgSelectModule

  ],
  declarations: [BtCreateLegalentityComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateLegalentityComponent],
  providers: [NgxSpinnerService, ligalEntityService, Loginservices, FieldValidation,CirclesService],
})
export class BtCreateLegalEntityModule {

}    