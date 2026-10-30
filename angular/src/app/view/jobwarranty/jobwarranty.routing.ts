import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { JobwarrantyComponent } from './jobwarranty.component';

export const routes:Routes=[{
    path:'',component:JobwarrantyComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class JobwarrantyRouting{}