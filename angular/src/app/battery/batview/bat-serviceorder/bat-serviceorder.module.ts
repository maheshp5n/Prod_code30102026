import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule } from 'ngx-spinner';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { BatServiceorderComponent } from './bat-serviceorder.component';
import { BatServiceOrderRouting } from './bat-serviceorder.routing';
import { BtServiceOrderReceiveModule } from '../../batDirectiveView/bt-service-order-receive/bt-service-order-receive.module';
import { BtAssignServiceOrderModule } from '../../batDirectiveView/bt-assign-service-order/bt-assign-service-order.module';
import { BtCreateServiceOrderReturnModule } from '../../batDirectiveView/bt-create-service-order-return/bt-create-service-order-return.module';



@NgModule({
  declarations: [BatServiceorderComponent],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    BatServiceOrderRouting,
    NgSelectModule,
    BtServiceOrderReceiveModule,
    BtAssignServiceOrderModule,
    BtCreateServiceOrderReturnModule
  ]
})
export class BatServiceorderModule { }
