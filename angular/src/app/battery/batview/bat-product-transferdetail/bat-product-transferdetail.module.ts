import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';
import { BatProducttransferDetailsRouting } from './bat-product-transferdetail.routing';
import { SapdocdisplayModule } from '../../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import { BatProductTransferDetailsComponent } from './bat-product-transfer-details/bat-product-transfer-details.component';
import { BatProductTransferRelatedComponent } from './bat-product-transfer-related/bat-product-transfer-related.component';
import { BatProductTransferdetailComponent } from './bat-product-transferdetail.component';
import { PrudcttransferDataService } from '../../../view/producttransferdetails/producttransferdata.service';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BtProductTransferDataService } from './bat-product-transfer.data.serice';
import { BatSapDocumentDirectiveModule } from '../../batDirectiveView/bat-sap-document-directive/bat-sap-document-directive.module';
import { BtEwaybillModule } from '../../batDirectiveView/bt-ewaybill/bt-ewaybill.module';



@NgModule({
  declarations: [BatProductTransferdetailComponent,BatProductTransferDetailsComponent,BatProductTransferRelatedComponent],
  imports: [
    CommonModule,
    BatProducttransferDetailsRouting,
    NgxSpinnerModule,
    FormsModule,   
    BtActionhistoryModule,
    SapdocdisplayModule,
    BatSapDocumentDirectiveModule,
    BtEwaybillModule,
     ModalModule.forRoot(),

  ],
  providers: [NgxSpinnerService,PrudcttransferDataService,BtProductTransferDataService]
})
export class BatProducttransferdetailsModule { }
