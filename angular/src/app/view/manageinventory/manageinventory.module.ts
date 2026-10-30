import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { OrderModule } from 'ngx-order-pipe';
import { CreateManageinventoryModule } from '../../DirectivesView/create-manageinventory/create-manageinventory.module';
import { SearchfiltPipe } from '../manageinventory/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { ManageinventoryRouting } from './manageinventory.routing';
import { ManageinventoryComponent } from './manageinventory.component';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { caseService } from '../../services/caseService.service';

@NgModule({
  imports: [
    CommonModule,
    ManageinventoryRouting,
    FormsModule,ReactiveFormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    NgxSpinnerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OrderModule,
    CreateManageinventoryModule,
    
  ],
  declarations: [ ManageinventoryComponent,SearchfiltPipe ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService,ManageInventoryService,caseService]  
})
export class ManageinventoryModule { }
