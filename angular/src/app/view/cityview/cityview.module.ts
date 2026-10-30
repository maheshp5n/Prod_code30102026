import { NgModule } from "@angular/core";
import { cityViewRouting } from "./cityviewRout.routing";
import { CommonModule } from "@angular/common";
import { ModalModule ,BsDropdownModule} from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { cityService } from "../../services/cityService.service";
import { CityviewComponent } from "./cityview.component";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { CreatecityModule } from "../../DirectivesView/createcity/createcity.module";
import { Loginservices } from "../../services/login.services";
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';


@NgModule({
    imports: [
      CommonModule,
      cityViewRouting,
      ModalModule, 
      FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      CreatecityModule,
      ActionhistoryModule
    ],
    declarations: [CityviewComponent],
    providers:[cityService, NgxSpinnerService,Loginservices]
  })
export class CityViewModule{

}