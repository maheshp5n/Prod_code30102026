import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatCustomerInventoryComponent } from './bat-customer-inventory.component';

const routes: Routes = [{
    path: '',
    component: BatCustomerInventoryComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCustomerInventoryRouting {

}
