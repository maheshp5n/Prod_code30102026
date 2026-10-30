import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ZoneComponent } from "./zone.component";


const routes:Routes=[{
    path:'',component:ZoneComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class ZoneRouting{

}