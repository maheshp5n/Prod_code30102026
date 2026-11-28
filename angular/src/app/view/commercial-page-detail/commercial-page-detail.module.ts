
import { NgModule } from "@angular/core";

import { CommonModule, DatePipe } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { caseService } from '../../services/caseService.service';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { commercialPageDetailRouting } from './commercial-pge-detail.routing';
import { CommercialPageDetailComponent } from './commercial-page-detail.component';
import { CreateInvoiceModule } from '../../DirectivesView/createinvoice/createinvoice.module';
import { CommercialService } from '../../services/commercial.services';
import { AreYouSureModule } from '../../DirectivesView/are-you-sure/are-you-sure.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { RejectCaseCOmModule } from "../../DirectivesView/rejectcase/rejectcase.module";
import { PdfViewerModule } from "ng2-pdf-viewer";
import { ChangeCaseTypeModule } from "../../DirectivesView/change-casetype-commer/change_casetype-commer.module";


@NgModule({
  imports: [
    CommonModule,
    commercialPageDetailRouting,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    CreateInvoiceModule,
    AreYouSureModule,PdfViewerModule,
    ActionhistoryModule,RejectCaseCOmModule,ChangeCaseTypeModule
  ],
  declarations: [CommercialPageDetailComponent],
 providers: [caseService, NgxSpinnerService, Loginservices,CommercialService,DatePipe]
})
export class commercialPageDetailModule{

}