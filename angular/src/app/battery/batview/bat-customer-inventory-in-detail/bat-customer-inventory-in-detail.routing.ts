import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatCustomerInventoryInDetailComponent } from "./bat-customer-inventory-in-detail.component";

const routes : Routes = [
    {
        path :'',
        component :BatCustomerInventoryInDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatCustomerInventoryInDetailRouting{}