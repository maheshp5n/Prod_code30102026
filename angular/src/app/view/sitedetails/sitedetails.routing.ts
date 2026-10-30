import { Routes, RouterModule } from "@angular/router";
import { SitedetailsComponent } from "./sitedetails.component";
import { NgModule } from "@angular/core";


const routes:Routes=[{
    path:'',component:SitedetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class Sitedetailsrouting{}