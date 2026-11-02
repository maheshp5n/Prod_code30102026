import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from "@angular/forms";
import { LigalEntityViewRouting } from "./legalentityview.routing";
import { LegalentityviewComponent } from "./legalentityview.component";
import { ligalEntityService } from "../../services/ligalentity.services";
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { CreateLegalEntityModule } from "../../DirectivesView/createlegalentity/createlegalentity.module";
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ContactsService } from '../../services/contacts.services';
import { createlocationModule } from '../../DirectivesView/create-location/create-location.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';


@NgModule({
    imports: [
      CommonModule,
      LigalEntityViewRouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      CreateLegalEntityModule,
      ContactcreateModule,
      createlocationModule,
      ActionhistoryModule
    ],
    declarations: [LegalentityviewComponent],
    providers:[ligalEntityService,NgxSpinnerService,ContactsService]
  })
export class LigalEntityViewModule{

}