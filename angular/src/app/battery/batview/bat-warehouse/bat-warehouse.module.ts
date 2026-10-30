import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatWarehouseComponent } from './bat-warehouse.component';
import { BatWarehouseRouting } from './bat-warehouse.routing';
import { BtCreatewarehouseModule } from '../../batDirectiveView/bt-createwarehouse/bt-createwarehouse.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { SearchfiltPipe } from './searchfilt.pipe';
import { BtwarehouseService } from '../../bat_services/btwarehouse.service';
import { ligalEntityService } from '../../../services/ligalentity.services';
import { PagefilterService } from '../../../services/pagefilterservice';
import { Loginservices } from '../../../services/login.services';
import { OrderModule } from 'ngx-order-pipe';



@NgModule({
  declarations: [BatWarehouseComponent,SearchfiltPipe],
  imports: [
    CommonModule,
    BatWarehouseRouting,
    FormsModule,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreatewarehouseModule,
    OrderModule
  ],
  exports: [SearchfiltPipe],
  providers: [BtwarehouseService, ligalEntityService, NgxSpinnerService,PagefilterService,Loginservices],
})
export class BatWarehouseModule { }
