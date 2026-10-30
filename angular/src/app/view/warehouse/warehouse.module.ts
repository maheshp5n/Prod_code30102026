import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { WarehouseRouting } from "./warehouse.routing";
import { WarehouseComponent } from "./warehouse.component";
import { WarehouseService } from "../../services/warehouse.service";
import { ligalEntityService } from "../../services/ligalentity.services";
import { SearchfiltPipe } from "./searchfilt.pipe";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { PopupSuccessModule } from "../../DirectivesView/popupSuccess/popupSuccess.module";
import { CreateNewWarehouseModule } from "../../DirectivesView/create-new-warehouse/create-new-warehouse.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    WarehouseRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    PopupSuccessModule,
    CreateNewWarehouseModule
  ],
  declarations: [WarehouseComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [WarehouseService, ligalEntityService, NgxSpinnerService],
})
export class WarehouseModule { }