import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatAllViewPageComponent } from "./bat-all-view-page.component";

const routes:Routes=[{
    path:'',component:BatAllViewPageComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatAllViewPageRouting{

}