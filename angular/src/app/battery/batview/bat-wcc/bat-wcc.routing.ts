import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatWccComponent } from "./bat-wcc.component";

const routes:Routes=[{
    path:'',component:BatWccComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatWCCRouting{

}