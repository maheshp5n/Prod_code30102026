import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepairEngineerRouting } from './repair-engineer.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateManageinventoryModule } from '../../DirectivesView/create-manageinventory/create-manageinventory.module';
import { RepairEngineerComponent } from './repair-engineer.component';
import { SearchfiltPipe } from '../repair-engineer/searchfilt.pipe';
import { ManageInventoryService } from '../../services/manageinventory.services';

@NgModule({
  imports: [
    CommonModule,
    RepairEngineerRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateManageinventoryModule,
    
  ],
  declarations: [RepairEngineerComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [NgxSpinnerService,ManageInventoryService]  
})
export class RepairEngineerModule { }
