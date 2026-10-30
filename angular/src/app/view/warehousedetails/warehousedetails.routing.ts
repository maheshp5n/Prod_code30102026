import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { WarehousedetailsComponent } from "./warehousedetails.component";

const routes: Routes = [
    {
        path: '',
        component: WarehousedetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WarehouseDetailsRouting { }