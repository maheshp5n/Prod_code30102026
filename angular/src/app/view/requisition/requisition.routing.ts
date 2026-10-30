import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { RequisitionComponent } from './requisition.component';

export const routes:Routes=[{
    path:'',component:RequisitionComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RequisitionRouting{}