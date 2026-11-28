import { AssetsService } from './../../services/assets.services';
import { caseService } from './../../services/caseService.service';
import { HttpClientModule } from '@angular/common/http';
import { SitedetailsComponent } from "./sitedetails.component";
import { CreateaccountModule } from "../../DirectivesView/createaccount/createaccount.module";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { ModalModule } from "ngx-bootstrap/modal";
import { Sitestrouting } from "../sites/sites.routing";
import { FormsModule } from "@angular/forms";
import { CommonModule, DatePipe } from "@angular/common";
import { NgModule } from "@angular/core";
import { Sitedetailsrouting } from "./sitedetails.routing";
import { AccountsindetailsRouting } from "../accountsindetails/accountsindetails.routing";
import { siteService } from "../../services/siteService.services";
import { Loginservices } from "../../services/login.services";
import { CreateSiteModule } from "../../DirectivesView/create-site/create-site.module";
import { NgxSpinnerService, NgxSpinnerModule } from "ngx-spinner";
import { PopoverModule } from 'ngx-bootstrap/popover';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { CreateNewServiceAppointmentsModule } from '../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { CreateassetModule } from '../../DirectivesView/createasset/createasset.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { CreatePreRegisCase } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.module';
import { PreRegCaseService } from '../../services/preregcaseservice.services';

@NgModule({
  imports: [
    CommonModule,
    Sitedetailsrouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    CreateNewServiceAppointmentsModule,
    CreateassetModule,
    HttpClientModule,
    BsDropdownModule,
    CreateSiteModule,
    NgxSpinnerModule,
    CreatecaseModule,
    PopoverModule.forRoot(),
    ActionhistoryModule

  ],
  declarations: [SitedetailsComponent],
  providers: [siteService, Loginservices, NgxSpinnerService, caseService,
     AssetsService, NgxSpinnerService,PreRegCaseService],

})
export class SitedetailsModule {

}
