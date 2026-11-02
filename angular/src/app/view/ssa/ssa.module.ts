import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SSARouting } from './ssa.routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { createSSAModule } from '../../DirectivesView/create-ssa/create-ssa-module';
import { OrderModule } from 'ngx-order-pipe';
import { SsaComponent } from './ssa.component';
import { SearchfiltPipe } from './searchfilt.pipe';
import { Loginservices } from '../../services/login.services';
import { ssaService } from '../../services/ssa.services';

@NgModule({
    imports: [
      CommonModule,
      SSARouting,
      ModalModule, FormsModule,
      BsDropdownModule,
      NgxSpinnerModule,
      createSSAModule,OrderModule, PopoverModule.forRoot()
      /* AgGridModule.withComponents([])*/
    ],
    declarations: [SsaComponent,SearchfiltPipe],
    exports: [SearchfiltPipe],
    providers:[Loginservices,NgxSpinnerService,ssaService]
  })
export class SSAModule{

}