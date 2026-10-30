import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { QueteTemplateTandCDetailsComponent } from './quete-template-tand-cdetails.component';

const routes:Routes=[{
    path:'',component:QueteTemplateTandCDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class QueteTemplateTandCDetailsRouting{}