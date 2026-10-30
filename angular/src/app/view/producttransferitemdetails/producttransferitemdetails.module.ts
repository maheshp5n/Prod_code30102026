import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import { ModalModule } from 'ngx-bootstrap';
import { ProducttransferitemdetailsComponent } from './producttransferitemdetails.component';
import { PrudcttransferDataService } from '../producttransferdetails/producttransferdata.service';
import { PTIDetailsRouting } from './producttransferitemdetails.routing';



@NgModule({
  declarations: [ProducttransferitemdetailsComponent],
  imports: [
    CommonModule,
    PTIDetailsRouting,
    NgxSpinnerModule,
    FormsModule
    ,ModalModule
  ],
  providers: [NgxSpinnerService,PrudcttransferDataService]
})
export class PTIDetailsModule{

}