import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatCustomerInventoryComponent } from './bat-customer-inventory.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BatCustomerInventoryRouting } from './bat-customer-inventory.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import {BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { BtCustmerInventoryOutModule } from '../../batDirectiveView/bt-customer-out/bt-customer-out.module';
import { BtCustomerInventoryInModule } from '../../batDirectiveView/bt-customer-in/bt-customer-in.module';
import { BatEditGRNModule } from '../../batDirectiveView/bat-edit-grn/bat-edit-grn.module';
@NgModule({
  declarations: [BatCustomerInventoryComponent],
  imports: [
    CommonModule,
    FormsModule,
    CommonModule,
    BatCustomerInventoryRouting,   
    NgxSpinnerModule,
    ReactiveFormsModule,
    ModalModule,
    BsDropdownModule,
    BtCustmerInventoryOutModule,
    BtCustomerInventoryInModule,
    BatEditGRNModule
  ],
  providers:[NgxSpinnerService]
})
export class BatCustomerInventoryModule { }
