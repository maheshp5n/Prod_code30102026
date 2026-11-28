import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { WarehouseService } from "../../services/warehouse.service";
import { ligalEntityService } from "../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { PopupSuccessModule } from "../../DirectivesView/popupSuccess/popupSuccess.module";
import { CreateNewWarehouseModule } from "../../DirectivesView/create-new-warehouse/create-new-warehouse.module";
import { WccComponent } from './wcc.component';
import { WCCRouting } from './wcc.routing';
import { Loginservices } from '../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';
import { createWCCModule } from '../../DirectivesView/createwcc/createwcc.module';
import { SearchfiltPipe } from './searchfilt.pipe';
import { WCCService } from '../../services/wcc.services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    WCCRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    createWCCModule,
    OrderModule, PopoverModule.forRoot()
  ],
  declarations: [WccComponent,SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [WarehouseService, NgxSpinnerService,Loginservices,WCCService],
})
export class WCCModule{

}