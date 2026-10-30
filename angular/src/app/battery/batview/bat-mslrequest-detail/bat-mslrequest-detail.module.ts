import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule, BsDropdownModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { createMslRqstModule } from '../../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { mslRequestService } from '../../../services/mslRequest.services';
import { createlocationModule } from '../../../DirectivesView/create-location/create-location.module';
import { LocationService } from '../../../services/location.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProductsService } from '../../../services/products.services';
import { ItemapprovalModule } from '../../../DirectivesView/itemapproval/itemapproval.module';
import { BatMslrequestDetailRouting } from "./bat-mslrequest-detail.routing";
import { BatMslrequestDetailComponent } from "./bat-mslrequest-detail.component";
import { BatTobeapprovedDrModule } from "../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module";


@NgModule({
    imports: [
      CommonModule,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      // createMslRqstModule,createlocationModule,
      NgSelectModule,ItemapprovalModule,
      BatMslrequestDetailRouting,
      BatTobeapprovedDrModule
    ],
    declarations: [BatMslrequestDetailComponent],
    providers:[mslRequestService,NgxSpinnerService,ProductsService]
  })
export class BatMslrequestDetailModule{

}
