import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ProductLineItemsComponent } from "./ProductLineItems.component";

const routes: Routes = [
    {
        path: '', component: ProductLineItemsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProductLineItemsRouting { }