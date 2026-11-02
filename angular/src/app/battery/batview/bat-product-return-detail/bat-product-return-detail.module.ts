import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { ProductreturnadditemsModule } from "../../../DirectivesView/productreturnadditems/productreturnadditems.module";
import { SapdocdisplayModule } from "../../../DirectivesView/sapdocdisplay/sapdocdisplay.module";
import { BatSapDocumentDirectiveModule } from "../../batDirectiveView/bat-sap-document-directive/bat-sap-document-directive.module";
import { BtActionhistoryModule } from "../../batDirectiveView/bt-action-history/bt-action-history.module";
import { BtAddProductReturnItemsModule } from "../../batDirectiveView/bt-add-product-return-items/bt-add-product-return-items.module";
import { BtProductReturnReceiveModule } from "../../batDirectiveView/bt-product-return-receive/bt-product-return-receive.module";
import { BatProductReturnDetailDetailsComponent } from "./bat-product-return-detail-details/bat-product-return-detail-details.component";
import { BatProductReturnDetailRelatedComponent } from "./bat-product-return-detail-related/bat-product-return-detail-related.component";
import { BatProductReturnDetailComponent } from "./bat-product-return-detail.component";
import { BtProductreturnorderDetailsDataTransfer } from "./bat-product-return-detail.datatrasfer";
import { BatProductReturnDetailRouting } from "./bat-product-retutn-detail.routing";

@NgModule({
  declarations: [BatProductReturnDetailComponent, BatProductReturnDetailDetailsComponent, BatProductReturnDetailRelatedComponent],
  imports: [
    CommonModule,
    BatProductReturnDetailRouting,
    FormsModule,
    AlertModule,
    ModalModule,
    NgxSpinnerModule,
    BtProductReturnReceiveModule,
    ProductreturnadditemsModule,
    BtActionhistoryModule,
    SapdocdisplayModule,
    BtAddProductReturnItemsModule,
    BatSapDocumentDirectiveModule
  ],
  providers: [NgxSpinnerService, BtProductreturnorderDetailsDataTransfer]
})
export class BatProductReturnDetailModule { }

