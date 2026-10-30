import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ManageinventoryComponent } from './manageinventory.component';

export const routes:Routes=[{
    path:'',component:ManageinventoryComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ManageinventoryRouting{}