
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { BtboadingloadgingDetailsRouting } from "./bt-boading-loadging-da-details.routing";
import { ActionhistoryModule } from "../../../DirectivesView/action-history/action-history.module";
import { Loginservices } from "../../../services/login.services";
import { BtBoadingLoadginDaDetailsComponent } from "./bt-boading-loadgin-da-details.component";
import { BTBodingLodginDACreateModule } from "../../batDirectiveView/bt-create-boading-lodgin-da/bt-create-boading-da-module";
import { BAT_BoardingLoadingService } from "../../bat_services/bt-boadigLoadgingDA.service";



@NgModule({
  imports: [
    CommonModule,
    BtboadingloadgingDetailsRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    BTBodingLodginDACreateModule,
    OrderModule,
    ActionhistoryModule
  ],
  declarations: [BtBoadingLoadginDaDetailsComponent],
  providers: [NgxSpinnerService,BAT_BoardingLoadingService]
})
export class btBoadingLoadginDetailsModule{
    
}