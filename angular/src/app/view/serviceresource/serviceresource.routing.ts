import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceresourceComponent } from './serviceresource.component';

export const routes:Routes=[{
    path:'',component:ServiceresourceComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ServiceresourceRouting{}