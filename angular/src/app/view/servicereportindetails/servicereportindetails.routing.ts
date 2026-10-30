import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServicereportindetailsComponent } from './servicereportindetails.component';

export const routes:Routes=[{
    path:'',component:ServicereportindetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ServicereportindetailsRouting{}