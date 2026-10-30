import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { JobwarrantydetailsComponent } from './jobwarrantydetails.component';

export const routes:Routes=[{
    path:'',component:JobwarrantydetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class JobwarrantydetailsRouting{}