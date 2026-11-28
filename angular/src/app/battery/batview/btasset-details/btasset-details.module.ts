
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BTassetDetailsComponent } from './btasset-details.component';
import { BtAssetDetailsRouting } from './btasset-details.routing';
import { RouterModule } from '@angular/router';
import { OrderModule } from 'ngx-order-pipe';
import { AssetsService } from '../../../services/assets.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { caseService } from '../../../services/caseService.service';
import { BtCreateAssetModule } from '../../batDirectiveView/bt-createasset/bt-createasset.module';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BTCreateCaseModule } from '../../batDirectiveView/btcreatecase/btcreatecase.module';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BtAssetDetailsRouting,RouterModule,OrderModule,BtCreateAssetModule,BTCreateCaseModule,NgxSpinnerModule,ActionhistoryModule
   
  ],
  declarations: [BTassetDetailsComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers: [AssetsService, NgxSpinnerService,DatePipe,BTassetserviceService,caseService]
})
export class BTAssetDetailsModules{

}