import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatLodgingdetailsComponent } from './bat-lodgingdetails.component';
import { BatLodgingdetailsRouting } from './bat-lodgingdetails.roting';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';
import { BtCreateLodgingModule } from '../../../batDirectiveView/bt-create-lodging/bt-create-lodging.module';
import { ActionhistoryModule } from '../../../../DirectivesView/action-history/action-history.module';



@NgModule({
  declarations: [BatLodgingdetailsComponent],
  imports: [
    CommonModule,
    BatLodgingdetailsRouting,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BtCreateLodgingModule,
    ActionhistoryModule
  ],
  providers: [NgxSpinnerService]
})
export class BatLodgingdetailsModule { }
