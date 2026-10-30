import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServicereportComponent } from './servicereport.component';

export const routes:Routes=[{
    path:'',component:ServicereportComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ServicereportRouting{}