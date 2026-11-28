import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from "@angular/forms";
import { ligalEntityService } from "../../../services/ligalentity.services";
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ContactcreateModule } from '../../../DirectivesView/contactcreate/contactcreate.module';
import { ContactsService } from '../../../services/contacts.services';
import { createlocationModule } from '../../../DirectivesView/create-location/create-location.module';
import { BatLegalEntityViewRouting } from "./bat-legalentitydetail.routing";
import { BatLegalentitydetailComponent } from "./bat-legalentitydetail.component";
import { BtCreateLegalEntityModule } from "../../batDirectiveView/bt-create-legalentity/bt-create-legalentity.module";
import { BtActionhistoryModule } from "../../batDirectiveView/bt-action-history/bt-action-history.module";
import { BtCreatewarehouseModule } from "../../batDirectiveView/bt-createwarehouse/bt-createwarehouse.module";


@NgModule({
    imports: [
      CommonModule,
      BatLegalEntityViewRouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      ContactcreateModule,
      createlocationModule,
      BtActionhistoryModule,
      BatLegalEntityViewRouting,
      BtCreateLegalEntityModule,
      BtCreatewarehouseModule
    ],
    declarations: [BatLegalentitydetailComponent],
    providers:[ligalEntityService,NgxSpinnerService,ContactsService]
  })
export class BatLegalEntityDetailModule{

}