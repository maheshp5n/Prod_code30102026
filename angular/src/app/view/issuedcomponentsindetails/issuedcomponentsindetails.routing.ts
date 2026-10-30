import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { IssuedcomponentsindetailsComponent } from './issuedcomponentsindetails.component';

export const routes:Routes=[{
    path:'',component:IssuedcomponentsindetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class IssuedcomponentsindetailsRouting{}