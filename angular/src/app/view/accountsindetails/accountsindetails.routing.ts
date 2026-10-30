import { Routes, RouterModule } from "@angular/router";
import { AccountsindetailsComponent } from "./accountsindetails.component";
import { NgModule } from "@angular/core";

const routes:Routes=[{
    path:'',component:AccountsindetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class AccountsindetailsRouting{}