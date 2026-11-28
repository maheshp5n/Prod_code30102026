import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/accounts.services';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { deliverychallandetailsComponent } from './deliverychallandetails.component';
import { deliverychallanDetailsRouting } from './deliverychallandetails.routing';
import { CreateNewdeliverychallanModule } from '../../DirectivesView/CreateNewDeliveryChallan/create-new-deliverychallan.module';
import { deliverychallanService } from '../../services/delivery-challan.service';
import { CreateassetModule } from '../../DirectivesView/createasset/createasset.module';

@NgModule({
  imports: [
    CommonModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    deliverychallanDetailsRouting,
    ContactcreateModule,
    CreateNewdeliverychallanModule,
    BsDropdownModule,
    CreateassetModule
  ],
  declarations: [deliverychallandetailsComponent],
  providers: [AccountService, NgxSpinnerService, Loginservices, deliverychallanService]
})
export class deliverychallandetailsModule { }
