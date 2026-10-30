import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatTobeApprovedComponent } from "./bat-tobe-approved.component";

const routes:Routes=[{
    path:'',
    component:BatTobeApprovedComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatTobeApprovedRouting{}