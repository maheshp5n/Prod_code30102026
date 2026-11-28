import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { FieldValidation } from "../../services/validation.services";
import { ZonecreateModule } from "../../DirectivesView/zonecreate/zonecreate.module";
import { EngineerRatingRout } from "./engineer-rating.routing";
import { EngineerRatingComponent } from "./engineer-rating.component";
import { ServiceResourceService } from "../../services/serviceresource.services";
@NgModule({
  imports: [
    CommonModule,
    EngineerRatingRout,
    ModalModule, FormsModule,
    BsDropdownModule,
    ModalModule,
    NgxSpinnerModule,
    ZonecreateModule
  ],
  declarations: [EngineerRatingComponent],
//   exports: [SearchfiltPipe],
  providers: [NgxSpinnerService, FieldValidation,ServiceResourceService]
})
export class EngineerRatingModule{

}