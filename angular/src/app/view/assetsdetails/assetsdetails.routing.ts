
import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { AssetsdetailsComponent } from "./assetsdetails.component";

const routes:Routes=[{
    path:'',component:AssetsdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class AssetsdetailsRouting{}