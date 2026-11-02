import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { Loginservices } from '../../../services/login.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderPipe } from 'ngx-order-pipe';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtCreateassetComponent } from './bt-createasset.component';
import { BtCreateSitemodule } from '../bt-createsite/bt-createsite.module';
import { BtCreateProductModule } from '../bt-creteproduct/bt-createproduct.module';
import { BTCreateaccountModule } from '../bt-createaccount/bt-createaccount.module';
import { BtaccountService } from '../../bat_services/btaccount.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,NgxSpinnerModule,
    RouterModule,NgSelectModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),BtCreateSitemodule,BtCreateProductModule,
    BTCreateaccountModule
  ],
  declarations: [BtCreateassetComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [BtCreateassetComponent],
  providers: [BTassetserviceService,Loginservices,OrderPipe,DatePipe,BtaccountService,NgxSpinnerService]
})
export class BtCreateAssetModule{

}