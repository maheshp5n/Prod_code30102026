import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap';
import { CreateaccountModule } from "../../DirectivesView/createaccount/createaccount.module";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { ZoneComponent } from "./zone.component";
import { ZoneRouting } from "./zone.routing";
import { ZoneService } from "../../services/zone.service";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { FieldValidation } from "../../services/validation.services";
import { ZonecreateModule } from "../../DirectivesView/zonecreate/zonecreate.module";
@NgModule({
  imports: [
    CommonModule,
    ZoneRouting,
    ModalModule, FormsModule,
    BsDropdownModule,
    CreateaccountModule,
    ModalModule,
    NgxSpinnerModule,
    ZonecreateModule
  ],
  declarations: [ZoneComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [ZoneService, NgxSpinnerService, FieldValidation]
})
export class ZoneModule { }