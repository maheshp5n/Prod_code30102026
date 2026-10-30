import { Routes, RouterModule } from "@angular/router";
import { WorkflowsComponent } from './workflows.component';
import { NgModule } from '@angular/core';

const routes:Routes=[{
    path:'',
    component:WorkflowsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class WorkflowRouting{}