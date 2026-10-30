import { NgModule ,CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { NewsiteComponent } from "./newsite.component";
import { AccountsTypeService } from "../../services/accountstype.services";
import { AccountsCatagoryServices } from "../../services/catagory.services";
import { AccountService } from "../../services/accounts.services";


@NgModule({
    imports: [
      CommonModule,
      FormsModule
    ],
    declarations: [NewsiteComponent],
    schemas:[CUSTOM_ELEMENTS_SCHEMA],
    exports:[NewsiteComponent],
    providers:[AccountsTypeService,AccountsCatagoryServices,AccountService]
  })

export class createSiteModule{}