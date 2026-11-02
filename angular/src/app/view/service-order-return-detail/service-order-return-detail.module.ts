import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule } from "ngx-spinner";
import { ActionhistoryModule } from "../../DirectivesView/action-history/action-history.module";
import { SapdocdisplayModule } from "../../DirectivesView/sapdocdisplay/sapdocdisplay.module";
import { ServiceOrderReturnDetailComponent } from "./service-order-return-detail.component";
import { ServiceOrderReturnDetailRouting } from "./service-order-return-detail.routing";

@NgModule({
    imports:[ServiceOrderReturnDetailRouting,
    CommonModule,FormsModule,SapdocdisplayModule,
    NgxSpinnerModule,ModalModule,ActionhistoryModule
],
    declarations:[ServiceOrderReturnDetailComponent]
})

export class ServiceOrderReturnDetailModule{

}