import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AlertModule } from 'ngx-bootstrap/alert';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProducttransferdirectiveModule } from '../../../DirectivesView/producttransferdirective/producttransferdirective.module';
import { ProductrequestreceiveModule } from '../../../DirectivesView/productrequestreceive/productrequestreceive.module';
import { SapdocdisplayModule } from '../../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import {BatProductrequestdetailRouting} from './bat-productrequestdetail.routing';
import { BatProductrequestdetailComponent } from './bat-productrequestdetail.component';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import {BtNewProductRequestDerectiveModule} from '../../batDirectiveView/bt-newproductrequestdirective/bt-newproductrequestdirective.module';


@NgModule({
  declarations: [BatProductrequestdetailComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatProductrequestdetailRouting,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    NgSelectModule,
    ProducttransferdirectiveModule,
    ProductrequestreceiveModule,
    BtActionhistoryModule,
    SapdocdisplayModule,
    BtNewProductRequestDerectiveModule,
    
  ],
  providers: [NgxSpinnerService]
})
export class BatProductrequestdetailsModule { }
