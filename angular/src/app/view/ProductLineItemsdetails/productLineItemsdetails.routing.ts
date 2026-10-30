import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ProductLineItemsdetailsComponent } from './productLineItemsdetails.component';

const routes:Routes=[{
    path:'',component: ProductLineItemsdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductLineItemsdetailsRouting {}