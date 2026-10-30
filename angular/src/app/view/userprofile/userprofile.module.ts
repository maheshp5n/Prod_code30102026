
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule, BsDropdownModule, AlertModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { ligalEntityService } from "../../services/ligalentity.services";
import { UserProfileRouting } from "./userprofile.routing";
import { UserprofileComponent } from "./userprofile.component";
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { UserprofiledirectiveModule } from '../../DirectivesView/userprofiledirective/userprofiledirective.module';


@NgModule({
    imports: [
      CommonModule,
      UserProfileRouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      AlertModule,
      NgSelectModule,
      UserprofiledirectiveModule
    ],
    declarations: [UserprofileComponent],
    providers:[ligalEntityService,NgxSpinnerService]
  })
export class UserprofileModule{

}