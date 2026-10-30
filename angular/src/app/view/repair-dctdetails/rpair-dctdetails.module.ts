import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDropdownModule, PopoverModule, ModalModule } from 'ngx-bootstrap';
import { createSSAModule } from '../../DirectivesView/create-ssa/create-ssa-module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ssaService } from '../../services/ssa.services';
import { RpairDetailsRout } from './repair-dctdetails.routing';
import { RepairDCTDetailsComponent } from './repair-dctdetails.component';
import { createRpairModule } from '../../DirectivesView/create-rpair-dct/create-rpair-dct.module';
import { DCTRepairservice } from '../../services/dctRepair.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
    imports: [
      CommonModule,
      RpairDetailsRout,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      createSSAModule,
      HttpClientModule,
      BsDropdownModule,
      NgxSpinnerModule,
      PopoverModule.forRoot(),
      createRpairModule,
      ActionhistoryModule
    ],
    declarations: [RepairDCTDetailsComponent],
    providers: [Loginservices, NgxSpinnerService, DCTRepairservice],
  
  })
export class RpairDetailsModule{

}