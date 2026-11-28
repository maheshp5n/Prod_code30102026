import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap/modal';
import {
  ServiceorderreceivedirecModule
} from '../../../DirectivesView/serviceorderreceivedirec/serviceorderreceivedirec.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { ActionhistoryModule } from '../../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BatServiceorderdetailsComponent } from './bat-serviceorderdetails.component';
import { BatServiceOrderDetailsRouting } from './bat-serviceorderdetails.routing';
import { BatServiceorderdetailsRelatedComponent } from './bat-serviceorderdetails-related/bat-serviceorderdetails-related.component';
import { BatServiceorderdetailsViewComponent } from './bat-serviceorderdetails-view/bat-serviceorderdetails-view.component';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtServiceOrderReceiveModule } from '../../batDirectiveView/bt-service-order-receive/bt-service-order-receive.module';
import { BtAssignServiceOrderModule } from '../../batDirectiveView/bt-assign-service-order/bt-assign-service-order.module';
import { BtCreateServiceOrderReturnModule } from '../../batDirectiveView/bt-create-service-order-return/bt-create-service-order-return.module';



@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [BatServiceorderdetailsComponent,
    BatServiceorderdetailsRelatedComponent,
    BatServiceorderdetailsViewComponent  
  ],
  imports: [
    CommonModule,
    BatServiceOrderDetailsRouting,
    FormsModule,
    ModalModule,
    ServiceorderreceivedirecModule,
    NgSelectModule,
    BtActionhistoryModule,SapdocdisplayModule,NgxSpinnerModule,
    BtServiceOrderReceiveModule,
    BtAssignServiceOrderModule,  
    BtCreateServiceOrderReturnModule
  ],
  providers :[NgxSpinnerService]
})
export class BatServiceorderdetailsModule { }
