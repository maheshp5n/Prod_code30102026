import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatFsrServiceComponent } from './bat-fsr-service.component';
import { BatFsrServiceRouting } from './bat-fsr-service.routing';
import { NgSelectModule } from '@ng-select/ng-select';
import { BtCreateFsrServiceModule } from '../../batDirectiveView/bt-create-fsr-service/bt-create-fsr-service.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';



@NgModule({
  declarations: [BatFsrServiceComponent],
  imports: [
    CommonModule,
    BatFsrServiceRouting,
    NgSelectModule,
    BtCreateFsrServiceModule,
    BsDropdownModule,
    ModalModule
  ]
})
export class BatFsrServiceModule { }
