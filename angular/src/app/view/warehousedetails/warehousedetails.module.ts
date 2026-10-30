import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactsService } from '../../services/contacts.services';
import { AccountService } from '../../services/accounts.services';
import { WarehouseDetailsRouting } from './warehousedetails.routing';
import { WarehouseService } from '../../services/warehouse.service';
import { WarehousedetailsComponent } from './warehousedetails.component';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { ligalEntityService } from '../../services/ligalentity.services';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateNewWarehouseModule } from '../../DirectivesView/create-new-warehouse/create-new-warehouse.module';

@NgModule({
  imports: [
    CommonModule,
    WarehouseDetailsRouting,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreateNewWarehouseModule,
    BsDropdownModule
  ],
  declarations: [WarehousedetailsComponent],
  providers: [WarehouseService, AccountService, ligalEntityService, NgxSpinnerService]
})
export class WarehousedetailsModule { }
