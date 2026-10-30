import { ServiceorderitemreturnreceiveModule } from './../../DirectivesView/serviceorderitemreturnreceive/serviceorderitemreturnreceive.module';
import { PendingserviceorderreturnModule } from './../../DirectivesView/pendingserviceorderreturn/pendingserviceorderreturn.module';
import { ServiceorderreceivedirecModule } from './../../DirectivesView/serviceorderreceivedirec/serviceorderreceivedirec.module';
import { ServiceorderdirectiveModule } from './../../DirectivesView/serviceorderdirective/serviceorderdirective.module';
import { ServiceorderComponent } from './serviceorder.component';
import { ServiceOrderRouting } from './serviceorder.routing';
import { GlobalSearchPipeModule } from './../../pipe/global-search-pipe.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule } from 'ngx-spinner';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [ServiceorderComponent],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    ModalModule,
    FormsModule,
    BsDropdownModule,
    GlobalSearchPipeModule,
    ServiceOrderRouting,
    // ServiceorderdirectiveModule,
    ServiceorderreceivedirecModule,
    PendingserviceorderreturnModule,
    ServiceorderitemreturnreceiveModule,NgSelectModule
  ]
})
export class ServiceorderModule { }
