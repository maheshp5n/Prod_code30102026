import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Loginservices } from '../../../services/login.services';
import { BatAllViewPageComponent } from './bat-all-view-page.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { RouterModule } from '@angular/router';
import { BatAllViewPageRouting } from './bat-all-view-page.routing';
import { BtCreateexpensesManagementModule } from '../../batDirectiveView/bt-createexpenses-management/bt-createexpenses-management.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,ModalModule,RouterModule,BatAllViewPageRouting,BtCreateexpensesManagementModule,BsDropdownModule
  ],
  declarations: [BatAllViewPageComponent],
//   exports: [SearchfiltPipe],
  providers: [Loginservices, NgxSpinnerService]
})
export class BatAllViewModule{

}