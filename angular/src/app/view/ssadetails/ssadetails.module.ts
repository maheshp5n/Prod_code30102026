import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SSADetailsRout } from './ssadetails.routing';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { ModalModule } from 'ngx-bootstrap/modal';
import { createSSAModule } from '../../DirectivesView/create-ssa/create-ssa-module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { SsadetailsComponent } from './ssadetails.component';
import { Loginservices } from '../../services/login.services';
import { ssaService } from '../../services/ssa.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
    imports: [
      CommonModule,
      SSADetailsRout,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      createSSAModule,
      HttpClientModule,
      BsDropdownModule,
      NgxSpinnerModule,
      PopoverModule.forRoot(),
      ActionhistoryModule
  
    ],
    declarations: [SsadetailsComponent],
    providers: [Loginservices, NgxSpinnerService, ssaService],
  
  })
export class SSADetailsModule{

}