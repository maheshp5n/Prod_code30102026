import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateNewWorkOrderModule } from '../../DirectivesView/create-new-work-order/create-new-work-order.module';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { FormsModule } from '@angular/forms';
import { WorkorderRouting } from './workorder.routing';
import { WorkorderComponent } from './workorder.component';
import { SearchfiltPipe } from '../workorder/searchfilt.pipe';
import { AccountService } from '../../services/accounts.services';
import { OrderModule } from 'ngx-order-pipe';
import { Loginservices } from '../../services/login.services';



@NgModule({
  imports: [
    CommonModule,
    WorkorderRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule.forRoot(),
    CreateNewWorkOrderModule,
    NgxSpinnerModule,
    OrderModule
    
  ],
  declarations: [WorkorderComponent,SearchfiltPipe], 
  exports: [SearchfiltPipe],
  providers: [NgxSpinnerService,Loginservices,AccountService],
  
})
export class WorkorderModule { }
