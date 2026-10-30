import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatApprovalflowComponent } from "./bat-approvalflow.component";

const routes:Routes=[
    {
        path:'',
        redirectTo:'details'
    },
    {
    path:'',
    component:BatApprovalflowComponent,
    children:[
        {
            path:'details',
            loadChildren:()=>import('./bat-approvalflow-details/bat-approvalflow-details.module').then(m=>m.BatApprovalflowDetailsModule)
        },{
            path:'releted',
            loadChildren:()=>import('./bat-approvalflow-releated/bat-approvalflow-releated.module').then(m=>m.BatApprovalflowReleatedModule)
        }
    ]
},]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatApprovalFlowRoutingModule{}