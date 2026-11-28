import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProducttransferdetailsComponent } from './producttransferdetails.component';
import { ProducttransferDetailsRouting } from './producttransferdetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';
import { ProducttransferdetailsdetailComponent } from './producttransferdetailsdetail/producttransferdetailsdetail.component';
import { ProducttransferdetailsreletedComponent } from './producttransferdetailsreleted/producttransferdetailsreleted.component';
import { PrudcttransferDataService } from './producttransferdata.service';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import { ModalModule } from 'ngx-bootstrap/modal';



@NgModule({
  declarations: [ProducttransferdetailsComponent, ProducttransferdetailsdetailComponent, ProducttransferdetailsreletedComponent],
  imports: [
    CommonModule,
    ProducttransferDetailsRouting,
    NgxSpinnerModule,
    FormsModule,
    ActionhistoryModule,
    SapdocdisplayModule,ModalModule
  ],
  providers: [NgxSpinnerService, PrudcttransferDataService]
})
export class ProducttransferdetailsModule { }
