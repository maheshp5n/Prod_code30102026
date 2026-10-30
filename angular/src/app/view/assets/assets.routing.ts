import { AssetsComponent } from "./assets.component";
import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";

export const routes:Routes=[{
    path:'',component:AssetsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class Assetsrouting{ }