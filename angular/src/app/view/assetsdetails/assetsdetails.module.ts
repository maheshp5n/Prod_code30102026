import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AssetsdetailsRouting } from './assetsdetails.routing';
import { AssetsdetailsComponent } from './assetsdetails.component';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { AssetsService } from '../../services/assets.services';
import {  ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { CreateassetModule } from '../../DirectivesView/createasset/createasset.module';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { caseService } from '../../services/caseService.service';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { CreatePreRegisCase } from '../../DirectivesView/case-create-pre-regis/case-create-pre-regis.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AssetsdetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    BsDropdownModule,
    CreateassetModule,
    CreatecaseModule,
    ActionhistoryModule,CreatePreRegisCase
  ],
  declarations: [AssetsdetailsComponent],
  providers: [ AssetsService, NgxSpinnerService,caseService,DatePipe]
})
export class AssetsdetailsModule { }
