import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { WccComponent } from './wcc.component';

const routes:Routes=[{
    path:'',component:WccComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class WCCRouting{

}