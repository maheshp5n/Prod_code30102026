import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LigalEntityRouting } from "./ligalentity.routing";
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { LigalentityComponent } from "./ligalentity.component";
import { ligalEntityService } from "../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { SearchfiltPipe } from "./searchfilt.pipe";
// import { CreatelegalentityComponent } from "../../DirectivesView/createlegalentity/createlegalentity.component";
import { CreateLegalEntityModule } from "../../DirectivesView/createlegalentity/createlegalentity.module";
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from '../../services/pagefilterservice';
@NgModule({
    imports: [
      CommonModule,
      LigalEntityRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      CreateLegalEntityModule,OrderModule
    ],
    declarations: [LigalentityComponent,SearchfiltPipe],
    exports: [SearchfiltPipe],
    providers:[ligalEntityService,NgxSpinnerService,Loginservices,PagefilterService]
  })


export class LigalEntity{

}