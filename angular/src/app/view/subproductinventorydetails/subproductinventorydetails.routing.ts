import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SubproductinventorydetailsComponent } from './subproductinventorydetails.component';

export const routes:Routes=[{
    path:'',component:SubproductinventorydetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class SubproductinventorydetailsRouting{}