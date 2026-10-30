import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { QueteTemplateTandCComponent } from './quete-template-tand-c.component';


const routes:Routes=[{
    path:'',component:QueteTemplateTandCComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class QueteTemplateTCRouting{
    
}