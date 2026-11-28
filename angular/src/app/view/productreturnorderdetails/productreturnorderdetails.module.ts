import { ProductreturnadditemsModule } from './../../DirectivesView/productreturnadditems/productreturnadditems.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductreturnorderdetailsRouting } from './productreturnorderdetails.routing';
import { ProductreturnorderdetailsComponent } from './productreturnorderdetails.component';
import { FormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ProductreturnreceivedirectiveModule } from '../../DirectivesView/productreturnreceivedirective/productreturnreceivedirective.module';
import { ProductreturnorderdetailsdetailsComponent } from './productreturnorderdetailsdetails/productreturnorderdetailsdetails.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ProductreturnorderDetailsDataTransfer } from './productreturnorderdetails.datatransfer';
import { ProducttransferdetailsreletedComponent } from '../producttransferdetails/producttransferdetailsreleted/producttransferdetailsreleted.component';
import { ProductreturnorderdetailsreletedComponent } from './productreturnorderdetailsreleted/productreturnorderdetailsreleted.component';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../DirectivesView/sapdocdisplay/sapdocdisplay.module';



@NgModule({
  declarations: [ProductreturnorderdetailsComponent, ProductreturnorderdetailsdetailsComponent, ProductreturnorderdetailsreletedComponent],
  imports: [
    CommonModule,
    ProductreturnorderdetailsRouting,
    FormsModule,
    AlertModule,
    ModalModule,
    NgxSpinnerModule,
    ProductreturnreceivedirectiveModule,
    ProductreturnadditemsModule,
    ActionhistoryModule,
    SapdocdisplayModule
  ],
  providers: [NgxSpinnerService, ProductreturnorderDetailsDataTransfer]
})
export class ProductreturnorderdetailsModule { }
