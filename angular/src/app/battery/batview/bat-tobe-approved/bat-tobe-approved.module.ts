import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BatTobeApprovedRouting } from './bat-tobe-approved.routing';
import { BatTobeApprovedComponent } from './bat-tobe-approved.component';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BatTobeapprovedDrModule } from '../../batDirectiveView/bat-tobeapproved-dr/bat-tobeapproved-dr.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [BatTobeApprovedComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatTobeApprovedRouting,
    BatTobeapprovedDrModule,
    ModalModule,
    NgSelectModule,
    BsDatepickerModule.forRoot(),
    NgxSpinnerModule
  ],
  providers:[NgxSpinnerService, DatePipe]
})
export class BatTobeApprovedModule { }
