import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { RepairEngineerDetailsComponent } from './repair-engineer-details.component';

export const routes:Routes=[{
    path:'',component:RepairEngineerDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RepairEngineerdetailsRouting{}