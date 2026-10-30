import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatApprovalviewlistComponent } from "./bat-approvalviewlist.component";

const route:Routes=[{
    path:'',
    component:BatApprovalviewlistComponent
}]
@NgModule({
    imports:[RouterModule.forChild(route)],
    exports:[RouterModule]
})
export class BatApprovalviewlistRoutingModule{}