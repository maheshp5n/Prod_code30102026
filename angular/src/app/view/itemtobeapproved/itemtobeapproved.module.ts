import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItemtobeApprovedRouting } from './itemtobeapproved.routing';
import { ItemtobeapprovedComponent } from './itemtobeapproved.component';
import { BsDropdownModule, AlertModule, ModalModule, PaginationModule, BsDatepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  declarations: [ItemtobeapprovedComponent],
  imports: [
    CommonModule,
    ItemtobeApprovedRouting,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    FormsModule,
    NgSelectModule,
    PaginationModule,BsDatepickerModule.forRoot()
  ],
  providers:[NgxSpinnerService,DatePipe]
})
export class ItemtobeapprovedModule { }
