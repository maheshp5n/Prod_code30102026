
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { createboardingModule } from '../../DirectivesView/createboarding/createboarding.module';
import { boadingDetailsRouting } from './boading-da-details.routing';
import { BoadingDaDetailsComponent } from './boading-da-details.component';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { Loginservices } from '../../services/login.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';



@NgModule({
  imports: [
    CommonModule,
    boadingDetailsRouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    createboardingModule,
    OrderModule,
    ActionhistoryModule
  ],
  declarations: [BoadingDaDetailsComponent],
  providers: [BoardingLoadingService, NgxSpinnerService,Loginservices]
})
export class boadingDetailsModule{
    
}