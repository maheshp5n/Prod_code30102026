import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { siteService } from '../../../services/siteService.services';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { BtCreateSitemodule } from '../../batDirectiveView/bt-createsite/bt-createsite.module';
import { BatSiteDetailRouting } from './bat-site-detail.routing';
import { BatSiteDetailComponent } from './bat-site-detail.component';
import { CreateNewServiceAppointmentsModule } from '../../../DirectivesView/create-new-service-appointments/create-new-service-appointments.module';
import { CreateassetModule } from '../../../DirectivesView/createasset/createasset.module';
import { HttpClientModule } from '@angular/common/http';
import { CreatecaseModule } from '../../../DirectivesView/createcase/createcase.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { CreatePreRegisCase } from '../../../DirectivesView/case-create-pre-regis/case-create-pre-regis.module';
import { PreRegCaseService } from '../../../services/preregcaseservice.services';
import { AssetsService } from '../../../services/assets.services';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BTCreateCaseModule } from '../../batDirectiveView/btcreatecase/btcreatecase.module';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtActionHistoryComponent } from '../../batDirectiveView/bt-action-history/bt-action-history.component';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BatSiteDetailRouting,
    NgxSpinnerModule,
    BtCreateSitemodule,
    BsDropdownModule,
    HttpClientModule,
    BTCreateCaseModule,
    PopoverModule.forRoot(),
    BtActionhistoryModule,
    BTCreateCaseModule
   
  ],
  declarations: [BatSiteDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BatSiteDetailComponent],
  providers: [Loginservices,NgxSpinnerService,OrderPipe,BTassetserviceService,BtsiteserviceService]
})
export class batSiteDetailModule{}