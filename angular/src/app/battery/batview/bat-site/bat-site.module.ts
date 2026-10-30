import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, PopoverModule } from 'ngx-bootstrap';
import { BatSiteComponent } from './bat-site.component';
import { BatSiteRouting } from './bat-site.routing';
import { siteService } from '../../../services/siteService.services';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule, OrderPipe } from 'ngx-order-pipe';
import { BtCreateSitemodule } from '../../batDirectiveView/bt-createsite/bt-createsite.module';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,BatSiteRouting,NgxSpinnerModule,BtCreateSitemodule,
    BsDropdownModule,OrderModule, PopoverModule.forRoot()
   
  ],
  declarations: [BatSiteComponent],
  // schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatSiteComponent],
  providers: [Loginservices,NgxSpinnerService,OrderPipe,BtsiteserviceService]
})
export class batSiteModule{}