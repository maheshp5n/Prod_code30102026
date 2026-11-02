import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { PdfViewerModule } from "ng2-pdf-viewer";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule } from "ngx-spinner";
import { BatEditGRNModule } from "../../batDirectiveView/bat-edit-grn/bat-edit-grn.module";
import { BtCustomerInventoryInModule } from "../../batDirectiveView/bt-customer-in/bt-customer-in.module";
import { BatCustomerInventoryInDetailComponent } from "./bat-customer-inventory-in-detail.component";
import { BatCustomerInventoryInDetailRouting } from "./bat-customer-inventory-in-detail.routing";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgxSpinnerModule,
        BatCustomerInventoryInDetailRouting,
        BtCustomerInventoryInModule,
        ModalModule,
        PdfViewerModule,
        BatEditGRNModule
    ],
    declarations :[BatCustomerInventoryInDetailComponent],
    providers:[]
    
})
export class BatCustomerInventoryInDetailModule{}