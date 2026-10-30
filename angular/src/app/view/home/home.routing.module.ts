import { HomeComponent } from "./home.component";
import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ChartsModule } from "ng2-charts"
const routes:Routes=[{
    path:'',component:HomeComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],

})
export class Homerouting{}