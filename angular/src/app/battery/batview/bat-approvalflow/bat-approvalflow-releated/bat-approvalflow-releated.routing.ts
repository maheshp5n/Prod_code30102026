import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatApprovalflowReleatedComponent } from "./bat-approvalflow-releated.component";

const routes:Routes=[{
    path:'',
    component:BatApprovalflowReleatedComponent
}];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatApprovalFlowReletatedRoutingModule{}