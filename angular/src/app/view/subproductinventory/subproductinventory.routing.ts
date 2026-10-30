import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SubproductinventoryComponent } from './subproductinventory.component';

export const routes:Routes=[{
    path:'',component:SubproductinventoryComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class SubproductinventoryRouting{}