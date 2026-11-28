import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from "@angular/forms";
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { MslRequestDetailsRouting } from './mslrequestdetails.routing';
import { MslrequestdetailsComponent } from './mslrequestdetails.component';
import { createMslRqstModule } from '../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { mslRequestService } from '../../services/mslRequest.services';
import { createlocationModule } from '../../DirectivesView/create-location/create-location.module';
import { LocationService } from '../../services/location.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProductsService } from '../../services/products.services';
import { ItemapprovalModule } from '../../DirectivesView/itemapproval/itemapproval.module';


@NgModule({
    imports: [
      CommonModule,
      MslRequestDetailsRouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,createMslRqstModule,createlocationModule,
      NgSelectModule,ItemapprovalModule
    ],
    declarations: [MslrequestdetailsComponent],
    providers:[mslRequestService,NgxSpinnerService,LocationService,ProductsService]
  })
export class MslreqestDetailsModule{

}