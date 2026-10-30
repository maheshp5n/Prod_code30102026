import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SystemconfigurationparametersDetailsComponent } from './systemconfigurationparameters-details.component';

export const routes:Routes=[{
    path:'',component:SystemconfigurationparametersDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class SystemconfigurationparametersDetailsRouting{}