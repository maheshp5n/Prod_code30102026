import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ligalEntityService } from "../../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { OrderModule } from 'ngx-order-pipe';
import { PagefilterService } from "../../../services/pagefilterservice";
import { BatLegalEntityRouting } from "./bat-legalentity.routing";
import { BatLegalentityComponent } from "./bat-legalentity.component";
import { BtCreateLegalEntityModule } from "../../batDirectiveView/bt-create-legalentity/bt-create-legalentity.module";
@NgModule({
    imports: [
      CommonModule,
      BatLegalEntityRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      BtCreateLegalEntityModule,
      OrderModule
    ],
    declarations: [BatLegalentityComponent],
    providers:[ligalEntityService,NgxSpinnerService,Loginservices,PagefilterService]
  })


export class BatLegalEntityModule{

}