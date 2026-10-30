import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SitesComponent } from "./sites.component";


const routes:Routes=[{
    path:'',component:SitesComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class Sitestrouting{

}