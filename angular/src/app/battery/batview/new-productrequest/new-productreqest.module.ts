import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewProductrequestComponent } from './new-productrequest.component';
import { BtNewProductReqRouting } from './new-productrequest.routing';
import { BtNewProductRequestDerectiveModule } from '../../batDirectiveView/bt-newproductrequestdirective/bt-newproductrequestdirective.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { BsDatepickerModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [NewProductrequestComponent],
  imports: [
    CommonModule,
    FormsModule,
    BtNewProductReqRouting,
    BtNewProductRequestDerectiveModule,
    NgxSpinnerModule,
    NgSelectModule,
    ModalModule,
    BsDatepickerModule

  ]
})
export class BtNewProductReqModule { }
