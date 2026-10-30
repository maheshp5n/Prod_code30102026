import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceresourcedetailsComponent } from './serviceresourcedetails.component';

export const routes:Routes=[{
    path:'',component:ServiceresourcedetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ServiceresourcedetailsRouting{}