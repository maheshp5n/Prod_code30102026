import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ManageinventorydetailsComponent } from './manageinventorydetails.component';

export const routes:Routes=[{
    path:'',component:ManageinventorydetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ManageinventorydetailsRouting{}