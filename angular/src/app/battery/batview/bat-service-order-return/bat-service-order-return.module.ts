import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatServiceOrderReturnRouting } from './bat-service-order-return.routing';
import { BatServiceOrderReturnComponent } from './bat-service-order-return.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BtCreateServiceOrderReturnModule } from '../../batDirectiveView/bt-create-service-order-return/bt-create-service-order-return.module';
import { BatReturnOrderReceiveDirModule } from '../../batDirectiveView/bat-return-order-receive-dir/bat-return-order-receive-dir.module';



@NgModule({
  declarations: [BatServiceOrderReturnComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatServiceOrderReturnRouting,
    NgxSpinnerModule,
    ModalModule,
    BsDropdownModule,
    BtCreateServiceOrderReturnModule,
    BatReturnOrderReceiveDirModule
  ],
  providers:[NgxSpinnerService]
})
export class BatServiceOrderReturnModule { }
