import { ServiceOrderDetailsRouting } from './serviceorderdetails.routing';
import { ServiceorderdetailsComponent } from './serviceorderdetails.component';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceorderDetailsviewComponent } from './serviceorder.detailsview/serviceorder.detailsview.component';
import { ServiceorderReleatedviewComponent } from './serviceorder.releatedview/serviceorder.releatedview.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import {
  ServiceorderreceivedirecModule
} from '../../DirectivesView/serviceorderreceivedirec/serviceorderreceivedirec.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';
import { SapdocdisplayModule } from '../../DirectivesView/sapdocdisplay/sapdocdisplay.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ServiceorderdetailsComponent,
    ServiceorderDetailsviewComponent,
    ServiceorderReleatedviewComponent],
  imports: [
    CommonModule,
    ServiceOrderDetailsRouting,
    FormsModule,
    ModalModule,
    ServiceorderreceivedirecModule,
    NgSelectModule,
    ActionhistoryModule,SapdocdisplayModule,NgxSpinnerModule
  ],
  providers :[NgxSpinnerService]
})
export class ServiceorderdetailsModule { }
