import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { IssuedcomponentsComponent } from './issuedcomponents.component';

export const routes:Routes=[{
    path:'',component:IssuedcomponentsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class IssuedcomponentsRouting{}