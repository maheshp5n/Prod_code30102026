
import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { ModalModule, BsDropdownModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { caseService } from '../../../services/caseService.service';
import { CommercialService } from '../../../services/commercial.services';
import { PdfViewerModule } from "ng2-pdf-viewer";
import { BatCommercialDetailRouting} from "./bat-commercial-detail.routing";
import { BatCommercialDetailComponent } from "./bat-commercial-detail.component";
import { BtCreateInvoiceModule } from "../../batDirectiveView/bt-create-invoice/bt-create-invoice.module";
import { BtActionhistoryModule } from "../../batDirectiveView/bt-action-history/bt-action-history.module";
import { BtAreYouSureModule } from "../../batDirectiveView/bt-are-you-sure/bt-are-you-sure.module";
import { BtChangeCaseTypeModule } from "../../batDirectiveView/bt-change-case-type-commer/bt-change-case-type-commer.module";
import {BtRejectCaseModule } from "../../batDirectiveView/bt-reject-case/bt-reject-case.module";


@NgModule({
  imports: [
    CommonModule,
    BatCommercialDetailRouting,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreateInvoiceModule,
    PdfViewerModule,
    BtActionhistoryModule,
    BtAreYouSureModule,
    BtChangeCaseTypeModule,
    BtRejectCaseModule
  ],
  declarations: [BatCommercialDetailComponent],
 providers: [caseService, NgxSpinnerService, Loginservices,CommercialService,DatePipe]
})
export class BatcommercialDetailModule{

}