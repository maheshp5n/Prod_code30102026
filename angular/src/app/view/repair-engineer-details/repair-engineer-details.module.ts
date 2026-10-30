import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RepairEngineerdetailsRouting } from './repair-engineer-details.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { CreateManageinventoryModule } from '../../DirectivesView/create-manageinventory/create-manageinventory.module';
import { AssignToEngModule } from '../../DirectivesView/assign-to-eng/assign-to-eng.module';
import { ReceiveFromEngModule } from '../../DirectivesView/receive-from-eng/receive-from-eng.module';
import { DispatchCustomerModule } from '../../DirectivesView/dispatch-customer/dispatch-customer.module';
import { InventroyProductConsumedModule } from '../../DirectivesView/inventroy-product-consumed/inventroy-product-consumed.module';
import { RepairEngineerDetailsComponent } from './repair-engineer-details.component';
import { AssetsService } from '../../services/assets.services';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RepairEngineerdetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateManageinventoryModule,
    AssignToEngModule,
    ReceiveFromEngModule,
    DispatchCustomerModule,
    InventroyProductConsumedModule
],
  declarations: [ RepairEngineerDetailsComponent ],
  providers: [NgxSpinnerService]
})
export class RepairEngineerDetailsModule { }
