import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { workorderindetailsRouting } from './workorderindetails.routing';
import { CreateNewWorkOrderModule } from '../../DirectivesView/create-new-work-order/create-new-work-order.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AccountService } from '../../services/accounts.services';
import { Loginservices } from '../../services/login.services';
import { WorkorderindetailsComponent } from './workorderindetails.component';
import { AddProductConsumedModule } from '../../DirectivesView/add-product-consumed/add-product-consumed.module';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    workorderindetailsRouting,
    CreateNewWorkOrderModule,  
    AddProductConsumedModule,  
    NgxSpinnerModule
  ],
  declarations: [WorkorderindetailsComponent],
  providers: [NgxSpinnerService,AccountService,Loginservices]
})
export class WorkorderindetailsModule { }
