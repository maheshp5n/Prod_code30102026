import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { WarehouseService } from "../../../services/warehouse.service";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { PopupSuccessModule } from "../../../DirectivesView/popupSuccess/popupSuccess.module";
import { Loginservices } from '../../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';
import { createWCCModule } from '../../../DirectivesView/createwcc/createwcc.module';
import { WCCService } from '../../../services/wcc.services';
import { BatWccComponent } from "./bat-wcc.component";
import { BatWCCRouting } from "./bat-wcc.routing";
import { BatcreateWCCModule } from "../../batDirectiveView/bt-create-wcc/bt-create-wcc.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BatWCCRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    BatcreateWCCModule,
    OrderModule, PopoverModule.forRoot()
  ],
  declarations: [BatWccComponent],
  providers: [WarehouseService, NgxSpinnerService,Loginservices,WCCService],
})
export class BatWCCModule{

}