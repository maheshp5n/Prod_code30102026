import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SsaComponent } from './ssa.component';


const routes:Routes=[{
    path:'',component:SsaComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class SSARouting{

}