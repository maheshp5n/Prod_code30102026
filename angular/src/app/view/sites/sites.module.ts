import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";


import { Sitestrouting } from "./sites.routing";
import { SitesComponent } from "./sites.component";
import { ModalModule } from "ngx-bootstrap/modal";
import { CreateaccountModule } from "../../DirectivesView/createaccount/createaccount.module";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { siteService } from "../../services/siteService.services";
import { Loginservices } from "../../services/login.services";
import { NgxSpinnerService ,NgxSpinnerModule} from "ngx-spinner";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { CreateSiteModule } from "../../DirectivesView/create-site/create-site.module";
import { OrderModule } from "ngx-order-pipe";
import { PopoverModule } from "ngx-bootstrap";

//import { AgGridModule } from 'ag-grid-angular';




@NgModule({
  imports: [
    CommonModule,
    Sitestrouting,
    ModalModule, FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    CreateaccountModule,
    CreateSiteModule,OrderModule, PopoverModule.forRoot()
    /* AgGridModule.withComponents([])*/
  ],
  declarations: [SitesComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers:[siteService,Loginservices,NgxSpinnerService]
})
export class SitesModule { }
