import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatApprovalflowDetailsComponent } from "./bat-approvalflow-details.component";

const routes:Routes=[{
    path:'',
    component:BatApprovalflowDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatApprovalflowDetailsRoutingModule{}