import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CityviewComponent } from "./cityview.component";


const routes:Routes=[{
    path:'',component:CityviewComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class cityViewRouting{}