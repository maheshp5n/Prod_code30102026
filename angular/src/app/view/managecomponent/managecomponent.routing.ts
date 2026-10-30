import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ManagecomponentComponent } from './managecomponent.component';

export const routes:Routes=[{
    path:'',component:ManagecomponentComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ManagecomponentRouting{}