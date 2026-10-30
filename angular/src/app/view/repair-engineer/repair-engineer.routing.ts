import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { RepairEngineerComponent } from './repair-engineer.component';

export const routes:Routes=[{
    path:'',component:RepairEngineerComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RepairEngineerRouting{}