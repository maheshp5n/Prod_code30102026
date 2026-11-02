import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepairEngineerRouting } from './repair-engineer.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
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
