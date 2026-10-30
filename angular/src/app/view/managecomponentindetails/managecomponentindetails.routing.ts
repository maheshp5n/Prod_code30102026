import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ManagecomponentindetailsComponent } from './managecomponentindetails.component';

export const routes:Routes=[{
    path:'',component:ManagecomponentindetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ManagecomponentindetailsRouting{}