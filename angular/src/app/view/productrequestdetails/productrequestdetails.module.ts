import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductrequestdetailsComponent } from './productrequestdetails.component';
import { FormsModule } from '@angular/forms'
import { ProductrequestRoutingmodule } from './productrequestdetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AlertModule } from 'ngx-bootstrap/alert';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProducttransferdirectiveModule } from '../../DirectivesView/producttransferdirective/producttransferdirective.module';
import { ProductrequestreceiveModule } from '../../DirectivesView/productrequestreceive/productrequestreceive.module';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../DirectivesView/sapdocdisplay/sapdocdisplay.module';


@NgModule({
  declarations: [ProductrequestdetailsComponent],
  imports: [
    CommonModule,
    FormsModule,
    ProductrequestRoutingmodule,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    NgSelectModule,
    ProducttransferdirectiveModule,
    ProductrequestreceiveModule,
    ActionhistoryModule,
    SapdocdisplayModule
  ],
  providers: [NgxSpinnerService]
})
export class ProductrequestdetailsModule { }
