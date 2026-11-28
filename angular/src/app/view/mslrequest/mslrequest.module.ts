
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ligalEntityService } from "../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { OrderModule } from 'ngx-order-pipe';
import { MslrequestComponent } from './mslrequest.component';
import { MslrequestRouting } from './mslrequest.routing';
import { SearchfiltPipe } from './searchfilt.pipe';
import { createMslRqstModule } from '../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { mslRequestService } from '../../services/mslRequest.services';
import { PagefilterService } from '../../services/pagefilterservice';
@NgModule({
    imports: [
      CommonModule,
      MslrequestRouting,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      createMslRqstModule,OrderModule
    ],
    declarations: [MslrequestComponent,SearchfiltPipe],
    exports: [SearchfiltPipe],
    providers:[ligalEntityService,NgxSpinnerService,Loginservices,mslRequestService,PagefilterService]
  })
export class MslrequestModule{

}