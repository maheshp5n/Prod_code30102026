import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import { BatProductRequestItemsDetailsComponent } from './bat-product-request-items-details.component';
import { BatProductRequestItemsRouting } from './bat-product-request.routing';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      BatProductRequestItemsRouting,
      NgxSpinnerModule,
      
    ],
    declarations: [BatProductRequestItemsDetailsComponent],
    providers: [ NgxSpinnerService, Loginservices]
  })
export class BatProductRequestItemsDetailModule{

}