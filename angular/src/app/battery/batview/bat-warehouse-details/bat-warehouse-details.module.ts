import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BatWarehouseDetailsComponent } from './bat-warehouse-details.component';
import { BatWarehouseDetailsRouting } from './bat-warehouse-details.routing';
import { AlertModule, BsDatepickerModule, BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtCreatewarehouseModule } from '../../batDirectiveView/bt-createwarehouse/bt-createwarehouse.module';
import { createMslRqstModule } from '../../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { ProductrequestdirectiveModule } from '../../../DirectivesView/productrequestdirective/productrequestdirective.module';
import { ProductreturnorderdirectiveModule } from '../../../DirectivesView/productreturnorderdirective/productreturnorderdirective.module';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { mslRequestService } from '../../../services/mslRequest.services';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { Loginservices } from '../../../services/login.services';
import { BtNewProductReqModule } from '../new-productrequest/new-productreqest.module';
import { BtNewProductRequestDerectiveModule } from '../../batDirectiveView/bt-newproductrequestdirective/bt-newproductrequestdirective.module';
import { BtCreateMslrequestModule } from '../../batDirectiveView/bt-create-mslrequest/bt-create-mslrequest.module';
import { BtMslrequestService } from '../../bat_services/bt-mslrequest.service';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtCreateProductReturnModule } from '../../batDirectiveView/bt-create-product-return/bt-create-product-return.module';



@NgModule({
  declarations: [BatWarehouseDetailsComponent],
  imports: [
    CommonModule,
    BatWarehouseDetailsRouting,
    ModalModule, FormsModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreatewarehouseModule,
    BtCreateMslrequestModule,
    // BtNewProductRequestDerectiveModule,
    AlertModule,
    ProductreturnorderdirectiveModule,
    BtActionhistoryModule,
    BsDatepickerModule.forRoot(),
    BtCreateProductReturnModule
  ],
  providers:[BtMslrequestService,NgxSpinnerService,BtwarehouseService,Loginservices,DatePipe]
})
export class BatWarehouseDetailsModule { }
